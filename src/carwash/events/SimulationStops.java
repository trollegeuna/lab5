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
		super.name = "Stop";
		super.startTime = startTime;
		this.state = state;
		this.eventQueue = eventQueue;
	}

	@Override
	public void execute() {
		startTime = state.stopTime;
		state.setCurrentEvent(this);
		state.setTime(startTime);
		state.updateIdleTime();
		state.updateQueueTime();
		state.setTime(state.stopTime);
		state.setCurrentCar(null);

		state.setChanged();
		state.notifyObservers();

		eventQueue.clear();
	}

}
