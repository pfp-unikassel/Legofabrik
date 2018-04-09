package modellFabrik.actions;

import lejos.hardware.sensor.EV3TouchSensor;
import lejos.utility.Delay;

//Läuft bis der Sensor gedrückt wird
public class TouchSensor implements Runnable {
	
	EV3TouchSensor touch;
	float [] touchCond;

	public TouchSensor (EV3TouchSensor touch) {
		this.touch=touch;
		touchCond = new float [touch.sampleSize()];
	}
	
	@Override
	public void run() {
		touch.fetchSample(touchCond, 0);
		while (touchCond[0]==0){
			touch.fetchSample(touchCond, 0);
			Delay.msDelay(10);
		}
		Thread.currentThread().interrupt();
		
		
	}

}
