import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Random;

public class ClimbTSP {
    // Main method for finding a solution using the Hill Climbing algorithm
    public void calculate(int[][] distanceMatrix, LinkedList<Integer[]> vertexList, int maxIterations, int noImproveCutoff) {
        System.out.println("= Hill Climb Solution =");
        long startTime = System.nanoTime();
        int locationCount = distanceMatrix.length;
        int[] currentTour = generateRandomTour(locationCount); // Generate a random initial tour.
        int[] shortestTour = Arrays.copyOf(currentTour, locationCount); // Initialize the shortest tour with the initial
                                                                        // tour.
        int shortestTourDistance = calculateDistance(shortestTour, distanceMatrix); // Calculate the distance of the
                                                                                    // initial tour.

        int noImprovementCycle = 0;
        // Repeat the hill climbing process a fixed number of times (1000 in this case).
        for (int tourAttempt = 0; (tourAttempt < maxIterations) && (noImprovementCycle < noImproveCutoff); tourAttempt++) {
            int[] neighbouringTours = getNeighbouringTour(currentTour); // Generate a neighboring tour.
            int neighbouringDistance = calculateDistance(neighbouringTours, distanceMatrix); // Calculate the distance
                                                                                             // of the neighboring tour.

            // If the neighboring tour is better (shorter), update the best tour and
            // distance.
            if (neighbouringDistance < shortestTourDistance) {
                shortestTour = neighbouringTours;
                shortestTourDistance = neighbouringDistance;
                currentTour = Arrays.copyOf(currentTour, locationCount); // Update the current tour.
                noImprovementCycle = 0; // reset cycle counter
            } else {
                noImprovementCycle++; // provides a limit to the number of times no improvements can be made
            }
        }

        // Construct a string representation of the best tour.
        String outputString = "";
        for (int index : shortestTour) {
            Integer[] coordinate = vertexList.get(index);
            String coordinateString = "(" + coordinate[0] + "," + coordinate[1] + ")->";
            outputString += coordinateString;
        }
        outputString += "END";

        long endTime = System.nanoTime();
        double elapsedTime = (endTime - startTime) / 1e6; // Calculate the elapsed time in milliseconds.

        // Print the results.
        System.out.println("[*] Tour:" + outputString);
        System.out.println("[*] Total Distance: " + shortestTourDistance);
        System.out.println("[*] Completion Time: " + elapsedTime + "ms\n");
    }

    // Function to generate a neighboring tour by swapping two random cities in the
    // current tour.
    private int[] getNeighbouringTour(int[] currentTour) {
        int locationCount = currentTour.length;
        int[] neighbours = Arrays.copyOf(currentTour, locationCount);

        Random tourRandomiser = new Random();
        int firstSwap = tourRandomiser.nextInt(locationCount);
        int secondSwap = tourRandomiser.nextInt(locationCount);

        int tempHolder = neighbours[firstSwap]; // Swap two cities in the tour.
        neighbours[firstSwap] = neighbours[secondSwap];
        neighbours[secondSwap] = tempHolder;

        return neighbours; // Return the neighboring tour.
    }

    // Function to generate a random tour by shuffling a list of locations.
    private int[] generateRandomTour(int locationCount) {
        ArrayList<Integer> tour = new ArrayList<>();
        for (int i = 0; i < locationCount; i++) {
            tour.add(i);
        }
        Collections.shuffle(tour);
        int[] randomisedTour = new int[locationCount];
        for (int i = 0; i < locationCount; i++) {
            randomisedTour[i] = tour.get(i);
        }
        return randomisedTour;
    }

    // Function to calculate the total distance of a given tour using a distance
    // matrix.
    private int calculateDistance(int[] currentTour, int[][] distanceMatrix) {
        int totalDistance = 0;
        int locationCount = currentTour.length;
        for (int i = 0; i < locationCount - 1; i++) {
            int travellingFrom = currentTour[i];
            int travellingTo = currentTour[i + 1];
            totalDistance += distanceMatrix[travellingFrom][travellingTo];
        }
        totalDistance += distanceMatrix[currentTour[locationCount - 1]][currentTour[0]]; // This adds the distance from
                                                                                         // the end to start location,
                                                                                         // "rounding off" the trip.
        return totalDistance;
    }
}