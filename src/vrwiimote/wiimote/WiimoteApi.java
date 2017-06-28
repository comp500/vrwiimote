package vrwiimote.wiimote;

import java.util.logging.Level;
import java.util.logging.Logger;

import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;

public class WiimoteApi {
	private static final Logger LOGGER = Logger.getLogger( WiimoteApi.class.getName() );
	
	public WiimoteApi() {
		super();
	}
	
	public void connect() {
		LOGGER.log(Level.INFO, "Scanning for wiimotes...");
		Wiimote[] wiimotes = WiiUseApiManager.getWiimotes(1, true);
		LOGGER.log(Level.INFO, "Got wiimote");
        Wiimote wiimote = wiimotes[0];
        wiimote.activateMotionSensing();
        wiimote.addWiiMoteEventListeners(new WiimoteEventListener());
        LOGGER.log(Level.INFO, "Event listener added");
	}

}
