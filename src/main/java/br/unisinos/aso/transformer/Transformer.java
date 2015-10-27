package br.unisinos.aso.transformer;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.googlecode.charts4j.*;

import br.unisinos.aso.dao.*;
import br.unisinos.aso.model.*;

@Component
public class Transformer {

	@Autowired
	private DiseaseDAO diseaseDAO;
	@Autowired
	private PatientDAO patientDAO;
	@Autowired
	private MedicationDAO medicationDAO;
//	@Autowired
//	private ExamDAO examDAO;
	private List<Patient> patientsWithDisease;
	SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

	public TransformedInfo transformPatientInfo(Patient patientTransforming) {
		Set<Treatment> patientTreatment = patientTransforming.getTreatment();
		TransformedInfo info = new TransformedInfo();
		
		info.setVitalSignsEvolutionChart(generateVitalSignsEvolutionChart(patientTransforming));
		
		for (Treatment treatment : patientTreatment) {
			String bloodPressureChartUrl = generateBloodPressureCharts(treatment.getExam());
			info.setChartUrl(bloodPressureChartUrl);
			System.out.println(bloodPressureChartUrl);
			
			String bloodPressureComparisonChartUrl = generateBloodPressureChartComparison(patientTransforming);
			info.setBloodPressureComparisonChartUrl(bloodPressureComparisonChartUrl);
			System.out.println(bloodPressureComparisonChartUrl);
		}
		
		patientsWithDisease.remove(patientTransforming);
		info.setPatientsWithSameDiagnosis(patientsWithDisease);
		List<Patient> patientsTakingSameMedication = listPatientsTakingSameMedication(patientTransforming.getAdministeredMedication());
		
		patientsTakingSameMedication.remove(patientTransforming);
		info.setPatientsTakingSameMedication(patientsTakingSameMedication);

		return info;
	}
	
	private List<String> generateVitalSignsEvolutionChart(Patient patient){
		List<String> urlList = new LinkedList<String>();
		Map<String, List<String>> examResults = buildResultsFromExamList(patient.getAllExams());
		
		for(String key : examResults.keySet()){
			if(key.endsWith("2")) continue;
			else key = key.substring(0, key.length()-1);
			
			List<String> resultList = examResults.get(key+"1");
			double[] resultArray = new double[resultList.size()];
			for(int i = 0; i<resultList.size(); i++){
				resultArray[i] = Double.parseDouble(resultList.get(i));
			}
			
			sortResultsInAscendingOrder(resultList);
			double maxValue = Double.parseDouble(resultList.get(resultList.size()-1));
			double minValue = Double.parseDouble(resultList.get(0));
			minValue = minValue < 5 ? 0 : minValue -5; 

			Line line1 = Plots.newLine(DataUtil.scaleWithinRange(minValue, maxValue, resultArray), Color.newColor("CA3D05"), key+" "+resultArray[resultArray.length-1]);
	        LineChart chart = GCharts.newLineChart(line1);
	        
	        chart.setSize(300, 150);
	        chart.setTitle(key+" evolution", Color.WHITE, 14);
	        int xStepSize = 100/(resultArray.length-1);
			double yStepSize = maxValue/(resultArray.length-1);
			chart.setGrid(xStepSize, yStepSize, 3, 2);
	        
	        chart.addYAxisLabels(AxisLabelsFactory.newNumericRangeAxisLabels(minValue, maxValue));
	        
	        List<Double> wrapperArray = wrapPrimitiveArrayInObjectList(resultArray, xStepSize);
        	chart.addXAxisLabels(AxisLabelsFactory.newAxisLabels(examResults.get(key+"2"), wrapperArray));
	        
	        urlList.add(chart.toURLString());
		}
		
		return urlList;
	}

	private List<Double> wrapPrimitiveArrayInObjectList(double[] resultArray, int xStepSize) {
		List<Double> wrapperArray = new LinkedList<Double>();
		for(double i = resultArray.length; i>0; i--){
			double lastValue = wrapperArray.size() <= 1 ? 0 : wrapperArray.get(wrapperArray.size()-1);
			wrapperArray.add(lastValue+xStepSize);
		}
		return wrapperArray;
	}

	private void sortResultsInAscendingOrder(List<String> resultList) {
		Collections.sort(resultList, new Comparator<String>() {
			public int compare(String o1, String o2) {
				double dO1 = Double.parseDouble(o1);
				double dO2 = Double.parseDouble(o2);
				return (int) (dO1 - dO2);
			}
		});
	}
	
	private Map<String, List<String>> buildResultsFromExamList(List<Exam> allExams) {
		Map<String, List<String>> examResultMap = new HashMap<String, List<String>>();
		List<String> elegibleExams = Arrays.asList("Heart Rate", "Temperature", "Respirations", "SPO2", "BPS", "BPD");
		for(Exam exam : allExams){
			if(!elegibleExams.contains(exam.getName())) continue;
			
			List<String> results = examResultMap.get(exam.getName()+"1");
			List<String> resultDates = examResultMap.get(exam.getName()+"2");
			if(null == results) {
				results = new LinkedList<String>();
				results.add("0");
				resultDates = new LinkedList<String>();
				resultDates.add(" ");
			}
			results.add(exam.getResults());
			resultDates.add(dateFormatter.format(exam.getDate()));
			examResultMap.put(exam.getName()+"1", results);
			examResultMap.put(exam.getName()+"2", resultDates);
		}
		return examResultMap;
	}

