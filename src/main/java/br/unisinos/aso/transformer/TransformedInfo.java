package br.unisinos.aso.transformer;

import java.util.List;

import br.unisinos.aso.model.Patient;

public class TransformedInfo {
	private String chartUrl;
	private String bloodPressureComparisonChartUrl;
	private List<Patient> patientsWithSameDiagnosis;
	private List<Patient> patientsTakingSameMedication;
	private String patientByDiseaseChart;
	private List<String> vitalSignsEvolutionChart;
	
	public String getChartUrl() {
		return chartUrl;
	}
	public void setChartUrl(String chartUrl) {
		this.chartUrl = chartUrl;
	}
	public String getBloodPressureComparisonChartUrl() {
		return bloodPressureComparisonChartUrl;
	}
	public void setBloodPressureComparisonChartUrl(String bloodPressureComparisonChartUrl) {
		this.bloodPressureComparisonChartUrl = bloodPressureComparisonChartUrl;
	}
	public List<Patient> getPatientsWithSameDiagnosis() {
		return patientsWithSameDiagnosis;
	}
	public void setPatientsWithSameDiagnosis(List<Patient> patientsWithSameDiagnosis) {
		this.patientsWithSameDiagnosis = patientsWithSameDiagnosis;
	}
	public List<Patient> getPatientsTakingSameMedication() {
		return patientsTakingSameMedication;
	}
	public void setPatientsTakingSameMedication(List<Patient> patientList) {
		this.patientsTakingSameMedication = patientList;
	}
	public String getPatientByDiseaseChart() {
		return patientByDiseaseChart;
	}
	public void setPatientByDiseaseChart(String patientByDiseaseChart) {
		this.patientByDiseaseChart = patientByDiseaseChart;
	}
	public List<String> getVitalSignsEvolutionChart() {
		return vitalSignsEvolutionChart;
	}
	public void setVitalSignsEvolutionChart(List<String> vitalSignsEvolutionChart) {
		this.vitalSignsEvolutionChart = vitalSignsEvolutionChart;
	}
}