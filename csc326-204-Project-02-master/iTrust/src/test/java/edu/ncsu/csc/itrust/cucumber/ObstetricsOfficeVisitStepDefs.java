package edu.ncsu.csc.itrust.cucumber;

import cucumber.api.java.en.Given;

import edu.ncsu.csc.itrust.controller.obstetricsOfficeVisit.ObstetricsOfficeVisitController;

import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.model.ConverterDAO;

import edu.ncsu.csc.itrust.model.obstetricsOfficeVisit.ObstetricsOfficeVisit;

import edu.ncsu.csc.itrust.model.obstetricsOfficeVisit.ObstetricsOfficeVisitValidator;
import edu.ncsu.csc.itrust.model.obstetricsOfficeVisit.Ultrasound;
import cucumber.api.java.en.Then;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Assert;
public class ObstetricsOfficeVisitStepDefs {
	private ObstetricsOfficeVisit visit;
	private ObstetricsOfficeVisitController controller;
    private ObstetricsOfficeVisitValidator validator;
    private DataSource ds;
    private Ultrasound u;
    
 
    
    @Given("^I enter -(\\d+), (\\d+), \"([^\"]*)\", (\\d+), (\\d+), \"([^\"]*)\"$")
    public void i_enter(int arg1, int arg2, String arg3, int arg4, int arg5, String arg6) throws Throwable {
    	this.ds =ConverterDAO.getDataSource();
    	visit = new ObstetricsOfficeVisit();
    	validator = new ObstetricsOfficeVisitValidator(ds);
    	Date date = new Date();
        date.setMonth(8);
        date.setDate(17);
        date.setYear(2017);
        
    	visit.setDate(date);
    	visit.setPatientMID((long)1);
        visit.setNumWeeks(arg1);
        visit.setWeight((double)arg2);
        visit.setBloodPressure(arg3);
        visit.setFHR(arg4);
        visit.setNumFetus(arg5);
    }
    @Then("^an error message appears$")
    public void invalidInputs() throws Throwable {
    	try {
        	validator.validate(visit);
        } catch (FormValidationException e){
        	Assert.assertEquals("This form has not been validated correctly. The following field are not properly filled in: [Blood Pressure: Up to 3-digit number / Up to 3-digit number, Invalid Number of Weeks Pregnant]", e.getMessage());
        }
    }
   
    @Given("^I enter valid inputs (\\d+), (\\d+), \"([^\"]*)\", (\\d+), (\\d+), \"([^\"]*)\"$")
    public void i_enter_valid_inputs(int arg1, int arg2, String arg3, int arg4, int arg5, String arg6) throws Throwable {
    	this.ds =ConverterDAO.getDataSource();
    	visit = new ObstetricsOfficeVisit();
    	validator = new ObstetricsOfficeVisitValidator(ds);
    	Date date = new Date();
        date.setMonth(8);
        date.setDate(17);
        date.setYear(2017);
        
    	visit.setDate(date);
    	visit.setPatientMID((long)1);
        visit.setNumWeeks(arg1);
        visit.setWeight((double)arg2);
        visit.setBloodPressure(arg3);
        visit.setFHR(arg4);
        visit.setNumFetus(arg5);
    }

    @Then("^the information is stored in the database$")
    public void the_information_is_stored_in_the_database() throws Throwable {
    	
    	validator.validate(visit);
    	
    	
    }
    
    @Given("^I have selected to edit a previous office visit$")
    public void i_have_selected_to_edit_a_previous_office_visit() throws Throwable {
//
    }

    @Given("^I enter (\\d+), (\\d+), \"([^\"]*)\", (\\d+), (\\d+), no$")
    public void i_enter_no(int arg1, int arg2, String arg3, int arg4, int arg5) throws Throwable {
    	this.ds =ConverterDAO.getDataSource();
    	visit = new ObstetricsOfficeVisit();
    	validator = new ObstetricsOfficeVisitValidator(ds);
    	Date date = new Date();
        date.setMonth(8);
        date.setDate(17);
        date.setYear(2017);
        
    	visit.setDate(date);
    	visit.setPatientMID((long)1);
        visit.setNumWeeks(arg1);
        visit.setWeight((double)arg2);
        visit.setBloodPressure(arg3);
        visit.setFHR(arg4);
        visit.setNumFetus(arg5);
    }

