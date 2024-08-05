import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Main {
    public static void main(String[] args) throws FileNotFoundException {
        int maxIterations = Integer.parseInt(args[0]);
        int maxNoImprovementCutoff = Integer.parseInt(args[1]);
        try {
            for (int i = 2; i < args.length; i++) {
                Scanner scanner = new Scanner(new File(args[i])); // Create a scanner to read from the file.
                int nodeCount = Integer.parseInt(scanner.nextLine()); // Read the number of nodes.

                // Display the map name.
                System.out.println("\n <== " + args[i] + " ==>");

                Map map = new Map(args[i], nodeCount); // Create a map object with the map name and node count.

                // Read coordinates and add them as vertices to the map.
                while (scanner.hasNext()) {
                    Integer[] coordinate = new Integer[2];
                    String[] coordinateString = scanner.nextLine().split(" ");
                    coordinate[0] = Integer.parseInt(coordinateString[0]);
                    coordinate[1] = Integer.parseInt(coordinateString[1]);
                    map.addNewVertex(coordinate);
                }
                map.generateMatrix(); // Generate the adjacency matrix for the map.

                DynaTSP dynamicCalculation = new DynaTSP(); // Create an object for dynamic programming TSP calculation.
                ClimbTSP hillClimbCalculation = new ClimbTSP(); // Create an object for hill climbing TSP calculation.

                // Calculate and print TSP solutions using both algorithms for the current map.
                dynamicCalculation.calculate(map.getAdjacencyMatrix(), map.getVertexList());
                hillClimbCalculation.calculate(map.getAdjacencyMatrix(), map.getVertexList(), maxIterations, maxNoImprovementCutoff);
                scanner.close();
            }
        } catch (Exception e) {
            System.out.println("Invalid Input");
            e.printStackTrace(); // Print the exception details if there's an error.
        }
    }
}
