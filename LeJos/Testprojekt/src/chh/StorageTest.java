package chh;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;

import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;
import stations.Stock;

public class StorageTest {

	static String IP_String = "192.168.0.";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RemoteEV3 b111 = null;
		RemoteEV3 b112 = null;
		RemoteEV3 b113 = null;
		
		try {
			b111 = new RemoteEV3(IP_String + "111");
			b112 = new RemoteEV3(IP_String + "112");
			b113 = new RemoteEV3(IP_String + "113");
			
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<RMIRegulatedMotor> m = new ArrayList<RMIRegulatedMotor>();
		
		RMIRegulatedMotor b111a = b111.createRegulatedMotor("A", 'L');
		RMIRegulatedMotor b111b = b111.createRegulatedMotor("B", 'L');
		RMIRegulatedMotor b111c = b111.createRegulatedMotor("C", 'L');
		RMIRegulatedMotor b111d = b111.createRegulatedMotor("D", 'L');

		RMIRegulatedMotor b112a = b112.createRegulatedMotor("A", 'L');
		RMIRegulatedMotor b112b = b112.createRegulatedMotor("B", 'L');
		RMIRegulatedMotor b112c = b112.createRegulatedMotor("C", 'L');
		RMIRegulatedMotor b112d = b112.createRegulatedMotor("D", 'L');		
		
		RMIRegulatedMotor b113a = b113.createRegulatedMotor("A", 'L');
		RMIRegulatedMotor b113b = b113.createRegulatedMotor("B", 'L');		
		m.addAll(Arrays.asList(
				b111a, b111b, b111c, b111d,
				b112a, b112b, b112c, b112d,
				b113a, b113b
		));
		
		
		final Stock stock = new Stock(null, b112a, b112d, b113a, b113b, b112c, b112b, b111a, b111b, b111c, b111d);
		
		System.out.println("Starte");
		
		boolean mode = true;
		Timer t = null;
		if (mode == true) {
			t = new Timer();
			t.schedule(new java.util.TimerTask() {
				@Override
				public void run() {
					try {
						stock.elevatorToRight(true);
						stock.elevatorUp(false);
						stock.elevatorToLeft(true);
						stock.elevatorDown(false);
						System.out.println("ich bin drin");
						this.cancel();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}, 1000);

		} else {
			stock.reset();
		}
		
		try {
			Thread.sleep(50000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		t.cancel();
		System.out.println("Schlieﬂe");
		
		for (RMIRegulatedMotor r : m)
			try {
				r.close();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

}
