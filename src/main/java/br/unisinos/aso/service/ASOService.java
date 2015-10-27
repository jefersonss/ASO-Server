package br.unisinos.aso.service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unisinos.aso.converter.hl7.HL7Converter;
import br.unisinos.aso.converter.json.JSONConverter;
import br.unisinos.aso.dao.PatientDAO;
import br.unisinos.aso.model.Patient;
import br.unisinos.aso.summarizer.Summarizer;
import br.unisinos.aso.transformer.TransformedInfo;
import br.unisinos.aso.transformer.Transformer;

@Path("aso")
@Service
public class ASOService {
	
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
	
	@Path("/aggregateInfo")
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	public void aggregateMiscelaneousInfo(String patientData){
		Patient patient = hl7Converter.convertFromHL7ToPatientObj(patientData);
		patientDAO.updatePatient(patient);
	}
	
	@Path("/retrieve/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String retrievePatientOntology(@PathParam("id") String patientId){
		System.out.println("Chegou");
		
		int id = Integer.parseInt(patientId);
		Patient patient = patientDAO.getPatientById(id);
		TransformedInfo transformed = transformer.transformPatientInfo(patient);
		patient = summarizer.getSummarizedPatient(patient);
		
		return jsonConverter.convert(patient, transformed);
	}
}