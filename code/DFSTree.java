import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/*
 * Johayer Rahman Chowdury
 * Final Project
 * December 15/ 2020
 * Uses Depth-First-Search to calculate the count and sizes of connected components in the graph.
 * Description of Input: Graph
 * Description of Output: 
 * 	Fields that represent the parents of and visited vertices.
 * 	Fields that represent the count and sizes of connected components.
 */

public class DFSTree {
  private IntGraphList graph;

  // keeps track of visited vertices
  private Set < Integer > visited = new HashSet < >();

  // vertices to vertices
  private Map < Integer, Integer > parents = new HashMap < >();

  // list of the sizes of each component
  private List < Integer > componentSizes = new ArrayList < Integer > ();

  // keep track of individual component size
  private int individualComponentSize;

  // keep track of count of connected components
  private int connectedComponentsCount;

  public DFSTree(IntGraphList graph) {
    this.graph = graph;

    depthFirstSearch(graph);

  }

  private void depthFirstSearch(IntGraphList graph) {

    // initialize a set representing the vertices in graph
    Set < Integer > set = graph.getVertices();
    // remove the vertices in the graph
    this.visited.removeAll(set);

    for (int u: set) { // for every vertex
      if (!this.visited.contains(u)) {
        this.individualComponentSize = 0;
        dfsVisit(graph, u, visited);
        this.connectedComponentsCount++;
        // add the size of each individual component to the field
        this.componentSizes.add(this.individualComponentSize);
      }
    }

  }

  // iterative algorithm of DFS
  private void dfsVisit(IntGraphList graph, int u, Set < Integer > visited) {
    // initialize a new stack to keep track of vertices to visit
    Stack < Integer > verticesToVisit = new Stack < Integer > ();
    verticesToVisit.push(u);

    // every time the algorithm reaches an unvisited vertex
    // add the vertex to the map
    // increment individual component size
    visited.add(u);
    this.individualComponentSize++;

    // "MEAT" of DFS Algorithm
    while (!verticesToVisit.isEmpty()) {
      // pop the top element in stack
      u = verticesToVisit.pop();
      Set < Integer > adjList = graph.getAdjacencyList(u);
      // for every vertex within the adjacency list of the popped element
      // from stack
      for (int w: adjList) {
        // if vertex was not visited
        if (!visited.contains(w)) {
          // push popped element and unvisited vertex into stack
          verticesToVisit.push(u);
          verticesToVisit.push(w);
          // the vertex w in the adjacency list of u is child of u
          parents.put(w, u);
          // every time the algorithm reaches an unvisited vertex
          // add the vertex to the map
          // increment individual component size
          visited.add(w);
          this.individualComponentSize++;
          break;
        }

      }
    }

  }

  // getter
  public IntGraphList getGraph() {
    return graph;
  }

  // getter
  public int getParent(int v) {
    return parents.get(v);
  }

  public Map < Integer,
  Integer > getParents() {
    return parents;
  }

  // getter
  public List < Integer > getComponentSizes() {
    return componentSizes;
  }

  // getter
  public int getNumberOfConnectedComponents() {
    return connectedComponentsCount;
  }

}

/*
 * import java.util.HashMap; import java.util.HashSet; import
 * java.util.LinkedList; import java.util.Map; import java.util.Queue; import
 * java.util.Set; import java.util.List;
 * 
 * //incorporated Prof. Hancock algorithm from lecture public class DFSTree { //
 * TODO: consider what fields you might want // Hint: probably not (just) a
 * graph and source vertex private Set<Integer> visited = new HashSet<>();
 * private Map<Integer, Integer> parents = new HashMap<>();
 * 
 * // TODO: create the DFSTree constructor public DFSTree(IntGraph graph, int
 * source){
 * 
 * //the commented out code fragment was from Prof. Hancock algorithm //
 * Set<Integer> set = graph.getVertices(); // for(int v : set){ // dfsVisit(v,
 * this.visited, this.parents, graph); // }
 * 
 * this.parents.put(source, null); this.visited.add(source); dfsVisit(source,
 * this.visited, this.parents, graph); }
 * 
 * private void dfsVisit(int u, Set<Integer> visited, Map<Integer, Integer>
 * parents, IntGraph graph) { visited.add(u); List<Integer> adjList =
 * graph.getAdjacencyList(u); for (int v: adjList) { if(!visited.contains(v)) {
 * parents.put(v, u); dfsVisit(v, this.visited, this.parents, graph); } } }
 * 
 * 
 * // TODO: create the isConnected method public boolean isConnected(int v){
 * return this.visited.contains(v); }
 * 
 * public void depthFirstSearch() { Set<Integer> visited = new HashSet<>();
 * Map<Integer, Integer> parents = new HashMap<>();
 * 
 * for (int v : adjacencyList.keySet()) { dfsVisit(v, visited, parents); }
 * 
 * }
 * 
 * private void dfsVisit(int u, Set<Integer> visited, Map<Integer, Integer>
 * parents) { visited.add(u); for (int v: adjacencyList.get(u)) {
 * if(!visited.contains(v)) { parents.put(v, u); dfsVisit(v, visited, parents);
 * }
 * 
 * }
 * 
 * }
 * 
 * }
 * 
 * 
 * 
 */
