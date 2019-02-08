package edu.ncsu.csc216.course_manager.manager;

import edu.ncsu.csc216.course_manager.courses.Course;
import edu.ncsu.csc216.course_manager.exceptions.*;
import edu.ncsu.csc216.course_manager.users.Student;
import edu.ncsu.csc216.course_manager.utils.ArrayList;

public class EnrollmentManager {
	
	private ArrayList<Course> courses;
	private ArrayList<Student> students;
	
	public EnrollmentManager() {
		courses = new ArrayList<Course>();
		students = new ArrayList<Student>();
	}
	
	public void addCourse(Course c) {
		if(courses.contains(c))
			throw new DuplicateCourseException();
		courses.add(c);
	}
	
	public void addStudent(Student s) {
		if(students.contains(s))
			throw new DuplicateStudentException();
		students.add(s);
	}
	
	public boolean addStudentToCourse(Student s, Course c) {
		for(Student st : c.getEnrolledStudents())
			if(s.equals(st))
				throw new StudentAlreadyEnrolledException();
		if(c.getWaitlist().contains(s))
			throw new StudentAlreadyEnrolledException();
		if(!c.canEnroll(s))
			throw new CourseCapacityException();
		c.enroll(s);
		if(c.getWaitlist().contains(s))
			return false;
		return true;
	}
	
	public boolean removeStudentFromCourse(Student s, Course c) {
		return c.drop(s);
	}

}
