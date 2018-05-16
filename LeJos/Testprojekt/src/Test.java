import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;
import lejos.utility.Delay;

public class Test {
	public static void main (String args []) throws RemoteException, MalformedURLException, NotBoundException{
		
		RemoteEV3 b105 = new RemoteEV3 ("192.168.0.105");
		 Port b105port1 = b105.getPort("S4");		;
		 EV3TouchSensor b1051 = new EV3TouchSensor (b105port1);
		 
		 
		 float [] Sensorarray = new float [b1051.sampleSize()];
		
		 
		 while(Sensorarray[0] != 1.0 ) {
			 
			b1051.fetchSample(Sensorarray, 0);					// schreibt Sensorwert in Array [0]
			System.out.println(Sensorarray[0]);
		 
		 
		 }
		 
		 System.out.println("Test erfolgreich ");
		 b1051.close();
		
	}
}
