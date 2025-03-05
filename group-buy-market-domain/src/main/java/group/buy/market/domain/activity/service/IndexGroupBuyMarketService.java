package group.buy.market.domain.activity.service;

import group.buy.market.domain.activity.model.entity.MarketProductEntity;
import group.buy.market.domain.activity.model.entity.TrialBalanceEntity;
import group.buy.market.domain.activity.model.entity.UserGroupBuyOrderDetailEntity;
import group.buy.market.domain.activity.model.valobj.TeamStatisticVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 返回首页展示结果
 */
public interface IndexGroupBuyMarketService {

    /**
     * 试算
     * @param marketProductEntity
     * @return
     * @throws Exception
     */
    TrialBalanceEntity indexMarketTrial(MarketProductEntity marketProductEntity) throws Exception;

    /**
     * 查询进行中的拼团
     * @param activityId 活动ID
     * @param userId 用户ID
     * @param ownerCount  个人数量
     * @param randomCount 随机数量
     * @return
     */
    List<UserGroupBuyOrderDetailEntity> queryInProgressUserGroupBuyOrderDetailList(Long activityId, String userId, int ownerCount, int randomCount);

    /**
     * 统计拼团数据
     * @param activityId
     * @return
     */
    TeamStatisticVO queryTeamStatisticByActivityId(Long activityId);
}