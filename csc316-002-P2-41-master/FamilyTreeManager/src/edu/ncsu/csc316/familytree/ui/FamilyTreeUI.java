package edu.ncsu.csc316.familytree.ui;

import java.io.FileNotFoundException;
import edu.ncsu.csc316.familytree.manager.FamilyTreeManager;
import edu.ncsu.csc316.familytree.person.Person;
import edu.ncsu.csc316.familytree.util.List;

/**
 * Prompts the user for file names, and prints output.
 * 
 * @author Samuel Jessee (sijessee)
 *
 */
public class FamilyTreeUI {

	/**
	 * Main method that starts the program.
	 * 
	 * @param args
	 *            list of system arguments
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		String preFile = args[0];
		String postFile = args[1];
		FamilyTreeManager manager = new FamilyTreeManager(preFile, postFile);
		List<Person> levelOrder = manager.getLevelOrder();
		int size = levelOrder.size();
		String levelOrderString = "";
		for (int i = 0; i < size - 1; i++) {
			levelOrderString += levelOrder.get(i).getFullName() + ", ";
		}
		levelOrderString += levelOrder.get(size - 1).getFullName();
		System.out.println(levelOrderString);

		// begin user input
		// Scanner in = new Scanner(System.in);
		// System.out.print("Full name of first person (! to quit): ");
		// String name1;
		// String name2;
		// while ((name1 = in.nextLine()) != "!") {
		// System.out.print("Full name of second person: ");
		// name2 = in.nextLine();
		// System.out.println(manager.getRelationship(name1, name2));
		// System.out.print("Full name of first person (! to quit): ");
		// }
		// in.close();
	}

}