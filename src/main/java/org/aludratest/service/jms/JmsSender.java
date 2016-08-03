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

import org.aludratest.dict.ActionWordLibrary;
import org.aludratest.service.jms.data.FileMessageData;
import org.aludratest.service.jms.data.ObjectMessageData;
import org.aludratest.service.jms.data.TextMessageData;

/**
 * Abstract parent class for specific JMS message senders.
 * @author Volker Bergmann
 */

public abstract class JmsSender<E extends JmsSender<E>> implements ActionWordLibrary<E> {

	private JmsService service;
	private String destinationName;
	
	public JmsSender(String destinationName, JmsService service) {
		this.destinationName = destinationName;
		this.service = service;
	}

	public E sendTextMessage(TextMessageData data) {
		service.perform().sendTextMessage(data.getMessageText(), destinationName);
		return verifyState();
	}

	public E sendObjectMessage(ObjectMessageData data) {
		service.perform().sendObjectMessage(data.getMessageObject(), destinationName);
		return verifyState();
	}

	public E sendFileAsTextMessage(FileMessageData data) {
		service.perform().sendFileAsTextMessage(data.getFileUri(), destinationName);
		return verifyState();
	}
	
	public E sendFileAsBytesMessage(FileMessageData data) {
		service.perform().sendFileAsBytesMessage(data.getFileUri(), destinationName);
		return verifyState();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public E verifyState() {
		// no possibility of having an invalid state
		return (E) this;
	}

}
