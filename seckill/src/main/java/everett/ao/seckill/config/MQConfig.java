package everett.ao.seckill.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class MQConfig {

    public static final String SECKILL_QUEUE = "seckill.queue";


    @Bean
    public Queue seckillQueue(){
        return new Queue(SECKILL_QUEUE, true);
    }

    public static final String QUEUE = "queue";
    public static final String TOPIC_QUEUE1 = "topic.queue1";
    public static final String TOPIC_QUEUE2 = "topic.queue2";
    public static final String TOPIC_EXCHANGE = "topic.exchange";
    public static final String FANOUT_EXCHANGE = "fanout.exchange";
    // routing key似乎不能再使用“a.b.c”这种形式
    public static final String ROUTING_KEY1 = "key1";
    // 通配符：#代表多个单词
    public static final String ROUTING_KEY2 = "#";
    public static final String HEADERS_EXHCANGE = "headers.exchange";
    public static final String HEADERS_QUEUE = "headers.queue";

    @Bean
    public Queue queue() {
        // 队列名称及持久化
        return new Queue(QUEUE, true);
    }

    @Bean
    public HeadersExchange headersExchange() {
        // 队列名称及持久化
        return new HeadersExchange(HEADERS_EXHCANGE);
    }

    @Bean
    public Queue headersQueue() {
        // 队列名称及持久化
        return new Queue(HEADERS_QUEUE, true);
    }

    @Bean
    public Queue topicQueue1() {
        return new Queue(TOPIC_QUEUE1, true);
    }

    @Bean
    public Queue topicQueue2() {
        return new Queue(TOPIC_QUEUE2, true);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean
    public Binding fanoutBinding1() {
        return BindingBuilder.bind(topicQueue1()).to(fanoutExchange());
    }

    @Bean
    public Binding fanoutBinding2() {
        return BindingBuilder.bind(topicQueue2()).to(fanoutExchange());
    }


    @Bean
    public Binding topicBinding1() {
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with(ROUTING_KEY1);
    }

    @Bean
    public Binding topicBinding2() {
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with(ROUTING_KEY2);
    }

    @Bean
    public Binding headersBinding() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("header1", "value1");
        map.put("header2", "value2");
        // 只有所有的keyvalue满足后才放到这个里面
        return BindingBuilder.bind(headersQueue()).to(headersExchange()).whereAll(map).match();
    }
}
