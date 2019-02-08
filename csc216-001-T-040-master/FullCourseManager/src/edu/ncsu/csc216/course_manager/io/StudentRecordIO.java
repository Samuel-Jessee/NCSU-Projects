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
import edu.ncsu.csc216.course_manager.users.Student;

/**
 * Reads and writes Student information from files, and uses information from
 * files to create new Students.
 * 
 * @author SamuelJessee
 *
 */
public class StudentRecordIO {

	/**
	 * Reads Student records from a file. Throws FileNotFoundException if file
	 * does not exist.
	 * 
	 * @param fileName
	 *            file to be read
	 * @return list of students
	 * @throws FileNotFoundException
	 *             if file does not exist
	 */
	public static List<Student> readStudentRecords(String fileName) throws FileNotFoundException {
		List<Student> students = new ArrayList<Student>();

		Scanner fileScanner = new Scanner(new FileInputStream(fileName));
		while (fileScanner.hasNextLine()) {
			try {
				students.add(processStudent(fileScanner.nextLine()));
			} catch (IllegalArgumentException e) {
				// if the exception is thrown, ignore the Student line.
			}
		}

		fileScanner.close();
		return students;
	}

	/**
	 * Creates a Student from the String record. Throws IllegalArgumentException
	 * if an item is missing, or Student can't be constructed.
	 * 
	 * @param studentLine
	 *            String of Student information
	 * @return Student object
	 */
	private static Student processStudent(String studentLine) {
		Scanner lineScanner = new Scanner(studentLine);
		try {
			lineScanner.useDelimiter(",");
			String firstName = lineScanner.next();
			String lastName = lineScanner.next();
			String id = lineScanner.next();
			String email = lineScanner.next();
			String password = lineScanner.next();
			int maxCredits = lineScanner.nextInt();
			Student student = new Student(firstName, lastName, id, email, password, maxCredits);
			while (lineScanner.hasNext()) {
				Course c = CourseManager.getInstance().getCourseByName(lineScanner.next());
				if (c == null) {
					lineScanner.close();
					throw new IllegalArgumentException();
				}
				if (student.canAddCourse(c) && c.canEnroll(student)) {
					student.addCourse(c);
					c.enroll(student);
				} else {
					lineScanner.close();
					throw new IllegalArgumentException();
				}
			}
			lineScanner.close();
			return student;
		} catch (NoSuchElementException e) {
			lineScanner.close();
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Writes information about Students to a file.
	 * 
	 * @param fileName
	 *            file to write to
	 * @param students
	 *            list of students
	 * @throws IOException
	 *             if cannot write to file
	 */
	public static void writeStudentRecords(String fileName, List<Student> students) throws IOException {
		PrintWriter fileOut = new PrintWriter(new FileWriter(fileName));

		for (Student s : students) {
			fileOut.println(s.toString());
		}

		fileOut.close();
	}

}
