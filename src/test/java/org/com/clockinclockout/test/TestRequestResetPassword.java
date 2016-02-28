package org.com.clockinclockout.test;

import java.util.Locale;

import org.com.clockinclockout.domain.Email;
import org.com.clockinclockout.domain.RequestResetPassword;
import org.com.clockinclockout.domain.User;
import org.com.clockinclockout.service.RequestResetPasswordService;
import org.com.clockinclockout.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TestRequestResetPassword {

	@Autowired
	UserService userService;
	
	@Autowired
	RequestResetPasswordService resetPasswordService;
	
	@Test
	public void test() {
		Assert.notNull( resetPasswordService, "No instance was assigned to resetPasswordService." );
		
		User jean = new User( new Email( "jean.villete@gmail.com" ), new Locale( "en" ) );
		RequestResetPassword requestResetPassword = new RequestResetPassword( jean );
		this.resetPasswordService.processRequest( requestResetPassword );
	}
}
