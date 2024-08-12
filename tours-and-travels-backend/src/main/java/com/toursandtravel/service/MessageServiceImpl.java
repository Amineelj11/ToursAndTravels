package com.toursandtravel.service;

import com.toursandtravel.dao.MessageDao;
import com.toursandtravel.dao.UserDao;
import com.toursandtravel.dto.ChatMessageDTO;
import com.toursandtravel.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageDao messageDao;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private UserDao userRepository;

    @Autowired
    private UserService userService;
    @Override
    public void saveAndBroadcastMessage(ChatMessageDTO messageDTO) {
        try {
            Message message = new Message();
            message.setSender(userService.getUserById(messageDTO.getSenderId()));
            message.setText(messageDTO.getText());
            messageDao.save(message);
            messagingTemplate.convertAndSend( "/topic/messages", message);

        }
        catch (Exception e){
            System.err.println("Error saveving"+e.getMessage());
            e.printStackTrace();
        }


    }

    public List<Message> findAllMessages() {
        return messageDao.findAll(); // Fetch all messages from the database
    }


}

