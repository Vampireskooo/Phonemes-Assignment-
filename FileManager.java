
/* 
 * Student number : 2422647
 * CSCU9T4
 * version: March2017
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManager {
	/**
	 * ArrayList variables to store the phonemes' objects
	 */
	private static ArrayList<Phonemes> stopList = new ArrayList<Phonemes>();
	private static ArrayList<Phonemes> closureList = new ArrayList<Phonemes>();
	private static ArrayList<Phonemes> fricativeList = new ArrayList<Phonemes>();
	private static ArrayList<Phonemes> affricativeList = new ArrayList<Phonemes>();
	private static ArrayList<Phonemes> nasalList = new ArrayList<Phonemes>();
	private static ArrayList<Phonemes> semivowelList = new ArrayList<Phonemes>();
	private static ArrayList<Phonemes> VowelList = new ArrayList<Phonemes>();
	private static ArrayList<Phonemes> OthersList = new ArrayList<Phonemes>();

	/**
	 * a List variable to store all the lists.
	 */
	private static List<List<Phonemes>> phonemesList = new ArrayList<List<Phonemes>>();

	/**
	 * boolean variables to set condition if the block of lines for deleting a
	 * file should be executed
	 */
	private static boolean delete = false;

	/**
	 * variables for manipulating the extracted data
	 */
	private static String phoneme;
	private static double start;
	private static double end;
	private static int sum;

	/**
	 * An array full of the names of the classes, used for the printing
	 */
	private static String s1 = Stops.class.getSimpleName();
	private static String s2 = Closures.class.getSimpleName();
	private static String s3 = Fricatives.class.getSimpleName();
	private static String s4 = Affricatives.class.getSimpleName();
	private static String s5 = Nasals.class.getSimpleName();
	private static String s6 = Semivowels.class.getSimpleName();
	private static String s7 = Vowels.class.getSimpleName();
	private static String s8 = Others.class.getSimpleName();
	private static String sArray[] = { s1, s2, s3, s4, s5, s6, s7, s8 };

	/**
	 * A two-dimentional array used to check and sort out the data the first
	 * dimention is in order :stops, closures, fricatives, affricatives, nasals,
	 * semivowels
	 */
	private static String phonemeArray[][] = { { "b", "d", "g", "p", "t", "k", "dx", "q" },
			{ "bcl", "dcl", "gcl", "pcl", "tck", "kcl", "tcl" }, { "s", "sh", "z", "zh", "f", "th", "v", "dh" },
			{ "jh", "ch" }, { "m", "n", "ng", "em", "en", "eng", "nx" }, { "l", "r", "w", "y", "hh", "hv", "el" },
			{ "iy", "ih", "eh", "ey", "ae", "aa", "aw", "ay", "ah", "ao", "oy", "ow", "uh", "uw", "ux", "er", "ax",
					"ix", "axr", "ax-r" } };

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, IOException {

		/**
		 * variables for initialization, the sample rate is set on default
		 */
		String inputFileName = "";
		int samplerate = 16000;
		String outputFileName = "";
		/**
		 * If -else if blocks to check the validity and the number of arguments
		 * given in the Command-line. The program runs in two cases - four
		 * arguments if it sees a first argument -s and then sample rate, input
		 * file, output file name or if it has two arguments - input file,
		 * output file name. If something went wrong, prints a statement and
		 * terminates the program.
		 */

		if (args.length < 2) {
			System.out.println("Not enough arguments. Run the program again.");
			System.exit(1);
		} else if (args.length == 2) {
			try {
				inputFileName = args[0];
				File inputFile = new File(inputFileName);
				Scanner in = new Scanner(inputFile);
			} catch (Exception e) {
				System.err.println("Input file not found. Run the program again.");
				System.exit(1);
			}
			try {
				outputFileName = args[1];

			} catch (Exception e) {

			}
		} else if (args.length == 3) {
			System.out.println("Not enough arguments. Run the program again.");
			System.exit(1);
		} else if (args[0].equals("-s")) {
			if (isNumber(args[1])) {
				samplerate = Integer.parseInt(args[1]);
				if (samplerate < 0) {
					System.out.println("The sample rate should be positive number. Run the program again.");
					System.exit(1);
				}
			} else {
				System.out
						.println("After -s is the sample rate argument which have to be a positive number. Try again.");
				System.exit(1);
			}
			try {
				inputFileName = args[2];
				File inputFile = new File(inputFileName);
				Scanner in = new Scanner(inputFile);
			} catch (Exception e) {
				System.err.println("Input file not found. Run the program again.");
				System.exit(1);
			}
			try {
				outputFileName = args[3];
			} catch (Exception e) {
				System.err.println("Output file not found. Run the program again.");
			}
		} else {
			System.out.println("The four argument are not in order, or they don't start with -s");
			System.exit(1);
		}
		if (args.length > 4) {
			System.out.println("Too many arguments. Run the program again.");
			System.exit(1);
		}

		/**
		 * Variables to store the input file and a scanner to go trough the
		 * file.
		 */
		File inputFile = new File(inputFileName);
		Scanner in = new Scanner(inputFile);
		int i = 0; // initialised for the charAt method and then the separation
		/**
		 * A while loop to go trough the input file and get all the valid
		 * information.
		 */
		while (in.hasNextLine()) {
			String line = in.nextLine();
			/**
			 * if the next line is empty or it doesn't start with a digit, it
			 * skips the line else procede with extracting the data
			 */
			if (line.isEmpty()) {
				continue;
			} else if (!(Character.isDigit(line.charAt(0)))) {
				continue;
			} else {
				/**
				 * Using an array to store the line split into 3 parts. I am
				 * using blank space as delimiter.The first and second parts are
				 * checked and stored as doubles, the third is stored as a
				 * string. After that the start and end are divided by the
				 * sample rate.
				 */
				String[] parts = line.split(" ");
				if (isNumber(parts[0])) {
					start = Double.parseDouble(parts[0]);
				} else {
					continue;
				}
				if (isNumber(parts[1])) {
					end = Double.parseDouble(parts[1]);
				} else {
					continue;
				}
				phoneme = parts[2];
				phoneme.trim();
				start = (start / samplerate);
				end = (end / samplerate);

				/**
				 * After the data has been extracted and put into variables it
				 * is given to the following method to do what it says.
				 */
				sortingIntoClasses(start, end, phoneme);
			}

		}
		/**
		 * Adding the lists in one list in order to create a file for each class
		 */
		phonemesList.add(stopList);
		phonemesList.add(closureList);
		phonemesList.add(fricativeList);
		phonemesList.add(affricativeList);
		phonemesList.add(nasalList);
		phonemesList.add(semivowelList);
		phonemesList.add(VowelList);
		phonemesList.add(OthersList);

		/**
		 * A for loop to create new printWriter variables. If a list is empty it
		 * still create a printWriter, which creates a file that then is deleted
		 * before termination of the program because the program is not very
		 * robust and if the order is somewhere broken the program will not
		 * function as expected,probably.
		 */
		List<PrintWriter> writers = new ArrayList<PrintWriter>();
		for (int k = 0; k <= phonemesList.size() - 1; k++) {
			if (!(phonemesList.get(k).size() == 0)) {
				PrintWriter w = new PrintWriter(outputFileName + sArray[k] + ".dat");
				writers.add(w);
			} else {
				delete = true;
				PrintWriter w = new PrintWriter("tobedeleted");
				writers.add(w);
			}
		}

		/**
		 * for loop to create files and to print in the console how many
		 * phonemes a class have.
		 */
		for (i = 0; i < phonemesList.size(); i++) {
			printFilesAndSize(writers.get(i), phonemesList.get(i));
		}

		/**
		 * closing all the IO variables
		 */
		in.close();
		for (PrintWriter w : writers) {
			w.close();
		}
		/**
		 * deleting the empty file(s)
		 */
		if (delete) {
			File fileToDelete = new File("tobedeleted");
			fileToDelete.delete();
		}
		/**
		 * printing in the console the sum of all phonemes from the input file
		 */
		System.out.println("The number of the phonemes in the input file is: " + sum);

		/**
		 * Creating a scanner and a double type to store the offset variable
		 * provided by the user.
		 */
		Scanner num = new Scanner(System.in);
		System.out.print("Enter an offset: ");
		double offset = 0;
		do {
			offset = num.nextDouble();
			if (offset < 0) {
				System.out.println("The offset should be a positive number. Try again.");
			}
		} while (offset < 0);

		/**
		 * A for loop to go trough all the lists and find if there is an offset.
		 * If it finds print the corresponding phoneme If it doesn't find
		 * corresponding, it prints out a statement.
		 */
		for (int p = 0; p < phonemesList.size(); p++) {
			exists(offset, phonemesList.get(p));
		}
		if (notFound) {
			System.out.println("The offset has not been found");
		}

	}

	/**
	 * A method to see if there is a offset in the data and print the phoneme,
	 * if it finds it the boolean variable is set to "false".
	 * 
	 * @x is the offset given
	 * @list is the list given for searching
	 */
	private static boolean notFound = true;

	public static void exists(double x, List<Phonemes> list) {

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getStart() <= x && x <= list.get(i).getEnd()) {
				System.out.println(list.get(i));
				notFound = false;
			}
		}
	}

	/**
	 * checks if the input can be turn into a double variable
	 * 
	 * @param input
	 *            - the string parsed from the line from the file
	 * @return true - if it can, else
	 */
	public static boolean isNumber(String input) {
		try {
			Double.parseDouble(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * The method creates a file and prints a statement of the size of objects
	 * in it
	 * 
	 * @counter so that in the printing statement the name of the class to be
	 *          the name of the respective list
	 * @write the corresponding writer of the corresponding @list
	 * 
	 */
	private static int counter = 0;

	public static void printFilesAndSize(PrintWriter write, List<Phonemes> list) {

		for (int i = 0; i < list.size(); i++) {
			((PrintWriter) write).println(list.get(i));
		}
		System.out.println(sArray[counter] + " : have " + list.size() + " phoneme(s).");
		counter++;
		sum = sum + list.size();// store the sum of all objects
	}

	/**
	 * checks if a phoneme is from a given type and add the input in the
	 * respective list, if not goes into "others"
	 * 
	 * @param x
	 *            store the start sample number
	 * @param y
	 *            store the end sample number
	 * @param s
	 *            store the phoneme
	 */
	public static void sortingIntoClasses(double x, double y, String s) {
		phoneme = s;
		start = x;
		end = y;
		for (int j = 0; j < phonemeArray[6].length; j++) {
			if (j < 8 && phoneme.equals(phonemeArray[0][j])) {
				stopList.add(new Stops(start, end, phoneme));
				break;
			}

			else if (j < 7 && phoneme.equals(phonemeArray[1][j])) {
				closureList.add(new Closures(start, end, phoneme));
				break;
			}

			else if (j < 8 && phoneme.equals(phonemeArray[2][j])) {
				fricativeList.add(new Fricatives(start, end, phoneme));
				break;
			}

			else if (j < 2 && phoneme.equals(phonemeArray[3][j])) {
				affricativeList.add(new Affricatives(start, end, phoneme));
				break;
			} else if (j < 7 && phoneme.equals(phonemeArray[4][j])) {
				nasalList.add(new Nasals(start, end, phoneme));
				break;
			} else if (j < 7 && phoneme.equals(phonemeArray[5][j])) {
				semivowelList.add(new Semivowels(start, end, phoneme));
				break;
			} else if (phoneme.equals(phonemeArray[6][j])) {
				VowelList.add(new Vowels(start, end, phoneme));
				break;
			} else if (j == phonemeArray[6].length - 1) {
				OthersList.add(new Others(start, end, phoneme));
			}
		}
	}
}