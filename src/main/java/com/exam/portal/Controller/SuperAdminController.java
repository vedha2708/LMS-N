package com.exam.portal.Controller;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.exam.portal.Model.AdminUser;
import com.exam.portal.Repository.AdminUserRepo;
import com.exam.portal.Repository.AuthoritiesRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
@RequestMapping("/superadmin")
public class SuperAdminController {


    @Autowired
    AuthoritiesRepository authoritiesrepo;

    @Autowired
    AdminUserRepo adminuserrepo;

    @GetMapping("/assignroles")
    public String returnAssignRoles(Model model) {
        model.addAttribute("admin", new AdminUser());
        model.addAttribute("roles",authoritiesrepo.findAll() );
        return "organiser/assign_roles";
    }
    

    @PostMapping("/assignroles")
    public String assignRoles(@ModelAttribute("admin") AdminUser adminUser) {
        adminUser.setCreatedate(new Date());
        adminUser.setRole("admin");
        adminUser.setPassword(new Encrypt().sha256(adminUser.getPassword()));
        adminuserrepo.save(adminUser);

         return "redirect:/organiser/dashboard";
    }
    


}
