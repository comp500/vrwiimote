package vrwiimote;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import vrwiimote.vridge.VRidgeControlChannel;
import vrwiimote.vridge.VRidgeControllerEndpoint;

public class VRidgeRunner {

	private static final Logger LOGGER = Logger.getLogger( VRidgeRunner.class.getName() );
	private final ExecutorService threadPool = Executors.newFixedThreadPool(1);
	private VRidgeControlChannel controlChannel = new VRidgeControlChannel();
	private VRidgeControllerEndpoint controllerEndpoint;
	
	public VRidgeRunner() {
		LOGGER.addHandler(MainGUI.logHandler);
	}
	
	public void stop() {
		threadPool.execute(() -> handleStop());
		threadPool.shutdown();
	}
	
	private void handleStop() {
		controlChannel.disconnect();
		if (controllerEndpoint != null) {
			controllerEndpoint.disconnect();
		}
	}
	
	public void connectController() {
		threadPool.execute(() -> handleConnectController());
	}
	
	private void handleConnectController() {
		try {
			controllerEndpoint = (VRidgeControllerEndpoint) controlChannel.connectEndpoint("Controller");
			LOGGER.log(Level.INFO, controllerEndpoint.getEndpointAddress());
			LOGGER.log(Level.INFO, String.valueOf(controllerEndpoint.getTimeoutSec()));
			controllerEndpoint.connect();
			LOGGER.log(Level.INFO, "Connected!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}
	}

}
