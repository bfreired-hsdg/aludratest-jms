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

import javax.jms.Topic;

import org.aludratest.exception.FunctionalFailure;
import org.aludratest.service.jms.data.ObjectMessageData;
import org.aludratest.service.jms.data.TextMessageData;
import org.databene.commons.Validator;

/**
 * Receives messages from a JMS {@link Topic}.
 * In order to use it, you need to derive a sub class, for example
 * <pre>
 * 		class MyTopicSubscriber extends TopicSubscriber&lt;MyTopicSubscriber&gt;
 * </pre>
 * then instantiate it, activate it by calling <code>start()</code> and e.g. call the 
 * {@link #receiveTextMessage(String, long, TextMessageData)} Method with a 
 * {@link TextMessageData} object provided to receive the content of the message:
 * <pre> 
 * 		TopicSubscriber subscriber = new TopicSubscriber(SUBSCRIPTION_NAME, TOPIC_NAME, true, service);
 * 		subscriber.start(null);
 * 		TextMessageData receivedMessage = new TextMessageData();
 * 		subscriber.receiveTextMessage(null, 1000, receivedMessage);
 * 		subscriber.stop();
 * </pre>
 * @param E when subclassing TopicSubscriber, use the subclass itself as generic class parameter, 
 * 			for example <code>class MyTopicSubscriber extends TopicSubscriber&lt;MyTopicSubscriber&gt;</code>
 * @author Volker Bergmann
 */

public abstract class TopicSubscriber<E extends TopicSubscriber<E>> extends AbstractJmsReceiver<E> {

	private String subscriptionName;
	private boolean durable;

	/** Full constructor.
	 *  @param subscriptionName a unique name to identify your subscription
	 *  @param topicName the name of the topic to receive messages from
	 *  @param durable a flag indicating if the subscription shall be durable
	 *  @param service the {@link JmsService} to use for accessing the topic */
	public TopicSubscriber(String subscriptionName, String topicName, boolean durable, JmsService service) {
		super(topicName, service);
		this.subscriptionName = subscriptionName;
		this.durable = durable;
	}

	/** Starts the subscription. */
	public final void start(String messageSelector) {
		service.perform().startSubscriber(subscriptionName, destinationName, messageSelector, durable);
	}

	/** Stops the subscription. */
	public final void stop() {
		service.perform().stopSubscriber(subscriptionName);
	}

	/** Receives a text message from the queue and puts its content into the result object provided as invocation parameter.
	 *  If no message is received within the timeout period, null is returned as message text.
	 *  @param messageSelector a String or null value for filtering messages as described in https://docs.oracle.com/cd/E19798-01/821-1841/bncer/index.html
	 *  @param timeout the timeout to apply in milliseconds
	 *  @param result a {@link TextMessageData} instance to be used for returning the text message content */
	public final E receiveTextMessage(String messageSelector, long timeout, TextMessageData result) {
		String messageText = service.perform().receiveTextMessageFromTopic(subscriptionName, messageSelector, timeout);
		result.setMessageText(messageText);
		return verifyState();
	}

	/** Receives a text message from the queue and validates it using the {@link Validator} object provided as parameter.
	 *  If no message is received within the timeout period, a null value is passed to the validator.
	 *  @exception FunctionalFailure if the message text is not valid
	 *  @param messageSelector a String or null value for filtering messages as described in https://docs.oracle.com/cd/E19798-01/821-1841/bncer/index.html
	 *  @param timeout the timeout to apply in milliseconds
	 *  @param validator a {@link Validator} object to be used for validating the message text */
	public final E receiveTextMessageAndValidate(String messageSelector, long timeout, Validator<String> validator) {
		service.perform().receiveTextMessageFromTopicAndValidate(subscriptionName, messageSelector, timeout, validator);
		return verifyState();
	}

	/** Receives an object message from the queue and puts its content into the result object provided as invocation parameter.
	 *  If no message is received within the timeout period, null is returned as message content object.
	 *  @param messageSelector a String or null value for filtering messages as described in https://docs.oracle.com/cd/E19798-01/821-1841/bncer/index.html
	 *  @param timeout the timeout to apply in milliseconds
	 *  @param result an {@link ObjectMessageData} instance to be used for returning the message content object */
	public final E receiveObjectMessage(String messageSelector, long timeout, ObjectMessageData result) {
		String object = service.perform().receiveTextMessageFromTopic(subscriptionName, messageSelector, timeout);
		result.setMessageObject(object);
		return verifyState();
	}

	/** Receives an object message from the queue and validates it using the {@link Validator} object provided as parameter.
	 *  If no message is received within the timeout period, a null value is passed to the validator.
	 *  @exception FunctionalFailure if the message object is not valid
	 *  @param messageSelector a String or null value for filtering messages as described in https://docs.oracle.com/cd/E19798-01/821-1841/bncer/index.html
	 *  @param timeout the timeout to apply in milliseconds
	 *  @param validator a {@link Validator} object to be used for validating the message object */
	public final E receiveObjectMessageAndValidate(String messageSelector, long timeout, Validator<Serializable> validator) {
		service.perform().receiveObjectMessageFromTopicAndValidate(subscriptionName, messageSelector, timeout, validator);
		return verifyState();
	}

}
