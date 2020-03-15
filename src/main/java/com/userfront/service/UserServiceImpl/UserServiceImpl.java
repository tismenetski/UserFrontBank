package com.userfront.service.UserServiceImpl;


import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.userfront.dao.RoleDao;
import com.userfront.dao.UserDao;
import com.userfront.domain.User;
import com.userfront.domain.security.UserRole;
import com.userfront.service.AccountService;
import com.userfront.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Transactional // 
public class UserServiceImpl implements UserService {
	
	private static final Logger LOG= LoggerFactory.getLogger(UserService.class);

	
	@Autowired //DI
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private AccountService accountService;
	
	
	//Save User to the database
	public void save(User user)
	{
		userDao.save(user);
	}
	
	public User findByUsername(String username)
	{
		return userDao.findByUsername(username);
	}
	
	public User findByEmail(String email)
	{
		return userDao.findByEmail(email);
	}
	
	
	
	public boolean checkUserExists(String username, String email)
	{
		if (checkUsernameExists(username) || checkEmailExists(email))
		{
			return true;
		}
		return false;
	}
	
	public boolean checkUsernameExists(String username)
	{
		if (findByUsername(username)!=null)
		{
			return true;
		}
		return false;
	}
	
	public boolean checkEmailExists(String email)
	{
		if (findByEmail(email)!=null)
		{
			return true;
		}
		return false;
	}


	public User createUser(User user, Set<UserRole> userRoles) {
		User localUser = userDao.findByUsername(user.getUsername());

		if (localUser != null) {
			LOG.info("User with username {} already exist. Nothing will be done. ", user.getUsername());
		} else {
			String encryptedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encryptedPassword);

			for (UserRole ur : userRoles) {
				roleDao.save(ur.getRole());
			}

			user.getUserRoles().addAll(userRoles);

			user.setPrimaryAccount(accountService.createPrimaryAccount());
			user.setSavingsAccount(accountService.createSavingsAccount());

			localUser = userDao.save(user);
		}

		return localUser;
	}

	public void saveUser(User user)
	{
		userDao.save(user);
	}
	
	
	
}
