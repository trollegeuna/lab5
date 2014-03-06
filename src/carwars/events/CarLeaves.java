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

		// Make a report
		String reportLine = String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s", state.currentTime,
				state.availableFastWashers, state.availableSlowWashers, car.id,
				name, state.totalIdleTime, state.totalQueueTime,
				state.carQueue.size(), state.totalRejected);

		if (fastWasher) {
			state.availableFastWashers = state.availableFastWashers + 1;
		} else {
			state.availableSlowWashers = state.availableSlowWashers + 1;
		}

		eventQueue.add(new CarArrives(state, eventQueue));

	}

}