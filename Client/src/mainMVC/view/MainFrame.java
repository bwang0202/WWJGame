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
 * main view for the client
 * type in ip address to connect,
 * choose remote host and click join to ask for chatroominvites
 * type in integer to make a choice of the  chatroom, do not click "chose" button unless you need to choose from chatroom invite
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
	private final JTextField ipTextField = new JTextField();
	private final JButton btnConnect = new JButton("Connect");
	private final JScrollPane scrollPane = new JScrollPane();
	private final JTextArea mainTextArea = new JTextArea();
	private final JComboBox<String> ipComboBox = new JComboBox<String>();
	private final JButton btnJoin = new JButton("Join");
	private final JTextField txtGameClientRunning = new JTextField();
	private final JPanel panel = new JPanel();
	private final JTextField choiceTextField = new JTextField();
	private final JButton btnChose = new JButton("chooes");
	/**
	 * constructor 
	 * @param modelAdapter
	 */
	public MainFrame(IModelAdapter modelAdapter) {
		choiceTextField.setToolTipText("type in chatroom choice using a integer number");
		choiceTextField.setColumns(10);
		txtGameClientRunning.setText("Game Client 1.0");
		txtGameClientRunning.setEditable(false);
		txtGameClientRunning.setBackground(Color.CYAN);
		txtGameClientRunning.setColumns(10);
		_modelAdapter = modelAdapter;
		ipTextField.setToolTipText("type in ip address to connect to remote host");
		ipTextField.setColumns(10);
		initGUI();
	}
	/**
	 * 
	 */
	private void initGUI() {
		northPanel.setBackground(Color.CYAN);
		
		getContentPane().add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(new MigLayout("", "[86px,grow][73px][28px][51px][28px]", "[23px][]"));
		
		northPanel.add(ipTextField, "cell 0 0,alignx left,aligny center");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String ipAddress = ipTextField.getText();
				String result = _modelAdapter.connectTo(ipAddress);
				if(result == null){
					mainTextArea.append("unable to connect to " + ipAddress);
				} else{
					ipComboBox.insertItemAt(result, 0);
				}
			}
		});
		
		northPanel.add(btnConnect, "cell 1 0,alignx left,aligny top");
		
		northPanel.add(txtGameClientRunning, "cell 0 1,growx");
		ipComboBox.setToolTipText("select a remote host to join their game");
		
		northPanel.add(ipComboBox, "cell 1 1,alignx left,aligny center");
		btnJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String temp = ipComboBox.getItemAt(ipComboBox.getSelectedIndex());
				_modelAdapter.joinGame(temp);
			}
		});
		
		northPanel.add(btnJoin, "cell 3 1,alignx left,aligny top");
		
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		mainTextArea.setBackground(Color.WHITE);
		mainTextArea.setEditable(false);
		
		scrollPane.setViewportView(mainTextArea);
		panel.setBackground(Color.GREEN);
		
		scrollPane.setColumnHeaderView(panel);
		
		panel.add(choiceTextField);
		btnChose.setToolTipText("click this only when you need to choose from a list of chat room invites");
		btnChose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(choiceTextField.getText() != null){
					_modelAdapter.informChatroomChoice(choiceTextField.getText());
				}else{
					mainTextArea.append("Please input a valid integer");
				}
			}
		});
		
		panel.add(btnChose);
	}
	/**
	 * start the view by setting it to visible
	 */
	public void start(){
		setVisible(true);
	}
	/**
	 * called by model, when got a hold of a new remote host stub
	 * @param remoteHostName
	 */
	public void addRemoteHost(String remoteHostName){
		ipComboBox.insertItemAt(remoteHostName, 0);
	}
	/**
	 * append string to view
	 * @param string
	 */
	public void append(String string) {
		mainTextArea.append(string);
	}
	/**
	 * facotry method producing a visible miniview with a mini-view to model adapter installed
	 * @param mv2ma
	 * @return
	 */
	public MiniView makeMiniView(IMiniView2ModelAdapter mv2ma){
		MiniView mv = new MiniView(mv2ma);
		mv.start();
		return mv;
	}
}
