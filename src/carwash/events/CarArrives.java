package carwash.events;

import carwash.state.Car;
import carwash.state.CarWashState;
import lab5.simulator.Event;
import lab5.simulator.EventQueue;

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
		this.state = state;
		this.eventQueue = eventQueue;
		super.name = "Arrive";
		super.startTime = startTime;
	}

	@Override
	public void execute() {

		if (startTime >= state.stopTime) {
			new SimulationStops(state.currentTime, state, eventQueue).execute();
			return;
		}
		state.setCurrentEvent(this);
		state.setTime(startTime);
		state.updateIdleTime();

		Car newCar = state.carFactory.makeCar();

		Car carToWash;
		if (state.carQueue.isEmpty()) {
			carToWash = newCar;
		} else {
			carToWash = state.carQueue.first();
		}

		state.updateQueueTime();
		state.setCurrentCar(newCar);

		state.setChanged();
		state.notifyObservers();

		if (state.carQueue.size() == state.maxCarQueueSize) {
			state.totalRejected = state.totalRejected + 1;
		} else {
			state.carQueue.add(newCar);
		}

		if (state.washersAreAvailable()) {
			// Washer available, schedule time for leave event of first car in
			// queue.
			double timeFinished;
			boolean fastWasher = false;
			if (state.availableFastWashers != 0) {
				// Fast washer available, schedule time for leave event of first
				// car in queue.
				fastWasher = true;
				state.availableFastWashers = state.availableFastWashers - 1;
				timeFinished = state.getFastWasherFinishTime();

			} else {
				state.availableSlowWashers = state.availableSlowWashers - 1;
				timeFinished = state.getSlowWasherFinishTime();
			}
			// Remove first car from queue since it can be washed now
			state.carQueue.removeFirst();
			CarLeaves leaveEvent = new CarLeaves(timeFinished, state,
					eventQueue, carToWash, fastWasher);
			leaveEvent.startTime = timeFinished;
			eventQueue.add(leaveEvent);

		} else if (state.carQueue.size() != 0) {
			state.totalCarsQueued = state.totalCarsQueued + 1;
		}

		CarArrives arrivalEvent = new CarArrives(state.getNextArrivalTime(),
				state, eventQueue);
		eventQueue.add(arrivalEvent);

	}

}
