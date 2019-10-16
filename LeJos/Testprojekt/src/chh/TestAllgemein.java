package chh;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.remote.ev3.RMIEV3;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;
import lejos.remote.ev3.RemoteMotorPort;

public class TestAllgemein {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long currentTime = System.nanoTime();
		for (int i = 0; i < 100; i++);
		System.out.println(System.nanoTime() - currentTime);
		
		try {
			// new RemoteEV3("192.168.0.107")
			RemoteEV3 lebrick = new RemoteEV3("192.168.0.107");
			System.out.println(Naming.lookup("//192.168.0.107/RemoteEV3"));
			RMIEV3 rmiEV3 = (RMIEV3)Naming.lookup("//192.168.0.107/RemoteEV3");
			RMIRegulatedMotor m = lebrick.createRegulatedMotor("A", 'L');
			// b107a = b107.createRegulatedMotor("A", 'L');
			// RemoteMotorPort pp = new RemoteMotorPort(rmiEV3);
			// pp.close();
			m.close();
			// RMIRegulatedMotor m = lebrick.createRegulatedMotor("A", 'M');
			System.out.println(lebrick.getPort("A"));
			System.out.println("Engine created.");
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
