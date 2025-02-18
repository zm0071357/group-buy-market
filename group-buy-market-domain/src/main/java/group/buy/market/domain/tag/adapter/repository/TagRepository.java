package group.buy.market.domain.tag.adapter.repository;

import group.buy.market.domain.tag.model.entity.CrowdTagsJobEntity;

/**
 * 人群标签仓储
 */
public interface TagRepository {

    /**
     * 根据标签ID和批次ID查询人群标签任务
     * @param tagId 标签ID
     * @param batchId 批次ID
     * @return
     */
    CrowdTagsJobEntity queryCrowdTagsJobEntity(String tagId, String batchId);

    /**
     * 增加人群标签明细
     * @param tagId 标签ID
     * @param userId 用户ID
     */
    void addCrowdTagsUserId(String tagId, String userId);

    /**
     * 更新人群标签统计量
     * @param tagId 标签ID
     * @param size 统计量
     */
    void updateCrowdTagsStatistics(String tagId, int size);

    /**
     * 判断用户是否在人群标签范围内
     * @param tagId 人群标签ID
     * @param userId 用户ID
     * @return
     */
    boolean isTagCrowRange(String tagId, String userId);
}
