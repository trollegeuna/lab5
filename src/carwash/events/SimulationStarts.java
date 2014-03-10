package carwash.events;

import simulator.Event;
import simulator.EventQueue;
import carwash.state.CarWashState;

/**
 * This is the first event added to the event queue. It will simply make the
 * first car arrive.
 */
public class SimulationStarts extends Event {

	CarWashState state;
	EventQueue eventQueue;

	public SimulationStarts(CarWashState state, EventQueue eventQueue) {
		this.state = state;
		this.eventQueue = eventQueue;
	}

	/**
	 * Create the first arrival and adds it to the event queue.
	 */
	@Override
	public void execute() {
		// Update the state data
		state.setCurrentEvent(this);
		state.setTime(startTime);

		// Display the new state
		state.setChanged();
		state.notifyObservers();

		// Schedule the first arrival
		Event firstArrival = new CarArrives(state.getNextArrivalTime(), state,
				eventQueue);
		eventQueue.add(firstArrival);
	}

	@Override
	public String toString() {
		return "Start";
	}

}
