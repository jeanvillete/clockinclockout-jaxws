package org.com.clockinclockout.domain;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EmailConfirmation extends EmailContent {

	private static final SimpleDateFormat SDF = new SimpleDateFormat( "dd_MM_yyyy_HH:mm:ss_z" );
	
	private String hash;
	
	public EmailConfirmation( Email email ) {
		super( email );
		
		this.hash = this.getEmail().getAddress() + SDF.format( this.getEmail().getRecordedTime() );
		this.hash = new BCryptPasswordEncoder().encode( this.hash );
		try {
			this.hash = URLEncoder.encode( this.hash, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException( e );
		}
	}

	public String getHash() {
		return hash;
	}
	
}
