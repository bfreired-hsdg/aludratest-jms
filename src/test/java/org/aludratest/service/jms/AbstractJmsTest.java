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

import org.aludratest.config.impl.SimplePreferences;
import org.aludratest.service.jms.impl.JmsServiceImpl;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.jndi.ActiveMQInitialContextFactory;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Parent test class for testing aludratest-jms.
 * @author Volker Bergmann
 */

public class AbstractJmsTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJmsTest.class);

    protected static final String testBrokerUri = "vm://localhost";

    private static BrokerService testBroker;

    protected JmsService service;

    @BeforeClass
    public static void startTestBroker() {
        LOGGER.info("Setting up embedded ActiveMQ broker for URL " + testBrokerUri);
        testBroker = new BrokerService();
        try {
            testBroker.setPersistent(false);
            testBroker.addConnector(testBrokerUri);
            testBroker.start();
        } catch (Exception e) {
            Assert.fail("Failed to setup testbroker for url "
                    + testBrokerUri + " : " + e.getMessage());
        }
        LOGGER.info("Done setting up embedded ActiveMQ broker for URL " + testBrokerUri);
    }

    @Before
    public void prepareJmsService() {
        LOGGER.info("Setting up JmsService object connected to URL " + testBrokerUri);
        try {
            this.service = buildJmsService();
 

        } catch (Exception e) {
            Assert.fail("Failed to connect to testbroker on url "
                    + testBrokerUri + " : " + e.getMessage());
        }
        LOGGER.info("Done setting up JmsService object connected to URL " + testBrokerUri);
    }

    @AfterClass
    public static void stopTestBroker() {
        LOGGER.info("Stopping embedded ActiveMQ broker for URL " + testBrokerUri);
        if (testBroker != null && testBroker.isStarted()) {
            try {
                testBroker.stop();
            } catch (Exception e)  {
                Assert.fail("Failed to stop testbroker for url "
                        + testBrokerUri + " : " + e.getMessage());
            }
        }
        LOGGER.info("Done stopping embedded ActiveMQ broker for URL " + testBrokerUri);
    }

    /**
     * Build a JmsService connected to the jms at testBrokerUri
     *
     * @return  the JmsService.
     * @throws org.aludratest.exception.TechnicalException On error initiating the service.
     */
    protected JmsService buildJmsService() {
        SimplePreferences preferences = new  SimplePreferences();
        preferences.setValue("connectionFactoryJndiName","ConnectionFactory");
        preferences.setValue("providerUrl",testBrokerUri);
        preferences.setValue("initialContextFactory",ActiveMQInitialContextFactory.class.getName());

        JmsServiceImpl prepareTestObject = new JmsServiceImpl();
        prepareTestObject.configure(preferences);
        return prepareTestObject;
    }

}
