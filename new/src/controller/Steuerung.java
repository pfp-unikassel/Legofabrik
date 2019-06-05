package controller;

/**
 *  wird vom Controller des Ui´s initialisiert.
 *  Steuerung ist das Hauptprogramm welches alle anderen subprogramme verbindet und steuert.
 *	Es Initialisiert die Lego Bricks, hier müssen auch die Ip´s hinterlegt werden.
 * 	Initialisiert ebenfalls den SensorDeamon, die Station und den LegoClient
 * 
 */

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import communication.BrickConfig;
import communication.LegoClient;
import javafx.scene.control.Label;
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
import stations.Car;
import stations.Chargier;
import stations.Lift;
import stations.Quality;
import stations.QualityStation;
import stations.Stock;
import userInterface.Controller;
import stations.Cleaning;
import stations.Compressor;
import stations.Deliverylane;
import stations.FillStation;

public class Steuerung {

	RemoteEV3 b101;
	RemoteEV3 b102;
	RemoteEV3 b103;
	RemoteEV3 b104;
	RemoteEV3 b105;
	RemoteEV3 b106;
	RemoteEV3 b107;
	RemoteEV3 b108;
	RemoteEV3 b109;
	RemoteEV3 b110;
	RemoteEV3 b111;
	RemoteEV3 b112;
	RemoteEV3 b113;
	RemoteEV3 b114;
	// RemoteEV3 b120;

	static RMIRegulatedMotor b101a;
	static RMIRegulatedMotor b101b;
	static RMIRegulatedMotor b101c;
	static RMIRegulatedMotor b101d;

	static RMIRegulatedMotor b102a;
	static RMIRegulatedMotor b102b;
	static RMIRegulatedMotor b102c;
	static RMIRegulatedMotor b102d;

	static RMIRegulatedMotor b103a;
	static RMIRegulatedMotor b103b;
	static RMIRegulatedMotor b103c;
	static RMIRegulatedMotor b103d;

	static RMIRegulatedMotor b104a;
	static RMIRegulatedMotor b104b;
	static RMIRegulatedMotor b104c;
	static RMIRegulatedMotor b104d;

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

	static RMIRegulatedMotor b109a;
	static RMIRegulatedMotor b109b;
	static RMIRegulatedMotor b109c;
	static RMIRegulatedMotor b109d;

	static RMIRegulatedMotor b110a;
	static RMIRegulatedMotor b110b;
	static RMIRegulatedMotor b110c;
	static RMIRegulatedMotor b110d;

	static RMIRegulatedMotor b111a;
	static RMIRegulatedMotor b111b;
	static RMIRegulatedMotor b111c;
	static RMIRegulatedMotor b111d;

	static RMIRegulatedMotor b112a;
	static RMIRegulatedMotor b112b;
	static RMIRegulatedMotor b112c;
	static RMIRegulatedMotor b112d;

	static RMIRegulatedMotor b113a;
	static RMIRegulatedMotor b113b;
	
	static RMIRegulatedMotor b114a;
	static RMIRegulatedMotor b114b;
	static RMIRegulatedMotor b114c;
	static RMIRegulatedMotor b114d;

	static ArrayList<RMIRegulatedMotor> openMotorPorts = new ArrayList<>(); // all
	static ArrayList<RMISampleProvider> openSensorPorts = new ArrayList<>();
	static ArrayList<RemoteEV3> bricks = new ArrayList<>();
	private ArrayList<String> brickIps = new ArrayList<>();

	static Chargier chargier;
	static Lift lift;
	static Cleaning cleaner;
	static Quality quality;
	static Compressor compressor;
	static Airarms airarms;
	static Deliverylane deliverylane;
	static QualityStation qualitystation;
	static Stock stock;
	static FillStation fillStation;
	private Sensordeamon sensordeamon;
	static Car car;

	static BrickConfig config;

	private boolean b1053Status = false; // set True if button fires
	private boolean b1054Status = false;
	private boolean b1061Status = false;
	private boolean b1072Status = false;

	private Controller c;
	private static LegoClient legoClient;
	private boolean twinConnection = false;
	private String lastRecivedMessage;
	private int sendErrorCounter = 0;
	private int numberOfSendTrys = 5;
	private int szenario = 0;

	public Steuerung(Controller c) {
		this.c = c;
		start(c);
	}

	// public static void main(String[] args) throws RemoteException {
	//
	// }

	public void start(Controller c1) {

		/**
		 *   holt ips aus Datei
		 * @param Controller des main UI
		 */

		c = c1;

		config = new BrickConfig(this);
		getBrickIpsFromConfig();

	}

	// ------------init--------------------------------------------------

