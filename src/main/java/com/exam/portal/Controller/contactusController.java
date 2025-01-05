package com.exam.portal.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.exam.portal.Model.Organiser;
import com.exam.portal.Model.contactus;
import com.exam.portal.Repository.contactusrepo;

import jakarta.servlet.http.HttpSession;


@Controller
public class contactusController {
	
	@Autowired
	contactusrepo contactrepo;
	
	
	
	@PostMapping("/Contact")
	public String savecontactus(@ModelAttribute contactus contactus,HttpSession session) {
		System.out.println("message sent sucessfulluy"+contactus);
		contactrepo.save(contactus);
		session.setAttribute("message","Message sent Sucessfully");
		return "redirect:/userdashboard";
	}
	

}
