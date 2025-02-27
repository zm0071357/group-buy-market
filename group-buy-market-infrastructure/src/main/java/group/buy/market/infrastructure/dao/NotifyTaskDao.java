package group.buy.market.infrastructure.dao;

import group.buy.market.infrastructure.dao.po.NotifyTask;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NotifyTaskDao {

    /**
     * 插入回调任务
     * @param notifyTask
     */
    void insert(NotifyTask notifyTask);
}
