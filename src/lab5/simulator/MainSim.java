package lab5.simulator;

import carwash.events.SimulationStarts;
import carwash.state.CarWashState;
import carwash.view.CarWashView;

public class MainSim {

	public static void main(String[] args) {
		long seed = 1234;

		EventQueue eventQueue = new EventQueue();

		CarWashState stateOne = new CarWashState();

		eventQueue.add(new SimulationStarts(stateOne, eventQueue));

		CarWashView view = new CarWashView(stateOne);
		stateOne.addObserver(view);

		stateOne.totalFastWashers = 2;
		stateOne.totalSlowWashers = 2;

		stateOne.setFastWasherDistribution(2.8, 4.6, seed);
		stateOne.setSlowWasherDistribution(3.5, 6.7, seed);
		stateOne.setCarArrivalDistribution(2, seed);
		stateOne.maxCarQueueSize = 5;

		Simulator sim = new Simulator(eventQueue);
		sim.run();

		// The first event SimulationStarts will be created in CarWashStateand
		// added to the event queue.
		CarWashState stateTwo = new CarWashState();
		
		eventQueue.add(new SimulationStarts(stateTwo, eventQueue));
		
		view.setState(stateTwo);
		stateTwo.addObserver(view);

		stateTwo.totalFastWashers = 2;
		stateTwo.totalSlowWashers = 4;
		stateTwo.setFastWasherDistribution(2.8, 5.6, seed);
		stateTwo.setSlowWasherDistribution(4.5, 6.7, seed);
		stateTwo.setCarArrivalDistribution(1.5, seed);
		stateTwo.maxCarQueueSize = 7;

		sim.run();
	}
}
