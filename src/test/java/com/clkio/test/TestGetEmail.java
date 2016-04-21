package com.clkio.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.clkio.domain.Email;
import com.clkio.exception.ValidationException;
import com.clkio.exception.PersistenceException;
import com.clkio.service.EmailService;

@RunWith( SpringJUnit4ClassRunner.class )
@ActiveProfiles( "devtest" )
@ContextConfiguration({
	"classpath:springframework/spring-application-context.xml",
	"classpath:springframework/spring-datasource.xml",
	"classpath:springframework/spring-java-mail.xml",
	"classpath:springframework/spring-velocity.xml"
})
public class TestGetEmail {

	@Autowired
	private EmailService emailService;
	
	@Test
	public void test() {
		Assert.notNull( emailService, "No instance was assigned to emailService." );
		
		try {
			Email email = new Email( 23 );
			System.out.println( this.emailService.get( email ) );
		} catch ( ValidationException | PersistenceException e ) {
			e.printStackTrace();
		}
	}
}
