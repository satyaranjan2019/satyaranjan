package com.niit.Weebly.Controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.niit.Weebly.Dao.BlogCommentDao;
import com.niit.Weebly.Dao.BlogDao;
import com.niit.Weebly.Dao.BlogLikeDao;
import com.niit.Weebly.Model.Blog;
import com.niit.Weebly.Model.BlogComment;
import com.niit.Weebly.Model.BlogLike;
import com.niit.Weebly.Model.UserDetails;

@RestController
public class BlogController {

	@Autowired
	BlogDao blog1;
	
	@Autowired
	Blog blog;
	
	@Autowired
	UserDetails userDetails;
	
	@Autowired
	BlogCommentDao blogCommentDao;
	
	@Autowired
	BlogComment blogComment;
	
	@Autowired
	BlogLikeDao blogd;
	@Autowired
	BlogLike bloge;
	
	
	//********************************************** BLOG **************************************************//
	//******************************************************************************************************//
	
	
	
	
	// 1 Display all blogs......
	
	@RequestMapping(value="/listOfBlog", method=RequestMethod.GET)
	public ResponseEntity<List<Blog>> getAllBlogs(){
		List<Blog> blog=blog1.getAllBlogs();
		if(blog.isEmpty()){
			return new ResponseEntity<List<Blog>>(blog, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Blog>>(blog, HttpStatus.OK);
	}
	
	
	// 2 Create New Blog By User........
	
	@RequestMapping(value="/blogCreateByUser/", method=RequestMethod.POST)
	public ResponseEntity<Blog> createBlog(@RequestBody Blog blog,HttpSession session){
		System.out.println("Create Blog");
		
		UserDetails user=(UserDetails) session.getAttribute("loggedInUser");
		
		
		blog.setBlogDate(new Date());
		blog.setBlogStatus("Pending");
		blog.setUserId(user.getUserId());
		blog.setBlogCountLike(0);
		blog.setBlogCommentCount(0);
		blog1.saveBlog(blog);
		System.out.println("blog" + blog.getBlogId());
		return new ResponseEntity<Blog>(blog,HttpStatus.OK);

		}
	// 3 Get a Single Blog Details By Blog Id.....
	
	@RequestMapping(value= "/blogDetails/{id}",method=RequestMethod.GET)
	public ResponseEntity<Blog>getBlogByBlogId(@PathVariable("id")int id){
		Blog blog = blog1.getBlogByBlogId(id);
		if (blog == null){
			blog = new Blog();
			blog.setErrorMessage("Blog does not exist with id : " + id);
				return new ResponseEntity<Blog>(blog, HttpStatus.NOT_FOUND);
				
		}
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}
	
	
	// 4 Update Blog ...By Blog Id....
	
	@RequestMapping(value="/updateBlogDetails/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Blog> updateblog(@PathVariable("id") int id, @RequestBody Blog blog ){
		
		
		if(blog1.getAllBlogs()==null){
			blog =new Blog();
			blog.setErrorMessage("blog does not exist with id : " +blog.getBlogId());
			return new ResponseEntity<Blog>(blog, HttpStatus.NO_CONTENT);
		}
		blog.setBlogStatus("Pending");
	    blog.setBlogDate(new Date(System.currentTimeMillis()));
	    
	    blog.setBlogCountLike(0);
		blog.setBlogCommentCount(0);
		blog1.updateBlog(blog);
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}
	
    // 5 Showing Approved Blog List........
	
	@RequestMapping(value= "/approvedBlogList",method=RequestMethod.GET)
	public ResponseEntity<List<Blog>>getAllApprovedBlogs(){
		List<Blog> blogs=blog1.getAllApproveBlogs();
		if(blogs.isEmpty()){
			return new ResponseEntity<List<Blog>>(blogs,HttpStatus.NO_CONTENT);
		}
		System.out.println(blogs.size());
		System.out.println("approved blogs displaying");
		return new ResponseEntity<List<Blog>>(blogs,HttpStatus.OK);
	}
	
	// 6 Showing UnApproved Blog List........
	
	@RequestMapping(value= "/unapprovedBlogList",method=RequestMethod.GET)
	public ResponseEntity<List<Blog>>getAllPendingBlogs(){
		List<Blog> blogs=blog1.getAllPendingBlogs();
		if(blogs.isEmpty()){
			return new ResponseEntity<List<Blog>>(blogs,HttpStatus.NO_CONTENT);
		}
		System.out.println(blogs.size());
		System.out.println("unapproved blogs displaying");
		return new ResponseEntity<List<Blog>>(blogs,HttpStatus.OK);
	}
	
	
    // 7 Blog Approved by Admin.......
	
	@RequestMapping(value="/approveBlog/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Blog>approveBlog(@PathVariable("id") int id,HttpSession session)
	{	
		
			Blog blog=blog1.getBlogByBlogId(id);
			if(((UserDetails)session.getAttribute("loggedInUser")).getRole().equals("ADMIN")&&
					(blog!=null))
			{
						blog.setBlogStatus("Approved");
						blog1.updateBlog(blog);
						return new ResponseEntity<Blog>(blog,HttpStatus.OK);
					}
			else
			{
						
						return new ResponseEntity<Blog>(blog,HttpStatus.NOT_FOUND);
					}
			
		}
	
	// 8 Blog Rejected by Admin.......
	
	@RequestMapping(value="/rejectBlog/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Blog>rejectBlog(@PathVariable("id") int id,HttpSession session)
	{	
		
			Blog blog=blog1.getBlogByBlogId(id);
			if(((UserDetails)session.getAttribute("loggedInUser")).getRole().equals("ADMIN")&&
					(blog!=null))
			{
						blog.setBlogStatus("Reject");
						blog1.updateBlog(blog);
						return new ResponseEntity<Blog>(blog,HttpStatus.OK);
					}
			else
			{
						
						return new ResponseEntity<Blog>(blog,HttpStatus.NOT_FOUND);
					}
			
		}
	
	
	// 9 Delete Blog By Blog Id.......
	
	@RequestMapping(value="/deleteBlog/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Blog>deleteBlog(@PathVariable("id")int id){
		Blog blog=blog1.getBlogByBlogId(id);
		if(blog == null){
			blog = new Blog();
			blog.setErrorMessage("Blog does not exist with id : " + id);
			return new ResponseEntity<Blog>(blog, HttpStatus.NOT_FOUND);
			
		}
		blog1.deleteBlog(blog);
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}
	
	
	//***********************************BLOG LIKE ******************************************************//
	//******************************************************************************************************//
	
	@RequestMapping(value="/likeBlog/{blogId}", method=RequestMethod.PUT)
	public ResponseEntity<Blog> likeBlog(@PathVariable("blogId") int blogId,HttpSession session) 
	{
		try
		{
			UserDetails user=(UserDetails) session.getAttribute("loggedInUser");
			System.out.println(user.getName());
			Blog blog = blog1.getBlogByBlogId(blogId);
			if (blog == null)
			{
				blog = new Blog();
				
				blog.setErrorMessage("No blog exist with id : " + blogId);
	
				return new ResponseEntity<Blog>(blog, HttpStatus.NOT_FOUND);
		}
		
			else if(blogd.isExist(blogId, user.getUserId()))
			{
				blog = new Blog();
				
				blog.setErrorMessage("User has already liked the blog: " + blogId);

				return new ResponseEntity<Blog>(blog, HttpStatus.NOT_FOUND);
			}
			
		blog.setBlogCountLike(blog.getBlogCountLike()+1);
		blog1.updateBlog(blog);
		BlogLike blogLike=new BlogLike();
		blogLike.setBlogId(blogId);blogLike.setUserId(user.getUserId());blogLike.setUserName(user.getName());
		blogLike.setBlogLikeDate(new Date(System.currentTimeMillis()));
		blogd.saveBlogLike(blogLike);
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
			Blog blog=new Blog();
			blog.setErrorMessage("not logged in");
			blog.setErrorCode("404");
			return new ResponseEntity<Blog>(blog, HttpStatus.NOT_FOUND);

		}
		
		
	}
		
		


	
	
	
	
//1. list of BlogLike by BlodId............!
	@RequestMapping(value="/bloglike/{blogId}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<BlogLike>> getBlogLikesByblogId(@PathVariable("blogId") int blogId,HttpSession session) {
		Blog blog = blog1.getBlogByBlogId(blogId);
		if (blog == null) {
			return new ResponseEntity<List<BlogLike>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<BlogLike>>(blogd.getBlogLikesByBlogId(blogId), HttpStatus.OK);
		
	}	
	
	
	
	@RequestMapping(value="/createNewBloglike/", method=RequestMethod.POST)
	public ResponseEntity<BlogLike> saveBlogLike(@RequestBody BlogLike bl,HttpSession session){
		System.out.println("Create Bloglike");
		
		UserDetails user=(UserDetails) session.getAttribute("loggedInUser");
		
		
		
		bl.setBlogLikeDate(new Date());
		
		bl.setUserName(user.getName());
		bl.setUserId(user.getUserId());
		blogd.saveBlogLike(bl);
		System.out.println("bloglike" + blog.getBlogId());
		return new ResponseEntity<BlogLike>(bl,HttpStatus.OK);
		
		
		}
	
	
	//***********************************BLOG COMMENT ******************************************************//
	//******************************************************************************************************//
	
	
	// 1 Display all BlogComments......
	
	@RequestMapping(value="/blogComments", method=RequestMethod.GET)
	public ResponseEntity<List<BlogComment>> getAllBlogComments(){
		List<BlogComment> blogcom=blogCommentDao.getAllBlogComments();
		if(blogcom.isEmpty()){
			return new ResponseEntity<List<BlogComment>>(blogcom, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<BlogComment>>(blogcom, HttpStatus.OK);
	}
	
	
	// 2 Create New BlogComments By User........
	
	@RequestMapping(value="/blogCommentsByUser/", method=RequestMethod.POST)
	public ResponseEntity<BlogComment> createBlogComment(@RequestBody BlogComment blogComment,HttpSession session){
		System.out.println("Create Blog");
		
		UserDetails user=(UserDetails) session.getAttribute("loggedInUser");
		
		
		blogComment.setBlogCommentDate(new Date());
		
		blogComment.setUserId(user.getUserId());
		blogComment.setUserName(user.getName());
		blogCommentDao.save(blogComment);
		System.out.println("blogComment" + blogComment.getBlogId());
		return new ResponseEntity<BlogComment>(blogComment,HttpStatus.OK);
				
		}
	
	// 3 Get details of a single blogcomment By Blog Id.................
	
	@RequestMapping(value= "/blogCommentDetails/{id}",method=RequestMethod.GET)
	public ResponseEntity<BlogComment>getByBlogCommentId(@PathVariable("id")int id){
		BlogComment blogcom = blogCommentDao.getByBlogCommentId(id);
		if (blogcom == null){
			blogcom = new BlogComment();
			blogcom.setErrorMessage("BlogComment does not exist with id : " + id);
				return new ResponseEntity<BlogComment>(blogcom, HttpStatus.NOT_FOUND);
				
		}
		return new ResponseEntity<BlogComment>(blogcom, HttpStatus.OK);
	}
	
	// 4 BlogComments List of A User By User Id.....
	
	@RequestMapping(value= "/BlogCommentsByUserId/{id}",method=RequestMethod.GET)
	public ResponseEntity<List<BlogComment>>listByUserId(@PathVariable("id") String id){
		List<BlogComment> blogs=blogCommentDao.listByUserId(id);
		if (blogs == null){
			blogComment.setErrorMessage("BlogComment does not exist with id : " + id);
				return new ResponseEntity<List<BlogComment>>(blogs, HttpStatus.NOT_FOUND);
				
		}
		return new ResponseEntity<List<BlogComment>>(blogs, HttpStatus.OK);
	}
	
	
	// 5 List of BlogComments Of Single Blog By Blog Id...........
	
@RequestMapping(value= "/blogCommentbyblogId/{id}", method=RequestMethod.GET)
	
	public ResponseEntity<List<BlogComment>> listByBlogId(@PathVariable("id")int id){
		List<BlogComment> blogComments=blogCommentDao.listByBlogId(id);
		if(blogComments.isEmpty()){
			blogComment.setErrorMessage("BlogComment does not exist with id : " +id);
			return new ResponseEntity<List<BlogComment>>(blogComments,HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<BlogComment>>(blogComments,HttpStatus.OK);
	}


      // 6 Update Blog Comment By Comment Id.....

      @RequestMapping(value="/updateBlogComments/{id}", method=RequestMethod.PUT)
      public ResponseEntity<BlogComment> updateBlogComment(@PathVariable("id") int id, @RequestBody BlogComment blogComment,HttpSession session){
    	 // UserDetails user=(UserDetails) session.getAttribute("loggedInUser");
	        if(blogCommentDao.getAllBlogComments()==null){
	        	blogComment =new BlogComment();
		         blogComment.setErrorMessage("BlogComment does not exist with id : " +id);
		       return new ResponseEntity<BlogComment>(blogComment, HttpStatus.NO_CONTENT);
	        }
	        
	       // blogComment.setUserId(user.getUserId()); Pending For Next Authentication...
	        blogComment.setBlogCommentDate(new Date(System.currentTimeMillis()));
    
	        blogCommentDao.update(blogComment);
	        return new ResponseEntity<BlogComment>(blogComment, HttpStatus.OK);
}        
      
      
      // 7 Delete BlogComment By BlogComment Id........
      
      @RequestMapping(value="/deleteBlogComment/{id}", method=RequestMethod.DELETE)
  	public ResponseEntity<BlogComment>deleteBlogComment(@PathVariable("id")int id){
  		BlogComment blogComment=blogCommentDao.getByBlogCommentId(id);
  		if(blogComment == null){
  			blogComment = new BlogComment();
  			blogComment.setErrorMessage("BlogComment does not exist with id : " + id);
  			return new ResponseEntity<BlogComment>(blogComment, HttpStatus.NOT_FOUND);
  			
  		}
  		blogCommentDao.delete(blogComment);
  		return new ResponseEntity<BlogComment>(blogComment, HttpStatus.OK);
  	}
	
	
}




