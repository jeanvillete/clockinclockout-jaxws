package org.com.clockinclockout.service;

import org.com.clockinclockout.domain.RequestResetPassword;
import org.com.clockinclockout.domain.User;

public interface RequestResetPasswordService {

	/**
	 * Delete all the requests for reset password that were not confirmed/proceeded by the user for some reason.
	 * @param user
	 */
	void deleteNotConfirmed( User user );
	
	/**
	 * Deletes all the requests for reset password requested <b>longer than one day </b>
	 * but were not confirmed/proceeded by the user.
	 */
	void cleanNotConfirmed();
	
	/**
	 * This service is meant to be used when the user requests a reset password.
	 * @param requestResetPassword
	 */
	void processRequest( RequestResetPassword requestResetPassword );

	/**
	 * This service is responsable to deal with a confirmation step from the user, that is, the user received the
	 * email on his inbox and clicked the link.
	 * @param requestResetPassword
	 * @return The return is the value for <b>confirmationCodeValue</b> and means a valid identifier to be used
	 *  later by the user with the new password.
	 */
	String confirm( RequestResetPassword requestResetPassword );
	
	/**
	 * Service responsable to effectively change a user's password.
	 * It has to consider mainly the properties 'emailAddress', 'confirmationCodeValue' and 'newPassword'.
	 * 
	 * @param requestResetPassword
	 * @return
	 */
	boolean changePassword( RequestResetPassword requestResetPassword );
}
