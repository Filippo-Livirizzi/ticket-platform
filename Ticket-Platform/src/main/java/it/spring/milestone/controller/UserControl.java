package it.spring.milestone.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/user")
public class UserControl{

	
	@GetMapping ("/me")
	public String GetLoggedUser() {
		//Recupero la security utilizzata nel progetto dove authentication ha le info dell'utente loggato
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		return "Sessione attiva da " + userDetails.getUsername();
	}
}