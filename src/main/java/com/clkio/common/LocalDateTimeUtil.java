package com.clkio.common;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

abstract public class LocalDateTimeUtil {

	public static final Timestamp getTimestamp( LocalDateTime localDateTime ) {
		return localDateTime != null ? new Timestamp( localDateTime.atZone( ZoneId.systemDefault() ).toInstant().getEpochSecond() * 1000 ) : null;
	}
	
	public static final Timestamp getTimestamp( LocalDate localDate ) {
		return localDate != null ? getTimestamp( localDate.atStartOfDay() ) : null;
	}
	
}