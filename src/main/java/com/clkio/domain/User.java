package com.clkio.domain;

import java.util.List;
import java.util.Locale;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

import com.clkio.exception.DomainValidationException;

public class User extends CommonDomain {

	private Email email;
	private transient String password;
	private Locale locale;
	private List< Profile > profiles;
	
	public User( Integer id ) {
		super( id );
	}
	
	public User( Email email ) {
		this( null, email, Locale.getDefault() );
	}
	
	public User( Email email, Locale locale ) {
		this( null, email, locale );
	}
	
	public User( Integer id, Email email, Locale locale ) {
		super( id );
		
		this.setEmail(email);
		this.setLocale(locale);
	}
	
	public void setPassword(String password) {
		this.setPassword( password, true );
	}
	
	public void setPassword( String password, boolean toBeEncoded ) {
		if ( !StringUtils.hasText( password ) || password.length() <= 6 )
			throw new DomainValidationException( "Argument password cannot be null nor empty, and must be greater than 6 characters." );
		
		this.password = toBeEncoded ? new BCryptPasswordEncoder().encode( password ) : password;
	}
	
	public void setEmail(Email email) {
		if( email == null ) throw new DomainValidationException( "Argument email cannot be null." );
		
		this.email = email;
		this.email.setUser( this );
	}
	
	public void setLocale(Locale locale) {
		if( locale == null ) throw new DomainValidationException( "Argument locale cannot be null." );
		this.locale = locale;
	}
	
	public String getPassword() {
		if( !StringUtils.hasText( password ) ) throw new DomainValidationException( "The field 'password' has not been initialized yet." );
		return password;
	}
	public Email getEmail() {
		if( email == null ) throw new DomainValidationException( "Field email has not been initialized yet." );
		return email;
	}

	public Locale getLocale() {
		if( locale == null ) throw new DomainValidationException( "Field locale has not been initialized yet." );
		return locale;
	}

	public List< Profile > getProfiles() {
		return profiles;
	}

	public void setProfiles( List< Profile > profiles ) {
		this.profiles = profiles;
	}

}
