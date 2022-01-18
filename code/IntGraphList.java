import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * This class represents a graph stored using adjacency lists for each vertex.
 * 
 * @author Mark Hancock
 * @author <your name can go here if you modify this>
 */
public class IntGraphList {
	/**
	 * A map of adjacency lists for each vertex. This differs slightly from the
	 * weekly exercises in that it uses a Set, so that no duplicates can be
	 * added to the adjacency lists and has fast access time.
	 */
	private Map<Integer, HashSet<Integer>> adjacencyList;

	/**
	 * Creates a new empty graph.
	 */
	public IntGraphList() {
		// use a HashMap to improve efficiency (ignores order)
		adjacencyList = new HashMap<Integer, HashSet<Integer>>();
	}

	/**
	 * Creates a new graph with vertices numbered 0 to numVertices - 1.
	 * 
	 * @param numVertices the number of vertices to add to this graph on
	 *                    creation
	 */
	public IntGraphList(int numVertices) {
		this();
		for (int i = 0; i < numVertices; i++) {
			addVertex(i);
		}
	}

	/**
	 * Adds a vertex with a value to this graph.
	 * 
	 * @param value the integer value of this vertex
	 * @throws IllegalArgumentException if this vertex value has been added
	 *                                  before
	 */
	public void addVertex(int value) {
		if (hasVertex(value)) {
			throw new IllegalArgumentException(value
					+ " is already in the graph");
		}
		adjacencyList.put(value, new HashSet<>());
	}

	/**
	 * Adds an edge from first to second to this graph. Note that this differs
	 * from the weekly exercises version, in that it only adds in one direction.
	 * 
	 * @param first  the first vertex in the edge
	 * @param second the second vertex in the edge
	 * 
	 * @throws IllegalArgumentException if either first or second are not
	 *                                  vertices in the graph.
	 */
	public void addEdge(int first, int second) {
		checkVertex(first);
		adjacencyList.get(first).add(second);
	}

	/**
	 * Returns the number of vertices in this graph.
	 * 
	 * @return the number of vertices in this graph.
	 */
	public int getNumVertices() {
		return adjacencyList.size();
	}

	/**
	 * Returns an adjacency list for vertex v.
	 * 
	 * @param v the vertex for which to retrieve the adjacency list
	 * @return an adjacency list for vertex v.
	 */
	public Set<Integer> getAdjacencyList(int v) {
		checkVertex(v);
		return Collections.unmodifiableSet(adjacencyList.get(v));
	}

	/**
	 * Returns the set of vertices in this graph.
	 * 
	 * @return the set of vertices in this graph.
	 */
	public Set<Integer> getVertices() {
		return Collections.unmodifiableSet(adjacencyList.keySet());
	}

	/**
	 * Checks if this vertex is in the graph and returns true if it is, false if
	 * it is not.
	 * 
	 * @param v the vertex to check
	 * @return true if vertex v is in the graph, false otherwise.
	 */
	public boolean hasVertex(int v) {
		return adjacencyList.containsKey(v);
	}

	/**
	 * Reads a graph in from a comma-separated file of adjacency lists.
	 * 
	 * @param scanner          the scanner to read from
	 * @param statusAfterCount after this many lines are processed, outputs a
	 *                         message to the console, -1 for no message
	 * @return a graph object representing the graph from the file
	 */
	public static IntGraphList read(Scanner scanner, int statusAfterCount) {
		IntGraphList graph = new IntGraphList();

		if (scanner.hasNextLine()) {
			if (scanner.hasNextInt()) {
				scanner.nextInt(); // ignore number of records
			}
			scanner.nextLine();
		}

		int i = 0;
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			Scanner lineScanner = new Scanner(line).useDelimiter(",");

			if (lineScanner.hasNextInt()) {
				int value = lineScanner.nextInt();
				graph.addVertex(value);

				while (lineScanner.hasNextInt()) {
					graph.addEdge(value, lineScanner.nextInt());
				}
			}
			lineScanner.close();

			i++;
			if (statusAfterCount > 0 && i % statusAfterCount == 0) {
				System.out.printf("Processed %,d records so far...\n", i);
			}
		}

		return graph;
	}

	/**
	 * Helper function to check if this vertex is in the graph and throw an
	 * error if not.
	 * 
	 * @param v the vertex to check.
	 */
	private void checkVertex(int v) {
		if (!hasVertex(v)) {
			throw new IllegalArgumentException("vertex " + v
					+ " is not in the graph");
		}
	}
}