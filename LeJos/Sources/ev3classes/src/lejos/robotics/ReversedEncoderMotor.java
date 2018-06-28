package lejos.robotics;

class ReversedEncoderMotor implements EncoderMotor {

	EncoderMotor encoderMotor;
	
	ReversedEncoderMotor(EncoderMotor motor) {
		this.encoderMotor = motor;
	}
	
	@Override
	public int getPower() {
		return encoderMotor.getPower();
	}

	@Override
	public void setPower(int power) {
		encoderMotor.setPower(power);
	}

	@Override
	public void backward() {
		encoderMotor.forward();
	}

	@Override
	public void flt() {
		encoderMotor.flt();
	}

	@Override
	public void forward() {
		encoderMotor.backward();
	}

	@Override
	public boolean isMoving() {
		return encoderMotor.isMoving();
	}

	@Override
	public void stop() {
		encoderMotor.stop();
	}

	@Override
	public int getTachoCount() {
		return -encoderMotor.getTachoCount();
	}

	@Override
	public void resetTachoCount() {
		encoderMotor.resetTachoCount();
	}
}
