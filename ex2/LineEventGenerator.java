package rp.assignments.team.ex2;

import java.util.Arrays;
import lejos.nxt.LightSensor;
import lejos.util.Delay;

public class LineEventGenerator extends Thread {
	private LightSensor ls1, ls2, ls3;
	private LineEventListener listener;
	private int lthreshold;
	private int hthreshold;
	
	/**
	 * Construct a LineEventGenerator object
	 * @param ls1 the left light sensor
	 * @param ls2 the middle light sensor
	 * @param ls3 the right light sensor
	 * @param lthreshold the on-line threshold
	 * @param hthreshold the off-line threshold
	 */
	public LineEventGenerator(LightSensor ls1, LightSensor ls2, LightSensor ls3, int lthreshold, int hthreshold) {
		this.ls1 = ls1;
		this.ls2 = ls2;
		this.ls3 = ls3;
		this.lthreshold = lthreshold;
		this.hthreshold = hthreshold;
	}
	
	/**
	 * Set the event listener
	 * @param listener the object that implements LineEventListener interface
	 */
	public void setLineEventListener(LineEventListener listener) {
		this.listener = listener;
	}
	
	/**
	 * Start the event generator
	 */
	public void run() {
		
		//check if listener has been set
		if(listener == null) {
			System.out.println("listener is not set yet");
			System.exit(1);
		}
		
		//turn on the FloodLights
		ls1.setFloodlight(true);
		ls2.setFloodlight(true);
		ls3.setFloodlight(true);
		
		byte[] oldevent = new byte[3];
		byte[] newevent = new byte[3];
		int[] vals = new int[3];
		
		while(true) {
			Delay.msDelay(20);
			
			//get the readings
			vals[0] = ls1.getNormalizedLightValue();
			vals[1] = ls2.getNormalizedLightValue();
			vals[2] = ls3.getNormalizedLightValue();
			
			for(int i = 0; i < vals.length; i++) {
				int val = vals[i];
				if(hthreshold-30 <= val && val <= hthreshold+30) { //off line //old ones were 350 and 340
					newevent[i] = 0;
				} else if(lthreshold-30 <= val && val <= lthreshold+30) { //on line
					newevent[i] = 1;
				}
			}
			
			if(Arrays.equals(oldevent, newevent)) {
				//no new event
			} else {
				//generate new event
				listener.stateChanged(newevent);

				//assign newevent values to oldevent
				for(int i = 0; i < oldevent.length; i++) {
					oldevent[i] = newevent[i];
				}
			}
			
		}
		
	}
	
}
