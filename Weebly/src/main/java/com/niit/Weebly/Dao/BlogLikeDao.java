package com.niit.Weebly.Dao;

import java.util.List;

import com.niit.Weebly.Model.BlogLike;

public interface BlogLikeDao {

	public List<BlogLike>getBlogLikesByBlogId(int blogId);
	public boolean saveBlogLike(BlogLike blogLike);
	public boolean isExist(int blogId,String userId);
}
