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
import org.springframework.web.bind.annotation.RestController;

import com.niit.Weebly.Dao.ForumCommentDao;
import com.niit.Weebly.Dao.ForumDao;
import com.niit.Weebly.Model.ForumComment;
import com.niit.Weebly.Model.Forum;
import com.niit.Weebly.Model.UserDetails;


@RestController
public class ForumController {
	@Autowired
	Forum forum;
	@Autowired
	ForumDao forumDao;
	@Autowired
	UserDetails userDetails;
	@Autowired
	ForumComment forumComment;
	@Autowired
	ForumCommentDao forumCommentDao;
	
	
	
	
	//***************************************FORUM*****************************************************//
	//*************************************************************************************************//
	
	
	
	
	
	
	// 1 Showing all list of Forums....
	
	@RequestMapping(value="/forums", method=RequestMethod.GET)
	public ResponseEntity<List<Forum>> getAllForums(){
		List<Forum> form=forumDao.getAllForums();
		if(form.isEmpty()){
			return new ResponseEntity<List<Forum>>(form, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Forum>>(form, HttpStatus.OK);
	}
	
	
	// 2 Create New Forum  By User.......
	 
	@RequestMapping(value="/ForumCreateByUser/", method=RequestMethod.POST)
	public ResponseEntity<Forum> createForum(@RequestBody Forum forum,HttpSession session){
		System.out.println("Create Forum");
		
		UserDetails user=(UserDetails) session.getAttribute("loggedInUser");
		
		
		forum.setForumCreationDate(new Date(System.currentTimeMillis()));
		forum.setForumStatus("Pending");
		forum.setUserId(user.getUserId());
		forum.setUserName(user.getName());
		forumDao.saveForum(forum);
		System.out.println("forum" + forum.getForumId());
		return new ResponseEntity<Forum>(forum,HttpStatus.OK);
				
		}
	
	// 3 Getting a single Forum Details by Forum Id......
	
	@RequestMapping(value= "/forumDetails/{id}",method=RequestMethod.GET)
	public ResponseEntity<Forum>getForumByForumId(@PathVariable("id")int id){
		Forum forum = forumDao.getForumByForumId(id);
		if (forum == null){
			forum = new Forum();
			forum.setErrorMessage("Forum does not exist with id : " + id);
				return new ResponseEntity<Forum>(forum, HttpStatus.NOT_FOUND);
				
		}
		return new ResponseEntity<Forum>(forum, HttpStatus.OK);
	}
	
	// 4 Update Forum Details...By Forum Id....
	
		@RequestMapping(value="/updateForum/{id}", method=RequestMethod.PUT)
		public ResponseEntity<Forum> updateforum(@PathVariable("id") int id, @RequestBody Forum forum){
			
			
			if(forumDao.getAllForums()==null){
				forum =new Forum();
				forum.setErrorMessage("Forum does not exist with id : " +forum.getForumId());
				return new ResponseEntity<Forum>(forum, HttpStatus.NO_CONTENT);
			}
			forum.setForumCreationDate(new Date(System.currentTimeMillis()));
			forum.setForumStatus("Pending");
			
			forumDao.updateForum(forum);
			return new ResponseEntity<Forum>(forum, HttpStatus.OK);
		}
		
		// 5 Showing Approved Forum List........
		
		@RequestMapping(value= "/approvedForumList",method=RequestMethod.GET)
		public ResponseEntity<List<Forum>>getAllApprovedForums(){
			List<Forum> forums=forumDao.getAllApprovedForums();
			if(forums.isEmpty()){
				return new ResponseEntity<List<Forum>>(forums,HttpStatus.NO_CONTENT);
			}
			System.out.println(forums.size());
			System.out.println("approved forums displaying");
			return new ResponseEntity<List<Forum>>(forums,HttpStatus.OK);
		}
		
		// 6 Showing UnApproved Forum List........
		
		@RequestMapping(value= "/unapprovedForumList",method=RequestMethod.GET)
		public ResponseEntity<List<Forum>>getAllRejectedForums(){
			List<Forum> forums=forumDao.getAllRejectedForums();
			if(forums.isEmpty()){
				return new ResponseEntity<List<Forum>>(forums,HttpStatus.NO_CONTENT);
			}
			System.out.println(forums.size());
			System.out.println("unapproved forums displaying");
			return new ResponseEntity<List<Forum>>(forums,HttpStatus.OK);
		}
		
		
	    // 7 Forum Approved by Admin.......
		
		@RequestMapping(value="/approveForum/{id}", method=RequestMethod.PUT)
		public ResponseEntity<Forum>approveForum(@PathVariable("id") int id,HttpSession session)
		{	
			
				Forum forum=forumDao.getForumByForumId(id);
				if(((UserDetails)session.getAttribute("loggedInUser")).getRole().equals("ADMIN")&&
						(forum!=null))
				{
							forum.setForumStatus("Approved");
							forumDao.updateForum(forum);
							return new ResponseEntity<Forum>(forum,HttpStatus.OK);
						}
				else
				{
							
							return new ResponseEntity<Forum>(forum,HttpStatus.NOT_FOUND);
						}
				
			}
		
		// 8 Forum Rejected by Admin.......
		
		@RequestMapping(value="/rejectForum/{id}", method=RequestMethod.PUT)
		public ResponseEntity<Forum>rejectForum(@PathVariable("id") int id,HttpSession session)
		{	
			
				Forum forum=forumDao.getForumByForumId(id);
				if(((UserDetails)session.getAttribute("loggedInUser")).getRole().equals("ADMIN")&&
						(forum!=null))
				{
							forum.setForumStatus("Reject");
							forumDao.updateForum(forum);
							return new ResponseEntity<Forum>(forum,HttpStatus.OK);
						}
				else
				{
							
							return new ResponseEntity<Forum>(forum,HttpStatus.NOT_FOUND);
						}
				
			}
		
		
		// 9 Delete Forum By Using Forum Id.....
		
		@RequestMapping(value="/deleteForum/{id}", method=RequestMethod.DELETE)
		public ResponseEntity<Forum>deleteForum(@PathVariable("id")int id){
			Forum forum=forumDao.getForumByForumId(id);
			if(forum == null){
				forum = new Forum();
				forum.setErrorMessage("Job does not exist with id : " + id);
				return new ResponseEntity<Forum>(forum, HttpStatus.NOT_FOUND);
				
			}
			forumDao.deleteForum(forum);
			return new ResponseEntity<Forum>(forum, HttpStatus.OK);
		}
		
		
		//**********************************FORUM COMMENT*********************************************************//
        //*******************************************************************************************//
		
		
		// 1 Showing all list of ForumComments....

		@RequestMapping(value="/forumcomment", method=RequestMethod.GET)
		public ResponseEntity<List<ForumComment>> getAllForumComments(){
			List<ForumComment> formcom=forumCommentDao.getAllForumComments();
			if(formcom.isEmpty()){
				return new ResponseEntity<List<ForumComment>>(formcom, HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<ForumComment>>(formcom, HttpStatus.OK);
		}
		
		// 2 Create New ForumComment  By User.......
		
		@RequestMapping(value="/newForumComment/", method=RequestMethod.POST)
		public ResponseEntity<ForumComment> createForumComment(@RequestBody ForumComment forumcom,HttpSession session){
			System.out.println("Create ForumComment");
			
			UserDetails user=(UserDetails) session.getAttribute("loggedInUser");
			
			
			forumcom.setForumCommentDate(new Date(System.currentTimeMillis()));
			forumcom.setUserId(user.getUserId());
			forumcom.setUserName(user.getName());
			forumCommentDao.save(forumcom);
			System.out.println("forumcom" + forumcom.getForumId());
			return new ResponseEntity<ForumComment>(forumcom,HttpStatus.OK);
					
			}
		
		// 3 Getting a single forum comment by forum comment Id.....
		
		@RequestMapping(value= "/forumCommentDetails/{id}",method=RequestMethod.GET)
		public ResponseEntity<ForumComment>getByForumCommentId(@PathVariable("id")int id){
			ForumComment forumcom = forumCommentDao.getByForumCommentId(id);
			if (forumcom == null){
				forumcom = new ForumComment();
				forumcom.setErrorMessage("ForumComment does not exist with id : " + id);
					return new ResponseEntity<ForumComment>(forumcom, HttpStatus.NOT_FOUND);
					
			}
			return new ResponseEntity<ForumComment>(forumcom, HttpStatus.OK);
		}
		
		
		
		//ForumComments List of A User By User Id.....
		
		@RequestMapping(value= "/ForumCommentsByUserId/{id}",method=RequestMethod.GET)
		public ResponseEntity<List<ForumComment>>listByUserId(@PathVariable("id") String id){
			List<ForumComment> forums=forumCommentDao.listByUserId(id);
			if (forums == null){
				forumComment.setErrorMessage("ForumComment does not exist with id : " + id);
					return new ResponseEntity<List<ForumComment>>(forums, HttpStatus.NOT_FOUND);
					
			}
			return new ResponseEntity<List<ForumComment>>(forums, HttpStatus.OK);
		}
		
		//List of ForumComments Of Single Forum By Forum Id...........
		
		@RequestMapping(value= "/forumCommentbyforumId/{id}", method=RequestMethod.GET)
		
		public ResponseEntity<List<ForumComment>> listByForumId(@PathVariable("id")int id){
			List<ForumComment> forums=forumCommentDao.listByForumId(id);
			if(forums.isEmpty()){
				forumComment.setErrorMessage("ForumComment does not exist with id : " +id);
				return new ResponseEntity<List<ForumComment>>(forums,HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<List<ForumComment>>(forums,HttpStatus.OK);
		}
		
		
		// 9 Delete Forum Comment By Using Forum Id.....
		
				@RequestMapping(value="/deleteForumComment/{id}", method=RequestMethod.DELETE)
				public ResponseEntity<ForumComment>delete(@PathVariable("id")int id){
					ForumComment forums=forumCommentDao.getByForumCommentId(id);
					if(forums == null){
						forums = new ForumComment();
						forums.setErrorMessage("Job does not exist with id : " + id);
						return new ResponseEntity<ForumComment>(forums, HttpStatus.NOT_FOUND);
						
					}
					forumCommentDao.delete(forums);
					return new ResponseEntity<ForumComment>(forums, HttpStatus.OK);
				}
		
		
}
