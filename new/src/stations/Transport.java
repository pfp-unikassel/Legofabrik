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
	
	boolean isLiftRunning = false;

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

	public void rotateLiftline(String desc,int degree, boolean instantReturn) {
		isLiftRunning = true;
		switch(desc) {
			case "left":
			try {
				
				liftlineleft.setSpeed(liftlinespeed);
				liftlineleft.rotate(degree, instantReturn);
				isLiftRunning = false;
			} catch (RemoteException e) {
				e.printStackTrace();
			}

			break;

			// right
			default:
			try {
				liftlineright.setSpeed(liftlinespeed);
				liftlineright.rotate(degree, instantReturn);
				isLiftRunning = false;
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			break;
		};
		
	}
	
	public void rotateEjectline(String desc,int degree, boolean instantReturn) {
		if (!isLiftRunning) {
			switch(desc) {
			case "left":
				try {
					ejectlineleft.setSpeed(ejectlinespeed);
					ejectlineleft.rotate(degree, instantReturn);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
							
			//right
			default:
				try {
					ejectlineright.setSpeed(ejectlinespeed);
					ejectlineright.rotate(degree, instantReturn);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				break;
			}
		}
	}
	
	public void liftBallsLeft() {
		rotateLiftline("left", 360*numberOfRotations, false);
	}
	
	public void liftBallsRight() {
		rotateLiftline("right", 360*numberOfRotations, false);
	}
	
	public void ejectLeftBox() {
		rotateEjectline("left", 360, false); // TODO: adjust angle degree
	}
	
	public void ejectRightBox() {
		rotateEjectline("right", 360, false); // TODO: adjust angle degree
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