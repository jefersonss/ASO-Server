package br.unisinos.aso.ct;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import br.unisinos.aso.model.PatientInfo;

@Path("aggregated")
public interface ASOServiceCT {
	
	@POST
	@Path("/aggregateInfo")
	@Consumes(MediaType.TEXT_PLAIN)
	public void aggregateMiscelaneousInfo(String patientData);
	
	@GET
	@Path("/retrieve/{id: \\d+}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public String retrievePatientOntology(@PathParam("id") int patientId);

	@GET
	@Path("/retrieveObject/{id: \\d+}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public PatientInfo retrievePatientInfo(@PathParam("id") int patientId);
}
