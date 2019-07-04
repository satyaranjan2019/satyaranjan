package com.niit.Weebly.Dao;

import java.util.List;

import com.niit.Weebly.Model.BlogComment;


public interface BlogCommentDao {
	public List<BlogComment> getAllBlogComments();
	public boolean save(BlogComment blogComment);
	public boolean update(BlogComment blogComment);
	public boolean saveOrUpdate(BlogComment blogComment);//Pending
	public boolean delete(BlogComment blogComment);//Pending
	public BlogComment getByBlogCommentId(int id);
	public List<BlogComment> listByBlogId(int id);
	public List<BlogComment> listByUserId(String id);

}
