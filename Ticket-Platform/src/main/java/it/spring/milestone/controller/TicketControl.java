package it.spring.milestone.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import it.spring.milestone.Model.Ticket;
import it.spring.milestone.repository.TicketRepo;

@Controller
@RequestMapping("/ticket")
public class TicketControl {
	
	@Autowired
	private TicketRepo ticketrepo;
	
	@GetMapping
	public String Index (Model model) {
		
		List<Ticket> ticket = new ArrayList<>();
		
		ticket = ticketrepo.findAll();
		model.addAttribute("Ticket", ticket);
		
		return"/ticket/index";
	}
	
	@GetMapping("/show/{id}")
	public String show(@PathVariable("id") Integer idticket, Model model) {

		model.addAttribute("Ticket", ticketrepo.findById(idticket).get());

		return "/ticket/show";
	}

}
