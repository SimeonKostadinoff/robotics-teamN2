package rp.assignments.team.ex2;

import java.util.LinkedList;
import java.util.Random;

import lejos.nxt.Button;
import lejos.nxt.Sound;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;

public class LineJunctionController implements LineEventListener {

	private DifferentialPilot pilot;
	private boolean eventOccurred;
	private byte[] oldevent;
	private byte[] newevent;

	public LineJunctionController(DifferentialPilot pilot) {
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

		LinkedList<Byte> sequence = new LinkedList<Byte>();
		int sequenceIndex = 0;
		
		System.out.println("Please input the sequence of movement and enter to start the robot");
		
		Button.LEFT.addButtonListener(new LineButtonListener((byte)1, sequence));
		Button.RIGHT.addButtonListener(new LineButtonListener((byte)0, sequence));
		Button.ESCAPE.addButtonListener(new LineButtonListener((byte)-1, sequence));
		
		Button.ENTER.waitForPressAndRelease(); // start the robot when user pressed Enter button
		
		Random rng = null;
		// if the user did not input any sequence, the robot will turn randomly
		if(sequence.isEmpty()) {
			rng = new Random();
			sequenceIndex = -1;
		}		
		
		// set the speed to 0.4 * the robot's max speed
		pilot.setTravelSpeed(0.4 * pilot.getMaxTravelSpeed());
		pilot.forward();

		while (true) {
			
			Delay.msDelay(30);

			if (eventOccurred) {

				System.out.print("changed: ");
				for (byte val : newevent) {
					System.out.print(val + " ");
				}
				System.out.println();

				if(newevent[0] == 0 && newevent[1] == 0 && newevent[2] == 0) { //0,0,0
					if (oldevent[0] == 0 && oldevent[1] == 0 && oldevent[2] == 1) { //oldevent 0,0,1
						System.out.println("large arc left");
						pilot.arcForward(30);
					} else if(oldevent[0] == 1 && oldevent[1] == 0 && oldevent[2] == 0) { //oldevent 1,0,0
						System.out.println("large arc right");
						pilot.arcForward(-30);
					}
				} else if(newevent[0] == 0 && newevent[1] == 0 && newevent[2] == 1) { //0,0,1
					System.out.println("arc left");
					pilot.arcForward(50);
				} else if(newevent[0] == 1 && newevent[1] == 0 && newevent[2] == 0) { //1,0,0
					System.out.println("arc right");
					pilot.arcForward(-50);
				} else if(newevent[0] == 1 && newevent[1] == 1 && newevent[2] == 1) { //1,1,1
					System.out.print("junction detected, ");
					Sound.playTone(4000, 800, 100);
					pilot.stop();
					pilot.travel(6.0);
					
					if(sequenceIndex == -1) {
						if(rng.nextBoolean()) {
							System.out.println("turn left");
							pilot.rotate(90.0); //turn left
						} else {
							System.out.println("turn right");
							pilot.rotate(-90); //turn right
						}
					} else {
						if(sequenceIndex == sequence.size()) {
							sequenceIndex = 0;
						}
						
						byte direction = sequence.get(sequenceIndex++);
						switch(direction) {
						case 1: 
							System.out.println("turn left");
							pilot.rotate(90.0);
							break;
						
						case 0:
							System.out.println("turn right");
							pilot.rotate(-90.0);
							break;
							
						default:
							System.out.println("go forward");
							//just forward
						}
					}
					
					
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
