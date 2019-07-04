package com.niit.Weebly.Dao;

import java.util.List;

import com.niit.Weebly.Model.Forum;



public interface ForumDao {
	
public boolean saveForum(Forum forum);
	
	public boolean deleteForum(Forum forum);
	
	public boolean updateForum(Forum forum);
	
	public Forum getForumByForumId(int forumId);
	
	public List<Forum> getAllForums();
	
	public List<Forum> getAllApprovedForums();
	
	public List<Forum> getAllRejectedForums();

}
