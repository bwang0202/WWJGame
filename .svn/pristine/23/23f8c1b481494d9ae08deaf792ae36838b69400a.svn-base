package mainMVC.model.dataPacket;

import provided.datapacket.ADataPacket;
import provided.datapacket.ADataPacketAlgoCmd;
import comp310f13.rmiChat.IAddCmd;
/**
 * Represents the data type containing information to add 
 * a new command to the data packet processing visitor.   
 * Implementations should be associated with the host ID 
 * IAddCommand.class
 */
public class AddCmd implements IAddCmd{
	private static final long serialVersionUID = -4938744964784419925L;
	private ADataPacketAlgoCmd<ADataPacket, ?, ?> _cmd;
	private Class<?> _index;
	/**
	 * constructor index class<?> object that specifies the index of the cmd
	 * @param index
	 * @param cmd cmd that's inside of this data
	 */
	public AddCmd(Class<?> index, ADataPacketAlgoCmd<ADataPacket, ?, ?> cmd){
		_index = index;
		_cmd = cmd;
	}
	@Override
	public Class<?> getID() {
		return _index;
	}

	@Override
	public ADataPacketAlgoCmd<ADataPacket, ?, ?> getNewCmd() {
		return _cmd;
	}

}
