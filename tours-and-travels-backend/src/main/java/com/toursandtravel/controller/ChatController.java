package com.toursandtravel.controller;

import com.toursandtravel.dto.ChatMessageDTO;
import com.toursandtravel.entity.Message;
import com.toursandtravel.service.MessageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller

public class ChatController {

    @Autowired
    private MessageServiceImpl chatService;

    @MessageMapping("/chat")
    public void send(ChatMessageDTO message) {

        System.out.println(message.getText()+ "heloo");

        chatService.saveAndBroadcastMessage(message);
    }

    @GetMapping("/api/messages")
    public ResponseEntity<List<com.toursandtravel.entity.Message>> getAllMessages() {
        List<Message> messages = chatService.findAllMessages();
        return ResponseEntity.ok(messages);
    }
}
