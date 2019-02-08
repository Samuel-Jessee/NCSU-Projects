package edu.ncsu.csc.itrust.controller.laborreport;

import java.time.LocalDate;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ValidationFormat;
import edu.ncsu.csc.itrust.model.old.enums.TransactionType;


@ManagedBean(name= "labor_report_form")
@ViewScoped
public class LaborReportForm {
	private LaborReport lr;
	private Long patientMID;
	private LaborReportController controller;
	private LocalDate edd;

	public LaborReport getLr() {
		return this.lr;
	}
	
	/**
	 * @return the edd
	 */
	public LocalDate getEdd() {
		return edd;
	}

	/**
	 * @param edd the edd to set
	 */
	public void setEdd(LocalDate edd) {
		this.edd = edd;
	}

	@PostConstruct
	public void init() {
		
		//Get current patient id
		long pid = -1;
		FacesContext ctx = FacesContext.getCurrentInstance();
		String patientID = "";
		try {	
			if (ctx.getExternalContext().getRequest() instanceof HttpServletRequest) {
				HttpServletRequest req = (HttpServletRequest) ctx.getExternalContext().getRequest();
				HttpSession httpSession = req.getSession(false);
				patientID = (String) httpSession.getAttribute("pid");
			}
			if (ValidationFormat.NPMID.getRegex().matcher(patientID).matches()) {
				pid = Long.parseLong(patientID);
			}
			patientMID = pid;
		} catch (NullPointerException e) {
		
		}
		try {
			controller = new LaborReportController();
			lr = controller.getReport(patientMID);
			setEdd(lr.getEstimatedDeliveryDate());
			controller.logTransaction(TransactionType.VIEW_LABOR_REPORT, "");
		} catch (DBException e) {
			e.printStackTrace();
		}
		
	}

}
