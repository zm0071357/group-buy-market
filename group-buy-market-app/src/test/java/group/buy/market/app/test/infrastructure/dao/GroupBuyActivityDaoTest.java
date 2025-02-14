package group.buy.market.app.test.infrastructure.dao;

import com.alibaba.fastjson.JSON;
import group.buy.market.infrastructure.dao.GroupBuyActivityDao;
import group.buy.market.infrastructure.dao.po.GroupBuyActivity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupBuyActivityDaoTest {

    @Resource
    private GroupBuyActivityDao groupBuyActivityDao;

    @Test
    public void test_queryGroupBuyActivityList() {
        List<GroupBuyActivity> groupBuyActivities = groupBuyActivityDao.queryGroupBuyActivityList();
        log.info("测试结果:{}", JSON.toJSONString(groupBuyActivities));
    }

}

