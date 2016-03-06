package org.com.clockinclockout.test;

import org.com.clockinclockout.domain.Email;
import org.com.clockinclockout.domain.Profile;
import org.com.clockinclockout.domain.User;
import org.com.clockinclockout.service.EmailService;
import org.com.clockinclockout.service.ProfileService;
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

		Email email = this.emailService.getBy( "jean.villete@gmail.com", true );
		User user = this.userService.getBy( email );
		for ( Profile profile : profileService.listBy( user ) )
			System.out.println( profile.toString() );
	}
}
