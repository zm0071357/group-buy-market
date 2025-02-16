package group.buy.market.infrastructure.dao;

import group.buy.market.infrastructure.dao.po.GroupBuyActivity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GroupBuyActivityDao {

    /**
     * 查询全部拼团活动
     * @return
     */
    List<GroupBuyActivity> queryGroupBuyActivityList();

    /**
     * 查询拼团活动信息
     * @param groupBuyActivityReq
     * @return
     */
    GroupBuyActivity queryValidGroupBuyActivity(GroupBuyActivity groupBuyActivityReq);
}
