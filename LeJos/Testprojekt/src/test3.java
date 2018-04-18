import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;

public class test3 {
	
	static RemoteEV3 b106;
	
	static	RMIRegulatedMotor b106a;
	static	RMIRegulatedMotor b106b;
	static	RMIRegulatedMotor b106c;
	static	RMIRegulatedMotor b106d;
	static Port b106port1;
	static EV3TouchSensor b1061;
	
	public static void main(String args []) throws RemoteException {
		
		try {
			b106 = new RemoteEV3 ("192.168.0.106");
		} catch (RemoteException | MalformedURLException | NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
		 b106a = b106.createRegulatedMotor("A", 'L');
		 b106b = b106.createRegulatedMotor("B", 'L');
		 b106d = b106.createRegulatedMotor("D", 'L');
		 
		 
		 b106a.backward();
		 b106d.forward();
		 b106b.forward();
		 
		 b106port1 = b106.getPort("S1");
		 b1061 = new EV3TouchSensor (b106port1); 
		 
		 
		 float [] Sensorarray = new float [b1061.sampleSize()];
		 
		 
		 while( true ) {
			 
			b1061.fetchSample(Sensorarray, 0);					// schreibt Sensorwert in Array [0]
			if(Sensorarray[0] == 0) {							// wenn array stelle 1 = 0 solange laufe
				
				System.out.print("Button pushed");
				b106a.stop(true);
				b106d.stop(true);
				b106b.stop(true);
				
				break;
			}
		 }
		 
		 
		 
		 
		 
	}


}
