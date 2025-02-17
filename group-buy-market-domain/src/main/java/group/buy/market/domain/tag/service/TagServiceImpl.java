package group.buy.market.domain.tag.service;

import group.buy.market.domain.tag.adapter.repository.TagRepository;
import group.buy.market.domain.tag.model.entity.CrowdTagsJobEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagRepository tagRepository;

    @Override
    public void execTagBatchJob(String tagId, String batchId) {
        log.info("人群标签批次任务 tagId:{} batchId:{}", tagId, batchId);
        // 查询批次任务
        CrowdTagsJobEntity crowdTagsJobEntity = tagRepository.queryCrowdTagsJobEntity(tagId, batchId);

        // 采集用户数据 - 这部分需要采集用户的消费类数据，后续有用户发起拼单后再处理。

        // 数据写入记录
        List<String> userIdList = new ArrayList<String>() {{
            add("xiaofuge");
            add("LZM");
        }};

        // 写入数据库
        for (String userId : userIdList) {
            tagRepository.addCrowdTagsUserId(tagId, userId);
        }

        // 更新人群标签统计量
        tagRepository.updateCrowdTagsStatistics(tagId, userIdList.size());

    }
}
