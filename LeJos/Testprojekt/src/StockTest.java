import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RMISampleProvider;
import lejos.remote.ev3.RemoteEV3;

public class StockTest {

	private boolean stock1 = true; // topleft false = empty   Zu test zwecken alle voll
	private boolean stock2 = true; // topright
	private boolean stock3 = true; // downleft
	private boolean stock4 = true; // downright
	
	static RemoteEV3 b117;
	static RemoteEV3 b118;
	static RemoteEV3 b119;

	static RMIRegulatedMotor laneToStock1;   //
	static RMIRegulatedMotor laneToStock2;

	static RMIRegulatedMotor elevatorHorizontal1;		 // 
	static RMIRegulatedMotor elevatorHorizontal2;
	static RMIRegulatedMotor elevatorVertical1;
	static RMIRegulatedMotor elevatorVertical2;

	static RMIRegulatedMotor stockPlace1;   //1  Lager
	static RMIRegulatedMotor stockPlace2;
	static RMIRegulatedMotor stockPlace3;
	static RMIRegulatedMotor stockPlace4;

	private   char elevatorPositionHorizontal = 'd'; // d = down u= up
	private static  char elevatorPositionVertical = 'l'; // l = left r = right

	private static  int stockRotationDegree = 160; // is allready callibrated
	private static  int horizontalDegree = 600;
	
	private static  int lineSpeed = 300;    // line spped of elevator can be changed as u want

	
	public  static void main(String[] args) throws RemoteException, InterruptedException {

		try {
			b117 = new RemoteEV3("192.168.0.111");   
			b118 = new RemoteEV3("192.168.0.114");
			b119 = new RemoteEV3("192.168.0.112");
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		stockPlace1 = b117.createRegulatedMotor("A", 'L');
		stockPlace2 = b117.createRegulatedMotor("B", 'L');
		stockPlace3 = b117.createRegulatedMotor("C", 'L');
		stockPlace4 = b117.createRegulatedMotor("D", 'L');
		
		laneToStock1 = b118.createRegulatedMotor("A", 'L');
		laneToStock2 = b118.createRegulatedMotor("D", 'L');
		elevatorVertical1 = b118.createRegulatedMotor("C", 'L');
		elevatorVertical2 = b118.createRegulatedMotor("B", 'L');
		
		elevatorHorizontal1 = b119.createRegulatedMotor("A", 'L');
		elevatorHorizontal2  = b119.createRegulatedMotor("B", 'L');
		
		
		//-------------------------Test Code-----------------------------------------------under this
		
		
		       // einfach durch ein aus kommentieren die zu testenen methoden aufrufen
			
			elevatorToRight();
			elevatorUp();
			pushStock1();   // dont use 4
			startLineToStock(true);  // true is to store false is to charge
			Thread.sleep(2000);                              // wait for X millis so 1000 = 1 sec
			stopLaneToStock();
			
			elevatorToLeft();
			elevatorDown();
		
			
		
			
		
		
		
		//-------------------------Test Code-----------------------------------------------over this
		
		try {
			stockPlace1.close();
			stockPlace2.close();
			stockPlace3.close();
			stockPlace4.close();
			elevatorHorizontal1.close();
			elevatorHorizontal2.close();
			elevatorVertical2.close();
			elevatorVertical1.close();
			laneToStock1.close();
			laneToStock2.close();

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public static void pushStock1() throws RemoteException {

		
			stockPlace1.rotate(-stockRotationDegree, false);
			stockPlace1.rotate(stockRotationDegree, false);
			
	
	}

	public static void pushStock2() throws RemoteException {

		
			stockPlace2.rotate(-stockRotationDegree, false);
			stockPlace2.rotate(stockRotationDegree, false);
			
		
	}
	public static void pushStock3() throws RemoteException {

		
			stockPlace3.rotate(-stockRotationDegree, false);
			stockPlace3.rotate(stockRotationDegree, false);
		
	}

	public static void pushStock4() throws RemoteException {

			stockPlace4.setSpeed(1);
			stockPlace4.rotate(-stockRotationDegree, false);
			stockPlace4.rotate(stockRotationDegree, false);

	}

	public static void startLineToStock(boolean direction) throws RemoteException {

		laneToStock1.setSpeed(getLineSpeed());
		laneToStock2.setSpeed(getLineSpeed());

		if (direction) {
			laneToStock1.forward();
			laneToStock2.backward();
		} else {
			laneToStock1.backward();
			laneToStock2.forward();
		}

	}

	public static void stopLaneToStock() throws RemoteException {

		laneToStock1.stop(false); // false he waits for the motor to stop until
									// it really finishes
		laneToStock2.stop(false);
	}

	public static void elevatorUp() throws RemoteException {

	 // set speed up
			elevatorVertical1.rotate(11520, true); // move both at the same time
													// and wait for second
			elevatorVertical2.rotate(-11520, false);
	

	}

	public static void elevatorDown() throws RemoteException {

		
			elevatorVertical1.rotate(-11520, true); // move both at the same time
													// and wait for second
			elevatorVertical2.rotate(11520, false);
			
		
	}

	public static void elevatorToLeft() throws RemoteException {

	
			elevatorHorizontal1.rotate(-horizontalDegree, true); // TODO:
																		// vorzeichen
																		// möglicherweise
																		// andersherum
			elevatorHorizontal2.rotate(horizontalDegree, false);
		
	}

	public static void elevatorToRight() throws RemoteException {

	
			elevatorHorizontal1.rotate(horizontalDegree, true); // TODO:
																	// vorzeichen
																	// möglicherweise
																	// andersherum
			elevatorHorizontal2.rotate(-horizontalDegree, false);
		
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

	public static int getLineSpeed() {
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

	public static char getElevatorPositionVertical() {
		return elevatorPositionVertical;
	}

	public void setElevatorPositionVertical(char elevatorPositionVertical) {
		this.elevatorPositionVertical = elevatorPositionVertical;
	}
	
	
}
