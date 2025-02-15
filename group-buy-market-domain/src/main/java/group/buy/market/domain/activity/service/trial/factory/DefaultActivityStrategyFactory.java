package group.buy.market.domain.activity.service.trial.factory;

import group.buy.market.domain.activity.model.entity.MarketProductEntity;
import group.buy.market.domain.activity.model.entity.TrialBalanceEntity;
import group.buy.market.domain.activity.service.trial.node.RootNode;
import group.buy.market.types.design.framework.tree.StrategyHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * 工厂
 */
@Service
public class DefaultActivityStrategyFactory {

    private final RootNode rootNode;

    public DefaultActivityStrategyFactory(RootNode rootNode) {
        this.rootNode = rootNode;
    }

    @Bean
    public StrategyHandler<MarketProductEntity, DynamicContext, TrialBalanceEntity> strategyHandler() {
        return rootNode;
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DynamicContext {

    }

}
