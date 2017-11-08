//Author: Doren Proctor
//Created for CS450 - Design and Analysis of Algorithms
//Due Nov 6, 2017

import java.io.*;
import java.lang.*;
import java.util.Scanner;
import java.util.Vector;

class Main {
	private static Scanner getScanner() {
		File file = new File("tree.txt");
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		}
		catch (FileNotFoundException ex) {
			System.out.println("File could not be opened\n");
			System.exit(1);
		}
		return sc;
	}

	private static String[] getFirstLine(Scanner sc) {
		String line = sc.nextLine();
		String[] contents = line.split("\\s"); //should be any whitespace
		if (contents.length != 3) {
			System.out.println("\nExpected format of first line is: BuildTree alpha key\n");
			System.exit(1);
		}
		if (!contents[0].equals("BuildTree")) {
			System.out.println("\nFirst word of first line should be BuildTree\n");
			System.exit(1);
		}
		// System.out.println(contents[0]+"\t"+contents[1]+"\t"+contents[2]);
		return contents;
	}

	// private static String[] getInput(Scanner sc) {
	// String line = sc.nextLine();
	// 	String[] contents = line.split("\\s");
	// 	// if (contents.length != 2) {
	// 	// 	System.out.println("\nFound " + contents.length + " entries: " + line);
	// 	// 	System.out.println("Skipping\n");
	// 	// 	return null;
	// 	// }
	// 	// System.out.println("Command: " + contents[0]);
	// 	// System.out.println("Number: " + contents[1]);
	// 	return contents;
	// }

	private static boolean validInput(String[] contents) {
		if (contents.length < 1 || contents[0].isEmpty())
			return false;
		if (contents[0].matches("^.*?(Print|Done).*$")) {
			// System.out.println("Print or Done: " + contents[0]);
			if (contents.length != 1)
				return false;
		}
		else if (contents[0].matches("^.*?(Insert|Search|Delete).*$")) { 
			// System.out.println("Insert, Search, or Delete: " + contents[0]);
			if (contents.length != 2)
				return false;
		}
		return true;
	}

	private static void evaluateInput(String[] contents, Scapegoat sg, int i) throws FileNotFoundException {
		String command = contents[0];
		if (!validInput(contents)) {
			System.out.println("Invalid input on line " + i);
			return;
		}
		int val = -1;
		if (contents.length > 1)
			val = Integer.parseInt(contents[1]);
		if (command.equals("Insert")) {
			sg.insert(val);
		}
		else if (command.equals("Search")) {
			System.out.println("Searching for val: "+val);
			if (sg.search(val) != null)
				System.out.println("Found");
			else
				System.out.println("Not found");
			System.out.println("");
		}
		else if (command.equals("Delete")) {
			sg.delete(val);
		}
		else if (command.equals("Print")) {
			Vector<String> vec = serialize(sg.getRoot());
			TreePrinter treePrinter = new TreePrinter(vec);
			File file = new File("output.svg");
			PrintStream stream = new PrintStream(file);
			treePrinter.printSVG(stream);
			System.out.println("Printing tree to `output.svg`\n");
		}
		else if (command.equals("Done")) {
			System.out.println("Done on line " + i + "\n");
			System.exit(0);
		}
		else {
			System.out.println("Invalid input on line " + i);
		}
	}


	private static void serialize(Node tree, Vector<String> vec) {
    if (tree == null)
        vec.addElement(null);
    else {
        vec.addElement(String.valueOf(tree.value));
        serialize(tree.left, vec);
        serialize(tree.right, vec);
    }
  }


  private static Vector<String> serialize(Node root) {
    Vector<String> vec = new Vector<>();
    serialize(root, vec);
    return vec;
  }


	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc = getScanner();
		String[] contents = getFirstLine(sc);
		Scapegoat sg = new Scapegoat(Double.parseDouble(contents[1]), Integer.parseInt(contents[2]));
		int i = 2;
		while (sc.hasNext()) {
			// contents = getInput(sc);
			String line = sc.nextLine();
			contents = line.split("\\s");
			if (contents != null) {
				evaluateInput(contents, sg, i++);
			}
		}
	}
} 
