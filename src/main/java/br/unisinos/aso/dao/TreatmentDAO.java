package br.unisinos.aso.dao;

import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.unisinos.aso.model.Treatment;

@Repository
public class TreatmentDAO extends BaseDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	public void save(Treatment treatment){
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(treatment);
		tx.commit();
		session.close();
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}