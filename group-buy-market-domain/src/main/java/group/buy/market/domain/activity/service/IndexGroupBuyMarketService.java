package group.buy.market.domain.activity.service;

import group.buy.market.domain.activity.model.entity.MarketProductEntity;
import group.buy.market.domain.activity.model.entity.TrialBalanceEntity;
import org.springframework.stereotype.Service;

/**
 * 返回首页展示结果
 */
public interface IndexGroupBuyMarketService {

    /**
     * 首页试算
     * @param marketProductEntity
     * @return
     * @throws Exception
     */
    TrialBalanceEntity indexMarketTrial(MarketProductEntity marketProductEntity) throws Exception;
}