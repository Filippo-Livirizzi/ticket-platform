
package it.spring.milestone.Model;

import java.util.List;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class User {

	@Id
	private Integer id;

	
	private String username;

	private String email;

	
	private String password;

	private Boolean disponibile;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Role> roles;
	
	@OneToMany(mappedBy = "user")
	private List<Ticket> ticket;
	
	@OneToMany(mappedBy = "user")
	private List<Note> note;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getDisponibile() {
		return disponibile;
	}

	public void setDisponibile(Boolean disponibile) {
		this.disponibile = disponibile;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;

		if ("ADMIN".equals(this.roles)) {
			this.disponibile = null;
		}
		;
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
