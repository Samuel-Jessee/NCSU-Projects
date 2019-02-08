package edu.ncsu.csc216.flix_2.inventory;

import edu.ncsu.csc216.flix_2.list_util.MultiPurposeList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The database of available movies, keeps track of what a customer can rent
 * 
 * @author MatthewLilly
 * @author SamuelJessee
 * 
 */
public class MovieDB {
	/** The list of movies in the inventory **/
	private MultiPurposeList<Movie> movies;

	/**
	 * Constructs the database from a file
	 * 
	 * @param filename
	 *            the filename from which the database is to be loaded
	 * @throws IllegalArgumentException
	 *             if the file cannot be read
	 */
	public MovieDB(String filename) {
		movies = new MultiPurposeList<Movie>();
		readFromFile(filename);
	}

	/**
	 * Returns a string corresponding to the movies in the database in the
	 * proper order, separated by newlines. Acceptable for display in the Movie
	 * Inventory area
	 * 
	 * @return a string containing all movies separated by newlines
	 */
	public String traverse() {
		String s = "";
		for (int i = 0; i < movies.size(); i++) {
			s += movies.lookAtItemN(i).getDisplayName() + "\n";
		}
		return s;
	}

	/**
	 * Returns the movie at a given position in the list
	 * 
	 * @param idx
	 *            the index at which the movie is found
	 * @return the movie at the specified index
	 * @throws IllegalArgumentException
	 *             if idx is out of bounds
	 */
	public Movie findItemAt(int idx) {
	   if (idx < 0 || idx > movies.size() - 1)
	   {
	      throw new IllegalArgumentException();
	   }
		Movie movie = movies.lookAtItemN(idx);
		return movie;
	}

	/**
	 * Reads movies from a String and inserts them into the list.
	 * 
	 * @param filename
	 *            String
	 */
	private void readFromFile(String filename) {
		Scanner scanner;
		Movie movie;
		try {
			scanner = new Scanner(new File(filename));
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException();
		}
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine().trim();
			movie = new Movie(line);
			insertInOrder(movie);
		}
		scanner.close();
	}

	private void insertInOrder(Movie movie) {
		if (movies.isEmpty()) {
			movies.addToRear(movie);
		} else {
			for (int i = 0; i < movies.size(); i++) {
				Movie temp = movies.lookAtItemN(i);
				if (movie.compareToByName(temp) < 0) {
					movies.addItem(i, movie);
					break;
				} else if (movie.compareToByName(temp) == 0) {
					break;
				} else if (movie.compareToByName(temp) > 0 && i == movies.size() - 1) {
					movies.addToRear(movie);
				}
			}
		}
	}

}