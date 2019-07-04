package com.niit.Weebly.DaoImpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.Weebly.Dao.UserDetailsDao;
import com.niit.Weebly.Model.UserDetails;

@Repository("usersDetailsDao")
@Transactional
public class UserDetailsDaoImpl implements UserDetailsDao{

    private SessionFactory sessionFactory;
	
	public  UserDetailsDaoImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.openSession();
	}


	public boolean saveUser(UserDetails user) {
		try
		{
			Session session = getSession();

			session.save(user);

			session.flush(); 

			session.close();
			
			return true;
		}
		catch(HibernateException e)
		{
			return false;
		}
	}

	public boolean updateUser(UserDetails user) {
		try
		{
			Session session = getSession();

			session.update(user);

			session.flush(); 

			session.close();
			
			return true;
		}
		catch(HibernateException e)
		{
			return false;
		}
	}

	public boolean deleteUser(UserDetails user) {
		try
		{
			Session session = getSession();

			session.delete(user);

			session.flush(); 

			session.close();
			
			return true;
		}
		catch(HibernateException e)
		{
			return false;
		}
		
	}

	public List<UserDetails> getAllUser() {
		Session session = getSession();
		Query query=session.createQuery("from UserDetails");
		List<UserDetails> userlist=(List<UserDetails>)query.list();
		session.close();
		return userlist;
		
	}
	
	
	public UserDetails UserAuthentication(String userId, String userPassword) {
		Session session=getSession();
		Query query=session.createQuery("FROM UserDetails w where userId=:userId and password=:password");
		query.setParameter("userId", userId);
		query.setParameter("password", userPassword);
		UserDetails w=(UserDetails)query.uniqueResult();
		if( w==null)
		{
		 return w;
				 
		}
		 else
		 {
			
		 return w;
		 }
		
	}

	public UserDetails getUserByUserId(String id) {
		Session session=getSession();
		Query query=session.createQuery("FROM UserDetails where userId=:userId");
		query.setParameter("userId", id);
		UserDetails u=(UserDetails)query.uniqueResult();
		session.close();
		return u;
	}

	public UserDetails getUserById(String id) {
		Session session=getSession();
		Query query=session.createQuery("FROM UserDetails where userId=:userId");
		query.setParameter("userId", id);
		UserDetails u=(UserDetails)query.uniqueResult();
		session.close();
		return u;
	}
	
}

