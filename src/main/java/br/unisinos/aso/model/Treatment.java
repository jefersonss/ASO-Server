package br.unisinos.aso.model;

import java.util.Set;

import javax.persistence.*;

@Entity
public class Treatment {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String observations;
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "treat_exam", joinColumns = { @JoinColumn(name = "treatment_id") }, inverseJoinColumns = { @JoinColumn(name = "exam_id") })
	private Set<Exam> exam;
	
	public int getId() {
		return id;
	}
	public Set<Exam> getExam() {
		return exam;
	}
	public void setExam(Set<Exam> exam) {
		this.exam = exam;
	}
	public String getObservations() {
		return observations;
	}
	public void setObservations(String observations) {
		this.observations = observations;
	}

	@Override
	public String toString() {
		return "Treatment [id=" + id + ", observations=" + observations + ", exam=" + exam + "]";
	}
}