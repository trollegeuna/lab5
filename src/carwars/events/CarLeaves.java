package carwars.events;

import carwash.state.CarWashState;
import lab5.simulator.Event;
import lab5.simulator.EventQueue;

/**
 * This will contain:
 * 
 * Add used washer to available washers.
 * 
 * If car queue is not empty, create a new car arrives-event.
 * 
 */

public class CarLeaves extends Event {

	public CarLeaves(CarWashState state, EventQueue queue) {
		super(state, queue);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}