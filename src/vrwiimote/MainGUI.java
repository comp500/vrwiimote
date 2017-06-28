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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

public class MainGUI {

	private JFrame frmVrwiimote;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// Ignore look and feel exceptions
		}
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
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		tabbedPane.addTab("VRidge", null, splitPane, null);
		
		JPanel panel_2 = new JPanel();
		splitPane.setLeftComponent(panel_2);
		JButton btnConnectToVridge = new JButton("Connect to VRidge");
		panel_2.add(btnConnectToVridge);
		
		JLabel lblNotConnected = new JLabel("Not connected");
		panel_2.add(lblNotConnected);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		splitPane.setRightComponent(textArea);
		
		btnConnectToVridge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblNotConnected.setText("Connecting");
				textArea.setText("Starting connection...");
			}
		});
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Wiimote", null, panel_1, null);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnConnectWiimotes = new JButton("Connect Wiimotes");
		panel_1.add(btnConnectWiimotes);
		
		JLabel lblNoWiimotesConnected = new JLabel("No wiimotes connected");
		panel_1.add(lblNoWiimotesConnected);
	}

}
