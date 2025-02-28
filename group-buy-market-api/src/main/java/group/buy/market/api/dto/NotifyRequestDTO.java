package group.buy.market.api.dto;

import lombok.Data;

import java.util.List;

/**
 * 回调请求对象
 */
@Data
public class NotifyRequestDTO {

    /**
     * 拼团ID
     */
    private String teamId;

    /**
     * 外部单号
     */
    private List<String> outTradeNoList;

}
