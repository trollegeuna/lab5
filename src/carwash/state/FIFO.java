package carwash.state;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * A FIFO queue for cars.
 */
public class FIFO {
	private ArrayList<Car> queue = new ArrayList<Car>();

	/**
	 * Adds the car to the end of the queue.
	 * 
	 * @param car
	 *            The car to add to queue.
	 */
	public void add(Car car) {
		queue.add(car);
	}

	/**
	 * Returns the first car in the queue. If the queue is empty, a
	 * NoSuchElementException is thrown.
	 * 
	 * @return The first car in queue.
	 * @throws NoSuchElementException
	 *             If the queue is empty.
	 */
	public Car first() throws NoSuchElementException {
		if (queue.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return queue.get(0);
		}
	}

	/**
	 * This method returns true if the queue is empty of cars.
	 * 
	 * @return True if the queue is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return queue.isEmpty();
	}

	/**
	 * Removes the first car from the queue. If the queue is empty, a
	 * NoSuchElementException is thrown.
	 * 
	 * @throws NoSuchElementException
	 *             If queue is empty.
	 */
	public void removeFirst() throws NoSuchElementException {
		if (queue.isEmpty()) {
			throw new NoSuchElementException();
		} else {
			queue.remove(0);
		}
	}

	/**
	 * Returns the number of elements in the queue.
	 * 
	 * @return The current size of the queue.
	 */
	public int size() {
		return queue.size();
	}
}