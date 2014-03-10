package simulator;

/**
 * An abstract event.
 */
public abstract class Event implements Comparable<Event> {

	public double startTime;

	/**
	 * Compares this event with another. Used for ordering the event queue.
	 */
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

	/**
	 * Execute this event.
	 */
	public abstract void execute();

	/**
	 * Prints the name of the event.
	 */
	@Override
	public abstract String toString();
}
