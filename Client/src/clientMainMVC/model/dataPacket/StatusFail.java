package clientMainMVC.model.dataPacket;

import provided.datapacket.ADataPacket;
import comp310f13.rmiChat.IStatusFail;

public class StatusFail implements IStatusFail{
	
	/**
	 * Generated serial uid
	 */
	private static final long serialVersionUID = -7443649890041607700L;
	/**
	 * message from the user
	 */
	private String _msg;
	/**
	 * the failed data packet
	 */
	private ADataPacket _dp;
	/**
	 * constructor 
	 * @param msg message from the user
	 * @param dp the failed data packet
	 */
	public StatusFail(String msg, ADataPacket dp){
		_msg = msg;
		_dp = dp;
	}
	/**
	 * Get any message from the user
	 * @return A String message from the user 
	 */
	public String getMsg() {
		return _msg;
	}

	/**
	 * Get the failed data packet
	 * @return A datapacket
	 */
	public ADataPacket getDataPacket() {
		return _dp;
	}

}
