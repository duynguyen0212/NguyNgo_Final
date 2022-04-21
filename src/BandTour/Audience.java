package BandTour;

import Skeleton.SimulationInput;
import Skeleton.Unit;

public class Audience extends Unit {
    public Manager m;

    public Audience(SimulationInput input, Manager manager) {
        super(input);
        this.m = manager;
    }

    @Override
    public void performAction() {
        for (int i = 0; i < this.m.getTicket(); i++) {

        }

    }

    @Override
    public void submitStatistics() {

    }
}
