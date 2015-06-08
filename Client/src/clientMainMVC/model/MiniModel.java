package clientMainMVC.model;

import java.awt.Component;
import java.rmi.RemoteException;
import java.util.Date;

import clientMainMVC.model.dataPacket.RemoveUser;
import clientMainMVC.model.dataPacket.TextMessage;
import provided.datapacket.ADataPacket;
import provided.datapacket.DataPacket;
import comp310f13.rmiChat.IChatRoom;
import comp310f13.rmiChat.IRemoveUser;
import comp310f13.rmiChat.ITextMessage;
import comp310f13.rmiChat.IUser;

/**
 * A mini-model is specifiyed by a chatroom
 * @author Bojun
 *
 */
public class MiniModel {
	private IMiniModel2ViewAdapter _mm2VA;
	private IChatRoom _chatroom;
	private IUser _userStub;
	private Component _compmap;
	
	public MiniModel(IChatRoom chatroom, IUser user){
		_chatroom = chatroom;
		_userStub = user;
	}

	/**
	 * a method of installing a mini model view adapter
	 * @param mm2va
	 */
	public void setMM2VA(IMiniModel2ViewAdapter mm2va){
		_mm2VA = mm2va;
	}
	/**
	 * a append string s to mini view
	 * @param s
	 */
	public void append(String s){
		_mm2VA.append(s);
	}
	/**
	 * update mini view to this chatroom
	 * @param chatroom
	 */
	public void updateView(IChatRoom chatroom){
		_chatroom = chatroom;
		_mm2VA.append("\n Chatroom's name "+ _chatroom.getName() + "\n");
		_mm2VA.append("containing users: ");
		for(IUser user: _chatroom.getUsers()){
			try {
				_mm2VA.append(user.getName() + ", ");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		_mm2VA.append("\n");
	}
	/**
	 * send a text message to chatroom
	 * @param text
	 */
	public void sendTextMessage(String text) {
		ADataPacket dp;
		try {
			dp = new DataPacket<ITextMessage>(ITextMessage.class, _userStub, new TextMessage(_userStub.getName(), new Date(),text));
			_chatroom.sendMessage(dp);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return;
		
	}
	/**
	 * leave chatroom and send a remove user dp ot chatroom
	 */
	public void leave() {
		final ADataPacket dp = new DataPacket<IRemoveUser>(IRemoveUser.class, _userStub, new RemoveUser(_userStub));
		_chatroom.removeLocalUser(_userStub);
		(new Thread(){
			public void run(){
				_chatroom.sendMessage(dp);
			}
		}).start();
		
	}
	/**
	 * add a component to mini view
	 * @param newComp
	 */
	public void addCompp(Component newComp) {
		_compmap = newComp;
		_mm2VA.addComp(_compmap);
		_compmap.setVisible(true);
	}
}
