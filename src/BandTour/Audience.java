package BandTour;

import Skeleton.SimulationInput;
import Skeleton.Unit;
import java.util.Random;

public class Audience extends Unit {
    public Manager m;
    private int numberOfAudience;

    public Audience(SimulationInput input, Manager manager) {
        super(input);
        this.m = manager;
    }

    public int getNumberOfAudience(){
        return this.numberOfAudience;
    }

    @Override
    public void performAction() {
        while (true) {
            this.m.acquireTicket(1);
            Random rnd = new Random();
            numberOfAudience = rnd.nextInt(0, this.m.availableTicket());
            this.m.acquireTicket(numberOfAudience);
            this.m.acquireTicket(this.m.availableTicket());
            System.out.println(numberOfAudience + 1 + " audiences are entering into the theatre...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("The Audience are ready to see the performance");
            this.m.releasePerformPermit(4);
        }
    }

    @Override
    public void submitStatistics() {

    }
}
