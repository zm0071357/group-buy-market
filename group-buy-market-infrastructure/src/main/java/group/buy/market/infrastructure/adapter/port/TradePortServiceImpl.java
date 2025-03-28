package group.buy.market.infrastructure.adapter.port;

import group.buy.market.domain.trade.adapter.port.TradePortService;
import group.buy.market.domain.trade.model.entity.NotifyTaskEntity;
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

    @Override
    public String groupBuyNotify(NotifyTaskEntity notifyTaskEntity) throws Exception {
        // 防止重复通知 - 加锁
        RLock lock = redisService.getLock(notifyTaskEntity.lockKey());
        try {
            if (lock.tryLock(3, 0, TimeUnit.SECONDS)) {
                try {
                    if (StringUtils.isBlank(notifyTaskEntity.getNotifyUrl()) || notifyTaskEntity.getNotifyUrl().equals("default")) {
                        return "success";
                    }
                    return groupBuyNotifyServiceImpl.groupBuyNotify(notifyTaskEntity.getNotifyUrl(), notifyTaskEntity.getParameterJson());
                } finally {
                    if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                        // 解锁
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
