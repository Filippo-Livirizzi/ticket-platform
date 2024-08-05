package it.spring.milestone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import it.spring.milestone.Model.Ticket;

public interface TicketRepo extends JpaRepository<Ticket, Integer>{


	List<Ticket> findByTitle(String title);
	
}
