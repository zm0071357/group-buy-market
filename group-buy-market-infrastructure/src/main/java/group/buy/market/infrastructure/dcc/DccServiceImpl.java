package group.buy.market.infrastructure.dcc;

import group.buy.market.types.annotations.DCCValue;
import group.buy.market.types.common.Constants;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DccServiceImpl {

    // 降级
    @DCCValue(value = "downgradeSwitch:0")
    private String downgradeSwitch;

    // 切量
    @DCCValue(value = "cutRange:100")
    private String cutRange;

    // sc 黑名单
    @DCCValue(value = "scBlackList:s02c02")
    private String scBlackList;

    /**
     * 判断是否开启降级开关
     * 0 不开启
     * 1 开启
     * @return
     */
    public boolean isDowngradeSwitch() {
        return "1".equals(downgradeSwitch);
    }

    /**
     * 判断用户是否在切量范围内
     * @param userId
     * @return
     */
    public boolean isCutRange(String userId) {
        // 获取用户ID的哈希值
        int hashCode = Math.abs(userId.hashCode());
        // 取最后两位
        int lastTwoDigits = hashCode % 100;
        if (lastTwoDigits <= Integer.parseInt(cutRange)) {
            return true;
        }
        return false;
    }

    /**
     * 判断 sc 黑名单
     */
    public boolean isScBlackList(String source, String channel) {
        List<String> list = Arrays.asList(scBlackList.split(Constants.SPLIT));
        return list.contains(source + channel);
    }

}
