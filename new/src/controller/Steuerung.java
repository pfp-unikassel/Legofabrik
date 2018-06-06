package controller;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.BaseSensor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RMISampleProvider;
import lejos.remote.ev3.RemoteEV3;
import stations.Chargier;
import stations.Lift;
import stations.Quality;
import stations.Cleaning;

public class Steuerung {

	RemoteEV3 b101;
	RemoteEV3 b105;
	RemoteEV3 b106;
	RemoteEV3 b107;
	RemoteEV3 b108;

	static RMIRegulatedMotor b101a;
	static RMIRegulatedMotor b101b;
	static RMIRegulatedMotor b101c;
	static RMIRegulatedMotor b101d;

	static RMIRegulatedMotor b105a;
	static RMIRegulatedMotor b105b;
	static RMIRegulatedMotor b105c;
	static RMIRegulatedMotor b105d;

	static RMIRegulatedMotor b106a;
	static RMIRegulatedMotor b106b;
	static RMIRegulatedMotor b106c;
	static RMIRegulatedMotor b106d;

	static RMIRegulatedMotor b107a;
	static RMIRegulatedMotor b107b;
	static RMIRegulatedMotor b107c;
	static RMIRegulatedMotor b107d;

	static RMIRegulatedMotor b108a;
	static RMIRegulatedMotor b108b;
	static RMIRegulatedMotor b108c;
	static RMIRegulatedMotor b108d;

	static ArrayList<RMIRegulatedMotor> openMotorPorts = new ArrayList<>(); // all
	static ArrayList<RMISampleProvider> openSensorPorts = new ArrayList<>();

	static Chargier chargier;
	static Lift lift;
	static Cleaning cleaner;
	static Quality quality;

	private boolean b1053Status = false; // set True if button fires
	private boolean b1054Status = false;
	private boolean b1061Status = false;
	private boolean b1072Status = false;

	public static void main(String[] args) throws RemoteException {

	}

	public void start() {

		initAll();
		System.out.println("steuerung start");

		chargier = new Chargier(// b1061, b1054, b1053,
				b106a, b106d, b106b, b105d, b105c);
		lift = new Lift(b101a, b101b, b101c, b101d, b108a);
		cleaner = new Cleaning(b108b, b108c);
		quality = new Quality(b107c, b107b, b107d);

		Sensordeamon sensordeamon = new Sensordeamon(this, b105, b106, b107); // uebergebe das Object und rufe b1073
		sensordeamon.start();
	}

	public void b1053Fired() { // lift schalter
		chargier.touchLiftfired();
		b1053Status = true;
	}

	public void b1054Fired() { // drehtischschalter
		chargier.touchTablefired();
		b1054Status = true;
	}

	public void b1061Fired() { // ultraschall sensor
		chargier.schrankefired();
		b1061Status = true;
	}

	public void b1072Fired() {

		quality.counterSensorFired();
		b1072Status = true;
	}

	public void b1073Fired(String colorString) {

		quality.colorSensorFired(colorString);
	}

	public void resetSensorStatus() {
		b1053Status = false;
		b1053Status = false;
		b1053Status = false;
		b1053Status = false;
		quality.resetColorString();
	}

	public void initAll() {
		System.out.println("init All");

		initBrick1();
		initBrick5();
		initBrick6();
		initBrick7();
		initBrick8();
	}

	public void initBrick1() {
		// Brick 101
		try {
			b101 = new RemoteEV3("192.168.0.101");
			getPowerLevel(b101);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("B1 not Found");
		}
		b101a = b101.createRegulatedMotor("A", 'M');
		b101b = b101.createRegulatedMotor("B", 'M');
		b101c = b101.createRegulatedMotor("C", 'M');
		b101d = b101.createRegulatedMotor("D", 'M');

		openMotorPorts.add(b101a);
		openMotorPorts.add(b101b);
		openMotorPorts.add(b101c);
		openMotorPorts.add(b101d);
	}

	public void initBrick5() {

		try {
			b105 = new RemoteEV3("192.168.0.105");
			getPowerLevel(b105);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("B1 not Found");
		}

		b105c = b105.createRegulatedMotor("C", 'L'); // Motor Drehtisch
		b105d = b105.createRegulatedMotor("D", 'L'); // Motor R‰der Drehtisch

		openMotorPorts.add(b105c);
		openMotorPorts.add(b105d);
	}

