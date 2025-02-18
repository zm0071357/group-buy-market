package group.buy.market.domain.activity.service.trial.node;

import group.buy.market.domain.activity.model.entity.MarketProductEntity;
import group.buy.market.domain.activity.model.entity.TrialBalanceEntity;
import group.buy.market.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import group.buy.market.domain.activity.service.trial.AbstractGroupBuyMarketSupport;
import group.buy.market.domain.activity.service.trial.factory.DefaultActivityStrategyFactory;
import group.buy.market.domain.tag.adapter.repository.TagRepository;
import group.buy.market.types.design.framework.tree.StrategyHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 人群标签过滤节点
 */
@Slf4j
@Service
public class TagNode extends AbstractGroupBuyMarketSupport<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> {

    @Resource
    private EndNode endNode;

    @Resource
    private TagRepository tagRepository;

    @Override
    protected TrialBalanceEntity doApply(MarketProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        GroupBuyActivityDiscountVO groupBuyActivityDiscountVO = dynamicContext.getGroupBuyActivityDiscountVO();
        // 人群标签
        String tagId = groupBuyActivityDiscountVO.getTagId();
        // 活动是否可见
        boolean visible = groupBuyActivityDiscountVO.isVisible();
        // 活动是否可参加
        boolean enable = groupBuyActivityDiscountVO.isEnable();

        // 没有设置人群标签，所有用户都可参与
        if (StringUtils.isBlank(tagId)) {
            dynamicContext.setVisible(true);
            dynamicContext.setEnable(true);
            return router(requestParameter, dynamicContext);
        }

        // 判断用户是否在人群标签范围内
        boolean within = tagRepository.isTagCrowRange(tagId, requestParameter.getUserId());
        // 在人群标签内则可见可参加
        // 不在人群标签内则根据配置决定是否可见可参加
        dynamicContext.setVisible(within || visible);
        dynamicContext.setEnable(within || enable);
        return router(requestParameter, dynamicContext);
    }

    @Override
    public StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MarketProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) {
        return endNode;
    }
}
