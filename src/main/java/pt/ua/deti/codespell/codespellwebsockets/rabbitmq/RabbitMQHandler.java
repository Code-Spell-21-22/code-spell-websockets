package pt.ua.deti.codespell.codespellwebsockets.rabbitmq;

import lombok.Getter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Getter
public class RabbitMQHandler {

    private final String topicExchangeName = "code-spell-launcher-exchange";

    private final String codeReportsQueue = "code-spell-code-exec-reports";

    private final String routingKey = "code_spell.launcher.#";

    @Bean
    Queue codeExecReportsQueue() {
        return new Queue(codeReportsQueue, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding bindingReceiverQueue(Queue codeExecReportsQueue, TopicExchange exchange) {
        return BindingBuilder.bind(codeExecReportsQueue).to(exchange).with(getRoutingKeyWithTopic("reports"));
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(codeReportsQueue);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(RabbitMQReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    public String getRoutingKeyWithTopic(String topic) {
        return routingKey.replace("#", topic);
    }

}
