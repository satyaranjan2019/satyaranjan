package com.niit.Weebly.DaoImpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.Weebly.Dao.BlogDao;
import com.niit.Weebly.Model.Blog;

@Repository("blogDao")
@Transactional
public class BlogDaoImpl implements BlogDao{
private SessionFactory sessionFactory;
	
	public BlogDaoImpl(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.openSession();
	}
    // function 1 of contrller 
	public List<Blog> getAllBlogs() {
		Session session = getSession();
		Query query=session.createQuery("from Blog");
		List<Blog> bloglist=query.list();
		return bloglist;
	}

	public boolean saveBlog(Blog blog) {
		try
		{
			Session session = getSession();

			session.save(blog);

			session.flush(); 

			session.close();
			
			return true;
		}
		catch(HibernateException e)
		{
			return false;
		}
	}
   //Pending in Controller
	
	public boolean deleteBlog(Blog blog) {
		try
		{
			Session session = getSession();

			session.delete(blog);

			session.flush(); 

			session.close();
			
			return true;
		}
		catch(HibernateException e)
		{
			return false;
		}
	}

	// Function 4 of the controller 
	
	public boolean updateBlog(Blog blog) {
		try
		{
			Session session = getSession();

			session.update(blog);

			session.flush(); 

			session.close();
			
			return true;
		}
		catch(HibernateException e)
		{
			return false;
		}
	}

	//Pending in Controller
	
	public Blog getBlogByBlogId(int blogId) {
		Session session = getSession();
		Query query=session.createQuery("FROM Blog a where blogId=:blogId");
		query.setParameter("blogId", blogId);
		Blog  blog =(Blog)query.uniqueResult();
		session.close();
		return blog;
	}

	// Function 5 of the controller
	
	public List<Blog> getAllApproveBlogs() {
		Session session=getSession();
		Query query=session.createQuery("FROM Blog a where blogStatus=:blogStatus");
		query.setParameter("blogStatus", "Approved");
		List<Blog> bloglist=query.list();
		session.close();
		return bloglist;
	}

	// Function 6 of the controller
	
	public List<Blog> getAllPendingBlogs() {
		Session session=getSession();
		Query query=session.createQuery("FROM Blog a where blogStatus=:blogStatus");
		query.setParameter("blogStatus", "Pending");
		List<Blog> bloglist=query.list();
		session.close();
		return bloglist;
	}

}
