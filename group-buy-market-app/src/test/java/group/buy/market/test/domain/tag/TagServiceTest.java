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
        log.info("测试结果:{}", bitSet.isExists());
        log.info("xfg01 存在，预期结果为 true，测试结果:{}", bitSet.get(redisService.getIndexFromUserId("xfg01")));
        log.info("xfg02 存在，预期结果为 true，测试结果:{}", bitSet.get(redisService.getIndexFromUserId("xfg02")));
        log.info("xfg03 存在，预期结果为 true，测试结果:{}", bitSet.get(redisService.getIndexFromUserId("xfg03")));
    }

    @Test
    public void test_null_tag_bitmap() {
        RBitSet bitSet = redisService.getBitSet("null");
        log.info("测试结果:{}", bitSet.isExists());
    }

}
