package group.buy.market.domain.trade.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 回调配置值对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotifyConfigVO {

    /**
     * 回调方式；MQ、HTTP
     */
    private NotifyTypeEnumVO notifyType;

    /**
     * 回调消息
     */
    private String notifyMQ;

    /**
     * 回调地址
     */
    private String notifyUrl;

}
