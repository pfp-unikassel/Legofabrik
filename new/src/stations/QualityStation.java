package stations;

import java.rmi.RemoteException;

import lejos.remote.ev3.RMIRegulatedMotor;

public class QualityStation {

	private boolean towerStatus = false; // false off
	private boolean armPositionVertical = false; // false up
	private boolean armIsStalled = false;
	private char armPositionHorizontal = 'm'; // m mid g good b bad
	private String colorString = "";
	private int countedBalls = 0;
	private int goodBalls = 0;
	private int badBalls = 0;
	private int armVerticalMotorAngle = 0; // TODO: set motor angle
	private RMIRegulatedMotor table;
	private RMIRegulatedMotor armVertical;
	private RMIRegulatedMotor armHorizontal;
	private RMIRegulatedMotor tower;

	public QualityStation(RMIRegulatedMotor table, RMIRegulatedMotor armHorizontal, RMIRegulatedMotor armVertical,
			RMIRegulatedMotor tower) {

		this.table = table;
		this.armHorizontal = armHorizontal;
		this.armVertical = armVertical;
		this.tower = tower;
	}

	public void colorSensorFired(String colorString) {

		this.colorString = colorString;
		System.out.println("erkannte Farbe ist " + colorString);

		if (colorString == "White") {
			setGoodBalls(getGoodBalls() + 1);

		} else {
			setBadBalls(getBadBalls() + 1);

		}

		setCountedBalls(getCountedBalls() + 1);
	}

	public void startTower() {
		if (!getTowerStatus()) {
			try {
				tower.forward();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // TODO: check if forward is right
		}
	}

	public void stopTower() {
		if (getTowerStatus()) {
			try {
				tower.stop(false);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void moveArmToGood() {

		if (!getArmPositionVertical()) { // beweg dich nur wenn arm oben ist

			if (getArmPositionHorizontal() == 'm') {
				// mid wait till motor is finished
			} else if (getArmPositionHorizontal() == 'g') {
				// good
			} else if (getArmPositionHorizontal() == 'b') {
				// bad
			}

			setArmPositionHorizontal('g');
		}
	}

	public void moveArmToBad() {

		if (!getArmPositionVertical()) {

			if (getArmPositionHorizontal() == 'm') {
				// mid
			} else if (getArmPositionHorizontal() == 'g') {
				// good
			} else if (getArmPositionHorizontal() == 'b') {
				// bad
			}

			setArmPositionHorizontal('b');
		}
	}

	public void moveArmToMid() {

		if (!getArmPositionVertical()) {

			if (getArmPositionHorizontal() == 'm') {
				// mid
			} else if (getArmPositionHorizontal() == 'g') {
				// good
			} else if (getArmPositionHorizontal() == 'b') {
				// bad
			}

			setArmPositionHorizontal('m');
		}
	}

	public void resetArm() throws RemoteException { // run to wall until resistance gets to big, move back to mid
													// afterwards

//	
//		 armUp();
//		 while(armIsStalled){
//		 //lasse Motor langsam in eine Richtung fahren 
//		 }
//		

	}

	public void stopArmHorizontal() {
		try {
			armHorizontal.stop(false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void armUp() throws RemoteException {

		if (getArmPositionVertical()) {
			// move arm up
			armVertical.rotate(armVerticalMotorAngle);
		}
	}

	public void armDown() throws RemoteException {
		if (!getArmPositionVertical()) {
			// move arm down
			armVertical.rotate(-armVerticalMotorAngle); // rotate without true or fals should wait until it finished
														// rotation
		}
	}

	public void reset() throws RemoteException {
		armUp();
		stopTower();
		resetArm();

	}

	public void takeBallToGood() {

		try {
			armDown();
			armUp();
			moveArmToGood();
			armDown();
			armUp();
			goodBalls++;
			countedBalls++;

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void takeBallToBad() {

		try {
			armDown();
			armUp();
			moveArmToBad();
			armDown();
			armUp();
			badBalls++;
			countedBalls++;

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean getTowerStatus() {
		return towerStatus;
	}

	public void setTowerStatus(boolean towerStatus) {
		this.towerStatus = towerStatus;
	}

	public boolean getArmPositionVertical() {
		return armPositionVertical;
	}

	public void setArmPositionVertical(boolean armPositionVertical) {
		this.armPositionVertical = armPositionVertical;
	}

	public char getArmPositionHorizontal() {
		return armPositionHorizontal;
	}

	public void setArmPositionHorizontal(char armPositionHorizontal) {
		this.armPositionHorizontal = armPositionHorizontal;
	}

	public String getColorString() {
		return colorString;
	}

	public void setColorString(String colorString) {
		this.colorString = colorString;
	}

	public int getCountedBalls() {
		return countedBalls;
	}

	public void setCountedBalls(int countedBalls) {
		this.countedBalls = countedBalls;
	}

	public int getGoodBalls() {
		return goodBalls;
	}

	public void setGoodBalls(int goodBalls) {
		this.goodBalls = goodBalls;
	}

	public int getBadBalls() {
		return badBalls;
	}

	public void setBadBalls(int badBalls) {
		this.badBalls = badBalls;
	}

	public boolean getArmIsStalled() {
		return armIsStalled;
	}

	public void setArmIsStalled(boolean armIsStalled) {
		this.armIsStalled = armIsStalled;
	}

}
