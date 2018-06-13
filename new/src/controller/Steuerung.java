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
import stations.Airarms;
import stations.Chargier;
import stations.Lift;
import stations.Quality;
import stations.QualityStation;
import stations.Cleaning;
import stations.Compressor;
import stations.Deliverylane;

public class Steuerung {

	RemoteEV3 b101;
	RemoteEV3 b105;
	RemoteEV3 b106;
	RemoteEV3 b107;
	RemoteEV3 b108;
	RemoteEV3 b111;
	RemoteEV3 b113;
	RemoteEV3 b114;
	RemoteEV3 b115;


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
	
	static RMIRegulatedMotor b111a;
	static RMIRegulatedMotor b111b;
	static RMIRegulatedMotor b111c;
	static RMIRegulatedMotor b111d;
	
	static RMIRegulatedMotor b113a;
	static RMIRegulatedMotor b113b;
	static RMIRegulatedMotor b113c;
	static RMIRegulatedMotor b113d;
	
	static RMIRegulatedMotor b114a;
	static RMIRegulatedMotor b114b;
	static RMIRegulatedMotor b114c;
	
	static RMIRegulatedMotor b115a;
	static RMIRegulatedMotor b115b;
	static RMIRegulatedMotor b115c;
	static RMIRegulatedMotor b115d;
	
	

	static ArrayList<RMIRegulatedMotor> openMotorPorts = new ArrayList<>(); // all
	static ArrayList<RMISampleProvider> openSensorPorts = new ArrayList<>();

	static Chargier chargier;
	static Lift lift;
	static Cleaning cleaner;
	static Quality quality;
	static Compressor compressor;
//	static Airarms airarms;
//	static Deliverylane deliverylane;
//	static QualityStation qualitystation;

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
		compressor = new Compressor(b113a,b113b,b113c,b113d);
	//	ariarms = new Airarms(b111a,b111b,b111c,b111d,b114a,b114b);		// distanzsensor
//		qualitystation = new QualityStation(b115a,b115b,b115c,b115d); // add Colorsensor
//		deliverylane = new Deliverylane(); add motors 

		Sensordeamon sensordeamon = new Sensordeamon(this, b105, b106, b107,b113); // uebergebe das Object und rufe b1073  TODO: ad 114 distanz
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
	
	public void b1131Fired(boolean button) {
		
		compressor.pressureButtonfired(button);
	}
	
	public void armIsStalled(boolean armIsStalled) {
		 // QualityStation arm is stalled boolean true stalled, false nicht
		//qulityStation.setArmIsStalled(armIsStalled);

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
//		initBrick11();
		initBrick13();
//		initBrick14();
//		initBrick15();
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
	
	public void initBrick11() {
		// Brick 111
		try {
			b111 = new RemoteEV3("192.168.0.111");
			getPowerLevel(b111);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("B1 not Found");

		}

		b111a = b111.createRegulatedMotor("A", 'M');
		b111b = b111.createRegulatedMotor("B", 'M');
		b111c = b111.createRegulatedMotor("C", 'M');
		b111d = b111.createRegulatedMotor("D", 'M');


		openMotorPorts.add(b111a);
		openMotorPorts.add(b111b);
		openMotorPorts.add(b111c);
		openMotorPorts.add(b111d);

	}
	
	public void initBrick13() {
		// Brick 113
		try {
			b113 = new RemoteEV3("192.168.0.113");
			getPowerLevel(b113);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("B1 not Found");

		}

		b113a = b113.createRegulatedMotor("A", 'L');
		b113b = b113.createRegulatedMotor("B", 'L');
		b113c = b113.createRegulatedMotor("C", 'L');
		b113d = b113.createRegulatedMotor("D", 'L');


		openMotorPorts.add(b113a);
		openMotorPorts.add(b113b);
		openMotorPorts.add(b113c);
		openMotorPorts.add(b113c);

	}
	
	public void initBrick14() {
		// Brick 114
		try {
			b114 = new RemoteEV3("192.168.0.114");
			getPowerLevel(b114);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("B1 not Found");

		}

		b114a = b114.createRegulatedMotor("A", 'M');
		b114b = b114.createRegulatedMotor("B", 'M');
		b114c = b114.createRegulatedMotor("C", 'L');

		openMotorPorts.add(b114a);
		openMotorPorts.add(b114b);
		openMotorPorts.add(b114c);
	}
	
	public void initBrick15() {
		// Brick 115
		try {
			b115 = new RemoteEV3("192.168.0.115");
			getPowerLevel(b115);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("B1 not Found");

		}

		b115a = b115.createRegulatedMotor("A", 'M');
		b115b = b115.createRegulatedMotor("B", 'M');
		b115c = b115.createRegulatedMotor("C", 'M');
		b115d = b115.createRegulatedMotor("D", 'M');

		openMotorPorts.add(b115a);
		openMotorPorts.add(b115b);
		openMotorPorts.add(b115c);
		openMotorPorts.add(b115d);
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

					cleaner.startCleaner(true); //TODO: maybe falls = andere richtung
					cleaner.startLiftLine(true); //TODO: maybe falls = andere richtung
					
					chargier.startLineToLifter(true);
					chargier.startTableLine(true);
					
					 quality.startCounterLine(false); 
					 quality.startLine(true);

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
					
					Thread.sleep(10000); // wait 10 sec
					
					cleaner.stopLiftLine();
					cleaner.stop();
					quality.stopCounterLine();
					quality.stopLine();

					

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
