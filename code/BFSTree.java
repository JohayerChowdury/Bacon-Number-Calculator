import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/*
 * Johayer Rahman Chowdury
 * Final Project
 * December 15/ 2020
 * Uses Breadth-First-Search to calculate the shortest distances from a vertex to the source.
 * Description of Input: Two parameters: graph and source.
 * Description of Output: Fields that represent the shortest distances, parents and visited vertices.
 */

public class BFSTree {
  private IntGraphList graph;
  private int source;
  private Map < Integer, Integer > distances = new HashMap < >(); // vertices to integers
  private Map < Integer, Integer > parents = new HashMap < >(); // vertices to vertices
  private Queue < Integer > visited = new LinkedList < >(); // keep track of visited vertices
  
  public BFSTree(IntGraphList graph, int source) {
    this.graph = graph; // initial graph set to field of graph
    this.source = source; // initial source set to field of source
    // initialize a set representing the vertices in graph
    Set < Integer > set = graph.getVertices();

    for (int vertex: set) { // for every vertex
      this.distances.put(vertex, -1); // Initialize distance
      this.parents.put(vertex, null); // Initialize parent
    }

    this.distances.put(source, 0); // put source in map with distance of 0
    this.visited.add(source); // source is the first to be visited
    // "MEAT" of BFS Algorithm
    while (!this.visited.isEmpty()) {
      int u = this.visited.remove();
      Set < Integer > adjList = graph.getAdjacencyList(u);
      for (int v: adjList) {
        if (this.distances.get(v) == -1) {
          this.distances.put(v, this.distances.get(u) + 1);
          this.parents.put(v, u);
          this.visited.add(v);
        }
      }
    }

  }

  // returns the distance from the source to the vertex parameter
  public int getDistanceTo(int v) {
    // TODO: complete this method
    return distances.get(v);
  }

  // returns the parent of the vertex parameter
  public int getParent(int v) {
    return parents.get(v);
  }

  // getter for graph field
  public IntGraphList getGraph() {
    return graph;
  }

  // getter for source field
  public int getSource() {
    return source;
  }

  // getter for distances field
  public Map < Integer, Integer > getDistances() {
    return distances;
  }
}

/*
 * BFS Algorithm from Prof. Hancock lecture
 * 
 * public void breadthFirstSearch(int source) { Map<Integer, Integer> distances
 * = new HashMap<>(); //vertices to integers Map<Integer, Integer> parents = new
 * HashMap<>(); //vertices to vertices
 * 
 * for (int node : adjacencyList.keySet()) { //for every vertex
 * distances.put(node, -1); //Initialize distance parents.put(node, null);
 * //Initialize parent }
 * 
 * Queue<Integer> q = new LinkedList<>(); //keep track of "visited" vertices
 * distances.put(source, 0); q.add(source);
 * 
 * while (!q.isEmpty()) { int u = q.remove(); for(int v : adjacencyList.get(u))
 * { if(distances.get(v) == -1) { distances.put(v, distances.get(u) + 1);
 * parents.put(v, u); q.add(v); } } }
 * 
 * //do something with distance, and/or parents (future code implementation)
 * 
 * }
 * 
 * }
 */
