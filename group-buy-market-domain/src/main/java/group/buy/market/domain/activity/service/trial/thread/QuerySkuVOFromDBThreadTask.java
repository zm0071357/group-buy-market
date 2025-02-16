package group.buy.market.domain.activity.service.trial.thread;

import group.buy.market.domain.activity.adapter.repository.ActivityRepository;
import group.buy.market.domain.activity.model.valobj.SkuVO;

import java.util.concurrent.Callable;

/**
 * 异步查询商品信息任务
 */
public class QuerySkuVOFromDBThreadTask implements Callable<SkuVO> {

    private final String goodsId;

    private final ActivityRepository activityRepository;

    public QuerySkuVOFromDBThreadTask(String goodsId, ActivityRepository activityRepository) {
        this.goodsId = goodsId;
        this.activityRepository = activityRepository;
    }

    @Override
    public SkuVO call() throws Exception {
        return activityRepository.querySkuByGoodsId(goodsId);
    }
}
