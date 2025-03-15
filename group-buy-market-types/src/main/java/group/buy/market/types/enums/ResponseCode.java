package group.buy.market.types.enums;

import javafx.scene.input.KeyCodeCombination;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 返回码枚举类
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ResponseCode {

    SUCCESS("0000", "成功"),
    UN_ERROR("0001", "未知失败"),
    ILLEGAL_PARAMETER("0002", "非法参数"),
    INDEX_EXCEPTION("0003", "唯一索引冲突"),
    UPDATE_ZERO("0004", "更新记录为0"),
    E0001("E0001", "不存在对应的折扣计算服务"),
    E0002("E0002", "不存在拼团营销配置"),
    E0003("E0003", "服务降级拦截"),
    E0004("E0004", "服务切量拦截"),
    E0005("E0005", "拼团组队失败，记录更新为0"),
    E0006("E0006", "拼团组队失败，锁单量已完成"),
    E0007("E0007", "不在人群标签范围内"),
    E0101("E0101", "拼团活动未生效"),
    E0102("E0102", "不在拼团活动有效时间内"),
    E0103("E0103", "当前用户参与此拼团次数已达上限"),
    ;

    private String code;
    private String info;

}
