package stations;

import java.rmi.RemoteException;

import javax.swing.Timer;

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
	private int shakerSpeed = 700;
		

	public Lift(	RMIRegulatedMotor greifenLinks,
			RMIRegulatedMotor greifenRechts,
			RMIRegulatedMotor hebenLinks, 
			RMIRegulatedMotor hebenRechts,
			RMIRegulatedMotor shaker) {
		this.greifenLinks=greifenLinks;
		this.greifenRechts=greifenRechts;
		this.hebenLinks=hebenLinks;
		this.hebenRechts=hebenRechts;
		this.shaker = shaker;
	}
	
	public void startGrab() throws RemoteException {  
		
		greifenLinks.rotate(winkelGreifen);
		greifenRechts.rotate(-winkelGreifen);
	}
	
	public void releaseGrab() throws RemoteException { 
		
		greifenLinks.rotate(-winkelGreifen);
		greifenRechts.rotate(winkelGreifen);
	}
	public void startLiftUp() throws RemoteException {   // start lift/elevator and hold him up
		
		hebenLinks.setSpeed(liftSpeed);
		hebenRechts.setSpeed(liftSpeed);
		
		hebenLinks.rotate(winkelHeben);
		hebenRechts.rotate(winkelHeben);
		
	}
	
	public void startLiftDown() throws RemoteException { // brings box and elevator/lift back down 
		
		hebenLinks.setSpeed(liftSpeed);
		hebenRechts.setSpeed(liftSpeed);
		
		hebenLinks.rotate(-winkelHeben);
		hebenRechts.rotate(-winkelHeben);
		
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
	
		shaker.stop(false);
	}
	public int getShakerSpeed() {
		return shakerSpeed;
	}
	
	public void setShakerSpeed(int shakerSpeed) {
		this.shakerSpeed = shakerSpeed;
	}
	
	public void start() {
		
		try {
			
			startGrab();
			
			new java.util.Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			            	try {
								startLiftUp();
							} catch (RemoteException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			            }
			        }, 
			        2500 								//time lift needs to grab
			);
			
			new java.util.Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			            	try {
								startLiftDown();
							} catch (RemoteException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			            }
			        }, 
			        5000								//time lift needs to lift and to be empty
			);

			new java.util.Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			            	try {
								releaseGrab();
							} catch (RemoteException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			            }
			        }, 
			        500
			);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
