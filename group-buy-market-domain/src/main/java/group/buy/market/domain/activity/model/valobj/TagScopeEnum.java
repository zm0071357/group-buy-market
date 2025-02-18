package group.buy.market.domain.activity.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TagScopeEnum {

    VISIBLE(true, false, "是否可看见拼团活动"),
    ENABLE(true, false, "是否可参加拼团活动"),
    ;

    private Boolean allow;

    private Boolean refuse;

    private String desc;

}
