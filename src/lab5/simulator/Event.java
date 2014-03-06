package lab5.simulator;

import carwash.state.CarWashState;

public abstract class Event {
	public String name;
	public double startTime;

	protected CarWashState state;
	protected EventQueue eventQueue;

	public Event(CarWashState state, EventQueue queue) {
		this.state = state;
		this.eventQueue = queue;
	}

	public abstract void execute();
}
