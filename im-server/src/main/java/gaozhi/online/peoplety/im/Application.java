package gaozhi.online.peoplety.im;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author http://gaozhi.online
 * @version 1.0
 * @description: TODO
 * @date 2022/9/11 15:35
 */
@Slf4j
@EnableEurekaClient
@SpringBootApplication(exclude = {
//      排除数据源
        DataSourceAutoConfiguration.class,
        DruidDataSourceAutoConfigure.class,
//        禁止mongodb自动装配
        MongoAutoConfiguration.class,
//        禁止redis自动装配
//        RedisAutoConfiguration.class,
//        RedisRepositoriesAutoConfiguration.class,
//        禁止rabbitmq自动装配
//        RabbitAutoConfiguration.class
})
@ComponentScan(basePackageClasses = {gaozhi.online.base.ScanClass.class, gaozhi.online.peoplety.ScanClass.class, Application.class})
public class Application implements SpringApplicationRunListener {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        log.info("启动成功：{}", context.getApplicationName());
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        log.error("启动失败：{}", context.getApplicationName());
    }
}
