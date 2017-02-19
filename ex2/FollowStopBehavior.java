package rp.assignments.team.ex2;

import java.awt.Rectangle;

import lejos.nxt.addon.NXTCam;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

public class FollowStopBehavior implements Behavior {

	private NXTCam camera;
	private DifferentialPilot pilot;
	// private boolean shouldRun;

	/**
	 * Construct a stop behaviour
	 * 
	 * @param camera
	 *            the NXTCam object
	 * @param pilot
	 *            the DifferentialPilot object
	 */
	public FollowStopBehavior(NXTCam camera, DifferentialPilot pilot) {
		this.camera = camera;
		this.pilot = pilot;
		// this.shouldRun = true;
	}

	/**
	 * Return true if the robot gets very close to the tracked object
	 */
	@Override
	public boolean takeControl() {
		if (camera.getNumberOfObjects() > 0) {
			Rectangle rect = camera.getRectangle(0);
			if (rect.height * rect.width > 8000) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Start the stop behaviour
	 */
	@Override
	public void action() {
		pilot.stop();
	}

	@Override
	public void suppress() {

	}

}
