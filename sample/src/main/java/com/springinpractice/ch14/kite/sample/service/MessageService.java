package com.springinpractice.ch14.kite.sample.service;

import java.util.List;

import com.springinpractice.ch14.kite.sample.model.Message;


/**
 * @author Willie Wheeler (willie.wheeler@gmail.com)
 */
public interface MessageService {
	
	Message getMotd();
	
	List<Message> getImportantMessages();
}
