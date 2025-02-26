package group.buy.market.domain.trade.service.factory;

import group.buy.market.domain.trade.adapter.repository.TradeRepository;
import group.buy.market.domain.trade.model.entity.GroupBuyActivityEntity;
import group.buy.market.domain.trade.model.entity.TradeRuleCommandEntity;
import group.buy.market.domain.trade.model.entity.TradeRuleFilterBackEntity;
import group.buy.market.domain.trade.service.filter.ActivityUsabilityRuleFilter;
import group.buy.market.domain.trade.service.filter.UserTakeLimitRuleFilter;
import group.buy.market.types.design.framework.link.model2.LinkArmory;
import group.buy.market.types.design.framework.link.model2.chain.BusinessLinkedList;
import group.buy.market.types.design.framework.link.model2.handler.LogicHandler;
import group.buy.market.types.enums.ResponseCode;
import group.buy.market.types.exception.AppException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 交易规则过滤工厂
 */
@Slf4j
@Service
public class TradeRuleFilterFactory {

    @Bean("tradeRuleFilter")
    public BusinessLinkedList<TradeRuleCommandEntity, TradeRuleFilterFactory.DynamicContext, TradeRuleFilterBackEntity> tradeRuleFilter(ActivityUsabilityRuleFilter activityUsabilityRuleFilter, UserTakeLimitRuleFilter userTakeLimitRuleFilter) {
        // 组装链
        LinkArmory<TradeRuleCommandEntity, DynamicContext, TradeRuleFilterBackEntity> linkArmory =
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
