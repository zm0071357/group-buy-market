package group.buy.market.domain.trade.service.filter;

import group.buy.market.domain.trade.adapter.repository.TradeRepository;
import group.buy.market.domain.trade.model.entity.GroupBuyActivityEntity;
import group.buy.market.domain.trade.model.entity.TradeRuleCommandEntity;
import group.buy.market.domain.trade.model.entity.TradeRuleFilterBackEntity;
import group.buy.market.domain.trade.service.factory.TradeRuleFilterFactory;
import group.buy.market.types.design.framework.link.model2.handler.LogicHandler;
import group.buy.market.types.enums.ActivityStatusEnumVO;
import group.buy.market.types.enums.ResponseCode;
import group.buy.market.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 活动的可用性，规则过滤【状态、有效期】
 */
@Slf4j
@Service
public class ActivityUsabilityRuleFilter implements LogicHandler<TradeRuleCommandEntity, TradeRuleFilterFactory.DynamicContext, TradeRuleFilterBackEntity> {

    @Resource
    private TradeRepository tradeRepository;

    @Override
    public TradeRuleFilterBackEntity apply(TradeRuleCommandEntity requestParameter, TradeRuleFilterFactory.DynamicContext dynamicContext) throws Exception {
        log.info("交易规则过滤-活动可用性校验{} activityId:{}", requestParameter.getUserId(), requestParameter.getActivityId());
        // 查询活动信息
        GroupBuyActivityEntity groupBuyActivityEntity = tradeRepository.queryGroupBuyActivityByActivityId(requestParameter.getActivityId());
        // 判断活动是否可用
        if (!ActivityStatusEnumVO.EFFECTIVE.equals(groupBuyActivityEntity.getStatus())) {
            throw new AppException(ResponseCode.E0101.getCode(), ResponseCode.E0101.getInfo());
        }
        // 判断是否在活动有效期内
        Date currentTime = new Date();
        if (currentTime.before(groupBuyActivityEntity.getStartTime()) || currentTime.after(groupBuyActivityEntity.getEndTime())) {
            throw new AppException(ResponseCode.E0102.getCode(), ResponseCode.E0102.getInfo());
        }
        // 写入上下文
        dynamicContext.setGroupBuyActivityEntity(groupBuyActivityEntity);
        return next(requestParameter, dynamicContext);
    }
}
