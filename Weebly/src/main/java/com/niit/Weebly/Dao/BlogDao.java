package com.niit.Weebly.Dao;

import java.util.List;

import com.niit.Weebly.Model.Blog;

public interface BlogDao {
	
    public List<Blog> getAllBlogs();
	
	public boolean saveBlog(Blog blog);
	
	public boolean deleteBlog(Blog blog);
	
	public boolean updateBlog(Blog blog);
	
	public Blog getBlogByBlogId(int blogId);
	
	public List<Blog> getAllApproveBlogs();
    
	public List<Blog> getAllPendingBlogs();

}
