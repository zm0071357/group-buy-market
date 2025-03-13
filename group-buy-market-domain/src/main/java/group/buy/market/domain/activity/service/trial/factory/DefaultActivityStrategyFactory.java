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
import org.springframework.stereotype.Service;

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

    /**
     * 定义上下文信息
     * 用于节点间的传输使用
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DynamicContext {

        /**
         * 活动折扣配置值对象
         */
        private GroupBuyActivityDiscountVO groupBuyActivityDiscountVO;

        /**
         * 商品值对象
         */
        private SkuVO skuVO;
    }

}
