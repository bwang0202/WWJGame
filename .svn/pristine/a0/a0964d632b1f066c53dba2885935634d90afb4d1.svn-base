package mainMVC.model.dataPacket;

import java.util.UUID;

import javax.swing.JTextArea;

import comp310f13.rmiChat.IStatusFail;
import comp310f13.rmiChat.IStatusOk;
import provided.datapacket.ADataPacket;
import provided.datapacket.ADataPacketAlgoCmd;
import provided.datapacket.DataPacket;
import provided.datapacket.ICmd2ModelAdapter;
import provided.mixedData.IMixedDataDictionary;
import provided.mixedData.MixedDataKey;

public class MyGameStatusMsgCmd extends ADataPacketAlgoCmd<ADataPacket,MyGameStatusMsg,Object>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3265425009792130240L;
	private transient ICmd2ModelAdapter _cmd2MA;

	/**
	 * process the MyGameStatusMsg by putting it to a textarea designed for showing game status
	 * requires mixed data dictionary working on the client implementation.
	 */
	public ADataPacket apply(Class<?> index, DataPacket<MyGameStatusMsg> host,
			Object... params) {
		try{
		IMixedDataDictionary dict = _cmd2MA.getDataDict();
		MixedDataKey<JTextArea> keyTextArea = new MixedDataKey<JTextArea>(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"), "textArea", JTextArea.class);
		if(dict != null){ //server's getDataDict will return null;
			//if(dict.containsKey(keyTextArea)){
				dict.get(keyTextArea).setText(host.getData().getString());
		//}
		}
		StatusOk dok = new StatusOk();
		return new DataPacket<IStatusOk>(IStatusOk.class, _cmd2MA.getLocalUserStub(), dok);
		}catch(Exception e){
			return new DataPacket<IStatusFail>(IStatusFail.class, _cmd2MA.getLocalUserStub(), new StatusFail(e.getMessage(),host));
		}
	}
	/**
	 * setter for cmd2model adapter
	 */
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		// TODO Auto-generated method stub
		_cmd2MA = cmd2ModelAdpt;
	}

}
