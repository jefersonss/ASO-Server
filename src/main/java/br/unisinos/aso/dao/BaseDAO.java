package br.unisinos.aso.dao;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

public class BaseDAO {
	protected static SessionFactory factory;
	protected Session session;
	protected Transaction transaction;
	
//	static{
//		Configuration configuration = new Configuration();
//		configuration.configure();
//		factory = configuration.buildSessionFactory();
//	}
	
	public void openConnection(){
		if(null != null && !session.isOpen())
			session = factory.openSession();
		else
			session = factory.getCurrentSession();
		transaction = session.beginTransaction();
	}
	
	public void commitAndCloseConnection() {
		try {
			transaction.commit();
			session.close();
			factory.close();
		} catch (SessionException e) {
			System.out.println("Session already closed");
		}
	}
}