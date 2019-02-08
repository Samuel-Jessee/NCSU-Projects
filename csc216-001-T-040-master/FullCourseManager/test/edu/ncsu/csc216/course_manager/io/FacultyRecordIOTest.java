package edu.ncsu.csc216.course_manager.io;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.course_manager.courses.Course;
import edu.ncsu.csc216.course_manager.io.FacultyRecordIO;
import edu.ncsu.csc216.course_manager.manager.CourseManager;
import edu.ncsu.csc216.course_manager.users.Faculty;

/**
 * Test the FacultyRecordIO class.
 * 
 * @author SamuelJessee
 */
public class FacultyRecordIOTest {

	private final String validTestFile = "test-files/faculty_records.txt";
	private final String invalidTestFile = "test-files/invalid_faculty_records.txt";

	private String validFaculty0 = "Ashely,Witt,awitt,mollis@Fuscealiquetmagna.net,pw,2,CSC116";
	private String validFaculty1 = "Fiona,Meadows,fmeadow,pharetra.sed@et.org,pw,3,CSC216,CSC230";
	private String validFaculty2 = "Brent,Brewer,bbrewer,sem.semper@orcisem.co.uk,pw,1,CSC226";
	private String validFaculty3 = "Halla,Aguirre,haguirr,Fusce.dolor.quam@amalesuadaid.net,pw,3,CSC236";
	private String validFaculty4 = "Kevyn,Patel,kpatel,risus@pellentesque.ca,pw,1";
	private String validFaculty5 = "Elton,Briggs,ebriggs,arcu.ac@ipsumsodalespurus.edu,pw,3,CSC246";
	private String validFaculty6 = "Norman,Brady,nbrady,pede.nonummy@elitfermentum.co.uk,pw,1,CSC316";
	private String validFaculty7 = "Lacey,Walls,lwalls,nascetur.ridiculus.mus@fermentum.net,pw,2,CSC379";

	private String[] validFaculty = { validFaculty0, validFaculty1, validFaculty2, validFaculty3, validFaculty4,
			validFaculty5, validFaculty6, validFaculty7 };

	private Course c1, c2, c3, c4, c5, c6, c7, c8;

	private CourseManager manager;

	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";

	/**
	 * Sets up Courses used to help with testing the student file in isolation.
	 */
	@Before
	public void setUp() {
		c1 = new Course("CSC116", 3, 10);
		c2 = new Course("CSC216", 3, 10);
		c3 = new Course("CSC226", 3, 10);
		c4 = new Course("CSC230", 3, 10);
		c5 = new Course("CSC236", 3, 10);
		c6 = new Course("CSC246", 3, 10);
		c7 = new Course("CSC316", 3, 10);
		c8 = new Course("CSC379", 1, 10);

		Course[] courses = { c1, c2, c3, c4, c5, c6, c7, c8 };

		// Set up the manager and clear out all data to start
		// test from scratch.
		manager = CourseManager.getInstance();
		manager.clearData();
		for (int i = 0; i < courses.length; i++) {
			manager.addCourse(courses[i]);
		} // for

		try {
			String password = "pw";
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(password.getBytes());
			String hashPW = new String(digest.digest());

			for (int i = 0; i < validFaculty.length; i++) {
				validFaculty[i] = validFaculty[i].replace(",pw,", "," + hashPW + ",");
			} // for
		} catch (NoSuchAlgorithmException e) {
			fail("Unable to create hash during setup");
		} // try

		// Reset faculty_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "expected_full_faculty_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "faculty_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		} // try

	}// setUp

	/**
	 * Test reading faculty from a valid record file.
	 */
	@Test
	public void testReadFacultyRecordsValid() {
		try {
			List<Faculty> faculty = FacultyRecordIO.readFacultyRecords(validTestFile);
			assertEquals(8, faculty.size());

			for (int i = 0; i < validFaculty.length; i++) {
				assertEquals(validFaculty[i], faculty.get(i).toString());
			} // for
		} catch (FileNotFoundException e) {
			fail("Unexpected error reading " + validTestFile);
		} // try
	}// testReadFacultyRecordsValid

	/**
	 * Test invalid faculty record files.
	 */
	@Test
	public void testReadFacultyRecordsInvalid() {
		List<Faculty> faculty;
		try {
			faculty = FacultyRecordIO.readFacultyRecords(invalidTestFile);
			assertEquals(0, faculty.size());
		} catch (FileNotFoundException e) {
			fail("Unexpected FileNotFoundException");
		} // try
	}// testReadFacultyRecordsInvalid

	/**
	 * Tests writeFacultyRecords()
	 */
	@Test
	public void testWriteFacultyRecords() {
		ArrayList<Faculty> faculty = new ArrayList<Faculty>();
		Faculty f = new Faculty("first", "last", "flast", "first_last@ncsu.edu", "pw", 2);
		f.addCourse(c1);
		f.addCourse(c2);
		faculty.add(f);
		//

		try {
			FacultyRecordIO.writeFacultyRecords("test-files/actual_faculty_records.txt", faculty);
		} catch (IOException e) {
			fail("Cannot write faculty to files");
		} // try

		checkFiles("test-files/expected_faculty_records.txt", "test-files/actual_faculty_records.txt");
	}// testWriteFacultyRecords

	/**
	 * Helper method to compare two files for the same contents
	 * 
	 * @param expFile
	 *            expected output
	 * @param actFile
	 *            actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try {
			Scanner expScanner = new Scanner(new File(expFile));
			Scanner actScanner = new Scanner(new File(actFile));

			while (expScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			} // while

			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		} // try
	}// checkFiles

}// class