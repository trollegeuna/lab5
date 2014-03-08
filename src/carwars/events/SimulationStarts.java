package carwars.events;

import carwash.state.CarWashState;
import lab5.simulator.Event;
import lab5.simulator.EventQueue;

/**
 * This is the first event added to the event queue. It will simply make the
 * first car arrive.
 */
public class SimulationStarts extends Event {

	public SimulationStarts(CarWashState state, EventQueue queue) {
		super(state, queue);
		super.name = "Start";
	}

	@Override
	public void execute() {
		System.out
				.println("Time\tFast\tSlow\tId\tEvent\tIdleTime\tQueueTime\tQueueSize\tRejected");
		String reportLine = String.format(
				"%.2f\t%s\t%s\t-\t%s\t%s\t\t%.2f\t\t%s\t\t%s",
				state.currentTime, state.availableFastWashers,
				state.availableSlowWashers, name, state.totalIdleTime,
				state.totalQueueTime, state.carQueue.size(),
				state.totalRejected);
		System.out.println(reportLine);
		Event firstArrival = new CarArrives(state, eventQueue);
		firstArrival.startTime = 0;
		eventQueue.add(firstArrival);
	}

}
