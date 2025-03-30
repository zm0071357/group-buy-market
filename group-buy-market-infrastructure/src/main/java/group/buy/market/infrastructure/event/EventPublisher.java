package group.buy.market.infrastructure.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 发送信息
 */
@Slf4j
@Component
public class EventPublisher {

    @Value("${spring.rabbitmq.config.producer.exchange}")
    private String exchangeName;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publish(String routingKey, String message) {
        try {
            rabbitTemplate.convertAndSend(exchangeName, routingKey, message, m -> {
                m.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                return m;
            });
        } catch (Exception e) {
            log.error("发送MQ信息失败，message:{}", message, e);
            throw e;
        }
    }
}
