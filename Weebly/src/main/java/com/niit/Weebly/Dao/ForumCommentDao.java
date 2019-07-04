package com.niit.Weebly.Dao;

import java.util.List;

import com.niit.Weebly.Model.ForumComment;


public interface ForumCommentDao {
	
	public boolean save(ForumComment forumComment);
	public boolean update(ForumComment forumComment);// pending
	public boolean saveOrUpdate(ForumComment forumComment);// pending
	public boolean delete(ForumComment forumComment);//Pending
	public ForumComment getByForumCommentId(int id);
	public List<ForumComment> listByUserId(String id);
	public List<ForumComment> listByForumId(int id);
	public List<ForumComment> getAllForumComments();

}
