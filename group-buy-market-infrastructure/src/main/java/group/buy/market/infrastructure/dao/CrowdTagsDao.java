package group.buy.market.infrastructure.dao;

import group.buy.market.infrastructure.dao.po.CrowdTags;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CrowdTagsDao {

    /**
     * 更新人群标签统计量
     * @param crowdTagsReq
     */
    void updateCrowdTagsStatistics(CrowdTags crowdTagsReq);

}
