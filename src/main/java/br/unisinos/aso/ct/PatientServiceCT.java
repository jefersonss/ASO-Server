package br.unisinos.aso.ct;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import br.unisinos.aso.model.Patient;
import br.unisinos.aso.transformer.TransformedInfo;

@Path("/patient")
public interface PatientServiceCT {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	Map<String, List<Patient>> getPatients();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/countByDiseaseChart")
	TransformedInfo getPatientCountByDiseaseChart();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/countByDisease")
	Map<String, BigInteger> getPatientCountByDisease();
}
