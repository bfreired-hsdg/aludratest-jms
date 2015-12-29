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

import org.aludratest.config.ConfigProperties;
import org.aludratest.config.ConfigProperty;
import org.aludratest.service.AludraService;

@ConfigProperties({
		@ConfigProperty(name = "initialContextFactory", description = "The name of the InitialContextFactory class to use for JMS initialization.", type = String.class, required = true),
		@ConfigProperty(name = "providerUrl", description = "The URL to use as Provider URL for the initial context.", type = String.class, required = true),
		@ConfigProperty(name = "connectionFactoryJndiName", description = "The JNDI name of the Connection Factory to use.", type = String.class, required = true),
		@ConfigProperty(name = "jmsUser", description = "User name to use for JMS connection. May not be required.", type = String.class, required = false),
		@ConfigProperty(name = "jmsPassword", description = "Password to use for JMS connection. Only required if jmsUser is set.", type = String.class, required = false) })
public interface JmsService extends AludraService {

	@Override
	JmsInteraction perform();

	@Override
	JmsCondition check();

	@Override
	JmsVerification verify();

}
