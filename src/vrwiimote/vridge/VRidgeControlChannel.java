package vrwiimote.vridge;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.zeromq.ZMQ;
import com.google.gson.Gson;

public class VRidgeControlChannel {
	private static final Logger LOGGER = Logger.getLogger( VRidgeControlChannel.class.getName() );
	private Gson g;
	private ZMQ.Context context;
	private ZMQ.Socket requester;

	public VRidgeControlChannel() {
		g = new Gson();
		context = ZMQ.context(1);
	}
	
	public void connect() {
		//  Socket to talk to server
		LOGGER.log(Level.FINE, "Connecting to VRidge control channel");
		requester = context.socket(ZMQ.REQ);
		requester.connect("tcp://localhost:38219");
	}
	
	public VRidgeStatusReply getStatus() {
		VRidgeStatusRequest req = new VRidgeStatusRequest(1);
		String reqJSON = g.toJson(req);
        LOGGER.log(Level.FINE, "Sending Status request");
        requester.send(reqJSON.getBytes(), 0);
        
        LOGGER.log(Level.FINE, "Awaiting reply");
        byte[] replyJSON = requester.recv(0);
        VRidgeStatusReply reply = g.fromJson(new String(replyJSON), VRidgeStatusReply.class);
        LOGGER.log(Level.FINE, "Got Status reply");
        return reply;
	}
	
	public VRidgeEndpointReply requestEndpoint(String endpointName) {
		VRidgeEndpointRequest req = new VRidgeEndpointRequest(1, endpointName);
		String reqJSON = g.toJson(req);
        LOGGER.log(Level.FINE, "Sending Endpoint request");
        requester.send(reqJSON.getBytes(), 0);
        
        LOGGER.log(Level.FINE, "Awaiting reply");
        byte[] replyJSON = requester.recv(0);
        VRidgeEndpointReply reply = g.fromJson(new String(replyJSON), VRidgeEndpointReply.class);
        LOGGER.log(Level.FINE, "Got Endpoint reply");
        return reply;
	}
	
	public void disconnect() {
		requester.close();
	}
	
	private VRidgeEndpoint findEndpoint(String endpointName, String endpointAddress, int timeoutSec) {
		switch (endpointName) {
			case "Controller":
				return new VRidgeControllerEndpoint(endpointAddress, timeoutSec);
			default:
				throw new IndexOutOfBoundsException("Endpoint not implemented");
		}
	}
	
	public VRidgeEndpoint connectEndpoint(String endpointName) throws Exception {
		this.connect();
		VRidgeStatusReply status = this.getStatus();
		boolean foundEndpoint = false;
		for (VRidgeStatusReply.VRidgeEndpointStatus endpoint : status.Endpoints) {
			if (endpoint.Name.equals(endpointName)) {
				foundEndpoint = true;
			}
		}
		if (foundEndpoint == false) {
			throw new IllegalArgumentException("Endpoint not found");
		}
		VRidgeEndpointReply endpointStatus = this.requestEndpoint(endpointName);
		if (endpointStatus.Code != 0) {
			throw new Exception("Endpoint already in use, code " + endpointStatus.Code);
		}
		this.disconnect();
		return this.findEndpoint(endpointName, endpointStatus.EndpointAddress, endpointStatus.TimeoutSec);
	}
	
	public class VRidgeControlMessage {
		public int Code; 
		public int ProtocolVersion;

		public VRidgeControlMessage(int protocolVersion, int code) {
			super();
			ProtocolVersion = protocolVersion;
			Code = code;
		}
	}
	
	public class VRidgeStatusRequest extends VRidgeControlMessage {
		public VRidgeStatusRequest(int protocolVersion) {
			super(protocolVersion, 2);
		}
	}

	public class VRidgeStatusReply extends VRidgeControlMessage {
		public VRidgeEndpointStatus[] Endpoints;

		public VRidgeStatusReply(int protocolVersion, int code, VRidgeEndpointStatus[] endpoints) {
			super(protocolVersion, code);
			Endpoints = endpoints;
		}

		public class VRidgeEndpointStatus {
			public String Name;
			public int ProtocolVersion;
			public int Code;
			
			public VRidgeEndpointStatus(String name, int protocolVersion, int code) {
				super();
				Name = name;
				ProtocolVersion = protocolVersion;
				Code = code;
			}
		}
	}
	
	public class VRidgeEndpointRequest extends VRidgeControlMessage {
		public String RequestedEndpointName;

		public VRidgeEndpointRequest(int protocolVersion, String requestedEndpointName) {
			super(protocolVersion, 1);
			RequestedEndpointName = requestedEndpointName;
		}
	}
	
	public class VRidgeEndpointReply extends VRidgeControlMessage {
		String EndpointAddress;
		int TimeoutSec;
		
		public VRidgeEndpointReply(int protocolVersion, int code, String endpointAddress, int timeoutSec) {
			super(protocolVersion, code);
			EndpointAddress = endpointAddress;
			TimeoutSec = timeoutSec;
		}
	}

}
