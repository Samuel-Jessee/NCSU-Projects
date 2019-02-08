package edu.ncsu.csc.itrust.action;

import java.sql.Timestamp;
import java.util.List;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.old.beans.FitnessBean;
import edu.ncsu.csc.itrust.model.old.dao.DAOFactory;
import edu.ncsu.csc.itrust.model.old.dao.mysql.FitnessDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PatientDAO;
import edu.ncsu.csc.itrust.model.old.dao.mysql.PersonnelDAO;

public class ViewPatientFitnessSummaryAction {
	private long loggedInMID;

	private FitnessDAO fitnessDAO;
	private PatientDAO patientDAO;
	private PersonnelDAO personnelDAO;

	public ViewPatientFitnessSummaryAction(DAOFactory factory, long loggedInMID) {
		this.loggedInMID = loggedInMID;
		fitnessDAO = factory.getFitnessDAO();
		patientDAO = factory.getPatientDAO();
		personnelDAO = factory.getPersonnelDAO();
		// Transaction logged code
	}

	public List<FitnessBean> getStepInfo(long mid, Timestamp sDate, Timestamp eDate) throws DBException {
		return fitnessDAO.getFitnessRange(mid, sDate, eDate);
	}
	
	public List<FitnessBean> getWeeklyInfo(long mid, Timestamp sDate, Timestamp eDate) throws DBException {
		if(sDate.getDate() - sDate.getDay() < 0) {
			sDate.setMonth(sDate.getMonth() - 1);
			if( sDate.getMonth() == 1 || sDate.getMonth() == 3 || sDate.getMonth() == 5 || sDate.getMonth() == 7 || sDate.getMonth() == 8 || sDate.getMonth() == 10 || sDate.getMonth() == 12) {
				sDate.setDate(31 - Math.abs(sDate.getDate() - sDate.getDay()));
			} else if (sDate.getMonth() == 2){
				if(sDate.getYear() % 4 == 0) {
					sDate.setDate(29 - Math.abs(sDate.getDate() - sDate.getDay()));
				} else {
					sDate.setDate(28 - Math.abs(sDate.getDate() - sDate.getDay()));
				}
			} else {
				sDate.setDate(30 - Math.abs(sDate.getDate() - sDate.getDay()));
			}
		} else {
			sDate.setDate(sDate.getDate() - sDate.getDay());
		}
		if(eDate.getMonth() == 1 || eDate.getMonth() == 3 || eDate.getMonth() == 5 || eDate.getMonth() == 7 || eDate.getMonth() == 8 || eDate.getMonth() == 10 || eDate.getMonth() == 12) {
			if(eDate.getDate() + (6 - eDate.getDay()) > 31) {
				eDate.setMonth(eDate.getMonth()+1);
				eDate.setDate(31 - (eDate.getDate() + (6 - eDate.getDay())));
			} else {
				eDate.setDate(eDate.getDate() + (6 - eDate.getDay()));
			}
		} else if(eDate.getMonth() == 2) {
			if(sDate.getYear() % 4 == 0) {
				if(eDate.getDate() + (6 - eDate.getDay()) > 29) {
					eDate.setMonth(eDate.getMonth()+1);
					eDate.setDate(29 - (eDate.getDate() + (6 - eDate.getDay())));
				} else {
					eDate.setDate(eDate.getDate() + (6 - eDate.getDay()));
				}
			} else {
				if(eDate.getDate() + (6 - eDate.getDay()) > 28) {
					eDate.setMonth(eDate.getMonth()+1);
					eDate.setDate(28 - (eDate.getDate() + (6 - eDate.getDay())));
				} else {
					eDate.setDate(eDate.getDate() + (6 - eDate.getDay()));
				}
			}
		} else {
			if(eDate.getDate() + (6 - eDate.getDay()) > 30) {
				eDate.setMonth(eDate.getMonth()+1);
				eDate.setDate(30 - (eDate.getDate() + (6 - eDate.getDay())));
			} else {
				eDate.setDate(eDate.getDate() + (6 - eDate.getDay()));
			}
		}

		return fitnessDAO.getFitnessRange(mid, sDate, eDate);
	}
}