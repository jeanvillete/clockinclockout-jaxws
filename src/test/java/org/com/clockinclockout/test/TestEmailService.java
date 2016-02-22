package org.com.clockinclockout.test;

import org.com.clockinclockout.domain.Email;
import org.com.clockinclockout.service.EmailService;
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
public class TestEmailService {

	@Autowired
	EmailService emailService;
	
	@Test
	public void test() {
		Assert.notNull( emailService, "No instance was assigned to emailService." );

		Email email = new Email( "jean.villete@gmail.com" );
		email.setConfirmationCode( "%242a%2410%24427pXvpAoIfsMeixWRhGHe55m.DEr8Nr.bhOXXHs4u7IZz05ZJN4W" );
		
		this.emailService.confirm( email );
	}
}
