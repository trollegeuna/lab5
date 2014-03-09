package carwash.state;

public class CarFactory {
	private int index = -1;

	public Car makeCar() {
		index++;
		return new Car(index);
	}
}
