package com.clkio.common;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

abstract public class LocalDateTimeUtil {

	public static final Timestamp getTimestamp( LocalDateTime localDateTime ) {
		return localDateTime != null ? new Timestamp( localDateTime.toInstant( ZoneOffset.UTC ).getEpochSecond() * 1000 ) : null;
	}
	
}