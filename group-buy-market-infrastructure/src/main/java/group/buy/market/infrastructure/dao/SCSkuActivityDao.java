package group.buy.market.infrastructure.dao;

import group.buy.market.infrastructure.dao.po.SCSkuActivity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SCSkuActivityDao {

    /**
     * 查询商品活动配置关联
     * @param scSkuActivity
     * @return
     */
    SCSkuActivity querySCSkuActivityBySCGoodsId(SCSkuActivity scSkuActivity);

}
