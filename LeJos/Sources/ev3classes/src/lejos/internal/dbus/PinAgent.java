package lejos.internal.dbus;

import org.freedesktop.dbus.Path;
import org.freedesktop.dbus.UInt32;

public class PinAgent implements Agent {
	
	private String pin;
	
	public PinAgent(String pin) {
		this.pin = pin;
	}

    @Override
	public void Authorize(Path device, String uuid) {
    	System.out.println("Authorize called");
    }

    @Override
	public void ConfirmModeChange(String mode)  {
    	System.out.println("ConfirmModeChange called");
    }

    @Override
	public void DisplayPasskey(Path device, UInt32 passkey, byte entered) {
    }

    @Override
	public void RequestConfirmation(Path device, UInt32 passkey) {
    }

    @Override
	public UInt32 RequestPasskey(Path device) {
    	System.out.println("Request pass key called for " + device);
        return null;
    }

    @Override
	public String RequestPinCode(Path device)  {
    	System.out.println("Request pin code called for " + device);
        return pin;
    }

    @Override
	public void Cancel() {
    	System.out.println("Cancel called");
    }

    @Override
	public void Release() {
    	System.out.println("Release called");
    }

    @Override
	public boolean isRemote() {
        return false;
    }

}
