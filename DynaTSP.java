import java.util.Arrays;
import java.util.LinkedList;

public class DynaTSP {
    private int cost;
    private int[] tourIndex;

    // The main method for solving the Traveling Salesman Problem (TSP) using
    // Dynamic Programming.
    public void calculate(int[][] distanceMatrix, LinkedList<Integer[]> vertexList) {
        System.out.println("= Dynamic Programming Solution ="); // Print the header.
        long startTime = System.nanoTime(); // Record the start time for execution timing.
        int numCities = distanceMatrix.length; // Get the number of cities.

        // Initialize the dynamic programming table for subproblem solutions.
        int[][] visitedMatrix = new int[1 << numCities][numCities];
        for (int[] row : visitedMatrix) {
            Arrays.fill(row, -1); // Initialize with -1 (unvisited).
        }
        visitedMatrix[1][0] = 0; // Mark the starting city (0) as visited.

        // Dynamic Programming loop to compute the shortest path for all subsets of
        // cities.
        for (int subset = 1; subset < (1 << numCities); subset += 2) {
            for (int currentCity = 1; currentCity < numCities; currentCity++) {
                if ((subset & (1 << currentCity)) != 0) { // Check if currentCity is in the subset.
                    for (int prevCity = 0; prevCity < numCities; prevCity++) {
                        if ((subset & (1 << prevCity)) != 0 && prevCity != currentCity
                                && distanceMatrix[prevCity][currentCity] > 0
                                && visitedMatrix[subset ^ (1 << currentCity)][prevCity] != -1) {
                            // Update the minimum distance for the current subset and currentCity.
                            if (visitedMatrix[subset][currentCity] == -1
                                    || visitedMatrix[subset][currentCity] > visitedMatrix[subset
                                            ^ (1 << currentCity)][prevCity] + distanceMatrix[prevCity][currentCity]) {
                                visitedMatrix[subset][currentCity] = visitedMatrix[subset
                                        ^ (1 << currentCity)][prevCity] + distanceMatrix[prevCity][currentCity];
                            }
                        }
                    }
                }
            }
        }

        int minCost = -1;
        int lastCity = -1;

        // Find the minimum cost of the TSP tour and the last city visited.
        for (int currentCity = 1; currentCity < numCities; currentCity++) {
            if (distanceMatrix[currentCity][0] > 0 && visitedMatrix[(1 << numCities) - 1][currentCity] != -1) {
                if (minCost == -1 || minCost > visitedMatrix[(1 << numCities) - 1][currentCity]
                        + distanceMatrix[currentCity][0]) {
                    minCost = visitedMatrix[(1 << numCities) - 1][currentCity] + distanceMatrix[currentCity][0];
                    lastCity = currentCity;
                }
            }
        }

        this.cost = minCost; // Store the minimum cost.

        // Reconstruct the TSP tour.
        int[] tempTourIndex = new int[numCities];
        int subset = (1 << numCities) - 1;
        for (int i = numCities - 1; i >= 0; i--) {
            tempTourIndex[i] = lastCity; // Add the current city to the tour.
            int prevSubset = subset ^ (1 << lastCity);
            for (int prevCity = 0; prevCity < numCities; prevCity++) {
                if ((prevSubset & (1 << prevCity)) != 0 && distanceMatrix[prevCity][lastCity] > 0
                        && visitedMatrix[subset][lastCity] == visitedMatrix[prevSubset][prevCity]
                                + distanceMatrix[prevCity][lastCity]) {
                    lastCity = prevCity;
                    subset = prevSubset;
                    break;
                }
            }
        }
        this.tourIndex = tempTourIndex; // Store the tour index.

        String outputString = "";

        // Construct a string representing the TSP tour.
        for (int index : this.tourIndex) {
            Integer[] coordinate = vertexList.get(index);
            String coordinateString = "(" + coordinate[0] + "," + coordinate[1] + ")->";
            outputString += coordinateString;
        }
        outputString += "END";

        long endTime = System.nanoTime(); // Record the end time for execution timing.
        double elapsedTime = (endTime - startTime) / 1e6; // Calculate elapsed time in milliseconds.

        // Print the TSP tour, total distance, and completion time.
        System.out.println("[*] Tour: " + outputString);
        System.out.println("[*] Total Distance: " + this.cost);
        System.out.println("[*] Completion Time: " + elapsedTime + " ms\n");
    }

}
