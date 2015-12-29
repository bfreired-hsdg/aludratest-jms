package org.aludratest.service.jms.impl;

import java.io.Serializable;
import java.util.List;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.aludratest.exception.AccessFailure;
import org.aludratest.exception.AutomationException;
import org.aludratest.exception.FunctionalFailure;
import org.aludratest.exception.PerformanceFailure;
import org.aludratest.exception.TechnicalException;
import org.aludratest.service.SystemConnector;
import org.aludratest.service.jms.JmsCondition;
import org.aludratest.service.jms.JmsInteraction;
import org.aludratest.service.jms.JmsVerification;
import org.aludratest.testcase.event.attachment.Attachment;

public class JmsActionImpl implements JmsInteraction, JmsCondition, JmsVerification {

	private Connection connection;

	private InitialContext context;

	private Session session;

	public JmsActionImpl(Connection connection, InitialContext context) {
		this.connection = connection;
		this.context = context;
	}

	public void close() {
		if (session != null) {
			try {
				session.close();
			}
			catch (JMSException e) {
			}
		}
	}

	@Override
	public List<Attachment> createDebugAttachments() {
		return null;
	}

	@Override
	public List<Attachment> createAttachments(Object object, String title) {
		return null;
	}

	@Override
	public void setSystemConnector(SystemConnector systemConnector) {
	}

	@Override
	public void assertDestinationAvailable(String destinationName) {
		if (!isDestinationAvailable(destinationName)) {
			throw new FunctionalFailure("Destination " + destinationName + " is not available");
		}
	}

	@Override
	public boolean isDestinationAvailable(String destinationName) {
		try {
			Object o = context.lookup(destinationName);
			return (o instanceof Destination);
		}
		catch (NamingException e) {
			return false;
		}
	}

	@Override
	public void sendTextMessage(String text, String destinationName) {
		TextMessage msg = createTextMessage();
		try {
			msg.setText(text);
		}
		catch (JMSException e) {
			throw new TechnicalException("Could not set text of text message", e);
		}
		sendMessage(msg, destinationName);
	}

	@Override
	public void sendObjectMessage(Serializable object, String destinationName) {
		ObjectMessage msg = createObjectMessage();
		try {
			msg.setObject(object);
		}
		catch (JMSException e) {
			throw new TechnicalException("Could not set object of object message", e);
		}
		sendMessage(msg, destinationName);
	}

	@Override
	public TextMessage createTextMessage() {
		try {
			return getSession().createTextMessage();
		}
		catch (JMSException e) {
			throw new AccessFailure("Could not create text message", e);
		}
	}

	@Override
	public ObjectMessage createObjectMessage() {
		try {
			return getSession().createObjectMessage();
		}
		catch (JMSException e) {
			throw new AccessFailure("Could not create object message", e);
		}
	}

	@Override
	public BytesMessage createBytesMessage() {
		try {
			return getSession().createBytesMessage();
		}
		catch (JMSException e) {
			throw new AccessFailure("Could not create bytes message", e);
		}
	}

	@Override
	public MapMessage createMapMessage() {
		try {
			return getSession().createMapMessage();
		}
		catch (JMSException e) {
			throw new AccessFailure("Could not create map message", e);
		}
	}

	@Override
	public StreamMessage createStreamMessage() {
		try {
			return getSession().createStreamMessage();
		}
		catch (JMSException e) {
			throw new AccessFailure("Could not create stream message", e);
		}
	}

	@Override
	public void sendMessage(Message message, String destinationName) {
		MessageProducer producer = null;
		try {
			Destination dest = (Destination) context.lookup(destinationName);
			producer = getSession().createProducer(dest);
			connection.start();
			producer.send(message);
			connection.stop();
		}
		catch (NamingException e) {
			throw new AutomationException("Could not lookup destination " + destinationName, e);
		}
		catch (ClassCastException e) {
			throw new AutomationException("JNDI object with name " + destinationName + " is no destination", e);
		}
		catch (JMSException e) {
			throw new AccessFailure("Could not send JMS message", e);
		}
		finally {
			if (producer != null) {
				try {
					producer.close();
				}
				catch (JMSException e) {
				}
			}
		}
	}

	@Override
	public Message receiveMessage(String destinationName, long timeout) {
		MessageConsumer consumer = null;
		try {
			Destination dest = (Destination) context.lookup(destinationName);
			consumer = getSession().createConsumer(dest);
			connection.start();
			Message msg = consumer.receive(timeout);
			connection.stop();
			if (msg == null) {
				throw new PerformanceFailure("Destination " + destinationName + " did not deliver a message within timeout");
			}
			return msg;
		}
		catch (NamingException e) {
			throw new AutomationException("Could not lookup destination " + destinationName, e);
		}
		catch (ClassCastException e) {
			throw new AutomationException("JNDI object with name " + destinationName + " is no destination", e);
		}
		catch (JMSException e) {
			throw new AccessFailure("Could not receive JMS message", e);
		}
		finally {
			if (consumer != null) {
				try {
					consumer.close();
				}
				catch (JMSException e) {
				}
			}
		}
	}

	private Session getSession() throws JMSException {
		if (session == null) {
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		}
		return session;
	}
}
