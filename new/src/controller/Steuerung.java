package controller;


import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.BaseSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;
import stations.Chargier;
import stations.Lift;
import stations.Cleaning;

public class Steuerung {

	static RemoteEV3 b101;					
	static RemoteEV3 b105;
	static RemoteEV3 b106;
	static RemoteEV3 b107;
	static RemoteEV3 b108;
	
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
	
	
	static Port b104port1;
	static Port b105port3;
	static Port b105port4;
	static Port b106port1;
	static Port b107port2;
	
	static EV3UltrasonicSensor b1061;
	static EV3TouchSensor b1053;
	static EV3TouchSensor b1054;
	static EV3TouchSensor b1072;  // zaehler noch nicht implementiert
	
	static ArrayList<RMIRegulatedMotor> openMotorPorts = new ArrayList<>(); // all open Motor they need to be closed after
	static ArrayList<BaseSensor> openSensorPorts = new ArrayList<>(); 
	
	private static Chargier chargier;
	private static Lift lift;
	private static Cleaning cleaner;
	
	
	
	public static void main(String[] args) throws RemoteException {
		

	}
	
	public void start(){
		
		initAll();
		
		chargier = new Chargier(b1061, b1054, b1053, b106a, b106d, b106b, b105d, b105c);
	  	lift = new Lift(b101a, b101b, b101c, b101d,b108a);
		cleaner = new Cleaning(b108b, b108c);
		
		Sensordeamon sensordeamon = new Sensordeamon(this,b1061,b1053,b1054,b1072); // uebergebe das Object und rufe 
		sensordeamon.start();
		
	}
	
	public void b1053Fired() {
		chargier.touchEndefired();
	}
	
	public void b1054Fired() {
		chargier.touchfired();
	}
	
	public void b1061Fired() {
		chargier.schrankefired();
	}
	
	public void b1072Fired() {
		
	}
	
	
	public static void initAll() {
		initBrick1(); 
		initBrick5(); 
		initBrick6();
		initBrick7();
		initBrick8();  

	}
	
	public static void initBrick1() {
		//Brick 101
				try {
					b101 = new RemoteEV3 ("192.168.0.101");
				} catch (RemoteException | MalformedURLException | NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("B1 not Found");
				}
				 b101a =b101.createRegulatedMotor("A", 'M');
				 b101b =b101.createRegulatedMotor("B", 'M');
				 b101c =b101.createRegulatedMotor("C", 'M');
				 b101d =b101.createRegulatedMotor("D", 'M');
				
				openMotorPorts.add(b101a);
				openMotorPorts.add(b101b);
				openMotorPorts.add(b101c);
				openMotorPorts.add(b101d);
	}
	
	public static void initBrick5() {
		
		try {
			b105 = new RemoteEV3 ("192.168.0.105");
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("B1 not Found");
			
		}
		
		 b105c = b105.createRegulatedMotor("C", 'L');	//Motor Drehtisch
		 b105d = b105.createRegulatedMotor("D", 'L');	//Motor Räder Drehtisch
		
		 b105port3 = b105.getPort("S3");						
		 b1053 = new EV3TouchSensor (b105port3);	//Sensor Förderband
		 b105port4 = b105.getPort("S4");													
		 b1054 = new EV3TouchSensor (b105port4);	//Sensor Drehtisch
		
		openMotorPorts.add(b105c);
		openMotorPorts.add(b105d);
		openSensorPorts.add(b1053);
		openSensorPorts.add(b1054);
	}
	
	public static void initBrick6() {
		
		try {
			b106 = new RemoteEV3 ("192.168.0.106");
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("B1 not Found");
			
		}
		
		 b106a = b106.createRegulatedMotor("A", 'L');	//Laufband zum Drehtisch
		 b106b = b106.createRegulatedMotor("B", 'L');	//Laufband vom Drehtisch
		 b106d = b106.createRegulatedMotor("D", 'L');	//Laufband zur Kippvorrichtung
		
		b106port1 = b106.getPort("S1");							//Sensor Ultraschall FTS-Erkennnung
		b1061 = new EV3UltrasonicSensor (b106port1);
		b1061.getDistanceMode();
		
		openMotorPorts.add(b106a);
		openMotorPorts.add(b106b);
		openMotorPorts.add(b106d);
		openSensorPorts.add(b1061);
		
	}
	
	public static void initBrick7() {
		try {
			b107 = new RemoteEV3 ("192.168.0.107");
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("B1 not Found");
			
		}
		
		 b107b = b107.createRegulatedMotor("B", 'M');
		 b107c = b107.createRegulatedMotor("C", 'L');
		 b107d = b107.createRegulatedMotor("D", 'L');
		 b107port2 = b107.getPort("S2");
		 b1072 = new EV3TouchSensor (b107port2); //Sensor Zähler	
		
		openMotorPorts.add(b107b);
		openMotorPorts.add(b107c);
		openMotorPorts.add(b107d);
		openSensorPorts.add(b1072);
	}
	
	public static void initBrick8() {
		//Brick 108
				try {
					b108 = new RemoteEV3 ("192.168.0.108");
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
	
 

	public static void closePorts(){
		
		for (RMIRegulatedMotor temp : openMotorPorts) {     //close every open Motor
			try {
				temp.close();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				System.out.println(temp.toString() + " could not be closed");
				e.printStackTrace();
			}
		}
		
		for (BaseSensor temp1 : openSensorPorts) {   //close every sensor in Sensorlist
			temp1.close();
		}
	}
	
	public Chargier getChargier() {
		System.out.println("get chargier");
		return chargier;
	}

	public Lift getLift() {
		return lift;
	}

	public Cleaning getCleaner() {
		return cleaner;
	}


	public void startSzenario1()   {
		
		try {
			chargier.startLineToTable(true);
			chargier.startTableLine(true);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void startSzenario2()   {
		
		try {
			chargier.stopLineToTable();
			chargier.stopTableLine();
			chargier.turnTable(660);
			
			new java.util.Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			            	try {
								chargier.startLineToLifter(true);
							} catch (RemoteException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			            }
			        }, 
			        5000   // time to turn around table
			);		
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void startSzenario3()   {
	
		try {
			
			chargier.stopLineToLifter();
			lift.start();
			lift.startShaker();
			
			new java.util.Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			            	try {
								lift.stopShaker();								
							} catch (RemoteException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			            }
			        }, 
			        15000   // time after shaker stops
			);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
