package com.gnc.websocketspringmongo.chatroom;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    public Optional<String> getChatRoomId(String senderId, String recipientId,boolean createRoomIfNotExist){

        return chatRoomRepository.findBySenderIdAndRecipientId(senderId,recipientId).map(ChatRoom::getChatId).or(()->{
            if(createRoomIfNotExist){
                var chatId=createChatId(senderId,recipientId);
                return Optional.of(chatId);
            }
            return Optional.empty();
        });


    }

    private String createChatId(String senderId, String recipientId) {
        var chatId = String.format("%s-%s",senderId,recipientId);
        ChatRoom senderRecipient = ChatRoom.builder().
                chatId(chatId)
                        .recipientId(recipientId)
                                .senderId(senderId).
                build();
        ChatRoom recipientSender = ChatRoom.builder().
                chatId(chatId)
                .recipientId(senderId)
                .senderId(recipientId).
                build();
        chatRoomRepository.save(senderRecipient);
        chatRoomRepository.save(recipientSender);
        return chatId;




    }
}
