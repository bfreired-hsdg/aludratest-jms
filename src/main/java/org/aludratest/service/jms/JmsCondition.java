package org.aludratest.service.jms;

import org.aludratest.service.Condition;

public interface JmsCondition extends Condition {

	boolean isDestinationAvailable(String destinationName);

}
