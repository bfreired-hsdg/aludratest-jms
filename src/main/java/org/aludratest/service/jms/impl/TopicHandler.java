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

package org.aludratest.service.jms.impl;

import java.io.Closeable;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TopicSubscriber;

import org.aludratest.exception.AccessFailure;
import org.aludratest.exception.AutomationException;

/**
 * Encapsulates access to {@link TopicSubscriber} and underlying {@link Connection}.
 * @author Volker Bergmann
 */

public class TopicHandler implements Closeable {
	
	private TopicSubscriber subscriber;
	private Connection connection;
	private String subscriptionName;
	boolean durable;
	
	public TopicHandler(String subscriptionName, boolean durable, TopicSubscriber subscriber, Connection connection) {
		this.subscriptionName = subscriptionName;
		this.durable = durable;
		this.subscriber = subscriber;
		this.connection = connection;
	}
	
	public boolean isDurable() {
		return durable;
	}

	public Message receive() {
		try {
			return subscriber.receive();
		} catch (JMSException e) {
			throw new AccessFailure("Could not receive JMS message", e);
		}
	}

	public Message receive(long timeout) {
		try {
			return subscriber.receive(timeout);
		} catch (JMSException e) {
			throw new AccessFailure("Could not receive JMS message", e);
		}
	}

	public void start() {
		try {
			this.connection.start();
		} catch (JMSException e) {
			throw new AutomationException("Starting connection failed", e);
		}
	}

	public void stop() {
		try {
			this.connection.stop();
		} catch (JMSException e) {
			throw new AutomationException("Stopping connection failed for " + toString(), e);
		}
	}
	
	@Override
	public void close() {
		try {
			subscriber.close();
		} catch (JMSException e) {
			throw new AutomationException("Failed to close the subscriber for " + toString(), e);
		}
		try {
			connection.close();
		} catch (JMSException e) {
			throw new AutomationException("Failed to close the connection for " + toString(), e);
		}
	}
	
	@Override
	public String toString() {
		return "dynamic connection [ " + subscriptionName + " ]";
	}
	
}
