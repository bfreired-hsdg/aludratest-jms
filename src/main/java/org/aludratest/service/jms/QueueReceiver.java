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

import org.aludratest.exception.FunctionalFailure;
import org.aludratest.exception.PerformanceFailure;
import org.aludratest.service.jms.data.ObjectMessageData;
import org.aludratest.service.jms.data.TextMessageData;
import org.databene.commons.Validator;

/**
 * Receives messages from a JMS {@link Queue}.
 * In order to use it, you need to derive a sub class, for example
 * <pre>
 * 		class MyTopicSubscriber extends TopicSubscriber&lt;MyTopicSubscriber&gt;
 * </pre>
 * then instantiate it and e.g. call the {@link #receiveTextMessage(String, long, TextMessageData)} 
 * Method with a {@link TextMessageData} object provided to receive the content of the message:
 * <pre> 
 * 		MyQueueReceiver receiver = new MyQueueReceiver(QUEUE_NAME, service);
 * 		TextMessageData receivedMessage = new TextMessageData();
 * 		receiver.receiveTextMessage(null, 1000, receivedMessage);
 * </pre>
 * @param E when subclassing QueueReceiver, use the subclass itself as generic class parameter, 
 * 			for example <code>class MyJmsReceiver extends JmsReceiver&lt;MyJmsReceiver&gt;</code>
 * @author Volker Bergmann
 */

public abstract class QueueReceiver<E extends QueueReceiver<E>> extends AbstractJmsReceiver<E> {

	/** Full constructor.
	 *  @param queueName the name of the queue to receive messages from
	 *  @param service the {@link JmsService} to use for accessing the queue */
	public QueueReceiver(String queueName, JmsService service) {
		super(queueName, service);
	}

	/** Receives a text message from the queue and puts its content into the result object provided as invocation parameter.
	 *  @exception PerformanceFailure if no message is received within the timeout period.
	 *  @param messageSelector a String or null value for filtering messages as described in https://docs.oracle.com/cd/E19798-01/821-1841/bncer/index.html
	 *  @param timeout the timeout to apply in milliseconds
	 *  @param result a {@link TextMessageData} instance to be used for returning the text message content */
	public final E receiveTextMessage(String messageSelector, long timeout, TextMessageData result) {
		String messageText = service.perform().receiveTextMessageFromQueue(destinationName, messageSelector, timeout);
		result.setMessageText(messageText);
		return verifyState();
	}

	/** Receives a text message from the queue and validates it using the {@link Validator} object provided as parameter.
	 *  @exception PerformanceFailure if no message is received within the timeout period.
	 *  @exception FunctionalFailure if the message text is not valid
	 *  @param messageSelector a String or null value for filtering messages as described in https://docs.oracle.com/cd/E19798-01/821-1841/bncer/index.html
	 *  @param timeout the timeout to apply in milliseconds
	 *  @param validator a {@link Validator} object to be used for validating the message text */
	public final E receiveTextMessageAndValidate(String messageSelector, long timeout, Validator<String> validator) {
		service.perform().receiveTextMessageFromQueue(destinationName, messageSelector, timeout);
		return verifyState();
	}

	/** Receives an object message from the queue and puts its content into the result object provided as invocation parameter.
	 *  @exception PerformanceFailure if no message is received within the timeout period.
	 *  @param messageSelector a String or null value for filtering messages as described in https://docs.oracle.com/cd/E19798-01/821-1841/bncer/index.html
	 *  @param timeout the timeout to apply in milliseconds
	 *  @param result an {@link ObjectMessageData} instance to be used for returning the message content object */
	public final E receiveObjectMessage(String messageSelector, long timeout, ObjectMessageData result) {
		Serializable object = service.perform().receiveObjectMessageFromQueue(destinationName, messageSelector, timeout);
		result.setMessageObject(object);
		return verifyState();
	}

	/** Receives an object message from the queue and validates it using the {@link Validator} object provided as parameter.
	 *  @exception PerformanceFailure if no message is received within the timeout period.
	 *  @exception FunctionalFailure if the message object is not valid
	 *  @param messageSelector a String or null value for filtering messages as described in https://docs.oracle.com/cd/E19798-01/821-1841/bncer/index.html
	 *  @param timeout the timeout to apply in milliseconds
	 *  @param validator a {@link Validator} object to be used for validating the message object */
	public final E receiveObjectMessageAndValidate(String messageSelector, long timeout, Validator<Serializable> validator) {
		service.perform().receiveObjectMessageFromQueueAndValidate(destinationName, messageSelector, timeout, validator);
		return verifyState();
	}

}
