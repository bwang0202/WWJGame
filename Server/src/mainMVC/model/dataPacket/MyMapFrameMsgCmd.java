package mainMVC.model.dataPacket;

import gov.nasa.worldwind.geom.Position;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import map.MapLayer;
import map.MapPanel;
import comp310f13.rmiChat.IStatusOk;
import comp310f13.rmiChat.IUser;
import provided.datapacket.ADataPacket;
import provided.datapacket.ADataPacketAlgoCmd;
import provided.datapacket.DataPacket;
import provided.datapacket.ICmd2ModelAdapter;
import provided.mixedData.IMixedDataDictionary;
import provided.mixedData.MixedDataKey;
/**
 * cmd that processes mapframmsg by adding mappanel and buttons, layer of annotation with action listeners that
 * will send back datapacket of certain type to the server Iuser, so that the player could communicate
 * back to server.
 * @author Bojun
 *
 */
public class MyMapFrameMsgCmd extends ADataPacketAlgoCmd<ADataPacket,MyMapFrameMsg,Object>{

	private static final long serialVersionUID = -8194572824593500401L;
	private transient ICmd2ModelAdapter _cmd2MA;
	private IUser _serverUserStub;
	/**
	 * setter of the server user stub. needs to be called right after instantiation.
	 * otherwise the action listeners can not send back data to server.
	 * @param serveruserstub
	 */
	public void setServerUserStub(IUser serveruserstub){
		_serverUserStub = serveruserstub;
	}
	/**
	 * process the map frame msg cmd by setting up map, buttons, annotations and add the whole thing to view of client
	 */
	public ADataPacket apply(Class<?> index, DataPacket<MyMapFrameMsg> host,
			Object... params) {
		//_cmd2MA.addComponent("map jframe", new MapPanel());
		JPanel jp = new JPanel();
		MapLayer _layer = new MapLayer();
		_layer.addAnnotation(new MyToggleAnnotation("Houston", "Houston", Position.fromDegrees(29.71, -95.399, 150), _serverUserStub, _cmd2MA.getLocalUserStub()));
		_layer.addAnnotation(new MyToggleAnnotation("New York", "New York", Position.fromDegrees(40.748974, -73.990288, 150), _serverUserStub, _cmd2MA.getLocalUserStub()));
		_layer.addAnnotation(new MyToggleAnnotation("Moscow", "Moscow", Position.fromDegrees(55.7500, 37.6167, 150), _serverUserStub,_cmd2MA.getLocalUserStub()));
		_layer.addAnnotation(new MyToggleAnnotation("Mexico City", "Mexico City", Position.fromDegrees(19.4328, -99.1333, 150), _serverUserStub,_cmd2MA.getLocalUserStub()));
//		cityNodes.add(new CityNode("Buenos Aires", "BA", -34.6033, -58.3817));
		_layer.addAnnotation(new MyToggleAnnotation("Buenos Aires", "Buenos Aires", Position.fromDegrees(-34.6033, -58.3817, 150), _serverUserStub,_cmd2MA.getLocalUserStub()));
//		cityNodes.add(new CityNode("Tokyo", "T", 35.6895, 139.6917));
		_layer.addAnnotation(new MyToggleAnnotation("Tokyo", "Tokyo", Position.fromDegrees(35.6895, 139.6917, 150),_serverUserStub,_cmd2MA.getLocalUserStub()));
//		cityNodes.add(new CityNode("Beijing", "B", 39.9139, 116.3917));
		_layer.addAnnotation(new MyToggleAnnotation("Beijing", "Beijing", Position.fromDegrees(39.9139, 116.3917, 150),_serverUserStub,_cmd2MA.getLocalUserStub()));
//		cityNodes.add(new CityNode("Hong Kong", "HK", 22.2783, 114.1589));
		_layer.addAnnotation(new MyToggleAnnotation("Hong Kong", "Hong Kong", Position.fromDegrees(22.2783, 114.1589, 150),_serverUserStub,_cmd2MA.getLocalUserStub()));
//		cityNodes.add(new CityNode("Mumbai", "MUM", 18.9750, 72.8258));
		_layer.addAnnotation(new MyToggleAnnotation("Mumbai", "Mumbai", Position.fromDegrees(18.9750, 72.8258, 150),_serverUserStub,_cmd2MA.getLocalUserStub()));
//		cityNodes.add(new CityNode("Paris", "P", 48.8567, 2.3508));
		_layer.addAnnotation(new MyToggleAnnotation("Paris", "Paris", Position.fromDegrees(48.8567, 2.3508, 150),_serverUserStub,_cmd2MA.getLocalUserStub()));
//		cityNodes.add(new CityNode("Cape Town", "CT", -33.9253, 18.4239));
		_layer.addAnnotation(new MyToggleAnnotation("Cape Town", "Cape Town", Position.fromDegrees(-33.9253, 18.4239, 150),_serverUserStub,_cmd2MA.getLocalUserStub()));
//		cityNodes.add(new CityNode("Vienna", "V", 48.205456,16.339202));
		_layer.addAnnotation(new MyToggleAnnotation("Vienna", "Vienna", Position.fromDegrees(48.205456,16.339202, 150),_serverUserStub,_cmd2MA.getLocalUserStub()));
		jp.setBorder(new EmptyBorder(5, 5, 5, 5));
		jp.setLayout(new BorderLayout(0, 0));
		MapPanel mp = new MapPanel();
		mp.addMapLayer(_layer);
		jp.add(mp,BorderLayout.CENTER);
		
		JTextArea textArea = new JTextArea();
		textArea.setMargin(new Insets(10,10,10,10));
		textArea.setColumns(20);
		jp.add(textArea, BorderLayout.EAST);
		
		JPanel southPanel = new JPanel();
		JLabel lblSend = new JLabel("Send ");
		southPanel.add(lblSend);
		
		final JTextField textField = new JTextField();
		southPanel.add(textField);
		textField.setColumns(10);
		
		JLabel lblHumansTo = new JLabel("Humans To");
		southPanel.add(lblHumansTo);
		String[] destinations = {"Mine","Farm", "Factory","Fish"};
		final JComboBox<String> comboBox = new JComboBox<String>(destinations);
		southPanel.add(comboBox);
		
		JButton btnGo = new JButton("Go!");
		southPanel.add(btnGo);
		jp.add(southPanel, BorderLayout.SOUTH);
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {String msg = textField.getText() + ";" + comboBox.getItemAt(comboBox.getSelectedIndex());
					_serverUserStub.receiveData(new DataPacket<MyStringMsg>(MyStringMsg.class, _cmd2MA.getLocalUserStub(), new MyStringMsg(msg)));
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		_cmd2MA.addComponent("map jframe", jp);
		IMixedDataDictionary dict = _cmd2MA.getDataDict();
		MixedDataKey<JTextArea> keyTextArea = new MixedDataKey<JTextArea>(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"), "textArea", JTextArea.class);
		if(dict != null){
			dict.put(keyTextArea, textArea);
		}
		StatusOk dok = new StatusOk();
		return new DataPacket<IStatusOk>(IStatusOk.class, _cmd2MA.getLocalUserStub(), dok);
	}

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		// TODO Auto-generated method stub
		_cmd2MA = cmd2ModelAdpt;
	}

}