	public String generatePatientsByDiseaseChart() {
		Map<String, BigInteger> patientCountByDisease = diseaseDAO.retrievePatientCountByDisease();
		int totalCountOfPatients = getTotalCountOfPatients(patientCountByDisease);
		List<Slice> pieChartSlices = new LinkedList<Slice>();
		
		for (String key : patientCountByDisease.keySet()) {
			BigInteger numOfPatients = patientCountByDisease.get(key);
			int percentOfPatients = (numOfPatients.intValue() * 100) / totalCountOfPatients;
			
			pieChartSlices.add(Slice.newSlice(percentOfPatients, key));
		}

        PieChart chart = GCharts.newPieChart(pieChartSlices);
        chart.setTitle("Patients by diagnosis", Color.BLACK, 16);
        chart.setSize(300, 150);
        chart.setThreeD(true);
        String chartUrl = chart.toURLString();
        System.out.println(chartUrl);
        return chartUrl;
	}

	private String generateBloodPressureCharts(Set<Exam> exams) {
		final int MAX_PRESSURE = 51;
		List<Double> diastolicLevels = new LinkedList<Double>();
		List<Double> systolicLevels = new LinkedList<Double>();
		List<String> examDates = new LinkedList<String>();
		
		for (Exam exam : exams) {
			if(exam.getName().contains("systolic")){
				systolicLevels.add(Double.parseDouble(exam.getResults()));
				examDates.add(dateFormatter.format(exam.getDate()));
			}
			else
				diastolicLevels.add(Double.parseDouble(exam.getResults()));
		}
		
		BarChart chart = createChart(MAX_PRESSURE, diastolicLevels, systolicLevels);
		defineChartLabels(MAX_PRESSURE, examDates, chart);
		
		chart.setHorizontal(true);
		chart.setSize(320, 290);
		chart.setSpaceBetweenGroupsOfBars(15);

		chart.setTitle("Diastolic/Systolic blood pressure history", Color.BLACK, 16);
		chart.setGrid((50.0 / MAX_PRESSURE) * 20, 600, 3, 2);
		
		return chart.toURLString();
	}

	private String generateBloodPressureChartComparison(Patient patient) {
		final int MAX_PRESSURE = 51;
		List<Integer> patientsIdsWithDisease = diseaseDAO.getPatientsWithDisease(patient.getDiseases().iterator().next());
		patientsWithDisease = patientDAO.getPatientsWithId(patientsIdsWithDisease);
		List<Double> diastolicBloodByPatient = new LinkedList<Double>();
		List<Double> sistolicBloodByPatient = new LinkedList<Double>();
		List<String> patientNames = new LinkedList<String>();
		
		for(Patient patiDise : patientsWithDisease){
			diastolicBloodByPatient.add(getAverage(patiDise.getAllExams(), "diastolic"));
			sistolicBloodByPatient.add(getAverage(patiDise.getAllExams(), "systolic"));
			patientNames.add(patiDise.getName());
		}
		
		BarChart chart = createChart(MAX_PRESSURE, diastolicBloodByPatient, sistolicBloodByPatient);
		defineChartLabels(MAX_PRESSURE, patientNames, chart);
		
		chart.setHorizontal(true);
		chart.setSize(300, 225);
		chart.setSpaceBetweenGroupsOfBars(15);
		
		chart.setTitle("Diastolic/Systolic blood pressure comparison", Color.BLACK, 16);
		chart.setGrid((50.0 / MAX_PRESSURE) * 20, 600, 3, 2);
		
		return chart.toURLString();
	}

	private BarChart createChart(final int MAX_PRESSURE, List<Double> diastolicLevels, List<Double> systolicLevels) {
		Data diastolicData = DataUtil.scaleWithinRange(0, MAX_PRESSURE, diastolicLevels);
		Data systolicData = DataUtil.scaleWithinRange(0, MAX_PRESSURE, systolicLevels);
		
		BarChartPlot diastolic = Plots.newBarChartPlot(diastolicData, Color.RED, "Diastolic");
		BarChartPlot systolic = Plots.newBarChartPlot(systolicData, Color.BLUE, "Systolic");

		BarChart chart = GCharts.newBarChart(diastolic, systolic);
		return chart;
	}
	
	private void defineChartLabels(final int MAX_PRESSURE, List<String> names, BarChart chart) {
		AxisStyle axisStyle = AxisStyle.newAxisStyle(Color.BLACK, 13,AxisTextAlignment.CENTER);
		AxisLabels nameLabels = AxisLabelsFactory.newAxisLabels(names);
		nameLabels.setAxisStyle(axisStyle);
		AxisLabels yValues = AxisLabelsFactory.newAxisLabels("Pressure Level", 50.0);
		yValues.setAxisStyle(axisStyle);
		AxisLabels xValues = AxisLabelsFactory.newNumericRangeAxisLabels(0,MAX_PRESSURE);
		xValues.setAxisStyle(axisStyle);
		
		chart.addXAxisLabels(xValues);
		chart.addXAxisLabels(yValues);
		chart.addYAxisLabels(nameLabels);
		chart.addTopAxisLabels(xValues);
	}
	
	private int getTotalCountOfPatients(Map<String, BigInteger> patientCountByDisease) {
		int totalOfPatients = 0;
		
		for(Entry<String, BigInteger> patientCount : patientCountByDisease.entrySet())
			totalOfPatients += patientCount.getValue().intValue();
		return totalOfPatients;
	}

	private List<Patient> listPatientsTakingSameMedication(Set<Medication> administeredMedication) {
		List<Integer> patientIds = medicationDAO.getPatientsTakingSameMedication(administeredMedication);
		List<Patient> patients = patientDAO.getPatientsWithId(patientIds);
		return patients;
	}

	private Double getAverage(List<Exam> allExams, String examType) {
		int numberOfExams = 0;
		double sumValue = 0D;
		
		for(Exam exam : allExams)
			if(exam.getName().contains(examType)){
				numberOfExams++;
				sumValue += Double.parseDouble(exam.getResults());
			}
		
		return sumValue / numberOfExams;
	}
}