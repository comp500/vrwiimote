package vrwiimote.wiimote;

import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;

public class WiimoteApi {
	
	public WiimoteApi() {
		super();
	}
	
	public void connect() {
		System.out.println("Scanning for wiimotes...");
		Wiimote[] wiimotes = WiiUseApiManager.getWiimotes(1, true);
		System.out.println("Got wiimote");
        Wiimote wiimote = wiimotes[0];
        wiimote.activateMotionSensing();
        wiimote.addWiiMoteEventListeners(new WiimoteEventListener());
        System.out.println("Event listener added");
	}

}
