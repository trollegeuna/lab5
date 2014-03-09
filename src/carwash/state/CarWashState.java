package carwash.state;

import lab5.simulator.Event;
import lab5.simulator.SimState;

public class CarWashState extends SimState {
	public int totalFastWashers = 2;
	public int totalSlowWashers = 2;
	public int totalAmountOfWashers;
	public int availableFastWashers;
	public int availableSlowWashers;

	public double stopTime = 15;
	public double currentTime = super.currentTime;

	private UniformRandomStream fastURS;
	private UniformRandomStream slowURS;
	private ExponentialRandomStream eRS;

	private double[] fastDist = new double[] { 2.8, 4.6 };
	private double[] slowDist = new double[] { 3.5, 6.7 };
	private double lambda = 2;
	private long seed = 1234;

	public double totalQueueTime;
	public double totalIdleTime;
	public int totalRejected;
	public int maxCarQueueSize = 5;
	public int totalCarsQueued;

	public Car currentCar;
	public FIFO carQueue = new FIFO();
	public CarFactory carFactory = new CarFactory(this);
	private Event previousEvent;
	private Event currentEvent;

	public CarWashState() {
		setFastWasherDistribution(fastDist[0], fastDist[1], seed);
		setSlowWasherDistribution(slowDist[0], slowDist[1], seed);
		eRS = new ExponentialRandomStream(lambda, seed);
		availableFastWashers = totalFastWashers;
		availableSlowWashers = totalSlowWashers;
	}

	public int amountAvailableWashers() {
		return availableFastWashers + availableSlowWashers;
	}

	public Event getCurrentEvent() {
		return currentEvent;
	}

	public double[] getFastWasherDistribution() {
		return fastDist;
	}

	public double getFastWasherFinishTime() {
		return currentTime + fastURS.next();
	}

	public double getLambda() {
		return lambda;
	}

	public double getNextArrivalTime() {
		return currentTime + eRS.next();
	}

	public long getSeed() {
		return seed;
	}

	public double[] getSlowWasherDistribution() {
		return slowDist;
	}
	
	
	public double getSlowWasherFinishTime() {
		return currentTime + slowURS.next();
	}
	
	public boolean isFull() {
		if (availableFastWashers == 0 && availableSlowWashers == 0) {
			return true;
		}
		return false;
	}

	public double meanQueueingTime() {
		return (totalCarsQueued == 0) ? 0 : totalQueueTime / totalCarsQueued;
	}

	public void setCarArrivalDistribution(double lambda, long seed) {
		this.lambda = lambda;
		this.seed = seed;
		eRS = new ExponentialRandomStream(lambda, seed);
	}

	public void setChanged() {
		super.setChanged();
	}

	public void setCurrentCar(Car car) {
		currentCar = car;
	}

	public void setCurrentEvent(Event currentEvent) {
		previousEvent = this.currentEvent;
		this.currentEvent = currentEvent;
	}

	public void setFastWasherDistribution(double low, double high, long seed) {
		fastDist[0] = low;
		fastDist[1] = high;
		this.seed = seed;
		fastURS = new UniformRandomStream(low, high, seed);
	}

	public void setSlowWasherDistribution(double low, double high, long seed) {
		slowDist[0] = low;
		slowDist[1] = high;
		this.seed = seed;
		slowURS = new UniformRandomStream(low, high, seed);
	}

	public void setTime(double time) {
		currentTime = time;
	}

	public void updateIdleTime() {
		double currentEventTime = (currentEvent == null) ? 0
				: currentEvent.startTime;
		double previousEventTime = (previousEvent == null) ? 0
				: previousEvent.startTime;

		totalIdleTime = totalIdleTime + amountAvailableWashers()
				* (currentEventTime - previousEventTime);
	}

	public void updateQueueTime() {
		double currentEventTime = (currentEvent == null) ? 0
				: currentEvent.startTime;
		double previousEventTime = (previousEvent == null) ? 0
				: previousEvent.startTime;

		totalQueueTime = totalQueueTime + carQueue.size()
				* Math.abs(currentEventTime - previousEventTime);
	}

	public boolean washersAreAvailable() {
		return (availableFastWashers != 0 || availableSlowWashers != 0);
	}

}