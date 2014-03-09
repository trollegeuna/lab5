package carwars.events;

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

	public CarArrives(CarWashState state, EventQueue eventQueue) {
		super(state, eventQueue);
		super.name = "Arrive";
	}

	@Override
	public void execute() {

		boolean washersAreAvailable = (state.availableFastWashers != 0 || state.availableSlowWashers != 0);
		int amountAvailableWashers = state.availableFastWashers
				+ state.availableSlowWashers;

		if (startTime >= state.stopTime) {
			state.currentTime = state.stopTime;
			new SimulationStops(state, eventQueue).execute();
			return;
		}

		if (washersAreAvailable) {
			state.totalIdleTime = state.totalIdleTime + amountAvailableWashers
					* (startTime - state.currentTime);
		}
		state.currentTime = startTime;

		Car newCar = state.carFactory.makeCar();
		Car firstCar;
		if (state.carQueue.isEmpty()) {
			firstCar = newCar;
		} else {
			firstCar = state.carQueue.first();
		}
		
		state.totalQueueTime = state.totalQueueTime
				+ (state.currentTime - firstCar.arrivalTime);
		
		// Make a report
		String reportLine = String.format(
				"%.2f\t%s\t%s\t%s\t%s\t%.2f\t\t%.2f\t\t%s\t\t%s",
				state.currentTime, state.availableFastWashers,
				state.availableSlowWashers, newCar.id, name,
				state.totalIdleTime, state.totalQueueTime,
				state.carQueue.size(), state.totalRejected);
		System.out.println(reportLine);

		if (state.carQueue.size() == state.maxCarQueueSize) {
			state.totalRejected = state.totalRejected + 1;
		} else {
			state.carQueue.add(newCar);
		}

		if (washersAreAvailable) {
			// Washer available, schedule time for leave event of first car in
			// queue.
			double timeFinished;
			boolean fastWasher = false;
			if (state.availableFastWashers != 0) {
				// Fast washer available, schedule time for leave event of first
				// car in queue.
				fastWasher = true;
				state.availableFastWashers = state.availableFastWashers - 1;
				double timeToWash = state.fastURS.next();
				timeFinished = state.currentTime + timeToWash;
			} else {
				state.availableSlowWashers = state.availableSlowWashers - 1;
				double timeToWash = state.slowURS.next();
				timeFinished = state.currentTime + timeToWash;
			}
			// Remove first car from queue since it can be washed now
			state.carQueue.removeFirst();
			CarLeaves leaveEvent = new CarLeaves(state, eventQueue, firstCar,
					fastWasher);
			leaveEvent.startTime = timeFinished;
			eventQueue.add(leaveEvent);

		} else {
			// No washers available, update queue time.
			// state.totalQueueTime = state.totalQueueTime
			// + (state.currentTime - state.carQueue.first().arrivalTime);
		}

		CarArrives arrivalEvent = new CarArrives(state, eventQueue);
		arrivalEvent.startTime = state.currentTime + state.eRS.next();
		eventQueue.add(arrivalEvent);

	}

}
