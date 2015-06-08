package mainMVC.model.dataPacket;
import provided.datapacket.ADataPacket;
import comp310f13.rmiChat.IStatusFail;
/**
 * concrete class of IStatusfail which says a datapacket is failed to be processed
 * @author Bojun
 *
 */
public class StatusFail implements IStatusFail{
	
	/**
	 * Generated serial uid
	 */
	private static final long serialVersionUID = 5987504660845610394L;
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
