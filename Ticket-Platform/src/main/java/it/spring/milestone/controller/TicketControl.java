package it.spring.milestone.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.spring.milestone.Model.Ticket;
import it.spring.milestone.repository.CategoriaRepository;
import it.spring.milestone.repository.TicketRepo;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/ticket")
public class TicketControl {

	@Autowired
	private TicketRepo ticketrepo;

	@Autowired
	private CategoriaRepository cateRepo;

	@GetMapping
	public String index(Model model) {

		List<Ticket> tickets = new ArrayList<>();

		tickets = ticketrepo.findAll();
		model.addAttribute("ticket", tickets);

		return "/index";
	}

	@GetMapping("/create")
	public String create(Model model) {
		Ticket ticket = new Ticket();
	
		model.addAttribute("categorias", cateRepo.findAll());
		model.addAttribute("ticket", ticket);

		return "/create";
	}

	@PostMapping("/create")
	public String dashboard(@Valid @ModelAttribute("ticket") Ticket ticketForm, BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
			
			return "/ticket/create";
		}
		ticketrepo.save(ticketForm);

		return "redirect:/ticket";
	}

}
