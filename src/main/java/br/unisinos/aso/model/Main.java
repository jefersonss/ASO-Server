package br.unisinos.aso.model;

import java.sql.Date;

import org.joda.time.DateTime;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.common.collect.Sets;

import br.unisinos.aso.dao.PatientDAO;

public class Main {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		Date today = new Date(new java.util.Date().getTime());
		
		Patient patient = new Patient();
		patient.setAge(25);
		patient.setGender("F");
		patient.setName("Mariazinha");
		patient.setRoom("21A");
		patient.setLastEnteredDate(today);
		
		Disease disease = new Disease();
		disease.setName("Cancer");
		patient.setDiseases(Sets.newHashSet(disease));
		
		Treatment treatment = new Treatment();
		treatment.setObservations("Cancer in the lungs due to cigarrete");
		
		Exam exam = new Exam();
		exam.setName("Arterial diastolic pressure");
		DateTime jodaTime = new DateTime(today.getTime());
		exam.setDate(today);
		exam.setResults("15");
		
		Exam exam2 = new Exam();
		exam2.setName("Arterial systolic pressure");
		exam2.setDate(today);
		exam2.setResults("8");
		
		Exam exam3 = new Exam();
		exam3.setName("Arterial diastolic pressure");
		exam3.setDate(new Date(jodaTime.minusDays(1).getMillis()));
		exam3.setResults("12");
		
		Exam exam4 = new Exam();
		exam4.setName("Arterial systolic pressure");
		exam4.setDate(new Date(jodaTime.minusDays(1).getMillis()));
		exam4.setResults("8");
		
		Exam exam5 = new Exam();
		exam5.setName("Arterial diastolic pressure");
		exam5.setDate(new Date(jodaTime.minusDays(2).getMillis()));
		exam5.setResults("17");
		
		Exam exam6 = new Exam();
		exam6.setName("Arterial systolic pressure");
		exam6.setDate(new Date(jodaTime.minusDays(2).getMillis()));
		exam6.setResults("10");
		
		//############################################
		//HEART RATE
		Exam exam15 = new Exam();
		exam15.setName("Heart Rate");
		exam15.setDate(new Date(jodaTime.minusDays(2).getMillis()));
		exam15.setResults("78");
		//HEART RATE
		Exam exam16 = new Exam();
		exam16.setName("Heart Rate");
		exam16.setDate(new Date(jodaTime.minusDays(2).getMillis()));
		exam16.setResults("95");
		//HEART RATE
		Exam exam17 = new Exam();
		exam17.setName("Heart Rate");
		exam17.setDate(new Date(jodaTime.minusDays(2).getMillis()));
		exam17.setResults("82");
		//TEMPERATURE
		Exam exam18 = new Exam();
		exam18.setName("Temperature");
		exam18.setDate(new Date(jodaTime.minusDays(2).getMillis()));
		exam18.setResults("36");
		//TEMPERATURE
		Exam exam19 = new Exam();
		exam19.setName("Temperature");
		exam19.setDate(new Date(jodaTime.minusDays(2).getMillis()));
		exam19.setResults("37");
		//TEMPERATURE
		Exam exam20 = new Exam();
		exam20.setName("Temperature");
		exam20.setDate(new Date(jodaTime.minusDays(2).getMillis()));
		exam20.setResults("40");
		//RESPIRATIONS
		Exam exam21 = new Exam();
		exam21.setName("Respirations");
		exam21.setDate(new Date(jodaTime.minusDays(2).getMillis()));
		exam21.setResults("25");
		//RESPIRATIONS
		Exam exam22 = new Exam();
		exam22.setName("Respirations");
		exam22.setDate(new Date(jodaTime.minusDays(2).getMillis()));
		exam22.setResults("23");
		//RESPIRATIONS
		Exam exam23 = new Exam();
		exam23.setName("Respirations");
		exam23.setDate(new Date(jodaTime.minusDays(2).getMillis()));
		exam23.setResults("25");
		//SPO2
		Exam exam24 = new Exam();
		exam24.setName("SPO2");
		exam24.setDate(new Date(jodaTime.minusDays(2).getMillis()));
		exam24.setResults("100");
		//SPO2
		Exam exam25 = new Exam();
		exam25.setName("SPO2");
		exam25.setDate(new Date(jodaTime.minusDays(2).getMillis()));
		exam25.setResults("95");
		//SPO2
		Exam exam26 = new Exam();
		exam26.setName("SPO2");
		exam26.setDate(new Date(jodaTime.minusDays(2).getMillis()));
		exam26.setResults("98");
		//BPS
		Exam exam27 = new Exam();
		exam27.setName("BPS");
		exam27.setDate(new Date(jodaTime.minusDays(2).getMillis()));
		exam27.setResults("115");
		//BPS
		Exam exam28 = new Exam();
		exam28.setName("BPS");
		exam28.setDate(new Date(jodaTime.minusDays(2).getMillis()));
		exam28.setResults("120");
		//BPS
		Exam exam29 = new Exam();
		exam29.setName("BPS");
		exam29.setDate(new Date(jodaTime.minusDays(2).getMillis()));
		exam29.setResults("110");
		//BPD
		Exam exam30 = new Exam();
		exam30.setName("BPD");
		exam30.setDate(new Date(jodaTime.minusDays(2).getMillis()));
		exam30.setResults("50");
		//BPD
		Exam exam31 = new Exam();
		exam31.setName("BPD");
		exam31.setDate(new Date(jodaTime.minusDays(2).getMillis()));
		exam31.setResults("65");
		//BPD
		Exam exam32 = new Exam();
		exam32.setName("BPD");
		exam32.setDate(new Date(jodaTime.minusDays(2).getMillis()));
		exam32.setResults("80");
		//############################################
		
