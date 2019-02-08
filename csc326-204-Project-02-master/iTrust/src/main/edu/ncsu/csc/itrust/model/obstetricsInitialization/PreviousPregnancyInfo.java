package edu.ncsu.csc.itrust.model.obstetricsInitialization;

import java.time.LocalDateTime;

import javax.faces.bean.ManagedBean;

@ManagedBean(name = "previous_pregnancy_info")
public class PreviousPregnancyInfo {
	private long infoID;
	private long patientMID;
	private Integer year;
	private Integer weeksPreg;
	private Integer hoursInLabor;
	private Integer weightGained;
	private Integer deliveryType;
	private Integer amtChildren;

	/**
	 * Enum for Delivery Type in obstetrics
	 */
	public enum DeliveryType {
		VAGINAL_DELIVERY(1, "Vaginal Delivery"), VAGINAL_DELIVERY_VACUUM_ASSIST(2,
				"Vaginal Delivery Vacuum Assist"), VAGINAL_DELIVERY_FORCEPS(3,
						"Vaginal Delivery forceps"), CAESAREAN_SECTION(4,
								"Caesarean section"), MISCARRIAGE(5, "Miscarriage"), UNSELECTED(0, "Unselected");

		private int id;
		private String description;

		DeliveryType(int id, String description) {
			this.id = id;
			this.description = description;
		}

		public static String getDesriptionById(int id) {
			for (DeliveryType status : values()) {
				if (status.id == id) {
					return status.description;
				}
			}
			return null;
		}
	}

	public long getInfoID() {
		return infoID;
	}

	public void setInfoID(long infoID) {
		this.infoID = infoID;
	}

	public long getPatientMID() {
		return patientMID;
	}

	public void setPatientMID(long patientMID) {
		this.patientMID = patientMID;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getWeeksPreg() {
		return weeksPreg;
	}

	public void setWeeksPreg(Integer weeksPreg) {
		this.weeksPreg = weeksPreg;
	}

	public Integer getHoursInLabor() {
		return hoursInLabor;
	}

	public void setHoursInLabor(Integer hoursInLabor) {
		this.hoursInLabor = hoursInLabor;
	}

	public Integer getWeightGained() {
		return weightGained;
	}

	public void setWeightGained(Integer weightGained) {
		this.weightGained = weightGained;
	}

	public Integer getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
	}

	/**
	 * @return string representation of delivery type in the format of: "id -
	 *         description"
	 */
	public String getDeliveryTypeDescription() {
		String description = DeliveryType.getDesriptionById(deliveryType);
		if (description == null) {
			return "";
		}
		return String.format("%d - %s", deliveryType, description);
	}

	public Integer getAmtChildren() {
		return amtChildren;
	}

	public void setAmtChildren(Integer amtChildren) {
		this.amtChildren = amtChildren;
	}

}
