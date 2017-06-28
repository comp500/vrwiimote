package vrwiimote;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
	private static final Logger LOGGER = Logger.getLogger( Main.class.getName() );
	
	public static void main(String[] args) throws Exception {
		MainGUI.main(null);
		LOGGER.log(Level.INFO, "Opened GUI");
		/*WiimoteApi test = new WiimoteApi();
		test.connect();*/
	}

}
