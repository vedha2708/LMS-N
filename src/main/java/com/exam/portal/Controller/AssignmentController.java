package com.exam.portal.Controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.exam.portal.Model.AdminUser;
import com.exam.portal.Model.Assignment;
import com.exam.portal.Model.Course;
import com.exam.portal.Model.NodalOfficer;
import com.exam.portal.Model.Program;
import com.exam.portal.Model.Topic;
import com.exam.portal.Model.modules;
import com.exam.portal.Repository.AdminUserRepo;
import com.exam.portal.Repository.AssignmentRepo;
import com.exam.portal.Repository.moduleRepository;
import com.exam.portal.Repository.topicRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class AssignmentController {
	
	
	
	@Autowired
	moduleRepository modulerepo;
	
	@Autowired
	topicRepository topicrepo;
	@Autowired
	AdminUserRepo adminuserrepo;
	@Autowired
	AssignmentRepo assignmentrepo;
	
	@GetMapping("/organiser/Assignment")
	public String Assignment(Model model) {
		
	List<Assignment> ass = assignmentrepo.findAll();
	model.addAttribute("assignments", ass);
	model.addAttribute("modulename", modulerepo.findAll());
	model.addAttribute("topicname", topicrepo.findAll());
	
		return "organiser/Assignment/assview";	
	}

	@GetMapping("/organiser/Assignment/new")
	public String newassignment(Model model) {
		model.addAttribute("assignment", new Assignment());
		model.addAttribute("topiclist", topicrepo.findAll());
		model.addAttribute("modulelist", modulerepo.findAll());
		return "/organiser/Assignment/createAssignment";	
	}
	


	 @PostMapping("/registerAssignment")
	    public String saveUser(Assignment assignment,BindingResult result,
				Model model,          
	             @RequestParam("uploadassignment") MultipartFile uploadassignment 
				 ) throws IOException {
	       try {  
	    	       	  
	    //    String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
	         
	         String uploadassignment1=StringUtils.cleanPath(uploadassignment.getOriginalFilename());
			
			
			 assignment.setUploadassignment(uploadassignment1);
			
	         
			 assignmentrepo.save(assignment);
	         
	       
	        String uploadDir1 = "user-files/" ;
  
	       
			FileUploadUtil.saveFile(uploadDir1, uploadassignment1, uploadassignment);
	       

	        
	    	model.addAttribute("assignment", new Assignment());
			//session.setAttribute("message", new Message("Succesfully Registered !!!!","alert-success"));
			   
	        return "redirect:/organiser/Assignment";
	       }
	       catch (Exception e) {
			
	    	   e.printStackTrace();
				model.addAttribute("user", assignment);
					
				return "organiser/organization";
		}
	    }
		// @PostMapping("/uploadpdf")
	    // public String uploadpdfuser(Assignment assignment,BindingResult result,
		// 		Model model,          
	    //          @RequestParam("viewassignment") MultipartFile viewassignment 
		// 		 ) throws IOException {
	    //    try {  
	    	       	  
	    // //    String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
	         
	    //      String viewassignment1=StringUtils.cleanPath(viewassignment.getOriginalFilename());
			
			
		// 	 assignment.setViewassignment(viewassignment1);
			
	         
		// 	 assignmentrepo.save(assignment);
	         
	       
	    //     String uploadDir1 = "user-files/" ;
  
	       
		// 	FileUploadUtil.saveFile(uploadDir1, viewassignment1, viewassignment);
	       

	        
	    // 	model.addAttribute("assignment", new Assignment());
		// 	//session.setAttribute("message", new Message("Succesfully Registered !!!!","alert-success"));
			   
	    //     return "redirect:/course/{id}";
	    //    }
	    //    catch (Exception e) {
			
	    // 	   e.printStackTrace();
		// 		model.addAttribute("user", assignment);
					
		// 		return "organiser/organization";
		// }
	    // }


	 @PostMapping("/uploadpdf/{courseId}")
		public String saveEditedAssignment(@RequestParam("id") Integer id, Model model,
		@PathVariable("courseId") Integer courseId,
		        @RequestParam("viewassignment") MultipartFile viewassignment,
				Principal principal) {
			
					System.out.println("Inside uploadPDF");
					System.out.println(id);
					try {
						
						
						String viewassignment1 = StringUtils.cleanPath(viewassignment.getOriginalFilename());
				
						// assignment.setViewassignment(viewassignment1);
				
						// assignmentrepo.save(assignment);
				
						String uploadDir1 = "user-files/";
				
						FileUploadUtil.saveFile(uploadDir1, viewassignment1, viewassignment);

			Optional<Assignment> optionalAssignment = assignmentrepo.findById(id);
			if (optionalAssignment.isPresent()) {
				// AdminUser adminuser = adminUserRepo.findByEmail(principal.getName());
				Assignment existingAssignment = optionalAssignment.get();
				existingAssignment.setViewassignment(viewassignment1);
				

				assignmentrepo.save(existingAssignment);
			} else {
				// Handle the case where the program with the given ID does not exist
				// Redirect or show an error message
				return "organiser/organization";
			}

			// Redirect to a confirmation page or another appropriate page
			return "redirect:/course/{courseId}";
		}catch (Exception e) {
	        e.printStackTrace();
	        // model.addAttribute("assignments", assignment);
	        // Redirect to an appropriate error page or show an error message
	        return "organiser/organization";
	    }
	}

	@RequestMapping(value = "/organiser/deleteassignment", method = RequestMethod.GET)
	public String deleteConfig(@RequestParam("cid") String[] configId) {
		System.out.println("hello");
		assignmentrepo.delass(Arrays.asList(configId));
		
		return "redirect:/organiser/Assignment";
	}

}
