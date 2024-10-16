package it.spring.milestone.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.Authentication;

import org.checkerframework.checker.units.qual.Current;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import it.spring.milestone.Model.Note;
import it.spring.milestone.Model.StatoTick;
import it.spring.milestone.Model.Ticket;
import it.spring.milestone.Model.User;
import it.spring.milestone.repository.CategoriaRepository;
import it.spring.milestone.repository.NoteRepository;
import it.spring.milestone.repository.TicketRepo;
import it.spring.milestone.repository.UserRepository;
import it.spring.milestone.security.DatabaseUserDetails;
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
	// List<StatoTick> stati = Arrays.asList(StatoTick.APERTO, StatoTick.CHIUSO,
	// StatoTick.IN_CORSO);
	Note note = new Note();

	@GetMapping
	public String index(Model model) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String currentUsername = authentication.getName();
		
	    if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ADMIN"))) {
	    	
	    	List<Ticket> ticket = new ArrayList<>();
	    	ticket = ticketrepo.findAll();
	    	model.addAttribute("ticket",ticket);
	    }else {
	    		Optional<User> user = userRepo.findByUsername(currentUsername);
	    		if(user.isPresent()){
	    			User users = user.get();
	    			List<Ticket> userTicket = ticketrepo.findByUser(users);
	    			model.addAttribute("ticket", userTicket);
	    		} else {
	    			model.addAttribute("error", "utente non trovato");
	    		}
	    	}

		return "index";
	}

	@GetMapping("/create")
	public String create(Model model) {
		Ticket ticket = new Ticket();

		model.addAttribute("stati", StatoTick.values());
		model.addAttribute("categorias", cateRepo.findAll());
		model.addAttribute("ticket", ticket);
		model.addAttribute("user", userRepo.findAll());

		return "/create";
	}

	@PostMapping("/create")
	public String create(@Valid @ModelAttribute("ticket") Ticket ticketForm, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {

			model.addAttribute("stati", Arrays.asList(StatoTick.APERTO, StatoTick.CHIUSO, StatoTick.IN_CORSO));
			model.addAttribute("categorias", cateRepo.findAll());
			model.addAttribute("user", userRepo.findAll());
			return "/create";
		}

		if (ticketForm.getUser() != null) {
			User user = ticketForm.getUser();
			user.setDisponibile(false);
		}

		ticketForm.setDataCreazione(LocalDate.now());
		ticketrepo.save(ticketForm);
		// noteRepo.save(note);
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
	
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {

		model.addAttribute("user", userRepo.findAll());
        model.addAttribute("stato", StatoTick.values());
		model.addAttribute("ticket", ticketrepo.findById(id).get());
        model.addAttribute("categorias", cateRepo.findAll());
		return "/edit";
	}
	
	@PostMapping("/edit/{id}")
	public String ticketUpdate (@Valid @ModelAttribute("ticket") Ticket ticketUpdate, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			
			model.addAttribute("stati", Arrays.asList(StatoTick.APERTO, StatoTick.CHIUSO, StatoTick.IN_CORSO));
			model.addAttribute("categorias", cateRepo.findAll());
			
			return"/ticket/edit";
		}
		
		ticketUpdate.setDataCreazione(LocalDate.now());
		ticketrepo.save(ticketUpdate);
		
		return"redirect:/ticket";
	}

	
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer ticketid) {

		ticketrepo.deleteById(ticketid);
		return "redirect:/ticket";
	}

	@GetMapping("/note/{id}")
	public String addNote(@PathVariable("id") Integer id, Model model) {

		Ticket ticket = ticketrepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Ticket non trovato"));

		System.out.println("Ticket ID: " + ticket.getId());

		Note note = new Note();
		note.setTicket(ticket);

		model.addAttribute("ticket", ticket);
		model.addAttribute("note", note);

		return "/note";
	}

	@PostMapping("/note/{id}")
	public String addNote(@Valid @ModelAttribute("note") Note note, BindingResult bindingResult, Model model,
			@PathVariable("id") Integer id, Authentication authentication) {

		if (bindingResult.hasErrors()) {
			return "note";
		}

		Ticket ticket = ticketrepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Ticket non trovato"));

		note.setCreate(LocalDate.now()); // data di creazione

		note.setTicket(ticket);

		Optional<User> user = userRepo.findByUsername(authentication.getName());

		note.setTicket(ticket);
		noteRepo.save(note);

		return "redirect:/ticket";
	}

	@GetMapping("/user")
	public String getLoggedInUser(Model model) {
		// Ottieni l'autenticazione attuale
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Ottieni l'username dell'utente autenticato
		String username = authentication.getName();

		// Cerca l'utente nel database usando il repository
		Optional<User> userOptional = userRepo.findByUsername(username);

		if (userOptional.isPresent()) {
			// Se l'utente è presente, passalo al modello
			User user = userOptional.get();
			model.addAttribute("user", user);

			List<Ticket> ticketUser = ticketrepo.findByUser(user);
			model.addAttribute("ticketUser", ticketUser);

		} else {
			// Gestisci il caso in cui l'utente non sia trovato
			model.addAttribute("error", "Utente non trovato");
		}

		return "user";
	}
	
	@PostMapping("/user/disponibile")
	public String updateDisp(@RequestParam("disponibile") boolean disponibile, Model model) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String currentUsername = authentication.getName();
	    
	    Optional<User> utente = userRepo.findByUsername(currentUsername);
	    if(utente.isPresent()) {
	    	User user = utente.get();
	    	user.setDisponibile(disponibile);
	    	userRepo.save(user);
	    	model.addAttribute("user", user);
	    }else {
	    	model.addAttribute("error", "utente non trovato");
	    }
	    
	    return "redirect:/ticket/user";
	}

	
	

	@GetMapping("/search")
	public String search(@RequestParam("title") String title, Model model) {
		List<Ticket> find = ticketrepo.findByTitle(title);
		model.addAttribute("ticket", find);
		return "/search";
	}
	


}
