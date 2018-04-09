package modellFabrik.actions;

import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;


//Läuft bis ein bestimmter Abstand unterschritten wird
public class LichtschrankeIR implements Runnable {
	
	private EV3IRSensor u;
	private float dist=1;
	
	public LichtschrankeIR (EV3IRSensor u, float dist) {
		this.u=u;
		this.dist=dist;
	}
	
	@Override
	public void run () {
    	SampleProvider distance = u.getDistanceMode();
    	SampleProvider average = new MeanFilter (distance, 5);
    	float [] sample = new float [average.sampleSize()];
    	average.fetchSample(sample, 0);
    	
		while (dist < sample [0]){
        	average.fetchSample(sample, 0);
		}
		
		Thread.currentThread().interrupt();
	}

}
