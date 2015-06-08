package clientMainMVC.model.dataPacket;

import comp310f13.rmiChat.IRequestCmd;
/**
 * data of requesting cmd, the type of cmd is specified by an id, class<?>
 * @author Bojun
 *
 */
public class RequestCmd implements IRequestCmd{

	/**
	 * generated uid
	 */
	private static final long serialVersionUID = -6302753702410718671L;
	/**
	 * id stored, the type of cmd that is requested
	 */
	private Class<?> _id;
	/**
	 * constructor
	 * @param id stored id
	 */
	public RequestCmd(Class<?> id){
		_id = id;
	}
	/**
	 * id stored
	 */
	public Class<?> getID() {
		return _id;
	}

}
