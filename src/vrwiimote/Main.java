package vrwiimote;

import vrwiimote.wiimote.WiimoteApi;

//import vrwiimote.vridge.VRidgeControlChannel;
//import vrwiimote.vridge.VRidgeControllerEndpoint;

public class Main {

	public static void main(String[] args) throws Exception {
		/*VRidgeControlChannel controlChannel = new VRidgeControlChannel();
		VRidgeControllerEndpoint endpoint = (VRidgeControllerEndpoint) controlChannel.connectEndpoint("Controller");
		System.out.println(endpoint.getEndpointAddress());
		System.out.println(endpoint.getTimeoutSec());
		endpoint.connect();
		System.out.println("Connected!");*/
		WiimoteApi test = new WiimoteApi();
		test.connect();
	}

}
