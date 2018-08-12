package vrwiimote.vridge;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.riftcat.vridge.api.client.java.VridgeApi.ControllerStateRequest;
import com.riftcat.vridge.api.client.java.VridgeApi.VRController;
import com.riftcat.vridge.api.client.java.VridgeApi.VRControllerState_t;

public class VRidgeControllerEndpoint extends VRidgeEndpoint {

	public VRidgeControllerEndpoint(String endpointAddress, int timeoutSec) {
		super(endpointAddress, timeoutSec);
	}

	@Override
	public void getStatus() {
		// TODO Auto-generated method stub
		
	}
	
	public void setControllerPosTest() {
		Matrix4f matrix = getMatrix();
		
		VRController test = VRController.newBuilder(VRController.getDefaultInstance())
				.setButtonState(VRControllerState_t.getDefaultInstance())
				.addOrientationMatrix(matrix.m00)
				.addOrientationMatrix(matrix.m10)
				.addOrientationMatrix(matrix.m20)
				.addOrientationMatrix(matrix.m30)
				.addOrientationMatrix(matrix.m01)
				.addOrientationMatrix(matrix.m11)
				.addOrientationMatrix(matrix.m21)
				.addOrientationMatrix(matrix.m31)
				.addOrientationMatrix(matrix.m02)
				.addOrientationMatrix(matrix.m12)
				.addOrientationMatrix(matrix.m22)
				.addOrientationMatrix(matrix.m32)
				.addOrientationMatrix(matrix.m03)
				.addOrientationMatrix(matrix.m13)
				.addOrientationMatrix(matrix.m23)
				.addOrientationMatrix(matrix.m33)
				.setStatus(0)
				.setControllerId(0)
				.build();
		
		ControllerStateRequest controller = ControllerStateRequest
				.newBuilder(ControllerStateRequest.getDefaultInstance())
				.setVersion(3)
				.setOrigin(0)
				.setTaskType(1)
				.setControllerState(test)
				.build();

		System.out.println(controller.toString());
		requester.send(controller.toByteArray(), 0);
	}
	
	public static Matrix4f getMatrix() {
		Quat4f quat = new Quat4f();
		Vector3f vec = new Vector3f(0F, 1F, 0F);
		
		return new Matrix4f(quat, vec, 1);
	}

}
