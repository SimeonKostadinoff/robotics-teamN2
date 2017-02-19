package rp.assignments.team.ex2;

import lejos.nxt.Button;
import lejos.nxt.ButtonListener;

public class ProportionalButtonListener implements ButtonListener {

	private int[] distance;
	private boolean change;
	
	/**
	 * Construct a button listener
	 * @param distance the distance array of size 1 that will be adjusted by the button listener
	 * @param change true if it is the right button which should increment the value by 10, else decrement by 10
	 */
	public ProportionalButtonListener(int[] distance, boolean change) {
		this.distance = distance;
		this.change = change;
	}
	
	@Override
	public void buttonPressed(Button b) {
		int tmpdistance = this.distance[0];
		if(change) {
			tmpdistance += 10;
		} else {
			tmpdistance -=10;
		}

		if(tmpdistance >= 10 && tmpdistance <= 40) {
			this.distance[0] = tmpdistance;
			System.out.println("new distance: " + this.distance[0]);
		}
		
	}

	@Override
	public void buttonReleased(Button b) {
		// TODO Auto-generated method stub

	}

}
