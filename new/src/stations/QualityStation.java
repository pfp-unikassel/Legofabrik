package stations;

import java.rmi.RemoteException;

import lejos.remote.ev3.RMIRegulatedMotor;

public class QualityStation {

	private boolean towerStatus = false; // false off
	private boolean armPositionVertical = false; // false up
	private boolean armIsStalled = false;
	private boolean armWorking=false;
	
	private char armPositionHorizontal = 'm'; // m mid g good b bad
	private String colorString = "";
	private int countedBalls = 0;
	private int goodBalls = 0;
	private int badBalls = 0;
	private int towerSpeed =140;

	private RMIRegulatedMotor table;
	private RMIRegulatedMotor armVertical;
	private RMIRegulatedMotor armHorizontal;
	private RMIRegulatedMotor tower;

	public QualityStation(RMIRegulatedMotor table, RMIRegulatedMotor armVertical, RMIRegulatedMotor armHorizontal,
			RMIRegulatedMotor tower) {

		this.table = table;
		this.armHorizontal = armHorizontal;
		this.armVertical = armVertical;
		this.tower = tower;
		try {
			tower.setSpeed(towerSpeed);
			armHorizontal.setSpeed(600);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void colorSensorFired(String colorString) {

		this.colorString = colorString;
		System.out.println("Station Farbe ist " + colorString);
		
//		if (!armWorking) {                // wenn der arm nicht schon am arbeiten ist
//
//			if (colorString == "White") {
//				takeBallToBad();
//
//			} else {
//				takeBallToBad();
//
//			}
//		}
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

	public void moveArmToGood() throws RemoteException {

		if (!getArmPositionVertical()) { // beweg dich nur wenn arm oben ist

			if (getArmPositionHorizontal() == 'm') {
				// mid  to good
				armHorizontal.rotate(-350 , false);     //-350
			} else if (getArmPositionHorizontal() == 'g') {
				// good do nothing
			} else if (getArmPositionHorizontal() == 'b') {
				// bad move to good
				armHorizontal.rotate(-550 , false);  //-550
			}

			setArmPositionHorizontal('g');
		}
	}

	public void moveArmToBad() throws RemoteException {

		if (!getArmPositionVertical()) {

			if (getArmPositionHorizontal() == 'm') {
				// mid
				armHorizontal.rotate(250 , false); 
			} else if (getArmPositionHorizontal() == 'g') {
				// good
				armHorizontal.rotate(550 , false); 
			} else if (getArmPositionHorizontal() == 'b') {
				// bad do nothing
			}

			setArmPositionHorizontal('b');
		}
	}

	public void moveArmToMid() throws RemoteException {

		if (!getArmPositionVertical()) {

			if (getArmPositionHorizontal() == 'm') {
				// mid do nothing
			} else if (getArmPositionHorizontal() == 'g') {
				// good
				armHorizontal.rotate(370 , false); 
			} else if (getArmPositionHorizontal() == 'b') {
				// bad
				armHorizontal.rotate(-250 , false); 
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

	public void takeBall() throws RemoteException {

		if (!getArmPositionVertical()) {
			// move arm up
			armVertical.rotate(350, false);
			armVertical.rotate(-350, false);
		}
	}

	public void liftTable(){
		
		try {
			table.rotate(-150, false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sinkTable(){
		
		try {
			table.rotate(150, false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void freeBall() throws RemoteException {
		
		if (!getArmPositionVertical()) {
			// move arm down
			armVertical.rotate(200, false);
			armVertical.rotate(-200, false);// rotate without true or fals should wait until it finished
														// rotation
		}
	}

	public void reset() throws RemoteException {
		
		stopTower();
		resetArm();
		sinkTable();

	}

	public void takeBallToGood() {

		setArmWorking(true);
		try {
			liftTable();
			takeBall();
			moveArmToGood();
			freeBall();
			moveArmToMid();
			sinkTable();
			goodBalls++;
			countedBalls++;

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setArmWorking(false);
	}

	public void takeBallToBad() {
		setArmWorking(true);
		try {
			liftTable();
			takeBall();
			moveArmToBad();
			freeBall();
			moveArmToMid();
			sinkTable();
			badBalls++;
			countedBalls++;

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setArmWorking(false);
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

	public boolean isArmWorking() {
		return armWorking;
	}

	public void setArmWorking(boolean armWorking) {
		this.armWorking = armWorking;
	}

}
