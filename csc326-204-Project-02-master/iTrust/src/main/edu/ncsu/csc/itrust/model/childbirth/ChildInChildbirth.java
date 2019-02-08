package edu.ncsu.csc.itrust.model.childbirth;

import java.time.LocalDateTime;

import javax.faces.bean.ManagedBean;

public class ChildInChildbirth {
	private long childID;
	private LocalDateTime date;
	private boolean approximate;
	private String gender;
	private long mid;
	
	/**
	 * @return the childID
	 */
	public long getChildID() {
		return childID;
	}
	/**
	 * @param childID the childID to set
	 */
	public void setChildID(long childID) {
		this.childID = childID;
	}

	/**
	 * @return the date
	 */
	public LocalDateTime getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	/**
	 * @return the approximate
	 */
	public boolean isApproximate() {
		return approximate;
	}
	/**
	 * @param approximate the approximate to set
	 */
	public void setApproximate(boolean approximate) {
		this.approximate = approximate;
	}
	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * @return the mid
	 */
	public long getMid() {
		return mid;
	}
	/**
	 * @param mid the mid to set
	 */
	public void setMid(long mid) {
		this.mid = mid;
	}
	
}
