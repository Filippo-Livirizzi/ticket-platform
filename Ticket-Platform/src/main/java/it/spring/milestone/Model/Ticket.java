package it.spring.milestone.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Ticket")
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	// TITLE - DESCRIZIONE - DATA CREAZIONE - STATO
	@Column(name = "Titolo", nullable = false)
	private String title;
	@Column(name = "Descrizione", nullable = false)
	private String descrizione;
	@Column(name = "Data creazione", nullable = false)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate dataCreazione;

	//private StatoTick stato;

	@Column(name = "Stato_ticket", nullable = false)
	private String stato;

	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;

	@OneToMany(mappedBy = "ticket")
	private List<Note> note = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public LocalDate getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(LocalDate dataCreazione) {
		this.dataCreazione = dataCreazione;

	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<Note> getNote() {
		return note;
	}

	public void setNote(List<Note> note) {
		this.note = note;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public StatoTick getStato() {
		switch(stato) {
		case "Aperto":
			return StatoTick.APERTO;
		case "In corso":
			return StatoTick.IN_CORSO;
		case "chiuso":
			return StatoTick.CHIUSO;
        default:
            throw new IllegalArgumentException("Stato sconosciuto: " + stato);
		}
	}

	public void setStato(StatoTick stato) {
		this.stato = stato.getValoreVisualizzazione();
		}

}
