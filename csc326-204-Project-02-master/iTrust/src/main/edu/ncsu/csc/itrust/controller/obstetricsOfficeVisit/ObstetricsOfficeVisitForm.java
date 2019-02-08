package edu.ncsu.csc.itrust.controller.obstetricsOfficeVisit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;

import edu.ncsu.csc.itrust.controller.obstetricsInitialization.ObstetricsInitializationController;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.model.ValidationFormat;
import edu.ncsu.csc.itrust.model.obstetricsInitialization.ObstetricsInitialization;
import edu.ncsu.csc.itrust.model.obstetricsOfficeVisit.ObstetricsOfficeVisit;
import edu.ncsu.csc.itrust.model.obstetricsOfficeVisit.Ultrasound;



@ManagedBean(name= "obstetrics_office_visit_form")
@ViewScoped
public class ObstetricsOfficeVisitForm {
	private ObstetricsOfficeVisitController controller;
	private ObstetricsOfficeVisit ov;
	// Needed to get rh flag; should prob be done in controller but it is late
	private ObstetricsInitializationController iniCont;
	private ObstetricsInitialization oi;
	private Long visitID;
	private Long patientMID;
	private Date date;
	private Integer numWeeks;
	private Double weight;
	private String bloodPressure;
	private Integer FHR;
	private Integer numFetus;
	private boolean lowLyingPlacenta;
	private boolean highGenetic;
	private String complications;
	private boolean includeUltrasound;
	private ArrayList<Ultrasound> ultrasounds;
	private UploadedFile picture;
	private File picFile;
	boolean newOV;


	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getNumWeeks() {
		return numWeeks;
	}

	public void setNumWeeks(Integer numWeeks) {
		this.numWeeks = numWeeks;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getBloodPressure() {
		return bloodPressure;
	}

	public void setBloodPressure(String bloodPressure) {
		this.bloodPressure = bloodPressure;
	}

	/**
	 * Fetal Heart Rate
	 * @return
	 */
	public Integer getFHR() {
		return FHR;
	}

	/**
	 * Fetal Heart Rate
	 * @param fHR
	 */
	public void setFHR(Integer fHR) {
		FHR = fHR;
	}

	/**
	 * Number of Fetus in the current Prenancy
	 * @return
	 */
	public Integer getNumFetus() {
		return numFetus;
	}
	
	/**
	 * Number of Fetus in the current Prenancy
	 * @param numFetus
	 */
	public void setNumFetus(Integer numFetus) {
		this.numFetus = numFetus;
	}

	public boolean isLowLyingPlacenta() {
		return lowLyingPlacenta;
	}

	public void setLowLyingPlacenta(boolean lowLyingPlacenta) {
		this.lowLyingPlacenta = lowLyingPlacenta;
	}

	/**
	 * @return the includeUltrasound
	 */
	public boolean isIncludeUltrasound() {
		return includeUltrasound;
	}

	/**
	 * @param includeUltrasound the includeUltrasound to set
	 */
	public void setIncludeUltrasound(boolean includeUltrasound) {
		this.includeUltrasound = includeUltrasound;
	}

	public ArrayList<Ultrasound> getUltrasounds() {
		return ultrasounds;
	}

	public void setUltrasounds(ArrayList<Ultrasound> ultrasounds) {
		this.ultrasounds = ultrasounds;
	}
	
	
	/**
	 * @return the picture
	 */
	public UploadedFile getPicture() {
		return picture;
	}

	/**
	 * @param picture the picture to set
	 */
	public void setPicture(UploadedFile picture) {
		this.picture = picture;
	}
	

	/**
	 * @return the highGenetic
	 */
	public boolean isHighGenetic() {
		return highGenetic;
	}

	/**
	 * @param highGenetic the highGenetic to set
	 */
	public void setHighGenetic(boolean highGenetic) {
		this.highGenetic = highGenetic;
	}

	/**
	 * @return the complications
	 */
	public String getComplications() {
		return complications;
	}

	/**
	 * @param complications the complications to set
	 */
	public void setComplications(String complications) {
		this.complications = complications;
	}

	/**
	 * Initialize form with required data
	 * 
	 */
	@PostConstruct
	public void init() {
		
		try {
			controller = new ObstetricsOfficeVisitController();
		} catch (DBException e1) {
			e1.printStackTrace();
		}
		newOV = !controller.isEditVisit();
		if (!newOV) {
			ov = controller.getSelectedVisit();
			visitID = ov.getVisitID();
			patientMID = ov.getPatientMID();
			date = ov.getDate();
			numWeeks = ov.getNumWeeks();
			weight = ov.getWeight();
			bloodPressure = ov.getBloodPressure();
			FHR = ov.getFHR();
			numFetus = ov.getNumFetus();
			lowLyingPlacenta = ov.isLowLyingPlacenta();
			ultrasounds = ov.getUltrasounds();
			highGenetic = ov.isHighgenetic();
			complications = ov.getComplications();
			picFile = ov.getPicture();
			if (ultrasounds != null && !ultrasounds.isEmpty()) {
				includeUltrasound = true;
			} else {
				includeUltrasound = false;
			}
			
		} else {
		
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
			

			} catch (NullPointerException e) {
				
			}
			patientMID = pid;
			date = Date.from(Instant.now());
			ultrasounds = new ArrayList<Ultrasound>();
			numWeeks = (int) controller.calculateCurrentPatientWeeksPregnant();
			// RH- flag
			if (numWeeks > 28) {
				try {
					iniCont = new ObstetricsInitializationController();
					oi = iniCont.getObstetricsInitializationForCurrentPatient().get(0);
					if (oi.isRH()) {
						controller.printFacesMessage(FacesMessage.SEVERITY_ERROR, "Patient should be given RH immune globulin shot if they haven't already",
								"Patient should be given RH immune globulin shot if they haven't already", null);
					}
				} catch (DBException e) {
					e.printStackTrace();
				}
			}
			ov = new ObstetricsOfficeVisit();
		}
	}
	/**
	 * Convert uploadedFile to File
	 */
	public void upload() {
		InputStream in;
		OutputStream out;
		try {
		String fileName = FilenameUtils.getName(picture.getFileName());
		in = picture.getInputstream();
		picFile = new File(fileName);
		picFile.createNewFile();
		out = new FileOutputStream(picFile);
		IOUtils.copy(in, out);
		IOUtils.closeQuietly(in);
		IOUtils.closeQuietly(out);
		} catch (Exception e) {
			
		}	
		
	}
	
