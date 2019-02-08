package edu.ncsu.csc316.file_compressor.manager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import edu.ncsu.csc316.file_compressor.exception.InvalidInputFileTypeException;
import edu.ncsu.csc316.file_compressor.list_util.LinkedList;

/**
 * This program determines if a file needs to be compressed or decompressed, and
 * then compresses or decompresses it accordingly.
 * 
 * @author Samuel Jessee (sijessee)
 *
 */
public class FileCompressorManager {

	/** string to return after compression */
	public static final String COMP = "COMPRESS";

	/** string to return after decompression */
	public static final String DECOMP = "DECOMPRESS";

	/** correct file type */
	public static final String TYPE = ".txt";

	/** compressed file ending */
	public static final String COMP_NAME = "-compressed.txt";

	/**
	 * Starts the program and begins processing the file.
	 * 
	 * @param args
	 *            fileName
	 * @throws IOException
	 * @throws InvalidInputFileTypeException
	 * 
	 */
	public static void main(String[] args) throws InvalidInputFileTypeException, IOException {
		processFile(args[0]);
	}

	/**
	 * The processFile method processes the file with the given name to
	 * determine whether to compress or decompress the file. If the file type is
	 * invalid or the file cannot be found, an exception is thrown.
	 * 
	 * @param fileName
	 *            the name of the file to process
	 * @return "DECOMPRESS" if the file was decompressed, or "COMPRESS" if the
	 *         file was compressed
	 * @throws InvalidInputFileTypeException
	 *             if the file is not a valid .txt file
	 * @throws IOException
	 */
	public static String processFile(String fileName) throws InvalidInputFileTypeException, IOException {

		fileName = fileName.trim();
		int length = fileName.length();

		if (length < TYPE.length()) {
			throw new InvalidInputFileTypeException();
		}

		String s = fileName.substring(length - TYPE.length());

		if (!s.equals(TYPE)) {
			throw new InvalidInputFileTypeException();
		}

		InputStreamReader reader = new InputStreamReader(new FileInputStream(fileName), "UTF8");
		String newName;
		FileOutputStream fos;
		Writer writer;

		InputStreamReader scan = new InputStreamReader(new FileInputStream(fileName), "UTF8");
		s = "" + ((char) scan.read()) + ((char) scan.read());
		boolean decompress = s.equals("0 ");
		scan.close();

		if (decompress) {
			newName = "TextFileCompressor/output/decompressed/" + fileName.substring(0, length - COMP_NAME.length())
					+ TYPE;
			fos = new FileOutputStream(newName);
			writer = new OutputStreamWriter(fos, "UTF8");
			decompressFile(reader, writer);
			return DECOMP;
		} else {
			newName = "TextFileCompressor/output/compressed/" + fileName.substring(0, length - TYPE.length())
					+ COMP_NAME;
			fos = new FileOutputStream(newName);
			writer = new OutputStreamWriter(fos, "UTF8");
			compressFile(reader, writer);
			return COMP;
		}
	}

	/**
	 * Method that compresses a file.
	 * 
	 * @param reader
	 *            file reader
	 * @param writer
	 *            file writer
	 * @throws IOException
	 */
	public static void compressFile(InputStreamReader reader, Writer writer) throws IOException {
		String word = "";
		int r;
		LinkedList list = new LinkedList();
		char c;
		while ((r = reader.read()) != -1) {
			c = (char) r;
			if (isLetter(c)) {
				word += c;
			} else {
				if (word.length() > 0) {
					writer.write(list.addWord(word));
					word = "";
					writer.write(c);
				}
			}
		}
		if (word.length() > 0) {
			writer.write(list.addWord(word));
			word = "";
		}
		reader.close();
		writer.close();
	}

	/**
	 * Method that decompresses a file.
	 * 
	 * @param reader
	 *            file reader
	 * @param writer
	 *            file writer
	 * @throws IOException
	 */
	public static void decompressFile(InputStreamReader reader, Writer writer) throws IOException {
		String word = "";
		LinkedList list = new LinkedList();
		char c;
		int index = 0;
		word = word + ((char) reader.read()) + ((char) reader.read());
		if (!word.equals("0 ")) {
			throw new IllegalArgumentException();
		}
		while ((c = (char) reader.read()) != '0') {
			if (isLetter(c)) {
				if (index > 0) {
					writer.write(list.retrieve(index));
					index = 0;
				}
				word += c;
			} else if (isDigit(c)) {
				if (word.length() > 0) {
					writer.write(list.addWord(word));
					word = "";
				}
				index = (index * 10) + c;
			} else {
				if (index > 0) {
					writer.write(list.retrieve(index));
					index = 0;
				} else if (word.length() > 0) {
					writer.write(list.addWord(word));
					word = "";
				}
				writer.write(c);
			}
		}
		if (word.length() > 0) {
			writer.write(list.addWord(word));
			word = "";
		} else if (index > 0) {
			writer.write(list.retrieve(index));
			index = 0;
		}
		reader.close();
		writer.close();
	}

	/**
	 * Returns true if char is a letter
	 * 
	 * @param c
	 *            character
	 * @return true if c is a letter
	 */
	public static boolean isLetter(char c) {
		return Character.isLetter(c);
	}

	/**
	 * Returns true if char is a digit
	 * 
	 * @param c
	 *            character
	 * @return true if c is a digit
	 */
	public static boolean isDigit(char c) {
		return Character.isDigit(c);
	}

}