	public void initBrick6() {

		try {
			b106 = new RemoteEV3("192.168.0.106");
			getPowerLevel(b106);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("B1 not Found");

		}

		b106a = b106.createRegulatedMotor("A", 'L'); // Laufband zum Drehtisch
		b106b = b106.createRegulatedMotor("B", 'L'); // Laufband vom Drehtisch
		b106d = b106.createRegulatedMotor("D", 'L'); // Laufband zur
														// Kippvorrichtung)

		openMotorPorts.add(b106a);
		openMotorPorts.add(b106b);
		openMotorPorts.add(b106d);

	}

	public void initBrick7() {
		try {
			b107 = new RemoteEV3("192.168.0.107");
			getPowerLevel(b107);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("B1 not Found");

		}

		b107b = b107.createRegulatedMotor("B", 'M');
		b107c = b107.createRegulatedMotor("C", 'L');
		b107d = b107.createRegulatedMotor("D", 'L');

		openMotorPorts.add(b107b);
		openMotorPorts.add(b107c);
		openMotorPorts.add(b107d);

	}

	public void initBrick8() {
		// Brick 108
		try {
			b108 = new RemoteEV3("192.168.0.108");
			getPowerLevel(b108);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("B1 not Found");

		}

		b108a = b108.createRegulatedMotor("A", 'M');
		b108b = b108.createRegulatedMotor("B", 'L');
		b108c = b108.createRegulatedMotor("C", 'M');

		openMotorPorts.add(b108a);
		openMotorPorts.add(b108b);
		openMotorPorts.add(b108c);
	}

	public void addToSensorList(RMISampleProvider s) {

		openSensorPorts.add(s);
	}

	public ArrayList<RMISampleProvider> getSensorList() {

		return openSensorPorts;
	}

	public static void closePorts() {

		for (RMIRegulatedMotor temp : openMotorPorts) { // close every open
														// Motor
			try {
				temp.close();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				System.out.println(temp.toString() + " could not be closed");
				e.printStackTrace();
			}
		}

		for (RMISampleProvider temp1 : openSensorPorts) { // close every sensor
															// in Sensorlist
			try {
				temp1.close();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				System.out.println("Sensor port konnte nicht geschloﬂen werden");
				e.printStackTrace();
			}
		}
		openSensorPorts.clear();
	}

	public Chargier getChargier() {
		return chargier;
	}

	public Lift getLift() {
		return lift;
	}

	public Cleaning getCleaner() {
		return cleaner;
	}

	public Quality getQuality() {
		return quality;
	}

	public float getPowerLevel(RemoteEV3 brick) {

		System.out.println(brick.getName() + " hat noch " + brick.getPower().getBatteryCurrent() + " Akku");

		// TODO: PoverLevel in % umrechnen, wenn unter wert x dann alarm
		return brick.getPower().getBatteryCurrent();
	}

	public void startSzenario1() {

		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override
			public void run() {
				try {
					chargier.resetTable();
					chargier.startLineToTable(false);
					chargier.startTableLine(true);

					// wait till Table Button is pushed, test maybe Ui freezes
					while (!b1054Status) {
						System.out.println("h‰nge in schleife 1");
					}

					chargier.stopLineToTable();
					chargier.stopTableLine();
					chargier.turnTable(660);

					chargier.startLineToLifter(false);
					chargier.startTableLine(false);

					while (!b1053Status) {
						System.out.println("h‰nge in schleife 2");
					}

					chargier.stopLineToLifter();
					chargier.stopTableLine();

					lift.startShaker();
					lift.start(); // wait until it finished
					lift.stopShaker();

					chargier.startLineToLifter(true);
					chargier.startTableLine(true);

					while (!b1054Status) { // wait table button pushed
						System.out.println("h‰nge in schleife 3");
					}

					chargier.stopLineToLifter();
					chargier.stopTableLine();

					chargier.turnTable(-1320);

					chargier.startLineToStore(true); // maybe falls
					chargier.startTableLine(false);

					Thread.sleep(2000);
					chargier.stopTableLine();
					chargier.stopLineToStorer();

					chargier.resetTable(); // turns 660 to much repair later

					 quality.startCounterLine(true); 
					 quality.startLine(false);

				} catch (RemoteException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, 1000);

	}

	public void startSzenario2() {

		 try {
			quality.startCounterLine(false);
			 quality.startLine(true);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		

	}

	public void startSzenario3() {

		 try {
				quality.stopCounterLine();
				 quality.stopLine();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	}

}
