package controller;

import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.Color;

public class Sensordeamon extends Thread {
	
	private Steuerung s;
	private EV3UltrasonicSensor b1061;
	private EV3TouchSensor b1053;
	private EV3TouchSensor b1054;
	private EV3TouchSensor b1072;
	private EV3ColorSensor b1071;
	
	public Sensordeamon(){
		setDaemon(true);  // makes this thread a deamon, closes hisself after the main thread
	}
	
	public Sensordeamon(Steuerung s,EV3UltrasonicSensor u,EV3TouchSensor s1 ,EV3TouchSensor s2, EV3TouchSensor s3,EV3ColorSensor s4){ 
		
		setDaemon(true);  // makes this thread a deamon, closes hisself after the main thread
		
		this.s= s;
		this.b1061 = u;
		this.b1053 = s1;
		this.b1054 = s2;
		this.b1072 = s3;
		this.b1071 = s4;
	}

	@Override
	public void run() {
		
		SensorMode color = b1071.getColorIDMode();
		
		 float [] Sensorarray1 = new float [b1061.sampleSize()];
		 float [] Sensorarray2 = new float [b1053.sampleSize()];
		 float [] Sensorarray3 = new float [b1054.sampleSize()];
		 float [] Sensorarray4 = new float [b1072.sampleSize()];
		 float[] Sensorarray5 = new float [color.sampleSize()];
		 
		while(true) { 		// kontrolliere jederzeit ob einer der Sensoren etwas erkennt
			
			b1061.fetchSample(Sensorarray1, 0);		// aktualisiere werte in [0] vom Array
			b1053.fetchSample(Sensorarray2, 0);
			b1054.fetchSample(Sensorarray3, 0);
			b1072.fetchSample(Sensorarray4, 0);
			color.fetchSample(Sensorarray5, 0);
			
			
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
			if(Sensorarray4[0]==1 ){     // counter sensor
				s.b1072Fired();
				System.out.println("Sensor b1072 fired");
				Sensorarray4[0] = 0;
				s.resetSensorStatus();

			}
			if(Sensorarray5[0] != -1) {
				System.out.println("Farbsensor erkennt farbe");
				int coloIndex = (int)Sensorarray5[0];
				String colorString = "";
				switch(coloIndex){
					
					case Color.BLACK: colorString = "BLACK"; break;
					case Color.BLUE: colorString = "BLUE"; break;
					case Color.GREEN: colorString = "GREEN"; break;
					case Color.YELLOW: colorString = "YELLOW"; break;
					case Color.RED: colorString = "RED"; break;
					case Color.WHITE: colorString = "WHITE"; break;
					case Color.BROWN: colorString = "BROWN"; break;					
				}
				s.b1071Fired(colorString);
				waitSek(1); // TODO: maybe turn line to sensor slow 
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
