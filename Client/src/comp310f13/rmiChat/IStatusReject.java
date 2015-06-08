package comp310f13.rmiChat;

import java.io.Serializable;

import provided.datapacket.ADataPacket;


/**
 * A status type signifying that the sender acknowledges having 
 * received the contained data packet but for the reasons 
 * given by the contained message, has not processed the
 * data packet.
 * @author swong
 *
 */
public interface IStatusReject extends Serializable {
	
	/**
	 * Get any message from the user
	 * @return A String message from the user 
	 */
	public String getMsg();
	
	/**
	 * Get the rejected data packet
	 * @return A datapacket
	 */
	public ADataPacket getDataPacket();
}
