package carwash.state;


import carwars.events.SimulationStarts;
import lab5.simulator.EventQueue;
import lab5.simulator.SimState;

public class CarWashState extends SimState {
	final public int totalFastWashers = 2;
	final public int totalSlowWashers = 2;
	public int availableFastWashers;
	public int availableSlowWashers;
	public int currentTime = super.currentTime;

	private long seed = 1234;

	public UniformRandomStream fastURS;
	public UniformRandomStream slowURS;
	public ExponentialRandomStream eRS;

	private double[] fastDist = new double[] { 2.8, 4.6 };
	private double[] slowDist = new double[] { 3.5, 6.7 };
	private double lambda = 2;
	public double totalQueueTime;
	public double totalIdleTime;
	public int totalRejected;

	public FIFO carQueue;
	public CarFactory carFactory = new CarFactory();
	public EventQueue eventQueue;
	
	public CarWashState(EventQueue eventQueue) {
		this.eventQueue = eventQueue;
		eventQueue.add(new SimulationStarts(this, eventQueue));
	}

	public boolean isFull() {
		if (availableFastWashers == 0 && availableSlowWashers == 0) {
			return true;
		}
		return false;
	}

	public CarWashState() {
		fastURS = new UniformRandomStream(fastDist[0], fastDist[1], seed);
		slowURS = new UniformRandomStream(slowDist[0], slowDist[1], seed);
		eRS = new ExponentialRandomStream(lambda, seed);
	}

}