package group.buy.market.domain.trade.adapter.port;

import group.buy.market.domain.trade.model.entity.NotifyTaskEntity;

public interface TradePortService {

    /**
     * 执行回调任务
     * @param notifyTaskEntity
     * @return
     * @throws Exception
     */
    String groupBuyNotify(NotifyTaskEntity notifyTaskEntity) throws Exception;

}
