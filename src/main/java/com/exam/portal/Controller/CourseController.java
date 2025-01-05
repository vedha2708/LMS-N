package com.exam.portal.Controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;


import com.exam.portal.Model.AdminUser;
import com.exam.portal.Model.Assignment;
import com.exam.portal.Model.Course;
import com.exam.portal.Model.Program;
import com.exam.portal.Model.Topic;
import com.exam.portal.Model.modules;
import com.exam.portal.Repository.AdminUserRepo;
import com.exam.portal.Repository.CourseRepository;
import com.exam.portal.Repository.ProgramRepository;
import com.exam.portal.Repository.moduleRepository;
import com.exam.portal.Repository.topicRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CourseController {


	

	@Autowired
	CourseRepository repo;
	@Autowired
	ProgramRepository programrepo;
	@Autowired
	AdminUserRepo adminUserRepo;

	@Autowired
	moduleRepository modulerepo;
	@Autowired
	topicRepository topicRepository;
	
	@Autowired
	JdbcTemplate template;

	@GetMapping("/programs/{id}")
	public String getEnrolledCourses(@PathVariable("id") Integer programId, Model model) {
		Optional<Program> optionalProgram = programrepo.findById(programId);
		if (optionalProgram.isPresent()) {
			Program program = optionalProgram.get();
			List<Course> courses = program.getCourses();
//			List<Integer> courseIds = courses.stream().map(Course::getCourseId).collect(Collectors.toList());
//			List<String> courseNames = courses.stream().map(Course::getCourseName).collect(Collectors.toList());
			
			List<Course> course = repo.findAll();
			model.addAttribute("courses", course);
//			model.addAttribute("courseIds", courseIds);
//			model.addAttribute("courseNames", courseNames);
			return "courses";
		} else {
			// Handle program not found case
			return "userdashboard";
		}
	}

	@GetMapping("/course/{id}")
	public String getModulesByCourseId(@PathVariable("id") Integer courseId, Model model) {
		Optional<Course> optionalCourse = repo.findByCourseId(courseId);
		if (optionalCourse.isPresent()) {
			Course course = optionalCourse.get();
			
			List<modules> modules = course.getModules();
			List<Assignment> assignments = new ArrayList<>();
        
        // Iterate over each module
        for (modules module : course.getModules()) {
            // Iterate over each topic in the module
            for (Topic topic : module.getTopics()) {
                // Add assignments linked to each topic
                assignments.addAll(topic.getAssignments());
            }
        }
			// List<Assignment> assignments = modules.get;
			model.addAttribute("courseName", course.getCourseName());
			model.addAttribute("courseDescription", course.getCourseDescription());
			model.addAttribute("modules", modules);
			model.addAttribute("assignments", assignments);
			model.addAttribute("courseId", course.getCourseId());
			// model.addAttribute("assignments", assignments);
			
			return "courses-details"; // Assuming this is your view name
			// return "simple";
		} else {
			// Handle course not found case
			return "redirect:/userdashboard"; // Redirect to home page or handle appropriately
		}
	}
	// Delete item from Config Campaign
		@RequestMapping(value = "/organiser/delcourse", method = RequestMethod.GET)
		public String deleteConfig(@RequestParam("cid") int[] configId) {
			System.out.println("hello");
			String configIds = Arrays.toString(configId).replace("[", "").replace("]", "");
			template.execute("DELETE FROM enrolled_courses WHERE course_id IN(" + configIds + ");");
			template.execute("DELETE FROM enrolled_modules WHERE course_id IN(" + configIds + ");");
			template.execute("DELETE FROM course WHERE course_id IN(" + configIds + ");");
			
			// programrepo.deleteProgram(Arrays.asList(configId));

			return "redirect:/organiser/course";
		}
	
		@GetMapping("/course/view/{id}")
		public String viewModule(@PathVariable("id") Integer moduleId, Model model) {

			Course course = repo.getReferenceById(moduleId);
			System.out.println(course);
			if (course != null) {
				model.addAttribute("org", course);
			} else {
				System.out.println("not shown view");
			}
			return "organiser/course/coursepreview";
		}
	@GetMapping("/organiser/course")
	public String viewNodal(Model model) {

		List<String> categories = repo.findDistinctCourseCategory();
		model.addAttribute("categories", categories);
		List<Course> course = repo.findAll();
		model.addAttribute("course", course);

		return "organiser/course/cview";
	}

	@GetMapping("/organiser/cour")
	public String viewCourseByCategory(Model model, @RequestParam(required = false) String category) {
		List<String> categories = repo.findDistinctCourseCategory();
		model.addAttribute("categories", categories);
		List<Course> course;
		if (category != null && !category.isEmpty()) {
			// course = repo.findByCourseCategory(category);
			course = repo.findAll();
		} else {
			course = repo.findAll();
		}
		model.addAttribute("course", course);
		return "organiser/course/cview";
	}

	@GetMapping("/organiser/course/new")
	public String newCourse(Model model) {
		List<String> categories = repo.findDistinctCourseCategory();
		model.addAttribute("categories", categories);
		model.addAttribute("modulelist", modulerepo.findAll());
		model.addAttribute("course", new Course());
		return "organiser/course/courseCreate";
	}
	 @GetMapping("/cource/images/{imageName:.+}")
	    public ResponseEntity<Resource> viewImage(@PathVariable String imageName) throws IOException {
	        String uploadDir1 = "course/";
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


	 @PostMapping(path="/createCourse", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	    public String createcourse(@Valid @ModelAttribute("course") Course course,
	    		BindingResult bindingResult, 
	    		RedirectAttributes redirectAttributes,
	    		@RequestParam("image_input") MultipartFile imagefile) {
	        // Perform server-side validation
	        if (bindingResult.hasErrors()) {
	            // If there are validation errors, redirect back to the form page with error messages
	            redirectAttributes.addFlashAttribute("course", course);
	            redirectAttributes.addFlashAttribute("courseErrors", bindingResult);
	            return "redirect:/organiser/course/new"; // Change the redirect URL if needed
	        } else {
	            // Set created date
	            course.setCreatedDate(new Date());
	            
	            try {
		            String imagefile1 = course.getCourseName();
		            course.setImage(imagefile1);
		            // Save the program to the database
		            repo.save(course);
		            
		            String uploadDir = "course/";
		            FileUploadUtil.saveFile(uploadDir, imagefile1, imagefile);
		          
		          
		            return "redirect:/organiser/course";
		        } catch (Exception e) {
		            e.printStackTrace();
		            redirectAttributes.addFlashAttribute("errorMessage", "Error uploading files. Please try again.");
		            redirectAttributes.addFlashAttribute("user", course);
		            return "organiser/organization";
		        }
	            
	          
	        }
	    }

	@GetMapping("/organiser/course/edit")
	public String editCourse(@RequestParam(name = "id") Integer id, Model model) {
		Optional<Course> course = repo.findByCourseId(id);

		if (course.isPresent()) {
			Course courses = course.get();

			model.addAttribute("courses", courses);
			model.addAttribute("moduleList", modulerepo.findAll());
			return "organiser/course/edit_course"; // Assuming this is your edit page
		} else {
			// Handle program not found case
			return "redirect:/organiser/course"; // Assuming you have a Thymeleaf template named programNotFound.html
		}

	}

	@PostMapping("/organiser/course/edit")
	public String saveEditedCourse(@ModelAttribute("courses") Course course,
			@RequestParam("selectedModule") List<Integer> selectedModules,
			Principal principal) {
		

		Optional<Course> optionalCourse = repo.findByCourseId(course.getCourseId());
		if (optionalCourse.isPresent()) {
			// AdminUser adminuser = adminUserRepo.findByEmail(principal.getName());
			Course existingCourse = optionalCourse.get();
			existingCourse.setCourseName(course.getCourseName());
			existingCourse.setCourseDescription(course.getCourseDescription());
			existingCourse.setCreatedDate(new Date());
			existingCourse.setCourseAbbrevation(course.getCourseAbbrevation());

			// Update the courses for the program
			List<modules> updatedModules = modulerepo.findAllById(selectedModules);
			existingCourse.setModules(updatedModules);

			repo.save(existingCourse);
		} else {
			// Handle the case where the program with the given ID does not exist
			// Redirect or show an error message
			return "redirect:/organiser/dashboard";
		}

		// Redirect to a confirmation page or another appropriate page
		return "redirect:/organiser/course";
	}

}