	public void connectBricks() {
		/**
		 * initialisiert jede Station
		 * startet den Sensor deamon
		 * laedt akkustaende
		 * */
		initAll();
		System.out.println("Connected");

		chargier = new Chargier(this, b103a, b103d, b103b, b102d, b102c);
		lift = new Lift(this, b101a, b101b, b101c, b101d, b105a);
		cleaner = new Cleaning(this, b105b, b105c);
		quality = new Quality(this, b104c, b104b, b104d);
		compressor = new Compressor(this, b107a, b107b, b107c, b107d);
		airarms = new Airarms(this, b106a, b106b, b106c, b106d, b108a, b108b); // distanzsensor
		qualitystation = new QualityStation(this, b109a, b109b, b109c, b109d);
		deliverylane = new Deliverylane(this, b110a, b110b, b110c, b110d, b108c);
		stock = new Stock(this, b112a, b112d, b113a, b113b, b112c, b112b, b111a, b111b, b111c, b111d);
		fillStation = new FillStation(this, b102a);
//		car = new Car(this,b114a,b114b,b114c);

		startSensordeamon();

		updatePowerLevel();
		resetDigitalTwin(); // brings digital twin in start position
		sendPowerLevels(); // sends brick powerlevel to dig twin

	}

	public void disconnectBricks() { /* */

		reset();
		closeSensordeamon();
		closePorts();
		System.out.println("DISCONNECTED");

	}

	private void startSensordeamon() {
		sensordeamon = new Sensordeamon(this, b102, b103, b104, b107, b109);
		sensordeamon.start();
	}

	private void closeSensordeamon() {

		sensordeamon.setStoper(true);
	}

	private void reset() {
		// TODO add every reset method from every station

	}
	
	public void initAll() {
		/**
		 *  Initialisiert
		 *  alle Bricks wenn ip in Datei steht wird diese genommen, wenn nicht
		 *  die im code definierte
		 */
		System.out.println("init All");

		initBrick1();
		initBrick2();
		initBrick3();
		initBrick4();
		initBrick5();
		initBrick6();
		initBrick7();
		initBrick8();
		initBrick9();
		initBrick10();
		initBrick11();
		initBrick12();
		initBrick13();
//		initBrick14();

	}

