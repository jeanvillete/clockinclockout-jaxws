package com.clkio.quartz;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.Assert;

import com.clkio.service.EmailService;

public class CleanEmailNotConfirmed extends QuartzJobBean implements InitializingBean {

	private static final Logger LOG = Logger.getLogger( CleanEmailNotConfirmed.class );
	
	transient private EmailService emailService;

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull( emailService, "The field 'emailService' has not been properly initialized." );
	}
	
	@Override
	protected void executeInternal( JobExecutionContext context ) throws JobExecutionException {
		try {
			this.emailService.cleanNotConfirmed();
		} catch ( Exception e ) {
			LOG.error( e );
		}
	}

	public void setEmailService( EmailService emailService ) {
		this.emailService = emailService;
	}

}