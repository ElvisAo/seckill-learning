# Rabbitmq

## 快速上手使用

1. 引入依赖

   ```java
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-amqp</artifactId>
   </dependency>
   ```

2. 配置

   ```properties
   # rabbitmq
   spring.rabbitmq.host=localhost
   spring.rabbitmq.port=5672
   spring.rabbitmq.username=admin
   spring.rabbitmq.password=admin
   spring.rabbitmq.virtual-host=/
   spring.rabbitmq.listener.simple.concurrency=10
   spring.rabbitmq.listener.simple.max-concurrency=10
   # 每次从队列中取几个
   spring.rabbitmq.listener.simple.prefetch=1
   spring.rabbitmq.listener.simple.auto-startup=true
   # 消费失败后重新把数据放入队列
   spring.rabbitmq.listener.simple.default-requeue-rejected=true
   # 重试
   spring.rabbitmq.listener.simple.retry.enabled=true
   spring.rabbitmq.listener.simple.retry.initial-interval=1000
   spring.rabbitmq.listener.simple.retry.max-attempts=3
   spring.rabbitmq.listener.direct.retry.multiplier=1
   ```

   ```java
   @Configuration
   public class MQConfig {
       public static final String QUEUE = "queue";
   
       @Bean
       public Queue queue() {
           // 队列名称及持久化
           return new Queue(QUEUE, true);
       }
   }
   ```

   

3. 使用

   1. 发送方

      ```java
      @Slf4j
      @Service
      public class MQSender {
          @Autowired
          AmqpTemplate amqpTemplate;
      
          public void send(Object msg) {
              String s = RedisService.serializeObject(msg);
              amqpTemplate.convertAndSend(MQConfig.QUEUE, s);
              log.info("send message:" + s);
          }
      }
      ```

   2. 接收方

      ```java
      @Slf4j
      @Service
      public class MQReceiver {
      
          @RabbitListener(queues = {MQConfig.QUEUE})
          public void receive(String msg) {
              log.info("receive message:" + msg);
          }
      }
      ```

## 四种模式

**direct模式**

[快速上手](#快速上手)中只是最简单的Direct模式



**交换机Exchange**

- 发数据的过程：先发数据到Exchange（交换机），再由交换机路由到消息队列
- 需要在配置中将队列和交换机以某个routinekey绑定
- 接收的时候还是指定队列
- 发送的时候指定交换机以及routinkey、消息即可



**fanout模式（广播模式）**

- 发数据时指定routingkey为“”
- 其他基本同交换机模式



**headers模式**

- 只有有满足条件的header和value时才接收，所以在发送的时候消息少稍微用Message和MessageProperties处理下，然后发送的一般是byte[]
- 接收的时候由于是byte[]，所以也要对消息稍微处理下进行还原
