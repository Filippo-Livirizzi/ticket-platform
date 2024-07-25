package it.spring.milestone.Model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Operatore")
public class Operatore {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	// NAME - COGNOME - EMAIL - PASSWORD - STATO

	@Column(name = "Nome", nullable = false)
	private String name;
	@Column(name = "Cognome", nullable = false)
	private String cognome;
	@Column(name = "Email", nullable = false)
	private String email;
	@Column(name = "Password", nullable = false)
	private String password;
	@Column(name = "Stato di lavorazione", nullable = false)
	private String Stato;

	@OneToMany(mappedBy = "operatore")
	private List<Ticket> ticket;

	@OneToMany(mappedBy = "operatore")
	private List<Note> note;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStato() {
		return Stato;
	}

	public void setStato(String stato) {
		Stato = stato;
	}

	public List<Ticket> getTicket() {
		return ticket;
	}

	public void setTicket(List<Ticket> ticket) {
		this.ticket = ticket;
	}

	public List<Note> getNote() {
		return note;
	}

	public void setNote(List<Note> note) {
		this.note = note;
	}

}
