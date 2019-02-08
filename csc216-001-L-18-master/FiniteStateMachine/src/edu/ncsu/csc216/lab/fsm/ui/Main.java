package edu.ncsu.csc216.lab.fsm.ui;

import java.util.Scanner;

import edu.ncsu.csc216.lab.fsm.course.Course;

/**
 * This client provides a command-line interface for specifying a course name
 * @author Jason King
 *
 */
public class Main {
	
	/**
	 * Returns the course name entered by the user in the console
	 * @return the course name provided by the user in the console
	 */
	private String promptForCourseName()
	{
		System.out.println("Please enter a course name:");
		Scanner scan = new Scanner(System.in);
		return scan.next();
	}

	/**
	 * Begin execution of the program
	 * @param args command-line arguments
	 */
	public static void main(String[] args)
	{
		// Create a new instance of the Main object
		Main m = new Main();	
		
		// Use an infinite loop to continuously prompt
		// the user for more input while the program
		// is running. Ending the program will 
		// terminate the loop.
		while(true)
		{
			String name = m.promptForCourseName();
			try
			{
				// Try to construct a Course object with the user-provided name
				Course c = new Course(name, 5, 100);
				System.out.println(name +" is valid!");
			} catch(IllegalArgumentException e)
			{
				System.out.println(name+": "+e.getMessage());
			}
		}
	}

}
