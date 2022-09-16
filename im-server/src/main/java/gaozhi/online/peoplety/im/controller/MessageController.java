package gaozhi.online.peoplety.im.controller;

import gaozhi.online.peoplety.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author http://gaozhi.online
 * @version 1.0
 * @description: TODO  消息
 * @date 2022/9/16 12:17
 */
@Validated
@RestController
@RequestMapping("general/message")
@Slf4j
public class MessageController {

    /**
     * @return long
     * @description: 生成消息id
     * @author http://gaozhi.online
     * @date: 2022/9/16 12:20
     */
    @GetMapping("/get/msgId")
    public long generateID() {
        return Message.generateId();
    }
}
