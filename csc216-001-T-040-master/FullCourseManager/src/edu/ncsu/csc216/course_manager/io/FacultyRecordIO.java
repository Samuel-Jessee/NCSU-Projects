/**
 * 
 */
package edu.ncsu.csc216.course_manager.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.course_manager.courses.Course;
import edu.ncsu.csc216.course_manager.manager.CourseManager;
import edu.ncsu.csc216.course_manager.users.Faculty;

/**
 * Reads and writes Faculty information from files, and uses information from
 * files to create new Faculty.
 * 
 * @author SamuelJessee
 *
 */
public class FacultyRecordIO {

	/**
	 * Reads Faculty records from a file. Throws FileNotFoundException if file
	 * does not exist.
	 * 
	 * @param fileName
	 *            file to be read
	 * @return list of faculty
	 * @throws FileNotFoundException
	 *             if file does not exist
	 */
	public static List<Faculty> readFacultyRecords(String fileName) throws FileNotFoundException {
		List<Faculty> faculty = new ArrayList<Faculty>();

		Scanner fileScanner = new Scanner(new FileInputStream(fileName));
		while (fileScanner.hasNextLine()) {
			try {
				faculty.add(processFaculty(fileScanner.nextLine()));
			} catch (IllegalArgumentException e) {
				// if the exception is thrown, ignore the Student line.
			} // try
		} // while

		fileScanner.close();
		return faculty;
	}// readFacultyRecords

	/**
	 * Creates a Faculty from the String record. Throws IllegalArgumentException
	 * if an item is missing, or Faculty can't be constructed.
	 * 
	 * @param facultyLine
	 *            String of Faculty information
	 * @return Faculty object
	 */
	private static Faculty processFaculty(String facultyLine) {
		Scanner lineScanner = new Scanner(facultyLine);
		try {
			lineScanner.useDelimiter(",");
			String firstName = lineScanner.next();
			String lastName = lineScanner.next();
			String id = lineScanner.next();
			String email = lineScanner.next();
			String password = lineScanner.next();
			int maxCourses = lineScanner.nextInt();
			Faculty f = new Faculty(firstName, lastName, id, email, password, maxCourses);
			while (lineScanner.hasNext()) {
				Course c = CourseManager.getInstance().getCourseByName(lineScanner.next());
				if (c == null) {
					lineScanner.close();
					throw new IllegalArgumentException();
				} // if
				if (f.canAddCourse(c) && c.canAddFaculty()) {
					f.addCourse(c);
					c.addFaculty(f);
				} else {
					lineScanner.close();
					throw new IllegalArgumentException();
				} // if
			} // while
			lineScanner.close();
			return f;
		} catch (NoSuchElementException e) {
			lineScanner.close();
			throw new IllegalArgumentException();
		} // try
	}// processFaculty

	/**
	 * Writes information about Faculty to a file.
	 * 
	 * @param fileName
	 *            file to write to
	 * @param faculty
	 *            list of faculty
	 * @throws IOException
	 *             if cannot write to file
	 */
	public static void writeFacultyRecords(String fileName, List<Faculty> faculty) throws IOException {
		PrintWriter fileOut = new PrintWriter(new FileWriter(fileName));

		for (Faculty f : faculty) {
			fileOut.println(f.toString());
		} // for

		fileOut.close();
	}// writeFacultyRecords

}// class