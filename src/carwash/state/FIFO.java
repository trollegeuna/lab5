package carwash.state;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class FIFO {
	private ArrayList<Car> waitingQueue = new ArrayList<Car>();

	public void add(Car car) {
		waitingQueue.add(car);
	}

	public boolean isEmpty() {
		if (waitingQueue.size() == 0) {
			return true;
		}
		return false;
	}

	public void removeFirst() throws NoSuchElementException {
		if (waitingQueue.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			waitingQueue.remove(0);
		}
	}

	public Car first() throws NoSuchElementException {
		if (waitingQueue.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return waitingQueue.get(0);
		}
	}

	public int size() {
		return waitingQueue.size();
	}
}