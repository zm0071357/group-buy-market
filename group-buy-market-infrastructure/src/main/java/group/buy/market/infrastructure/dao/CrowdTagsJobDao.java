package group.buy.market.infrastructure.dao;

import group.buy.market.infrastructure.dao.po.CrowdTagsJob;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CrowdTagsJobDao {

    /**
     * 查询人群标签任务
     * @param crowdTagsJobReq
     * @return
     */
    CrowdTagsJob queryCrowdTagsJob(CrowdTagsJob crowdTagsJobReq);

}
