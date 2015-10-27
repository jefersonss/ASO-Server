package br.unisinos.aso.model;

import java.sql.Date;
import java.util.*;

import javax.persistence.*;

@Entity
public class Patient {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "patient_disease", joinColumns = { @JoinColumn(name = "patient_id") }, inverseJoinColumns = { @JoinColumn(name = "disease_id") })
	private Set<Disease> diseases;
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "patient_treatment", joinColumns = { @JoinColumn(name = "patient_id") }, inverseJoinColumns = { @JoinColumn(name = "treatment_id") })
	private Set<Treatment> treatment;
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "recomended_medication", joinColumns = { @JoinColumn(name = "patient_id") }, inverseJoinColumns = { @JoinColumn(name = "medication_id") })
	private Set<Medication> recommendedMedication;
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "administered_medication", joinColumns = { @JoinColumn(name = "patient_id") }, inverseJoinColumns = { @JoinColumn(name = "medication_id") })
	private Set<Medication> administeredMedication;
	private String name;
	private String room;
	private int age;
	private String gender;
	private Date lastEnteredDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Set<Disease> getDiseases() {
		return diseases;
	}
	public void setDiseases(Set<Disease> diseases) {
		this.diseases = diseases;
	}
	public Set<Treatment> getTreatment() {
		return treatment;
	}
	public void setTreatment(Set<Treatment> treatment) {
		this.treatment = treatment;
	}
	public Set<Medication> getRecommendedMedication() {
		return recommendedMedication;
	}
	public void setRecommendedMedication(Set<Medication> recommendedMedication) {
		this.recommendedMedication = recommendedMedication;
	}
	public Set<Medication> getAdministeredMedication() {
		return administeredMedication;
	}
	public void setAdministeredMedication(Set<Medication> administeredMedication) {
		this.administeredMedication = administeredMedication;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getLastEnteredDate() {
		return lastEnteredDate;
	}
	public void setLastEnteredDate(Date lastEnteredDate) {
		this.lastEnteredDate = lastEnteredDate;
	}
	
	public List<Exam> getAllExams() {
		List<Exam> allExams = new LinkedList<Exam>();
		
		for(Treatment treatment : getTreatment()){
			allExams.addAll(treatment.getExam());
		}
		return allExams;
	}
	
	@Override
	public String toString() {
		return "Patient [id=" + id + ", disease=" + diseases + ", treatment="
				+ treatment + ", name=" + name + ", age=" + age + ", gender="
				+ gender + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((diseases == null) ? 0 : diseases.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + id;
		result = prime * result + ((lastEnteredDate == null) ? 0 : lastEnteredDate.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((room == null) ? 0 : room.hashCode());
		result = prime * result + ((treatment == null) ? 0 : treatment.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Patient other = (Patient) obj;
		if (age != other.age)
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (id != other.id)
			return false;
		if (lastEnteredDate == null) {
			if (other.lastEnteredDate != null)
				return false;
		} else if (!lastEnteredDate.equals(other.lastEnteredDate))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (room == null) {
			if (other.room != null)
				return false;
		} else if (!room.equals(other.room))
			return false;
		return true;
	}
}