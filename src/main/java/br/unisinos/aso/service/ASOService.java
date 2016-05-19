package br.unisinos.aso.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unisinos.aso.converter.hl7.HL7Converter;
import br.unisinos.aso.converter.json.JSONConverter;
import br.unisinos.aso.ct.ASOServiceCT;
import br.unisinos.aso.dao.PatientDAO;
import br.unisinos.aso.model.Patient;
import br.unisinos.aso.model.PatientInfo;
import br.unisinos.aso.summarizer.Summarizer;
import br.unisinos.aso.transformer.TransformedInfo;
import br.unisinos.aso.transformer.Transformer;

@Service
public class ASOService implements ASOServiceCT{
	
	@Autowired
	private PatientDAO patientDAO;
	@Autowired
	private Summarizer summarizer;
	@Autowired
	private JSONConverter jsonConverter;
	@Autowired
	private HL7Converter hl7Converter;
	@Autowired
	private Transformer transformer;
	
	
	public void aggregateMiscelaneousInfo(String patientData){
		Patient patient = hl7Converter.convertFromHL7ToPatientObj(patientData);
		patientDAO.updatePatient(patient);
	}
	
	public String retrievePatientOntology(int patientId){
		Patient patient = patientDAO.getPatientById(patientId);
		TransformedInfo transformed = transformer.transformPatientInfo(patient);
		patient = summarizer.getSummarizedPatient(patient);
		Map<Patient,TransformedInfo> returningMap = new HashMap<Patient, TransformedInfo>();
		returningMap.put(patient, transformed);
		return jsonConverter.convert(patient, transformed);
	}
	
	public PatientInfo retrievePatientInfo(int patientId){
		Patient patient = patientDAO.getPatientById(patientId);
		TransformedInfo transformed = transformer.transformPatientInfo(patient);
		patient = summarizer.getSummarizedPatient(patient);
		PatientInfo info = new PatientInfo();
		info.setPatient(patient);
		info.setTransformedInfo(transformed);
		info.setVitalSignsExams(transformer.getVitalSignsExams(patient));
		return info;
		
		
			
	}
}