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
