package group.buy.market.types.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 回调任务状态枚举
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum NotifyTaskHttpEnumVO {

    SUCCESS("success", "成功"),
    ERROR("error", "失败"),
    NULL("null", "空执行"),
    ;

    private String code;

    private String info;
}
