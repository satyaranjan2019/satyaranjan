package com.niit.Weebly.Dao;

import java.util.List;

import com.niit.Weebly.Model.UserDetails;



public interface UserDetailsDao {

	public boolean saveUser(UserDetails user);
	public boolean updateUser(UserDetails user);
	public boolean deleteUser(UserDetails user);
	public List<UserDetails> getAllUser();
	public UserDetails UserAuthentication(String userId, String userPassword);
	public UserDetails getUserByUserId(String id);
	public UserDetails getUserById(String id);

}
