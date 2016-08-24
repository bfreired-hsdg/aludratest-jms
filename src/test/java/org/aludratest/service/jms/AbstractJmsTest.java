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

import org.aludratest.service.AludraService;
import org.aludratest.service.ComponentId;
import org.aludratest.testing.service.AbstractAludraServiceTest;
import org.apache.activemq.broker.BrokerService;
import org.databene.commons.IOUtil;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Parent test class for testing aludratest-jms.
 * @author Volker Bergmann
 */

public class AbstractJmsTest extends AbstractAludraServiceTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJmsTest.class);

    protected static final String testBrokerUri = "vm://localhost";

    private static BrokerService testBroker;

    protected JmsService service;

    @BeforeClass
    public static void startTestBroker() throws Exception {
        LOGGER.info("Setting up embedded ActiveMQ broker for URL " + testBrokerUri);
        testBroker = new BrokerService();
        testBroker.setPersistent(false);
        testBroker.addConnector(testBrokerUri);
        testBroker.start();
        LOGGER.info("Done setting up embedded ActiveMQ broker for URL " + testBrokerUri);
    }

    @Before
    public void prepareJmsService() {
        LOGGER.info("Setting up JmsService object connected to URL " + testBrokerUri);
        this.service = getLoggingJmsService();
        LOGGER.info("Done setting up JmsService object connected to URL " + testBrokerUri);
    }
    
    @After
    public void closeJmsService() {
    	IOUtil.close(service);
    }

    @AfterClass
    public static void stopTestBroker() throws Exception {
        LOGGER.info("Stopping embedded ActiveMQ broker for URL " + testBrokerUri);
        if (testBroker != null && testBroker.isStarted()) {
        	testBroker.stop();
        }
        LOGGER.info("Done stopping embedded ActiveMQ broker for URL " + testBrokerUri);
    }

    /**
     * Build a JmsService connected to the jms at testBrokerUri
     *
     * @return  the JmsService.
     * @throws org.aludratest.exception.TechnicalException On error initiating the service.
     */
    protected JmsService getLoggingJmsService() {
        return newLoggingService(JmsService.class, "jmsTest");
    }

    @SuppressWarnings("unchecked")
    public <T extends AludraService, U extends T> U newLoggingService(Class<T> interfaceClass, String moduleName) {
        return (U) framework.getServiceManager().createAndConfigureService(ComponentId.create(interfaceClass, moduleName), context, true);
    }

}
