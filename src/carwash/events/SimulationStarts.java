package carwash.events;

import carwash.state.CarWashState;
import lab5.simulator.Event;
import lab5.simulator.EventQueue;

/**
 * This is the first event added to the event queue. It will simply make the
 * first car arrive.
 */
public class SimulationStarts extends Event {

	CarWashState state;
	EventQueue eventQueue;

	public SimulationStarts(CarWashState state, EventQueue eventQueue) {
		super.name = "Start";
		this.state = state;
		this.eventQueue = eventQueue;
	}

	@Override
	public void execute() {
		state.setCurrentEvent(this);
		state.setTime(startTime);

		state.setChanged();
		state.notifyObservers();

		Event firstArrival = new CarArrives(state.getNextArrivalTime(), state,
				eventQueue);
		eventQueue.add(firstArrival);
	}

}