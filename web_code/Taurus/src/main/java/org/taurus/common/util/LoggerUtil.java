package org.taurus.common.util;

import org.slf4j.Logger;

public class LoggerUtil {

	public static void printParam(Logger logger, String key, Object param) {
		logger.info(key + ":" + param);
	}

}
