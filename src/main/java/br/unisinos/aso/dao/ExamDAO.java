package br.unisinos.aso.dao;

import java.util.List;

import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.unisinos.aso.model.Exam;

@Repository
public class ExamDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void saveExam(Exam exam){
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(exam);
		tx.commit();
		session.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<Exam> searchExamByName(String name){
		Session session = this.sessionFactory.openSession();
		String hql = "FROM Exam E WHERE E.name LIKE :exam_name";
		Query query = session.createQuery(hql).setParameter("exam_name", name+"%");
		List<Exam> exams = query.list();
		session.close();
		return exams;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}