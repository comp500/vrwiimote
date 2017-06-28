package vrwiimote.vridge;

import org.zeromq.ZMQ;
import com.google.gson.Gson;

public abstract class VRidgeEndpoint {
	protected Gson g;
	protected ZMQ.Context context;
	protected ZMQ.Socket requester;
	String EndpointAddress;
	int TimeoutSec;
	
	public VRidgeEndpoint(String endpointAddress, int timeoutSec) {
		super();
		EndpointAddress = endpointAddress;
		TimeoutSec = timeoutSec;
		g = new Gson();
		context = ZMQ.context(1);
	}
	
	public void connect() {
		//  Socket to talk to server
		System.out.println("Connecting to VRidge endpoint channel");
		requester = context.socket(ZMQ.REQ);
		requester.connect(this.EndpointAddress);
	}
	
	public abstract void getStatus();
	
	public void disconnect() {
		requester.close();
	}

	public String getEndpointAddress() {
		return EndpointAddress;
	}

	public void setEndpointAddress(String endpointAddress) {
		EndpointAddress = endpointAddress;
	}

	public int getTimeoutSec() {
		return TimeoutSec;
	}

	public void setTimeoutSec(int timeoutSec) {
		TimeoutSec = timeoutSec;
	}

}
