import java.util.LinkedList;
import java.lang.Math;

public class Map {
    private int nodeCount; 

    private String name;

    private LinkedList<Integer[]> vertexList = new LinkedList<Integer[]>();

    private int[][] adjacencyMatrix;

    public Map(String name, int nodeCount) {
        this.nodeCount = nodeCount; // Initialize the number of nodes.
        this.name = name; // Initialize the map name or identifier.
    }

    // Add a new vertex (a point with coordinates) to the map.
    public void addNewVertex(Integer[] newVertex) {
        this.vertexList.add(newVertex); // Add the new vertex to the list.
    }

    // Generate an adjacency matrix for the map based on vertex distances.
    public void generateMatrix() {
        this.adjacencyMatrix = new int[vertexList.size()][vertexList.size()]; // Initialize the adjacency matrix.

        // Fill the adjacency matrix with distances between vertices.
        for (int row = 0; row < adjacencyMatrix.length; row++) {
            for (int col = 0; col < adjacencyMatrix[0].length; col++) {
                adjacencyMatrix[row][col] = calculateDistance(this.vertexList.get(row), this.vertexList.get(col));
            }
        }
    }

    // Calculate the distance between two points (vertices) using the Euclidean
    // distance formula.
    private Integer calculateDistance(Integer[] p1, Integer[] p2) {
        Double distance = Math.sqrt((Math.pow((p2[0] - p1[0]), 2) + Math.pow((p2[1] - p1[1]), 2)));
        return (int) Math.round(distance); // Return the rounded distance as an integer.
    }

    // Get the list of vertices in the map.
    public LinkedList<Integer[]> getVertexList() {
        return this.vertexList;
    }

    // Get the adjacency matrix that represents distances between vertices.
    public int[][] getAdjacencyMatrix() {
        return this.adjacencyMatrix;
    }
}
