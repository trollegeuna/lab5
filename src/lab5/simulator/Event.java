package lab5.simulator;

import carwash.state.CarWashState;

public abstract class Event {
	public String name;
	public double startTime;

	protected CarWashState state;
	protected EventQueue queue;

	public Event(CarWashState state, EventQueue queue) {
		this.state = state;
		this.queue = queue;
	}

	public abstract void execute();
}
