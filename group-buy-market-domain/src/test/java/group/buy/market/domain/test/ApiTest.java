package group.buy.market.domain.test;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ApiTest {
    public static void main(String[] args) {
        // 可使用字符串和整数构造
        // 不推荐使用double构造
        BigDecimal price1 = new BigDecimal(12);
        BigDecimal price2 = new BigDecimal("3");
        // 转换成其他数值类型
        double doublePrice1 = price1.doubleValue();
        int intPrice2 = price2.intValue();

        // 加法
        BigDecimal addResult = price1.add(price2);
        // 减法
        BigDecimal subtractResult = price1.subtract(price2);
        // 乘法
        BigDecimal multiplyResult = price1.multiply(price2);
        // 除法
        // divide() 方法需要指定舍入模式，因为除法有可能导致无限循环小数。常用的舍入模式有：
        // RoundingMode.HALF_UP（四舍五入）
        // RoundingMode.HALF_DOWN（五舍六入）
        // RoundingMode.DOWN（直接舍去多余的小数）
        BigDecimal divideResult = price1.divide(price2, RoundingMode.HALF_UP);

        // 比较大小
        // 大于返回 -1, 等于返回 0, 小于返回 1
        int result = price1.compareTo(price2);
        // 是否相等
        boolean equals = price1.equals(price2);

        // 输出结果
        System.out.println("price1: " + price1);
        System.out.println("price2: " + price2);
        System.out.println("doublePrice1: " + doublePrice1);
        System.out.println("intPrice2: " + intPrice2);

        System.out.println("price1 + price2 = " + addResult);  // 加法结果
        System.out.println("price1 - price2 = " + subtractResult);  // 减法结果
        System.out.println("price1 * price2 = " + multiplyResult);  // 乘法结果
        System.out.println("price1 / price2 = " + divideResult);  // 除法结果

        // 比较大小结果
        if (result > 0) {
            System.out.println("price1 > price2");
        } else if (result < 0) {
            System.out.println("price1 < price2");
        } else {
            System.out.println("price1 == price2");
        }
        // 是否相等
        System.out.println("price1 equals price2: " + equals);
    }
}
