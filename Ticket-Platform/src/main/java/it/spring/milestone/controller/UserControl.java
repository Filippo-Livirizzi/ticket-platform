package it.spring.milestone.controller;




import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import it.spring.milestone.Model.User;
import it.spring.milestone.repository.TicketRepo;
import it.spring.milestone.repository.UserRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserControl {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private TicketRepo ticketRepo;

	@GetMapping
	public String userInterface(Authentication authentication, Model model) {
		String username = authentication.getName();
		
		
		
		 Optional<User> optionalUser = userRepo.findByUsername("pippo");
		 
		 if (optionalUser.isPresent()) {
	            User user = optionalUser.get();
	            model.addAttribute("user", user);
	            model.addAttribute("ticketList", ticketRepo.findAll()); // Supponendo che tutti i ticket devono essere mostrati
	        }

		return "/user";
	}
/*
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer userid, Model model) {
		model.addAttribute("user", userRepo.findById(userid).get());
		return "/user/edit";
	}

	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("user") User userForm, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "/user/edit";
		}

		userRepo.save(userForm);

		return "redirect:/user";
	}*/
}
