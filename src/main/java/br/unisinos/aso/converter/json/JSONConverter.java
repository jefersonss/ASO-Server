package br.unisinos.aso.converter.json;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.unisinos.aso.model.Patient;
import br.unisinos.aso.transformer.TransformedInfo;

@Component
public class JSONConverter {

	public String convert(Patient patient, TransformedInfo transformed) {
		String patientJson = "";
		ObjectMapper mapper = new ObjectMapper();

		try {
			patientJson = mapper.writeValueAsString(patient);
			JsonNode readTree = mapper.readTree(patientJson);
			
			ObjectNode changedJSON = ((ObjectNode)readTree).put("chartUrl", transformed.getChartUrl());
			changedJSON = changedJSON.put("bloodPressureComparisonChartUrl", transformed.getBloodPressureComparisonChartUrl());
			
			addUrlList(transformed.getVitalSignsEvolutionChart(), changedJSON, "vitalSignsEvolutionChart");
			addPatientList(transformed.getPatientsWithSameDiagnosis(), changedJSON, "patientsWithSameDiagnosis");
			addPatientList(transformed.getPatientsTakingSameMedication(), changedJSON, "patientsTakingSameMedication");
			
			patientJson = mapper.writeValueAsString(changedJSON);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return patientJson;
	}

	private void addUrlList(List<String> vitalSignsEvolutionChart, ObjectNode changedJSON, String arrayName) {
		ArrayNode arrayNode = changedJSON.arrayNode();
		
		for (String url : vitalSignsEvolutionChart)
			arrayNode.add(url);
		
		changedJSON.putArray(arrayName).addAll(arrayNode);
	}

	private void addPatientList(List<Patient> list, ObjectNode changedJSON, String arrayName) {
		ArrayNode arrayNode = changedJSON.arrayNode();
		
		for(Patient transformedPatient : list)
			arrayNode.addPOJO(transformedPatient);
		
		changedJSON.putArray(arrayName).addAll(arrayNode);
	}
}