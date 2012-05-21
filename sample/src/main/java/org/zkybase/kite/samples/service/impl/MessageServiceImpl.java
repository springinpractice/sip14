/*
 * Copyright (c) 2010 the original author or authors.
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
package org.zkybase.kite.samples.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.zkybase.kite.GuardCallback;
import org.zkybase.kite.guard.CircuitBreakerTemplate;
import org.zkybase.kite.samples.model.Message;
import org.zkybase.kite.samples.service.MessageService;
import org.zkybase.kite.samples.util.Flakinator;

/**
 * @author Willie Wheeler (willie.wheeler@gmail.com)
 * @since 1.0
 */
@Service
public class MessageServiceImpl implements MessageService {
	@Inject private CircuitBreakerTemplate breaker;
	
	private Flakinator flakinator = new Flakinator();

	/* (non-Javadoc)
	 * @see org.zkybase.kite.samples.service.MessageService#getMotd()
	 */
	@Override
	public Message getMotd() {
		try {
			return breaker.execute(new GuardCallback<Message>() {
				
				/* (non-Javadoc)
				 * @see org.zkybase.kite.GuardCallback#doInGuard()
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
	 * @see org.zkybase.kite.samples.service.MessageService#getImportantMessages()
	 */
	@Override
	public List<Message> getImportantMessages() {
		try {
			return breaker.execute(new GuardCallback<List<Message>>() {

				/* (non-Javadoc)
				 * @see org.zkybase.kite.GuardCallback#doInGuard()
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
