package br.unisinos.aso.service;

import java.math.BigInteger;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unisinos.aso.ct.PatientServiceCT;
import br.unisinos.aso.dao.DiseaseDAO;
import br.unisinos.aso.dao.PatientDAO;
import br.unisinos.aso.model.Patient;
import br.unisinos.aso.transformer.TransformedInfo;
import br.unisinos.aso.transformer.Transformer;

@Service
public class PatientService implements PatientServiceCT{
	
	@Autowired
	private PatientDAO patientDAO;
	@Autowired
	private Transformer transformer;
	@Autowired DiseaseDAO diseaseDAO;
	
	public Map<String, List<Patient>> getPatients(){
		List<Patient> patients = patientDAO.getPatients();
		Map<String, List<Patient>> patientsMap = new HashMap<String, List<Patient>>();
		
		for (Patient patient : patients) {
			if(patientsMap.containsKey(patient.getRoom()))
				patientsMap.get(patient.getRoom()).add(patient);
			else{
				List<Patient> patientList = new ArrayList<Patient>();
				patientList.add(patient);
				patientsMap.put(patient.getRoom(), patientList);
			}
		}
		
		return patientsMap;
	}
	
	public TransformedInfo getPatientCountByDiseaseChart(){
		TransformedInfo info = new TransformedInfo();
		info.setPatientByDiseaseChart(transformer.generatePatientsByDiseaseChart());
		return info;
	}
	
	public Map<String, BigInteger> getPatientCountByDisease(){
		Map<String, BigInteger> patientCountByDisease = diseaseDAO.retrievePatientCountByDisease();
		return patientCountByDisease;
	}
	
}