package mainMVC.model.dataPacket;

import java.rmi.RemoteException;
import java.util.UUID;

import comp310f13.rmiChat.IStatusOk;
import provided.datapacket.ADataPacket;
import provided.datapacket.ADataPacketAlgoCmd;
import provided.datapacket.DataPacket;
import provided.datapacket.ICmd2ModelAdapter;
/**
 * cmd of processing string msg.
 * This game communicates server and client through special text message data packet sent by client's clicking some buttons and annotations
 * this cmd is never shipped to client as the datapacket is only sent to server, the server never send MyStringMsg to client.
 * @author Bojun
 *
 */
public class MyStringMsgCmd extends ADataPacketAlgoCmd<ADataPacket,MyStringMsg,Object>{

	/**
	 * generated uid
	 */
	private static final long serialVersionUID = -6439403382861939110L;
	private transient ICmd2ModelAdapter _cmd2MA;
	/**
	 * my server's cmd2model adapter's append implementation checks if the text is a GAMECOMMAND before displays it
	 * so add "GAMECOMMAND" to the text message and passes it to main model. and the game server main model's cmd2modeladapter
	 * will process the text message. so we could tell the server what are the players moves.
	 */
	public ADataPacket apply(Class<?> index, DataPacket<MyStringMsg> host,
			Object... params) {
		// TODO Auto-generated method stub
		//host.getData().getString();
		UUID userUUID;
		StatusOk dok = new StatusOk();
		try {
			userUUID = host.getSender().getUUID();
			_cmd2MA.append("GAMECOMMAND:" + host.getData().getString() + ":UUID=" + userUUID.toString());
			return new DataPacket<IStatusOk>(IStatusOk.class, null, dok);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new DataPacket<IStatusOk>(IStatusOk.class, null, dok);
	}
	/**
	 * setter
	 */
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		// TODO Auto-generated method stub
		_cmd2MA = cmd2ModelAdpt;
	}





}
