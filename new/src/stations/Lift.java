package stations;

import java.rmi.RemoteException;

import controller.Steuerung;
import lejos.remote.ev3.RMIRegulatedMotor;

public class Lift {

	RMIRegulatedMotor hebenLinks;
	RMIRegulatedMotor hebenRechts;
	RMIRegulatedMotor greifenLinks;
	RMIRegulatedMotor greifenRechts;
	RMIRegulatedMotor shaker;
	
	private int winkelGreifen = 280;
	private int winkelHeben = 3400;
	private int liftSpeed = 740;
	private int shakerSpeed = 720;//360
	private boolean running = false;
		
	private Steuerung s;
	
	public Lift(	Steuerung s,RMIRegulatedMotor greifenLinks,
			RMIRegulatedMotor greifenRechts,
			RMIRegulatedMotor hebenLinks, 
			RMIRegulatedMotor hebenRechts,
			RMIRegulatedMotor shaker) {
		this.s = s;
		this.greifenLinks=greifenLinks;
		this.greifenRechts=greifenRechts;
		this.hebenLinks=hebenLinks;
		this.hebenRechts=hebenRechts;
		this.shaker = shaker;
	}
	
	public void startGrab(boolean instantReturn) throws RemoteException {  
		
		greifenLinks.rotate(winkelGreifen,true);
		greifenRechts.rotate(-winkelGreifen,instantReturn);
	}
	
	public void releaseGrab(boolean instantReturn) throws RemoteException { 
		
		greifenLinks.rotate(-winkelGreifen,true);
		greifenRechts.rotate(winkelGreifen,instantReturn);
	}
	public void startLiftUp(boolean instantReturn) throws RemoteException {   // start lift/elevator and hold him up
		
		hebenLinks.setSpeed(liftSpeed);
		hebenRechts.setSpeed(liftSpeed);
		
		hebenLinks.rotate(winkelHeben,true);
		hebenRechts.rotate(winkelHeben,instantReturn);
		
	}
	
	public void startLiftDown(boolean instantReturn) throws RemoteException { // brings box and elevator/lift back down 
		
		hebenLinks.setSpeed(liftSpeed);
		hebenRechts.setSpeed(liftSpeed);
		
		hebenLinks.rotate(-winkelHeben,true);
		hebenRechts.rotate(-winkelHeben,instantReturn);
		
	}
	
	
	
	public int getliftSpeed() {
		return liftSpeed;
	}
	
	public void setliftSpeedt(int liftSpeed) {
		this.liftSpeed = liftSpeed;
	}
	
	public void startShaker() throws RemoteException {
		
		shaker.setSpeed(shakerSpeed);
		shaker.forward();
	}
	
	public void stopShaker() throws RemoteException {
	
		shaker.stop(true);
	}
	public int getShakerSpeed() {
		return shakerSpeed;
	}
	
	public void setShakerSpeed(int shakerSpeed) {
		this.shakerSpeed = shakerSpeed;
	}
	
	public void start(boolean instantReturn) {
		
		try {
			setRunning(true);
			startGrab(instantReturn);
			

			startLiftUp(instantReturn);
			Thread.sleep(4000);			
			startLiftDown(instantReturn);
	
		    releaseGrab(instantReturn);
			setRunning(false);
				
			
		} catch (RemoteException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public RMIRegulatedMotor getHebenLinks() {
		return hebenLinks;
	}

	public void setHebenLinks(RMIRegulatedMotor hebenLinks) {
		this.hebenLinks = hebenLinks;
	}

	public RMIRegulatedMotor getHebenRechts() {
		return hebenRechts;
	}

	public void setHebenRechts(RMIRegulatedMotor hebenRechts) {
		this.hebenRechts = hebenRechts;
	}

	public RMIRegulatedMotor getGreifenLinks() {
		return greifenLinks;
	}

	public void setGreifenLinks(RMIRegulatedMotor greifenLinks) {
		this.greifenLinks = greifenLinks;
	}

	public RMIRegulatedMotor getGreifenRechts() {
		return greifenRechts;
	}

	public void setGreifenRechts(RMIRegulatedMotor greifenRechts) {
		this.greifenRechts = greifenRechts;
	}

	public RMIRegulatedMotor getShaker() {
		return shaker;
	}

	public void setShaker(RMIRegulatedMotor shaker) {
		this.shaker = shaker;
	}

}
