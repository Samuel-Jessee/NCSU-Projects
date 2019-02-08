package edu.ncsu.csc216.flix_2.inventory;

/**
 * Represents a movie, available for rent or in the customers' list
 * 
 * @author MatthewLilly
 * @author SamuelJessee
 * 
 */
public class Movie {
	/** The name of the movie **/
	private String name;

	/** The amount of the movie in stock **/
	private int inStock;

	/**
	 * Constructs a movie from a formatted string with relevant information if
	 * the number in stock is negative, set it to 0
	 * 
	 * @param line
	 *            the line of text to be converted to a movie
	 * @throws IllegalArgumentException
	 *             if the string is not valid after trimming
	 */
	public Movie(String line) {
		line = line.trim();
		int n = line.indexOf(" ");
		if (n == -1) {
			throw new IllegalArgumentException();
		}
		inStock = Integer.parseInt(line.substring(0, n));
		if (inStock < 0) {
			inStock = 0;
		}
		line = line.substring(line.indexOf(" "));
		name = line.trim();
	}

	/**
	 * Returns the name of the movie
	 * 
	 * @return the name of the movie
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the movie title + " (currently unavailable)" if the movie is out
	 * of stock and just the movie title if it is in stock
	 * 
	 * @return the name of the movie appended with "currently unavailable" of
	 *         out of stock, just the movie name otherwise
	 */
	public String getDisplayName() {
		String displayName = getName();

		if (inStock == 0) {
			displayName += " (currently unavailable)";
		}

		return displayName;
	}

	/**
	 * Compares two movies by title to determine their order in the inventory
	 * list
	 * 
	 * @param movie
	 *            the movie to be compared to this one
	 * @return positive int if other movie comes first, 0 if same movie,
	 *         negative int if this comes first
	 */
	public int compareToByName(Movie movie) {
		String a = "a ";
		String b = "an ";
		String c = "the ";
		String movie1 = this.getName().toLowerCase();
		String movie2 = movie.getName().toLowerCase();
		int n;
		if (movie1.startsWith(a) || movie1.startsWith(b) || movie1.startsWith(c)) {
			movie1 = movie1.substring(movie1.indexOf(" ")).trim();
		}
		if (movie2.startsWith(a) || movie2.startsWith(b) || movie2.startsWith(c)) {
			movie2 = movie2.substring(movie2.indexOf(" ")).trim();
		}
		for (int i = 0; i < movie1.length() && i < movie2.length(); i++) {
			n = movie1.charAt(i) - movie2.charAt(i);
			if (n != 0) {
				return n;
			}
		}
		if (movie1.length() > movie2.length()) {
			return 1;
		} else if (movie1.length() < movie2.length()) {
			return -1;
		} else {
			return 0;
		}
	}

	/**
	 * Returns true if there are DVDs of this movie in stock
	 * 
	 * @return true if there are DVDs of this movie in stock
	 */
	public boolean isAvailable() {
		return inStock > 0;
	}

	/**
	 * Puts a copy of the movie back into the inventory
	 */
	public void backToInventory() {
		inStock++;
	}

	/**
	 * Removes a DVD of the movie from the inventory
	 * 
	 * @throws IllegalStateException
	 *             if there are no DVDs left
	 */
	public void removeOneCopyFromInventory() {
		if (inStock > 0) {
			inStock--;
		} else {
			throw new IllegalStateException("No copy of this movie currently available.");
		}
	}
}