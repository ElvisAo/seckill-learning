# 主要内容

1. 根拒官方文档搭建Springboot项目
1. 集成Mybatis-Plus
1. 集成redis并做封装（基于Jedis）
    1. 自定义Redis连接池
    1. 设计key的前缀：每个模块都有自己的KeyPrefix类，取这个类的SimpleName并简单设计后作为key
    1. 实现redisService的基本功能
1. 封装返回结果R
    1. 枚举类封装code和msg
    1. R采用static method factory