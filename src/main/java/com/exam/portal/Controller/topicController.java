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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.exam.portal.Model.AdminUser;
import com.exam.portal.Model.Course;
import com.exam.portal.Model.Program;
import com.exam.portal.Model.Topic;
import com.exam.portal.Model.modules;
import com.exam.portal.Repository.moduleRepository;
import com.exam.portal.Repository.topicRepository;

import jakarta.validation.Valid;


@Controller
public class topicController {
	@Autowired
	topicRepository repo;
	
	@Autowired
	moduleRepository modulerepo;
	
	@GetMapping("/organiser/topic")
	public String showTopic(Model model) {
		
	List<Topic> org = repo.findAll();
	model.addAttribute("topics", org);
		return "organiser/topic/tlist";	
	}
	
	@GetMapping("/organiser/newtopic")
	public String newTopic(Model model) {
		model.addAttribute("topic", new Topic());
		return "organiser/topic/Topic";	
	}
	
	// Delete item from Config Campaign
	@RequestMapping(value = "/organiser/deletetopic", method = RequestMethod.GET)
	public String deleteConfig(@RequestParam("cid") String[] configId) {
		System.out.println("hello");
		repo.deltopic(Arrays.asList(configId));
		
		return "redirect:/organiser/topic";
	}
	
	
	 @PostMapping("/upload")
	    public String saveUser(@Valid @ModelAttribute("topic") Topic topic,BindingResult result,
	    		Model model, RedirectAttributes redirectAttributes ,       
	            @RequestParam("videofile") MultipartFile videofile,
	            @RequestParam("transcriptfile") MultipartFile transcriptfile, 
				@RequestParam("pdffile") MultipartFile pdffile ) throws IOException {
		 if (result.hasErrors()) {
			 redirectAttributes.addFlashAttribute("topic", topic);
		     redirectAttributes.addFlashAttribute("topicErrors", result);
		     
		     return "redirect:/organiser/newtopic";
     } else {
	       try {
	    	   
	    	       	  
	    //    String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
	         String videofile1 = StringUtils.cleanPath(videofile.getOriginalFilename());
	         String transcriptfile1=StringUtils.cleanPath(transcriptfile.getOriginalFilename());
			 String pdffile1=StringUtils.cleanPath(pdffile.getOriginalFilename());
	         topic.setVideoFile(videofile1);
			 topic.setTranscriptFile(transcriptfile1);
			 topic.setPdfFile(pdffile1);
	         
	         repo.save(topic);
	         
	       
	        String uploadDir1 = "user-files/" ;
  
	        FileUploadUtil.saveFile(uploadDir1, videofile1, videofile);
			FileUploadUtil.saveFile(uploadDir1, transcriptfile1, transcriptfile);
	        FileUploadUtil.saveFile(uploadDir1, pdffile1, pdffile);

	        
	    	model.addAttribute("topic", new Topic());
			//session.setAttribute("message", new Message("Succesfully Registered !!!!","alert-success"));
			   
	        return "redirect:/organiser/topic";
	       }
	       catch (Exception e) {
			
	    	   e.printStackTrace();
				model.addAttribute("user", topic);
					
				return "organiser/organization";
		}
     }
	    }
//	 @GetMapping("/organiser/topic/edit")
//		public String editProgram(@RequestParam(name = "id") int sequenceNo, Model model) {
//			Optional<Topic> optionalTopic = repo.findById(sequenceNo);
//
//			if (optionalTopic.isPresent()) {
//				Topic topic = optionalTopic.get();
//
//				model.addAttribute("topic", topic);
//				
//				return "organiser/topic/edit_topic"; // make this page
//			} else {
//				// Handle program not found case
//				return "redirect:/organiser/course"; 
//			}
//
//		}
//
//		@PostMapping("/organiser/program/edit")
//		public String saveEditedProgram(@ModelAttribute("topic") Topic topic,
//				@RequestParam("selectedmodules") List<Integer> selectedmodules,
//				Principal principal) {
//			if (topic.getSequenceNo() == null){
//			
//				return "redirect:/organiser/dashboard";
//			}
//
//			Optional<Topic> optionalTopic = repo.findById(topic.getSequenceNo());
//			if (optionalTopic.isPresent()) {
//				
//				Topic existingTopic = optionalTopic.get();
//				existingTopic.setTopicName(topic.getTopicName());
//				existingTopic.setTopicDescrpition(topic.getTopicDescrpition());
//				existingTopic.setVideoFile(topic.getVideoFile());
//				existingTopic.setTranscriptFile(topic.getTranscriptFile());
//				existingTopic.setPdfFile(topic.getPdfFile());
//				
//				
//
//				// Update the courses for the program
//				List<modules> updatedmodules = modulerepo.findAllById(selectedmodules);
//				existingTopic.setModules(updatedmodules);
//
//				repo.save(existingTopic);
//			} else {
//				// Handle the case where the program with the given ID does not exist
//				// Redirect or show an error message
//				return "redirect:/organiser/dashboard";
//			}
//
//			// Redirect to a confirmation page or another appropriate page
//			return "redirect:/organiser/course";
//		}

}
