package com.niit.Weebly.Controller;

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

import com.niit.Weebly.Dao.FriendDao;
import com.niit.Weebly.Dao.UserDetailsDao;
import com.niit.Weebly.Model.Friend;
import com.niit.Weebly.Model.UserDetails;


@RestController
public class FriendController {
	
	@Autowired
	Friend friend;
	
	@Autowired
	FriendDao friendDao;
	
	@Autowired
	UserDetailsDao userdetailsDao;
	
	
	
	
// Show My all Friend List........
	
	@RequestMapping(value = "/Friends" , method=RequestMethod.GET)

	public ResponseEntity<List<Friend>>getMyFriends(HttpSession session) {
		System.out.println("getmyFriends() method");
		UserDetails user=(UserDetails) session.getAttribute("loggedInUser");
		List<Friend> Friends = friendDao.getMyFriends(user.getUserId());
		System.out.println("retrieving friends ");
		return new ResponseEntity<List<Friend>> (Friends, HttpStatus.OK);
		}
	
	//
	

	@RequestMapping(value = "/addFriend/{friendId}" , method=RequestMethod.POST)			
	public ResponseEntity<Friend> sendFriendRequest(@PathVariable("friendId") String friendId, HttpSession session) {
		System.out.println("Starting of sendFriendRequest() method");
		Friend frnd=new Friend();
		UserDetails user = (UserDetails) session.getAttribute("loggedInUser");
		
		Friend f=friendDao.get(user.getUserId(), friendId);
		
		System.out.println(user.getUserId()+"   "+friendId);
		if(f==null)
		{
			frnd.setUserId(user.getUserId());
		
			frnd.setFriendId(friendId);
			frnd.setStatus("N");
			frnd.setIsOnline(userdetailsDao.getUserByUserId(friendId).getIsOnline());
		friendDao.save(frnd);
		}
		
		else
		{
			f.setStatus("N");
			friendDao.update(f);
		}
		System.out.println("End of sendFriendRequest() method");
		return new ResponseEntity<Friend> (frnd, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/acceptFriend/{friendId}"  , method=RequestMethod.PUT)			
	public ResponseEntity<Friend> acceptFriendRequest(@PathVariable("friendId") String friendId, HttpSession session) {
		UserDetails loggedInUser = (UserDetails) session.getAttribute("loggedInUser");
		String userId=loggedInUser.getUserId();
		Friend friend = friendDao.getRequest(userId, friendId);
		friend.setUserId(loggedInUser.getUserId());
		friend.setFriendId(userId);
		friend.setUserId(friendId);
		friend.setStatus("A");	// N = New, A = Accepted, R = Rejected, U = Unfriend 
		friendDao.update(friend);
		return new ResponseEntity<Friend> (friend, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/unFriend/{friendId}" , method=RequestMethod.PUT)			
	public ResponseEntity<Friend> unFriend(@PathVariable("friendId") String friendId, HttpSession session) {
		UserDetails loggedInUser = (UserDetails) session.getAttribute("loggedInUser");
		String userId=loggedInUser.getUserId();
		Friend friend = friendDao.get(userId, friendId);
		friend.setUserId(loggedInUser.getUserId());
		friend.setFriendId(friendId);
		friend.setStatus("U");	// N = New, A = Accepted, R = Rejected, U = Unfriend 
		friendDao.update(friend);
		return new ResponseEntity<Friend> (friend, HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "/rejectFriend/{friendId}"  , method=RequestMethod.PUT)				
	public ResponseEntity<Friend> rejectFriendRequest(@PathVariable("friendId") String friendId, HttpSession session) {
		System.out.println("Starting of rejectFriendRequest() method");
		UserDetails loggedInUser = (UserDetails) session.getAttribute("loggedInUser");
		String userId=loggedInUser.getUserId();
		Friend friend = friendDao.get(userId, friendId);
		friend.setUserId(loggedInUser.getUserId());
		friend.setFriendId(friendId);
		friend.setStatus("R");	// N = New, A = Accepted, R = Rejected, U = Unfriend  
		friendDao.update(friend);
		System.out.println("End of rejectFriendRequest() method");
		return new ResponseEntity<Friend> (friend, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/newFriendRequest"  , method=RequestMethod.GET)
	public ResponseEntity<List<Friend>>newFriendRequest(HttpSession session) {
		UserDetails user=(UserDetails)session.getAttribute("loggedInUser");
		List<Friend> friend=friendDao.getNewFriendRequests(user.getUserId());
		return new ResponseEntity<List<Friend>>(friend,HttpStatus.OK);	
	}

}
