package br.unisinos.aso.model;

import javax.persistence.*;

@Entity
public class Disease {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;

	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Disease [id=" + id + ", name=" + name + "]";
	}
}