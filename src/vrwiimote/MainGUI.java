package vrwiimote;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JSlider;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class MainGUI {

	private JFrame frmVrwiimote;
	private static final Logger LOGGER = Logger.getLogger(MainGUI.class.getName());
	public static final TextAreaHandler logHandler = new TextAreaHandler();
	private VRidgeRunner vridgeRunner;

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
		LOGGER.addHandler(logHandler);
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
		frmVrwiimote.setBounds(100, 100, 550, 300);
		frmVrwiimote.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmVrwiimote.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JPanel panelConnection = new JPanel();
		tabbedPane.addTab("Connection", null, panelConnection, null);
		JButton btnConnectToVridge = new JButton("Connect to VRidge");

		btnConnectToVridge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LOGGER.log(Level.INFO, "Starting VRidge connection");
				vridgeRunner = new VRidgeRunner();
				vridgeRunner.connectController();
			}
		});

		JLabel lblNotConnected = new JLabel("Not connected");

		JButton btnConnectWiimotes = new JButton("Connect Wiimotes");

		JLabel lblNoWiimotesConnected = new JLabel("No wiimotes connected");
		GroupLayout gl_panelConnection = new GroupLayout(panelConnection);
		gl_panelConnection
				.setHorizontalGroup(gl_panelConnection.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelConnection.createSequentialGroup().addContainerGap()
								.addGroup(gl_panelConnection.createParallelGroup(Alignment.LEADING)
										.addComponent(btnConnectToVridge).addComponent(btnConnectWiimotes))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(gl_panelConnection.createParallelGroup(Alignment.LEADING)
										.addComponent(lblNoWiimotesConnected).addComponent(lblNotConnected))
								.addGap(184)));
		gl_panelConnection
				.setVerticalGroup(gl_panelConnection.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelConnection.createSequentialGroup().addGap(11)
								.addGroup(gl_panelConnection.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnConnectToVridge).addComponent(lblNotConnected))
								.addGap(4)
								.addGroup(gl_panelConnection.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnConnectWiimotes).addComponent(lblNoWiimotesConnected))
								.addGap(4)));
		panelConnection.setLayout(gl_panelConnection);

		JPanel panelConfig = new JPanel();
		tabbedPane.addTab("Configuration", null, panelConfig, null);

		JLabel lblControllerDistance = new JLabel("Controller Distance");

		JSlider slider = new JSlider();
		slider.setMinorTickSpacing(5);
		slider.setMajorTickSpacing(20);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		GroupLayout gl_panelConfig = new GroupLayout(panelConfig);
		gl_panelConfig.setHorizontalGroup(gl_panelConfig.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelConfig.createSequentialGroup().addContainerGap()
						.addComponent(lblControllerDistance).addGap(2).addComponent(slider, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(126, Short.MAX_VALUE)));
		gl_panelConfig.setVerticalGroup(gl_panelConfig.createParallelGroup(Alignment.LEADING).addGroup(gl_panelConfig
				.createSequentialGroup()
				.addGroup(gl_panelConfig.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelConfig.createSequentialGroup().addGap(6).addComponent(slider,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(
								gl_panelConfig.createSequentialGroup().addGap(21).addComponent(lblControllerDistance)))
				.addContainerGap(182, Short.MAX_VALUE)));
		panelConfig.setLayout(gl_panelConfig);

		JPanel panelPreview = new JPanel();
		tabbedPane.addTab("Preview", null, panelPreview, null);
		panelPreview.setLayout(new BorderLayout(0, 0));

		JLabel lblNoWiimotesConnected_1 = new JLabel("No wiimotes connected");
		lblNoWiimotesConnected_1.setHorizontalAlignment(SwingConstants.CENTER);
		panelPreview.add(lblNoWiimotesConnected_1, BorderLayout.NORTH);
		
		JPanel previewPanel = new WiimotePreview();
		previewPanel.setBackground(Color.WHITE);
		panelPreview.add(previewPanel, BorderLayout.CENTER);

		JScrollPane logPane = new JScrollPane();
		tabbedPane.addTab("Log", null, logPane, null);

		JTextArea logTextArea = new JTextArea();
		logTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		logTextArea.setLineWrap(true);
		logTextArea.setEditable(false);
		logPane.setViewportView(logTextArea);
		logHandler.setTextArea(logTextArea);
	}
	
	@SuppressWarnings("serial")
	class WiimotePreview extends JPanel {

		public WiimotePreview() {
			Timer timer = new Timer();
			WiimotePreview test = this;
			timer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							test.repaint();
						}
					});
				}
			}, 0, 16);
		}

		public Dimension getPreferredSize() {
			return new Dimension(250,200);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			//Rectangle bounds = g.getClipBounds();
			Point loc = MouseInfo.getPointerInfo().getLocation();
			SwingUtilities.convertPointFromScreen(loc, this);
			g.drawString("Wiimote 0: " + loc.x + ", " + loc.y, 10, 20);
			
			//g.translate(bounds.width / 2, bounds.height / 2);
			g.setColor(new Color(255, 0, 0));
			g.fillRect(loc.x, loc.y, 5, 5);
		} 
	}

}
