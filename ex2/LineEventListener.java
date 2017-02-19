package rp.assignments.team.ex2;

/**
 * Interface for listeners of LineEventGenerator
 */
public interface LineEventListener {
	/**
	 * This method will be called when an event occurs
	 * 
	 * @param e
	 *            a byte array of size 3 where 0 means off-line and 1 means
	 *            on-line
	 */
	public void stateChanged(byte[] e);
}
