package edu.ncsu.csc316.familytree.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.ncsu.csc316.familytree.person.Person;
import edu.ncsu.csc316.familytree.util.LinkedList;
import edu.ncsu.csc316.familytree.util.List;
import edu.ncsu.csc316.familytree.util.Tree;
import edu.ncsu.csc316.familytree.util.TreeNode;

/**
 * Builds and manages a family tree. Has a method to determine the relationship
 * between two people, and a method to calculate the level order of a family
 * tree.
 * 
 * @author Samuel Jessee (sijessee)
 *
 */
public class FamilyTreeManager {

	/** the family tree */
	public Tree tree;

	/** the preOrder traversal */
	public LinkedList<Person> pre;

	/** the postOrder traversal */
	public LinkedList<Person> post;

	/**
	 * Builds a family tree from two traversal files.
	 * 
	 * @param preOrderFile
	 *            file name of preOrder traversal
	 * @param postOrderFile
	 *            file name of postOrder traversal
	 * @throws FileNotFoundException
	 */
	public FamilyTreeManager(String preOrderFile, String postOrderFile) throws FileNotFoundException {
		buildTraversals(preOrderFile, postOrderFile);
		tree = new Tree(pre, post);
	}

	/**
	 * Reads the files and creates traversals.
	 * 
	 * @param preOrderFile
	 *            preOrder traversal file
	 * @param postOrderFile
	 *            postOrder traversal file
	 * @throws FileNotFoundException
	 */
	public void buildTraversals(String preOrderFile, String postOrderFile) throws FileNotFoundException {
		pre = new LinkedList<Person>();
		post = new LinkedList<Person>();
		Scanner reader = new Scanner(new File(preOrderFile));
		while (reader.hasNextLine()) {
			String first = reader.next();
			String last = reader.next();
			String gender = reader.next();
			Person temp = new Person(first, last, gender);
			pre.add(temp);
			reader.nextLine();
		}
		reader = new Scanner(new File(postOrderFile));
		while (reader.hasNextLine()) {
			String first = reader.next();
			String last = reader.next();
			String gender = reader.next();
			Person temp = new Person(first, last, gender);
			post.add(temp);
			reader.nextLine();
		}
		reader.close();
	}

	/**
	 * Determines the level order traversal of the family tree and returns the
	 * traversal as a list.
	 * 
	 * @return level order traversal
	 */
	public List<Person> getLevelOrder() {
		LinkedList<Person> levelOrder = new LinkedList<Person>();
		LinkedList<TreeNode<Person>> q = new LinkedList<TreeNode<Person>>();

		TreeNode<Person> temp = tree.getRoot();
		q.enqueue(temp);

		while (q.size() > 0) {
			temp = q.dequeue();
			int n = temp.numberOfChildren();
			for (int i = 0; i < n; i++) {
				q.enqueue(temp.getChild(i));
			}
			levelOrder.add(temp.getData());
		}
		return levelOrder;
	}

	/**
	 * Determines the relationship between two names and returns a String that
	 * tells the relationship.
	 * 
	 * @param fullNameA
	 *            name of first person
	 * @param fullNameB
	 *            name of second person
	 * @return String that tells the relationship
	 */
	public String getRelationship(String fullNameA, String fullNameB) {
		TreeNode<Person> personA = tree.getNode(fullNameA);
		TreeNode<Person> personB = tree.getNode(fullNameB);
		TreeNode<Person> commonAncestor = null;
		int aPath = 0;
		int bPath = 0;
		personA.markAncestors();
		if (personB.isMarked()) {
			commonAncestor = personB;
			bPath = 0;
		} else {
			TreeNode<Person> temp = personB;
			while ((temp = temp.getParent()) != null) {
				bPath++;
				if (temp.isMarked()) {
					commonAncestor = temp;
					break;
				}
			}
		}
		try {
			if (personA.getData().equalsPerson(commonAncestor.getData())) {
				aPath = 0;
			} else {
				TreeNode<Person> temp = personA;
				while ((temp = temp.getParent()) != null) {
					aPath++;
					if (temp.getData().equalsPerson(commonAncestor.getData())) {
						break;
					}
				}
			}
			personA.clearMarks();
			boolean g = personA.getData().getGender().equalsIgnoreCase("M");
			String relation = calculateRelation(fullNameA, fullNameB, aPath, bPath, g);
			return relation;
		} catch (NullPointerException e) {
			return null;
		}
	}

