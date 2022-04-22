import BandTour.Audience;
import BandTour.BandMember;
import BandTour.Manager;
import Skeleton.SimulationInput;

import java.lang.Thread;

/**
 * The class that is responsible for running the simulation.
 * <p>
 * You will need to modify the run method to initialize, and run all of your units.
 */
public class Matrix {
    public static void run(SimulationInput input) {

        Manager manager = new Manager("SPYAIR", input);
        BandMember ike = new BandMember("Ike", input, manager);
        BandMember kenta = new BandMember("Kenta", input, manager);
        BandMember uz = new BandMember("UZ", input, manager);
        BandMember momiken = new BandMember("Momiken", input, manager);
        Audience audience = new Audience(input, manager);

        Thread managerThread = new Thread(manager);
        Thread ikeThread = new Thread(ike);
        Thread kentaThread = new Thread(kenta);
        Thread uzThread = new Thread(uz);
        Thread momikenThread = new Thread(momiken);
        Thread audienceThread = new Thread(audience);

        managerThread.start();
        ikeThread.start();
        kentaThread.start();
        uzThread.start();
        momikenThread.start();
        audienceThread.start();

        try{
            managerThread.join();
            ikeThread.join();
            kentaThread.join();
            uzThread.join();
            momikenThread.join();
            audienceThread.join();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
