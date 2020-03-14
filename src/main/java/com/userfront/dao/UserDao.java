package com.userfront.dao;

import org.springframework.data.repository.CrudRepository;

import com.userfront.domain.User;

import java.util.List;

public interface UserDao extends CrudRepository<User, Long> {
	User findByUsername(String username);//Spring will automatically know to bind findBy to what string is attached to it
	User findByEmail(String email);
	List<User> findAll();

}
