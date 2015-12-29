package org.aludratest.service.jms;

import org.aludratest.service.Verification;

public interface JmsVerification extends Verification {

	void assertDestinationAvailable(String destinationName);

}
