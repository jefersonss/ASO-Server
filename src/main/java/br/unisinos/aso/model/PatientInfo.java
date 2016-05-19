package br.unisinos.aso.model;

import java.util.List;
import java.util.Map;

import br.unisinos.aso.transformer.TransformedInfo;

public class PatientInfo {

	private Patient patient;
	private TransformedInfo transformedInfo;
	private Map<String, List<String>> vitalSignsExams;
	
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public TransformedInfo getTransformedInfo() {
		return transformedInfo;
	}
	public void setTransformedInfo(TransformedInfo transformedInfo) {
		this.transformedInfo = transformedInfo;
	}
	public Map<String, List<String>> getVitalSignsExams() {
		return vitalSignsExams;
	}
	public void setVitalSignsExams(Map<String, List<String>> vitalSignsExams) {
		this.vitalSignsExams = vitalSignsExams;
	}
}