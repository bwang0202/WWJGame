package mainMVC.view;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
/**
 * the miniview that pops up when entering a new chatroom
 * @author Bojun
 *
 */
public class MiniView extends JFrame {

	private static final long serialVersionUID = -6605620430983740317L;
	private IMiniView2ModelAdapter _mv2ma;
	private int mostMsg = 0;
	private JPanel contentPane;
	private final JPanel northPanel = new JPanel();
	private final JPanel eastPanel = new JPanel();
	private final JPanel centerPanel = new JPanel();
	private final JScrollPane scrollPane = new JScrollPane();
	private final JTextArea textArea = new JTextArea();
	private final JTextField textField = new JTextField();
	private final JButton btnSend = new JButton("send");
	private final JButton btnLeave = new JButton("leave!");

	/**
	 * Create the frame.
	 */
	public MiniView(IMiniView2ModelAdapter mv2ma) {
		textField.setColumns(10);
		initGUI();
		_mv2ma = mv2ma;
	}
	private void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		//scrollPane.setSize(200,200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		northPanel.setBackground(Color.YELLOW);
		
		contentPane.add(northPanel, BorderLayout.NORTH);
		
		northPanel.add(textField);
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				_mv2ma.sendTextMessage(textField.getText());
			}
		});
		
		northPanel.add(btnSend);
		btnLeave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				_mv2ma.leave();
				setVisible(false);
			}
		});
		
		northPanel.add(btnLeave);
		eastPanel.setBackground(Color.ORANGE);
		
		contentPane.add(eastPanel, BorderLayout.EAST);
		centerPanel.setBackground(Color.GRAY);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		//contentPane.add(centerPanel, BorderLayout.CENTER);
		
		contentPane.add(scrollPane, BorderLayout.SOUTH);
		
		scrollPane.setViewportView(textArea);
	}
	
	public void start(){
		setVisible(true);
	}
	/**
	 * setter for mini model adapter
	 * @param mv2ma
	 */
	public void setMV2MA(IMiniView2ModelAdapter mv2ma){
		_mv2ma = mv2ma;
	}
	/**
	 * append a stirng to miniview's text area
	 * @param s
	 */
	public void append(String s){
		mostMsg = mostMsg++;
		if(mostMsg > 4){
			textArea.setText("");
			mostMsg = 0;;
		}else{
			textArea.append(s);
		}
	}
	/**
	 * add acomponent to miniview to the center of content panel
	 * @param newComp
	 */
	public void addComp(Component newComp) {
		// TODO Auto-generated method stub
		contentPane.add(newComp, BorderLayout.CENTER);
	}
}
