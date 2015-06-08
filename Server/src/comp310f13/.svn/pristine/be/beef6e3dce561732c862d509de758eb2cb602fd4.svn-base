package comp310f13.rmiChat;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

/** 
 * Represents the non-conversation-specific operations the application offers to remote machines.
 * A stub of this class is what the local machine gets from a remote Registry.   A stub of 
 * this class is passed to the remote machine to enable
 * the remote machine to communicate back to the local machine. 
 * A stub of this type will be bound in the Registry with the name IHost.BOUND_NAME 
 * on the port IHost.CONNECTION_PORT 
 * <br><br>
 * Connection and invitation protocol:
 * <ol>
 * <li> Upon receipt of the remote <code>IHost</code> instance from the remote Registry, call <code>sendLocalHostStub(localHostStub)</code> on remote <code>IHost</code> instance to establish 2-way communications.</li>
 * <li> For each desired invitation, call <code>sendInvite(chatroomInfo)</code> on remote <code>IHost</code> instance.</li>
 * <li> If <code>true</code> is returned, call <code>addToChatRoom(localChatRoom)</code> on remote <code>IHost</code> instance.</li>
 * <li> Remote <code>IHost</code> instance is required to send out <code>IAddUser</code> to the supplied chat room as quickly as possible.</li> 
 * </ol>
 */
public interface IHost extends Remote {
	
	/**
	 * Port that game server IHost stubs use.
	 */
	public static final int CONNECTION_PORT_SERVER = 2101;
	
	/**
	 * Port that game client IHost stubs use.  
	 * 
	 * Note:  If running client and server on the same machine, 
	 * to prevent the client from disrupting the server's registration 
	 * of its IHost stub into the Registry, either
	 * - start the client first then the server (overwrites the client's registration), or
	 * - implement a command line or other kind of switch that disables the
	 * client's registration of its IHost stub into the Registry.
	 */
	public static final int CONNECTION_PORT_CLIENT = 2102;

	
	/**
	 * The name to which the IHost object is bound to in the Registry.
	 */
	public static final String BOUND_NAME = "ChatApp.host";
	
	/**
	 * Get a name that is associated with this connection.   Typically,
	 * this would be related to the name the associated IUser returns,
	 * though technically, this is the name of the computer, not 
	 * the user.
	 * @return A name as a string.
	 * @throws RemoteException  Required for RMI transactions.
	 */
	public String getName() throws RemoteException;
	
	
	/**
	 * Returns the unique UUID value associated with all IUsers associated
	 * with this IHost connection, i.e the same value
	 * returned by all this IHost's IUser stubs.
	 * Since value enables different IHost stubs with the same 
	 * name to be differentiated.
	 * @return The unique UUID for this IHost and all its IUsers.
	 * @throws RemoteException  Required for RMI transactions.
	 */	
	public UUID getUUID() throws RemoteException;
	
	/** 
	* Sends the stub of the local IHost to the remote system.
	* It is required that this method be called immediately upon receipt 
	* of an IHost stub to establish a two-way connection between the machines.
	*  
	* @param localHostStub The stub of the local IHost object 
	* @throws RemoteException  Required for RMI transactions.
	*/ 
	public void sendLocalHostStub(IHost localHostStub) throws RemoteException;
	
	/**
	 * Initiate an invite sequence.  
	 * @param localHostStub  The stub of the local host, so that the receiver knows who is requesting the invite.
	 * @throws RemoteException Required for RMI transactions.
	 */
	public void requestInvite(IHost localHostStub) throws RemoteException;

	
	/**
	 * Invite the remote user to join the a chat room from a set of rooms 
	 * described by the given iterable of IChatRoomInvite objects.   
	 * The purpose of these rooms is left to the discretion 
	 * of the IHost.  Typically, this method would be called in response to 
	 * requestInvite() being called.
	 * @param chatroomInfo An iterable of descriptions of chat rooms to join.
	 * @return The description of the selected room, from the given iterable of descriptions.
	 * @throws RemoteException  Required for RMI transactions.
	 */
	public UUID sendInvite(Iterable<IChatRoomInvite> chatroomInfo) throws RemoteException;

	
	/**
	 * Add IUser stub associated with this IHost to the given chat room and thus include the 
	 * given chat room as one in which this IHost is participating. 
	 * Calling this method assumes that the remote user (the person associated with this IHost instance) 
	 * has already accepted an invitation to the given chat room.   
	 * The remote user is required to immediately send out an IAddUser 
	 * datapacket to everyone in the given chatroom to add themselves to the room and minimize
	 * race conditions in which other copies of the chat room have been modified in the interim.
	 * @param localChatRoom  The local chat room to which the remote user has already accepted an invitation. 
	 * @return  true if the joining was successful, false otherwise.
	 * @throws RemoteException  Required for RMI transactions.
	 */
	public boolean addToChatRoom(IChatRoom localChatRoom) throws RemoteException;
	
}
