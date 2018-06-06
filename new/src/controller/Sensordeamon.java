package controller;

import java.rmi.RemoteException;

import lejos.hardware.sensor.BaseSensor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.remote.ev3.RMISampleProvider;
import lejos.remote.ev3.RemoteEV3;
import lejos.robotics.Color;

public class Sensordeamon extends Thread {

	private Steuerung s;

	private RemoteEV3 b105;
	private RemoteEV3 b106;
	private RemoteEV3 b107;

	public Sensordeamon(Steuerung s, RemoteEV3 b105, RemoteEV3 b106, RemoteEV3 b107) {
		setDaemon(true); // makes this thread a deamon, closes hisself after the main thread
		this.b105 = b105;
		this.b106 = b106;
		this.b107 = b107;
		this.s = s;
	}

	@Override
	public void run() {

		RMISampleProvider b1061 = b106.createSampleProvider("S1", "lejos.hardware.sensor.EV3UltrasonicSensor", "Distance");
		RMISampleProvider b1053 = b105.createSampleProvider("S3", "lejos.hardware.sensor.EV3TouchSensor", null);
		RMISampleProvider b1054 = b105.createSampleProvider("S4", "lejos.hardware.sensor.EV3TouchSensor", null);
		RMISampleProvider b1072 = b107.createSampleProvider("S2", "lejos.hardware.sensor.EV3TouchSensor", null);
		RMISampleProvider b1073 = b107.createSampleProvider("S3", "lejos.hardware.sensor.EV3ColorSensor", "ColorID");

		s.addToSensorList( b1053);  
		s.addToSensorList(b1054);
		s.addToSensorList( b1061);
		s.addToSensorList( b1072);
		s.addToSensorList(b1073);
		
		float[] Sensorarray1 = new float[5];
		float[] Sensorarray2 = new float[5];
		float[] Sensorarray3 = new float[5];
		float[] Sensorarray4 = new float[5];
		float[] Sensorarray5 = new float[5];

		while (true) { // kontrolliere jederzeit ob einer der Sensoren etwas erkennt

			try {
					Sensorarray1 = b1061.fetchSample();
					Sensorarray2 = b1053.fetchSample();
					Sensorarray3 = b1054.fetchSample();
					Sensorarray4 = b1072.fetchSample();
					Sensorarray5 = b1073.fetchSample();	
				
				
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (Sensorarray1[0] == 1) { // wenn schalter gedrueckt wurde dann

				s.b1061Fired();
				System.out.println("Sensor b1061 fired");
				waitSek(8);
				Sensorarray1[0] = 0;
				s.resetSensorStatus();

			}
			if (Sensorarray2[0] == 1) {
				s.b1053Fired();
				System.out.println("Sensor b1053 fired");
				waitSek(8);
				Sensorarray2[0] = 0;
				s.resetSensorStatus();

			}
			if (Sensorarray3[0] == 1) {
				s.b1054Fired();
				System.out.println("Sensor b1054 fired");
				waitSek(8);
				Sensorarray3[0] = 0;
				s.resetSensorStatus();

			}
			if (Sensorarray4[0] == 1) { // counter sensor
				s.b1072Fired();
				System.out.println("Sensor b1072 fired");
				Sensorarray4[0] = 0;
				waitSek(1);
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
			 s.b1073Fired(colorString);
			 waitSek(1); // TODO: maybe turn line to sensor slow
			 s.resetSensorStatus();
			 }

		}
	}

	public void waitSek(int sekunden) {

		try {
			sleep(sekunden * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
