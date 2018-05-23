import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;
import lejos.robotics.Color;

public class test4 {
	
	static RemoteEV3 b107;

	static RMIRegulatedMotor b107a;
	static RMIRegulatedMotor b107b;
	static RMIRegulatedMotor b107c;
	static RMIRegulatedMotor b107d;


	static Port b107port2;
	static Port b107port3;

	static EV3TouchSensor b1072; 
	static EV3ColorSensor b1073 ; 
	
	
	public static void main( String[]Args) {
		
		
		try {
			b107 = new RemoteEV3("192.168.0.107");
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("B1 not Found");

		}

		b107b = b107.createRegulatedMotor("B", 'M');
		b107c = b107.createRegulatedMotor("C", 'L');
		b107d = b107.createRegulatedMotor("D", 'L');
		b107port2 = b107.getPort("S2");
		b1072 = new EV3TouchSensor(b107port2); // Sensor Zähler
		
		b107port3 = b107.getPort("S3");
		b1073 = new EV3ColorSensor(b107port3);
		
		SensorMode color = b1073.getColorIDMode();
		float[] Sensorarray5 = new float [color.sampleSize()];
		
		while(true) {
			color.fetchSample(Sensorarray5, 0);
			
			
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
				System.out.println(colorString);
				
			}
		}
		
		
	
	
	}

}
