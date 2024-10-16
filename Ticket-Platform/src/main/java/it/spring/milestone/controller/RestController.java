package it.spring.milestone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import it.spring.milestone.Model.Categoria;
import it.spring.milestone.Model.Ticket;

import it.spring.milestone.repository.TicketRepo;

@org.springframework.web.bind.annotation.RestController
@CrossOrigin
@RequestMapping("/api/ticket")
public class RestController {
	
	@Autowired
	private TicketRepo ticketrepo;
	
@GetMapping
public  List<Ticket> getAll(){
return ticketrepo.findAll();
}

@GetMapping("/categoria/{categoriaId}")
public List<Ticket> getTicketsByCategoria(@PathVariable Categoria categoriaId) {
    return ticketrepo.findByCategoria(categoriaId); // Assicurati di avere questo metodo nel tuo repository
}

@GetMapping("/stato/{stato}")
public List<Ticket> getTicketsByStato(@PathVariable String stato) {
    return ticketrepo.findByStato(stato); // Assicurati di avere questo metodo nel tuo repository
}


}
