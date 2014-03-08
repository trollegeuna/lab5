package lab5.simulator;

import carwash.state.CarWashState;

public abstract class Event implements Comparable<Event>{
	public String name;
	public double startTime;

	protected CarWashState state;
	protected EventQueue eventQueue;

	public Event(CarWashState state, EventQueue queue) {
		this.state = state;
		this.eventQueue = queue;
	}

	public abstract void execute();
	
	@Override
	public int compareTo(Event otherEvent) {
		if (this.startTime == otherEvent.startTime) {
			return 0;
		} else if (this.startTime < otherEvent.startTime) {
			return -1;
		} else {
			return 1;
		}
	}
}
