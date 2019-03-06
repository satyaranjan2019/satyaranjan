package com;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MyServletDemo
 */
@WebServlet("/MyServletDemo")
public class MyServletDemo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyServletDemo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String u=request.getParameter("Username");
		String p=request.getParameter("Password");
		Login Sc=new Login();
		Sc.setUsername(u);
		Sc.setPassword(p);
		
		if(Sc.check())
		{
			RequestDispatcher r=request.getRequestDispatcher("Successful.jsp");
			request.setAttribute("user", u);			
			r.forward(request, response);
		}
		else
		{
			RequestDispatcher r=request.getRequestDispatcher("Failur.jsp");
			r.forward(request, response);
		}
	}

}
