package br.unisinos.aso.summarizer;

import java.util.*;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import br.unisinos.aso.model.*;

@Component
public class Summarizer {
	
	public Patient getSummarizedPatient(Patient patient){
		DateTime today = new DateTime();
		patient.setTreatment(filterLatestTreatmentInfo(patient.getTreatment()));
		patient.setAdministeredMedication(filterLatestMedication(patient.getAdministeredMedication(), today));
		patient.setRecommendedMedication(filterLatestMedication(patient.getRecommendedMedication(), today));
		return patient;
	}

	private Set<Treatment> filterLatestTreatmentInfo(Set<Treatment> treatmentList) {
		DateTime today = new DateTime();
		Set<Treatment> filteredList = new HashSet<Treatment>();
		
		for (Treatment treatment : treatmentList) {
			treatment.setExam(filterLatestExams(treatment.getExam(), today));
			filteredList.add(treatment);
		}
		return filteredList;
	}

	private Set<Medication> filterLatestMedication(Set<Medication> medications, DateTime today) {
		Set<Medication> filteredList = new HashSet<Medication>();
		
		for (Medication medication : medications) {
			DateTime jodaTime = new DateTime(medication.getDateAdministered().getTime());
			
			//if(Days.daysBetween(jodaTime.toLocalDate(), today.toLocalDate()).getDays() <= 1)
				filteredList.add(medication);
		}
		return filteredList;
	}

	private Set<Exam> filterLatestExams(Set<Exam> exams, DateTime today) {
		Set<Exam> filteredList = new HashSet<Exam>();
		
		for (Exam exam : exams) {
			DateTime jodaTime = new DateTime(exam.getDate().getTime());
			
			//if(Days.daysBetween(jodaTime.toLocalDate(), today.toLocalDate()).getDays() <= 1)
				filteredList.add(exam);
		}
		return filteredList;
	}
	
}