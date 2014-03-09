package carwash.view;

import java.util.Observable;

import carwash.events.SimulationStarts;
import carwash.events.SimulationStops;
import carwash.state.CarWashState;
import lab5.simulator.SimView;

public class CarWashView extends SimView {

	private CarWashState state;

	public CarWashView(CarWashState state) {
		setState(state);
	}
	
	public void setState(CarWashState state) {
		this.state = state;
	}

	@Override
	public void update(Observable o, Object arg) {

		if (state.getCurrentEvent() instanceof SimulationStarts) {

			System.out.println(String.format("Fast machines: %s",
					state.totalFastWashers));
			System.out.println(String.format("Slow machines: %s",
					state.totalSlowWashers));
			System.out.println(String.format("Fast distribution: (%s, %s)",
					state.getFastWasherDistribution()[0], state.getFastWasherDistribution()[1]));
			System.out.println(String.format("Slow distribution: (%s, %s)",
					state.getSlowWasherDistribution()[0], state.getSlowWasherDistribution()[1]));
			System.out.println(String.format(
					"Exponential distribution with lambda = %s", state.getLambda()));
			System.out.println(String.format("Seed = %s", state.getSeed()));
			System.out.println(String.format("Max Queue size: %s",
					state.maxCarQueueSize));
			System.out.println("----------------------------------------");

			System.out
					.println("Time\tFast\tSlow\tId\tEvent\tIdleTime\tQueueTime\tQueueSize\tRejected");
			String reportLine = String.format(
					"%.2f\t%s\t%s\t-\t%s\t%s\t\t%.2f\t\t%s\t\t%s",
					state.currentTime, state.availableFastWashers,
					state.availableSlowWashers, state.getCurrentEvent().name,
					state.totalIdleTime, state.totalQueueTime,
					state.carQueue.size(), state.totalRejected);
			System.out.println(reportLine);

		} else if (state.getCurrentEvent() instanceof SimulationStops) {

			String reportLine = String.format(
					"%.2f\t%s\t%s\t-\t%s\t%.2f\t\t%.2f\t\t%s\t\t%s",
					state.currentTime, state.availableFastWashers,
					state.availableSlowWashers, state.getCurrentEvent().name,
					state.totalIdleTime, state.totalQueueTime,
					state.carQueue.size(), state.totalRejected);
			System.out.println(reportLine);
			System.out.println("----------------------------------------");
			System.out.println(String.format("Total idle machine time: %.2f",
					state.totalIdleTime));
			System.out.println(String.format("Total queueing time: %.2f",
					state.totalQueueTime));
			System.out.println(String.format("Mean queueing time: %.2f",
					state.meanQueueingTime()));
			System.out.println(String.format("Rejected cars: %s",
					state.totalRejected));
			System.out.println();
			
		} else {

			String reportLine = String.format(
					"%.2f\t%s\t%s\t%s\t%s\t%.2f\t\t%.2f\t\t%s\t\t%s",
					state.currentTime, state.availableFastWashers,
					state.availableSlowWashers, state.currentCar.id,
					state.getCurrentEvent().name, state.totalIdleTime,
					state.totalQueueTime, state.carQueue.size(),
					state.totalRejected);
			System.out.println(reportLine);
		}
	}
}
