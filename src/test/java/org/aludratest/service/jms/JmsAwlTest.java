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

import java.util.UUID;

import org.aludratest.service.jms.data.TextMessageData;
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
        MyTopicReceiver receiver = new MyTopicReceiver(SUBSCRIPTION_NAME, TOPIC_NAME, true, service);
        receiver.start(null);

        LOGGER.info("Sending TextMessage to topic " + TOPIC_NAME);
        MyJmsSender sender = new MyJmsSender(TOPIC_NAME, service);
        sender.sendTextMessage(new TextMessageData(textContent));
        
        LOGGER.info("Receiving TextMessage from topic " + TOPIC_NAME);
        TextMessageData receivedMessage = new TextMessageData();
        receiver.receiveTextMessage(null, 100, receivedMessage);
        receiver.stop();

        Assert.assertEquals(textContent, receivedMessage.getMessageText());

        LOGGER.info("End testTopic");
    }

}
