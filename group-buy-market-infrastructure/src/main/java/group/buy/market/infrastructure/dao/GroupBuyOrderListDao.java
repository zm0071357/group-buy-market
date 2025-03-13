package group.buy.market.infrastructure.dao;

import group.buy.market.infrastructure.dao.po.GroupBuyOrderList;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GroupBuyOrderListDao {

    /**
     * 插入用户拼单明细
     * @param groupBuyOrderList 用户拼单明细
     */
    void insert(GroupBuyOrderList groupBuyOrderList);

    /**
     * 查询未支付订单
     * @param groupBuyOrderReq 用户拼单明细
     * @return
     */
    GroupBuyOrderList queryGroupBuyOrderRecordByOutTradeNo(GroupBuyOrderList groupBuyOrderReq);

}
