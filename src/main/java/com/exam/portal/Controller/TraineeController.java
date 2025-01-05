package com.exam.portal.Controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.exam.portal.Model.AdminUser;
import com.exam.portal.Model.Assignment;
import com.exam.portal.Model.Organization;
import com.exam.portal.Model.Program;
import com.exam.portal.Model.trainee;
import com.exam.portal.Repository.AdminUserRepo;
import com.exam.portal.Repository.OrganizationRepository;
import com.exam.portal.Repository.ProgramRepository;
import com.exam.portal.Repository.traineeRepository;
import com.exam.portal.Service.TraineeService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;





@Controller
public class TraineeController {

    @Autowired
    traineeRepository traineeRepository;
    
    @Autowired
	AdminUserRepo adminUserRepo;
    
    @Autowired
    TraineeService traineeService;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    ProgramRepository programRepository;
    
    @Autowired
	JdbcTemplate template;
    
//    @GetMapping("/organiser/user")
//    public String traineeView(Model model) {
//        List<trainee> trainee= traineeRepository.findAll();
//        model.addAttribute("trainee",trainee);
//        return "/organiser/trainee/trainee_view";
//    }
    @GetMapping("/organiser/user")
    public String showRegisteredUserstoAdmin(Model model) {

        model.addAttribute("users", traineeRepository.findAll());
        return "organiser/trainee/trainee_view";
    }
    
	@GetMapping("/organiser/user/new")
	public String newtraniee(Model model) {
		model.addAttribute("trainee", new trainee());
		model.addAttribute("traineelist", traineeRepository.findAll());
		return "/organiser/trainee/create_trainee";
	}
	

	 @PostMapping("/createtrainee")
	    public String savetrainee(trainee trainee,BindingResult result,
				Model model,          
	             @RequestParam("aadharcardfile") MultipartFile uploadaadharcardfile,
	             @RequestParam("idcardphoto") MultipartFile uploadidcardphoto
				 ) throws IOException {
	       try {  
	    	       	  
	    //    String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
	         
	         String uploadaadharcard1=StringUtils.cleanPath(uploadaadharcardfile.getOriginalFilename());
	         String uploadidcardphoto1=StringUtils.cleanPath(uploadidcardphoto.getOriginalFilename());
			
	         trainee.setAadharcardfile(uploadaadharcard1);
	         trainee.setIdcardphoto(uploadidcardphoto1);
	         
	         traineeRepository.save(trainee);
	         
	       
	        String uploadDir1 = "user-files/" ;

	       
			FileUploadUtil.saveFile(uploadDir1, uploadaadharcard1, uploadaadharcardfile);
			FileUploadUtil.saveFile(uploadDir1, uploadidcardphoto1, uploadidcardphoto);

	        
	    	model.addAttribute("trainee", new trainee());
			//session.setAttribute("message", new Message("Succesfully Registered !!!!","alert-success"));
			   
	        return "redirect:/organiser/user";
	       }
	       catch (Exception e) {
			
	    	   e.printStackTrace();
				model.addAttribute("user", trainee);
					
				return "organiser/organization";
		}
	    }

    
    
