package carwash.state;

import carwash.events.SimulationStarts;
import lab5.simulator.Event;
import lab5.simulator.EventQueue;
import lab5.simulator.SimState;

public class CarWashState extends SimState {
	final public int totalFastWashers = 2;
	final public int totalSlowWashers = 2;
	public int totalAmountOfWashers;
	public int availableFastWashers;
	public int availableSlowWashers;

	public double stopTime;
	public double currentTime = super.currentTime;

	private UniformRandomStream fastURS;
	private UniformRandomStream slowURS;
	private ExponentialRandomStream eRS;

	public double[] fastDist = new double[] { 2.8, 4.6 };
	public double[] slowDist = new double[] { 3.5, 6.7 };
	public double lambda = 2;
	public long seed = 1234;

	public double totalQueueTime;
	public double totalIdleTime;
	public int totalRejected;
	public int maxCarQueueSize = 5;
	public int totalCarsQueued;

	public Car currentCar;
	public FIFO carQueue;
	public CarFactory carFactory;
	public EventQueue eventQueue;
	private Event previousEvent;
	private Event currentEvent;

	public void setCurrentCar(Car car) {
		currentCar = car;
	}

	public Event getCurrentEvent() {
		return currentEvent;
	}

	public double getSlowWasherFinishTime() {
		return currentTime + slowURS.next();
	}

	public double getFastWasherFinishTime() {
		return currentTime + fastURS.next();
	}

	public double getNextArrivalTime() {
		return currentTime + eRS.next();
	}

	public CarWashState(EventQueue eventQueue, double stopTime) {
		this.stopTime = stopTime;
		this.eventQueue = eventQueue;
		eventQueue.add(new SimulationStarts(this, eventQueue));
		carFactory = new CarFactory(this);
		carQueue = new FIFO();
		fastURS = new UniformRandomStream(fastDist[0], fastDist[1], seed);
		slowURS = new UniformRandomStream(slowDist[0], slowDist[1], seed);
		eRS = new ExponentialRandomStream(lambda, seed);
		availableFastWashers = totalFastWashers;
		availableSlowWashers = totalSlowWashers;
	}

	public void setChanged() {
		super.setChanged();
	}

	public void setTime(double time) {
		currentTime = time;
	}

	public boolean isFull() {
		if (availableFastWashers == 0 && availableSlowWashers == 0) {
			return true;
		}
		return false;
	}

	public boolean washersAreAvailable() {
		return (availableFastWashers != 0 || availableSlowWashers != 0);
	}

	public int amountAvailableWashers() {
		return availableFastWashers + availableSlowWashers;
	}

	public void updateIdleTime() {
		double currentEventTime = (currentEvent == null) ? 0
				: currentEvent.startTime;
		double previousEventTime = (previousEvent == null) ? 0
				: previousEvent.startTime;

		totalIdleTime = totalIdleTime + amountAvailableWashers()
				* (currentEventTime - previousEventTime);
	}

	public void setCurrentEvent(Event currentEvent) {
		previousEvent = this.currentEvent;
		this.currentEvent = currentEvent;
	}

	public void updateQueueTime() {
		double currentEventTime = (currentEvent == null) ? 0
				: currentEvent.startTime;
		double previousEventTime = (previousEvent == null) ? 0
				: previousEvent.startTime;

		totalQueueTime = totalQueueTime + carQueue.size()
				* Math.abs(currentEventTime - previousEventTime);
	}

	public double meanQueueingTime() {
		return (totalCarsQueued == 0) ? 0 : totalQueueTime / totalCarsQueued;
	}

}