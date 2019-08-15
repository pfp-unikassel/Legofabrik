package chh;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import lejos.hardware.DeviceException;
import lejos.hardware.port.PortException;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RMISampleProvider;
import lejos.remote.ev3.RemoteEV3;

public class BrickTest {
	
	static ArrayList<InetAddress> ips = new ArrayList<>();
	static ArrayList<InetAddress> failure = new ArrayList<>();
	
	// constants
	static String IPMASK = "192.168.0.";
	static int fOctet = 101;
	static int lOctet = 105;
	
	static void initIps() {
		boolean retry = true;
		if (retry) {
			for (int lastOctet = fOctet; lastOctet <= lOctet; lastOctet++) {
				String currentIp = IPMASK + lastOctet;
				try {
					System.out.println("Trying to add " + currentIp);
					InetAddress ip = InetAddress.getByName(currentIp);
					// maybe redundant
					
					if (ip.isReachable(1500)) {// milliseconds. 1000 is not long enough. 
						ips.add(ip);
					} else {
						failure.add(ip);
					}
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					System.out.println(currentIp + " was not reachable.");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("There was an IOException with " + currentIp); 
				}
			
			}
			if (failure.isEmpty())
				retry = false;
			else {
				System.out.print("Error reaching ips: ");
				for (InetAddress ip : failure)
					System.out.print(ip.getHostAddress() + " ");
				System.out.println();
				System.out.println("Do you wanna try those again? ");
				retry = false;
			}
		}
		System.out.println("Added " + ips.size() + " ips.");
	}
	
	
	public static void main(String[] args) {
		
		// we define an ip range we assume our bricks listen on
		System.out.println("Looking for bricks from " + IPMASK + fOctet + " to " + IPMASK + lOctet);
		initIps();
		
		// we found at least one ip address on our network
		if (!ips.isEmpty()) {
			
		} else System.exit(-1);

		
		ArrayList<Brick> bricks = new ArrayList<>();
		
		Engine lineToTurnTable = new Engine("Line to the turntable", "A", 'M');
		lineToTurnTable.setDirection(false);
		Engine turnTableToEnd = new Engine("Turntable to end", "D", 'M') {
			@Override
			public Runnable getSzenario() {
				return new Runnable() {
					@Override
					public void run() {
						System.out.println("Starte Motor " + desc);
						try {
							rmi.setSpeed(350);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							rmi.forward();
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						while(!stopped) {
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						try {
							rmi.stop(false);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
					
				};
			}
		};
		
		
		System.out.println(ips.size());
		
		// create a slot for every ip
		for (int x = 0; x < ips.size(); x++) {
			Brick currentBrick = new Brick(ips.get(x).getHostAddress(), null);
			bricks.add(currentBrick);
		}
		
		Sensor turnTable = new Sensor("Drehtischsensor", "S4");
		for (int x = 0; x < ips.size(); x++) {
			String currentIp = ips.get(x).getHostAddress();
			Brick currentBrick = bricks.get(x);
			System.out.println("Working on " + currentIp);
			switch(currentIp) {
			case "192.168.0.101":
				
				break;
			case "192.168.0.102":
				
				currentBrick.addSensor(turnTable);
				currentBrick.addEngine(turnTableToEnd, "");
				turnTableToEnd.addDependency(turnTable);
				break;
			case "192.168.0.103":
				currentBrick.addEngine(lineToTurnTable, "");
				
				lineToTurnTable.addDependency(turnTable);
				
				break;
			}
		
		}
		
		
		// if(bricks.get(0) == null) System.out.println("null");
		
		// bricks.add()
		
		// Nullinitialisierung damit im finally-Block auf korrekte Initialisierung geprüft werden kann.
		RemoteEV3 b110 = null;
		// b110.createRegulatedMotor(portName, motorType)
		
		String currentIp = "";
		
		try {
			// init
			
			for (int x = 0; x < ips.size(); x++) {
				currentIp = ips.get(x).getHostAddress();
				Brick currentBrick = bricks.get(x);
				currentBrick.init();
				currentBrick.initEngines();
				currentBrick.initSensors();
				currentBrick.startSensors();
				currentBrick.startEngines();
			}
			
			System.out.println("Schlafe");
			Thread.sleep(10000);
			if (bricks.get(1) != null)
				for (Sensor s : bricks.get(1).sensors)
					s.stopped = true;
			System.out.println("Gestoppt");
			
			// b110 = new RemoteEV3 ("192.168.0.110");
			// 
			/* lineToEnd = b110.createRegulatedMotor("A", 'M');
			
			// control
			lineToEnd.setSpeed(350);
			lineToEnd.forward();
			Thread.sleep(2000);
			lineToEnd.stop(false); */

		// Comes from RemotePort class.
		} catch (DeviceException e) {
			System.out.println("Port init failed.");
		

		} catch (PortException e) {
			System.out.println("Motor init failed.");
			
		} catch (InterruptedException e) {
			System.out.println("Thread was interrupted.");
			
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			System.out.println("Brick init failed.");
			
		} finally {
			System.out.println(currentIp);
			// Wenn korrekt initialisiert werden konnte, schließe im finally Block			
			for (Brick b : bricks) {
				try {
				System.out.println("Closing");
				b.shutdown();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			


		}
		
		
		
	}

}
