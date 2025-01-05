package com.exam.portal.config;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.exam.portal.Controller.Encrypt;
import com.exam.portal.Model.AdminUser;
import com.exam.portal.Repository.AdminUserRepo;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

	@Bean
    public UserDetailsService userDetailsService(AdminUserRepo repo) {
        return (String username) -> {
            AdminUser dbUser = repo.findByEmail(username);
        	 System.out.println("dbUser = " + dbUser);
            if (dbUser == null) {
                throw new UsernameNotFoundException("username: " + username + " not found");
            }
    
            //System.out.println("Authorities = " + grantedAuthorities);
            String rolesString=dbUser.getRole()+","+dbUser.getAuthorities().stream().map(auth -> auth.getAuthority())
            .collect(Collectors.joining(","));
              // List<GrantedAuthority> authorities = new ArrayList<>();
        
              String[] rolesarray = rolesString.split(",");




            UserDetails springUser = User.builder()
                    .username(dbUser.getEmail())
                    .password(dbUser.getPassword())
                    //.authorities(grantedAuthorities)
                     .roles(rolesarray)
                    .build();
            System.out.println("springUser = " + springUser);
            return springUser;
        };
	}

	@Bean
    public CustomAuthenticationSuccessHandler successHandler() {
        return new CustomAuthenticationSuccessHandler();
    }	
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                request -> request
                        .requestMatchers( "/webjars/**","/img/**","/video/**","/js/**","/webfonts/**" ,"/css/**" ,"/","/trainee_signup","/sign-up.html","/sign-up-trainee.html","/contact.html","/exam","/goto/exam","/courses-details.html","/organiser/organization/register","/trainee_sign_up","/organiser/organization/register").permitAll()
                        .requestMatchers("admindashboard").hasAnyRole("admin","superadmin")
                        .requestMatchers("organizationdashboard").hasAnyRole("organization")
                        .requestMatchers("userdashboard").hasAnyRole("trainee")
                        .requestMatchers("/organiser/organization","/organiser/organization/view/{id}").hasAnyRole("vieworganization","addorganization","superadmin")
                        .requestMatchers("/organiser/organization/**","/organiser/delcampaign","/organiser/org/**").hasAnyRole("addorganization","superadmin")
                        .requestMatchers("/programview","/program/view/{id}").hasAnyRole("addprogram","viewprogram","superadmin","nodalofficer")
                        .requestMatchers("/programcreate","/programdelcampaign").hasAnyRole("addprogram","superadmin")
                        .requestMatchers("/organiser/course","/organiser/cour").hasAnyRole("viewcourse","superadmin")   
                        .requestMatchers("/organiser/course/new","/createCourse").hasAnyRole("addcourse","superadmin")
                        .requestMatchers("/organiser/module").hasAnyRole("viewmodule","superadmin")
                        .requestMatchers("/organiser/createModule","/registerModule").hasAnyRole("addmodule","superadmin")
                        .requestMatchers("/organiser/topic").hasAnyRole("viewtopic","superadmin")
                        .requestMatchers("/organiser/newtopic","/upload").hasAnyRole("addtopic","superadmin")
                        .requestMatchers("/assignroless").hasRole("superadmin")
                        .anyRequest().authenticated()
        ).formLogin(login -> login.successHandler(successHandler())
                             .loginPage("/organiserlogin").usernameParameter("username")
                             .passwordParameter("password")
                        .permitAll())
                        .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/organiser/logout"))

                        .logoutSuccessUrl("/organiser/login?logout")
                        
                        .permitAll())
                       
                        .csrf(csrf -> csrf.disable());
	        return http.build();
	}
	  @Bean
	    public PasswordEncoder customPasswordEncoder() {
	        return new Encrypt();
	    }

}
