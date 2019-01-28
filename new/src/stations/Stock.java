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
	private boolean stock2 = true; // topright
	private boolean stock3 = false; // downleft
	private boolean stock4 = false; // downright
	
	private int bandPosition  =  0;   // True is startposition, ready to take box from store on line
	
	private char elevatorPositionHorizontal = 'l'; // l = left r = right 
	private char elevatorPositionVertical = 'd'; // d = down u= up

	private int elevatorHorizontalSpeed = 300;
	private int elevatorVerticalSpeed = 720;
	private int storeLineRotateDegree = 550;   // motor turn degree from line on Elevator to get Box from store or in store
	private int stockRotationDegree = 160;   // motor tur´n degree from push mechanism
	private int horizontalRotationDegree = 600;	// Elevator motor turndegree
	private int lineSpeed = 300;

	public Stock(RMIRegulatedMotor laneToStock1, RMIRegulatedMotor laneToStock2, RMIRegulatedMotor elevatorHorizontal1,
			RMIRegulatedMotor elevatorHorizontal2, RMIRegulatedMotor elevatorVertical1,
			RMIRegulatedMotor elevatorVertical2, RMIRegulatedMotor stockPlace1, RMIRegulatedMotor stockPlace2,
			RMIRegulatedMotor stockPlace3, RMIRegulatedMotor stockPlace4) {

		this.stockPlace1 = stockPlace1;
		this.stockPlace2 = stockPlace2;
		this.stockPlace3 = stockPlace3;
		this.stockPlace4 = stockPlace4;

		this.laneToStock1 = laneToStock1;
		this.laneToStock2 = laneToStock2;
		this.elevatorVertical1 = elevatorVertical1;
		this.elevatorVertical2 = elevatorVertical2;

		this.elevatorHorizontal1 = elevatorHorizontal1;
		this.elevatorHorizontal2 = elevatorHorizontal2;

	}
	
	public void pushBoxFromElevatorToStore(){ // have to make sure box is on elevator
		
		rotateLineToStock(1550-storeLineRotateDegree);  // Rotation 1550 - 350o from before is a 360° turn, so it ends where it starts
	}
	
	public void pushBoxFromElevatorToLine(){ // have to make sure Box is on elevator
		
		rotateLineToStock(-1550+storeLineRotateDegree); 
	}

	public void pushBoxFromStock(int stock) throws RemoteException, InterruptedException {

		switch (stock) {
		case 1:
			if (isStock1() == true) {
				elevatorUp();
				pushStock1();
				placeBoxFromStoreOnElevatorline();	
			//	startLineToStock(false); // Change to degree later
			//	Thread.sleep(2000);// 2000
				//stopLaneToStock();
				elevatorDown();
				setStock1(false);
				
			}
			break;
		case 2:
			if (isStock2() == true) {
				elevatorToRight();
				elevatorUp();
				pushStock2();
				placeBoxFromStoreOnElevatorline();
				//startLineToStock(false); // Change to degree later
			//	Thread.sleep(2000);
			//	stopLaneToStock();
				elevatorDown();
				elevatorToLeft();
				setStock2(false);
			}
			break;
		case 3:
			if (isStock3() == true) {
				pushStock3();
				placeBoxFromStoreOnElevatorline();
				//startLineToStock(false); // Change to degree later
				//Thread.sleep(2000);
//				stopLaneToStock();
				setStock3(false);
			}
			break;
		case 4:
			if (isStock4() == true) {
				elevatorToRight();
				pushStock4();
				placeBoxFromStoreOnElevatorline();	
			//	startLineToStock(false); // Change to degree later
			//	Thread.sleep(2000);
//				stopLaneToStock();
				elevatorToLeft();
				setStock4(false);
			}
			break;
		default:
			break;
		}
	}

	public void placeBoxFromStoreOnElevatorline(){
		
		
			rotateLineToStock(-storeLineRotateDegree);
		
	}
	
