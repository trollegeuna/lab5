package carwars.events;

import lab5.simulator.Event;
import lab5.simulator.EventQueue;
import carwash.state.CarWashState;

/**
 * When main loop encounters this in the event queue the simulation is stopped.
 */
public class SimulationStops extends Event {

	public SimulationStops(CarWashState state, EventQueue queue) {
		super(state, queue);
		this.name = "Stop";
	}

	@Override
	public void execute() {
		String reportLine = String.format(
				"%.2f\t%s\t%s\t-\t%s\t%.2f\t\t%.2f\t\t%s\t\t%s",
				state.currentTime, state.availableFastWashers,
				state.availableSlowWashers, name, state.totalIdleTime,
				state.totalQueueTime, state.carQueue.size(),
				state.totalRejected);
		System.out.println(reportLine);
		eventQueue.clear();

	}

}
