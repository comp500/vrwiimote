package vrwiimote;

import java.util.logging.Level;
import java.util.logging.Logger;

import vrwiimote.vridge.VRidgeControlChannel;
import vrwiimote.vridge.VRidgeControllerEndpoint;

public class Main {
	private static final Logger LOGGER = Logger.getLogger( Main.class.getName() );
	
	public static void main(String[] args) throws Exception {
		VRidgeControlChannel controlChannel = new VRidgeControlChannel();
		VRidgeControllerEndpoint endpoint = (VRidgeControllerEndpoint) controlChannel.connectEndpoint("Controller");
		LOGGER.log(Level.INFO, endpoint.getEndpointAddress());
		LOGGER.log(Level.INFO, String.valueOf(endpoint.getTimeoutSec()));
		endpoint.connect();
		LOGGER.log(Level.INFO, "Connected!");
		/*WiimoteApi test = new WiimoteApi();
		test.connect();*/
	}

}
