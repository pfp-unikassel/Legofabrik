import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RMISampleProvider;
import lejos.remote.ev3.RemoteEV3;

public class innsertTest {
	
	static RemoteEV3 b106;
	
	static	RMIRegulatedMotor b106a;
	static	RMIRegulatedMotor b106b;
	static	RMIRegulatedMotor b106d;
	static Port b106port1;
	static EV3TouchSensor b1061;
	
	public static void main(String args []) throws RemoteException {
		
		try {
			b106 = new RemoteEV3 ("192.168.0.115");
		} catch (RemoteException | MalformedURLException | NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		b106a = b106.createRegulatedMotor("A", 'L');
		
		
		//----------------------------------------------- Values to change ---------------
		
		b106a.setSpeed(100);
	b106a.forward();
		//b106a.backward();
		
		//b106a.rotate(360);
		
//--------------------------------------------------------		
		
		try {
			Thread.sleep(30000);     // set runtime 1000=1 sec
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		b106a.close();
		
		
		
		
		
		
		
//		RMISampleProvider b1054 = b105.createSampleProvider("S4", "lejos.hardware.sensor.EV3TouchSensor", null);
//	
//		 float[] Sensorarray = new float[5];
//		
//		 
//		 while(true) {
//			 
//			b1054.fetchSample();					// schreibt Sensorwert in Array [0]
//			Sensorarray = b1054.fetchSample();
//			System.out.println(Sensorarray[0]);
//			
//		 }
		 

		
		 
		 
		 
	}


}
