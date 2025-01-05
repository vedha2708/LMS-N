package com.exam.portal.Controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.exam.portal.Model.AdminUser;
import com.exam.portal.Model.Course;
import com.exam.portal.Model.Program;
import com.exam.portal.Model.Topic;
import com.exam.portal.Model.modules;
import com.exam.portal.Repository.AdminUserRepo;
import com.exam.portal.Repository.CourseRepository;
import com.exam.portal.Repository.moduleRepository;
import com.exam.portal.Repository.topicRepository;

import jakarta.validation.Valid;

@Controller
public class moduleController {

	@Autowired
	moduleRepository repo;

	@Autowired
	topicRepository topicrepo;

	@Autowired
	AdminUserRepo adminuserrepo;
	
	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	JdbcTemplate template;
	
	@GetMapping("/organiser/module")
    public String showModules(Model model ){
	
		List<modules> m = repo.findAll();

		model.addAttribute("m",m);
		model.addAttribute("topicname", topicrepo.findAll());
		
		 return "organiser/module/mview";
    }
	
	@GetMapping("/organiser/createModule")
	public String createModules(Model model, modules module){
		
		model.addAttribute("course", courseRepository.findAll());
		model.addAttribute("module", new modules());
		model.addAttribute("topiclist", topicrepo.findAll());
		return "organiser/module/module";
	}

	@PostMapping("/registerModule")
	public String RegisterModule(@Valid @ModelAttribute("module") modules module, BindingResult bindingResult, Model model, Principal principal, RedirectAttributes redirectAttributes){
		if (bindingResult.hasErrors()) {
			 redirectAttributes.addFlashAttribute("module", module);
		     redirectAttributes.addFlashAttribute("moduleErrors", bindingResult);
		     return "redirect:/organiser/createModule";
        } else {
		AdminUser creator=adminuserrepo.findByEmail(principal.getName());
		module.setCreatorId(creator.getId());
		module.setCreateddate(new Date());
		
		
		repo.save(module);
		return "redirect:/organiser/module";
	}
	}
	
	// Delete item from Config Campaign
	@RequestMapping(value = "/organiser/deletemodule", method = RequestMethod.GET)
	public String deleteConfig(@RequestParam("cid") int[] configId) {
		System.out.println("hello");
		String configIds = Arrays.toString(configId).replace("[", "").replace("]", "");
		template.execute("DELETE FROM enrolled_modules WHERE module_id IN(" + configIds + ");");
		template.execute("DELETE FROM enrolled_topic WHERE module_id IN(" + configIds + ");");
		template.execute("DELETE FROM assignment WHERE module_id IN(" + configIds + ");");
		template.execute("DELETE FROM module WHERE module_id IN(" + configIds + ");");
		
		// programrepo.deleteProgram(Arrays.asList(configId));

		return "redirect:/organiser/module";
	}
	
	@GetMapping("/module/view/{id}")
	public String viewModule(@PathVariable("id") Integer moduleId, Model model) {

		modules module = repo.getReferenceById(moduleId);
		System.out.println(module);
		if (module != null) {
			model.addAttribute("org", module);
		} else {
			System.out.println("not shown view");
		}
		return "organiser/module/mviewlist";
	}
	
	@GetMapping("/organiser/module/edit")
    public String editModule(@RequestParam(name = "id") Integer id, Model model) {
        Optional<modules> module = repo.findByModuleId(id);
		
        if (module.isPresent()) {
            modules modules = module.get();
			
            
            model.addAttribute("modules", modules);
			model.addAttribute("topicList", topicrepo.findAll());
            return "organiser/module/edit_module"; // Assuming this is your edit page
        } else {
            // Handle program not found case
            return "redirect:/organiser/module"; // Assuming you have a Thymeleaf template named programNotFound.html
        }
	
}


@PostMapping("/organiser/module/edit")
public String saveEditedCourse(@ModelAttribute("modules") modules module,
        @RequestParam("selectedTopic") List<Integer> selectedTopic,
        Principal principal) {
		

		Optional<modules> optionalModule = repo.findByModuleId(module.getModuleId());
		if (optionalModule.isPresent()) {
			// AdminUser adminuser = adminUserRepo.findByEmail(principal.getName());
			modules existingModule = optionalModule.get();
			existingModule.setModuleName(module.getModuleName());
			existingModule.setWeightage(module.getWeightage());
			existingModule.setModuleDescription(module.getModuleDescription());
			existingModule.setCreateddate(new Date());

			// Update the courses for the program
			List<Topic> updatedTopics = topicrepo.findAllById(selectedTopic);
			existingModule.setTopics(updatedTopics);

			repo.save(existingModule);
		} else {
			// Handle the case where the program with the given ID does not exist
			// Redirect or show an error message
			return "redirect:/organiser/dashboard";
		}

		// Redirect to a confirmation page or another appropriate page
		return "redirect:/organiser/module";
	}


}