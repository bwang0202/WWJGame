package clientMainMVC.model.dataPacket;

import java.util.Date;

import comp310f13.rmiChat.ITextMessage;
/**
 * a data of text message, with sender's name, date, the message
 * @author Bojun
 *
 */
public class TextMessage implements ITextMessage{

	private static final long serialVersionUID = -4925770300753416467L;
	private String _name;
	private Date _date;
	private String _msg;
	/**
	 * 
	 * @param name who sent it
	 * @param date when it sent
	 * @param msg what is says
	 */
	public TextMessage(String name, Date date, String msg){
		_name = name;
		_date = date;
		_msg = msg;
	}
	/**
	 * getter for name
	 */
	public String getName() {
		return _name;
	}
	/**
	 * getter for date
	 */
	public Date getTime() {
		return _date;
	}
	/**
	 * getter for msg
	 */
	public String getMsg() {
		return _msg;
	}

}
