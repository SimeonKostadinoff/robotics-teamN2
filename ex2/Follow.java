package rp.assignments.team.ex2;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.NXTCam;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Follow {

	public static void main(String[] args) {
		// create pilot
		DifferentialPilot pilot = new DifferentialPilot(5.6f, 11.5f, Motor.C, Motor.B, false);
		pilot.setTravelSpeed(0.4 * pilot.getMaxTravelSpeed());

		// create camera
		NXTCam camera = new NXTCam(SensorPort.S1);
		camera.setTrackingMode(NXTCam.OBJECT_TRACKING);
		// largest rectangle comes first at index 0
		camera.sortBy(NXTCam.SIZE);
		// enable object tracking
		camera.enableTracking(true);

		// create behaviours
		Behavior rotate = new FollowRotateBehavior(pilot);
		Behavior move = new FollowMoveBehavior(camera, pilot);
		Behavior stop = new FollowStopBehavior(camera, pilot);

		// add behaviours to array in order
		Behavior[] behaviors = new Behavior[3];
		behaviors[0] = rotate;
		behaviors[1] = move;
		behaviors[2] = stop;

		Arbitrator arb = new Arbitrator(behaviors);

		// start the robot
		arb.start();

	}

}
