package org.aludratest.service.jms;

import java.io.Serializable;

import javax.jms.BytesMessage;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

import org.aludratest.service.Interaction;
import org.aludratest.service.TechnicalArgument;
import org.aludratest.service.TechnicalLocator;

public interface JmsInteraction extends Interaction {

	void sendTextMessage(String text, @TechnicalLocator String destinationName);

	void sendObjectMessage(Serializable object, @TechnicalLocator String destinationName);

	TextMessage createTextMessage();

	ObjectMessage createObjectMessage();

	BytesMessage createBytesMessage();

	MapMessage createMapMessage();

	StreamMessage createStreamMessage();

	void sendMessage(Message message, @TechnicalLocator String destinationName);

	Message receiveMessage(@TechnicalLocator String destinationName, @TechnicalArgument long timeout);

}
