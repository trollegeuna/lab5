package carwash.events;

import simulator.Event;
import simulator.EventQueue;
import carwash.state.Car;
import carwash.state.CarWashState;

/**
 * This will contain:
 * 
 * Check if current sim time has exceeded max sim time.
 * 
 * Create a new car, add it to car queue.
 * 
 * Check if washer is free, if so, simulate washing by adding leave event to
 * event queue after random time. Remove washer from available washers. remove
 * the first car from the queue.
 * 
 * 
 */
public class CarArrives extends Event {

	CarWashState state;
	EventQueue eventQueue;

	public CarArrives(double startTime, CarWashState state,
			EventQueue eventQueue) {
		super.eventName = "Arrive";
		super.startTime = startTime;
		this.state = state;
		this.eventQueue = eventQueue;
	}

	@Override
	public void execute() {

		state.setCurrentEvent(this);
		state.setTime(startTime);
		state.updateIdleTime();
		state.updateQueueTime();

		Car newCar = state.carFactory.makeCar();
		state.setCurrentCar(newCar);

		state.setChanged();
		state.notifyObservers();

		if (state.carQueue.size() == state.maxCarQueueSize) {
			state.totalRejected = state.totalRejected + 1;
		} else {
			state.carQueue.add(newCar);
		}

		Car carToWash = state.carQueue.first();

		if (state.washersAreAvailable()) {
			// Washer available, schedule time for leave event of first car in
			// queue.
			double timeFinished;
			boolean fastWasher = false;

			// Determine which type of washer is available
			if (state.availableFastWashers != 0) {
				fastWasher = true;
				state.availableFastWashers = state.availableFastWashers - 1;
				timeFinished = state.getFastWasherFinishTime();

			} else {
				state.availableSlowWashers = state.availableSlowWashers - 1;
				timeFinished = state.getSlowWasherFinishTime();
			}
			// Remove first car from queue since it can be washed now
			state.carQueue.removeFirst();

			// Schedule leave event at the time the wash is finished.
			CarLeaves leaveEvent = new CarLeaves(timeFinished, state,
					eventQueue, carToWash, fastWasher);
			leaveEvent.startTime = timeFinished;
			eventQueue.add(leaveEvent);

		} else if (!state.carQueue.isEmpty()) {
			state.totalCarsQueued = state.totalCarsQueued + 1;
		}

		CarArrives arrivalEvent = new CarArrives(state.getNextArrivalTime(),
				state, eventQueue);
		eventQueue.add(arrivalEvent);
	}

}
