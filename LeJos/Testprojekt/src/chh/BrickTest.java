package chh;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.hardware.DeviceException;
import lejos.hardware.port.PortException;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;

public class BrickTest {

	public static void main(String[] args) {
		
		// Nullinitialisierung damit im finally-Block auf korrekte Initialisierung geprüft werden kann.
		RemoteEV3 b110 = null;
		RMIRegulatedMotor lineToEnd = null;
		
		try {
			// init
			b110 = new RemoteEV3 ("192.168.0.110");
			lineToEnd = b110.createRegulatedMotor("A", 'M');

			// control
			lineToEnd.setSpeed(350);
			lineToEnd.forward();
			Thread.sleep(2000);
			lineToEnd.stop(false);
		
		} catch (DeviceException e) {
			System.out.println("Port init failed.");
			
		} catch (PortException e) {
			System.out.println("Motor init failed.");
			
		} catch (InterruptedException e) {
			System.out.println("Thread was interrupted.");
			
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			System.out.println("Brick init failed.");
			
		} finally {
			
			// Wenn korrekt initialisiert werden konnte, schließe im finally Block
			if (lineToEnd != null) {
				System.out.println("Closing");
				try {
					lineToEnd.close();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}	
		}
		
		
		
	}

}
