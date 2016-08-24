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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import org.aludratest.service.jms.data.TextMessageData;
import org.aludratest.testcase.TestStatus;
import org.aludratest.testcase.event.TestStepInfo;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the JMS AWL.
 * @author Volker Bergmann
 */

public class JmsAwlTest extends AbstractJmsTest {

    private static final Logger LOGGER = Logger.getLogger(JmsAwlTest.class);
    
    private static final String QUEUE_NAME = "dynamicQueues/testQueue1";
    
    private static final String TOPIC_NAME = "dynamicTopics/testTopic1";

	private static final String SUBSCRIPTION_NAME = "theSubscriptionName";

    @Test
    public void testQueue() {
        String textContent = UUID.randomUUID().toString();

        LOGGER.info("Begin testQueue");
        MyJmsSender sender = new MyJmsSender(QUEUE_NAME, service);

        LOGGER.info("Sending TextMessage to queue " + QUEUE_NAME);
        sender.sendTextMessage(new TextMessageData(textContent));

        LOGGER.info("Receiving TextMessage from queue " + QUEUE_NAME);
        MyQueueReceiver receiver = new MyQueueReceiver(QUEUE_NAME, service);
        TextMessageData receivedMessage = new TextMessageData();
        receiver.receiveTextMessage(null, 100, receivedMessage);

        Assert.assertEquals(textContent, receivedMessage.getMessageText());
        LOGGER.info("End testQueue");
    }

    @Test
    public void testTopic() {
        final String textContent = UUID.randomUUID().toString();

        LOGGER.info("Starting receiver");
        MyTopicSubscriber receiver = new MyTopicSubscriber(SUBSCRIPTION_NAME, TOPIC_NAME, true, service);
        receiver.start(null);

        LOGGER.info("Sending TextMessage to topic " + TOPIC_NAME);
        MyJmsSender sender = new MyJmsSender(TOPIC_NAME, service);
        sender.sendTextMessage(new TextMessageData(textContent));
        
        LOGGER.info("Receiving TextMessage from topic " + TOPIC_NAME);
        TextMessageData receivedMessage = new TextMessageData();
        receiver.receiveTextMessage(null, 100, true, receivedMessage);
        receiver.stop();

        Assert.assertEquals(textContent, receivedMessage.getMessageText());

        LOGGER.info("End testTopic");
    }

    @Test
    public void testRequiredTopicMessageSuccess() {
        final String textContent = UUID.randomUUID().toString();

        LOGGER.info("Starting receiver");
        MyTopicSubscriber receiver = new MyTopicSubscriber(SUBSCRIPTION_NAME, TOPIC_NAME, false, service);
        receiver.start(null);

        new Thread() {
        	@Override
			public void run() {
                LOGGER.info("Sending TextMessage to topic " + TOPIC_NAME);
                MyJmsSender sender = new MyJmsSender(TOPIC_NAME, service);
                sender.sendTextMessage(new TextMessageData(textContent));
        	};
        }.start();
        
        LOGGER.info("Receiving TextMessage from topic " + TOPIC_NAME);
        TextMessageData receivedMessage = new TextMessageData();
        receiver.receiveTextMessage(null, 1000, true, receivedMessage);
        receiver.stop();

        Assert.assertEquals(textContent, receivedMessage.getMessageText());

        LOGGER.info("End testTopic");
    }

    @Test
    public void testRequiredTopicMessageFailure() {
        MyTopicSubscriber receiver = null;
    	try {
	        receiver = new MyTopicSubscriber(SUBSCRIPTION_NAME, TOPIC_NAME, false, service);
	        receiver.start(null);
	        TextMessageData receivedMessage = new TextMessageData();
	        receiver.receiveTextMessage(null, 100, true, receivedMessage);
	        TestStepInfo lastFailedTestStep = getLastFailedTestStep();
	        assertNotNull(lastFailedTestStep);
			assertEquals(TestStatus.FAILEDPERFORMANCE, lastFailedTestStep.getTestStatus());
    	} finally {
    		if (receiver != null) {
    			receiver.stop();
    		}
    	}
    }

    @Test
    public void testNonrequiredTopicMessageFailure() {
        final String textContent = UUID.randomUUID().toString();
        MyTopicSubscriber receiver = new MyTopicSubscriber(SUBSCRIPTION_NAME, TOPIC_NAME, false, service);
        receiver.start(null);
        TextMessageData receivedMessage = new TextMessageData();
        receiver.receiveTextMessage(null, 100, false, receivedMessage);
        receiver.stop();
        Assert.assertNull(textContent, receivedMessage.getMessageText());
    }

}