		treatment.setExam(Sets.newHashSet(exam, exam2, exam3, exam4, exam5, exam6,exam15, exam16, exam17, exam18, exam19, exam20, exam21, exam22, exam23, exam24, exam25, exam26, exam27, exam28, exam29, exam30, exam31, exam32));
		
		Medication admMedication = new Medication();
		admMedication.setName("Celestamine");
		admMedication.setType("Xarope");
		admMedication.setDateAdministered(today);
		patient.setAdministeredMedication(Sets.newHashSet(admMedication));
		
		Medication recMedication = new Medication();
		recMedication.setName("Dipirona");
		recMedication.setType("Pills");
		recMedication.setDateAdministered(today);
		patient.setRecommendedMedication(Sets.newHashSet(recMedication));
		
		patient.setTreatment(Sets.newHashSet(treatment));
		
		PatientDAO dao = context.getBean(PatientDAO.class);

		
		dao.savePatient(patient);
		

		
		Patient patient2 = new Patient();
		patient2.setAge(28);
		patient2.setGender("M");
		patient2.setName("Joazinho");
		patient2.setRoom("22A");
		patient2.setLastEnteredDate(today);
		
		
		Treatment treatment2 = new Treatment();
		treatment2.setObservations("Cancer in the lungs due to cigarrete");
		
		Exam exam7 = new Exam();
		exam7.setName("Arterial diastolic pressure");
		DateTime jodaTime2 = new DateTime(today.getTime());
		exam7.setDate(today);
		exam7.setResults("12");
		
		Exam exam8 = new Exam();
		exam8.setName("Arterial systolic pressure");
		exam8.setDate(today);
		exam8.setResults("8");
		
		Exam exam9 = new Exam();
		exam9.setName("Arterial diastolic pressure");
		exam9.setDate(new Date(jodaTime2.minusDays(1).getMillis()));
		exam9.setResults("13");
		
		Exam exam10 = new Exam();
		exam10.setName("Arterial systolic pressure");
		exam10.setDate(new Date(jodaTime2.minusDays(1).getMillis()));
		exam10.setResults("10");
		
		Exam exam11 = new Exam();
		exam11.setName("Arterial diastolic pressure");
		exam11.setDate(new Date(jodaTime2.minusDays(2).getMillis()));
		exam11.setResults("20");
		
		Exam exam12 = new Exam();
		exam12.setName("Arterial systolic pressure");
		exam12.setDate(new Date(jodaTime2.minusDays(2).getMillis()));
		exam12.setResults("15");
		
		treatment2.setExam(Sets.newHashSet(exam7, exam8, exam9, exam10, exam11, exam12));
		
		Medication admMedication2 = new Medication();
		admMedication2.setName("Celestamine");
		admMedication2.setType("Xarope");
		admMedication2.setDateAdministered(today);
		patient2.setAdministeredMedication(Sets.newHashSet(admMedication2));
		
		Medication recMedication2 = new Medication();
		recMedication2.setName("Dipirona");
		recMedication2.setType("Pills");
		recMedication2.setDateAdministered(today);
		patient2.setRecommendedMedication(Sets.newHashSet(recMedication2));
		
		patient2.setTreatment(Sets.newHashSet(treatment2));
		
		
		dao.savePatient(patient2);
		
//		DiseaseDAO diseaseDao = context.getBean(DiseaseDAO.class);
//		diseaseDao.retrievePatientCountByDisease();
		
		context.close();
//		dao.commitAndCloseConnection();
		
//		List<Patient> patients = dao.getPatients();
//		System.out.println(patients);
		
	}
}