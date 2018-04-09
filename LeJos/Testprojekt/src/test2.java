import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;
import lejos.utility.Delay;

public class test2 {

	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		
		RemoteEV3 ev3 = new RemoteEV3 ("192.168.0.107");
		RMIRegulatedMotor m = ev3.createRegulatedMotor("A", 'L');
		System.out.println("Rotiere");
		m.rotate(1020);
		System.out.println(m.getLimitAngle());
		Delay.msDelay(1000);
		m.rotate(-1020);
		System.out.println(m.getLimitAngle());
		System.out.println("Beende");
		
		m.close();

	}

}
