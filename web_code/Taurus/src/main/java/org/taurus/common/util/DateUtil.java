package org.taurus.common.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import org.taurus.common.code.DateFormat;

public class DateUtil {
	
	public static LocalDateTime getLocalDateTime() {
		return LocalDateTime.now();
	}
	
	public static String formatDate(Date date, DateFormat dateFormat) {
		SimpleDateFormat format = new SimpleDateFormat(dateFormat.getValue());
		return format.format(date);
	}

}
