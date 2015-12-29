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
