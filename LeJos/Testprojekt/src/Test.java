import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;
import lejos.utility.Delay;

public class Test {
	public static void main (String args []) throws RemoteException, MalformedURLException, NotBoundException{
		RemoteEV3 ev3 = new RemoteEV3 ("192.168.0.107");
		RMIRegulatedMotor m = ev3.createRegulatedMotor("A",'L');
		
		m.forward();
		Delay.msDelay(5000);
		m.stop(false);
		m.close();
		System.out.println("Beenden");
	}
}
