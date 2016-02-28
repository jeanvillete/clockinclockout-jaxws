package org.com.clockinclockout.service.impl;

import org.com.clockinclockout.domain.Email;
import org.com.clockinclockout.domain.User;
import org.com.clockinclockout.service.EmailService;
import org.com.clockinclockout.service.LoginService;
import org.com.clockinclockout.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Service
public class LoginServiceImpl implements LoginService, InitializingBean {

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserService userService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull( this.emailService, "The property 'emailService' has not been properly initialized." );
		Assert.notNull( this.userService, "The property 'userService' has not been properly initialized." );
	}
	
	@Override
	@Transactional( propagation = Propagation.NEVER, readOnly = true )
	public User login( User user ) {
		Assert.notNull( user );
		Assert.state( StringUtils.hasText( user.getPassword() ) );
		
		Email syncEmail = this.emailService.getBy( user.getEmail().getAddress(), true );
		Assert.state( syncEmail != null );
		
		User syncUser = this.userService.getBy( syncEmail );
		Assert.state( syncUser != null );
		Assert.state( new BCryptPasswordEncoder().matches( user.getPassword(), syncUser.getPassword() ) );
		
		return syncUser;
	}

}
