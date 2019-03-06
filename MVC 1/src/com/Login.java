package com;

public class Login 
{
	String Username;
	String Password;
	public String getUsername()
	{
		return Username;
	}
	public void setUsername(String Username)
	{
		this.Username = Username;
	}
	public String getPassword()
	{
		return Password;
	}
	public void setPassword(String Password)
	{
		this.Password = Password;
	}
	public boolean check()
	{
		if(Username.equals("NIIT")&& Password.equals("1111"))
			return true;
		else
			return false;
	}
			
	}
	
	
	
	
	
	
	
	
	

