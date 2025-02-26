package group.buy.market.infrastructure.dao;


import group.buy.market.infrastructure.dao.po.GroupBuyOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GroupBuyOrderDao {

    /**
     * 添加拼团
     * @param groupBuyOrder
     */
    void insert(GroupBuyOrder groupBuyOrder);

    /**
     * 添加锁单数
     * @param teamId 拼团ID
     * @return
     */
    int updateAddLockCount(String teamId);

    /**
     * 减少锁单数
     * @param teamId 拼团ID
     * @return
     */
    int updateSubtractLockCount(String teamId);

    /**
     * 查询拼团信息
     * @param teamId 拼团ID
     * @return
     */
    GroupBuyOrder queryGroupBuyProgress(String teamId);

}
