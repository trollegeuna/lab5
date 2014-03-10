package simulator;

import java.util.Observable;
import java.util.Observer;

/**
 * An abstract view for displaying simulation data.
 */
public abstract class SimView implements Observer {

	@Override
	public abstract void update(Observable o, Object arg);

}
