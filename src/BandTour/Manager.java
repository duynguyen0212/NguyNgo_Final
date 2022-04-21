package BandTour;

import Skeleton.SimulationInput;
import Skeleton.Unit;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Observer;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Manager extends Unit {

    private Date performDate = null;
    private String[] location = {"Canada", "Japan", "U.S.", "Korea", "Australia", "United Kingdom"};
    public int locIndex = 0;

    private Semaphore ticket;
    Semaphore schedule;

    Assistant a = new Assistant("Duy");

    public class Assistant extends java.util.Observable {
        private String name;

        ArrayList<Observer> memberList = new ArrayList<java.util.Observer>();

        /**
         * Assistant's constructor
         *
         * @param aName
         */
        public Assistant(String aName) {
            super();
            name = aName;
        }

        /**
         * get perform date
         *
         * @return performDate
         */
        public Date getPerformDate() {
            return performDate;
        }

        /**
         * get assistant's name
         *
         * @return name
         */
        public String getName() {
            return name;
        }

        /**
         * set midterm date and notify to the observers (students)
         *
         * @param date
         */
        public void setPerformDateAssist(Date date) {
            performDate = date;
            setChanged();
            notifyObservers(date);
        }

        /**
         * set random location from the list and notify to the observers (band members)
         */
        public void setLocationAssist(int i) {
            setChanged();
            notifyObservers(location[i]);
        }

        /**
         * getter for location
         *
         * @return room number
         */
        public String getLocation() {
            return location[locIndex];
        }

        /**
         * add member who is going to perform
         *
         * @param observer
         */
        public void addMemberPerform(java.util.Observer observer) {
            memberList.add(observer);
        }

        /**
         * print out all member who will perform
         */
        public void memberGoingToPerform() {
            Iterator<Observer> iterator = memberList.iterator();
            System.out.print("Members list: ");
            while (iterator.hasNext()) {
                System.out.print(iterator.next() + " ");
            }
        }
    }

    public void releaseTicket() {
        this.ticket.release();
    }

    public int getTicket() {
        return this.ticket.availablePermits();
    }

    /**
     * set midterm date and notify to the observers (students)
     *
     * @param date
     */
    public void setPerformDate(Date date) {
        performDate = date;
        a.setPerformDateAssist(date);
    }

    /**
     * set random location from the list and notify to the observers (band members)
     */
    public void setLocation() {
        Random rnd = new Random();
        int i = rnd.nextInt(0, 5);
        locIndex = i;
        a.setLocationAssist(locIndex);
    }

    public Manager(String name, SimulationInput input) {
        super(name, input);
        ticket = new Semaphore(0);
        schedule = new Semaphore(0);

    }

    /**
     * The manager will randomly choose a location and date for the band to perform
     * After arrive to the location, manager will release ticket to audience
     * After the performance is done band member release schedule-semaphore
     * so manager can schedule next tour
     */
    @Override
    public void performAction() {
        System.out.println("\nManager is scheduling next tour...");
        try {
            schedule.acquire(4);
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println(e);
        }

        Manager.this.setLocation();
        Manager.this.setPerformDate(new Date());
        System.out.println("The band is arriving to tour destination...");
        try {

            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("Manager is checking ticket...");
        this.ticket.release(10);


    }

    @Override
    public void submitStatistics() {

    }

    /**
     * method to test observer
     *
     * @param m manager
     */
    public void observerTesting(Manager m) {

        BandMember ike = new BandMember("Ike", new SimulationInput(), m);
        ike.registerManager(m);
        m.setPerformDate(new Date());
        m.setLocation();

        a.memberGoingToPerform();
    }
}