	/**
	 * Builds the String for getRelationship.
	 * 
	 * @param fullNameA
	 *            name of first person
	 * @param fullNameB
	 *            name of second person
	 * @param a
	 *            length of first person's ancestry path
	 * @param b
	 *            length of second ancestry path
	 * @param g
	 *            gender of first person, true for male
	 * @return String telling relationship
	 */
	private String calculateRelation(String fullNameA, String fullNameB, int a, int b, boolean m) {
		String relation = fullNameA + " is " + fullNameB + "'s ";
		if (a == 0 && b == 0) {
			return fullNameA + " is " + fullNameB;

		} else if (a == 0 && b == 1) {
			// mother father
			if (m) {
				relation += "father";
			} else {
				relation += "mother";
			}
			return relation;

		} else if (a == 0 && b == 2) {
			// grandparents
			if (m) {
				relation += "grandfather";
			} else {
				relation += "grandmother";
			}
			return relation;

		} else if (a == 0 && b == 3) {
			// great grandparents
			if (m) {
				relation += "great-grandfather";
			} else {
				relation += "great-grandmother";
			}
			return relation;

		} else if (a == 0 && b > 3) {
			// great+ grandparents
			int k = b - 2;
			String great = "";
			for (int i = 1; i <= k; i++) {
				great += "great-";
			}
			if (m) {
				relation += great + "grandfather";
			} else {
				relation += great + "grandmother";
			}
			return relation;

		} else if (a == 1 && b == 0) {
			// daughter son
			if (m) {
				relation += "son";
			} else {
				relation += "daughter";
			}
			return relation;

		} else if (a == 2 && b == 0) {
			// grandchild
			if (m) {
				relation += "grandson";
			} else {
				relation += "granddaughter";
			}
			return relation;

		} else if (a == 3 && b == 0) {
			// great grandchild
			if (m) {
				relation += "great-grandson";
			} else {
				relation += "great-granddaughter";
			}
			return relation;

		} else if (a > 3 && b == 0) {
			// great+ grandchild
			int k = a - 2;
			String great = "";
			for (int i = 1; i <= k; i++) {
				great += "great-";
			}
			if (m) {
				relation += great + "grandson";
			} else {
				relation += great + "granddaughter";
			}
			return relation;

		} else if (a == 1 && b == 1) {
			// sister brother
			if (m) {
				relation += "brother";
			} else {
				relation += "sister";
			}
			return relation;

		} else if (a == 1 && b == 2) {
			// aunt uncle
			if (m) {
				relation += "uncle";
			} else {
				relation += "aunt";
			}
			return relation;

		} else if (a == 1 && b > 2) {
			// great+ aunt uncle
			int k = b - 2;
			String great = "";
			for (int i = 1; i <= k; i++) {
				great += "great-";
			}
			if (m) {
				relation += great + "uncle";
			} else {
				relation += great + "aunt";
			}
			return relation;

		} else if (a == 2 && b == 1) {
			// niece nephew
			if (m) {
				relation += "nephew";
			} else {
				relation += "niece";
			}
			return relation;

		} else if (a > 2 && b == 1) {
			// great nephew niece
			int k = a - 2;
			String great = "";
			for (int i = 1; i <= k; i++) {
				great += "great-";
			}
			if (m) {
				relation += great + "nephew";
			} else {
				relation += great + "niece";
			}
			return relation;

		} else if (a >= 2 && b >= 2) {
			// cousins
			int x = Math.min(a, b) - 1;
			int y = Math.abs(a - b);
			relation += x + "th cousin " + y + " times removed";
			return relation;

		} else {
			return null;
		}
	}

}