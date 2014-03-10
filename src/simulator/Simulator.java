package simulator;

/**
 * A simulator for events.
 */
public class Simulator {

	private EventQueue eventQueue;

	public Simulator(EventQueue eventQueue) {
		setEventQueue(eventQueue);
	}

	/**
	 * Runs the events in the event queue until the queue is empty.
	 */
	public void run() {
		// Keep executing events while there are still some events left.
		do {
			Event event = eventQueue.first();
			eventQueue.removeFirst();
			event.execute();

		} while (!eventQueue.isEmpty());
	}

	/**
	 * Set which event queue to use.
	 * 
	 * @param eventQueue
	 *            The event queue.
	 */
	public void setEventQueue(EventQueue eventQueue) {
		this.eventQueue = eventQueue;
	}

}
