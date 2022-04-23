/**
 * Name: Ngoc Duy Nguyen
 * Date: 4/25/2022
 * Description: Final project - Music Band World Tour
 */

import BandTour.Manager;
import Skeleton.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The main class is responsible for the testing. It has a helper method
 * that makes it easier to run many tests.
 **/
public class Main {
    /**
     * Runs a test with the given input and returns the statistics
     * produced from the test run. Simplifies the testing process.
     *
     * @param input The input to run the test with.
     * @return The statistics of the test run.
     **/
    public static StatisticsContainer runTest(SimulationInput input) {
        // Initialize the stats singleton here so the input can
        // be ignored in future calls
        StatisticsContainer stats = StatisticsContainer.getInstance(input);
        Matrix.run(input);

        return stats;
    }

    /**
     * See method above for details.
     **/
    public static StatisticsContainer runTest(ArrayList<ArrayList<String>> input) {
        return runTest(new SimulationInput(input));
    }

    /**
     * Main body to run the band tour simulation
     * */
    public static void main(String[] args) {
		/*
		You can either prepare your input as an array, or add it directly to your
		Skeleton.SimulationInput object (see below).*/

        ArrayList<ArrayList<String>> input = new ArrayList<ArrayList<String>>();
        input.add(
                // The time to run in seconds
                new ArrayList<String>(Arrays.asList("Time", "600"))
        );
        input.add(
                // The number of actions units must perform per second
                new ArrayList<String>(Arrays.asList("ActionsPerSecond", "60"))
        );


        SimulationInput si = new SimulationInput();
        si.addInput("Time", List.of("10")); // In seconds
        si.addInput("ActionsPerSecond", List.of("1"));

        System.out.println("------SPYAIR WORLD TOUR------");
        // Run the simulation
        StatisticsContainer stats = runTest(si);
        System.out.println("\n------END OF TOUR. THANK YOU------");
        System.out.println("\nStatistic for the band tour:");
        // Post the finalized statistics
        stats.printStatisticsContainer();

		/* testing
         Manager manager = new Manager("test", new SimulationInput());
         manager.observerTesting(manager);*/


    }
}
