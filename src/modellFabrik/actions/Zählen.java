package modellFabrik.actions;

import lejos.hardware.sensor.EV3TouchSensor;
import modellFabrik.common.Kommunikation;

public class Zählen implements Runnable {

	EV3TouchSensor t;
	float [] cond;
	int i=0;
	
	public Zählen(EV3TouchSensor t, float [] cond) {
		this.t = t;
		this.cond = cond;
	}

	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()){
			t.fetchSample(cond, 0);
			if (cond [0] == 1) {
				i++;
				Kommunikation.setCounter(Kommunikation.getCounter()+1);
			}
		}
		System.out.println("Zähler beendet. Es wurden "+Kommunikation.getCounter()+" Bälle gezählt.");
		System.out.println("Zähler wird zurückgesetzt");
		Kommunikation.setCounter(0);
	}
	

}
