package br.unisinos.aso.model;

import java.sql.Date;

import javax.persistence.*;

@Entity
public class Medication {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	private String type;
	private Date dateAdministered;
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public Date getDateAdministered() {
		return dateAdministered;
	}
	public void setDateAdministered(Date dateAdministered) {
		this.dateAdministered = dateAdministered;
	}
	@Override
	public String toString() {
		return "Medication [id=" + id + ", name=" + name + ", type=" + type
				+ "]";
	} 
}