package com.userfront.domain.security;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Role {
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private int roleId;
	
	private String name;
	
	@OneToMany(mappedBy = "role" , cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
	private Set<UserRole> userRoles=new HashSet<>(); // We need this to have two types of roles, admin and user
	
	public Role()
	{
		
	}

	public int getRoleId() {
		return roleId;
	}

	public String getName() {
		return name;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
	
	
	

}
