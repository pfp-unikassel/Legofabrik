package controller;

import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class Sensordeamon extends Thread {
	
	private Steuerung s;
	private EV3UltrasonicSensor b1061;
	private EV3TouchSensor b1053;
	private EV3TouchSensor b1054;
	private EV3TouchSensor b1072;
	
	public Sensordeamon(){
		setDaemon(true);  // makes this thread a deamon, closes hisself after the main thread
	}
	
	public Sensordeamon(Steuerung s,EV3UltrasonicSensor u,EV3TouchSensor s1 ,EV3TouchSensor s2, EV3TouchSensor s3){
		
		setDaemon(true);  // makes this thread a deamon, closes hisself after the main thread
		
		this.s= s;
		this.b1061 = u;
		this.b1053 = s1;
		this.b1054 = s2;
		this.b1072 = s3;
	}

	@Override
	public void run() {
		
		while(true) { 		// kontrolliere jederzeit ob einer der Sensoren etwas erkennt
			
			//hole werte der Sensoren und wenn true dann s.Sensorfired();
			System.out.println("Deamon alive");
		}
	}
}
