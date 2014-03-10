package carwash.events;

import simulator.Event;
import simulator.EventQueue;
import carwash.state.CarWashState;

/**
 * Makes the simulation stop. It will clear the event queue.
 */
public class SimulationStops extends Event {

	CarWashState state;
	EventQueue eventQueue;

	public SimulationStops(double stopTime, CarWashState state,
			EventQueue eventQueue) {
		super.startTime = stopTime;
		this.state = state;
		this.eventQueue = eventQueue;
	}

	/**
	 * Clears the event queue after updating and displaying the state data.
	 */
	@Override
	public void execute() {
		state.setCurrentEvent(this);
		state.setTime(startTime);
		state.updateIdleTime();
		state.updateQueueTime();

		state.setChanged();
		state.notifyObservers();

		eventQueue.clear();
	}

	@Override
	public String toString() {
		return "Stop";
	}

}
