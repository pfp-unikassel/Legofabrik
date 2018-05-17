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
		
		 float [] Sensorarray1 = new float [b1061.sampleSize()];
		 float [] Sensorarray2 = new float [b1053.sampleSize()];
		 float [] Sensorarray3 = new float [b1054.sampleSize()];
		 float [] Sensorarray4 = new float [b1072.sampleSize()];
		 
		while(true) { 		// kontrolliere jederzeit ob einer der Sensoren etwas erkennt
			
			b1061.fetchSample(Sensorarray1, 0);		// aktualisiere werte in [0] vom Array
			b1053.fetchSample(Sensorarray2, 0);
			b1054.fetchSample(Sensorarray3, 0);
			b1072.fetchSample(Sensorarray4, 0);
			
			if(Sensorarray1[0]==1 ){   // wenn schalter gedrueckt wurde dann
				
				s.b1061Fired();
				System.out.println("Sensor b1061 fired");
				waitSek(8);
				Sensorarray1[0] = 0;
				s.resetSensorStatus();
				
			}
			if(Sensorarray2[0]==1 ){
				s.b1053Fired();
				System.out.println("Sensor b1053 fired");
				waitSek(8);
				Sensorarray2[0] = 0;
				s.resetSensorStatus();

			}
			if(Sensorarray3[0]==1 ){
				s.b1054Fired();
				System.out.println("Sensor b1054 fired");
				waitSek(8);
				Sensorarray3[0] = 0;
				s.resetSensorStatus();

			}
			if(Sensorarray4[0]==1 ){
				s.b1072Fired();
				System.out.println("Sensor b1072 fired");
				waitSek(8);
				Sensorarray4[0] = 0;
				s.resetSensorStatus();

			}
			
		}
	}
	
	public void waitSek(int sekunden){
		
		try {
			sleep(sekunden*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}		
