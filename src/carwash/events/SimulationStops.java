package carwash.events;

import lab5.simulator.Event;
import lab5.simulator.EventQueue;
import carwash.state.CarWashState;

/**
 * When main loop encounters this in the event queue the simulation is stopped.
 */
public class SimulationStops extends Event {

	CarWashState state;
	EventQueue eventQueue;

	public SimulationStops(double startTime, CarWashState state,
			EventQueue eventQueue) {
		this.state = state;
		this.eventQueue = eventQueue;
		super.name = "Stop";
		super.startTime = startTime;
	}

	@Override
	public void execute() {

		state.setCurrentEvent(this);
		startTime = state.stopTime;
		state.setTime(startTime);
		state.updateIdleTime();
		state.updateQueueTime();
		state.setTime(state.stopTime);

		String reportLine = String.format(
				"%.2f\t%s\t%s\t-\t%s\t%.2f\t\t%.2f\t\t%s\t\t%s",
				state.currentTime, state.availableFastWashers,
				state.availableSlowWashers, name, state.totalIdleTime,
				state.totalQueueTime, state.carQueue.size(),
				state.totalRejected);
		System.out.println(reportLine);

		state.setChanged();
		state.notifyObservers();

		eventQueue.clear();
	}

}
