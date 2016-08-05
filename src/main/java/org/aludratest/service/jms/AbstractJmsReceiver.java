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

/**
 * Receives JMS messages.
 * @author Volker Bergmann
 */

public abstract class AbstractJmsReceiver<E extends AbstractJmsReceiver<E>> implements ActionWordLibrary<E> {
	
	protected final JmsService service;
	protected final String destinationName;
	
	public AbstractJmsReceiver(String destinationName, JmsService service) {
		this.destinationName = destinationName;
		this.service = service;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E verifyState() {
		// no possibility of having an invalid state
		return (E) this;
	}

}
