package mainMVC.view;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JComboBox;

import net.miginfocom.swing.MigLayout;

/**
 * Server main view
 * @author Bojun
 *
 */
public class MainFrame extends JFrame{
	/**
	 * Generated serialize ID
	 */
	private static final long serialVersionUID = 1202836021223196250L;
	private IModelAdapter _modelAdapter;
	private final JPanel northPanel = new JPanel();
	private final JScrollPane scrollPane = new JScrollPane();
	private final JTextArea mainTextArea = new JTextArea();
	private final JComboBox<String> ipComboBox = new JComboBox<String>();
	private final JTextField txtGameServerRunning = new JTextField();
	private final JTextField teamNameTextField = new JTextField();
	private final JButton btnMakeATeam = new JButton("Make a team");
	private final JComboBox<String> teamComboBox = new JComboBox<String>();
	private final JTextField chatTextField = new JTextField();
	private final JButton btnSend = new JButton("send");
	private final JButton btnSendmap = new JButton("sendmap");
	private final JButton btnSendcity = new JButton("end game");
	public MainFrame(IModelAdapter modelAdapter) {
		chatTextField.setToolTipText("type in message server wish to send");
		chatTextField.setColumns(10);
		teamNameTextField.setToolTipText("type in the new team's name");
		teamNameTextField.setColumns(10);
		txtGameServerRunning.setText("Game Server Running...");
		txtGameServerRunning.setEditable(false);
		txtGameServerRunning.setBackground(Color.BLUE);
		txtGameServerRunning.setColumns(10);
		_modelAdapter = modelAdapter;
		initGUI();
	}
	private void initGUI() {
		northPanel.setBackground(Color.BLUE);
		
		getContentPane().add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(new MigLayout("", "[86px][73px,grow][28px][51px,grow][28px][]", "[23px][]"));
		ipComboBox.setToolTipText("select a remote host");
		
		northPanel.add(ipComboBox, "cell 0 0,growx,aligny center");
		
		northPanel.add(txtGameServerRunning, "cell 1 0,growx");
		
		northPanel.add(teamNameTextField, "cell 3 0,growx");
		btnMakeATeam.setToolTipText("make a team");
		btnMakeATeam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String text = teamNameTextField.getText();
				_modelAdapter.makeNewTeam(text);
				teamComboBox.insertItemAt(text, 0);
			}
		});
		
		northPanel.add(btnMakeATeam, "cell 4 0");
		teamComboBox.setToolTipText("select a team to send them a text message");
		
		northPanel.add(teamComboBox, "cell 1 1,growx");
		
		northPanel.add(chatTextField, "cell 3 1,growx");
		btnSend.setToolTipText("send the msg to selected team");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				_modelAdapter.send(teamComboBox.getItemAt(teamComboBox.getSelectedIndex()), chatTextField.getText());
			}
		});
		
		northPanel.add(btnSend, "flowx,cell 4 1");
		btnSendmap.setToolTipText("send game map to everyone and start the game");
		btnSendmap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_modelAdapter.sendMap();
			}
		});
		
		northPanel.add(btnSendmap, "cell 4 1");
		btnSendcity.setToolTipText("end the game and tell every one result");
		btnSendcity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_modelAdapter.endGame();
			}
		});
		
		northPanel.add(btnSendcity, "cell 5 1");
		
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		mainTextArea.setBackground(Color.WHITE);
		mainTextArea.setEditable(false);
		
		scrollPane.setViewportView(mainTextArea);
	}
	/**
	 * start the view by setting it visible
	 */
	public void start(){
		setVisible(true);
	}
	/**
	 * when server is connected to a new remote host, the remote host's name is sent to this view
	 * and add that name to a combobox
	 * @param remoteHostName
	 */
	public void addRemoteHost(String remoteHostName){
		ipComboBox.insertItemAt(remoteHostName, 0);
	}
	/**
	 * append string in main text area
	 * @param string
	 */
	public void append(String string) {
		mainTextArea.append(string);
	}
}
