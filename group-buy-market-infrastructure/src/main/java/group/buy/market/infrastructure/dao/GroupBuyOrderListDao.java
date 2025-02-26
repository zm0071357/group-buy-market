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
     * 查询用户拼单明细
     * @param groupBuyOrderReq 用户拼单明细
     * @return
     */
    GroupBuyOrderList queryGroupBuyOrderRecordByOutTradeNo(GroupBuyOrderList groupBuyOrderReq);

    /**
     * 查询用户参与拼团活动次数
     * @param groupBuyOrderListReq 用户拼单信息
     * @return
     */
    Integer queryOrderCount(GroupBuyOrderList groupBuyOrderListReq);
}
