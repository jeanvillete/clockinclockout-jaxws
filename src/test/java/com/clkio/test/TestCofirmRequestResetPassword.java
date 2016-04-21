package com.clkio.test;

import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.clkio.domain.Email;
import com.clkio.domain.RequestResetPassword;
import com.clkio.domain.User;
import com.clkio.exception.ValidationException;
import com.clkio.exception.PersistenceException;
import com.clkio.service.RequestResetPasswordService;

@RunWith( SpringJUnit4ClassRunner.class )
@ActiveProfiles( "devtest" )
@ContextConfiguration({
	"classpath:springframework/spring-application-context.xml",
	"classpath:springframework/spring-datasource.xml",
	"classpath:springframework/spring-java-mail.xml",
	"classpath:springframework/spring-velocity.xml"
})
public class TestCofirmRequestResetPassword {

	@Autowired
	RequestResetPasswordService resetPasswordService;
	
	@Test
	public void test() {
		Assert.notNull( resetPasswordService, "No instance was assigned to resetPasswordService." );
		
		User jean = new User( new Email( "jean.villete@gmail.com" ), new Locale( "en" ) );
		RequestResetPassword requestResetPassword = new RequestResetPassword( jean );
		requestResetPassword.setRequestCodeValue( "%242a%2410%24lf78i1Et8nVUvfsjiAqVS.GR35VNh0yCjFLBUjSSS8SJSi7nfaNlm" );
		try {
			String confirmationCodeValue;
			confirmationCodeValue = this.resetPasswordService.confirm( requestResetPassword );
			System.out.println( "Confirmation code value received from the confirmation invoking: " + confirmationCodeValue );
		} catch ( ValidationException | PersistenceException e ) {
			e.printStackTrace();
		}
	}
}
