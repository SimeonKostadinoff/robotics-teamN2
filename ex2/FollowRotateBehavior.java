package rp.assignments.team.ex2;

import lejos.nxt.Sound;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

public class FollowRotateBehavior implements Behavior {

	private DifferentialPilot pilot;
	private boolean shouldRun;

	/**
	 * Construct a rotate behaviour
	 * 
	 * @param pilot
	 *            the DifferentialPilot object
	 */
	public FollowRotateBehavior(DifferentialPilot pilot) {
		this.pilot = pilot;
		this.shouldRun = true;
	}

	/**
	 * Returns true always
	 */
	@Override
	public boolean takeControl() {
		return true;
	}

	/**
	 * Start the rotate behaviour
	 */
	@Override
	public void action() {
		Sound.playTone(3000, 100, 100);

		pilot.rotateLeft(); // non-blocking

		while (shouldRun) {
			Delay.msDelay(100);
		}

		pilot.stop();
		shouldRun = true;
	}

	/**
	 * Suppress the behaviour
	 */
	@Override
	public void suppress() {
		shouldRun = false;
	}

}
