/* Manager class will schedule the tour for the band
 *  Manager will have an assistant to take care of notify band's member
 *  After all members are ready, manager will start to schedule the next tour
 *  then release the ticket for the audience and wait for the band to finish performing
 *   and announce end of current tour and repeat */
package BandTour;

import Skeleton.SimulationInput;
import Skeleton.Unit;
import Skeleton.WorkerStatistic;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Observer;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Manager extends Unit {

    private Date performDate = null;
    private String[] location = {"Canada", "Japan", "U.S.A", "Korea", "Australia", "United Kingdom", "Spain", "Mexico", "Turkey"};
    private int locIndex = 0; // index of location array of string
    private int numberOfTour;
    private int counter = 0;
    private int totalTicket = 0;
    private Random rnd = new Random();
    private ArrayList<String> vCountries = new ArrayList<>();

    private Semaphore ticket;
    private Semaphore schedule;
    private Semaphore perform;

    Assistant a = new Assistant("Duy");

    /**
     * Manager's constructor. Semaphore ticket, schedule, perform will be initialized
     * set number of tour and add statistic to Statistics class
     *
     * @param name  manager's name
     * @param input simulation input
     */
    public Manager(String name, SimulationInput input) {
        super(name, input);
        ticket = new Semaphore(0);
        schedule = new Semaphore(0);
        perform = new Semaphore(0);
        setNumberOfTour();
        this.getStats().addStatistic("Ticket sold", new WorkerStatistic("Ticket sold"));
        this.getStats().addStatistic("Number of tour", new WorkerStatistic("Number of tour"));
        this.getStats().addStatistic("Countries visited", new WorkerStatistic("Countries visited"));
    }

    /**
     * Override performAction method from abstract class Unit
     * <p>
     * The manager will randomly choose a location and date for the band to perform
     * After arrive to the location, manager will release ticket to audience
     * After the performance is done band member release schedule-semaphore
     * so manager can schedule next tour
     */
    @Override
    public void performAction() {
        while (counter < getNumberOfTour()) {
            try {
                schedule.acquire(4);

            } catch (Exception e) {
                System.out.println(e);
            }

            System.out.println("\nManager is scheduling next tour...");
            counter++;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Set location and perform date
            Manager.this.setLocation();
            Manager.this.setPerformDate(new Date());

            // Wait for the band to arrive to destination
            System.out.println("\nThe band is arriving to tour destination...");
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println(e);
            }

            // Decide random number of tickets and release permit to audience to get in the theatre
            System.out.println("Manager is checking ticket...");
            try {
                Thread.sleep(rnd.nextInt(2000, 10000));
            } catch (Exception e) {
                System.out.println(e);
            }

            Random numberOfTickets = new Random();
            int tickets = numberOfTickets.nextInt(500);

            releaseTicket(tickets); // Release semaphore tickets
            System.out.println("Number of tickets: " + tickets);

            // Add number of tickets to total tickets sold
            totalTicket = totalTicket + tickets;

            // Acquire permit again from the band to end the current tour
            try {
                schedule.acquire(4);

            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println("End of tour number: " + counter);

            // Print out country that are visited
            System.out.print("Country visited: | ");
            printVisitedCountry();
        }
    }

    /**
     * Override submitStatistics method from abstract class Unit
     * this method will submit number of ticket sold, number of tour,
     * number of country visited, active units
     */
    @Override
    public void submitStatistics() {
        this.getStats().getStatistic("Ticket sold").addValue(totalTicket);
        this.getStats().getStatistic("Number of tour").addValue(getNumberOfTour());
        this.getStats().getStatistic("Countries visited").addValue(vCountries.size());
        this.getStats().getStatistic("ActiveUnits").addValue(1);
    }

    /**
     * Set midterm date and notify to the observers (students)
     *
     * @param date date to perform
     */
    public void setPerformDate(Date date) {
        a.setPerformDateAssist(date);
    }

    /**
     * Set random location from the location list and notify to the observers (band members)
     */
    public void setLocation() {
        Random rnd = new Random();
        int i = rnd.nextInt(0, 5);
        locIndex = i;
        a.setLocationAssist(locIndex);

    }

    /**
     * Set a random number of tour from 5 to 10 tour
     */
    public void setNumberOfTour() {
        Random rnd = new Random();
        numberOfTour = rnd.nextInt(5, 10);
    }

    /**
     * Getter for number of tour
     *
     * @return number of tour
     */
    public int getNumberOfTour() {
        return this.numberOfTour;
    }

    /**
     * Release semaphore Schedule
     */
    public void releaseSchedule() {
        this.schedule.release();
    }

    /**
     * Acquire semaphore Schedule
     */
    public void acquireSchedule() {
        try {
            this.schedule.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Release semaphore Perform
     */
    public void releasePerformPermit(int p) {
        this.perform.release(p);
    }

    /**
     * Acquire semaphore Perform
     */
    public void acquirePerformPermit() {
        try {
            this.perform.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Release semaphore Ticket
     */
    public void releaseTicket(int t) {
        this.ticket.release(t);
    }

    /**
     * Acquire semaphore Ticket
     */
    public void acquireTicket(int t) {
        try {
            this.ticket.acquire(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return available ticket
     *
     * @return number of tickets
     */
    public int availableTicket() {
        return this.ticket.availablePermits();
    }

    /**
     * Check if the country is already visited or not
     * if not add to the list of visited country
     *
     * @param name country's name
     */
    public void checkVisitedCountry(String name) {
        boolean flag = true;
        for (int i = 0; i < vCountries.size(); i++) {
            if (name == vCountries.get(i)) {
                flag = false;
                break;
            }
        }
        if (flag) {
            vCountries.add(name);
        }
    }

    /**
     * Method to print visited country
     */
    public void printVisitedCountry() {
        for (int i = 0; i < vCountries.size(); i++) {
            System.out.print(vCountries.get(i) + " | ");
        }
    }

    /* Helper class Assistant
     * the assistant will take care of notify band member about location and date*/
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
         * Get assistant's name
         *
         * @return name
         */
        public String getName() {
            return name;
        }

        /**
         * Set performance date and notify to the observers (band members)
         *
         * @param date
         */
        public void setPerformDateAssist(Date date) {
            setChanged();
            notifyObservers(date);
        }

        /**
         * Set random location from the list and notify to the observers (band members)
         * this method will also call checkVisitedCountry method
         */
        public void setLocationAssist(int i) {
            setChanged();
            notifyObservers(location[i]);
            checkVisitedCountry(location[i]);
        }

        /**
         * Getter for location
         *
         * @return country's name
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
