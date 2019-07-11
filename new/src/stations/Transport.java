package stations;

import java.rmi.RemoteException;

import controller.Steuerung;
import lejos.remote.ev3.RMIRegulatedMotor;

public class Transport {

	Steuerung s;

	RMIRegulatedMotor liftlineleft;
	RMIRegulatedMotor liftlineright;
	RMIRegulatedMotor ejectlineleft;
	RMIRegulatedMotor ejectlineright;

	int liftlinespeed = 360;
	int ejectlinespeed = 360;
	int numberOfRotations = 7;
	
	boolean isLeftLiftRunning = false;
	boolean isRightLiftRunning = false;
	boolean isLeftEjectLineRunning = false;
	boolean isRightEjectLineRunning = false;

	boolean liftlineforward = true;
	boolean ejectlineforward = true;

	boolean ejectlineboxleft = true;
	boolean ejectlineboxright = true;

	public Transport(Steuerung st, RMIRegulatedMotor lll, RMIRegulatedMotor llr, RMIRegulatedMotor ell, RMIRegulatedMotor elr) {
		s = st;
		liftlineleft = lll;
		liftlineright = llr;
		ejectlineleft = ell;
		ejectlineright = elr;
	}

	public void rotateLiftLineLeft(int degree, boolean instantReturn) {
		isLeftLiftRunning = true;
		
		try {
			liftlineleft.setSpeed(liftlinespeed);
			liftlineleft.rotate(degree, instantReturn);
			isLeftLiftRunning = false;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			isLeftLiftRunning = false;
			e.printStackTrace();
		}
		
	}
	
	public void rotateLiftLineRight(int degree, boolean instantReturn) {
		isRightLiftRunning = true;
		
		try {
			liftlineright.setSpeed(liftlinespeed);
			liftlineright.rotate(degree, instantReturn);
			isRightLiftRunning = false;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			isRightLiftRunning = false;
			e.printStackTrace();
		}
		
	
	}
	
	public void rotateEjectLineLeft(int degree, boolean instantReturn) {
		if (!isLeftLiftRunning) {
			isLeftEjectLineRunning = true;
			try {
				ejectlineleft.setSpeed(ejectlinespeed);
				ejectlineleft.rotate(degree, instantReturn);
				isLeftEjectLineRunning = false;
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				isLeftEjectLineRunning = false;
				e.printStackTrace();
			}
		}
	}
	
	public void rotateEjectLineRight(int degree, boolean instantReturn) {
		if (!isRightLiftRunning) {
			isRightEjectLineRunning = true;
			try {
				ejectlineright.setSpeed(ejectlinespeed);
				ejectlineright.rotate(degree, instantReturn);
				isRightEjectLineRunning = false;
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				isRightEjectLineRunning = false;
				e.printStackTrace();
			}
		}
	}
	
	public void liftBallsLeft() {
		rotateLiftLineLeft(360*numberOfRotations, false);
	}
	
	public void liftBallsRight() {
		rotateLiftLineRight(360*numberOfRotations, false);
	}
	
	public void ejectLeftBox() {
		rotateEjectLineLeft(360, false); // TODO: adjust angle degree
	}
	
	public void ejectRightBox() {
		rotateEjectLineRight(360, false); // TODO: adjust angle degree
	}
	
	public boolean isRightBoxThere() {
		return ejectlineboxright;
	}

	public boolean isLeftBoxThere() {
		return ejectlineboxleft;
	}

	public RMIRegulatedMotor getLiftlineleft() {
		return liftlineleft;
	}

	public void setLiftlineleft(RMIRegulatedMotor liftlineleft) {
		this.liftlineleft = liftlineleft;
	}

	public RMIRegulatedMotor getLiftlineright() {
		return liftlineright;
	}

	public void setLiftlineright(RMIRegulatedMotor liftlineright) {
		this.liftlineright = liftlineright;
	}

	public RMIRegulatedMotor getEjectlineleft() {
		return ejectlineleft;
	}

	public void setEjectlineleft(RMIRegulatedMotor ejectlineleft) {
		this.ejectlineleft = ejectlineleft;
	}

	public RMIRegulatedMotor getEjectlineright() {
		return ejectlineright;
	}

	public void setEjectlineright(RMIRegulatedMotor ejectlineright) {
		this.ejectlineright = ejectlineright;
	}

	public int getLiftlinespeed() {
		return liftlinespeed;
	}

	public void setLiftlinespeed(int liftlinespeed) {
		this.liftlinespeed = liftlinespeed;
	}

	public int getEjectlinespeed() {
		return ejectlinespeed;
	}

	public void setEjectlinespeed(int ejectlinespeed) {
		this.ejectlinespeed = ejectlinespeed;
	}

	public boolean isLiftlineforward() {
		return liftlineforward;
	}

	public void setLiftlineforward(boolean liftlineforward) {
		this.liftlineforward = liftlineforward;
	}

	public boolean isEjectlineforward() {
		return ejectlineforward;
	}

	public void setEjectlineforward(boolean ejectlineforward) {
		this.ejectlineforward = ejectlineforward;
	}

	public boolean isEjectlineboxleft() {
		return ejectlineboxleft;
	}

	public void setEjectlineboxleft(boolean ejectlineboxleft) {
		this.ejectlineboxleft = ejectlineboxleft;
	}

	public boolean isEjectlineboxright() {
		return ejectlineboxright;
	}

	public void setEjectlineboxright(boolean ejectlineboxright) {
		this.ejectlineboxright = ejectlineboxright;
	}

}