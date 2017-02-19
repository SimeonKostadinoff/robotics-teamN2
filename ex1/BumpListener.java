package rp.assignments.team.ex1;

import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;

public class BumpListener implements SensorPortListener {
	private BumpController controller;
	
	public BumpListener(BumpController controller) {
		this.controller = controller;
	}
	
	public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue) {
		if(aOldValue > aNewValue) {
			System.out.println("sensor pressed");
			controller.setBumped();
		} else {
			System.out.println("sensor released");
		}		
	}

}
