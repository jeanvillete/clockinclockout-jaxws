package com.clkio.test.timecard;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.clkio.domain.ClockinClockout;
import com.clkio.domain.Profile;
import com.clkio.domain.User;
import com.clkio.exception.ValidationException;
import com.clkio.exception.ConflictException;
import com.clkio.exception.PersistenceException;
import com.clkio.service.ProfileService;
import com.clkio.service.TimeCardService;

@RunWith( SpringJUnit4ClassRunner.class )
@ActiveProfiles( "devtest" )
@ContextConfiguration({
	"classpath:springframework/spring-application-context.xml",
	"classpath:springframework/spring-datasource.xml",
	"classpath:springframework/spring-java-mail.xml",
	"classpath:springframework/spring-velocity.xml"
})
public class TestInsertClockinClockout {

	@Autowired
	TimeCardService timeCardService;
	
	@Autowired
	ProfileService profileService;
	
	@Test
	public void test() {
		Assert.notNull( timeCardService, "No instance was assigned to 'timeCardService'." );
		Assert.notNull( profileService, "No instance was assigned to 'profileService'." );

		try {
			Profile profile = profileService.listBy( new User( 46 ) ).get( 0 );
			
			timeCardService.insert( profile, new ClockinClockout( LocalDateTime.now(), null ) );
			
			Thread.sleep( 3000 );
			
			timeCardService.insert( profile, new ClockinClockout( null, LocalDateTime.now() ) );

			Thread.sleep( 3000 );
			
			timeCardService.insert( profile, new ClockinClockout( LocalDateTime.now(), LocalDateTime.now().plusMinutes( 20 ) ) );
		} catch ( InterruptedException e ) {
			e.printStackTrace();
		} catch ( ValidationException e ) {
			e.printStackTrace();
		} catch ( PersistenceException e ) {
			e.printStackTrace();
		} catch ( ConflictException e ) {
			e.printStackTrace();
		}
		
	}
}
