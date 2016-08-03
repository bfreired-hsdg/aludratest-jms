/*
 * Copyright (C) 2015 Hamburg Sud and the contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.aludratest.service.jms;

import java.io.Serializable;

import javax.jms.Queue;

import org.aludratest.service.jms.data.TextMessageData;
import org.databene.commons.Validator;

/**
 * Receives messages from a JMS {@link Queue}.
 * @author Volker Bergmann
 */

public abstract class QueueReceiver<E extends QueueReceiver<E>> extends AbstractJmsReceiver<E> {

	public QueueReceiver(String destinationName, JmsService service) {
		super(destinationName, service);
	}

	public E receiveTextMessage(String messageSelector, long timeout, TextMessageData result) {
		String messageText = service.perform().receiveTextMessageFromQueue(destinationName, messageSelector, timeout);
		result.setMessageText(messageText);
		return verifyState();
	}

	public E receiveTextMessageAndValidate(String messageSelector, long timeout, Validator<String> validator) {
		service.perform().receiveTextMessageFromQueue(destinationName, messageSelector, timeout);
		return verifyState();
	}

	public E receiveObjectMessage(String messageSelector, long timeout) {
		service.perform().receiveTextMessageFromQueue(destinationName, messageSelector, timeout);
		return verifyState();
	}

	public E receiveObjectMessageAndValidate(String messageSelector, long timeout, Validator<Serializable> validator) {
		service.perform().receiveObjectMessageFromQueueAndValidate(destinationName, messageSelector, timeout, validator);
		return verifyState();
	}

}
