package edu.ncsu.csc.itrust.controller.laborreport;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import edu.ncsu.csc.itrust.controller.iTrustController;
import edu.ncsu.csc.itrust.controller.diagnosis.DiagnosisController;
import edu.ncsu.csc.itrust.controller.obstetricsInitialization.ObstetricsInitializationController;
import edu.ncsu.csc.itrust.controller.obstetricsOfficeVisit.ObstetricsOfficeVisitController;
import edu.ncsu.csc.itrust.controller.officeVisit.OfficeVisitController;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.diagnosis.Diagnosis;
import edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitialization;
import edu.ncsu.csc.itrust.model.obstetricsInitialization.PreviousPregnancyInfo;
import edu.ncsu.csc.itrust.model.obstetricsOfficeVisit.ObstetricsOfficeVisit;
import edu.ncsu.csc.itrust.model.officeVisit.OfficeVisit;
import edu.ncsu.csc.itrust.model.old.beans.AllergyBean;
import edu.ncsu.csc.itrust.model.old.beans.PatientBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.AllergyDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.webutils.SessionUtils;

public class LaborReportController extends iTrustController {

	private SessionUtils sessionUtils;
	private ObstetricsInitializationController OI;
	private ObstetricsOfficeVisitController OOV;
	private DiagnosisController DC;
	private OfficeVisitController OVC;
	private PatientDAO pDAO;
	private AllergyDAO allergyDAO;

	public LaborReportController() throws DBException {
		sessionUtils = SessionUtils.getInstance();
		DAOFactory factory = DAOFactory.getProductionInstance();
		OI = new ObstetricsInitializationController();
		OOV = new ObstetricsOfficeVisitController();
		DC = new DiagnosisController();
		OVC = new OfficeVisitController();
		pDAO = new PatientDAO(factory);
		allergyDAO = new AllergyDAO(factory);
	}
	
	public LaborReportController(DataSource ds, PatientDAO patientDAO, AllergyDAO allergyDAO) throws DBException {
		sessionUtils = SessionUtils.getInstance();
		DAOFactory factory = DAOFactory.getProductionInstance();
		OI = new ObstetricsInitializationController();
		OOV = new ObstetricsOfficeVisitController();
		DC = new DiagnosisController(ds);
		OVC = new OfficeVisitController(ds);
		pDAO = patientDAO;
		this.allergyDAO = allergyDAO;
	}

	/**
-	 * Constructor for testing purposes
-	 * 
-	 * @param ds
-	 * @throws DBException
-	 */
	public LaborReportController(DataSource ds) throws DBException {
		sessionUtils = SessionUtils.getInstance();
		DAOFactory factory = DAOFactory.getProductionInstance();
		OI = new ObstetricsInitializationController(ds);
		OOV = new ObstetricsOfficeVisitController(ds);
		DC = new DiagnosisController(ds);
		OVC = new OfficeVisitController(ds);
		pDAO = new PatientDAO(factory);
		allergyDAO = new AllergyDAO(factory);
	}

