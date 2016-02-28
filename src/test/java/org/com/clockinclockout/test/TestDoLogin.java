package org.com.clockinclockout.test;

import org.com.clockinclockout.domain.Email;
import org.com.clockinclockout.domain.User;
import org.com.clockinclockout.service.LoginService;
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
public class TestDoLogin {

	@Autowired
	LoginService loginService;
	
	@Test
	public void test() {
		Assert.notNull( loginService, "No instance was assigned to loginService." );
		
		User jean = new User( new Email( "jean.villete@gmail.com" ) );
		jean.setPassword( "mynewpassword", false );
		User syncUser = this.loginService.login( jean );
		
		Assert.notNull( syncUser );
		
		System.out.println( "[User logged successfully] id:" + syncUser.getId() );
	}
}
