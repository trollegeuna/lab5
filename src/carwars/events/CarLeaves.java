package carwars.events;

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
 * 
 */

public class CarLeaves extends Event {

	Car car;
	boolean fastWasher = false;

	public CarLeaves(CarWashState state, EventQueue queue, Car car,
			boolean fastWasher) {
		super(state, queue);
		name = "Leave";
		this.car = car;
		this.fastWasher = fastWasher;
	}

	@Override
	public void execute() {

		state.currentTime = startTime;
		// Make a report
		String reportLine = String.format(
				"%.2f\t%s\t%s\t%s\t%s\t%s\t\t%.2f\t\t%s\t\t%s",
				state.currentTime, state.availableFastWashers,
				state.availableSlowWashers, car.id, name, state.totalIdleTime,
				state.totalQueueTime, state.carQueue.size(),
				state.totalRejected);
		System.out.println(reportLine);

		if (fastWasher) {
			state.availableFastWashers = state.availableFastWashers + 1;
		} else {
			state.availableSlowWashers = state.availableSlowWashers + 1;
		}
		// should be in CarArrives...
		CarArrives arrivalEvent = new CarArrives(state, eventQueue);
		arrivalEvent.startTime = state.currentTime;
		eventQueue.add(arrivalEvent);

	}

}