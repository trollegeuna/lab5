package carwars.events;

import lab5.simulator.Event;
import lab5.simulator.EventQueue;
import carwash.state.CarWashState;

/**
 * When main loop encounters this in the event queue the simulation is stopped.
 */
public class SimulationStops extends Event{

	public SimulationStops(CarWashState state, EventQueue queue) {
		super(state, queue);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		super.eventQueue.clear();
		
	}

}
