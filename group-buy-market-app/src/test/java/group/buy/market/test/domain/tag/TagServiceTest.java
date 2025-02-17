package group.buy.market.test.domain.tag;

import group.buy.market.domain.tag.service.TagService;
import group.buy.market.infrastructure.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBitSet;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 人群标签服务测试
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TagServiceTest {

    @Resource
    private TagService tagService;
    @Resource
    private RedisService redisService;

    @Test
    public void test_tag_job() {
        tagService.execTagBatchJob("RQ_KJHKL98UU78H66554GFDV", "10001");
    }

    @Test
    public void test_get_tag_bitmap() {
        RBitSet bitSet = redisService.getBitSet("RQ_KJHKL98UU78H66554GFDV");
        // 是否存在
        log.info("xiaofuge 存在，预期结果为 true，测试结果:{}", bitSet.get(redisService.getIndexFromUserId("xiaofuge")));
        log.info("LZM 不存在，预期结果为 false，测试结果:{}", bitSet.get(redisService.getIndexFromUserId("LZM")));
    }

}
