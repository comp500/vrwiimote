package vrwiimote;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingWorker;

import vrwiimote.vridge.VRidgeControlChannel;
import vrwiimote.vridge.VRidgeControllerEndpoint;

public class VRidgeStart extends SwingWorker<Object, Object> {

	private static final Logger LOGGER = Logger.getLogger( VRidgeStart.class.getName() );
	
	public VRidgeStart() {
		super();
		LOGGER.addHandler(MainGUI.logHandler);
	}

	@Override
	protected Object doInBackground() throws Exception {
		VRidgeControlChannel controlChannel = new VRidgeControlChannel();
		VRidgeControllerEndpoint endpoint;
		try {
			endpoint = (VRidgeControllerEndpoint) controlChannel.connectEndpoint("Controller");
			LOGGER.log(Level.INFO, endpoint.getEndpointAddress());
			LOGGER.log(Level.INFO, String.valueOf(endpoint.getTimeoutSec()));
			endpoint.connect();
			LOGGER.log(Level.INFO, "Connected!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}
		return null;
	}
}
