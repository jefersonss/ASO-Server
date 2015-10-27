package br.unisinos.aso.dao;

import java.math.BigInteger;
import java.util.*;

import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.unisinos.aso.model.Disease;

@Repository
public class DiseaseDAO{

	@Autowired
	private SessionFactory sessionFactory;

	public void saveDisease(Disease disease) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(disease);
		tx.commit();
		session.close();
	}
	
	public List<Integer> getPatientsWithDisease(Disease disease){
		Session session = this.sessionFactory.openSession();
		String sql = "SELECT p.* FROM Patient p, Patient_Disease pd, Disease d "
				+ "WHERE p.id = pd.patient_id"
				+ "  AND pd.disease_id = d.id"
				+ "  AND d.id = :disease_id";
		
		Query sqlQuery = session.createSQLQuery(sql).setParameter("disease_id", disease.getId());
		List<Integer> patientIds = buildPatientList(sqlQuery.list());
		session.close();
		return patientIds;
	}
	
	public Map<String, BigInteger> retrievePatientCountByDisease(){
		Session session = this.sessionFactory.openSession();
		Map<String, BigInteger> patientCountByDisease = new HashMap<String, BigInteger>();
		
		String sql = "SELECT d.name, COUNT(p.id) "
				+ "FROM Patient p, Patient_Disease pd, Disease d "
				+ "WHERE p.id = pd.patient_id "
				+ "AND pd.disease_id = d.id "
				+ "GROUP BY d.id, d.name";
		
		Query sqlQuery = session.createSQLQuery(sql);
		buildGroupedDiseaseCount(patientCountByDisease, sqlQuery.list());
		session.close();
		return patientCountByDisease;
	}
	
	@SuppressWarnings("rawtypes")
	private void buildGroupedDiseaseCount(Map<String, BigInteger> patientCountByDisease, List list) {
		for(Object groupedPatientObject : list){
			Object[] objectArray = (Object[]) groupedPatientObject;
			patientCountByDisease.put((String)objectArray[0], (BigInteger)objectArray[1]);
		}
	}

	@SuppressWarnings("rawtypes")
	private List<Integer> buildPatientList(List list) {
		List<Integer> builtPatientList = new LinkedList<Integer>();
		
		for(Object patientObject : list){
			Object[] objectArray = (Object[]) patientObject;
			
			builtPatientList.add((Integer) objectArray[0]);
		}
		return builtPatientList;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}