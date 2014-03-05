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

	public CarArrives(CarWashState state, EventQueue queue) {
		super(state, queue);
		super.name = "Arrive";
	}

	@Override
	public void execute() {
		if (state.time() >= state.stopTime()) {
			queue.add(new SimulationStops(state, queue));
			return;
		}

		state.carQueue.add(state.carFactory.makeCar());

		if (state.availableFastWashers != 0) {
			state.availableFastWashers = state.availableFastWashers - 1;
			Car car = state.carQueue.first();
			state.carQueue.removeFirst();
//			double timeToWash = state.fastURF.next();

		} else if (state.availableSlowWashers != 0) {
			state.availableSlowWashers = state.availableSlowWashers - 1;
			Car car = state.carQueue.first();
			state.carQueue.removeFirst();
		}

	}

}
