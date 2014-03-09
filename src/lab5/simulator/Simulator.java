package lab5.simulator;

import carwash.state.CarWashState;
import carwash.view.CarWashView;

public class Simulator {

	public void run() {
		EventQueue queue = new EventQueue();

		// The first event (SimulationStarts) will be created in CarWashState
		// and
		// added to the event queue.
		CarWashState state = new CarWashState(queue, 15);

		CarWashView view = new CarWashView(state);
		state.addObserver(view);

		// Keep executing events while there are still some events left.
		do {
			Event event = queue.first();
			queue.removeFirst();
			event.execute();
		} while (queue.size() != 0);
	}

}
