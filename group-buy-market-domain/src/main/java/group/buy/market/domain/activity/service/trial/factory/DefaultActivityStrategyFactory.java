package group.buy.market.domain.activity.service.trial.factory;

import group.buy.market.domain.activity.model.entity.MarketProductEntity;
import group.buy.market.domain.activity.model.entity.TrialBalanceEntity;
import group.buy.market.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import group.buy.market.domain.activity.model.valobj.SkuVO;
import group.buy.market.domain.activity.service.trial.node.RootNode;
import group.buy.market.types.design.framework.tree.StrategyHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 活动策略工厂
 * 初始化根节点（串联整个链路）、定义上下文信息
 */
@Service
public class DefaultActivityStrategyFactory {

    private final RootNode rootNode;

    public DefaultActivityStrategyFactory(RootNode rootNode) {
        this.rootNode = rootNode;
    }

    /**
     * 从根节点开始串联链路
     * @return
     */
    public StrategyHandler<MarketProductEntity, DynamicContext, TrialBalanceEntity> strategyHandler() {
        return rootNode;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DynamicContext {
        private GroupBuyActivityDiscountVO groupBuyActivityDiscountVO;
        private SkuVO skuVO;
        private BigDecimal deductPrice;
        private boolean visible;
        private boolean enable;
    }

}
