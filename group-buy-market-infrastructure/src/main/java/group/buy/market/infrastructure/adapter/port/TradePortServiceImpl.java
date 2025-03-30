package group.buy.market.infrastructure.adapter.port;

import group.buy.market.domain.trade.adapter.port.TradePortService;
import group.buy.market.domain.trade.model.entity.NotifyTaskEntity;
import group.buy.market.domain.trade.model.valobj.NotifyTypeEnumVO;
import group.buy.market.infrastructure.event.EventPublisher;
import group.buy.market.infrastructure.gateway.GroupBuyNotifyServiceImpl;
import group.buy.market.infrastructure.redis.RedisService;
import group.buy.market.types.enums.NotifyTaskHttpEnumVO;
import group.buy.market.types.enums.ResponseCode;
import group.buy.market.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class TradePortServiceImpl implements TradePortService {

    @Resource
    private GroupBuyNotifyServiceImpl groupBuyNotifyServiceImpl;

    @Resource
    private RedisService redisService;

    @Resource
    private EventPublisher eventPublisher;

    @Override
    public String groupBuyNotify(NotifyTaskEntity notifyTaskEntity) throws Exception {
        RLock lock = redisService.getLock(notifyTaskEntity.lockKey());
        try {
            if (lock.tryLock(3, 0, TimeUnit.SECONDS)) {
                try {
                    // HTTP
                    if (NotifyTypeEnumVO.HTTP.getCode().equals(notifyTaskEntity.getNotifyType())) {
                        log.info("拼团:{} 回调类型:{}", notifyTaskEntity.getTeamId(), notifyTaskEntity.getNotifyType());
                        // 无效的 notifyUrl 则直接返回成功
                        if (StringUtils.isBlank(notifyTaskEntity.getNotifyUrl()) || "暂无".equals(notifyTaskEntity.getNotifyUrl())) {
                            return NotifyTaskHttpEnumVO.SUCCESS.getCode();
                        }
                        return groupBuyNotifyServiceImpl.groupBuyNotify(notifyTaskEntity.getNotifyUrl(), notifyTaskEntity.getParameterJson());
                    }
                    // MQ
                    if (NotifyTypeEnumVO.MQ.getCode().equals(notifyTaskEntity.getNotifyType())) {
                        log.info("拼团:{} 回调类型:{}", notifyTaskEntity.getTeamId(), notifyTaskEntity.getNotifyType());
                        eventPublisher.publish(notifyTaskEntity.getNotifyMq(), notifyTaskEntity.getParameterJson());
                        return NotifyTaskHttpEnumVO.SUCCESS.getCode();
                    }
                } finally {
                    if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                        lock.unlock();
                    }
                }
            }
            return NotifyTaskHttpEnumVO.NULL.getCode();
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            return NotifyTaskHttpEnumVO.ERROR.getCode();
        }
    }
}