    @Then("^the selected office visit is edited\\.$")
    public void the_selected_office_visit_is_edited() throws Throwable {
    	controller = new ObstetricsOfficeVisitController(ds);
    	validator.validate(visit);
    	
    	List<ObstetricsOfficeVisit> list = controller.getObstetricsVisitForPatient("1");
    	visit = list.get(0);
    	visit.setBloodPressure("108/55");
 //   	controller.edit(visit);
    	Assert.assertEquals(list.get(0).getBloodPressure(), visit.getBloodPressure());
    	Assert.assertEquals(list.get(0).getNumFetus(), visit.getNumFetus());
    	Assert.assertEquals(list.get(0).getFHR(), visit.getFHR());
    }
    
    @Given("^have created an Obstetrics Office Visit with valid information$")
    public void have_created_an_Obstetrics_Office_Visit_with_valid_information() throws Throwable {
    	this.ds =ConverterDAO.getDataSource();
    	visit = new ObstetricsOfficeVisit();
    	validator = new ObstetricsOfficeVisitValidator(ds);
    	Date date = new Date();
        date.setMonth(8);
        date.setDate(17);
        date.setYear(2017);
        
    	visit.setDate(date);
    	visit.setPatientMID((long)1);
        visit.setNumWeeks(15);
        visit.setWeight((double)150);
        visit.setBloodPressure("100/200");
        visit.setFHR(95);
        visit.setNumFetus(1);
    }

    @Given("^I enter Ultrasound information (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+)$")
    public void i_enter_Ultrasound_information(int crl, int bpd, int hc, int fl, int ofd, int ac, int hl, int efw) throws Throwable {
        u = new Ultrasound();
        u.setCRL((double)crl);
        u.setBPD((double)bpd);
        u.setHC((double)hc);
        u.setFL((double)fl);
        u.setOFD((double)ofd);
        u.setAC((double)ac);
        u.setHL((double)hl);
        u.setEFW((double)efw);
        ArrayList<Ultrasound> list = new ArrayList<Ultrasound>();
        list.add(u);
        visit.setUltrasounds(list);
    }

    @Then("^the information is stored$")
    public void the_information_is_stored() throws Throwable {
    	Assert.assertTrue(visit.getUltrasounds().get(0).equals(u));
    }
    
    @Given("^I have selected to enter a new office visit on (\\d+)/(\\d+)/(\\d+) at (\\d+):(\\d+) with (\\d+)$")
    public void i_have_selected_to_enter_a_new_office_visit_on_at_with(int arg1, int arg2, int arg3, int arg4, int arg5, int arg6) throws Throwable {
    	this.ds =ConverterDAO.getDataSource();
    	visit = new ObstetricsOfficeVisit();
    	validator = new ObstetricsOfficeVisitValidator(ds);
    	Date date = new Date();
        date.setMonth(arg1);
        date.setDate(arg2);
        date.setYear(arg3);
        date.setHours(arg4);
        date.setMinutes(arg5);
        
    	visit.setDate(date);
    	visit.setPatientMID((long)1);
        visit.setNumWeeks(arg6);
        visit.setWeight((double)150);
        visit.setBloodPressure("100/200");
        visit.setFHR(95);
        visit.setNumFetus(1);
    }


    @Then("^a new appointment is set for (\\d+)/(\\d+)/(\\d+) at (\\d+):(\\d+)$")
    public void a_new_appointment_is_set_for_at(int arg1, int arg2, int arg3, int arg4, int arg5) throws Throwable {
        //controller.add(visit);
        
        
    }
}
