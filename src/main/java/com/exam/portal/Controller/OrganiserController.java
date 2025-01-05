package com.exam.portal.Controller;

import com.exam.portal.Repository.AdminUserRepo;
import com.exam.portal.Repository.ExamRepository;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.exam.portal.Model.AdminUser;
import com.exam.portal.Model.Organiser;
import com.exam.portal.Repository.OrganiserRepository;
import com.exam.portal.Repository.OrganizationRepository;

import jakarta.validation.Valid;

@Controller
public class OrganiserController {

	@Autowired
	OrganiserRepository repo;

	@Autowired
	ExamRepository examRepository;
	
	@Autowired
	OrganizationRepository orgrepo;

	@Autowired
	AdminUserRepo adminuserrepo;


	public static String LOGIN_ROUTE="redirect:/organiserlogin";

	@GetMapping("organiser/register")
	public String register(Model model) {
		model.addAttribute("organiser",new Organiser());
		return "organiser/register";
	}
	
	@PostMapping("organiser/register")
	public String registerPost(@Valid Organiser org, BindingResult bindingResult) {
		 if (bindingResult.hasErrors()) {
		        
		        return "organiser/register"; 
		    }
		//BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
		String encodedPassword=new Encrypt().encode(org.getPassword());
		org.setPassword(encodedPassword);
		AdminUser adminuser=new AdminUser();
		adminuser.setCreatedate(new Date());
		adminuser.setEmail(org.getEmail());
		adminuser.setName(org.getName());
		adminuser.setPassword(encodedPassword);
		adminuser.setRights("admin");
		adminuser.setRole("admin");
		repo.save(org);
		adminuserrepo.save(adminuser);
		return "redirect:/organiser/dashboard";
	}
	
	@GetMapping("organiser/dashboard")
	public String login() {
		
		return "redirect:/organiser/dashboard1";
	}
	
	@GetMapping("organiser/dashboard1")
	public String dashboard(Model model) {
		System.out.println("organiser/dashboardiuhnkj");
		int examcount=0;
		Object user=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		/*if(user instanceof OrganiserDetails){
			Organiser org = ((OrganiserDetails) user).getOrg();
			      
			examcount = examRepository.findByOrganiserId(org.getId()).size();
		}*/
		long organizationCount = orgrepo.count();
		// Add the organization count to the model
        model.addAttribute("organizationCount", organizationCount);
		model.addAttribute("examcount",examcount);
		//System.out.println("dsad");
		return "organiser/dashboard";
	}
}
