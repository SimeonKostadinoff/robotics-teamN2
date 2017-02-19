package rp.assignments.team.ex2;

import java.awt.Rectangle;

import lejos.nxt.addon.NXTCam;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

public class FollowMoveBehavior implements Behavior {

	private NXTCam camera;
	private DifferentialPilot pilot;
	private boolean shouldRun;

	/**
	 * Construct a move behaviour
	 * 
	 * @param camera
	 *            the NXTCam object
	 * @param pilot
	 *            the DifferentialPilot object
	 */
	public FollowMoveBehavior(NXTCam camera, DifferentialPilot pilot) {
		this.camera = camera;
		this.pilot = pilot;
		this.shouldRun = true;
	}

	/**
	 * Return true when the camera sees a tracked object
	 */
	@Override
	public boolean takeControl() {
		return camera.getNumberOfObjects() > 0;
	}

	/**
	 * Start the object tracking behaviour
	 */
	@Override
	public void action() {
		// Sound.playTone(4000, 100, 100);

		pilot.forward();
		while (shouldRun) {
			Delay.msDelay(50);

			// ensure the camera sees objects
			int num = camera.getNumberOfObjects();
			if (num == 0)
				break;

			// get the largest rectangle
			Rectangle rect = camera.getRectangle(0);
			int area = rect.height * rect.width;
			// the centre of the rectangle
			int xmid = rect.x + (rect.width / 2);
			// int ymid = rect.y + (rect.height / 2);

			if (area != 0) {
				if (xmid >= 95) {
					// arc right
					pilot.arcForward(30);
				} else if (xmid <= 75) {
					// arc left
					pilot.arcForward(-30);
				} else {
					// straight forward
					pilot.forward();
				}
			}
		}

		// stop the robot when behaviour suppressed
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
