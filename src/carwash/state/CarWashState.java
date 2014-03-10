package carwash.state;

import simulator.Event;
import simulator.SimState;

/**
 * Represents the current state of a car wash.
 */
public class CarWashState extends SimState {
	private int totalFastWashers = 2;
	private int totalSlowWashers = 2;
	public int availableFastWashers;
	public int availableSlowWashers;

	private UniformRandomStream fastURS;
	private UniformRandomStream slowURS;
	private ExponentialRandomStream eRS;

	private double[] fastDist = new double[] { 2.8, 4.6 };
	private double[] slowDist = new double[] { 3.5, 6.7 };
	private double lambda = 2;
	private long seed = 1234;

	private double totalQueueTime;
	private double totalIdleTime;
	public int totalRejected;
	public int totalCarsQueued;

	private Car currentCar;
	public FIFO carQueue = new FIFO();

	private int maxCarQueueSize = 5;
	private CarFactory carFactory = new CarFactory();

	private Event previousEvent;
	private Event currentEvent;

	public CarWashState(int totalFastWashers, int totalSlowWashers) {
		setFastWasherDistribution(fastDist[0], fastDist[1], seed);
		setSlowWasherDistribution(slowDist[0], slowDist[1], seed);
		setCarArrivalDistribution(lambda, seed);
		this.totalFastWashers = totalFastWashers;
		this.totalSlowWashers = totalSlowWashers;
		availableFastWashers = totalFastWashers;
		availableSlowWashers = totalSlowWashers;
	}

	/**
	 * Makes a new car
	 * 
	 * @return
	 */
	public Car makeCar() {
		return carFactory.makeCar();
	}

	/**
	 * Total amount of both fast and slow washers.
	 * 
	 * @return
	 */
	public int amountAvailableWashers() {
		return availableFastWashers + availableSlowWashers;
	}

	/**
	 * True if car queue is full.
	 * 
	 * @return
	 */
	public boolean carQueueIsFull() {
		return (carQueue.size() >= maxCarQueueSize);
	}

	/**
	 * The car associated with the current event.
	 * 
	 * @return
	 */
	public Car getCurrentCar() {
		return currentCar;
	}

	/**
	 * Returns the current event.
	 * 
	 * @return
	 */
	public Event getCurrentEvent() {
		return currentEvent;
	}

	/**
	 * The random distribution for fast washer job time.
	 * 
	 * @return
	 */
	public double[] getFastWasherDistribution() {
		return fastDist;
	}

	/**
	 * The time when a fast washer will finish the job, based on current time.
	 * 
	 * @return
	 */
	public double getFastWasherFinishTime() {
		return currentTime + fastURS.next();
	}

	/**
	 * Lambda for car arrival distribution.
	 * 
	 * @return
	 */
	public double getLambda() {
		return lambda;
	}

	/**
	 * Maximum allowed size of car queue.
	 * 
	 * @return
	 */
	public int getMaxCarQueueSize() {
		return maxCarQueueSize;
	}

	/**
	 * The time when the next car will arrive, based on current time.
	 * 
	 * @return
	 */
	public double getNextArrivalTime() {
		return currentTime + eRS.next();
	}

	/**
	 * Seed for random number generation.
	 * 
	 * @return
	 */
	public long getSeed() {
		return seed;
	}

	/**
	 * The random distribution for slow washer job time.
	 * 
	 * @return
	 */
	public double[] getSlowWasherDistribution() {
		return slowDist;
	}

	/**
	 * The time when a slow washer will finish the job, based on current time.
	 * 
	 * @return
	 */
	public double getSlowWasherFinishTime() {
		return currentTime + slowURS.next();
	}

	/**
	 * How many fast washers the car wash has in total.
	 * 
	 * @return
	 */
	public int getTotalFastWashers() {
		return totalFastWashers;
	}

	/**
	 * The total time all washers have spent idle.
	 * 
	 * @return
	 */
	public double getTotalIdleTime() {
		return totalIdleTime;
	}

	/**
	 * The total time cars spent in queue..
	 * 
	 * @return
	 */
	public double getTotalQueueTime() {
		return totalQueueTime;
	}

	/**
	 * How many slow washers the car wash has in total.
	 * 
	 * @return
	 */
	public int getTotalSlowWashers() {
		return totalSlowWashers;
	}

	/**
	 * True if there are any free washers, fast or slow.
	 * 
	 * @return
	 */
	public boolean hasAvailableWashers() {
		return (availableFastWashers != 0 || availableSlowWashers != 0);
	}

	/**
	 * TODO: Not sure what this is.
	 * 
	 * @return
	 */
	public double meanQueueingTime() {
		return (totalCarsQueued == 0) ? 0 : totalQueueTime / totalCarsQueued;
	}

	/**
	 * Sets the time distribution for car arrivals.
	 * 
	 * @param lambda
	 * @param seed
	 */
	public void setCarArrivalDistribution(double lambda, long seed) {
		this.lambda = lambda;
		this.seed = seed;
		eRS = new ExponentialRandomStream(lambda, seed);
	}

	/**
	 * Marks this Observable object as having been changed; the hasChanged
	 * method will now return true.
	 */
	@Override
	public void setChanged() {
		super.setChanged();
	}

	/**
	 * Sets which car is associaded with the current event.
	 * 
	 * @param car
	 */
	public void setCurrentCar(Car car) {
		currentCar = car;
	}

	/**
	 * Sets which event is currently being processed.
	 * 
	 * @param currentEvent
	 */
	public void setCurrentEvent(Event currentEvent) {
		previousEvent = this.currentEvent;
		this.currentEvent = currentEvent;
	}

	/**
	 * Sets the time distribution for fast washer job time.
	 * 
	 * @param low
	 * @param high
	 * @param seed
	 */
	public void setFastWasherDistribution(double low, double high, long seed) {
		fastDist[0] = low;
		fastDist[1] = high;
		this.seed = seed;
		fastURS = new UniformRandomStream(low, high, seed);
	}

	/**
	 * The maximum allowed length of the car queue.
	 * 
	 * @param maxCarQueueSize
	 */
	public void setMaxCarQueueSize(int maxCarQueueSize) {
		this.maxCarQueueSize = maxCarQueueSize;
	}

	/**
	 * Sets the time distribution for slow washer job time.
	 * 
	 * @param low
	 * @param high
	 * @param seed
	 */
	public void setSlowWasherDistribution(double low, double high, long seed) {
		slowDist[0] = low;
		slowDist[1] = high;
		this.seed = seed;
		slowURS = new UniformRandomStream(low, high, seed);
	}

	/**
	 * Sets the current time.
	 * 
	 * @param time
	 */
	public void setTime(double time) {
		currentTime = time;
	}

	/**
	 * Updates the total time the washers have spent idle.
	 */
	public void updateIdleTime() {
		double currentEventTime = (currentEvent == null) ? 0
				: currentEvent.startTime;
		double previousEventTime = (previousEvent == null) ? 0
				: previousEvent.startTime;

		totalIdleTime = totalIdleTime + amountAvailableWashers()
				* (currentEventTime - previousEventTime);
	}

	/**
	 * Updates the total time all cars have spent in the queue.
	 */
	public void updateQueueTime() {
		double currentEventTime = (currentEvent == null) ? 0
				: currentEvent.startTime;
		double previousEventTime = (previousEvent == null) ? 0
				: previousEvent.startTime;

		totalQueueTime = totalQueueTime + carQueue.size()
				* Math.abs(currentEventTime - previousEventTime);
	}

}