package vrwiimote.vridge;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.zeromq.ZMQ;
import com.google.gson.Gson;

import vrwiimote.MainGUI;

public class VRidgeControlChannel {
	private static final Logger LOGGER = Logger.getLogger( VRidgeControlChannel.class.getName() );
	private Gson g;
	private ZMQ.Context context;
	private ZMQ.Socket requester;

	public VRidgeControlChannel() {
		g = new Gson();
		context = ZMQ.context(1);
		LOGGER.addHandler(MainGUI.logHandler);
	}
	
	public void connect() {
		//  Socket to talk to server
		LOGGER.log(Level.INFO, "Connecting to VRidge control channel");
		requester = context.socket(ZMQ.REQ);
		requester.connect("tcp://localhost:38219");
	}
	
	public VRidgeStatusReply getStatus() {
		VRidgeStatusRequest req = new VRidgeStatusRequest(1);
		String reqJSON = g.toJson(req);
        LOGGER.log(Level.INFO, "Sending Status request");
        requester.send(reqJSON.getBytes(), 0);
        
        LOGGER.log(Level.INFO, "Awaiting reply");
        byte[] replyJSON = requester.recv(0);
        VRidgeStatusReply reply = g.fromJson(new String(replyJSON), VRidgeStatusReply.class);
        LOGGER.log(Level.INFO, "Got Status reply");
        return reply;
	}
	
	public VRidgeEndpointReply requestEndpoint(String endpointName) {
		VRidgeEndpointRequest req = new VRidgeEndpointRequest(3, endpointName);
		String reqJSON = g.toJson(req);
        LOGGER.log(Level.INFO, "Sending Endpoint request");
        requester.send(reqJSON.getBytes(), 0);
        
        LOGGER.log(Level.INFO, "Awaiting reply");
        byte[] replyJSON = requester.recv(0);
        VRidgeEndpointReply reply = g.fromJson(new String(replyJSON), VRidgeEndpointReply.class);
        LOGGER.log(Level.INFO, "Got Endpoint reply");
        return reply;
	}
	
	public void disconnect() {
		if (requester != null) {
			requester.close();
		}
	}
	
	private VRidgeEndpoint findEndpoint(String endpointName, int endpointPort, int timeoutSec) {
		switch (endpointName) {
			case "Controller":
				return new VRidgeControllerEndpoint("tcp://localhost:" + endpointPort, timeoutSec);
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
		// Throw error for endpoint status if it != 0
		VridgeControlException.throwCodeError(endpointStatus.Code);
		this.disconnect();
		return this.findEndpoint(endpointName, endpointStatus.Port, endpointStatus.TimeoutSec);
	}
	
	public static class VridgeControlException extends RuntimeException {

		private static final long serialVersionUID = 1L;
		public final int Code;
		
		public VridgeControlException(String message, int code) {
			super(message);
			Code = code;
		}
		
		public static void throwCodeError(int code) {
			switch (code) {
			case 0:
				// Ignore, worked fine
			case 1:
				throw new VridgeControlException("API is not available because of undefined reason.", code);
			case 2:
				throw new VridgeControlException("API is in use by another client", code);
			case 3:
				throw new VridgeControlException("Client is trying to use something that requires API client to be updated to more recent version", code);
			case 4:
				throw new VridgeControlException("VRidge needs to be updated or client is not following protocol", code);
			default:
				throw new VridgeControlException("Unknown response code", code);
			}
		}
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
			VridgeControlException.throwCodeError(code);
			Endpoints = endpoints;
		}

		public class VRidgeEndpointStatus {
			public String Name;
			public int Code;
			
			public VRidgeEndpointStatus(String name, int code) {
				super();
				Name = name;
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
		int TimeoutSec;
		int Port;
		
		public VRidgeEndpointReply(int protocolVersion, int code, int timeoutSec, int port) {
			super(protocolVersion, code);
			VridgeControlException.throwCodeError(code);
			TimeoutSec = timeoutSec;
			Port = port;
		}
	}

}
