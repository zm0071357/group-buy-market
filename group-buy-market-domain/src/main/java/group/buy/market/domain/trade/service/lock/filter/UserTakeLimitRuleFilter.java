package group.buy.market.domain.trade.service.lock.filter;

import group.buy.market.domain.trade.adapter.repository.TradeRepository;
import group.buy.market.domain.trade.model.entity.GroupBuyActivityEntity;
import group.buy.market.domain.trade.model.entity.TradeLockRuleCommandEntity;
import group.buy.market.domain.trade.model.entity.TradeLockRuleFilterBackEntity;
import group.buy.market.domain.trade.service.lock.factory.TradeLockRuleFilterFactory;
import group.buy.market.types.design.framework.link.model2.handler.LogicHandler;
import group.buy.market.types.enums.ResponseCode;
import group.buy.market.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户参与限制，规则过滤
 */
@Slf4j
@Service
public class UserTakeLimitRuleFilter implements LogicHandler<TradeLockRuleCommandEntity, TradeLockRuleFilterFactory.DynamicContext, TradeLockRuleFilterBackEntity> {

    @Resource
    private TradeRepository tradeRepository;

    @Override
    public TradeLockRuleFilterBackEntity apply(TradeLockRuleCommandEntity requestParameter, TradeLockRuleFilterFactory.DynamicContext dynamicContext) throws Exception {
        log.info("交易规则过滤-用户参与次数校验{} activityId:{}", requestParameter.getUserId(), requestParameter.getActivityId());
        GroupBuyActivityEntity groupBuyActivityEntity = dynamicContext.getGroupBuyActivityEntity();
        Integer count = tradeRepository.queryOrderCount(requestParameter.getActivityId(), requestParameter.getUserId());
        // 判断用户参与拼团活动次数是否到达上限
        if (groupBuyActivityEntity.getTakeLimitCount() <= count) {
            throw new AppException(ResponseCode.E0103.getCode(), ResponseCode.E0103.getInfo());
        }
        return TradeLockRuleFilterBackEntity.builder()
                .userTakeOrderCount(count)
                .build();
    }
}