	public void initBrick1() {
		/**
		 * initialisiert Brick1 mit der Ip aus Datei falls vorhanden und speichert ihn in der Brick liste
		 * initialisiert Motoren des Bricks und speichert diese in der Motor Arraylist
		 * */
		// Brick 101
		try {
			if (getBrickIps().get(0) != null) {
				b101 = new RemoteEV3(getBrickIps().get(0));
			} else
				b101 = new RemoteEV3("192.168.0.101"); // hier muessen alle Brick Ips eingetragen werden
			getPowerLevel(b101);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closePorts();
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

		bricks.add(b101);
	}

	public void initBrick2() {

		try {
			if (getBrickIps().get(1) != null) {
				b102 = new RemoteEV3(getBrickIps().get(1));
			} else
				b102 = new RemoteEV3("192.168.0.102");
			getPowerLevel(b102);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closePorts();
			System.out.println("B5 not Found");
		}

		b102a = b102.createRegulatedMotor("A", 'L'); // Motor Fillstation
		b102c = b102.createRegulatedMotor("C", 'L'); // Motor Drehtisch
		b102d = b102.createRegulatedMotor("D", 'L'); // Motor Räder Drehtisch

		openMotorPorts.add(b102a);
		openMotorPorts.add(b102c);
		openMotorPorts.add(b102d);

		bricks.add(b102);
	}

	public void initBrick3() {

		try {
			if (getBrickIps().get(2) != null) {
				b103 = new RemoteEV3(getBrickIps().get(2));
			} else
				b103 = new RemoteEV3("192.168.0.103");
			getPowerLevel(b103);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closePorts();
			System.out.println("B6 not Found");

		}

		b103a = b103.createRegulatedMotor("A", 'L'); // Laufband zum Drehtisch
		b103b = b103.createRegulatedMotor("B", 'L'); // Laufband vom Drehtisch
		b103d = b103.createRegulatedMotor("D", 'L'); // Laufband zur
														// Kippvorrichtung)

		openMotorPorts.add(b103a);
		openMotorPorts.add(b103b);
		openMotorPorts.add(b103d);

		bricks.add(b103);
	}

	public void initBrick4() {
		try {
			if (getBrickIps().get(3) != null) {
				b104 = new RemoteEV3(getBrickIps().get(3));
			} else
				b104 = new RemoteEV3("192.168.0.104");
			getPowerLevel(b104);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closePorts();
			System.out.println("B7 not Found");

		}

		b104b = b104.createRegulatedMotor("B", 'M');
		b104c = b104.createRegulatedMotor("C", 'L');
		b104d = b104.createRegulatedMotor("D", 'L');

		openMotorPorts.add(b104b);
		openMotorPorts.add(b104c);
		openMotorPorts.add(b104d);

		bricks.add(b104);
	}

	public void initBrick5() {
		// Brick 108
		try {
			if (getBrickIps().get(4) != null) {
				b105 = new RemoteEV3(getBrickIps().get(4));
			} else
				b105 = new RemoteEV3("192.168.0.105");
			getPowerLevel(b105);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closePorts();
			System.out.println("B8 not Found");

		}

		b105a = b105.createRegulatedMotor("A", 'M');
		b105b = b105.createRegulatedMotor("B", 'L');
		b105c = b105.createRegulatedMotor("C", 'M');

		openMotorPorts.add(b105a);
		openMotorPorts.add(b105b);
		openMotorPorts.add(b105c);

		bricks.add(b105);
	}

	public void initBrick6() {
		// Brick 111
		try {
			if (getBrickIps().get(5) != null) {
				b106 = new RemoteEV3(getBrickIps().get(5));
			} else
				b106 = new RemoteEV3("192.168.0.106");
			getPowerLevel(b106);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closePorts();
			System.out.println("B11 not Found");

		}

		b106a = b106.createRegulatedMotor("A", 'M');
		b106b = b106.createRegulatedMotor("B", 'M');
		b106c = b106.createRegulatedMotor("C", 'M');
		b106d = b106.createRegulatedMotor("D", 'M');

		openMotorPorts.add(b106a);
		openMotorPorts.add(b106b);
		openMotorPorts.add(b106c);
		openMotorPorts.add(b106d);

		bricks.add(b106);
	}

	public void initBrick7() {
		// Brick 113
		try {
			if (getBrickIps().get(6) != null) {
				b107 = new RemoteEV3(getBrickIps().get(6));
			} else
				b107 = new RemoteEV3("192.168.0.107");
			getPowerLevel(b107);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closePorts();
			System.out.println("B13 not Found");

		}

		b107a = b107.createRegulatedMotor("A", 'L');
		b107b = b107.createRegulatedMotor("B", 'L');
		b107c = b107.createRegulatedMotor("C", 'L');
		b107d = b107.createRegulatedMotor("D", 'L');

		openMotorPorts.add(b107a);
		openMotorPorts.add(b107b);
		openMotorPorts.add(b107c);
		openMotorPorts.add(b107d);

		bricks.add(b107);
	}

	public void initBrick8() {
		// Brick 114
		try {
			if (getBrickIps().get(7) != null) {
				b108 = new RemoteEV3(getBrickIps().get(7));
			} else
				b108 = new RemoteEV3("192.168.0.108");
			getPowerLevel(b108);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closePorts();
			System.out.println("B14 not Found");

		}

		b108a = b108.createRegulatedMotor("A", 'M');
		b108b = b108.createRegulatedMotor("B", 'M');
		b108c = b108.createRegulatedMotor("C", 'L');

		openMotorPorts.add(b108a);
		openMotorPorts.add(b108b);
		openMotorPorts.add(b108c);

		bricks.add(b108);
	}

	public void initBrick9() {
		// Brick 115
		try {
			if (getBrickIps().get(8) != null) {
				b109 = new RemoteEV3(getBrickIps().get(8));
			} else
				b109 = new RemoteEV3("192.168.0.109");
			getPowerLevel(b109);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closePorts();
			System.out.println("B15 not Found");

		}

		b109a = b109.createRegulatedMotor("A", 'M');
		b109b = b109.createRegulatedMotor("B", 'M');
		b109c = b109.createRegulatedMotor("C", 'M');
		b109d = b109.createRegulatedMotor("D", 'M');

		openMotorPorts.add(b109a);
		openMotorPorts.add(b109b);
		openMotorPorts.add(b109c);
		openMotorPorts.add(b109d);

		bricks.add(b109);
	}

	public void initBrick10() {
		// Brick 116
		try {
			if (getBrickIps().get(9) != null) {
				b110 = new RemoteEV3(getBrickIps().get(9));
			} else
				b110 = new RemoteEV3("192.168.0.110");
			getPowerLevel(b110);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closePorts();
			System.out.println("B16 not Found");

		}

		b110a = b110.createRegulatedMotor("A", 'M');
		b110b = b110.createRegulatedMotor("B", 'M');
		b110c = b110.createRegulatedMotor("C", 'M');
		b110d = b110.createRegulatedMotor("D", 'M');

		openMotorPorts.add(b110a);
		openMotorPorts.add(b110b);
		openMotorPorts.add(b110c);
		openMotorPorts.add(b110d);

		bricks.add(b110);
	}

	public void initBrick11() {
		// Brick 117
		try {
			if (getBrickIps().get(10) != null) {
				b111 = new RemoteEV3(getBrickIps().get(10));
			} else
				b111 = new RemoteEV3("192.168.0.111");
			getPowerLevel(b111);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closePorts();
			System.out.println("B17 not Found");

		}

		b111a = b111.createRegulatedMotor("A", 'L');
		b111b = b111.createRegulatedMotor("B", 'L');
		b111c = b111.createRegulatedMotor("C", 'L');
		b111d = b111.createRegulatedMotor("D", 'L');

		openMotorPorts.add(b111a);
		openMotorPorts.add(b111b);
		openMotorPorts.add(b111c);
		openMotorPorts.add(b111d);

		bricks.add(b111);
	}

	public void initBrick12() {
		// Brick 118
		try {
			if (getBrickIps().get(11) != null) {
				b112 = new RemoteEV3(getBrickIps().get(11));
			} else
				b112 = new RemoteEV3("192.168.0.112");
			getPowerLevel(b112);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closePorts();
			System.out.println("B18 not Found");

		}

		b112a = b112.createRegulatedMotor("A", 'L');
		b112b = b112.createRegulatedMotor("B", 'L');
		b112c = b112.createRegulatedMotor("C", 'L');
		b112d = b112.createRegulatedMotor("D", 'L');

		openMotorPorts.add(b112a);
		openMotorPorts.add(b112b);
		openMotorPorts.add(b112c);
		openMotorPorts.add(b112d);

		bricks.add(b112);
	}

	public void initBrick13() {
		// Brick 119
		try {
			if (getBrickIps().get(12) != null) {
				b113 = new RemoteEV3(getBrickIps().get(12));
			} else
				b113 = new RemoteEV3("192.168.0.113");
			getPowerLevel(b113);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closePorts();
			System.out.println("B19 not Found");

		}

		b113a = b113.createRegulatedMotor("A", 'L');
		b113b = b113.createRegulatedMotor("B", 'L');

		openMotorPorts.add(b113a);
		openMotorPorts.add(b113b);

		bricks.add(b113);
	}
	
	public void initBrick14() {
		// Brick 114
		try {
			if (getBrickIps().get(12) != null) {
				b114 = new RemoteEV3(getBrickIps().get(13));
			} else
				b114 = new RemoteEV3("192.168.0.114");
			getPowerLevel(b113);
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closePorts();
			System.out.println("B14 not Found");

		}

		b114a = b114.createRegulatedMotor("A", 'L');
		b114b = b114.createRegulatedMotor("B", 'M');
		b114c = b114.createRegulatedMotor("C", 'M');
		
		openMotorPorts.add(b114a);
		openMotorPorts.add(b114b);
		openMotorPorts.add(b114c);

		bricks.add(b114);
	}

	public void addToSensorList(RMISampleProvider s) {

		openSensorPorts.add(s);
	}

	public ArrayList<RMISampleProvider> getSensorList() {

		return openSensorPorts;
	}

	public ArrayList<RemoteEV3> getBrickList() {

		return bricks;
	}

	public void closePorts() {
		/**
		 * schliesst
		 * alle Motorports/Sensorports der Bricks aus der Liste muss bei
		 * jedem programm Ende gemacht werden
		 */

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
				System.out.println("Sensor port konnte nicht geschloßen werden");
				e.printStackTrace();
			}
		}
		openSensorPorts.clear();
		openMotorPorts.clear();
		getBrickList().clear();
	}

	// ---Communication interactions---------------------------------

	public void setOnline() {
		/**
		 *  sorgt dafuer das Nachrichten an den Zwilling geschickt werden
		 * */
		setTwinConnection(true);
	}

	public void setOffline() {
		/**
		 *  sorgt dafuer das keine Nachrichten an den Zwilling geschickt werden
		 * */
		setTwinConnection(false);
	}

	public ArrayList<String> getBrickIpsFromConfig() {
		brickIps = config.getBrickips();
		return config.getBrickips();
	}

	public void saveBrickIps() { 
		/**
		 *  Speichert brick ips in Datai
		 * */
		
		config.setBrickips(brickIps);
		config.writeIps();
	}

	public void changeBrickIps(ArrayList<String> newBrickIps) {
		/**
		 *  tauscht alte gegen neue ips aus
		 * */
		brickIps = newBrickIps;
		saveBrickIps();
	}

	public ArrayList<String> getDefaultIps() {
		return config.getDefaultBrickips();
	}

	public void createLegoClient(String ip, int port) { // ip and port can be null in this case default values will be
														// used

		legoClient = new LegoClient(ip, port);

	}

	public void deleteLegoClient() {
		legoClient = null;
	}

	public void setTwinConnection(boolean twinConnection) {
		this.twinConnection = twinConnection;
	}

	public boolean getTwinConnection() {
		return twinConnection;
	}

	public boolean isConnected() {

		return twinConnection;
	}

	public LegoClient getLegoClient() {
		return legoClient;
	}

	public void sendPowerLevels() {

		/**
		 *  holt akkustand vom Brick und Name von Brick und sendet sie dem Zwilling
		 * */
		String message, brickName;
		String powerLevel;
		char[] c = new char[5];

		for (lejos.remote.ev3.RemoteEV3 b : getBrickList()) {

			// int akkustand = b.getPower().getVoltageMilliVolt();
			//
			// powerLevel = String.valueOf(b.getPower().getVoltageMilliVolt());
			// // int in String
			// powerLevel.getChars(0, 4, c, 0); // get first 4 chars
			brickName = b.getName();

			message = ("B1" + brickName + "-" + b.getPower().getVoltageMilliVolt()); // message
																						// looks
																						// like:
																						// B100XXX

			sendMessage(message);

		}

	}

	public void resetDigitalTwin() {
		sendMessage("ST");
	}

	public void sendMessage(String message) { // vergesse nicht vorher ein client aufzumachen

		/**sendet Nachricht an den Zwilling, Ip und Port sind in der Methode hardcoded
		 *  
		 *  @param Message as String
		 * */
		if (isConnected()) {
			new java.util.Timer().schedule(new java.util.TimerTask() {
				@Override
				public void run() {

					legoClient = new LegoClient("192.168.0.117", 33333);

					lastRecivedMessage = legoClient.sendMessage(message); // sendMessage allways returns the answer

					// if(lastRecivedMessage == null && sendErrorCounter >
					// numberOfSendTrys) { // try again
					// sendErrorCounter++;
					// sendMessage(message);
					// }
					// if(!lastRecivedMessage.equals("")) { // last message not
					// empty or null
					// System.out.println( "Erfoglreich gesendet" + message );
					// System.out.println("Empfangen" + lastRecivedMessage);
					// sendErrorCounter = 0; // after succesfully send a message
					// reset error counter
					// }
					// }else {
					// System.out.println("No Client available");
				}
			}, 10);
		}

	}

	// ---------------------Brick interactions------------------------

	public float getPowerLevel(RemoteEV3 brick) {
		/**
		 * @param RemoteEV3 legobrick
		 *  @return  Akkustand als mV float, 7V = 7000mv
		 */
		if (brick != null) {

			System.out.println(brick.getName() + " hat noch " + brick.getPower().getVoltageMilliVolt() + "V Akku");

			return brick.getPower().getVoltageMilliVolt();

		} else
			return 0;

		// TODO: PoverLevel in % umrechnen, wenn unter wert x dann alarm
	}

	public float getPowerUse(RemoteEV3 brick) {

		/**
		 * @param RemoteEV3 legobrick
		 *  @return momentanen akku verbrauch in mV
		 */
		if (brick != null) {

			System.out.println(brick.getName() + " verbraucht " + brick.getPower().getBatteryCurrent() + "Amp/s Akku"); // TODO:

			return brick.getPower().getBatteryCurrent();

		} else
			return 0;

	}

	public float getMotorPowerUse(RemoteEV3 brick) {

		/**
		 * @param lego Brick
		 * @return  Motor verbrauch float in mV
		 */
		if (brick != null) {

			System.out.println(
					brick.getName() + " Motoren verbrauchen " + brick.getPower().getMotorCurrent() + "Amp/s Akku");
			return brick.getPower().getMotorCurrent();
		} else {
			return 0;
		}

		// TODO: PoverLevel in % umrechnen, wenn unter wert x dann alarm
	}

	public void updatePowerLevel() {
		/**
		 *  updated powerlevel anzeige im controller
		 */

		c.updatePowerLevel();

	}

	// --------------Controller interactions----------------------

	public void updateLabelInController() {
		/**
		 * @param laesst
		 *            den Controller alle Labels updaten
		 */
		c.updateLabels();
	}

	// --------------------------------------------------------

	public void b1053Fired() { // lift schalter
		/**
		 * Programm ablauf wartet auf diesen boolean
		 * Sensor lift schalter
		 */
		chargier.touchLiftfired();
		b1053Status = true;
	}

	public void b1054Fired() { // drehtischschalter
		/**
		 * Programm ablauf wartet auf diesen boolean
		 * Dreht tisch  schalter
		 */
		chargier.touchTablefired();
		b1054Status = true;
	}

	public void b1061Fired() { // ultraschall sensor
		/**
		 * Programm ablauf wartet auf diesen boolean
		 * Ultraschall Sensor
		 */
		chargier.schrankefired();
		b1061Status = true;
	}

	public void b1072Fired() {
		/**
		 * zaehlt bälle
		 */
		quality.counterSensorFired();
		b1072Status = true;
	}

	public void b1073Fired(String colorString) {

		quality.colorSensorFired(colorString);
	}

	public void b1131Fired(boolean button) {

		compressor.pressureButtonfired(button);
	}

	public void b1151Fired(String colorString) {

		qualitystation.colorSensorFired(colorString);
	}

	public void armIsStalled(boolean armIsStalled) {
		/**
		 * methode soll aufgerufen werden wenn ein Motor blockiert
		 * das Funktioniert mit der Lego Biblio auch 
		 * noch zu implementieren
		 */
		// QualityStation arm is stalled boolean true stalled, false nicht
		// qulityStation.setArmIsStalled(armIsStalled);

	}

	public int getSzenario() {
		return szenario;
	}

	public void setSzenario(int szenario) {
		this.szenario = szenario;
	}

	public void resetSensorStatus() {
		b1053Status = false;
		b1054Status = false;
		b1061Status = false;
		b1072Status = false;
		quality.resetColorString();
	}

	// -------------------Getter-Stations---------------------------------------------

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

	public Deliverylane getDelivery() {
		return deliverylane;
	}

	public void turnKompressorOff() {
		compressor.setStatus(false);
	}

	public void turnKompressorOn() {
		compressor.setStatus(true);
	}

	public boolean getKompressorStatus() {
		return compressor.isStatus();
	}

	public Stock getStock() {
		return stock;
	}

	public static void setStock(Stock stock) {
		Steuerung.stock = stock;
	}

	public FillStation getFillStation() {
		return fillStation;
	}

	public static void setFillStation(FillStation fillStation) {
		Steuerung.fillStation = fillStation;
	}

	public static Compressor getCompressor() {
		return compressor;
	}

	public static void setCompressor(Compressor compressor) {
		Steuerung.compressor = compressor;
	}

	public static Airarms getAirarms() {
		return airarms;
	}

	public static void setAirarms(Airarms airarms) {
		Steuerung.airarms = airarms;
	}

	public ArrayList<String> getBrickIps() {
		return brickIps;
	}

	public void setBrickIps(ArrayList<String> brickIps) {

		this.brickIps = brickIps;
	}

	// ------------------------help methods with
	// werden durch test Button im UI aufgerufen und fuehren standart ablauf
	// durch
	// stations------------------------------

	public void runDelivery() {

		/**
		 * runs Station standart ablauf in seperaten thread
		 */
		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override
			public void run() {
				try {
					deliverylane.startLineToEnd(false);
					deliverylane.closeGates();
					deliverylane.turnLineToArms(-1048);
					airarms.runAirArms();
					deliverylane.openGateB();
					deliverylane.openEquallyGate();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, 1000);
		

	}

	public void runChargier(boolean mode) { // True starts false stops

		/**
		 * runs Station standart ablauf in seperaten thread
		 */
		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override
			public void run() {
				try {
					if (mode == true) {
						chargier.resetTable(true);
						chargier.startLineToTable(false);
						chargier.startTableLine(true);

						// wait till Table Button is pushed, test maybe Ui
						// freezes
						while (!b1054Status) {
							System.out.println("hänge in schleife 1");
						}

						chargier.stopLineToTable();
						chargier.stopTableLine();
						chargier.turnTable(660, false);

						chargier.startLineToLifter(false);
						chargier.startTableLine(false);

						while (!b1053Status) {
							System.out.println("hänge in schleife 2");
						}
						chargier.stopLineToLifter();
						chargier.stopTableLine();
						chargier.startLineToLifter(true);
						chargier.startTableLine(true);

						while (!b1054Status) { // wait table button pushed
							System.out.println("hänge in schleife 3");
						}

						chargier.stopLineToLifter();
						chargier.stopTableLine();

						chargier.turnTable(-1320, false);

						chargier.startLineToStore(false); // maybe falls
						chargier.startTableLine(false);

						Thread.sleep(3000);
						chargier.stopTableLine();
						chargier.stopLineToStorer();

						chargier.resetTable(true); // turns 660 to much repair
													// later

					} else {
						chargier.stop();
					}

				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, 1000);
	}

	public void runCleaner(boolean mode) {

		/**
		 * runs Station standart ablauf in seperaten thread
		 */
		if (mode == true) {
			try {
				cleaner.startLiftLine(true);
				cleaner.startCleaner(true);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				cleaner.stop();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void runShaker(boolean mode) {
		/**
		 * runs Station standart ablauf in seperaten thread
		 */
		if (mode == true) {
			try {
				lift.startShaker();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				lift.stopShaker();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void runLift(boolean mode) {
		/**
		 * runs Station standart ablauf in seperaten thread
		 */
		if (mode == true) {
			new java.util.Timer().schedule(new java.util.TimerTask() {
				@Override
				public void run() {
					lift.start(false);
				}
			}, 1000);
		} else {

		}
	}

	public void runQuality(boolean mode) {
		/**
		 * runs Station standart ablauf in seperaten thread
		 */
		if (mode == true) {
			try {
				quality.startCounterLine(false);
				quality.startLine(true);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				quality.stop();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void runAirarms(boolean mode) {
		/**
		 * runs Station standart ablauf in seperaten thread
		 */
		if (mode == true) {
			airarms.runAirArms();
		} else {
			airarms.reset();
		}
	}

	public void runStock(boolean mode) {
		/**
		 * runs Station standart ablauf in seperaten thread
		 */
		if (mode == true) {
			new java.util.Timer().schedule(new java.util.TimerTask() {
				@Override
				public void run() {
					try {
						stock.elevatorToRight(true);
						stock.elevatorUp(false);
						stock.elevatorToLeft(true);
						stock.elevatorDown(false);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}, 1000);

		} else {
			stock.reset();
		}
	}

	public void runDelivery(boolean mode) {
		/**
		 * runs Station standart ablauf in seperaten thread
		 */
		if (mode == true) {
			new java.util.Timer().schedule(new java.util.TimerTask() {
				@Override
				public void run() {
					try {
						deliverylane.startLineToEnd(false);
						deliverylane.turnLineToArms(-1048);
						runGates(true);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}, 1000);

		} else {
			try {
				runGates(false);
				deliverylane.stopLineToEnd();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void runGates(boolean mode) {
		/**
		 * runs Station standart ablauf in seperaten thread
		 */
		if (mode == true) {
			deliverylane.openGateB();
			deliverylane.openEquallyGate();
		} else {
			deliverylane.closeGates();
		}
	}

	public void runCar() {
		/**
		 * runs Station standart ablauf in seperaten thread
		 */
		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override
			public void run() {
				car.moveCarDegree(720, false);
				car.moveCarDegree(-720, false);
			}
		}, 1000);
	}
	// --------------------Szenarios------------------------------

	public void startSzenario1() {
		/**
		 * load box
		 * Box to lift
		 * balls are running until airarms
		 * box infront of stock
		 * box in stock
		 */
		setSzenario(1); 
		sendMessage("ST");
		sendPowerLevels();
		sendMessage("CA"); // Send everytime 1 new Container gets delivered, not
							

		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override
			public void run() {
				try {

					fillStation.loadCar();
					chargier.resetTable(true);
					chargier.startLineToTable(false);
					chargier.startTableLine(true);

					// wait till Table Button is pushed, test maybe Ui freezes
					while (!b1054Status) {
						System.out.println("hänge in schleife 1");
					}

					chargier.stopLineToTable();
					chargier.stopTableLine();
					chargier.turnTable(660, false);

					chargier.startLineToLifter(false);
					chargier.startTableLine(false);

					while (!b1053Status) {
						System.out.println("hänge in schleife 2");
					}
					chargier.stopLineToLifter();
					chargier.stopTableLine();

					lift.startShaker();
					lift.start(false); // wait until it finished
					lift.stopShaker();

					cleaner.startCleaner(true);
					cleaner.startLiftLine(true); 

					chargier.startLineToLifter(true);
					chargier.startTableLine(true);

					quality.startCounterLine(false);
					quality.startLine(true);

					while (!b1054Status) { // wait table button pushed
						System.out.println("hänge in schleife 3");
					}

					chargier.stopLineToLifter();
					chargier.stopTableLine();

					chargier.turnTable(-1320, false);

					chargier.startLineToStore(false); 
					chargier.startTableLine(false);

					Thread.sleep(3000);
					
					stock.placeBoxFromLineOnElevatorline(false);
					stock.storeBox(false);
					
					chargier.stopTableLine();
					chargier.stopLineToStorer();

					chargier.resetTable(true); // turns 660 to much repair later

					Thread.sleep(10000); // wait 10 sec
					//
					cleaner.stopLiftLine();
					cleaner.stop();
					//
					Thread.sleep(10000); // wait 10 sec
					//
					quality.stopCounterLine();
					quality.stopLine();

					System.out.println("N/IO: " + quality.getBadBalls() + "  IO: " + quality.getGoodBalls());

				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, 1000);

	}

	public void startSzenario2() {

		/**
		 * turn table to lift
		 * 
		 */
		setSzenario(2);
		sendMessage("TF");
		sendMessage("ST");
		c.updatePowerLevel();
		
		
		 new java.util.Timer().schedule(new java.util.TimerTask() {
		 @Override
		 public void run() {
		 try {
		
			 
			 
		 
		 } catch (RemoteException e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 } catch (InterruptedException e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }
		 }
		 }, 1000);

	}

	public void startSzenario3() {

		/**
		 *   Volle Kiste steht auf dem Band und Leere im Lager
		 *   
		 *   Gleichzeitiger ablauf 1&2:
		 *   
		 *   1: Kiste befüllt
		 *   
		 */
		setSzenario(3);
		// c.updateLabels();
		// sendMessage("ST");

		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override
			public void run() {
				try {
					chargier.resetTable(false);

					fillStation.rotateWheel(360, false);
					
					chargier.startLineToTable(false);
					chargier.startTableLine(true);

					// positioniere leere kiste auf dem Laufband vor dem
					// Elevator
					// Gleichzeitiger ablauf änder es spaeter im code solange
					// neuen Thread erstellen unsauber
					new java.util.Timer().schedule(new java.util.TimerTask() {
						@Override
						public void run() {
							try {
								stock.PushBox(true);							// box from stock on elevator line		
								chargier.startLineToStore(true);
								stock.pushBoxFromElevatorToLine(false); 		// from elevator Line on Chargier
								// chargier.stopLineToStorer();

							} catch (RemoteException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}, 1000);

					// wait till Table Button is pushed, test maybe Ui freezes
					while (!b1054Status) {
						// System.out.println("hänge in schleife 1");
					}
					chargier.stopLineToStorer();

					chargier.stopLineToTable();
					chargier.stopTableLine();

					chargier.turnToLift(false);
					chargier.startLineToLifter(false);
					chargier.startTableLine(false);

					while (!b1053Status) {
						// System.out.println("hänge in schleife 2");
					}

					chargier.stopLineToLifter();
					chargier.stopTableLine();

					new java.util.Timer().schedule(new java.util.TimerTask() { // neuer
																				// thread
																				// da
																				// simultan
																				// unsauber
						@Override
						public void run() {
							try {

								chargier.turnToStock(false);
								chargier.startLineToStore(true); // auf Tisch
								chargier.startTableLine(true);

								while (!b1054Status) { // wait table button
														// pushed
									// System.out.println("hänge in schleife
									// 3");
								}
								chargier.stopLineToStorer();
								chargier.stopTableLine();
								chargier.turnToCar(false);
								chargier.startTableLine(false);
								chargier.startLineToTable(true);
								Thread.sleep(2000);
								chargier.stopLineToTable();
								chargier.stopTableLine();

								chargier.turnToLift(false);
								chargier.startLineToLifter(true);
								chargier.startTableLine(true);
								// wait till Table Button is pushed, test maybe
								// Ui freezes
								while (!b1054Status) {
									System.out.println("hänge in schleife 1");
								}

								chargier.stopLineToLifter();
								chargier.stopTableLine();

								chargier.turnToStock(false);

								chargier.startLineToStore(false); // maybe falls
								chargier.startTableLine(false);
								Thread.sleep(2000);

								stock.storeBox(false);

								chargier.stopTableLine();
								chargier.stopLineToStorer();

								chargier.resetTable(false); // turns 660 to much
															// repair later

							} catch (RemoteException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}, 1000);

					lift.startShaker();
					lift.start(false); // wait until it finished
					lift.stopShaker();

					cleaner.startCleaner(true); // TODO: maybe falls = andere
												// richtung
					cleaner.startLiftLine(true); // TODO: maybe falls = andere
													// richtung

					// chargier.startLineToLifter(true);
					// chargier.startTableLine(true);
					// //
					// quality.startCounterLine(false);
					// quality.startLine(true);
					//
					// while (!b1054Status) { // wait table button pushed
					// System.out.println("hänge in schleife 3");
					// }
					//
					// chargier.stopLineToLifter();
					// chargier.stopTableLine();
					//
					// chargier.turnTable(-1320);
					//
					// chargier.startLineToStore(false); // maybe falls
					// chargier.startTableLine(false);
					//
					// Thread.sleep(3000);
					// chargier.stopTableLine();
					// chargier.stopLineToStorer();
					//
					// chargier.resetTable(); // turns 660 to much repair later
					//
					// Thread.sleep(20000); // wait 10 sec
					// //
					cleaner.stopLiftLine();
					cleaner.stop();
					//
					Thread.sleep(30000); // wait 10 sec
					//
					quality.stopCounterLine();
					quality.stopLine();

					System.out.println("N/IO: " + quality.getBadBalls() + "  IO: " + quality.getGoodBalls());

				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, 1000);

	}

	public static Car getCar() {
		return car;
	}

	public static void setCar(Car car) {
		Steuerung.car = car;
	}

}
