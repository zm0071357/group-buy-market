package group.buy.market.infrastructure.dao;

import group.buy.market.infrastructure.dao.po.Sku;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SkuDao {

    /**
     * 根据商品Id查询商品
     * @param goodsId
     * @return
     */
    Sku querySkuByGoodsId(String goodsId);

}
