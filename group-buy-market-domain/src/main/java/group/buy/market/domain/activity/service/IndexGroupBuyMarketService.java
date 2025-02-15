package group.buy.market.domain.activity.service;

import group.buy.market.domain.activity.model.entity.MarketProductEntity;
import group.buy.market.domain.activity.model.entity.TrialBalanceEntity;

/**
 * 返回首页展示结果
 */
public interface IndexGroupBuyMarketService {
    TrialBalanceEntity indexMarketTrial(MarketProductEntity marketProductEntity) throws Exception;
}