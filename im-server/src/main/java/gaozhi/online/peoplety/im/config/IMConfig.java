package gaozhi.online.peoplety.im.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author http://gaozhi.online
 * @version 1.0
 * @description: TODO  即时通信配置
 * @date 2022/9/11 19:49
 */
@Configuration
@Data
public class IMConfig{
    @Value("${imConfig.udpPort}")
    private int udpPort;
    @Value("${imConfig.tcpPort}")
    private int tcpPort;
    @Value("${imConfig.webSocketPort}")
    private int webSocketPort;
    @Value("${imConfig.loginURL}")
    private String loginURL;
    @Value("${imConfig.failedURL}")
    private String failedURL;
}
