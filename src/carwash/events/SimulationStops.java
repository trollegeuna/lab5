package carwash.events;

import simulator.Event;
import simulator.EventQueue;
import carwash.state.CarWashState;

/**
 * When main loop encounters this in the event queue the simulation is stopped.
 */
public class SimulationStops extends Event {

	CarWashState state;
	EventQueue eventQueue;

	public SimulationStops(double stopTime, CarWashState state,
			EventQueue eventQueue) {
		super.eventName = "Stop";
		super.startTime = stopTime;
		this.state = state;
		this.eventQueue = eventQueue;
	}

	@Override
	public void execute() {
		state.setCurrentEvent(this);
		state.setTime(startTime);
		state.updateIdleTime();
		state.updateQueueTime();
		state.setCurrentCar(null);

		state.setChanged();
		state.notifyObservers();

		eventQueue.clear();
	}

}
