package JPushy.Gui;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import JPushy.Listener.SettingsListener;

/**
 * 
 * @author Marcel Benning
 * 
 */
public class SettingsGui extends JFrame {

	private JPanel	         contentPane;
	public JTextField	       updateServer;
	public JTextField	       lvlServer;

	public JCheckBox	       chckbxDebugMode;

	private SettingsListener	listener	= new SettingsListener();

	public SettingsGui() {
		listener.setGui(this);
		setTitle("Settings");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 315, 185);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		/*
		 * chckbxDebugMode = new JCheckBox("Debug Mode");
		 * chckbxDebugMode.setSelected
		 * (Core.getSettings().getSettings(Core.getSettings().debug)); JLabel
		 * lblUpdateServer = new JLabel("Update Server:");
		 * 
		 * updateServer = new JTextField();
		 * updateServer.setText(Core.getSettings().getSetting
		 * (Core.getSettings().defaultUpdateServer)); updateServer.setColumns(10);
		 * 
		 * JLabel lblLevelServer = new JLabel("Level Server:");
		 * 
		 * lvlServer = new JTextField();
		 * lvlServer.setText(Core.getSettings().getSetting
		 * (Core.getSettings().defaultLevelServer)); lvlServer.setColumns(10);
		 * 
		 * JButton btnSave = new JButton("Save"); btnSave.setActionCommand("save");
		 * btnSave.addActionListener(listener);
		 * 
		 * JButton btnExit = new JButton("Close without saving");
		 * btnExit.setActionCommand("exit_nosave");
		 * btnExit.addActionListener(listener);
		 * 
		 * JButton btnClose = new JButton("Close");
		 * btnClose.setActionCommand("exit"); btnClose.addActionListener(listener);
		 * 
		 * GroupLayout gl_contentPane = new GroupLayout(contentPane);
		 * gl_contentPane.
		 * setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment
		 * .LEADING).addGroup(
		 * gl_contentPane.createSequentialGroup().addGroup(gl_contentPane
		 * .createParallelGroup
		 * (Alignment.LEADING).addComponent(chckbxDebugMode).addGroup
		 * (gl_contentPane.
		 * createSequentialGroup().addContainerGap().addGroup(gl_contentPane
		 * .createParallelGroup
		 * (Alignment.LEADING).addComponent(lblUpdateServer).addComponent
		 * (lblLevelServer
		 * )).addGap(18).addGroup(gl_contentPane.createParallelGroup(Alignment
		 * .LEADING, false).addComponent(lvlServer).addComponent(updateServer,
		 * GroupLayout.DEFAULT_SIZE, 185,
		 * Short.MAX_VALUE))).addGroup(gl_contentPane.
		 * createSequentialGroup().addComponent
		 * (btnExit).addPreferredGap(ComponentPlacement
		 * .RELATED).addComponent(btnSave
		 * ).addPreferredGap(ComponentPlacement.RELATED
		 * ).addComponent(btnClose))).addContainerGap(58, Short.MAX_VALUE)));
		 * gl_contentPane
		 * .setVerticalGroup(gl_contentPane.createParallelGroup(Alignment
		 * .LEADING).addGroup(
		 * gl_contentPane.createSequentialGroup().addComponent(chckbxDebugMode
		 * ).addPreferredGap
		 * (ComponentPlacement.RELATED).addGroup(gl_contentPane.createParallelGroup
		 * (Alignment
		 * .BASELINE).addComponent(lblUpdateServer).addComponent(updateServer,
		 * GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
		 * GroupLayout.PREFERRED_SIZE
		 * )).addPreferredGap(ComponentPlacement.RELATED).addGroup
		 * (gl_contentPane.createParallelGroup
		 * (Alignment.BASELINE).addComponent(lblLevelServer).addComponent(lvlServer,
		 * GroupLayout.PREFERRED_SIZE, 20,
		 * GroupLayout.PREFERRED_SIZE)).addPreferredGap(ComponentPlacement.RELATED,
		 * 42,
		 * Short.MAX_VALUE).addGroup(gl_contentPane.createParallelGroup(Alignment
		 * .BASELINE
		 * ).addComponent(btnExit).addComponent(btnSave).addComponent(btnClose))));
		 * contentPane.setLayout(gl_contentPane);
		 */
	}
}
