/*
 * (c) Copyright 2016 by Volker Bergmann. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, is permitted under the terms of the
 * GNU General Public License (GPL).
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * WITHOUT A WARRANTY OF ANY KIND. ALL EXPRESS OR IMPLIED CONDITIONS,
 * REPRESENTATIONS AND WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE
 * HEREBY EXCLUDED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.aludratest.service.jms;

import java.io.Serializable;

import javax.jms.Topic;

import org.aludratest.service.jms.data.TextMessageData;
import org.databene.commons.Validator;

/**
 * Receives messages from a JMS {@link Topic}.
 * @author Volker Bergmann
 */

public class TopicSubscriber<E extends TopicSubscriber<E>> extends AbstractJmsReceiver<E> {

	private String subscriptionName;
	private boolean durable;

	public TopicSubscriber(String subscriptionName, String destinationName, boolean durable, JmsService service) {
		super(destinationName, service);
		this.subscriptionName = subscriptionName;
		this.durable = durable;
	}

	public void start(String messageSelector) {
		service.perform().startSubscriber(subscriptionName, destinationName, messageSelector, durable);
	}

	public void stop() {
		service.perform().stopSubscriber(subscriptionName);
	}

	public E receiveTextMessage(String messageSelector, long timeout, TextMessageData result) {
		String messageText = service.perform().receiveTextMessageFromTopic(subscriptionName, messageSelector, timeout);
		result.setMessageText(messageText);
		return verifyState();
	}

	public E receiveTextMessageAndValidate(String messageSelector, long timeout, Validator<String> validator) {
		service.perform().receiveTextMessageFromTopicAndValidate(subscriptionName, messageSelector, timeout, validator);
		return verifyState();
	}

	public E receiveObjectMessage(String messageSelector, long timeout) {
		service.perform().receiveTextMessageFromTopic(subscriptionName, messageSelector, timeout);
		return verifyState();
	}

	public E receiveObjectMessageAndValidate(String messageSelector, long timeout, Validator<Serializable> validator) {
		service.perform().receiveObjectMessageFromTopicAndValidate(subscriptionName, messageSelector, timeout, validator);
		return verifyState();
	}

}
