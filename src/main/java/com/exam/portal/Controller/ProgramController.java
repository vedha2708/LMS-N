package com.exam.portal.Controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.exam.portal.Model.AdminUser;
import com.exam.portal.Model.Course;
import com.exam.portal.Model.NodalOfficer;
import com.exam.portal.Model.Organization;
import com.exam.portal.Model.Program;
import com.exam.portal.Repository.AdminUserRepo;
import com.exam.portal.Repository.NodalOfficerRepository;
import com.exam.portal.Repository.ProgramRepository;

import jakarta.validation.Valid;

import com.exam.portal.Repository.CourseRepository;

@Controller
// @RequestMapping("/admin")
public class ProgramController {

	@Autowired
	ProgramRepository programrepo;

	@Autowired
	AdminUserRepo adminuserrepo;

	@Autowired
	NodalOfficerRepository nodalofficerrepo;

	@Autowired
	CourseRepository courserepo;

	@Autowired
	JdbcTemplate template;

	@GetMapping("/enroll_program")
	public String EnrollProgram(Model model) {

		List<Program> program = programrepo.findAll();
		model.addAttribute("program", program);
		return "enroll_program";
	}

	@GetMapping("/programview")
	public String viewProgram(Model model) {
		model.addAttribute("programs", programrepo.findAll());
		return "organiser/program/program_view";
	}

	@GetMapping("/programcreate")
	public String createProgram(Model model) {
		model.addAttribute("program", new Program());
		model.addAttribute("courselist", courserepo.findAll());
		return "organiser/program/program_create";
	}
	@PostMapping(path="/programcreate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String registerProgram(@Valid @ModelAttribute("program") Program program, 
			BindingResult bindingResult, Principal principal, 
			RedirectAttributes redirectAttributes,
			@RequestParam("image_input") MultipartFile imagefile) {
	    // Perform server-side validation
	    if (bindingResult.hasErrors()) {
	        // If there are validation errors, redirect back to the form page with error messages
	        redirectAttributes.addFlashAttribute("program", program);
	        redirectAttributes.addFlashAttribute("programErrors", bindingResult);
	        
	        return "redirect:/programcreate";
	    } 
	    else {
	        // Set creator id and creation date
	        AdminUser adminUser = adminuserrepo.findByEmail(principal.getName());
	        program.setCreatorid(adminUser.getId());
	        program.setCreateddate(new Date());
	        program.setStatus("approved");
	        
	        // If the user is a nodal officer, set nodal officer id and organization id
	        if (adminUser.getRole().equals("nodalofficer")) {
	            NodalOfficer nodalOfficer = nodalofficerrepo.findByEmail(adminUser.getEmail());
	            program.setNodalofficerid(nodalOfficer.getId());
	            program.setOrganizationid(nodalOfficer.getOrganizationid());
	        }
	        try {
	            String imagefile1 = program.getName();
	            
	           
	            
	            program.setImage(imagefile1);
	           
	            // Save the program to the database
	            programrepo.save(program);
	            
	            String uploadDir = "program/";
	            FileUploadUtil.saveFile(uploadDir, imagefile1, imagefile);
	          
	          
	            return "redirect:/programview";
	        } catch (Exception e) {
	            e.printStackTrace();
	            redirectAttributes.addFlashAttribute("errorMessage", "Error uploading files. Please try again.");
	            redirectAttributes.addFlashAttribute("user", program);
	            return "organiser/organization";
	        }
	        // Save the program to the database
	       
	    }
	}
	@GetMapping("/images/{imageName:.+}")
    public ResponseEntity<Resource> viewImage(@PathVariable String imageName) throws IOException {
        String uploadDir1 = "program/";
        Path imagePath = Paths.get(uploadDir1, imageName);
        Resource resource = new UrlResource(imagePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + imageName + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

	@GetMapping("/program/view/{id}")
	public String viewProgram(@PathVariable("id") Integer id, Model model) {

		Program program = programrepo.getReferenceById(id);
		System.out.println(program);
		if (program != null) {
			model.addAttribute("org", program);
		} else {
			System.out.println("not shown view");
		}
		return "organiser/program/programpreview";
	}
	

	// Delete item from Config Campaign
	@RequestMapping(value = "/programdelcampaign", method = RequestMethod.GET)
	public String deleteConfig(@RequestParam("cid") Integer[] configId) {
		System.out.println("hello");
		String configIds = Arrays.toString(configId).replace("[", "").replace("]", "");
		template.execute("DELETE FROM enrolled_courses WHERE program_id IN(" + configIds + ");");
		template.execute("DELETE FROM program WHERE program.id IN(" + configIds + ");");
		// programrepo.deleteProgram(Arrays.asList(configId));

		return "redirect:/programview";
	}

	@GetMapping("/organiser/program/edit")
	public String editProgram(@RequestParam(name = "id") Integer id, Model model) {
		Optional<Program> optionalProgram = programrepo.findById(id);

		if (optionalProgram.isPresent()) {
			Program program = optionalProgram.get();

			model.addAttribute("program", program);
			model.addAttribute("courselist", courserepo.findAll());
			return "organiser/program/edit_program"; // Assuming this is your edit page
		} else {
			// Handle program not found case
			return "redirect:/programview"; // Assuming you have a Thymeleaf template named programNotFound.html
		}

	}

	@PostMapping("/organiser/program/edit")
	public String saveEditedProgram(@ModelAttribute("program") Program program,
			@RequestParam("selectedCourses") List<Integer> selectedCourses,
			Principal principal) {
		if (program.getId() == null) {
			// Handle the case where the ID is null
			// Redirect or show an error message
			return "redirect:/organiser/dashboard";
		}

		Optional<Program> optionalProgram = programrepo.findById(program.getId());
		if (optionalProgram.isPresent()) {
			AdminUser adminuser = adminuserrepo.findByEmail(principal.getName());
			Program existingProgram = optionalProgram.get();
			existingProgram.setName(program.getName());
			existingProgram.setDescription(program.getDescription());
			existingProgram.setDuration(program.getDuration());
			existingProgram.setCreatorid(adminuser.getId());
			existingProgram.setCreateddate(new Date());
			existingProgram.setStatus("Pending");

			// Update the courses for the program
			List<Course> updatedCourses = courserepo.findAllById(selectedCourses);
			existingProgram.setCourses(updatedCourses);

			programrepo.save(existingProgram);
		} else {
			// Handle the case where the program with the given ID does not exist
			// Redirect or show an error message
			return "redirect:/organiser/dashboard";
		}

		// Redirect to a confirmation page or another appropriate page
		return "redirect:/programview";
	}

}