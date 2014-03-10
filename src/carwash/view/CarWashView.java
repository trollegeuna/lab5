package carwash.view;

import java.util.Observable;

import simulator.SimView;
import carwash.events.SimulationStarts;
import carwash.events.SimulationStops;
import carwash.state.CarWashState;

/**
 * A view which prints the state of the car wash to system out.
 */
public class CarWashView extends SimView {

	private CarWashState state;

	public CarWashView(CarWashState state) {
		setState(state);
	}

	/**
	 * Set which state the view should display.
	 * 
	 * @param state
	 *            The state to be displayed.
	 */
	public void setState(CarWashState state) {
		this.state = state;
	}

	/**
	 * Prints the state to system out.
	 */
	@Override
	public void update(Observable o, Object arg) {

		if (state.getCurrentEvent() instanceof SimulationStarts) {

			// Current event is the start of simulation.

			System.out.println(String.format("Fast machines: %s",
					state.getTotalFastWashers()));
			System.out.println(String.format("Slow machines: %s",
					state.getTotalSlowWashers()));
			System.out.println(String.format("Fast distribution: (%s, %s)",
					state.getFastWasherDistribution()[0],
					state.getFastWasherDistribution()[1]));
			System.out.println(String.format("Slow distribution: (%s, %s)",
					state.getSlowWasherDistribution()[0],
					state.getSlowWasherDistribution()[1]));
			System.out.println(String.format(
					"Exponential distribution with lambda = %s",
					state.getLambda()));
			System.out.println(String.format("Seed = %s", state.getSeed()));
			System.out.println(String.format("Max Queue size: %s",
					state.getMaxCarQueueSize()));

			System.out.println("----------------------------------------");

			System.out
					.println("Time\tFast\tSlow\tId\tEvent\tIdleTime\tQueueTime\tQueueSize\tRejected");

			String reportLine = String.format(
					"%.2f\t%s\t%s\t-\t%s\t%s\t\t%.2f\t\t%s\t\t%s",
					state.currentTime, state.availableFastWashers,
					state.availableSlowWashers, state.getCurrentEvent(),
					state.getTotalIdleTime(), state.getTotalQueueTime(),
					state.carQueue.size(), state.totalRejected);
			System.out.println(reportLine);

		} else if (state.getCurrentEvent() instanceof SimulationStops) {

			// Current state is the end of the simulation.

			String reportLine = String.format(
					"%.2f\t%s\t%s\t-\t%s\t%.2f\t\t%.2f\t\t%s\t\t%s",
					state.currentTime, state.availableFastWashers,
					state.availableSlowWashers, state.getCurrentEvent(),
					state.getTotalIdleTime(), state.getTotalQueueTime(),
					state.carQueue.size(), state.totalRejected);
			System.out.println(reportLine);

			System.out.println("----------------------------------------");

			System.out.println(String.format("Total idle machine time: %.2f",
					state.getTotalIdleTime()));
			System.out.println(String.format("Total queueing time: %.2f",
					state.getTotalQueueTime()));
			System.out.println(String.format("Mean queueing time: %.2f",
					state.meanQueueingTime()));
			System.out.println(String.format("Rejected cars: %s",
					state.totalRejected));
			System.out.println();

		} else {

			// Current state is either the arrival or leaving of a car.

			String reportLine = String.format(
					"%.2f\t%s\t%s\t%s\t%s\t%.2f\t\t%.2f\t\t%s\t\t%s",
					state.currentTime, state.availableFastWashers,
					state.availableSlowWashers, state.getCurrentCar().id,
					state.getCurrentEvent(), state.getTotalIdleTime(),
					state.getTotalQueueTime(), state.carQueue.size(),
					state.totalRejected);
			System.out.println(reportLine);
		}
	}
}
