/* We will have 4 band member and they will register to the assistant for observer method
 * after release schedule permit for manager, band members will have to wait for all audience
 * to get in the theatre and perform. After the performance band member will release permit back to Manager
 * */
package BandTour;

import Skeleton.SimulationInput;
import Skeleton.Unit;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

public class BandMember extends Unit implements java.util.Observer {
    private String location;
    private String loc;
    private Manager m;
    private int counter = 0;


    /**
     * General constructor for the Skeleton.Unit.
     *
     * @param name  The name of the unit.
     * @param input The input settings.
     */
    public BandMember(String name, SimulationInput input, Manager manager) {
        super(name, input);
        this.m = manager;
        registerManager(m);
    }

    /**
     * Method for each band member to perform on stage
     */
    public void perform() {
        System.out.println(getMemberName() + " is performing on stage...");
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Getter for member's name
     *
     * @return member's name
     */
    public String getMemberName() {
        return this.getName();
    }

    /**
     * Override performAction method from abstract class Unit
     */
    @Override
    public void performAction() {
        while (counter < this.m.getNumberOfTour()) {
            this.m.releaseSchedule();
            this.m.acquirePerformPermit();
            perform();
            counter++;
            this.m.releaseSchedule();
        }
    }

    /**
     * Override submitStatistics method from abstract class Unit
     * the method will submit active unit
     */
    @Override
    public void submitStatistics() {
        this.getStats().getStatistic("ActiveUnits").addValue(1);
    }

    /**
     * Register band member to the manager
     *
     * @param m manager
     */

    public void registerManager(Manager m) {
        m.a.addObserver(this);
    }

    /**
     * Override toString method
     *
     * @return band member's name
     */
    @Override
    public String toString() {
        return getMemberName();
    }

    /**
     * This method is called whenever the observed object is changed. An
     * application calls an {@code Observable} object's
     * {@code notifyObservers} method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the {@code notifyObservers}
     */
    @Override
    public void update(Observable o, Object arg) {
        // check if object is instanceof string
        // if yes store in string loc and return
        if (arg instanceof String) {
            location = (String) arg;
            loc = location;
            return;
        }

        // Print out date and location that members are going to perform
        System.out.println("\nAssistant " + ((Manager.Assistant) o).getName() + " says our band will perform in " + loc + " on " + arg);
        System.out.println(getMemberName() + " says: got it");
        ((Manager.Assistant) o).addMemberPerform(this);
    }
}
