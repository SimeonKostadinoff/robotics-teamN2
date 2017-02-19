package rp.assignments.team.ex1;

import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;

public class EscapeBumpListener implements SensorPortListener {
	private EscapeController controller;
	
	public EscapeBumpListener(EscapeController controller) {
		this.controller = controller;
	}
	
	public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue) {
		if(aOldValue > aNewValue) {
			//System.out.println("sensor pressed"); //when old value is larger than new value
			controller.setBumped(); //it means sensor is pressed
		} else {
			//System.out.println("sensor released");
		}		
	}

}
