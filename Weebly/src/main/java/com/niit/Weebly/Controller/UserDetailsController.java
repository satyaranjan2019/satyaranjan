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

import com.niit.Weebly.Dao.UserDetailsDao;
import com.niit.Weebly.Model.UserDetails;

@RestController
public class UserDetailsController {
	
	@Autowired
	UserDetailsDao userDetailsDao;
	@Autowired
	UserDetails userDetails;
	
	
	
	
	//Showing all user List.......
	@RequestMapping(value="/listOfUsers", method=RequestMethod.GET)
	public ResponseEntity<List<UserDetails>> getAllUser(){
		List<UserDetails> list=userDetailsDao.getAllUser();
		if(list.isEmpty()){
			return new ResponseEntity<List<UserDetails>>(list, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<UserDetails>>(list, HttpStatus.OK);

	}

	//SignUp process for User.....
	@RequestMapping(value="/createUserId/", method=RequestMethod.POST)
	public ResponseEntity<UserDetails> createUser(@RequestBody UserDetails users){
		if(userDetailsDao.getUserByUserId(users.getUserId())==null){
			users.setRole("USER");
			users.setIsOnline("NO");
			users.setStatus("Active");
			userDetailsDao.saveUser(users);
			return new ResponseEntity<UserDetails>(users,HttpStatus.OK);
		}
		users.setErrorMessage("User already exist with id : "+users.getUserId());
		return new ResponseEntity<UserDetails>(users, HttpStatus.OK);
	}
	
	//Login for User......
	@RequestMapping(value = "/loginUser/authenticate/", method = RequestMethod.POST)
	public ResponseEntity<UserDetails> UserAuthentication(@RequestBody UserDetails users, HttpSession session){
		users = userDetailsDao.UserAuthentication(users.getUserId(), users.getPassword());
		if(users == null){
			users = new UserDetails();
			users.setErrorMessage("Invalid userId or password...");
		}
		else {
			session.setAttribute("loggedInUser", users);
		    
			System.out.println("logged session set ");
			session.setAttribute("loggedInUserID", users.getUserId());
		}
		users.setIsOnline("YES");
		users.setStatus("Active");
		userDetailsDao.updateUser(users);
		return new ResponseEntity<UserDetails>(users, HttpStatus.OK);
	}
	
	//Getting all information of a single user.....
	@RequestMapping(value= "/userDetails/{id}",method=RequestMethod.GET)
	public ResponseEntity<UserDetails>getUserByUserId(@PathVariable("id")String id){
		UserDetails users = userDetailsDao.getUserByUserId(id);
		if (users == null){
			users = new UserDetails();
			users.setErrorMessage("User does not exist with id : " + id);
				return new ResponseEntity<UserDetails>(users, HttpStatus.NOT_FOUND);
				
		}
		return new ResponseEntity<UserDetails>(users, HttpStatus.OK);
	}
	
	//Update User Details...By User Id....
	@RequestMapping(value="/updateProfile/{id}", method=RequestMethod.PUT)
	public ResponseEntity<UserDetails> updateuser(@PathVariable("id") String id, @RequestBody UserDetails users){
		if(userDetailsDao.getAllUser()==null){
			users =new UserDetails();
			users.setErrorMessage("User does not exist with id : " +users.getUserId());
			return new ResponseEntity<UserDetails>(users, HttpStatus.NO_CONTENT);
		}
		userDetailsDao.updateUser(users);
		return new ResponseEntity<UserDetails>(users, HttpStatus.OK);
	}
	
	//User LogOut.......
	
	 
	@RequestMapping(value= "/userlogout/{id}",method=RequestMethod.PUT)
	public ResponseEntity<UserDetails>logout(@PathVariable("id") String id ,@RequestBody UserDetails users,HttpSession session){
		System.out.println("hello  "+users.getName());	
		users.setIsOnline("NO");
		
		userDetailsDao.updateUser(users);
		
			session.invalidate();
			 
			
				return new ResponseEntity<UserDetails>(new UserDetails(), HttpStatus.OK);
				
		}

	@RequestMapping(value= "/profileDetails/{id}",method=RequestMethod.GET)
	public ResponseEntity<UserDetails>getUserById(@PathVariable("id")String id,HttpSession session){
		UserDetails users = userDetailsDao.getUserById(id);
		if (users == null){
			users = new UserDetails();
			users.setErrorMessage("User does not exist with id : " + id);
				return new ResponseEntity<UserDetails>(users, HttpStatus.NOT_FOUND);
				
		}
		return new ResponseEntity<UserDetails>(users, HttpStatus.OK);
	}
}
