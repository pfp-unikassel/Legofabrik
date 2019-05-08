import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RMISampleProvider;
import lejos.remote.ev3.RemoteEV3;
import lejos.robotics.Color;

public class Test7 {

	static RemoteEV3 b115;

	static RMIRegulatedMotor b115a;
	static RMIRegulatedMotor b115b;
	static RMIRegulatedMotor b115c;
//	static RMIRegulatedMotor b115d;
	

	public static void main(String[] args) throws InterruptedException {

		
		try {
			b115 = new RemoteEV3("192.168.0.115");
//			 b1151 = b115.createSampleProvider("S1", "lejos.hardware.sensor.EV3ColorSensor", "ColorID");
			b115a = b115.createRegulatedMotor("A", 'L');
			b115b = b115.createRegulatedMotor("B", 'M');
			b115c = b115.createRegulatedMotor("C", 'M');
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		float[] Sensorarray7 = new float[5];
	
		try {
			 
			b115a.setSpeed(720);
			b115a.forward();
//			b115b.rotate(360);
//			Thread.sleep(5000);
//			b115b.rotate(-360);
//			b115a.stop(false);
//			
//			b115a.close();
//			b115b.close();
//			b115c.close();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		while(true){
//			
//		try {
//			Sensorarray7 = b1151.fetchSample();
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		 if (Sensorarray7[0] != -1) {
//				System.out.println("Farbsensortower erkennt farbe");
//				int coloIndex = (int) Sensorarray7[0];
//				String colorString = "";
//				switch (coloIndex) {
//
//				case Color.BLACK:
//					colorString = "BLACK";
//					break;
//				case Color.BLUE:
//					colorString = "BLUE";
//					break;
//				case Color.GREEN:
//					colorString = "GREEN";
//					break;
//				case Color.YELLOW:
//					colorString = "YELLOW";
//					break;
//				case Color.RED:
//					colorString = "RED";
//					break;
//				case Color.WHITE:
//					colorString = "WHITE";
//					break;
//				case Color.BROWN:
//					colorString = "BROWN";
//					break;
//				}
//		
//				System.out.println(colorString);
//		 }
		 
//		 }
		 
		 
		 
	}

}
