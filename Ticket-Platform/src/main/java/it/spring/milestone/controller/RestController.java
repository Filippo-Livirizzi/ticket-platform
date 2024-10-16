package it.spring.milestone.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import it.spring.milestone.Model.Categoria;
import it.spring.milestone.Model.Ticket;
import it.spring.milestone.repository.CategoriaRepository;
import it.spring.milestone.repository.TicketRepo;

@org.springframework.web.bind.annotation.RestController
@CrossOrigin
@RequestMapping("/api/ticket")
public class RestController {
	
	@Autowired
	private TicketRepo ticketrepo;
	
	@Autowired
	private CategoriaRepository caterepo;
	
@GetMapping
public  List<Ticket> getAll(){
return ticketrepo.findAll();
}

@GetMapping("/categoria/{categoriaId}")
public List<Ticket> getTicketsByCategoria(@PathVariable Integer categoriaId) {
    
	Optional<Categoria> categorias = caterepo.findById(categoriaId);
	
	if (categorias.isPresent()) {
		List<Ticket> tickets = ticketrepo.findByCategoria(categorias.get());
		return ResponseEntity.ok(tickets);
	}else {
		return ResponseEntity.notFound().build();
	}
	
	return ticketrepo.findByCategoria(categoriaId); // Assicurati di avere questo metodo nel tuo repository
}

@GetMapping("/stato/{stato}")
public List<Ticket> getTicketsByStato(@PathVariable String stato) {
    return ticketrepo.findByStato(stato); // Assicurati di avere questo metodo nel tuo repository
}


}
