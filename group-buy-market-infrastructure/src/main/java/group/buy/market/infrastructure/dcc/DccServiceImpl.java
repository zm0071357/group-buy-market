package group.buy.market.infrastructure.dcc;

import group.buy.market.types.annotations.DCCValue;
import org.springframework.stereotype.Service;

@Service
public class DccServiceImpl {

    // 降级开关 0关闭 1开启
    @DCCValue(value = "downgradeSwitch:0")
    private String downgradeSwitch;

    // 切量
    @DCCValue(value = "cutRange:100")
    private String cutRange;

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
        // 获取用户ID的哈希码绝对值
        int hashCode = Math.abs(userId.hashCode());
        // 取最后两位
        int lastTwoDigits = hashCode % 100;
        if (lastTwoDigits <= Integer.parseInt(cutRange)) {
            return true;
        }
        return false;
    }

}
