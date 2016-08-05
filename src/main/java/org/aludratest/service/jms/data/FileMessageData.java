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
 * {@link JmsMessageData} class for sending files via JMS.
 * The fileUri field and parameters denote a URI of the file as supported 
 * by the Databene Commons library. It may be a resource in the classpath, 
 * a local file or a remote file like http://www.myserver.com/myfile.txt.
 * @author Volker Bergmann
 */

public class FileMessageData extends JmsMessageData {
	
	/** The URI of the file that contains the message data, 
	 * see the class comment for its semantics */
	private String fileUri;

	public FileMessageData() {
		this(null);
	}
	
	public FileMessageData(String fileUri) {
		this.fileUri = fileUri;
	}
	
	/**
	 * Returns the fileUri, see the class comment for its semantics.
	 * @return the fileUri
	 */
	public String getFileUri() {
		return fileUri;
	}
	
	/**
	 * Sets the fileUri, see the class comment for its semantics.
	 * @param fileUri the fileUri to set
	 */
	public void setFileUri(String fileUri) {
		this.fileUri = fileUri;
	}
	
}
