package vrwiimote;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.JLabel;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI {

	private JFrame frmVrwiimote;
	private static final Logger LOGGER = Logger.getLogger( MainGUI.class.getName() );

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI window = new MainGUI();
					window.frmVrwiimote.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainGUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// Ignore look and feel exceptions
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmVrwiimote = new JFrame();
		frmVrwiimote.setTitle("VRWiimote");
		frmVrwiimote.setBounds(100, 100, 450, 300);
		frmVrwiimote.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmVrwiimote.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("VRidge", null, panel_2, null);
		JButton btnConnectToVridge = new JButton("Connect to VRidge");
		panel_2.add(btnConnectToVridge);
		
		JLabel lblNotConnected = new JLabel("Not connected");
		panel_2.add(lblNotConnected);
		
		btnConnectToVridge.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e)
		    {
		    	LOGGER.log(Level.INFO, "Starting VRidge connection");
		        new VRidgeStart().execute();
		    }
		});
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Wiimote", null, panel_1, null);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnConnectWiimotes = new JButton("Connect Wiimotes");
		panel_1.add(btnConnectWiimotes);
		
		JLabel lblNoWiimotesConnected = new JLabel("No wiimotes connected");
		panel_1.add(lblNoWiimotesConnected);
		
		JScrollPane scrollPane = new JScrollPane();
		tabbedPane.addTab("Log", null, scrollPane, null);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setFont(new Font("Monospaced", Font.PLAIN, 12));
		textArea_1.setLineWrap(true);
		textArea_1.setEditable(false);
		scrollPane.setViewportView(textArea_1);
	}

}
