package group.buy.market.infrastructure.dao;

import group.buy.market.infrastructure.dao.po.GroupBuyOrderList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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

    /**
     * 更新订单状态
     * @param groupBuyOrderListReq
     * @return
     */
    int updateOrderStatusComplete(GroupBuyOrderList groupBuyOrderListReq);

    /**
     * 获取已完成拼团目标的订单号集合
     * @param teamId
     * @return
     */
    List<String> queryGroupBuyCompleteOrderOutTradeNoListByTeamId(String teamId);

    /**
     * 查询用户参与的拼团
     * @param groupBuyOrderListReq
     * @return
     */
    List<GroupBuyOrderList> queryInProgressUserGroupBuyOrderDetailListByUserId(GroupBuyOrderList groupBuyOrderListReq);

    /**
     * 查询用户未参与的拼团
     * @param groupBuyOrderListReq
     * @return
     */
    List<GroupBuyOrderList> queryInProgressUserGroupBuyOrderDetailListByRandom(GroupBuyOrderList groupBuyOrderListReq);

    /**
     * 查询活动下的所有拼团
     * @param activityId 活动ID
     * @return
     */
    List<GroupBuyOrderList> queryInProgressUserGroupBuyOrderDetailListByActivityId(Long activityId);
}
