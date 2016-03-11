package com.clkio.quartz;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.Assert;

import com.clkio.service.RequestResetPasswordService;

public class CleanRequestResetPasswordNotConfirmed extends QuartzJobBean implements InitializingBean {

	private static final Logger LOG = Logger.getLogger( CleanRequestResetPasswordNotConfirmed.class );
	
	transient private RequestResetPasswordService requestResetPasswordService;

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull( requestResetPasswordService, "The field 'requestResetPasswordService' has not been properly initialized." );
	}
	
	@Override
	protected void executeInternal( JobExecutionContext context ) throws JobExecutionException {
		try {
			this.requestResetPasswordService.cleanNotConfirmed();
		} catch ( Exception e ) {
			LOG.error( e );
		}
	}

	public void setRequestResetPasswordService( RequestResetPasswordService requestResetPasswordService ) {
		this.requestResetPasswordService = requestResetPasswordService;
	}
}