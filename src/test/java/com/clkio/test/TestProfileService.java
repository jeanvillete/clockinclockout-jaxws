package com.clkio.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.clkio.domain.Email;
import com.clkio.domain.Profile;
import com.clkio.domain.User;
import com.clkio.exception.ValidationException;
import com.clkio.exception.PersistenceException;
import com.clkio.service.EmailService;
import com.clkio.service.ProfileService;
import com.clkio.service.UserService;

@RunWith( SpringJUnit4ClassRunner.class )
@ActiveProfiles( "devtest" )
@ContextConfiguration({
	"classpath:springframework/spring-application-context.xml",
	"classpath:springframework/spring-datasource.xml",
	"classpath:springframework/spring-java-mail.xml",
	"classpath:springframework/spring-velocity.xml"
})
public class TestProfileService {

	@Autowired
	EmailService emailService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ProfileService profileService;
	
	@Test
	public void test() {
		Assert.notNull( profileService, "No instance was assigned to profileService." );

		Email email;
		try {
			email = this.emailService.getBy( "jean.villete@gmail.com", true );
			User user = this.userService.getBy( email );
			for ( Profile profile : profileService.listBy( user ) )
				System.out.println( profile.toString() );
		} catch ( ValidationException | PersistenceException e ) {
			e.printStackTrace();
		}
	}
}
