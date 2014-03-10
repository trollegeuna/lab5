package carwash.events;

import simulator.Event;
import simulator.EventQueue;
import carwash.state.Car;
import carwash.state.CarWashState;

/**
 * Simulates a new car arriving at the car wash.
 */
public class CarArrives extends Event {

	CarWashState state;
	EventQueue eventQueue;

	public CarArrives(double startTime, CarWashState state,
			EventQueue eventQueue) {
		super.startTime = startTime;
		this.state = state;
		this.eventQueue = eventQueue;
	}

	/**
	 * Updates the car wash state, creates a new car and schedules a washing if
	 * possible. Finally a new arrival event is scheduled.
	 */
	@Override
	public void execute() {
		// Update the state data
		state.setCurrentEvent(this);
		state.setTime(startTime);
		state.updateIdleTime();
		state.updateQueueTime();

		// A new car arrives
		Car arrivedCar = state.makeCar();
		state.setCurrentCar(arrivedCar);

		// Display the new state
		state.setChanged();
		state.notifyObservers();

		// Check if we can queue the new car or have to reject it
		if (state.carQueueIsFull()) {
			state.totalRejected++;
		} else {
			state.carQueue.add(arrivedCar);
			state.totalCars++;
		}

		// Check if there are washers available
		if (state.hasAvailableWashers()) {
			// Determine which type of washer is available
			boolean fastWasher = false;
			if (state.availableFastWashers != 0) {
				fastWasher = true;
				state.availableFastWashers--;
			} else {
				state.availableSlowWashers--;
			}

			double timeFinished = (fastWasher) ? state
					.getFastWasherFinishTime() : state
					.getSlowWasherFinishTime();

			// Remove first car from queue since it can be washed now
			Car carToWash = state.carQueue.first();
			state.carQueue.removeFirst();

			// Schedule leave event at the time the wash is finished.
			CarLeaves leaveEvent = new CarLeaves(timeFinished, state,
					eventQueue, carToWash, fastWasher);
			eventQueue.add(leaveEvent);

		}

		// Schedule a new arrival
		CarArrives arrivalEvent = new CarArrives(state.getNextArrivalTime(),
				state, eventQueue);
		eventQueue.add(arrivalEvent);
	}

	@Override
	public String toString() {
		return "Arrive";
	}

}
