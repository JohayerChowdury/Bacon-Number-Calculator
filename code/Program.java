import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/*
 * Johayer Rahman Chowdury & Mark Hancock
 * Final Project
 * December 15/ 2020
 * Program that computes answers to questions on Codio.
 * Description of Input: Text file that represents the graph of the Kevin Bacon dataset.
 * Description of Output: Print statements of computations.
 */

public class Program {
  /**
	 * This is the main method of your program.
	 * 
	 * @param args Command-line arguments
	 * @throws IOException If reading from your file had an error.
	 */
  public static void main(String[] args) throws IOException {
    /* TODO: change this source to your actor ID */
//    int source = 1706767; // 1706767 Jonah Hill
    int source = 2;
    /*
		 * TODO: create several sample adjacency lists in text files yourself to
		 * test your code before running it on the large Kevin Bacon dataset
		 */

//    Scanner scanner = new Scanner(new File("data/adj.txt"));
    Scanner scanner = new Scanner(new File("data/small-adj.txt"));
    IntGraphList graph = IntGraphList.read(scanner, 100000);
    scanner.close();

    /*
		 * TODO: you can also create custom graphs within the code as follows,
		 * but it is probably easier to just use your own test file.
		 *
		 * Example of creating graphs in code, rather than from a file:
		 */
    // Graph graph = new Graph();
    //
    // graph.addNode(1);
    // graph.addNode(2);
    // graph.addNode(3);
    // graph.addNode(4);
    //
    // graph.addEdge(1, 2);
    // graph.addEdge(1, 3);
    // graph.addEdge(1, 4);
    // graph.addEdge(2, 1);
    // graph.addEdge(3, 1);
    // graph.addEdge(3, 4);
    // graph.addEdge(4, 1);
    // graph.addEdge(4, 3);
    System.out.println("The original source actor is Jonah Hill.");
    System.out.println("Their actor ID is: " + source + "\n");
    DFSTree dfsTree = new DFSTree(graph);
    printComponentReport(dfsTree);

    BFSTree bfsTree = new BFSTree(graph, source);
    printPathReport(bfsTree);

    //create a new PrintStream object using private method
    PrintStream outputFile = createFile();

    //initialize the number of actors
    int randomNumberOfActors = 1000;
    outputFile.println("Source,Fraction,Mean Distance");
    var allSources = new ArrayList < >(graph.getVertices());
    Collections.shuffle(allSources);
    int min = Math.min(allSources.size(), randomNumberOfActors);
    for (int s: allSources.subList(0, min)) {
      if (bfsTree.getDistanceTo(s) >= 1) {
        printSimplePathReport(new BFSTree(graph, s), outputFile);
      }
    }
    outputFile.close();
    System.out.println("Printing results to printSimplePathReportFile.txt is completed.");
  }

  public static void printComponentReport(DFSTree dfsTree) {
    // Report on components
    System.out.println("Component Report");

    // initialize objects used to print out important info
    List < Integer > componentSizes = dfsTree.getComponentSizes();
    Statistics dfsStats = new Statistics(componentSizes);

    // number of connected components
    System.out.println("The graph has " + dfsTree.getNumberOfConnectedComponents() + " connected components.");

    // statistics
    printStats(dfsStats, "Statistics of size(s) of all components: ");

    // table of component sizes
    printTable(dfsStats, "| Size of Component: | Number of Components of this size: |");

    System.out.println();

  }

  public static void printPathReport(BFSTree bfsTree) {
    // Report on path from source
    System.out.println("Path Report");

    // initialize objects used to print out important info
    List < Integer > shortestDistances = new ArrayList < Integer > (bfsTree.getDistances().values());
    Statistics bfsStats = new Statistics(shortestDistances);

    // shortest distance fraction
    double fraction = shortestDistanceFraction(bfsStats, shortestDistances);
    System.out.println("Fraction of actors that can be reached from Jonah Hill: " + fraction);

    // statistics
    printStats(bfsStats, "Statistics of shortest distance(s) from Jonah Hill to other actors:");

    // table of shortest distances
    printTable(bfsStats, "| Distance from source: | Number of Actors: |");

    System.out.println();

  }

  //
  public static void printSimplePathReport(BFSTree bfsTree, PrintStream outputFile) {
    // Report on path from source
    // initialize objects used to print out important info
    List < Integer > shortestDistances = new ArrayList < Integer > (bfsTree.getDistances().values());
    Statistics bfsSimpleStats = new Statistics(shortestDistances);

    // shortest distance fraction
    double fraction = shortestDistanceFraction(bfsSimpleStats, shortestDistances);

    outputFile.println(bfsTree.getSource() + "," + fraction + "," + bfsSimpleStats.getMean());

  }

  /**
	 * 
	 * @param stats: statistics object
	 * @param s:     header
	 */
  // private method used to print out the statistics of each statistics object
  // terms are size of component and shortest distance
  private static void printStats(Statistics stats, String s) {
    System.out.println("\n" + s);

    // minimum
    // N/A for shortest distance
    System.out.println("Minimum: " + stats.getMin());

    // maximum
    System.out.println("Maximum: " + stats.getMax());

    // mean
    System.out.println("Mean: " + stats.getMean());

    // Q1
    System.out.println("1st quartile: " + stats.getQ1());

    // Q2
    System.out.println("2nd quartile: " + stats.getQ2());

    // Q3
    System.out.println("3rd quartile: " + stats.getQ3());

  }

  /**
	 * 
	 * @param stats:   statistics object
	 * @param headers: string headers
	 */
  // private method to print the table for each tree
  // terms are size of component and shortest distance
  private static void printTable(Statistics stats, String headers) {
    // title of table
    System.out.println("\n" + headers);
    // print statement to copy into Codio
    System.out.println("| --- | --- |");

    for (int term: stats.getSortedUniqueKeys()) {
      int count = stats.getCountOf(term);
      // print the term and the count
      System.out.print("|" + term);
      System.out.println("|" + count + "|");
    }

    System.out.println();
  }

  /**
	 * 
	 * @param stats:     statistics object
	 * @param distances: list of distances
	 * @return fraction of reachableActors/allActors
	 */
  // private method used to calculate the shortestDistanceFraction
  private static double shortestDistanceFraction(Statistics stats, List < Integer > distances) {

    // non reachable actors are actors that have a distance of -1 from
    // source
    double nonReachableActors = stats.getCountOf( - 1);

    // the total amount of actors is equal to the amount of values
    // within the distances list - 1 (do not count source actor)
    double allActors = distances.size() - 1;

    // the fraction will be implemented as follows:
    // (reachableActors/allActors) = 1 - (nonReachableActors/allActors)
    double fraction = 1 - nonReachableActors / allActors;
    return fraction;

  }

  /**
	 * 
	 * @return PrintStream object to print results from printSimplePathReport
	 * @throws FileNotFoundException
	 */
  private static PrintStream createFile() throws FileNotFoundException {
    //initialize new PrintStream object with file name shown below
    PrintStream outputFile = new PrintStream(new File("printSimplePathReportFile"));
    return outputFile;
  }

}