package com.userfront.service.UserServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.userfront.dao.UserDao;
import com.userfront.domain.User;



@Service
public class UserSecurityService implements UserDetailsService {
	/*
	 public interface UserDetailsService
	 Core interface which loads user-specific data.
	 It is used throughout the framework as a user DAO(Data access object)
 	 and is the strategy used by the DaoAuthenticationProvider.
	 The interface requires only one read-only method,
 	 which simplifies support for new data-access strategies.
	 */

	/** The application logger */
	private static final Logger LOG = LoggerFactory.getLogger(UserSecurityService.class);

	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUsername(username);
		if (user.isAccountNonExpired())
		{

			System.out.println("account not expired");
		}
		else
		{
			System.out.println("account  expired");
		}


		if (user.isAccountNonLocked())
		{
			System.out.println("account not locked");
		}
		else
		{
			System.out.println("account  locked");
		}

		if (user.isCredentialsNonExpired())
		{
			System.out.println("credentials not expired");
		}

		else
		{
			System.out.println("credentials  expired");
		}

		if (null == user) {
			LOG.warn("Username {} not found", username);
			throw new UsernameNotFoundException("Username " + username + " not found");
		}
		return user;
	}
	
}
