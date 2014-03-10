package carwash.state;

/**
 * Creates cars and keeps track of their id numbers.
 */
public class CarFactory {
	private int id = 0;

	/**
	 * Creates a new car.
	 * 
	 * @return The new car.
	 */
	public Car makeCar() {
		return new Car(id++);
	}
}
