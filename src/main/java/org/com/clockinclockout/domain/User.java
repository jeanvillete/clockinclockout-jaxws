package org.com.clockinclockout.domain;

import java.util.List;
import java.util.Locale;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

public class User extends CommonDomain {

	private Email email;
	private String password;
	private Locale locale;
	private List< Profile > profiles;
	
	public User( Email email, Locale locale ) {
		this( null, email, locale );
	}
	
	public User( Integer id ) {
		super( id );
	}
	
	public User( Integer id, Email email, Locale locale ) {
		super( id );
		
		this.setEmail(email);
		this.setLocale(locale);
	}
	
	public void setPassword(String password) {
		Assert.hasLength( password, "Argument password cannot be null nor empty." );
		this.password = new BCryptPasswordEncoder().encode( password );
	}
	
	public void setEmail(Email email) {
		Assert.notNull( email, "Argument email cannot be null." );
		
		this.email = email;
		this.email.setUser( this );
	}
	
	public void setLocale(Locale locale) {
		Assert.notNull( locale, "Argument locale cannot be null." );
		this.locale = locale;
	}
	
	public String getPassword() {
		return password;
	}
	public Email getEmail() {
		Assert.state( email != null, "Field email has not been initialized yet." );
		return email;
	}

	public Locale getLocale() {
		Assert.state( locale != null, "Field locale has not been initialized yet." );
		return locale;
	}

	public List< Profile > getProfiles() {
		return profiles;
	}

	public void setProfiles( List< Profile > profiles ) {
		this.profiles = profiles;
	}

}
