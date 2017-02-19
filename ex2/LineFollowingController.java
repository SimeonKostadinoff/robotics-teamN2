package rp.assignments.team.ex2;

import lejos.nxt.Sound;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;

public class LineFollowingController implements LineEventListener {

	private DifferentialPilot pilot;
	private boolean eventOccurred;
	private byte[] oldevent;
	private byte[] newevent;

	public LineFollowingController(DifferentialPilot pilot) {
		this.pilot = pilot;
		this.oldevent = new byte[3];
		this.newevent = new byte[3];
	}

	public void stateChanged(byte[] e) {

		eventOccurred = true;

		for (int i = 0; i < newevent.length; i++) {
			newevent[i] = e[i];
		}

	}

	public void run() {

		//set speed to 0.55 * robot's max speed
		pilot.setTravelSpeed(0.55 * pilot.getMaxTravelSpeed());
		pilot.forward();

		while (true) {

			Delay.msDelay(10);

			if (eventOccurred) {

				System.out.print("changed: ");
				for (byte val : newevent) {
					System.out.print(val + " ");
				}
				System.out.println();

				if(newevent[0] == 0 && newevent[1] == 0 && newevent[2] == 0) { //0,0,0
					if (oldevent[0] == 0 && oldevent[1] == 0 && oldevent[2] == 1) { //oldevent 0,0,1
						System.out.println("large arc left");
						pilot.arcForward(10);
					} else if(oldevent[0] == 1 && oldevent[1] == 0 && oldevent[2] == 0) { //oldevent 1,0,0
						System.out.println("large arc right");
						pilot.arcForward(-10);
					}
				} else if(newevent[0] == 0 && newevent[1] == 0 && newevent[2] == 1) { //0,0,1
					System.out.println("arc left");
					pilot.arcForward(30);
				} else if(newevent[0] == 1 && newevent[1] == 0 && newevent[2] == 0) { //1,0,0
					System.out.println("arc right");
					pilot.arcForward(-30);
				} else if(newevent[0] == 1 && newevent[1] == 1 && newevent[2] == 1) { //1,1,1
					System.out.println("junction detected, turn left");
					Sound.playTone(4000, 800, 100);
					pilot.stop();
					pilot.travel(6.0);
					pilot.rotate(90.0, false); //turn left
					pilot.forward();
				} else {
					pilot.forward(); //in other cases, move straight forward
				}
				
				for (int i = 0; i < oldevent.length; i++) {
					oldevent[i] = newevent[i];
				}
				eventOccurred = false;
				
			}

		}
	}

}
