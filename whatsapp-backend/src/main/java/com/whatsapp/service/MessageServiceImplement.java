package com.whatsapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whatsapp.exception.ChatException;
import com.whatsapp.exception.MessageException;
import com.whatsapp.exception.UserException;
import com.whatsapp.modal.Chat;
import com.whatsapp.modal.Message;
import com.whatsapp.modal.User;
import com.whatsapp.repository.MessageRepository;
import com.whatsapp.request.SendMessageRequest;

@Service
public class MessageServiceImplement implements MessageService {

	@Autowired
	private MessageRepository messageRepo;

	@Autowired
	private UserService userService;

	@Autowired
	private ChatService chatService;

	@Override
	public Message sendMessage(SendMessageRequest req) throws UserException, ChatException {

		System.out.println("send message ------- ");

		User user = userService.findUserById(req.getUserId());
		Chat chat = chatService.findChatById(req.getChatId());

		Message message = new Message();
		message.setChat(chat);
		message.setUser(user);
		message.setContent(req.getContent());
		message.setTimeStamp(LocalDateTime.now());
		message.setIs_read(false);

		return messageRepo.save(message);
	}

	@Override
	public String deleteMessage(Integer messageId) throws MessageException {

		Message message = findMessageById(messageId);

		messageRepo.deleteById(message.getId());

		return "message deleted successfully";
	}

	@Override
	public List<Message> getChatsMessages(Integer chatId) throws ChatException {

//		Chat chat = chatService.findChatById(chatId);

		List<Message> messages = messageRepo.findMessageByChatId(chatId);

		return messages;
	}

	@Override
	public Message findMessageById(Integer messageId) throws MessageException {

		Optional<Message> message = messageRepo.findById(messageId);

		if (message.isPresent()) {
			return message.get();
		}
		throw new MessageException("message not exist with id " + messageId);
	}

}
