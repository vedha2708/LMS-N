package com.exam.portal.Controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.exam.portal.Model.AdminUser;
import com.exam.portal.Model.Organization;
import com.exam.portal.Repository.AdminUserRepo;
import com.exam.portal.Repository.OrganizationRepository;
import com.exam.portal.Utils.RandomString;

import jakarta.validation.Valid;

@Controller
public class OrganizationController {
	@Autowired
	JavaMailSender javaMailSender;
	@Autowired
	AdminUserRepo adminUserRepo;

	public OrganizationRepository getRepo() {

		return repo;
	}

	public void setRepo(OrganizationRepository repo) {
		this.repo = repo;
	}

	@Autowired
	OrganizationRepository repo;

	
    @GetMapping("/trainee_signup")
    public String TraineeSignUp(Model model) {
        List<Organization> organizations = repo.findAll();// Fetch all organizations from the database
        List<String> organizationNames = organizations.stream()
                                    .map(Organization::getOrganization_name)
                                    .collect(Collectors.toList()); // Extract organization names
        model.addAttribute("organizationNames", organizations);
       
        return "sign-up.html";
    } 


	@GetMapping("/organiser/organization")
	public String showOrg(Model model) {

		List<Organization> org = repo.findAll();
		model.addAttribute("org", org);
		return "organiser/organization/org";
	}

	@GetMapping("/organiser/organization/new")
	public String newOrg() {

		return "organiser/organization/createOrg";
	}

	@PostMapping("/organiser/organization/register")
	public String registerOrganization(@Valid @ModelAttribute("org") Organization organization, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) {
	    if (bindingResult.hasErrors()) {
	       
	        redirectAttributes.addFlashAttribute("org", organization);
	        redirectAttributes.addFlashAttribute("orgErrors", bindingResult);
	        return "redirect:/organiser/organization/new";
	    } else {
	   
         organization.setRegistered_date(new Date());
         AdminUser org=new AdminUser();
		 org.setCreatedate(new Date());
		 org.setName(organization.getOrganization_name());
		 org.setEmail(organization.getEmail_id());
		 org.setRole("organization");
		 adminUserRepo.save(org);
		// Save the organization to the database
		repo.save(organization);
		try {
		     AdminUser admin= adminUserRepo.findByEmail(principal.getName());
		    
		//System.out.println("Authorities="+admin.getRole());
		if(admin.getRole().equals("admin") || admin.getRole().equals("superadmin")) {
			return "redirect:/organiser/organization";
		}
		else {
			return "redirect:/sign-up.html";
		}
		}
		catch(Exception e) {
			return "redirect:/sign-up.html";
		}
		// Redirect to a success page or return a success message
	}
	}

	@PostMapping("/organiser/organization/accept")
	public String acceptOrganization(@RequestParam("orgId") int orgId) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		String password= RandomString.getAlphaNumericString(24);
		
		Organization organization = repo.findByOrganizationId(orgId);
         AdminUser adminuser=adminUserRepo.findByEmail(organization.getEmail_id());
		 adminuser.setPassword(new Encrypt().sha256(password));
		 adminUserRepo.save(adminuser);
		if (organization != null) {
			organization.setStatus(true);
			repo.save(organization);

			String user_email = organization.getEmail_id();

			mailMessage.setFrom("vedant2709patel@gmail.com");
			mailMessage.setTo(user_email);
			mailMessage.setSubject("Organization Registration");
			mailMessage.setText("Your organization password is : "+password);
			try {

				javaMailSender.send(mailMessage);
			} catch (MailException e) {
				System.out.println("Mail not sent to : " + user_email);
			}
			return "redirect:/organiser/organization"; // Redirect to the organization page
		} else {
			return "404"; // Handle the case where organization is not found
		}
	}

	@PostMapping("/organiser/organization/discard")
	public String discardOrganization(@RequestParam("orgId") int orgId) {
		Organization organization = repo.findByOrganizationId(orgId);
		if (organization != null) {
			organization.setStatus(false);
			repo.save(organization);
			return "redirect:/organiser/organization"; // Redirect to the organization page
		} else {
			return "error"; // Handle the case where organization is not found
		}
	}

	// Delete item from Config Campaign
	@RequestMapping(value = "/organiser/delcampaign", method = RequestMethod.GET)
	public String deleteConfig(@RequestParam("cid") String[] configId) {
		System.out.println("hello");
		repo.delOrg(Arrays.asList(configId));
		adminUserRepo.deleteOrg(Arrays.asList(configId));
		return "redirect:/organiser/organization";
	}
	

	@GetMapping("/organiser/organization/view/{id}")
	public String viewOrganization(@PathVariable("id") Integer id, Model model) {

		Organization org = repo.findByOrganizationId(id);
		System.out.println(org);
		if (org != null) {
			model.addAttribute("org", org);
		} else {
			System.out.println("not shown view");
		}
		return "organiser/organization/oview";
	}


	@GetMapping("/organiser/org/edit")
	public String editExam(@RequestParam(name = "id") Integer id, Model model) {
		Organization org = repo.findByOrganizationId(id);
		model.addAttribute("org", org);
		return "organiser/organization/updateOrg";
	}

	@PostMapping("/organiser/org/edit")
	public String editSaveOrg(Organization org) {

		Organization org1 = repo.findByOrganizationId(org.getOrganizationId());
		if (org1 != null) {
			// Update the properties of the retrieved organization object
			// with the properties of the org object passed as a parameter
			// (Assuming that you want to update the existing organization)
			org1.setOrganization_name(org.getOrganization_name());
			org1.setDescription(org.getDescription());
			// Update other properties as needed
			
			// Save the updated organization object back to the repository
			repo.save(org1);
		} else {
			System.out.println("does not print ");
			// Handle the case where the organization is not found
			// You may want to log an error message or handle it differently
		}
	
		// Redirect to the appropriate page after saving
		return "redirect:/organiser/organization";

	}
	
	@GetMapping("/orgprofile.html")
	public String Profile(Model model,Principal principal) {

		String email=principal.getName();
        System.out.println("email="+email);
        Organization user=repo.findByEmail_Id(email);
		model.addAttribute("user", user);
		return "profile";
	}
}