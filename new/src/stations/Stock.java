package stations;

import java.rmi.RemoteException;

import lejos.remote.ev3.RMIRegulatedMotor;

public class Stock {

	RMIRegulatedMotor laneToStock1;
	RMIRegulatedMotor laneToStock2;

	RMIRegulatedMotor elevatorHorizontal1;
	RMIRegulatedMotor elevatorHorizontal2;
	RMIRegulatedMotor elevatorVertical1;
	RMIRegulatedMotor elevatorVertical2;

	RMIRegulatedMotor stockPlace1;
	RMIRegulatedMotor stockPlace2;
	RMIRegulatedMotor stockPlace3;
	RMIRegulatedMotor stockPlace4;

	private boolean stock1 = false; // topleft false = empty
	private boolean stock2 = false; // topright
	private boolean stock3 = false; // downleft
	private boolean stock4 = false; // downright

	private char elevatorPositionHorizontal = 'd'; // d = down u= up
	private char elevatorPositionVertical = 'l'; // l = left r = right

	private int stockRotationDegree = 90;
	private int veritcalRotationDegree = 540;
	private int lineSpeed = 300;

	public Stock(RMIRegulatedMotor laneToStock1, RMIRegulatedMotor laneToStock2, RMIRegulatedMotor elevatorHorizontal1,
			RMIRegulatedMotor elevatorHorizontal2, RMIRegulatedMotor elevatorVertical1,
			RMIRegulatedMotor elevatorVertical2, RMIRegulatedMotor stockPlace1, RMIRegulatedMotor stockPlace2,
			RMIRegulatedMotor stockPlace3, RMIRegulatedMotor stockPlace4) {

	}

	public void pushBoxFromStock(int stock) throws RemoteException {

		switch (stock) {
        case 1:  pushStock1();
                 break;
        case 2:  pushStock2();
                 break;
        case 3:  pushStock3();
                 break;
        case 4:  pushStock4();
                 break;
        default:  break;
    }
	}

	public void pushStock1() throws RemoteException {

		if (isStock1()) {
			stockPlace1.rotate(stockRotationDegree, false);
			stockPlace1.rotate(-stockRotationDegree, false);
			setStock1(false);
		} else {
			System.out.println("Stock 1 is empty");
		}
	}

	public void pushStock2() throws RemoteException {

		if (isStock2()) {
			stockPlace2.rotate(stockRotationDegree, false);
			stockPlace2.rotate(-stockRotationDegree, false);
			setStock2(false);
		} else {
			System.out.println("Stock 2 is empty");
		}
	}

	public void pushStock3() throws RemoteException {

		if (isStock3()) {
			stockPlace3.rotate(stockRotationDegree, false);
			stockPlace3.rotate(-stockRotationDegree, false);
			setStock3(false);
		} else {
			System.out.println("Stock 3 is empty");
		}
	}

	public void pushStock4() throws RemoteException {

		if (isStock4()) {
			stockPlace4.rotate(stockRotationDegree, false);
			stockPlace4.rotate(-stockRotationDegree, false);
			setStock4(false);
		} else {
			System.out.println("Stock 4 is empty");
		}
	}

	public void startLineToStock(boolean direction) throws RemoteException {

		laneToStock1.setSpeed(getLineSpeed());
		laneToStock2.setSpeed(getLineSpeed());

		if (direction) {
			laneToStock1.forward();
			laneToStock2.forward();
		} else {
			laneToStock1.backward();
			laneToStock2.backward();
		}

	}

	public void stopLaneToStock() throws RemoteException {

		laneToStock1.stop(false); // false he waits for the motor to stop until
									// it really finishes
		laneToStock2.stop(false);
	}

	public void elevatorUp() throws RemoteException {

		if (getElevatorPositionVertical() == 'u') {
			// its allready up
		} else {
			elevatorVertical1.rotate(11520, true); // move both at the same time
													// and wait for second
			elevatorVertical2.rotate(11520, false);
			setElevatorPositionVertical('u');
		}

	}

	public void elevatorDown() throws RemoteException {

		if (getElevatorPositionVertical() == 'd') {
			elevatorVertical1.rotate(11520, true); // move both at the same time
													// and wait for second
			elevatorVertical2.rotate(11520, false);
			setElevatorPositionVertical('d');
		}
	}

	public void elevatorToLeft() throws RemoteException {

		if (getElevatorPositionHorizontal() == 'l') {
			// nothing is allready left
		} else {
			elevatorVertical1.rotate(-veritcalRotationDegree, true); // TODO:
																		// vorzeichen
																		// möglicherweise
																		// andersherum
			elevatorVertical2.rotate(veritcalRotationDegree, false);
			setElevatorPositionHorizontal('l');
		}
	}

	public void elevatorToRight() throws RemoteException {

		if (getElevatorPositionHorizontal() == 'r') {
			// nothing is allready right
		} else {
			elevatorVertical1.rotate(veritcalRotationDegree, true); // TODO:
																	// vorzeichen
																	// möglicherweise
																	// andersherum
			elevatorVertical2.rotate(-veritcalRotationDegree, false);
			setElevatorPositionHorizontal('r');
		}
	}

	public void printElevatorPosition() {

		if (getElevatorPositionHorizontal() == 'l') {
			if (getElevatorPositionVertical() == 'u') {
				System.out.println("Elevator is UP/LEFT");
			} else {
				System.out.println("Elevator is UP/Right");
			}
		} else {
			if (getElevatorPositionVertical() == 'u') {
				System.out.println("Elevator is DOWN/LEFT");
			} else {
				System.out.println("Elevator is DOWN/RIGHT");
			}

		}
	}

	public boolean isStock1() {
		return stock1;
	}

	public void setStock1(boolean stock1) {
		this.stock1 = stock1;
	}

	public boolean isStock2() {
		return stock2;
	}

	public void setStock2(boolean stock2) {
		this.stock2 = stock2;
	}

	public boolean isStock3() {
		return stock3;
	}

	public void setStock3(boolean stock3) {
		this.stock3 = stock3;
	}

	public boolean isStock4() {
		return stock4;
	}

	public void setStock4(boolean stock4) {
		this.stock4 = stock4;
	}

	public int getLineSpeed() {
		return lineSpeed;
	}

	public void setLineSpeed(int lineSpeed) {
		this.lineSpeed = lineSpeed;
	}

	public char getElevatorPositionHorizontal() {
		return elevatorPositionHorizontal;
	}

	public void setElevatorPositionHorizontal(char elevatorPositionHorizontal) {
		this.elevatorPositionHorizontal = elevatorPositionHorizontal;
	}

	public char getElevatorPositionVertical() {
		return elevatorPositionVertical;
	}

	public void setElevatorPositionVertical(char elevatorPositionVertical) {
		this.elevatorPositionVertical = elevatorPositionVertical;
	}

}
