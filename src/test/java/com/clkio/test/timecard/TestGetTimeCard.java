package com.clkio.test.timecard;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.clkio.common.DurationUtil;
import com.clkio.domain.ClockinClockout;
import com.clkio.domain.Day;
import com.clkio.domain.DayEntering;
import com.clkio.domain.ManualEntering;
import com.clkio.domain.Profile;
import com.clkio.domain.TimeCard;
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
public class TestGetTimeCard {

	@Autowired
	TimeCardService timeCardService;
	
	@Autowired
	ProfileService profileService;
	
	@Test
	public void test() {
		Assert.notNull( timeCardService, "No instance was assigned to 'timeCardService'." );
		Assert.notNull( profileService, "No instance was assigned to 'profileService'." );

		Profile profile = profileService.listBy( new User( 46 ) ).get( 0 );
		
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern( profile.getDateFormat() );
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern( profile.getHoursFormat() );
		
		TimeCard tc = timeCardService.getTimeCard( profile, YearMonth.parse( "2015-08" ) );
		
		for ( Day day : tc.getDaysSorted() ) {
			System.out.println( "[DAY] id=[" + day.getId() + "], date=[" + day.getDate().format( dateTimeFormatter ) + 
					"], expectedHours=[" + ( day.getExpectedHours() != null ? DurationUtil.fromDuration( day.getExpectedHours(), profile.getHoursFormat() ) : null ) + 
					"], notes=[" + day.getNotes() + "], balance=[" + DurationUtil.fromDuration( day.getBalance(), profile.getHoursFormat() ) + "]" );
			if ( !CollectionUtils.isEmpty( day.getTableEntering() ) )
				for ( DayEntering entering : day.getTableEntering() )
					if ( entering instanceof ClockinClockout ) {
						ClockinClockout clkio = ( ClockinClockout ) entering;
						System.out.println( "\t[CLOCKINCLOCKOUT] id=[" + entering.getId() + "], clockin=[" + ( clkio.getClockin() != null ? clkio.getClockin().format( timeFormatter ) : null ) + 
								"], clockout=[" + ( clkio.getClockout() != null ? clkio.getClockout().format( timeFormatter ) : null ) + "]" );
					} else if ( entering instanceof ManualEntering ) {
						ManualEntering me = ( ManualEntering ) entering;
						System.out.println( "\t[MANUALENTERING] id=[" + entering.getId() + 
								"], time_interval=[" + DurationUtil.fromDuration( me.getTimeInterval(), profile.getHoursFormat() ) + 
								"], reason.id=[" + me.getReason().getId() + 
								"], reason.reason=[" + me.getReason().getReason() + "]" );
					} else throw new IllegalStateException( "Invalid value for 'entering'" );
		}
	}
}
