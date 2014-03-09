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
 * 
 */

public class CarLeaves extends Event {

	CarWashState state;
	EventQueue eventQueue;
	Car car;
	boolean fastWasher = false;

	public CarLeaves(double startTime, CarWashState state,
			EventQueue eventQueue, Car car, boolean fastWasher) {
		this.state = state;
		this.eventQueue = eventQueue;
		super.name = "Leave";
		super.startTime = startTime;
		this.car = car;
		this.fastWasher = fastWasher;
	}

	@Override
	public void execute() {
		state.setCurrentEvent(this);
		state.setTime(startTime);
		state.updateIdleTime();
		state.updateQueueTime();

		// Make a report
		String reportLine = String.format(
				"%.2f\t%s\t%s\t%s\t%s\t%.2f\t\t%.2f\t\t%s\t\t%s",
				state.currentTime, state.availableFastWashers,
				state.availableSlowWashers, car.id, name, state.totalIdleTime,
				state.totalQueueTime, state.carQueue.size(),
				state.totalRejected);
		System.out.println(reportLine);
		
		state.setChanged();
		state.notifyObservers();

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