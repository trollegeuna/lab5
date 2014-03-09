package simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventQueue {

	private List<Event> eventQueue = new ArrayList<Event>();

	public void add(Event event) {
		eventQueue.add(event);
		Collections.sort(eventQueue);
	}

	public void clear() {
		eventQueue.clear();
	}

	public Event first() {
		return eventQueue.get(0);
	}

	public boolean isEmpty() {
		return eventQueue.isEmpty();
	}

	public void removeFirst() {
		eventQueue.remove(0);
	}

	public int size() {
		return eventQueue.size();
	}
}