    @PostMapping("/trainee_sign_up")
    public String TraineeSignup(@ModelAttribute trainee trainee) {
        AdminUser org=new AdminUser();
        org.setCreatedate(new Date());
        org.setName(trainee.getName());
        org.setEmail(trainee.getEmail());
        org.setPassword(new Encrypt().sha256(trainee.getPassword()));
        org.setRole("trainee");
        adminUserRepo.save(org);
        traineeRepository.save(trainee);
        
        return "redirect:/organiserlogin";
    }

//    @GetMapping("/profile.html")
//	public String Profile(Model model,Principal principal) {
//
//		String email=principal.getName();
//        System.out.println("email="+email);
//        trainee user=traineeRepository.findByEmail(email);
//		model.addAttribute("user", user);
//		return "profile";
//	}
    @GetMapping("/userprofile.html")
  	public String Profile(Model model,Principal principal) {

  		String email=principal.getName();
          System.out.println("logged in tainee email======"+email);
          trainee user=traineeRepository.findByEmail(email);
         // System.out.println(user+"user");
  		model.addAttribute("user", user);
  		return "trainee_profile";
  	}
    
    
    @PostMapping("/editTrainee")
    public String editTraineefromProfile(Model model, @ModelAttribute("user") trainee trainee, @RequestParam("id") Integer id, MultipartHttpServletRequest req, HttpServletRequest request) throws Exception {
        System.out.println("tarine:" + trainee.getId());
        Optional<trainee> user = traineeRepository.findById(id);

        if (user.isPresent()) {
            trainee user1 = user.get();

            MultipartFile photoOneFile = req.getFile("photoone");
            if (photoOneFile != null && !photoOneFile.isEmpty()) {
                user1.setPhoto(traineeService.uploadfile(photoOneFile));
            } else {
                user1.setPhoto(request.getParameter("photoone"));
            }

            MultipartFile aadharCardFile = req.getFile("Aadharcard_File");
            if (aadharCardFile != null && !aadharCardFile.isEmpty()) {
                user1.setAadharcardfile(traineeService.uploadfile(aadharCardFile));
            } else {
                user1.setAadharcardfile(request.getParameter("Aadharcard_File"));
            }

            MultipartFile idCardPhotoFile = req.getFile("Idcard_photo");
            if (idCardPhotoFile != null && !idCardPhotoFile.isEmpty()) {
                user1.setIdcardphoto(traineeService.uploadfile(idCardPhotoFile));
            } else {
                user1.setIdcardphoto(request.getParameter("Idcard_photo"));
            }

            System.out.println("user  ===  " + user);
            System.out.println("user1 == " + user1);
            user1.setCreateddate(new Date());
            traineeService.setUser(user1, trainee);
            //   traineeRepository.save(user1);
        } else {
            System.out.println("else part executed");
        }
        return "redirect:/userdashboard";
    }

    
    @GetMapping("/users")
    public String listOfUsers(Model model, Principal principal) {

        AdminUser admin=adminUserRepo.findByEmail(principal.getName());
        if(admin.getRole().equals("organization")){
            Organization org=organizationRepository.findByEmail_Id(principal.getName());
            model.addAttribute("trainees", traineeRepository.findByOrganizationId(org.getOrganizationId()));
            return "registered_Trainees";
        }
        model.addAttribute("trainees", traineeRepository.findAll());
        return "registered_Trainees";
    }
    
   
    @GetMapping("/organiser/user/view/{id}")
    public String getMethodName(@PathVariable("id") Integer id, Model model) {
        Optional<trainee> org = traineeRepository.findById(id);
        trainee trainee=org.get();
		System.out.println(org);
		if (trainee != null) {
            Organization org1=organizationRepository.findByOrganizationId(trainee.getOrganizationId());
			model.addAttribute("user", trainee);
            model.addAttribute("organization", org1.getOrganization_name());
		} else {
			System.out.println("not shown view");
		}
		return "organiser/trainee/view_user";
    }
    
//    @GetMapping("/enrolledUsers")
//    public String UserEnrolledPrograms( Model model) {
//
//        model.addAttribute("programs", programRepository.findAll());
//        return "enrolled_users";
//    }
//
//    @GetMapping("/userEnrolledPrograms")
//    public String enrolledUsers(@RequestParam Integer id, Model model){
//
//        Optional<Program> program=programRepository.findById(id);
//        Program prog=program.get();
//        model.addAttribute("trainees", traineeRepository.findByProgram(prog));
//        model.addAttribute("programs", programRepository.findAll());
//        return "enrolled_users";
//    }
//    
    
//	@RequestMapping(value = "/organiser/deleteuser", method = RequestMethod.GET)
//	public String deleteConfig(@RequestParam("nid") String[] configId) {
//		System.out.println("hello");
//		traineeRepository.deluser(Arrays.asList(configId));
//		
//		return "redirect:/organiser/user";
//	}
    
//	  @Modifying
//    @Transactional
//    @Query(nativeQuery = true, value = "DELETE FROM trainee WHERE id IN (?1)")
//    void deluser(List<String> id);  
    
	@RequestMapping(value = "/organiser/deleteuser", method = RequestMethod.GET)
	public String deleteConfig(@RequestParam("nid") Integer[] configId) {
		System.out.println("hello");
		String configIds = Arrays.toString(configId).replace("]", "");
		
		template.execute("DELETE FROM trainee WHERE trainee.id IN(" + configIds + ");");
		// programrepo.deleteProgram(Arrays.asList(configId));

		return "redirect:/organiser/user";
	}
    

}
