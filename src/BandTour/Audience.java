/* Audience class, the audience will acquire number of available tickets and start entering the theater
 * after that the audience will release the permit for the band so they can get on stage and perform*/
package BandTour;

import Skeleton.FloatWorkerStatistic;
import Skeleton.SimulationInput;
import Skeleton.Unit;
import Skeleton.WorkerStatistic;

import java.util.Random;
import java.util.Timer;

public class Audience extends Unit {
    private Manager m;
    private int numberOfAudience;
    private int counter = 0;
    private float waitTime = 0;
    private Random rnd = new Random();

    /**
     * Audience's constructor
     *
     * @param name   audience name
     * @param input   simulation input
     * @param manager manager will take care of the audience
     */
    public Audience(String name, SimulationInput input, Manager manager) {
        super(name, input);
        this.m = manager;
        this.getStats().addStatistic("Average wait time", new FloatWorkerStatistic("Average wait time"));
    }

    /**
     * Getter for number of audience
     *
     * @return numberOfAudience
     */
    public int getNumberOfAudience() {
        return this.numberOfAudience;
    }

    /**
     * Override performAction method from abstract class Unit
     */
    @Override
    public void performAction() {
        while (counter < this.m.getNumberOfTour()) {
            // A timer to count how long the audience has to wait
            float startTime = System.nanoTime();
            // Acquire first ticket to trigger audience thread
            this.m.acquireTicket(1);
            numberOfAudience = this.m.availableTicket();
            // Acquire the rest of the ticket
            this.m.acquireTicket(numberOfAudience);

            int temp = numberOfAudience + 1;
            System.out.println(temp + " audiences are entering into the theatre...");
            try {
                Thread.sleep(rnd.nextInt(2000, 7000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            float endTime = System.nanoTime();
            waitTime = waitTime + (endTime - startTime);
            System.out.println("The Audience are ready to see the performance");
            // All audience are in the theatre so they release permit for the band
            this.m.releasePerformPermit(4);
            counter++;
        }
    }

    /**
     * Override submitStatistics method from abstract class Unit
     * the method will submit active unit
     */
    @Override
    public void submitStatistics() {
        this.getStats().getStatistic("ActiveUnits").addValue(1);
        this.getStats().getStatistic("Average wait time").addValue(waitTime/this.m.getNumberOfTour());
    }
}
