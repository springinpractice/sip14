/*
 * Copyright (c) 2010-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.springinpractice.ch14.kite.sample.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.springinpractice.ch14.kite.GuardCallback;
import com.springinpractice.ch14.kite.guard.CircuitBreakerTemplate;
import com.springinpractice.ch14.kite.sample.model.Message;
import com.springinpractice.ch14.kite.sample.service.MessageService;

/**
 * @author Willie Wheeler (willie.wheeler@gmail.com)
 * @since 1.0
 */
@Service
public class MessageServiceImpl implements MessageService {
	@Inject private CircuitBreakerTemplate breaker;
	@Inject private Flakinator flakinator;

	/* (non-Javadoc)
	 * @see com.springinpractice.ch14.kite.samples.service.MessageService#getMotd()
	 */
	@Override
	public Message getMotd() {
		try {
			return breaker.execute(new GuardCallback<Message>() {
				
				/* (non-Javadoc)
				 * @see com.springinpractice.ch14.kite.GuardCallback#doInGuard()
				 */
				@Override
				public Message doInGuard() throws Exception { return doGetMotd(); }
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private Message doGetMotd() {
		flakinator.simulateFlakiness();
		return createMessage("<p>Welcome to Aggro's Towne!</p>");
	}

	/* (non-Javadoc)
	 * @see com.springinpractice.ch14.kite.samples.service.MessageService#getImportantMessages()
	 */
	@Override
	public List<Message> getImportantMessages() {
		try {
			return breaker.execute(new GuardCallback<List<Message>>() {

				/* (non-Javadoc)
				 * @see com.springinpractice.ch14.kite.GuardCallback#doInGuard()
				 */
				@Override
				public List<Message> doInGuard() throws Exception { return doGetImportantMessages(); }
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private List<Message> doGetImportantMessages() {
		flakinator.simulateFlakiness();
		List<Message> messages = new ArrayList<Message>();
		messages.add(createMessage("<p>Important message 1</p>"));
		messages.add(createMessage("<p>Important message 2</p>"));
		messages.add(createMessage("<p>Important message 3</p>"));
		return messages;
	}
	
	private Message createMessage(String htmlText) {
		Message message = new Message();
		message.setHtmlText(htmlText);
		return message;
	}
	
}
