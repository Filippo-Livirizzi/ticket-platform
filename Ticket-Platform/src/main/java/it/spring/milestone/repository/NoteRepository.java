package it.spring.milestone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.spring.milestone.Model.Note;

public interface NoteRepository extends JpaRepository<Note,  Integer> {
	
	List<Note> findByTicketId(Integer id);

}
