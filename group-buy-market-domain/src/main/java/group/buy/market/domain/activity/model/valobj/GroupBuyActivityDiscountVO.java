package group.buy.market.domain.activity.model.valobj;

import group.buy.market.types.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupBuyActivityDiscountVO {

    /**
     * 自增
     */
    private Long id;

    /**
     * 活动ID
     */
    private Long activityId;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 来源
     */
    private String source;

    /**
     * 渠道
     */
    private String channel;

    /**
     * 商品ID
     */
    private String goodsId;

    /**
     * 折扣配置
     */
    private GroupBuyDiscount groupBuyDiscount;

    /**
     * 拼团方式（0自动成团、1达成目标拼团）
     */
    private Integer groupType;

    /**
     * 拼团次数限制
     */
    private Integer takeLimitCount;

    /**
     * 拼团目标
     */
    private Integer target;

    /**
     * 拼团时长（分钟）
     */
    private Integer validTime;

    /**
     * 活动状态（0创建、1生效、2过期、3废弃）
     */
    private Integer status;

    /**
     * 活动开始时间
     */
    private Date startTime;

    /**
     * 活动结束时间
     */
    private Date endTime;

    /**
     * 人群标签规则标识
     */
    private String tagId;

    /**
     * 人群标签规则范围
     */
    private String tagScope;

    // 活动是否可见
    // 配置为1 不可见
    public boolean isVisible() {
        // 没有配置 可见
        if (StringUtils.isBlank(this.tagScope)) {
            return TagScopeEnum.VISIBLE.getAllow();
        }
        String[] split = this.getTagScope().split(Constants.SPLIT);
        if (split.length > 0 && split[0].equals("1")) {
            return TagScopeEnum.VISIBLE.getRefuse();
        }
        return TagScopeEnum.VISIBLE.getAllow();
    }

    // 活动是否可参与
    // 配置为2 不可参与
    public boolean isEnable() {
        if (StringUtils.isBlank(this.tagScope)) {
            return TagScopeEnum.ENABLE.getAllow();
        }
        String[] split = this.getTagScope().split(Constants.SPLIT);
        if (split.length == 2 && split[1].equals("2")) {
            return TagScopeEnum.ENABLE.getRefuse();
        }
        return TagScopeEnum.ENABLE.getAllow();
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GroupBuyDiscount {

        /**
         * 折扣标题
         */
        private String discountName;

        /**
         * 折扣描述
         */
        private String discountDesc;

        /**
         * 折扣类型（0:base、1:tag）
         */
        private DiscountTypeEnum discountType;

        /**
         * 营销优惠计划（ZJ:直减、MJ:满减、N元购）
         */
        private String marketPlan;

        /**
         * 营销优惠表达式
         */
        private String marketExpr;

        /**
         * 人群标签，特定优惠限定
         */
        private String tagId;
    }

}
