package group.buy.market.infrastructure.dao;

import group.buy.market.infrastructure.dao.po.CrowdTagsDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CrowdTagsDetailDao {

    /**
     * 添加人群标签明细
     * @param crowdTagsDetailReq
     */
    void addCrowdTagsUserId(CrowdTagsDetail crowdTagsDetailReq);

}
