import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;
import lejos.utility.Delay;
import stations.Airarms;

public class Test {
	public static void main (String args []) throws RemoteException, MalformedURLException, NotBoundException{
		
		RemoteEV3 b105 = new RemoteEV3 ("192.168.0.111");
		
		RMIRegulatedMotor b105a = b105.createRegulatedMotor("A", 'M');
		RMIRegulatedMotor b105b = b105.createRegulatedMotor("B", 'M');
		RMIRegulatedMotor b105c = b105.createRegulatedMotor("C", 'M');
		RMIRegulatedMotor b105d = b105.createRegulatedMotor("D", 'M');
		
		Airarms air = new Airarms(b105a,b105b,b105c,b105d);
		
		air.armUp();
		
		 
		 
		
		
	}
}
