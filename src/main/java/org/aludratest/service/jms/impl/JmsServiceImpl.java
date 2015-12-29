package org.aludratest.service.jms.impl;

import java.util.Hashtable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.aludratest.config.ConfigurationException;
import org.aludratest.config.Preferences;
import org.aludratest.config.ValidatingPreferencesWrapper;
import org.aludratest.exception.TechnicalException;
import org.aludratest.service.AbstractConfigurableAludraService;
import org.aludratest.service.jms.JmsCondition;
import org.aludratest.service.jms.JmsInteraction;
import org.aludratest.service.jms.JmsService;
import org.aludratest.service.jms.JmsVerification;
import org.databene.commons.StringUtil;

public class JmsServiceImpl extends AbstractConfigurableAludraService implements JmsService {

	private String providerUrl;

	private InitialContext initialContext;

	private ConnectionFactory connectionFactory;

	private Connection connection;

	private JmsActionImpl action;

	@Override
	public String getDescription() {
		return "JMS @ " + providerUrl;
	}

	@Override
	public void close() {
		if (connection != null) {
			try {
				connection.close();
			}
			catch (JMSException e) {
				// ignore here
			}
		}
		if (action != null) {
			action.close();
		}
	}

	@Override
	public String getPropertiesBaseName() {
		return "jms";
	}

	@Override
	public void configure(Preferences preferences) {
		ValidatingPreferencesWrapper prefs = new ValidatingPreferencesWrapper(preferences);
		providerUrl = prefs.getRequiredStringValue("providerUrl");

		String initialContextFactory = prefs.getRequiredStringValue("initialContextFactory");
		String connectionFactoryName = prefs.getRequiredStringValue("connectionFactoryJndiName");

		String userName = prefs.getStringValue("jmsUser");
		String password = prefs.getStringValue("jmsPassword");

		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, initialContextFactory);
		env.put(Context.PROVIDER_URL, providerUrl);
		try {
			initialContext = new InitialContext(env);
			connectionFactory = (ConnectionFactory) initialContext.lookup(connectionFactoryName);
			if (connectionFactory == null) {
				throw new ConfigurationException("The connection factory could not be found.");
			}

			if (StringUtil.isEmpty(userName)) {
				connection = connectionFactory.createConnection();
			}
			else {
				connection = connectionFactory.createConnection(userName, password);
			}
		}
		catch (NamingException e) {
			throw new TechnicalException("Could not retrieve objects from JNDI context", e);
		}
		catch (JMSException e) {
			throw new TechnicalException("Could not establish JMS connection", e);
		}

		action = new JmsActionImpl(connection, initialContext);
	}

	@Override
	public JmsInteraction perform() {
		return action;
	}

	@Override
	public JmsCondition check() {
		return action;
	}

	@Override
	public JmsVerification verify() {
		return action;
	}

	@Override
	public void initService() {
		// nothing to do
	}

}
