package carwash.state;

/**
 * Creates cars and keeps track of their id numbers.
 */
public class CarFactory {
	private int id = -1;

	/**
	 * Creates a new car.
	 * 
	 * @return The new car.
	 */
	public Car makeCar() {
		id = id + 1;
		return new Car(id);
	}
}
