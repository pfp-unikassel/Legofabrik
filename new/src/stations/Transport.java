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

	int liftlinespeed;
	int ejectlinespeed;

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
		switch(desc) {
			case "left":
			try {
				liftlineleft.setSpeed(liftlinespeed);
				liftlineleft.rotate(degree, instantReturn);
			} catch (RemoteException e) {
				e.printStackTrace();
			}

			break;

			// right
			default:
			try {
				liftlineright.setSpeed(liftlinespeed);
				liftlineright.rotate(degree, instantReturn);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			break;
		};
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