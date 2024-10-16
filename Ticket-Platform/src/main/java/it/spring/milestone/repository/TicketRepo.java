package it.spring.milestone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.spring.milestone.Model.Categoria;
import it.spring.milestone.Model.Ticket;
import it.spring.milestone.Model.User;

public interface TicketRepo extends JpaRepository<Ticket, Integer>{

	List<Ticket> findByUser(User user);
	List<Ticket> findByTitle(String title);
	List<Ticket> findByCategoria(Categoria categoriaid);
	List<Ticket> findByStato(String stato);
}