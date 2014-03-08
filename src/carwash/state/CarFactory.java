package carwash.state;

public class CarFactory {
	private int index = -1;
	CarWashState carWashState;

	public CarFactory(CarWashState carWashState) {
		this.carWashState = carWashState;
	}

	public Car makeCar() {
		index++;
		return new Car(index, carWashState.currentTime);
	}
}
