package rp.assignments.team.ex2;

import java.util.LinkedList;

import lejos.nxt.Button;

public class LineButtonListener implements lejos.nxt.ButtonListener {

	private byte direction;
	private LinkedList<Byte> sequence;
	
	public LineButtonListener(byte direction, LinkedList<Byte> sequence) {
		this.direction = direction;
		this.sequence = sequence;
	}
	
	public void buttonPressed(Button b) {
		sequence.add(direction);
	}

	@Override
	public void buttonReleased(Button b) {

	}

}
