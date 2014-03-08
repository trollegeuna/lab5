package carwars.events;

import carwash.state.CarWashState;
import lab5.simulator.Event;
import lab5.simulator.EventQueue;

/**
 * This is the first event added to the event queue. It will simply make the first car arrive.
 */
public class SimulationStarts extends Event {

	public SimulationStarts(CarWashState state, EventQueue queue) {
		super(state, queue);
		super.name = "Start";
	}

	@Override
	public void execute() {
		System.out.println("Time\tFast\tSlow\tId\tEvent\tIdleTime\tQueueTime\tQueueSize\tRejected");
		Event firstArrival = new CarArrives(state, eventQueue);
		firstArrival.startTime = 0;
		eventQueue.add(firstArrival);
	}

}
