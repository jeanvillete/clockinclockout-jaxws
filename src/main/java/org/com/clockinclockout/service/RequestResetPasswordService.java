package org.com.clockinclockout.service;

import org.com.clockinclockout.domain.RequestResetPassword;
import org.com.clockinclockout.domain.User;

public interface RequestResetPasswordService {

	/**
	 * Delete all the requests for reset password that were not confirmed/proceeded by the user for some reason.
	 * @param user
	 */
	void deleteNotConfirmed( User user );
	
	void processRequest( RequestResetPassword requestResetPassword );
	
}
