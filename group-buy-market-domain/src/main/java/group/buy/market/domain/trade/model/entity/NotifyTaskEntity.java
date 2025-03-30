package group.buy.market.domain.trade.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 回调任务实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotifyTaskEntity {

    /**
     * 拼单组队ID
     */
    private String teamId;

    /**
     * 回调方式类型
     */
    private String notifyType;

    /**
     * MQ消息主题
     */
    private String NotifyMq;

    /**
     * 回调接口
     */
    private String notifyUrl;

    /**
     * 回调次数
     */
    private Integer notifyCount;

    /**
     * 参数对象
     */
    private String parameterJson;

    /**
     * 加锁
     * @return
     */
    public String lockKey() {
        return "notify_job_lock_key_" + this.teamId;
    }

}
