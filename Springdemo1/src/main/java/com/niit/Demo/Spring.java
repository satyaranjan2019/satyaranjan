package com.niit.Demo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class Spring {
@RequestMapping("/Welcome")
	public String display()
	{
		return "WelcomePage";
	}	
}
