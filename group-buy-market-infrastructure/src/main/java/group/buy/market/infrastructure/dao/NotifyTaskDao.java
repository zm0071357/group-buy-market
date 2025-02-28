package group.buy.market.infrastructure.dao;

import group.buy.market.infrastructure.dao.po.NotifyTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NotifyTaskDao {

    /**
     * 插入回调任务
     * @param notifyTask
     */
    void insert(NotifyTask notifyTask);

    /**
     * 查询没有被执行的回调任务集合
     * @return
     */
    List<NotifyTask> queryUnExecutedNotifyTaskList();

    /**
     * 查询没有被执行的回调任务
     * @param teamId 拼团ID
     * @return
     */
    NotifyTask queryUnExecutedNotifyTaskByTeamId(String teamId);

    /**
     * 执行回调任务成功
     * @param teamId 拼团ID
     * @return
     */
    int updateNotifyTaskStatusSuccess(String teamId);

    /**
     * 执行回调任务失败
     * @param teamId 拼团ID
     * @return
     */
    int updateNotifyTaskStatusError(String teamId);

    /**
     * 执行回调任务重试
     * @param teamId 拼团ID
     * @return
     */
    int updateNotifyTaskStatusRetry(String teamId);
}
