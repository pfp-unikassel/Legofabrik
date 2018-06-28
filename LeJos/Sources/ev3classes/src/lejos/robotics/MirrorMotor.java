package lejos.robotics;

/**
 * <p>This class returns a motor that rotates in the reverse direction of a regular motor. All tachometer
 * readings are also reversed.</p> 
 * 
 * <p>Use the factory method MirrorMotor.invertMotor(RegulatedMotor) to retrieve an inverted motor.</p>
 * 
 * @author BB
 *
 */
public class MirrorMotor implements RegulatedMotor, RegulatedMotorListener {
	
	private RegulatedMotor regMotor;
	private RegulatedMotorListener regListener;
	
	/**
	 * Returns an inverted RegulatedMotor.
	 * @param motor A RegulatedMotor, such as Motor.A.
	 * @return An inverted RegulatedMotor.
	 */
	public static RegulatedMotor invertMotor(RegulatedMotor motor) {
		if(motor instanceof MirrorMotor) {
			((MirrorMotor) motor).regMotor.addListener(((MirrorMotor) motor).regListener);
			return ((MirrorMotor) motor).regMotor;
		} 
		return new MirrorMotor(motor);
	}
	
	/**
	 * Returns an inverted EncoderMotor.
	 * @param motor An EncoderMotor, such as NXTMotor.
	 * @return An inverted EncoderMotor.
	 */
	public static EncoderMotor invertMotor(EncoderMotor motor) {
		if(motor instanceof ReversedEncoderMotor) {
			return ((ReversedEncoderMotor) motor).encoderMotor;
		}
		return new ReversedEncoderMotor(motor);
	}
						
	private MirrorMotor(RegulatedMotor motor) {
		// Make motor listener this regListener
		regListener = motor.removeListener(); // OK if listener is null
		// Need to add this to motor listener
		motor.addListener(this);
		this.regMotor = motor;
	}
	
	@Override
	public void addListener(RegulatedMotorListener listener) {
		// Listener needs to be reversed too.
		regMotor.addListener(this);
		this.regListener = listener;
	}
	
	@Override
	public RegulatedMotorListener removeListener() {
		RegulatedMotorListener old = regListener;
		regListener = null;
		regMotor.removeListener();
		return old;
	}

	@Override
	public void flt(boolean immediateReturn) {
		regMotor.flt(immediateReturn);
	}

	@Override
	public int getLimitAngle() { 
		return -regMotor.getLimitAngle();// REVERSED
	}

	@Override
	public float getMaxSpeed() {
		return regMotor.getMaxSpeed();
	}

	@Override
	public int getSpeed() {
		return regMotor.getSpeed();
	}

	@Override
	public boolean isStalled() {
		return regMotor.isStalled();
	}

	@Override
	public void rotate(int angle) {
		this.rotate(angle, false);
	}

	@Override
	public void rotate(int angle, boolean immediateReturn) {
		regMotor.rotate(-angle, immediateReturn);// REVERSED
	}

	@Override
	public void rotateTo(int angle) {
		this.rotateTo(angle, false);
	}

	@Override
	public void rotateTo(int angle, boolean immediateReturn) {
		regMotor.rotateTo(-angle, immediateReturn);// REVERSED
	}

	@Override
	public void setAcceleration(int acceleration) {
		regMotor.setAcceleration(acceleration);
	}

	@Override
	public void setSpeed(int speed) {
		regMotor.setSpeed(speed);
	}

	@Override
	public void setStallThreshold(int error, int time) {
		regMotor.setStallThreshold(error, time);
	}

	@Override
	public void stop(boolean immediateReturn) {
		regMotor.stop(immediateReturn);
	}

	@Override
	public void waitComplete() {
		regMotor.waitComplete();
	}

	@Override
	public void backward() {
		regMotor.forward();// REVERSED
	}

	@Override
	public void flt() {
		this.flt(false);
	}

	@Override
	public void forward() {
		regMotor.backward(); // REVERSED
	}

	@Override
	public boolean isMoving() {
		return regMotor.isMoving();
	}

	@Override
	public void stop() {
		regMotor.stop(false);
	}

	@Override
	public int getRotationSpeed() {
		return regMotor.getRotationSpeed();
	}

	@Override
	public int getTachoCount() {
		return -regMotor.getTachoCount();// REVERSED
	}

	@Override
	public void resetTachoCount() {
		regMotor.resetTachoCount();
	}

	@Override
	public void rotationStarted(RegulatedMotor motor, int tachoCount,
			boolean stalled, long timeStamp) {
		if(regListener!=null)
			regListener.rotationStarted(this, -tachoCount, stalled, timeStamp);
	}

	@Override
	public void rotationStopped(RegulatedMotor motor, int tachoCount,
			boolean stalled, long timeStamp) {
		if(regListener!=null)
				regListener.rotationStopped(this, -tachoCount, stalled, timeStamp);
	}

	@Override
	public void close() {
		regMotor.close();
	}

    @Override
    public void synchronizeWith(RegulatedMotor[] syncList)
    {
        regMotor.synchronizeWith(syncList);
        
    }

    @Override
    public void startSynchronization()
    {
        regMotor.startSynchronization();
        
    }

    @Override
    public void endSynchronization()
    {
        regMotor.endSynchronization();
        
    }
}