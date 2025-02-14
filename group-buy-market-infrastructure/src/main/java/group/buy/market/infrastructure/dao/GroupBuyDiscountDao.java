package group.buy.market.infrastructure.dao;

import group.buy.market.infrastructure.dao.po.GroupBuyDiscount;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GroupBuyDiscountDao {

    /**
     * 查询所有折扣配置
     * @return
     */
    List<GroupBuyDiscount> queryGroupBuyDiscountList();

}
