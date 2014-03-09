package lab5.simulator;

public abstract class Event implements Comparable<Event>{
	public String name;
	public double startTime;

	public abstract void execute();
	
	@Override
	public int compareTo(Event otherEvent) {
		if (this.startTime == otherEvent.startTime) {
			return 0;
		} else if (this.startTime < otherEvent.startTime) {
			return -1;
		} else {
			return 1;
		}
	}
}
