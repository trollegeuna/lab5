package carwash.events;

import carwash.state.Car;
import carwash.state.CarWashState;
import lab5.simulator.Event;
import lab5.simulator.EventQueue;

/**
 * This will contain:
 * 
 * Add used washer to available washers.
 * 
 * If car queue is not empty, create a new car arrives-event.
 */
public class CarLeaves extends Event {

	CarWashState state;
	EventQueue eventQueue;
	Car car;
	boolean fastWasher = false;

	public CarLeaves(double startTime, CarWashState state,
			EventQueue eventQueue, Car car, boolean fastWasher) {
		super.name = "Leave";
		super.startTime = startTime;
		this.state = state;
		this.eventQueue = eventQueue;
		this.car = car;
		this.fastWasher = fastWasher;
	}

	@Override
	public void execute() {
		state.setCurrentEvent(this);
		state.setTime(startTime);
		state.updateIdleTime();
		state.updateQueueTime();
		state.setCurrentCar(car);

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
			double timeFinished;

			if (fastWasher) {
				timeFinished = state.getFastWasherFinishTime();
			} else {
				timeFinished = state.getSlowWasherFinishTime();
			}

			Car car = state.carQueue.first();
			state.carQueue.removeFirst();

			CarLeaves leaveEvent = new CarLeaves(timeFinished, state,
					eventQueue, car, fastWasher);
			eventQueue.add(leaveEvent);
		}

	}

}