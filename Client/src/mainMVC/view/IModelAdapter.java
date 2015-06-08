package mainMVC.view;
/**
 * interface between main view and main model
 * @author Bojun
 *
 */
public interface IModelAdapter {
	/**
	 * returns a string uniquely representing the remote host with its ip address
	 * returns null if connect was unsuccessful
	 * @param address the IP address we are conncecting to
	 * @return string uniquely representing the remote host i.e. its ip address
	 */
	public String connectTo(String address);
	/**
	 * returns a string uniquely representing a remote game.
	 * returns null if join was unsuccessful
	 * @param uniqueString string uniquely representing the remote host i.e. its host name
	 */
	public void joinGame(String uniqueString);
	/**
	 * tell the model what choice the user made to join one of the invites
	 * @param s has to be a string that can be parsed into int
	 */
	public void informChatroomChoice(String s);
}