public void placeBoxFromLineOnElevatorline(){
		
	
			rotateLineToStock(storeLineRotateDegree);
		
	}
	
	public void rotateLineToStock(int degree) {

		try {
			laneToStock1.setSpeed(getLineSpeed());
			laneToStock2.setSpeed(getLineSpeed());
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			laneToStock1.rotate(degree, true);
			laneToStock2.rotate(-degree, false);
			bandPosition += degree;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void resetElevatorLinePosition(){
		rotateLineToStock(-bandPosition);
	}

	public void pushStock1() throws RemoteException {

		if (isStock1()) {
			stockPlace1.rotate(-stockRotationDegree, false);
			stockPlace1.rotate(stockRotationDegree, false);
			setStock1(false);
		} else {
			System.out.println("Stock 1 is empty");
		}
	}

	public void pushStock2() throws RemoteException {

		if (isStock2()) {
			stockPlace2.rotate(-stockRotationDegree, false);
			stockPlace2.rotate(stockRotationDegree, false);
			setStock2(false);
		} else {
			System.out.println("Stock 2 is empty");
		}
	}

	public void pushStock3() throws RemoteException {

		if (isStock3()) {
			stockPlace3.rotate(-stockRotationDegree, false);
			stockPlace3.rotate(stockRotationDegree, false);
			setStock3(false);
		} else {
			System.out.println("Stock 3 is empty");
		}
	}

	public void pushStock4() throws RemoteException {

		if (isStock4()) {
			stockPlace4.rotate(-stockRotationDegree, false);
			stockPlace4.rotate(stockRotationDegree, false);
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
			laneToStock2.backward();
			// laneToStock1.rotate(2000,true);
			// laneToStock2.rotate(-2000,false);
		} else {
			laneToStock1.backward();
			laneToStock2.forward();
			// laneToStock1.rotate(-2000,true);
			// laneToStock2.rotate(2000,false);
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
			elevatorVertical1.setSpeed(elevatorVerticalSpeed);
			elevatorVertical2.setSpeed(elevatorVerticalSpeed);
			
			elevatorVertical1.rotate(11620, true); // move both at the same time
												// 11520
												// and wait for second
			elevatorVertical2.rotate(-11520, false);
			setElevatorPositionVertical('u');
		}

	}

	public void elevatorDown() throws RemoteException {

		if (getElevatorPositionVertical() == 'u') {
			elevatorVertical1.setSpeed(elevatorVerticalSpeed);
			elevatorVertical2.setSpeed(elevatorVerticalSpeed);

			elevatorVertical1.rotate(-11620, true); // move both at the same
													// time
													// and wait for second
			elevatorVertical2.rotate(11520, false);
			setElevatorPositionVertical('d');
		}
	}

	public void elevatorToLeft() throws RemoteException {

		if (getElevatorPositionHorizontal() == 'l') {
			// nothing is allready left
		} else {
			elevatorHorizontal1.setSpeed(elevatorHorizontalSpeed);
			elevatorHorizontal2.setSpeed(elevatorHorizontalSpeed);
			
			elevatorHorizontal1.rotate(-horizontalRotationDegree, true); // TODO:
																		// vorzeichen
																		// möglicherweise
																		// andersherum
			elevatorHorizontal2.rotate(horizontalRotationDegree, false);
			setElevatorPositionHorizontal('l');
		}
	}

	public void elevatorToRight() throws RemoteException {

		if (getElevatorPositionHorizontal() == 'r') {
			// nothing is allready right
		} else {
			elevatorHorizontal1.setSpeed(elevatorHorizontalSpeed);
			elevatorHorizontal2.setSpeed(elevatorHorizontalSpeed);
			
			elevatorHorizontal1.rotate(horizontalRotationDegree, true); // TODO:
																		// vorzeichen
																		// möglicherweise
																		// andersherum
			elevatorHorizontal2.rotate(-horizontalRotationDegree, false);
			setElevatorPositionHorizontal('r');
		}
	}


	public void reset() {

		try {
			elevatorDown();
			elevatorToLeft();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void storeBox() throws RemoteException, InterruptedException {

		if (isStock1() == false) {
			storeIn1();
		} else {
			if (isStock2() == false) {
				storeIn2();
			} else {
				if (isStock3() == false) {
					storeIn3();
				} else {
					if (isStock4() == false) {
						storeIn4();
					} else {
						System.out.println("Error Lager voll");
					}
				}
			}

		}

	}

	private void storeIn1() throws RemoteException, InterruptedException {
		if (isStock1() == false) {
			placeBoxFromLineOnElevatorline();
			elevatorUp();
			pushBoxFromElevatorToStore();
			Thread.sleep(2000);
			elevatorDown();
			setStock1(true);
		}

	}

	private void storeIn2() throws RemoteException, InterruptedException {
		if (isStock2() == false) {
			placeBoxFromLineOnElevatorline();
			elevatorToRight();
			elevatorUp();
			pushBoxFromElevatorToStore();
			Thread.sleep(2000);
			elevatorDown();
			elevatorToLeft();
			setStock2(true);
		}

	}

	private void storeIn3() throws RemoteException, InterruptedException {
		if (isStock3() == false) {
			 placeBoxFromLineOnElevatorline();
				pushBoxFromElevatorToStore();
				Thread.sleep(2000);
			setStock3(true);
		}

	}

	private void storeIn4() throws RemoteException, InterruptedException {

		if (isStock4() == false) {
			placeBoxFromLineOnElevatorline();
			elevatorToRight();
			pushBoxFromElevatorToStore();
			Thread.sleep(2000);
			elevatorToLeft();
			setStock4(true);
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

	public void takeBoxOnElevator() throws InterruptedException, RemoteException {

		startLineToStock(true); // Change to degree later
		Thread.sleep(1500);
		stopLaneToStock();
	}

	public void takeBoxFromElevatorToTable() throws RemoteException, InterruptedException {
		startLineToStock(false); // Change to degree later
		Thread.sleep(2000);
		stopLaneToStock();
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
