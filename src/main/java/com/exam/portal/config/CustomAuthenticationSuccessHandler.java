package com.exam.portal.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationSuccessHandler implements  AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
        System.out.println("Granted Authorities are: " + authentication.getAuthorities());
		
	    //System.out.println(authorities);
	   String targetUrl = determineTargetUrl(authentication);
	   response.sendRedirect(targetUrl);
	}

	private String determineTargetUrl(Authentication authentication) {
		if (isAdmin(authentication) ) {
			System.out.println("is Admin");
	        return "admindashboard";   
           }
		if(isSuperAdmin(authentication)){
			System.out.println("is superadmin");
	        return "admindashboard";
             
		}if(isOrganization(authentication)) {
			System.out.println("is organization");
			return "organizationdashboard";
		}
		if(isNodalofficer(authentication)) {
			System.out.println("is nodal officer");
			return"admindashboard";
		}
		if(isTrainee(authentication)) {
			System.out.println("is Trainee");
			return"userdashboard";
		}
		else{
			System.out.println("fasssssssw");
		    return "/error";
		}
	}
	
	private boolean isTrainee(Authentication authentication) {
		
		return authentication.getAuthorities().stream()
	            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_trainee"));

	}

	private boolean isNodalofficer(Authentication authentication) {
		return authentication.getAuthorities().stream()
	            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_nodalofficer"));
	}

	private boolean isSuperAdmin(Authentication authentication) {
		
		return authentication.getAuthorities().stream()
	            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_superadmin"));
	}
	
	private boolean isOrganization(Authentication authentication) {
		
		return authentication.getAuthorities().stream()
	            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_organization"));
	}
	
	private boolean isAdmin(Authentication authentication) {

				return authentication.getAuthorities().stream()
	            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_admin"));
	}
}