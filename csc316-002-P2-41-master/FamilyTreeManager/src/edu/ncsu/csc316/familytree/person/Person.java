package edu.ncsu.csc316.familytree.person;

/**
 * Creates a Person object that stores a person's first name, last name, and
 * gender. Person has methods to return all of its fields, and also a method to
 * return the person's full name.
 * 
 * @author Samuel Jessee (sijessee)
 *
 */
public class Person {

	/** person's first name */
	private String firstName;

	/** person's last name */
	private String lastName;

	/** person't gender */
	private String gender;

	/**
	 * Constructs a Person with a first name, last name, and gender.
	 * 
	 * @param first
	 *            person's first name
	 * @param last
	 *            person's last name
	 * @param gender
	 *            person's gender
	 */
	public Person(String first, String last, String gender) {
		setFirstName(first);
		setLastName(last);
		setGender(gender);
	}

	/**
	 * Sets the person's first name.
	 * 
	 * @param first
	 *            new name
	 */
	public void setFirstName(String first) {
		firstName = first.trim();
	}

	/**
	 * Returns the person's first name.
	 * 
	 * @return first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the person's last name.
	 * 
	 * @param last
	 *            new name
	 */
	public void setLastName(String last) {
		lastName = last.trim();
	}

	/**
	 * Returns the person's last name.
	 * 
	 * @return last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the person's gender.
	 * 
	 * @param gender
	 *            new gender
	 */
	public void setGender(String gender) {
		String temp = gender.trim().toUpperCase();
		if (!temp.equals("M") && !temp.equals("F")) {
			throw new IllegalArgumentException();
		} else {
			this.gender = temp;
		}
	}

	/**
	 * Returns the person's gender.
	 * 
	 * @return gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Returns the person's full name.
	 * 
	 * @return full name
	 */
	public String getFullName() {
		return firstName + " " + lastName;
	}

	/**
	 * Compares two Person objects and returns true if they are the same.
	 * 
	 * @param person
	 *            person to compare to
	 * @return true if they are the same
	 */
	public boolean equalsPerson(Person person) {
		return this.getFullName().equals(person.getFullName());
	}

}