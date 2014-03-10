package carwash.events;

import simulator.Event;
import simulator.EventQueue;
import carwash.state.Car;
import carwash.state.CarWashState;

/**
 * Simulates finishing the washing of a car at the car wash.
 */
public class CarLeaves extends Event {

	CarWashState state;
	EventQueue eventQueue;
	Car car;
	boolean fastWasher = false;

	public CarLeaves(double startTime, CarWashState state,
			EventQueue eventQueue, Car car, boolean fastWasher) {
		super.eventName = "Leave";
		super.startTime = startTime;
		this.state = state;
		this.eventQueue = eventQueue;
		this.car = car;
		this.fastWasher = fastWasher;
	}

	/**
	 * Checks if there are any cars waiting to be washed after the washing of
	 * another is finished. If so, a new leave-event is scheduled, otherwise the
	 * washer used is released.
	 */
	@Override
	public void execute() {
		// Update the state data
		state.setCurrentEvent(this);
		state.setTime(startTime);
		state.updateIdleTime();
		state.updateQueueTime();
		state.setCurrentCar(car);

		// Display the new state
		state.setChanged();
		state.notifyObservers();

		// Schedule a new leave event immediately if there are cars waiting.
		// Otherwise, increase available washers.
		if (state.carQueue.isEmpty()) {
			if (fastWasher) {
				state.availableFastWashers = state.availableFastWashers + 1;
			} else {
				state.availableSlowWashers = state.availableSlowWashers + 1;
			}

		} else {
			double timeFinished = (fastWasher) ? state
					.getFastWasherFinishTime() : state
					.getSlowWasherFinishTime();

			Car carToWash = state.carQueue.first();
			state.carQueue.removeFirst();

			CarLeaves leaveEvent = new CarLeaves(timeFinished, state,
					eventQueue, carToWash, fastWasher);
			eventQueue.add(leaveEvent);
		}

	}

}