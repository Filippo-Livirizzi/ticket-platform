package it.spring.milestone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.spring.milestone.Model.Categoria;
import it.spring.milestone.Model.Ticket;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

	
	
	
}
