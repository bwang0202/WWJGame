package mainMVC.model;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.UUID;

import comp310f13.rmiChat.IChatRoom;
import comp310f13.rmiChat.IChatRoomInvite;
import comp310f13.rmiChat.IUser;
/**
 * An interface that represents the invitation to a particular chatroom
 *
 */
public class MyChatRoomInvite implements IChatRoomInvite{

	private static final long serialVersionUID = 4216214116101011386L;
	private IChatRoom _room;
	private UUID _uuid;
	/**
	 * an invite is associated with a chatroom, and that chatroom field is private, so it's safe to do so.
	 * @param room
	 */
	public MyChatRoomInvite(IChatRoom room){
		_room = room;
		_uuid = UUID.randomUUID();
	}
	@Override
	public String getName() {
		return _room.getName();
	}

	@Override
	public UUID getUUID() {
		return _uuid;
	}

	@Override
	public Iterable<String> getUserNames() {
		ArrayList<String> list = new ArrayList<String>();
		Iterable<IUser> i = _room.getUsers();
		try{
			for(IUser user: i){
				list.add(user.getName());
			}
		}
		catch(RemoteException e){
			e.printStackTrace();
		}
		return list;
	}

}
