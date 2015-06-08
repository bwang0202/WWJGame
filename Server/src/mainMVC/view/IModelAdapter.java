package mainMVC.view;
/**
 * interface between view and model
 * @author Bojun
 *
 */
public interface IModelAdapter {
	/**
	 * make a new team in the model with text as team name
	 * @param text
	 */
	public void makeNewTeam(String text);
	/**
	 * send one team a message of text
	 * @param teamName the team who receives message
	 * @param text the message
	 */
	public void send(String teamName, String text);
	/**
	 * send the game map to all teams and users
	 */
	public void sendMap();
	/**
	 * end the game and find out the winner and inform everyone.
	 */
	public void endGame();
}
