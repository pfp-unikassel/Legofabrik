package chh;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lejos.hardware.port.PortException;
import lejos.remote.ev3.RemoteEV3;

public class Brick {

	String ip;
	RemoteEV3 ev3;
	TreeSet<Engine> engines;
	TreeSet<Sensor> sensors;
	ExecutorService executor = Executors.newCachedThreadPool();
	
	// usually ev3 is null here. care about that later.
	public Brick(String ip, RemoteEV3 ev3) {
		this.ip = ip;
		this.ev3 = ev3;
		engines = new TreeSet<>();
		sensors = new TreeSet<>();
	}
	
	// Exceptions get thrown by the lejos framework
	public void init() throws RemoteException, MalformedURLException, NotBoundException {
		// ev3 is usually null initialized
		ev3 = new RemoteEV3(ip);
	}
	
	// NullPointerException if Engine e is null. PortException if createRegulatedMotor fails.
	public boolean addEngine(Engine e, String ip) throws NullPointerException, PortException {
		System.out.println("Adding " + e);
		return engines.add(e);
	}
	
	public void initEngines() throws PortException {
		for (Engine e : engines) {
			e.init(ev3);
			// ev3.createRegulatedMotor(e.getPort(), e.getType());
		}
	}
	
	public boolean addSensor(Sensor s) throws NullPointerException {
		return sensors.add(s);
	}
	
	public void initSensors() throws PortException {
		for (Sensor s : sensors)
			s.init(ev3);
	}
	
	public void startEngines() throws RemoteException, InterruptedException {
		for (Engine e : engines) {
			// executor.submit(e.getSzenario());
			executor.submit(e.callMe());
			System.out.println("Added " + e.desc + " to executor.");
		}
	}
	
	public void startSensors() throws RemoteException {
		
		for (Sensor s : sensors) {
			executor.submit(s);
		}
	}
	
	public void shutdown() throws RemoteException {
		for (Engine e : engines)
			e.shutdown();
		for (Sensor s : sensors)
			s.shutdown();
		executor.shutdown();
	}
	
	public RemoteEV3 getRemote() {
		return ev3;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