	/**
	 * Adds <numFetus> amount of Ultrasound objects to the ultrasounds list and sets includeUltrasound
	 * to true so the page knows to render the ultrasound info table.
	 */
	public void addUltrasound() {
		if (numFetus == null || numFetus < 1) {
			controller.printFacesMessage(FacesMessage.SEVERITY_ERROR, "Number of Fetuses Must be Set Before Adding an Ultrasound",
					"Number of Fetuses Must be Set Before Adding an Ultrasound", null);
		} else {
			for (int i = 0; i < numFetus; i++) {
				Ultrasound tmp = new Ultrasound();
				tmp.setPatientMID(patientMID);
				tmp.setAC(0.0);
				tmp.setBPD(0.0);
				tmp.setCRL(0.0);
				tmp.setEFW(0.0);
				tmp.setFL(0.0);
				tmp.setHC(0.0);
				tmp.setHL(0.0);
				tmp.setOFD(0.0);
				ultrasounds.add(tmp);
			}
			includeUltrasound = true;
		}
	}
	
	
	public void submit() {
		if (ov == null) {
			ov = new ObstetricsOfficeVisit();
		}
		if (controller == null) {
			try {
				controller = new ObstetricsOfficeVisitController();
			} catch (DBException e) {
				e.printStackTrace();
			}
		}
		ov.setBloodPressure(bloodPressure);
		ov.setDate(date);
		ov.setFHR(FHR);
		ov.setLowLyingPlacenta(lowLyingPlacenta);
		ov.setHighgenetic(highGenetic);
		ov.setComplications(complications);
		ov.setNumFetus(numFetus);
		ov.setNumWeeks(numWeeks);
		ov.setWeight(weight);
		if (newOV) {
			ov.setPatientMID(patientMID);
			ov.setUltrasounds(ultrasounds);
			upload();
			ov.setPicture(picFile);
			ov.setVisitID(null);
			long generatedVisitId = controller.addReturnGeneratedId(ov);
			visitID = generatedVisitId;
			ov.setVisitID(visitID);
		} else {
			controller.edit(ov);
		}
		ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
		if (ctx != null) {
			try {
				ctx.redirect("/iTrust/auth/hcp-uap/obstetricsOfficeVisit.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//controller.logTransaction(TransactionType.CREATE_OBSTETRICS_OV, ov.get);
		
	}
	
	public void downloadFile() {

	    File file = new File("UltraPic");
	    HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();  

	    response.setHeader("Content-Disposition", "attachment;filename=UltraPic");  
	    response.setContentLength((int) file.length());  
	    ServletOutputStream out = null;  
	    try {  
	        FileInputStream input = new FileInputStream(file);  
	        byte[] buffer = new byte[16777215];  
	        out = response.getOutputStream();  
	        int i = 0;  
	        while ((i = input.read(buffer)) != -1) {  
	            out.write(buffer);  
	            out.flush();  
	        }  
	        FacesContext.getCurrentInstance().getResponseComplete();  
	    } catch (IOException err) {  
	        err.printStackTrace();  
	    } finally {  
	        try {  
	            if (out != null) {  
	                out.close();  
	            }  
	        } catch (IOException err) {  
	            err.printStackTrace();  
	        }  
	    }  

	}


}