package group.buy.market.domain.activity.service.trial.node;

import group.buy.market.domain.activity.model.entity.MarketProductEntity;
import group.buy.market.domain.activity.model.entity.TrialBalanceEntity;
import group.buy.market.domain.activity.service.trial.AbstractGroupBuyMarketSupport;
import group.buy.market.domain.activity.service.trial.factory.DefaultActivityStrategyFactory;
import group.buy.market.types.design.framework.tree.StrategyHandler;
import group.buy.market.types.enums.ResponseCode;
import group.buy.market.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 开关节点
 * 在程序运行过程中，直接动态变更某些属性配置。这些动态变更的配置包括降级和切量的开关，也包括一些功能程序的白名单用户测试
 */
@Slf4j
@Service
public class SwitchNode extends AbstractGroupBuyMarketSupport<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> {

    @Resource
    private MarketNode marketNode;

    /**
     * 处理
     * @param requestParameter 入参
     * @param dynamicContext 动态上下文
     * @return
     * @throws Exception
     */
    @Override
    public TrialBalanceEntity doApply(MarketProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {

        String userId = requestParameter.getUserId();
        // 降级
        if (activityRepository.downgradeSwitch()) {
           throw new AppException(ResponseCode.E0003.getCode(), ResponseCode.E0003.getInfo());
        }
        // 切量
        if (!activityRepository.cutRange(userId)) {
           throw new AppException(ResponseCode.E0004.getCode(), ResponseCode.E0004.getInfo());
        }

        return router(requestParameter, dynamicContext);
    }

    /**
     * 获取下个节点 - 营销试算节点
     * @param requestParameter 入参
     * @param dynamicContext 动态上下文
     * @return
     */
    @Override
    public StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MarketProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) {
        return marketNode;
    }
}
