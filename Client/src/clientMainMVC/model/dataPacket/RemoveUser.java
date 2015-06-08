package clientMainMVC.model.dataPacket;

import comp310f13.rmiChat.IRemoveUser;
import comp310f13.rmiChat.IUser;
/**
 * remove user data
 * @author Bojun
 *
 */
public class RemoveUser implements IRemoveUser{

	private static final long serialVersionUID = 3732399565631419873L;
	/**
	 * the user need to be removed
	 */
	private IUser _user;
	/**
	 * constructor
	 * @param user the user to be stored
	 */
	public RemoveUser(IUser user){
		_user = user;
	}
	/**
	 * the stored user
	 */
	public IUser getUser() {
		return _user;
	}

}
