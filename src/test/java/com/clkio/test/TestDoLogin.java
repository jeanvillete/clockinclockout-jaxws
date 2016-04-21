package com.clkio.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.clkio.domain.Email;
import com.clkio.domain.User;
import com.clkio.exception.ValidationException;
import com.clkio.exception.PersistenceException;
import com.clkio.service.LoginService;

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
		
		try {
			User jean = new User( new Email( "jean.villete@gmail.com" ) );
			jean.setPassword( "mynewpassword", false );
			String code;
			code = this.loginService.login( jean, "127.0.0.1" );
			Assert.hasText( code );
			
			System.out.println( "[User logged successfully] code:" + code );
		} catch ( ValidationException | PersistenceException e ) {
			e.printStackTrace();
		}
		
	}
}
