package simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Keeps track of the events in the simulation. Sorts added events by their
 * start time.
 */
public class EventQueue {

	private List<Event> eventQueue = new ArrayList<Event>();

	/**
	 * Adds an event to the queue, then sorts the queue.
	 * 
	 * @param event
	 *            The event to be added.
	 */
	public void add(Event event) {
		eventQueue.add(event);
		Collections.sort(eventQueue);
	}

	/**
	 * Remove all events from the queue.
	 */
	public void clear() {
		eventQueue.clear();
	}

	/**
	 * Get the first event in queue.
	 * 
	 * @return The first event.
	 * @throws NoSuchElementException
	 *             If the queue is empty.
	 */
	public Event first() throws NoSuchElementException {
		if (eventQueue.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return eventQueue.get(0);
		}
	}

	/**
	 * Check if the queue is empty of events.
	 * 
	 * @return True if queue is empty.
	 */
	public boolean isEmpty() {
		return eventQueue.isEmpty();
	}

	/**
	 * Removes the first element in the queue.
	 * 
	 * @throws NoSuchElementException
	 *             If the queue is empty.
	 */
	public void removeFirst() throws NoSuchElementException {
		if (eventQueue.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			eventQueue.remove(0);
		}
	}

	/**
	 * The size of the queue.
	 * 
	 * @return The size of the queue.
	 */
	public int size() {
		return eventQueue.size();
	}
}
