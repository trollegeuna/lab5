package lab5.simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventQueue {

	private List<Event> eventQueue = new ArrayList<Event>();

	public void add(Event event) {
		eventQueue.add(event);
		Collections.sort(eventQueue);
	}

	public int size() {
		return eventQueue.size();
	}

	public Event first() {
		return eventQueue.get(0);
	}

	public void removeFirst() {
		eventQueue.remove(0);
	}

	public void clear() {
		eventQueue.clear();
	}
	
	public boolean isEmpty() {
		return eventQueue.isEmpty();
	}
}
