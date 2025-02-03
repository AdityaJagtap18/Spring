package com.springcrud.CurdOparation.jwt;

import java.util.List;

import com.springcrud.CurdOparation.model.UserTbl;

public class LoginUserResponse {
	private String token;
	
	private UserTbl userDetails;
	
	private List<UserTbl> projects;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserTbl getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserTbl userDetails) {
		this.userDetails = userDetails;
	}

	public List<UserTbl> getProjects() {
		return projects;
	}

	public void setProjects(List<UserTbl> projects) {
		this.projects = projects;
	}
	
	
}
