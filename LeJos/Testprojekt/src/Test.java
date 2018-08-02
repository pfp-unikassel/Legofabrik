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
		
		RemoteEV3 b105 = new RemoteEV3 ("192.168.0.115");
		
		 RMIRegulatedMotor table = b105.createRegulatedMotor("A", 'M');
		 RMIRegulatedMotor armVertical  = b105.createRegulatedMotor("B", 'M');
		 RMIRegulatedMotor armHorizontal = b105.createRegulatedMotor("C", 'M');
		 RMIRegulatedMotor tower = b105.createRegulatedMotor("D", 'M');
		
		 
//		 tower.rotate(360,false);
		 
//		 table.rotate(50, false); 										// True = programm laeuft direkt weiter, false wartet er bis er feritg ist 
		//gut Seite
		armHorizontal.rotate(300 , false); 
		 //Ball Aufheben
		 //armVertical.rotate(-350, false);
		 //Ball Ablegen
		 //armVertical.rotate(200, false);
		 //ausschuss Seite
		//armHorizontal.rotate(250 , false);
		
		table.close();
		armHorizontal.close();
		armVertical.close();
		tower.close();
	}
}
