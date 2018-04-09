package modellFabrik.actions;

import lejos.hardware.sensor.EV3TouchSensor;
import modellFabrik.common.Kommunikation;

public class Z�hlen implements Runnable {

	EV3TouchSensor t;
	float [] cond;
	int i=0;
	
	public Z�hlen(EV3TouchSensor t, float [] cond) {
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
		System.out.println("Z�hler beendet. Es wurden "+Kommunikation.getCounter()+" B�lle gez�hlt.");
		System.out.println("Z�hler wird zur�ckgesetzt");
		Kommunikation.setCounter(0);
	}
	

}
