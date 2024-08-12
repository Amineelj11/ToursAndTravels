package com.toursandtravel.service;

import com.toursandtravel.dto.ChatMessageDTO;
import com.toursandtravel.entity.Message;

import java.util.List;

public interface MessageService {
    void saveAndBroadcastMessage(ChatMessageDTO message);
     List<Message> findAllMessages();
}
