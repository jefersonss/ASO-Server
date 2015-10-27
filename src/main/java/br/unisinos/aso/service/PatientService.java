package br.unisinos.aso.service;

import java.util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unisinos.aso.dao.PatientDAO;
import br.unisinos.aso.model.Patient;
import br.unisinos.aso.transformer.TransformedInfo;
import br.unisinos.aso.transformer.Transformer;

@Path("/patient")
@Service
public class PatientService {
	
	@Autowired
	private PatientDAO patientDAO;
	@Autowired
	private Transformer transformer;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
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
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/countByDisease")
	public TransformedInfo getPatientCountByDisease(){
		TransformedInfo info = new TransformedInfo();
		info.setPatientByDiseaseChart(transformer.generatePatientsByDiseaseChart());
		return info;
	}
}