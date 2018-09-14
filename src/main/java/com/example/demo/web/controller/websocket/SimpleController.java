package com.example.demo.web.controller.websocket;

import com.example.demo.web.model.websocket.ReqMessage;
import com.example.demo.web.model.websocket.ResMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author nanco
 * -------------
 * -------------
 * @create 2018/9/12
 **/
@Controller
public class SimpleController {

    @Resource
    private SimpMessagingTemplate messagingTemplate;

    /**
     * accept /app/halo request and response to /topic/halo(default) which subscribes
     *
     * @param reqMessage request message
     * @return @ResMessage
     */
    @MessageMapping("/halo")
    @SendTo("/topic/halo")
    public ResMessage halo(ReqMessage reqMessage) {
        ResMessage resMessage = new ResMessage();

        resMessage.setContent(String.format("Halo, %s !", reqMessage.getUsername()));

        return resMessage;
    }

    /**
     * use template to response the message
     *
     * @param reqMessage
     */
    @MessageMapping("/hello")
    public void hello(ReqMessage reqMessage) {
        ResMessage resMessage = new ResMessage();

        resMessage.setContent(String.format("Halo, %s !", reqMessage.getUsername()));

        messagingTemplate.convertAndSend("/topic/hello", resMessage);
    }

    /**
     * directly to response subscribe frame request
     *
     * @return
     */
    @SubscribeMapping("/subscribe")
    public String subscribe() {
        return "Subscribe OK";
    }
}
