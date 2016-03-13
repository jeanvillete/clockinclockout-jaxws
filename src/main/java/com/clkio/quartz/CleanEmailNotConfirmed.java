package com.clkio.quartz;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.Assert;

import com.clkio.service.UserService;

public class CleanEmailNotConfirmed extends QuartzJobBean implements InitializingBean {

	private static final Logger LOG = Logger.getLogger( CleanEmailNotConfirmed.class );
	
	transient private UserService userService;

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull( userService, "The field 'userService' has not been properly initialized." );
	}
	
	@Override
	protected void executeInternal( JobExecutionContext context ) throws JobExecutionException {
		try {
			this.userService.cleanNotConfirmed();
		} catch ( Exception e ) {
			LOG.error( e );
		}
	}

	public void setUserService( UserService userService ) {
		this.userService = userService;
	}

}