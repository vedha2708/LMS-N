package com.exam.portal.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.exam.portal.Model.AdminUser;
import com.exam.portal.Repository.AdminUserRepo;
import com.exam.portal.Repository.OrganizationRepository;
import com.exam.portal.Repository.ProgramRepository;


@Controller

public class AdminController {

    @Autowired
    OrganizationRepository orgrepo;

	@Autowired
	AdminUserRepo adminUserRepo;
	
	@Autowired
	ProgramRepository programRepository;

    @GetMapping("admindashboard")
    public String showadminDashboard(Model model) {
		System.out.println("admin dashboard");
        	int examcount=0;
		Object user=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		long organizationCount = orgrepo.count();
		// Add the organization count to the model
        model.addAttribute("organizationCount", organizationCount);
		model.addAttribute("examcount",examcount);
		return "organiser/dashboard";
    }
    
	@GetMapping("organizationdashboard")
    public String showOrgDashboard(Model model) {
		System.out.println("organization dashboard");
      	return "organization_dashboard";
    }

	@GetMapping("userdashboard")
    public String showUserDashboard(Model model,Principal principal) {
		String email=principal.getName();

		AdminUser user=adminUserRepo.findByEmail(email);
		String name=user.getName();
		model.addAttribute("name", name);

		System.out.println("user dashboard");
      	return "userdashboard";
    }

	





}
