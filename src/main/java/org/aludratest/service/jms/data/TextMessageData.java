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
package org.aludratest.service.jms.data;

/**
 * {@link JmsMessageData} class for text messages.
 * @author Volker Bergmann
 */

public class TextMessageData extends JmsMessageData {
	
	private String messageText;
	
	public TextMessageData() {
		this(null);
	}
	
	public TextMessageData(String messageText) {
		this.messageText = messageText;
	}
	
	/**
	 * @return the messageText
	 */
	public String getMessageText() {
		return messageText;
	}
	
	/**
	 * @param messageText the messageText to set
	 */
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
	
	public String toString() {
		return "TextMessageData: "+messageText;
		
	}
	
}
