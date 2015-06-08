package mainMVC.model;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import provided.datapacket.ADataPacket;
import comp310f13.rmiChat.IChatRoom;
import comp310f13.rmiChat.IUser;
/**
 * Represents a chat room or "conversation", an encapsulation of the IUsers involved in the conversation. 
 * Conceptually, essentially a dispatcher to a collection of of IUser stubs. 
 * Note that instances of this class is NOT Remote objects, that is, they are NOT an RMI server objects! 
 * Entire IChatRoom instances are sent to remote hosts, not stubs.
 * one team is one chatroom
 */
public class MyChatRoom implements IChatRoom{

	private static final long serialVersionUID = -2424141779650677229L;
	private String _name;
	private ArrayList<IUser> _users;
	
	public MyChatRoom(String name){
		_name = name;
		_users = new ArrayList<IUser>();
	}
	
	public String getName() {
		return _name;
	}
	/**
	 * Locally add the given user to the chat room.  This 
	 * method does NOT add the user to the remote users' chatrooms.
	 * Assumes that an IAddUser datapacket was already sent
	 * out to all the current users in the chatroom.
	 * @param newUserStub The user stub to add to the chat room
	 */
	public void addLocalUser(IUser newUserStub) {
		int flag = 0;
		try{
			for(IUser user: _users){
				if(newUserStub.getUUID().compareTo(user.getUUID()) == 0){
					flag = 1;
				}
			}
			if(flag == 0){
				_users.add(newUserStub);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Locally remove the given user from the chatroom.  This
	 * method does NOT remove the user from the remote users' chatrooms. 
	 * @param userStub   The user to remove from the chatroom
	 */
	public void removeLocalUser(IUser userStub) {
		boolean result = _users.remove(userStub);
		if(!result){
			try {
				System.out.println("error removing userStub:" + userStub.getName() + " in Chatroom "+_name);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * getter of a list of users in this chatroom
	 */
	public Iterable<IUser> getUsers() {
		return _users;
	}
	/**
	 * send a datapacket to all people in this chatroom
	 */
	public Iterable<ADataPacket> sendMessage(ADataPacket dp) {
		// TODO Auto-generated method stub
		ArrayList<ADataPacket> results = new ArrayList<ADataPacket>();
		BlockingQueue<ADataPacket> bq = new LinkedBlockingQueue<ADataPacket>();
		for(IUser user: _users){
			try {
				bq.offer(user.receiveData(dp));
				ADataPacket temp = bq.take();
				System.out.println("finally received a dp as result");
				results.add(temp);
				//receive one dp and add it to results
			} catch (RemoteException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return results;
	}

}
