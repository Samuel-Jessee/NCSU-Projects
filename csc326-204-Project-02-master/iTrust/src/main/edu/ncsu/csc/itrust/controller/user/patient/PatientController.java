package edu.ncsu.csc.itrust.controller.user.patient;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import edu.ncsu.csc.itrust.controller.user.UserController;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.DataBean;
import edu.ncsu.csc.itrust.model.ValidationFormat;
import edu.ncsu.csc.itrust.model.user.User;
import edu.ncsu.csc.itrust.model.user.UserMySQLConverter;
import edu.ncsu.csc.itrust.model.user.patient.Patient;
import edu.ncsu.csc.itrust.model.user.patient.PatientMySQLConverter;

@ManagedBean(name="patient_controller")
public class PatientController extends UserController implements Serializable{
	private DataBean<Patient> patientData;
	public PatientController() throws DBException {
		patientData =  new PatientMySQLConverter(); //Constructor needs to initialize patientData. patientData is used in other methods
		//userData = new UserMySQLConverter(); //an example of initialization from another java class
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -164769440967020045L;
	

	
	public boolean doesPatientExistWithID(String mid) throws DBException{
		User user = null;
		if( mid == null) return false;
		if(!(ValidationFormat.NPMID.getRegex().matcher(mid).matches())) return false;
		long id = -1;
		try{
			id = Long.parseLong(mid);
		}
		catch(NumberFormatException ne){
			return false;
		}
		//System.out.println("ID: " + id); //Print Statements for testing usability
		if(null!=patientData)user = patientData.getByID(id);
		if(!(user == null)){
				return true;
		}
		else{
			return false;
		}

				
	}


}
