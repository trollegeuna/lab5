package lab5.simulator;

public class Simulator {

	EventQueue eventQueue;

	public Simulator(EventQueue eventQueue) {
		setEventQueue(eventQueue);
	}

	public void run() {
		// Keep executing events while there are still some events left.
		do {
			Event event = eventQueue.first();
			eventQueue.removeFirst();
			event.execute();

		} while (!eventQueue.isEmpty());
	}
	
	public void setEventQueue(EventQueue eventQueue) {
		this.eventQueue = eventQueue;
	}

}