	public LaborReport getReport(long patientMID) throws DBException{
		LaborReport labor = new LaborReport();

		labor.setPatientMID(patientMID);

		PatientBean patient = pDAO.getPatient(patientMID);
		labor.setBloodtype(patient.getBloodType());

		List<PreviousPregnancyInfo> prevPreg = OI.getPreviousPregnancyInfoForPatient(patientMID + "");
		labor.setPastPreg(prevPreg);

		List<ObstetricsInitialization> obsIni = OI.getObstetricsInitializationForPatient(patientMID + "");

		List<ObstetricsOfficeVisit> obsOV = OOV.getObstetricsVisitForPatient(patientMID + "");
		labor.setObOfficeVisit(obsOV);

		ObstetricsInitialization mostRecentInitilization = obsIni.get(0);
		ObstetricsOfficeVisit mostRecentOfficeVisit = null;
		if(!obsOV.isEmpty()){
			mostRecentOfficeVisit = obsOV.get(0);
		}

		labor.setRhFlag(false);
		labor.setHighBloodPressure(false);
		labor.setAdvancedMaternalAge(false);
		labor.setPreExisitngConditions(false);
		labor.setMaternalAllergies(false);
		labor.setLowLyingPlacenta(false);
		labor.setGeneticPotential(false);
		labor.setAbnormalFetalHeartRate(false);
		labor.setMultiplePreg(false);
		labor.setAtypicalWeightChange(false);
		labor.setHyperemesisGravidarum(false);
		labor.setHypothyroidism(false);

		if (mostRecentOfficeVisit != null) {
			// setting Low Lying Placenta
			labor.setLowLyingPlacenta(mostRecentOfficeVisit.isLowLyingPlacenta());

			// setting bloodPressure
			String bloodPressure = mostRecentOfficeVisit.getBloodPressure();
			String[] bloodPressureSplit = bloodPressure.split("/");
			boolean firstBP = false;
			boolean secondBP = false;
			if (Integer.parseInt(bloodPressureSplit[0]) >= 140)
				firstBP = true;
			if (Integer.parseInt(bloodPressureSplit[1]) >= 80)
				secondBP = true;
			if (firstBP || secondBP)
				labor.setHighBloodPressure(true);

			// calculating fetal heart rate
			int fetalHeartRate = mostRecentOfficeVisit.getFHR();
			if (fetalHeartRate > 120 || fetalHeartRate < 80)
				labor.setAbnormalFetalHeartRate(true);

			// calculate amount of fetus
			int amtFetus = mostRecentOfficeVisit.getNumFetus();
			if (amtFetus > 1)
				labor.setMultiplePreg(true);

			// calculating difference in weight
			if (obsOV.size() >= 2) {
				Double mostRecentWeight = mostRecentOfficeVisit.getWeight();
				Double lastWeight = obsOV.get(obsOV.size() - 1).getWeight();
				Double difference = mostRecentWeight - lastWeight;
				if (difference > 35 || difference < 15)
					labor.setAtypicalWeightChange(true);
			}
			
			labor.setGeneticPotential(mostRecentOfficeVisit.isHighgenetic());

		}

		// no need to check for null since there has to be atleast one
		// initlization
		// for the labor report to appear

		labor.setRhFlag(mostRecentInitilization.isRH());
		LocalDate EDD = mostRecentInitilization.getEDD();
		labor.setEstimatedDeliveryDate(EDD);

		if (patient.getAge() >= 35)
			labor.setAdvancedMaternalAge(true);
		// if the estimated due date is after today, calculate age at birth
		if (EDD.isAfter(LocalDate.now())) {
			long days = EDD.until(LocalDate.now(), ChronoUnit.DAYS);
			Date birthday = patient.getDateOfBirth();
			LocalDate newBirthday = new Date(birthday.getTime() + days * 86400000).toInstant()
					.atZone(ZoneId.systemDefault()).toLocalDate();
			;
			Period p = Period.between(newBirthday, LocalDate.now());
			if (p.getYears() >= 35)
				labor.setAdvancedMaternalAge(true);
		}

		// getting diagnosis for patient
		List<OfficeVisit> officeVisit = OVC.getOfficeVisitsForPatient(patientMID + "");
		List<Diagnosis> diagnosisList = new ArrayList<Diagnosis>();
		for (int i = 0; i < officeVisit.size(); i++) {
			List<Diagnosis> tempDiagnosisList = DC.getDiagnosesByOfficeVisit(officeVisit.get(i).getVisitID());
			for (int j = 0; j < tempDiagnosisList.size(); j++) {
				Diagnosis diagnosis = tempDiagnosisList.get(j);
				if (diagnosis.getName().equals("Hypothyroidism")) {
					labor.setHypothyroidism(true);
					diagnosisList.add(diagnosis);
				} else if (diagnosis.getName().equals("Hyperemesis gravidarum")) {
					labor.setHyperemesisGravidarum(true);
					diagnosisList.add(diagnosis);
				} else if (diagnosis.getName().toLowerCase().contains("cancer")) {
					diagnosisList.add(diagnosis);
				} else if (diagnosis.getName().toLowerCase().contains("diabetes")) {
					diagnosisList.add(diagnosis);
				} else if (diagnosis.getName().toLowerCase().contains("immunodeficiency")) {
					diagnosisList.add(diagnosis);
				} else if (diagnosis.getName().toLowerCase().contains("syphilis")) {
					diagnosisList.add(diagnosis);
				} else if (diagnosis.getName().toLowerCase().contains("chlamydia")) {
					diagnosisList.add(diagnosis);
				} else if (diagnosis.getIcdCode().isChronic()) {
					diagnosisList.add(diagnosis);
				}
			}
		}
		labor.conditions = diagnosisList;
		if(diagnosisList.size() > 0) labor.setPreExisitngConditions(true);

		// getting allergies
		List<AllergyBean> allergies = allergyDAO.getAllergies(patientMID);
		labor.allergies = allergies;
		if(allergies.size() > 0) labor.setMaternalAllergies(true);

		return labor;
	}

	public void close() {
		OOV.close();
		OI.close();
	}

}
