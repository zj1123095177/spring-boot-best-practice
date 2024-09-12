package cn.javastack.springboot.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 1、解耦
 * 2、异步提速
 * 3、流量削峰
 * 4、提高系统可靠性
 */
@RequiredArgsConstructor
@RestController
@Slf4j
public class MsgController {

    private final RabbitTemplate rabbitTemplate;

    /**
     * 发送 Direct 模式消息
     * @param msg
     * @return
     */
    @RequestMapping("/send")
    public String sendMsg(@RequestParam("msg") String msg) {
        rabbitTemplate.convertAndSend("test-direct-exchange",
                "test-direct-routing-key", msg);
        return "已发送";
    }

    /**
     * 接收 Direct 模式消息
     * @param msg
     */
    @RabbitListener(queues = "test-direct-queue")
    public void receiveMsg(String msg) {
        log.info("收到 RabbitMQ 消息：{}", msg);
    }

}
