package group.buy.market.types.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)     // 指定注解的生命周期 - 在运行时仍然可用
@Target({ElementType.FIELD})    // 注解的适用范围 - 成员变量、对象、属性（包括 enum 实例）
@Documented     // 注解将包含在 Javadoc 文档中
public @interface DCCValue {

    // 将 value 的值应用到引入该注解的字段上，默认为""
    String value() default "";
}
