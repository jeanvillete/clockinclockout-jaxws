package com.clkio.test.timecard;

import java.time.LocalDate;
import java.time.YearMonth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.clkio.domain.Profile;
import com.clkio.domain.User;
import com.clkio.exception.ValidationException;
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
public class TestTotalTime {

	@Autowired
	TimeCardService timeCardService;
	
	@Autowired
	ProfileService profileService;
	
	@Test
	public void test() {
		Assert.notNull( timeCardService, "No instance was assigned to 'timeCardService'." );
		Assert.notNull( profileService, "No instance was assigned to 'profileService'." );

		try {
			Profile profile;
			profile = profileService.listBy( new User( 46 ) ).get( 0 );
			for ( int i = 1; i < 10 ; i ++ )
				System.out.println( "2015-07-0" + i + ": " + timeCardService.getTotalTime( profile, LocalDate.parse( "2015-07-0" + i ) ) );
			
			System.out.println( timeCardService.getTotalTime( profile, YearMonth.parse( "2015-07" ).atEndOfMonth() ) );
			System.out.println( timeCardService.getTotalTime( profile ) );
		} catch ( ValidationException e ) {
			e.printStackTrace();
		}
		
	}
}
