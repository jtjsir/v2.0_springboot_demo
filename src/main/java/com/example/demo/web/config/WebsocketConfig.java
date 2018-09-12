package com.example.demo.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author nanco
 * -------------
 * websocket config
 * -------------
 * @create 2018/9/12
 **/
@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // register web socket contextPath and allow any origin
        registry.addEndpoint("/ws-demo").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // response destination prefix
        registry.enableSimpleBroker("/topic");
        // request destination prefix
        registry.setApplicationDestinationPrefixes("/app");
    }
}
