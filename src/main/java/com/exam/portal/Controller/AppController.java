package com.exam.portal.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AppController {

	@GetMapping("/")
	public String index() {
		return "index1";
	}
	@GetMapping("/organiserlogin")
	public String login() {
		return "log-in.html";
	}

	
	
	@GetMapping("/contact.html")
	public String Contact() {
		return "contact.html";
	}
	@GetMapping("/sign-up.html")
	public String SignUpOrganization() {
		return "organization_signup.html";
	}
	
	@GetMapping("/abcd")
	public String testing() {
		return "abc.html";
	}

	


	

	
	@GetMapping("/exam")
	public String exam() {
		return "index";
	}
	
	@GetMapping("/about")
	public String about() {
		return "about";
	}
	
//	@GetMapping("/contact-us")
//	public String contactUs() {
//		return "contact";
//	}
//	
	
	@GetMapping("organiser/assign")
	public String Showroles() {
		return "organiser/assign_roles";
	}
}