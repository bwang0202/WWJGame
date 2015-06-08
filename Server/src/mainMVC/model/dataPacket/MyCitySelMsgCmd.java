package mainMVC.model.dataPacket;

import java.util.UUID;

import javax.swing.JTextField;

import comp310f13.rmiChat.IStatusOk;
import provided.datapacket.ADataPacket;
import provided.datapacket.ADataPacketAlgoCmd;
import provided.datapacket.DataPacket;
import provided.datapacket.ICmd2ModelAdapter;
import provided.mixedData.IMixedDataDictionary;
import provided.mixedData.MixedDataKey;
/**
 * only used for testing unknown data packet and cmd2ModelAdapter
 * cmd that only process datapacket of MyCitySelMsg
 * one instance of this class is sent upon request
 * @author Bojun
 *
 */
public class MyCitySelMsgCmd extends ADataPacketAlgoCmd<ADataPacket,MyCitySelMsg,Object>{

	/**
	 * generated uid
	 */
	private static final long serialVersionUID = -3644247636112672367L;
	/**
	 * adapter for communicating to local models
	 */
	private transient ICmd2ModelAdapter _cmd2MA;

	/**
	 * set one of the text area sent to client to a specific test string "hello world"
	 */
	public ADataPacket apply(Class<?> index, DataPacket<MyCitySelMsg> host,
			Object... params) {
		IMixedDataDictionary dict = _cmd2MA.getDataDict();
		MixedDataKey<JTextField> keyTextField = new MixedDataKey<JTextField>(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"), "textField", JTextField.class);
		if(dict != null){ //server's getDataDict will return null;
			dict.get(keyTextField).setText("hello world");
		}
		StatusOk dok = new StatusOk();
		return new DataPacket<IStatusOk>(IStatusOk.class, null, dok);
	}
	/**
	 * setter for cmd2model adapter
	 */
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		// TODO Auto-generated method stub
		_cmd2MA = cmd2ModelAdpt;
	}

}
