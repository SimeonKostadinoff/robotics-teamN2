package rp.assignments.team.ex1;

import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;

public class EscapeController {

	private boolean bumped = false;
	private DifferentialPilot pilot;
	private UltrasonicSensor sonic;
	
	public EscapeController(DifferentialPilot pilot, UltrasonicSensor sonic) {
		this.pilot = pilot;
		this.sonic = sonic;
		sonic.continuous(); //set mode to continuous
	}
	
	public void setBumped() {
		bumped = true;
	}
	
	/**
	 * Contains the movement algorithm
	 */
	public void run() {
		pilot.forward();
		System.out.println("started");
		
		Delay.msDelay(100);
		
		float range;
		int counter = 0; //counter to prevent keep moving around a square
		
		while(true) {
			Delay.msDelay(30);
			range = sonic.getRange();
			if(bumped && range < 20) { //bumped and inRange
				pilot.stop();
				pilot.travel(-6.0f); //reverse
				pilot.rotate(-90.0); //turn left
				pilot.forward(); //move forward
				bumped = false;
				counter = 0;
			} else if(bumped && range >= 20) { //bumped and !inRange
				pilot.stop();
				pilot.travel(-6.0f); //reverse
				pilot.rotate(-90.0); //turn left
				pilot.travel(10);
				pilot.forward();
				bumped = false;
				counter = 0;
			} else if(!bumped && range < 20) {
				//keep forward
			} else {  //!bumped and !inRange
				if(counter < 4) {
					pilot.stop();
					pilot.rotate(90.0); //turn right
					pilot.travel(40); //travel the length of the robot
					pilot.forward();
					counter += 1;
				} else {
					pilot.forward(); //if it has already made 4 right turns, it will just move forward instead
				}
			}
		}
	}
	
}
