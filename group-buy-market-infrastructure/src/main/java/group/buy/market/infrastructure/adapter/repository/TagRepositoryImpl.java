package group.buy.market.infrastructure.adapter.repository;

import group.buy.market.domain.tag.adapter.repository.TagRepository;
import group.buy.market.domain.tag.model.entity.CrowdTagsJobEntity;
import group.buy.market.infrastructure.dao.CrowdTagsDao;
import group.buy.market.infrastructure.dao.CrowdTagsDetailDao;
import group.buy.market.infrastructure.dao.CrowdTagsJobDao;
import group.buy.market.infrastructure.dao.po.CrowdTags;
import group.buy.market.infrastructure.dao.po.CrowdTagsDetail;
import group.buy.market.infrastructure.dao.po.CrowdTagsJob;
import group.buy.market.infrastructure.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBitSet;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
@Slf4j
public class TagRepositoryImpl implements TagRepository {

    @Resource
    private CrowdTagsDao crowdTagsDao;

    @Resource
    private CrowdTagsDetailDao crowdTagsDetailDao;

    @Resource
    private CrowdTagsJobDao crowdTagsJobDao;

    @Resource
    private RedisService redisService;

    @Override
    public CrowdTagsJobEntity queryCrowdTagsJobEntity(String tagId, String batchId) {
        CrowdTagsJob crowdTagsJobReq = new CrowdTagsJob();
        crowdTagsJobReq.setTagId(tagId);
        crowdTagsJobReq.setBatchId(batchId);
        CrowdTagsJob crowdTagsJobRes = crowdTagsJobDao.queryCrowdTagsJob(crowdTagsJobReq);
        if (null == crowdTagsJobRes) {
            return null;
        }
        return CrowdTagsJobEntity.builder()
                .tagType(crowdTagsJobRes.getTagType())
                .tagRule(crowdTagsJobRes.getTagRule())
                .statStartTime(crowdTagsJobRes.getStatStartTime())
                .statEndTime(crowdTagsJobRes.getStatEndTime())
                .build();
    }

    @Override
    public void addCrowdTagsUserId(String tagId, String userId) {
        CrowdTagsDetail crowdTagsDetailReq = new CrowdTagsDetail();
        crowdTagsDetailReq.setTagId(tagId);
        crowdTagsDetailReq.setUserId(userId);

        try {
            crowdTagsDetailDao.addCrowdTagsUserId(crowdTagsDetailReq);
            // 写入缓存
            log.info("将userId为:{}写入tagId为：{} BitSet缓存", userId, tagId);
            RBitSet bitSet = redisService.getBitSet(tagId);
            bitSet.set(redisService.getIndexFromUserId(userId), true);
        } catch (DuplicateKeyException ignore) {
            log.info("...");
        }

    }

    @Override
    public void updateCrowdTagsStatistics(String tagId, int size) {
        CrowdTags crowdTagsReq = new CrowdTags();
        crowdTagsReq.setTagId(tagId);
        crowdTagsReq.setStatistics(size);
        crowdTagsDao.updateCrowdTagsStatistics(crowdTagsReq);
    }

    @Override
    public boolean isTagCrowRange(String tagId, String userId) {
        RBitSet bitSet = redisService.getBitSet(tagId);
        if (!bitSet.isExists()) {
            return true;
        }
        return bitSet.get(redisService.getIndexFromUserId(userId));
    }

}
