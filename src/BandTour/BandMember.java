package BandTour;

import Skeleton.SimulationInput;
import Skeleton.Unit;

import java.util.ArrayList;
import java.util.Observable;

public class BandMember extends Unit implements java.util.Observer {
    private String location;
    private String loc;
    public Manager m;

    /**
     * General constructor for the Skeleton.Unit.
     *
     * @param name  The name of the unit.
     * @param input The input settings.
     */
    public BandMember(String name, SimulationInput input, Manager manager) {
        super(name, input);
        this.m = manager;
    }

    public void playSong() {

    }

    public String getMemberName() {
        return this.getName();
    }

    @Override
    public void performAction() {
        registerManager(m);
        this.m.schedule.release();

    }

    @Override
    public void submitStatistics() {

    }

    public void registerManager(Manager m) {
        m.a.addObserver(this);
    }

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

        if (arg instanceof String) {
            location = (String) arg;
            loc = location;
            //System.out.println(getMemberName() + " will perform in " + location);
            return;
        }
        System.out.println("\nAssistant " + ((Manager.Assistant) o).getName() + " says our band will perform in " + loc + " on " + arg);
        System.out.println(getMemberName() + " says: got it");
        ((Manager.Assistant) o).addMemberPerform(this);
    }
}
