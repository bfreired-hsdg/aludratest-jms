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
