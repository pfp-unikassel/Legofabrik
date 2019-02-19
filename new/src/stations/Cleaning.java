package stations;

import java.rmi.RemoteException;

import controller.Steuerung;
import lejos.remote.ev3.RMIRegulatedMotor;

public class Cleaning {

	RMIRegulatedMotor cleaner;
	RMIRegulatedMotor liftLine;
	private int cleanerSpeed = 360;
	private int liftLaneSpeed = 260;
	
	private Steuerung s;
	
	public Cleaning(Steuerung s,
		RMIRegulatedMotor cleaner,
		RMIRegulatedMotor liftLine) {
		
		this.s = s;
		this.cleaner = cleaner;
		this.liftLine = liftLine;
	}
	
	public void startLiftLine(boolean direction) throws RemoteException {
		
		liftLine.setSpeed(liftLaneSpeed);
		
		if(direction) {
			liftLine.forward();
		}else {
			liftLine.backward();
		}
		
	}
	
	public void stopLiftLine() throws RemoteException {
		
		liftLine.stop(false);
	}
	
	public void startCleaner(boolean direction) throws RemoteException {
		
		cleaner.setSpeed(cleanerSpeed);
		
		s.sendMessage("WS");
		if(direction) {
			cleaner.forward();
		}else {
			cleaner.backward();
		}
		
	}
	
	public void stopCleaner() throws RemoteException{
		cleaner.stop(false);
	}
	
	public void stop() throws RemoteException {
		
		cleaner.stop(false);
		liftLine.stop(false);
	}

	public int getCleanerSpeed() {
		return cleanerSpeed;
	}

	public void setCleanerSpeed(int cleanerSpeed) {
		this.cleanerSpeed = cleanerSpeed;
	}

	public int getLiftLaneSpeed() {
		return liftLaneSpeed;
	}

	public void setLiftLaneSpeed(int liftLaneSpeed) {
		this.liftLaneSpeed = liftLaneSpeed;
	}
}
