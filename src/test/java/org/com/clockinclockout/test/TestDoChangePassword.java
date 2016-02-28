package org.com.clockinclockout.test;

import java.util.Locale;

import org.com.clockinclockout.domain.Email;
import org.com.clockinclockout.domain.RequestResetPassword;
import org.com.clockinclockout.domain.User;
import org.com.clockinclockout.service.RequestResetPasswordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

@RunWith( SpringJUnit4ClassRunner.class )
@ActiveProfiles( "devtest" )
@ContextConfiguration({
	"classpath:springframework/spring-application-context.xml",
	"classpath:springframework/spring-datasource.xml",
	"classpath:springframework/spring-java-mail.xml",
	"classpath:springframework/spring-velocity.xml"
})
public class TestDoChangePassword {

	@Autowired
	RequestResetPasswordService resetPasswordService;
	
	@Test
	public void test() {
		Assert.notNull( resetPasswordService, "No instance was assigned to resetPasswordService." );
		
		RequestResetPassword requestResetPassword = new RequestResetPassword( new User( new Email( "jean.villete@gmail.com" ), new Locale( "en" ) ) );
		requestResetPassword.setConfirmationCodeValue( "$2a$10$QVUS0kPTptRMLpZLRYkCTOZ7na1BKePVciU/eEmg.pxea0NC.e8Z." );
		requestResetPassword.setNewPassword( "mynewpassword" );
		
		this.resetPasswordService.changePassword( requestResetPassword );
	}
	
	@Test
	public void matchPasswords() {
		Assert.state( new BCryptPasswordEncoder().matches( "passwordtest", "$2a$10$APOr0DshQimVgoMv4Qykru6DWHwCxC8ieM7LTvaJhkM5HG932Oe56" ) );
		Assert.state( !new BCryptPasswordEncoder().matches( "mynewpassword", "$2a$10$APOr0DshQimVgoMv4Qykru6DWHwCxC8ieM7LTvaJhkM5HG932Oe56" ) );
		Assert.state( !new BCryptPasswordEncoder().matches( "passwordtest", "$2a$10$xZVJckF88Fwbj7fd8Bu3puO6na8Mmyfsdk60sDeHAT7.FPA97H4Gm" ) );
		Assert.state( new BCryptPasswordEncoder().matches( "mynewpassword", "$2a$10$xZVJckF88Fwbj7fd8Bu3puO6na8Mmyfsdk60sDeHAT7.FPA97H4Gm" ) );
	}
}