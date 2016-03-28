package com.clkio.test.timecard;

import java.time.Duration;
import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.clkio.domain.Day;
import com.clkio.domain.ManualEntering;
import com.clkio.domain.ManualEnteringReason;
import com.clkio.domain.Profile;
import com.clkio.domain.User;
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
public class TestUpdateManualEntering {

	@Autowired
	TimeCardService timeCardService;
	
	@Autowired
	ProfileService profileService;
	
	@Test
	public void test() {
		Assert.notNull( timeCardService, "No instance was assigned to 'timeCardService'." );
		Assert.notNull( profileService, "No instance was assigned to 'profileService'." );

		Profile profile = profileService.listBy( new User( 46 ) ).get( 0 );
		
		timeCardService.update( profile, new ManualEntering( 7, new Day( LocalDate.now() ), new ManualEnteringReason( 48 ), Duration.parse( "PT4H" ) ) );
	}
}
