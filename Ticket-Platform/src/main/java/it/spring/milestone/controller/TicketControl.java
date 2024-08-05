package it.spring.milestone.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.spring.milestone.Model.Categoria;
import it.spring.milestone.Model.Note;
import it.spring.milestone.Model.Ticket;
import it.spring.milestone.Model.User;
import it.spring.milestone.repository.CategoriaRepository;
import it.spring.milestone.repository.NoteRepository;
import it.spring.milestone.repository.TicketRepo;
import it.spring.milestone.repository.UserRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/ticket")
public class TicketControl {

	@Autowired
	private TicketRepo ticketrepo;

	@Autowired
	private CategoriaRepository cateRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private NoteRepository noteRepo;

	@GetMapping
	public String index(Model model) {

		List<Ticket> tickets = new ArrayList<>();

		tickets = ticketrepo.findAll();
		model.addAttribute("ticket", tickets);

		return "index";
	}

	@GetMapping("/create")
	public String create(Model model) {
		Ticket ticket = new Ticket();
		Note note = new Note();

		model.addAttribute("categorias", cateRepo.findAll());
		model.addAttribute("ticket", ticket);
		model.addAttribute("user", userRepo.findAll());
		model.addAttribute("note", note);

		return "/create";
	}

	@PostMapping("/create")
	public String create(@Valid @ModelAttribute("ticket") Ticket ticketForm, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {

			return "/create";
		}

		//User user = userRepo.findById(ticketForm.getUser().getId()).orElse(null);
	
		//Categoria categoria = cateRepo.findById(ticketForm.getCategoria().getId()).orElse(null);

	/*	List<Note> note = ticketForm.getNote();
		for (int i = 0; i < note.size(); i++) {
			Note nota = note.get(i); // prendo la nota
			nota.setTicket(ticketForm); // associo la nota al ticket
		}*/

		// Imposta l'utente e la categoria nel ticket
//		ticketForm.setUser(user);
	//	ticketForm.setCategoria(categoria);

		ticketrepo.save(ticketForm);
		return "redirect:/ticket";
	}

	@GetMapping("/show/{id}")
	public String show(@PathVariable("id") Integer ticketid, Model model) {
		
		List<Note> note = noteRepo.findByTicketId(ticketid);

		model.addAttribute("ticket", ticketrepo.findById(ticketid).get());
		model.addAttribute("note", note);
		model.addAttribute("user", userRepo.findById(ticketid));


		return "/show";
	}
	
/*	@PostMapping("/add/note/{id}")
	public String addNote(@PathVariable("id") Integer ticketId, @ModelAttribute("note") @Valid Note newNote, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			return"/ticket/show";
		}
		Ticket ticket = new Ticket();
		ticketrepo.findById(ticketId);
		newNote.setTicket(ticket);

		noteRepo.save(newNote);
		
		return "redirect:/ticket/show" + ticketId;
	}
*/

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer ticketid, Model model) {
		
		
		model.addAttribute("ticket", ticketrepo.findById(ticketid));
		model.addAttribute("categorias", cateRepo.findAll());
	//	model.addAttribute("ticket", ticketrepo.findById(ticketid).get());
		return "/ticket/edit";
	}

	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("ticket") Ticket ticketForm, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("categorias", cateRepo.findAll());
			return "/ticket/edit";
		}

		ticketrepo.save(ticketForm);

		return "redirect:/ticket";

	}

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer ticketid) {
		ticketrepo.deleteById(ticketid);
		return "redirect:/ticket";
	}

	@GetMapping("/search")
	public String search(@RequestParam("search") String search, Model model) {
		List<Ticket> tickets = ticketrepo.findByTitle(search);
		model.addAttribute("tickets", tickets);
		model.addAttribute("search", search);
		return "/index";
	}
}
