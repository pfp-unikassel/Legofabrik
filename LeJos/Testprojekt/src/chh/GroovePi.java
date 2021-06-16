package chh;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;

public class GroovePi {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RemoteEV3 brick = null;
		
		try {
			brick = new RemoteEV3("192.168.0.102");
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (brick != null) {
			RMIRegulatedMotor drehrad = brick.createRegulatedMotor("A", 'M');
			try {
				System.out.println(drehrad.getSpeed());
				drehrad.setSpeed(50);
				drehrad.forward();
				Thread.sleep(10000);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				drehrad.close();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		
	}

}
