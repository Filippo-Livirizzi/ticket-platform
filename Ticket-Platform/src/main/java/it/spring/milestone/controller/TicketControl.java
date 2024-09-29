package it.spring.milestone.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
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
	List<StatoTick> stati = Arrays.asList(StatoTick.APERTO, StatoTick.CHIUSO, StatoTick.IN_CORSO);
	Note note = new Note();

	@GetMapping
	public String index(Model model) {

		List<Ticket> tickets = new ArrayList<>();

		tickets = ticketrepo.findAll();
		model.addAttribute("ticket", tickets);

		return "index";
	}

	@GetMapping("/create")
	public String create(Model model, Authentication authentication) {
		Ticket ticket = new Ticket();
		//Note note = new Note();

		List<StatoTick> stati = Arrays.asList(StatoTick.APERTO, StatoTick.CHIUSO, StatoTick.IN_CORSO);

		model.addAttribute("stati", stati);
		model.addAttribute("categorias", cateRepo.findAll());
		model.addAttribute("ticket", ticket);
		model.addAttribute("user", userRepo.findAll());
		//model.addAttribute("note", note);

		return "/create";
	}

	@PostMapping("/create")
	public String create(@Valid @ModelAttribute("ticket") Ticket ticketForm, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("stati", stati);
			model.addAttribute("categorias", cateRepo.findAll());
			
			model.addAttribute("user", userRepo.findAll());
			return "/create";
		}

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
	public String edit(@PathVariable("id") Integer ticketid, Model model) {

		model.addAttribute("ticket", ticketrepo.findById(ticketid));
		model.addAttribute("categorias", cateRepo.findAll());

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

	@GetMapping("/user")
	public String getLoggedInUser(Model model) {
		// Ottieni l'autenticazione attuale
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Ottieni l'username dell'utente autenticato
		String username = authentication.getName();

		// Cerca l'utente nel database usando il repository
		Optional<User> userOptional = userRepo.findByUsername(username);

		/*if (userOptional.isPresent()) {
			// Se l'utente è presente, passalo al modello
			User user = userOptional.get();
			model.addAttribute("user", user);
			model.addAttribute("userTicket", user.getTicket());
		} else {
			// Gestisci il caso in cui l'utente non sia trovato
			model.addAttribute("error", "Utente non trovato");
		}*/

		// Restituisci il nome del template Thymeleaf (senza '/')
		return "user";
	}

	@GetMapping("/user/edit")
	public String UserEdit(Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Optional<User> userOptional = userRepo.findByUsername(username);

		if (userOptional.isPresent()) {
			model.addAttribute("user", userOptional.get());

			userRepo.findByUsername(username);
			return "userEdit"; // Restituisce la vista del form di modifica
		} else {
			model.addAttribute("error", "Utente non trovato");
			return "redirect:/user"; // Reindirizza alla pagina delle informazioni utente
		}
	}

	@Transactional
	@PostMapping("/user/edit")
	public String updateUser( @RequestParam Boolean disponibile,@RequestParam String username, @RequestParam String email, Model model) {
		Optional<User> userOptional = userRepo.findByUsername(username);

		if (userOptional.isPresent()) {
			User user = userOptional.get();
			user.setEmail(email); // Aggiorna l'email
			user.setUsername(username); // Aggiorna username
			user.setDisponibile(disponibile);

			// Salva le modifiche nel database
			userRepo.save(user);
			

			//return "redirect:/ticket/user";
		} else {
			model.addAttribute("error", "Utente non trovato");
			return "redirect:/ticket/user/edit";

		}
		return "redirect:/ticket/user";
	}
}
