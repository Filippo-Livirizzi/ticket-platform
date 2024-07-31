
package it.spring.milestone.Model;



import org.springframework.lang.NonNull;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Role {

	@Id
	private Integer id;
	
	@NonNull
	private String name;

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
	
}
