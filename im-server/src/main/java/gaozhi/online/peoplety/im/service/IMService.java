package gaozhi.online.peoplety.im.service;

import gaozhi.online.peoplety.im.service.im.ServerLauncherImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author http://gaozhi.online
 * @version 1.0
 * @description: TODO  即时通信服务
 * @date 2022/9/11 15:43
 */
@Service
@Slf4j
public class IMService implements InitializingBean {
    @Autowired
    private ServerLauncherImpl serverLauncher;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 启动MobileIMSDK服务端的Demo
        serverLauncher.startup();
        // 加一个钩子，确保在JVM退出时释放netty的资源
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            serverLauncher.shutdown();
            log.info("IMService shutdown...");
        }));
        log.info("IMService startup...");
    }
}
