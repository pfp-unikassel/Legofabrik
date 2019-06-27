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
		
		RemoteEV3 b110 = new RemoteEV3 ("192.168.0.110");
		 
		 RMIRegulatedMotor linetoEnd = b110.createRegulatedMotor("A", 'M');
		 RMIRegulatedMotor gateB  = b110.createRegulatedMotor("B", 'M');
		 RMIRegulatedMotor gateC = b110.createRegulatedMotor("C", 'M');
		 RMIRegulatedMotor gateD = b110.createRegulatedMotor("D", 'M');
		
		 
		 
		 //--------------------------------------------------------------------
		 int turnDegree = 45;;  
		 
		linetoEnd.setSpeed(350);  // set speed degree per second
		
		gateD.rotate(turnDegree);
		linetoEnd.backward();
				 
		 System.out.println("sollte sich drehen");
		
	   try {
		   
		Thread.sleep(5000);    
		// Time to wait
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	   linetoEnd.stop(false);
	   gateD.rotate(-turnDegree);
	   
		linetoEnd.close();
		gateC.close();
		gateB.close();
		gateD.close();
		 System.out.println("fertig");
	}
}
