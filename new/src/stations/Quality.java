package stations;

import java.rmi.RemoteException;
import java.util.Timer;

import controller.Steuerung;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.remote.ev3.RMIRegulatedMotor;

public class Quality {

	RMIRegulatedMotor line; // TODO: rename, dont know wich lane atm
	RMIRegulatedMotor gate; // schranke
	RMIRegulatedMotor counterLine;
	EV3TouchSensor counter; // from Bandzaehler class
	EV3ColorSensor s;
	float color[];

	private boolean gateStatus = false; // TODO: true us open
	private int counterLineSpeed = 120;
	private int gateSpeed = 720;
	private int lineSpeed = 120; // 60
	private int countedBalls = 0;
	private int goodBalls = 0;
	private int badBalls = 0;
	private String colorString = "";
	private String ioColor = "WHITE";
	private Timer timer = null;

	private Steuerung s1;

	public Quality(Steuerung s, RMIRegulatedMotor band, RMIRegulatedMotor gate, RMIRegulatedMotor counterLine) {

		this.s1 = s;
		this.line = band;
		this.gate = gate;
		this.counterLine = counterLine;

	}

	public void closeGate() {

		if (gateStatus) { // if gate is open close if allready closed stay
							// closed

			gateStatus = false;
			try {
				this.gate.setSpeed(gateSpeed);
				System.out.println("close Gate");
				gate.rotate(40); // maybe +40 dont know what open and close is
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void openGate() {
		if (!gateStatus) { // if gate is close open if allready open stay open

			gateStatus = true;

			try {
				this.gate.setSpeed(gateSpeed);
				
				new java.util.Timer().schedule(new java.util.TimerTask() {
					@Override
					public void run() {
						try {
							gate.rotate(-40);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}					
					}
				}, 1500);
				

				// int timeToClose = 1600;

				timer = new java.util.Timer();
				timer.schedule(new java.util.TimerTask() {
					@Override
					public void run() {
						System.out.println("timer runs ");
						closeGate();
					}
				}, 1950); // time after the gate closes again 1950

			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// Two white balls come in a short period of time.  The old timer to close the gate is canceled -> the gate will be kept open.
			timer.cancel();
			//timer =null;
			timer = new java.util.Timer();
			timer.schedule(new java.util.TimerTask() {
				@Override
				public void run() {
					System.out.println("timer runs ");
					closeGate();
				}
			}, 1950); // time after the gate closes again
//			gateStatus = false;
//			openGate();
		}
	}

	public void startCounterLine(boolean direction) throws RemoteException {

		counterLine.setSpeed(counterLineSpeed);

		if (direction) {
			counterLine.forward();
		} else {
			counterLine.backward();
		}

	}

	public void stopCounterLine() throws RemoteException {

		counterLine.stop(false);
	}

	public void startLine(boolean direction) throws RemoteException {

		line.setSpeed(lineSpeed);

		if (direction) {
			line.forward();
		} else {
			line.backward();
		}

	}

	public void stopLine() throws RemoteException {

		line.stop(false);
	}

	public void counterSensorFired() {

		countedBalls++;
		System.out.println(countedBalls); // TODO: delete after debug
	}

	public void colorSensorFired(String colorString) {

		this.colorString = colorString;
		// System.out.println("Quality Farbe ist " + colorString);

		if (colorString == ioColor) { // if ball is white close gate
			openGate();
			setGoodBalls(getGoodBalls() + 1);
			// System.out.println(" White open gate");
		} else { // if ball is not white open gate
			// closeGate();
			if (colorString == "BLACK") { // if he scans Black he should not
											// count nio balls, black is the
											// line

			} else {
				setBadBalls(getBadBalls() + 1);
			}
			// System.out.println(" not white close gate");
		}
	}

	public int getCounterLineSpeed() {
		return counterLineSpeed;
	}

	public void setCounterLineSpeed(int counterlineSpeed) {
		counterLineSpeed = counterlineSpeed;
	}

	public int getLineSpeed() {
		return lineSpeed;
	}

	public void setLineSpeed(int lineSpeed) {
		this.lineSpeed = lineSpeed;
	}

	public int getCountedBalls() {
		return countedBalls;
	}

	public void setCountedBalls(int countedBalls) {
		this.countedBalls = countedBalls;
	}

	public boolean getGateStatus() {
		return gateStatus;
	}

	public void reset() {

		if (gateStatus) { // closes gate if open
			closeGate();
		}
		setCountedBalls(0);
		resetColorString();
		setGoodBalls(0);
		setBadBalls(0);
	}

	public int getGoodBalls() {
		return goodBalls;
	}

	public void setGoodBalls(int goodBalls) {
		this.goodBalls = goodBalls;
	}

	public void setGateStatus(boolean gateStatus) {
		this.gateStatus = gateStatus;
	}

	public int getBadBalls() {
		return badBalls;
	}

	public void setBadBalls(int badBalls) {
		this.badBalls = badBalls;
	}

	public void setColorString(String colorString) {
		this.colorString = colorString;

	}

	public String getColorString() {
		return colorString;
	}

	public void resetColorString() {
		this.colorString = "";

	}

	public void stop() throws RemoteException {

		stopCounterLine();
		stopLine();
		reset();

	}

	public String getIoColor() {
		return ioColor;
	}

	public void setIoColor(String ioClore) {
		this.ioColor = ioClore;
	}

	public RMIRegulatedMotor getLine() {
		return line;
	}

	public void setLine(RMIRegulatedMotor line) {
		this.line = line;
	}

	public RMIRegulatedMotor getGate() {
		return gate;
	}

	public void setGate(RMIRegulatedMotor gate) {
		this.gate = gate;
	}

	public RMIRegulatedMotor getCounterLine() {
		return counterLine;
	}

	public void setCounterLine(RMIRegulatedMotor counterLine) {
		this.counterLine = counterLine;
	}

	public EV3TouchSensor getCounter() {
		return counter;
	}

	public void setCounter(EV3TouchSensor counter) {
		this.counter = counter;
	}

	public float[] getColor() {
		return color;
	}

	public void setColor(float[] color) {
		this.color = color;
	}
}
