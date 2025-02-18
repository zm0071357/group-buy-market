package group.buy.market.config;

import group.buy.market.types.annotations.DCCValue;
import group.buy.market.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 动态配置中心
 */
@Slf4j
@Configuration
public class DCCValueBeanFactory implements BeanPostProcessor {

    private static final String BASE_CONFIG_PATH = "group_buy_market_dcc_";

    private final RedissonClient redissonClient;

    private final Map<String, Object> dccObjectMap = new HashMap<>();

    public DCCValueBeanFactory(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Bean("dccTopic")
    public RTopic dccTopicListener(RedissonClient redissonClient) {
        // 创建主题
        RTopic topic = redissonClient.getTopic("group_buy_market_dcc");
        // 添加监听器
        topic.addListener(String.class, new MessageListener<String>() {
            @Override
            public void onMessage(CharSequence charSequence, String s) {
                // 监听格式 dccTopic.publish(key + "," + value);
                // 分别获取 key 和 value
                String[] split = s.split(Constants.SPLIT);
                String key = split[0];
                String redisKey = BASE_CONFIG_PATH + key;
                String value = split[1];

                // 设置值
                RBucket<Object> bucket = redissonClient.getBucket(redisKey);
                if (!bucket.isExists()) {
                    return;
                }
                bucket.set(value);

                // 获取 Bean 的类对象
                Object objBean = dccObjectMap.get(key);
                if (objBean == null) {
                    return;
                }
                // 获取目标类
                Class<?> objBeanClass = objBean.getClass();
                // 判断是否为 AOP 代理对象
                if (AopUtils.isAopProxy(objBeanClass)) {
                    // 获取代理对象的目标对象
                    objBeanClass = AopUtils.getTargetClass(objBean);
                }

                // 更新值
                try {
                    // getDeclaredField 方法用于获取指定类中声明的所有字段，包括私有字段、受保护字段和公共字段。
                    // getField 方法用于获取指定类中的公共字段，即只能获取到公共访问修饰符（public）的字段。
                    Field field = objBeanClass.getDeclaredField(key);
                    field.setAccessible(true);
                    field.set(objBean, value);
                    field.setAccessible(false);
                    log.info("DCC 节点监听，动态设置值 {} {}", key, value);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return topic;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 获取目标类和目标对象
        Class<?> targetBeanClass = bean.getClass();
        Object targetBeanObject = bean;
        // 判断是否为 AOP 代理对象
        if (AopUtils.isAopProxy(bean)) {
            // 获取代理对象的目标类和目标对象
            targetBeanClass = AopUtils.getTargetClass(bean);
            targetBeanObject = AopProxyUtils.getSingletonTarget(bean);
        }

        // 获取字段集合
        Field[] fields = targetBeanClass.getDeclaredFields();
        // 获取有 @DCCValue 注解的字段
        for (Field field : fields) {
            if (!field.isAnnotationPresent(DCCValue.class)) {
                continue;
            }
            // 获取注解的值
            DCCValue dccValue = field.getAnnotation(DCCValue.class);
            String value = dccValue.value();
            // 默认空值 抛异常
            if (StringUtils.isBlank(value)) {
                throw new RuntimeException("field.getName() + \" @DCCValue is not config value config case 「isSwitch/isSwitch:1」");
            }
            // 注解格式 @DCCValue(value = "downgradeSwitch:0")
            // 分别获取 key 和 value
            String[] split = value.split(":");
            String key = BASE_CONFIG_PATH.concat(split[0]);
            String defaultValue = split.length == 2 ? split[1] : null;
            // 设置值
            String setValue = defaultValue;

            try {
                // 没有配置值 抛异常
                if (StringUtils.isBlank(setValue)) {
                    throw new RuntimeException("dcc config error \" + key + \" is not null - 请配置默认值！");
                }
                // 从缓存中获取配置
                RBucket<String> bucket = redissonClient.getBucket(key);
                // 配置不存在 首次配置，将配置写入缓存
                if (!bucket.isExists()) {
                    bucket.set(defaultValue);
                    // 配置存在 获取最新值
                } else {
                    setValue = bucket.get();
                }

                // 设置 field 可访问后通过反射将配置注入到字段中，最后将可访问性恢复
                field.setAccessible(true);
                field.set(targetBeanObject, setValue);
                field.setAccessible(false);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            dccObjectMap.put(key, targetBeanObject);
        }
        return bean;
    }
}
