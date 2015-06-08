package mainMVC.model.dataPacket;

import java.util.Date;

import comp310f13.rmiChat.ITextMessage;
/**
 * A data type that encapsulates a text message
 * Implementations should be associated with the host ID
 * ITextMessage.class
 */
public class TextMessage implements ITextMessage{

	private static final long serialVersionUID = -203736699620797621L;
	private String _name;
	private Date _date;
	private String _msg;
	/**
	 * 
	 * @param name the name of sender
	 * @param date when it is sent
	 * @param msg what is says
	 */
	public TextMessage(String name, Date date, String msg){
		_name = name;
		_date = date;
		_msg = msg;
	}
	@Override
	public String getName() {
		return _name;
	}

	@Override
	public Date getTime() {
		return _date;
	}

	@Override
	public String getMsg() {
		return _msg;
	}

}
