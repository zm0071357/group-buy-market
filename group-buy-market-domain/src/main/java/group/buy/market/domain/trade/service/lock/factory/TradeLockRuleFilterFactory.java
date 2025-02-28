package group.buy.market.domain.trade.service.lock.factory;

import group.buy.market.domain.trade.model.entity.GroupBuyActivityEntity;
import group.buy.market.domain.trade.model.entity.TradeLockRuleCommandEntity;
import group.buy.market.domain.trade.model.entity.TradeLockRuleFilterBackEntity;
import group.buy.market.domain.trade.service.lock.filter.ActivityUsabilityRuleFilter;
import group.buy.market.domain.trade.service.lock.filter.UserTakeLimitRuleFilter;
import group.buy.market.types.design.framework.link.model2.LinkArmory;
import group.buy.market.types.design.framework.link.model2.chain.BusinessLinkedList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * 交易规则过滤工厂
 */
@Slf4j
@Service
public class TradeLockRuleFilterFactory {

    @Bean("tradeRuleFilter")
    public BusinessLinkedList<TradeLockRuleCommandEntity, TradeLockRuleFilterFactory.DynamicContext, TradeLockRuleFilterBackEntity> tradeRuleFilter(ActivityUsabilityRuleFilter activityUsabilityRuleFilter, UserTakeLimitRuleFilter userTakeLimitRuleFilter) {
        // 组装链
        LinkArmory<TradeLockRuleCommandEntity, DynamicContext, TradeLockRuleFilterBackEntity> linkArmory =
                new LinkArmory<>("交易规则过滤链", activityUsabilityRuleFilter, userTakeLimitRuleFilter);

        // 链对象
        return linkArmory.getLogicLink();
    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DynamicContext {

        /**
         * 拼团活动信息
         */
        private GroupBuyActivityEntity groupBuyActivityEntity;
    }

}
