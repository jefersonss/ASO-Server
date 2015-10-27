package br.unisinos.aso.converter.hl7;

import java.util.*;

import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;

import br.unisinos.aso.model.*;
import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v22.datatype.*;
import ca.uhn.hl7v2.model.v22.message.ADT_A01;
import ca.uhn.hl7v2.parser.Parser;

@Component
public class HL7Converter {

	public Patient convertFromHL7ToPatientObj(String hl7Data) {
		ADT_A01 message = convert(hl7Data);
		Patient patient = new Patient();
		
		patient.setDiseases(Sets.newHashSet(getDiseaseData(message)));
		patient.setTreatment(getTreatmentData(message));
		getTreatmentData(message);
		
		PN hl7Name = message.getPID().getPatientName();
		patient.setName(hl7Name.getGivenName()+" "+hl7Name.getMiddleInitialOrName()+" "+hl7Name.getFamilyName());
		
		patient.setGender(message.getPID().getSex().getValue());
		patient.setAge(calculatePatientAge(message));
		
		patient.setAdministeredMedication(getMedicationData(message));
		patient.setRecommendedMedication(getMedicationData(message));
		
		return patient;
	}

	private int calculatePatientAge(ADT_A01 message){
		int patientAge = 0;
		
		try {
			int day = message.getPID().getDateOfBirth().getTimeOfAnEvent().getDay();
			int month = message.getPID().getDateOfBirth().getTimeOfAnEvent().getMonth();
			int year = message.getPID().getDateOfBirth().getTimeOfAnEvent().getYear();
			LocalDate birthdate = new LocalDate (year, month, day);
			LocalDate now = new LocalDate();
			patientAge = Years.yearsBetween(birthdate, now).getYears();
		} catch (DataTypeException e) {
			e.printStackTrace();
		}
		
		return patientAge;
	}

	private Set<Exam> getExamData(ADT_A01 message) {
		Set<Exam> exams = new HashSet<Exam>();
		
		ST[] procedureDescription = message.getPR1().getProcedureDescription();
		for (ST description : procedureDescription) {
			Exam exam = new Exam();
			exam.setName(description.getValue());
		}
		return exams;
	}

	private Disease getDiseaseData(ADT_A01 message) {
		Disease disease = new Disease();
		disease.setName(message.getDG1().getDiagnosisDescription().getValue());
		return disease;
	}

	public Set<Medication> getMedicationData(ADT_A01 message) {
		Medication medication = new Medication();
		return Sets.newHashSet(medication);
	}

	private Set<Treatment> getTreatmentData(ADT_A01 message) {
		Treatment treatment = new Treatment();
		treatment.setExam(getExamData(message));
		
		return Sets.newHashSet(treatment);
	}

	private ADT_A01 convert(String hl7Data) {
		HapiContext context = new DefaultHapiContext();
		Parser p = context.getGenericParser();
		Message convertedMsg;
		try {
			convertedMsg = p.parse(hl7Data);
			context.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return (ADT_A01) convertedMsg;
	}
}