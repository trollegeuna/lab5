package simulator;

import java.util.Observable;

/**
 * An abstract simulation state.
 */
public abstract class SimState extends Observable {

	public double currentTime;
	public Event currentEvent;

}
