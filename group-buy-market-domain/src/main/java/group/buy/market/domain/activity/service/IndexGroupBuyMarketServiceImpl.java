package group.buy.market.domain.activity.service;

import com.alibaba.fastjson.JSON;
import group.buy.market.domain.activity.model.entity.MarketProductEntity;
import group.buy.market.domain.activity.model.entity.TrialBalanceEntity;
import group.buy.market.domain.activity.service.trial.factory.DefaultActivityStrategyFactory;
import group.buy.market.types.design.framework.tree.StrategyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class IndexGroupBuyMarketServiceImpl implements IndexGroupBuyMarketService{

    @Resource
    private DefaultActivityStrategyFactory defaultActivityStrategyFactory;

    @Override
    public TrialBalanceEntity indexMarketTrial(MarketProductEntity marketProductEntity) throws Exception {
        // 构建规则树
        StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> strategyHandler = defaultActivityStrategyFactory.strategyHandler();
        // 规则树执行节点 - 获取试算结果
        TrialBalanceEntity trialBalanceEntity = strategyHandler.apply(marketProductEntity, new DefaultActivityStrategyFactory.DynamicContext());
        log.info(JSON.toJSONString(trialBalanceEntity));
        return trialBalanceEntity;
    }
}
