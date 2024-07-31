package it.spring.milestone.Model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Note")
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	// TESTO - DATA DI CREAZIONE

	@Column(name = "Testo", nullable = false)
	private String testo;
	@Column(name = "data di creazione", nullable = false)
	private LocalDate create;

	@ManyToOne
	@JoinColumn(name = "ticket_id")
	private Ticket ticket;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public LocalDate getCreate() {
		return create;
	}

	public void setCreate(LocalDate create) {
		this.create = create;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


}
