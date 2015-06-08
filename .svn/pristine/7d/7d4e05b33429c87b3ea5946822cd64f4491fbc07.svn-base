package clientMainMVC.model;

import java.awt.Component;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JFrame;

import clientMainMVC.model.dataPacket.AddUser;
import clientMainMVC.model.dataPacket.RequestCmd;
import clientMainMVC.model.dataPacket.StatusFail;
import clientMainMVC.model.dataPacket.StatusOk;
import comp310f13.rmiChat.*;
import provided.datapacket.*;
import provided.mixedData.IMixedDataDictionary;
import provided.mixedData.MixedDataDictionary;
import provided.rmiUtils.*;
import provided.util.IVoidLambda;
/**
 * main model of client 
 * 
 * @author Bojun
 *
 */
public class MainModel {
	/**
	 * blocking queue used for choosing a chatroom invite
	 */
	private BlockingQueue<Integer> choiceQueue = new LinkedBlockingQueue<Integer>();
	/**
	 * method called when the user made a choice in view
	 * @param s
	 */
	public void informChatroomChoice(String s){
		int i = new Integer(s);
		choiceQueue.offer(i);
	}
	/**
	 * uuid shared by server host and server IUser
	 */
	private UUID _uuid = UUID.randomUUID();
	/**
	 * mixed data dictionary shared by cmds.
	 */
	private IMixedDataDictionary mixdict;
	/**
	 * a dictionary of connected remote host keyed with their uuid
	 */
	private HashMap<UUID, IHost> idRemoteHostDict = new HashMap<UUID, IHost>();
	/**
	 * a dictionary from a name string to remote host 
	 */
	private HashMap<String, IHost> nameRemoteHostDict = new HashMap<String, IHost>();
	/**
	 * client local host and its stub
	 */
	private IHost _host;
	private IHost _hostStub;
	/**
	 * client local IUser and its stub
	 */
	private IUser _user;
	private IUser _userStub;
	/**
	 * the chatroom that this client is currently in
	 */
	private IChatRoom _room;
	/**
	 * the minimodel for the current chatroom
	 */
	private MiniModel _miniModel;
	/**
	 * a visitor of cmds that are used for processing datapackets
	 */
	private DataPacketAlgo<ADataPacket, Object> _visitor;
	/**
	 * The adapter to the view
	 */
	private IViewAdapter _viewAdapter;
	/**
	 * the cmd to model adapter, shared by all data packet cmds
	 */
	private ICmd2ModelAdapter _cmd2MA;

	/**
	 * The RMI Registry
	 */
	private Registry registry;

	/**
	 * A command used as a wrapper around the view adapter for the IRMIUtils and the ComputeEngine.
	 */
	private IVoidLambda<String> outputCmd = new IVoidLambda<String> (){
		public void apply(String... strs){
			for(String s: strs)_viewAdapter.append(s);
		}
	};
	
	/**
	 * Utility object used to get the Registry
	 */
	private IRMIUtils rmiUtils = new RMIUtils(outputCmd);
	/**
	 * The constructor for the class
	 * 
	 * @param view
	 *            The adapter to the view
	 */
	public MainModel(IViewAdapter viewAdpt) {
		_viewAdapter = viewAdpt;
	}
	/**
	 * instantiate my IHost object and bind it to IHost.BOUND_NAME on the local registry.
	 */
	public void start() {

		rmiUtils.startRMI(IRMI_Defs.CLASS_SERVER_PORT_CLIENT);
		 mixdict = new MixedDataDictionary();
		 _cmd2MA = new ICmd2ModelAdapter(){
			 	/**
			 	 * return local user stub
			 	 */
				public IUser getLocalUserStub() {
					return _userStub;
				}
				/**
				 * append a string some where in the local model
				 */
				public void append(String s) {
					_miniModel.append(s);
				}
				/**
				 * add a component somewhere in the view
				 */
				public void addComponent(String name, final Component newComp) {
					(new JFrame(){
						private static final long serialVersionUID = -2401588886177882952L;
						public void start(){
							getContentPane().add(newComp);
							setVisible(true);
						}
					}).start();
				}
				/**
				 * the mixed data dictionary shared by all cmds
				 */
				public IMixedDataDictionary getDataDict() {
					return mixdict;
				}};
		_visitor = new DataPacketAlgo<ADataPacket, Object>(new ADataPacketAlgoCmd<ADataPacket, Object, Object>(){
			/**
			 * generated serial uid.
			 */
			private static final long serialVersionUID = 1L;
			/**
			 * default cmd for our visitor, it sends out request cmd and execute the result , which should be a IAddCmd dp
			 */
			public ADataPacket apply(Class<?> index, DataPacket<Object> host,
					Object... params) {
				System.out.println("entering default cmd, sending requestcmd");
				try {
					ADataPacket dad = host.getSender().receiveData(new DataPacket<IRequestCmd>(IRequestCmd.class, _userStub, new RequestCmd(index)));
					return dad.execute(_visitor, host);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return new DataPacket<IStatusFail>(IStatusFail.class, _userStub, new StatusFail(e.getMessage(),host));
				}
			}
			/**
			 * no-op
			 */
			public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
				//no-op
			}});
		_visitor.setCmd(IAddUser.class, new ADataPacketAlgoCmd<ADataPacket, IAddUser, Object>(){

			/**
			 * generated serial id
			 */
			private static final long serialVersionUID = 1L;
			/**
			 * add the user into local chatroom
			 */
			public ADataPacket apply(Class<?> index, DataPacket<IAddUser> host,
					Object... params) {
				try{
				IAddUser d = host.getData();
				_room.addLocalUser(d.getUser());
				_miniModel.updateView(_room);
				return new DataPacket<IStatusOk>(IStatusOk.class, _userStub, new StatusOk());
				}catch(Exception e){
					e.printStackTrace();
					return new DataPacket<IStatusFail>(IStatusFail.class, _userStub, new StatusFail(e.getMessage(),host));
				}
			}
			/**
			 * no-op
			 */
			public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
			}});
	
		_visitor.setCmd(IStatusOk.class, new ADataPacketAlgoCmd<ADataPacket, IStatusOk, Object>(){

			/**
			 * generated uid
			 */
			private static final long serialVersionUID = 1L;
			/**
			 * print ok status to view
			 */
			public ADataPacket apply(Class<?> index,
					DataPacket<IStatusOk> host, Object... params) {
				try {
					_viewAdapter.append(host.getSender().getName() + "says ok \n");
					//return new DataPacket<IStatusOk>(IStatusOk.class, _userStub, new StatusOk());
					return null;
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return new DataPacket<IStatusFail>(IStatusFail.class, _userStub, new StatusFail(e.getMessage(),host));
				}
			}
			/**
			 * no-op
			 */
			public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
				// TODO Auto-generated method stub
				
			}});
		
		_visitor.setCmd(IRemoveUser.class, new ADataPacketAlgoCmd<ADataPacket, IRemoveUser, Object>(){

			/**
			 * generated uid
			 */
			private static final long serialVersionUID = 1L;
			/**
			 * remove a user in local chatroom
			 */
			public ADataPacket apply(Class<?> index,
					DataPacket<IRemoveUser> host, Object... params) {
				try{
				IRemoveUser d = host.getData();
				_room.removeLocalUser(d.getUser());
				_miniModel.updateView(_room);
				return new DataPacket<IStatusOk>(IStatusOk.class, _userStub, new StatusOk());
				}catch(Exception e){
					e.printStackTrace();
					return new DataPacket<IStatusFail>(IStatusFail.class, _userStub, new StatusFail(e.getMessage(),host));
				}
			}
			/**
			 * no-op
			 */
			public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
				// TODO Auto-generated method stub
				
			}});

		_visitor.setCmd(IStatusFail.class, new ADataPacketAlgoCmd<ADataPacket, IStatusFail, Object>(){

			/**
			 * generated uid
			 */
			private static final long serialVersionUID = 1L;
			/**
			 * prints fail status from someone to view
			 */
			public ADataPacket apply(Class<?> index,
					DataPacket<IStatusFail> host, Object... params) {
				try{
				IStatusFail d = host.getData();
				_viewAdapter.append(host.getSender().getName()+" return a IStatusFail"+d.getMsg());
				return new DataPacket<IStatusOk>(IStatusOk.class, _userStub, new StatusOk());
				}catch(Exception e){
					e.printStackTrace();
					return new DataPacket<IStatusFail>(IStatusFail.class, _userStub, new StatusFail(e.getMessage(),host));
				
				}
			}
			/**
			 * no-op
			 */
			public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
				// TODO Auto-generated method stub
				
			}});
		_visitor.setCmd(IStatusReject.class, new ADataPacketAlgoCmd<ADataPacket, IStatusReject, Object>(){

			/**
			 * generated uid
			 */
			private static final long serialVersionUID = 1L;
			/**
			 * prints someone reject with a message to view
			 */
			public ADataPacket apply(Class<?> index,
					DataPacket<IStatusReject> host, Object... params) {
				try{
				IStatusReject d = host.getData();
				_viewAdapter.append(host.getSender().getName()+" return a IStatusReject"+ d.getMsg());
				return new DataPacket<IStatusOk>(IStatusOk.class, _userStub, new StatusOk());
				}catch(Exception e){
					e.printStackTrace();
					return new DataPacket<IStatusFail>(IStatusFail.class, _userStub, new StatusFail(e.getMessage(),host));
				
				}
			}
			/**
			 * no-op
			 */
			public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
				// TODO Auto-generated method stub
				
			}});
		_visitor.setCmd(ITextMessage.class, new ADataPacketAlgoCmd<ADataPacket, ITextMessage, Object>(){

			/**
			 * default uid
			 */
			private static final long serialVersionUID = 1L;
			/**
			 * print the msg with name time to mini view
			 */
			public ADataPacket apply(Class<?> index,
					DataPacket<ITextMessage> host, Object... params) {
				try{
				ITextMessage d = host.getData();
				_miniModel.append("\n" + d.getTime() + d.getName() + " says: " + d.getMsg() + ". \n");
				return new DataPacket<IStatusOk>(IStatusOk.class, _userStub, new StatusOk());
				}catch(Exception e){
					e.printStackTrace();
					return new DataPacket<IStatusFail>(IStatusFail.class, _userStub, new StatusFail(e.getMessage(),host));
				
				}
			}
			/**
			 * no-op
			 */
			public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
				// TODO Auto-generated method stub
				
			}});
		
		_visitor.setCmd(IAddCmd.class, new ADataPacketAlgoCmd<ADataPacket, IAddCmd, Object>(){

			/**
			 * default uid
			 */
			private static final long serialVersionUID = 1L;
			/**
			 * take cmd out of IAddCmd and put it into client visitor, execute visitor with params[0]
			 * @param params[0] should be the unprocessed datapacket
			 * this cmd is called upon receiving IAddCmd in default cmd, and my default cmd passes unprocessed dp as param[0]
			 */
			@SuppressWarnings("unchecked")
			public ADataPacket apply(Class<?> index, DataPacket<IAddCmd> host,
					Object... params) {
				try{
				System.out.println("got addcmd and install it into my visitor");
				IAddCmd d = host.getData();
				ADataPacketAlgoCmd<ADataPacket, ?, Object> cmd = (ADataPacketAlgoCmd<ADataPacket, ?, Object>)d.getNewCmd();
				cmd.setCmd2ModelAdpt(_cmd2MA);
				_visitor.setCmd(d.getID(), cmd);
				return ((ADataPacket)params[0]).execute(_visitor, _viewAdapter);
				}catch(Exception e){
					e.printStackTrace();
					return new DataPacket<IStatusFail>(IStatusFail.class, _userStub, new StatusFail(e.getMessage(),host));
				
				}
			}
			/**
			 * no-op
			 */
			public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
			}});
		
		try {
			_host = new IHost(){
				/**
				 * local host name
				 */
				public String getName() throws RemoteException {
					return "bw6 client IHost";
				}
				/**
				 * local host uuid
				 */
				public UUID getUUID() throws RemoteException {
					return _uuid;
				}
				
				@Override
				/**
				 *non-op
				 */
				public void sendLocalHostStub(IHost localHostStub)
						throws RemoteException {
					return;
					
				}

				@Override
				/**
				 * non-op for client host.
				 */
				public void requestInvite(IHost localHostStub)
						throws RemoteException {
					return;
				}
				/**
				 * some one sent me a list of IChatroominvite, this method prints the invites info and returns
				 * a uuid associated chosen invite.
				 */
				public UUID sendInvite(Iterable<IChatRoomInvite> chatroomInfo)
						throws RemoteException {
					_viewAdapter.append("\n Please choose from the following chatrooms \n");
					HashMap<Integer, UUID> intUUIDDict = new HashMap<Integer, UUID>();
					int i = 1;
					for(IChatRoomInvite invite: chatroomInfo){
						intUUIDDict.put(i, invite.getUUID());
						
						_viewAdapter.append("ChatRoom #" + i + ": " + invite.getName() + "* : \n");
						for(String name: invite.getUserNames()){
							_viewAdapter.append(" "+name+",");
						}
						_viewAdapter.append("\n");
						i++;
					}
					
					try {
						int ii = choiceQueue.take();
						System.out.println("blocking queue , user finally make a choice"+i);
						return intUUIDDict.get(ii);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return IChatRoomInvite.NONE;
					}
				}
				/**
				 * add local IUser to this localChatRoom
				 * return true if succeed
				 */
				public boolean addToChatRoom(IChatRoom localChatRoom)
						throws RemoteException {
					_miniModel = new MiniModel(localChatRoom, _userStub);
					_miniModel.setMM2VA(_viewAdapter.makeMiniModel2ViewAdapter(_miniModel));
					_miniModel.updateView(localChatRoom);
					_room = localChatRoom;
					_room.addLocalUser(_userStub);
					AddUser au = new AddUser(_userStub);
					ADataPacket dp = new DataPacket<IAddUser>(IAddUser.class, _userStub, au);
					Iterable<ADataPacket> results = _room.sendMessage(dp);
					for(ADataPacket result: results){
						//use host-visitor to check result
						result.execute(_visitor, _viewAdapter);
					}
					_miniModel.updateView(_room);
					return true;//if all return status ok, else return false;
				}
				
			};
			_user = new IUser(){
				/**
				 * local iuser name
				 */
				public String getName() throws RemoteException {
					return "bw6 client user";
				}
				/**
				 * local iuser uuid
				 */
				public UUID getUUID() throws RemoteException {
					return _uuid;
				}
				/**
				 * execute with local _visitor
				 */
				public ADataPacket receiveData(ADataPacket dp)
						throws RemoteException {
					if(dp != null){
						return dp.execute(_visitor, _viewAdapter);
					}else{
						return new DataPacket<IStatusFail>(IStatusFail.class, _userStub, new StatusFail("datapacket is null",dp));
					
					}
				}};
			_userStub = (IUser)UnicastRemoteObject.exportObject(_user, IUser.CONNECTION_PORT_CLIENT);
			_viewAdapter.append("Instantiated new IHost: "+"\n");
			_hostStub =(IHost) UnicastRemoteObject.exportObject(_host, IHost.CONNECTION_PORT_CLIENT);
			_viewAdapter.append("Looking for registry..."+"\n");
			registry = rmiUtils.getLocalRegistry();
			_viewAdapter.append("Found registry: " + "\n");
			registry.rebind(IHost.BOUND_NAME, _hostStub);
			_viewAdapter.append("My host bound to "+IHost.BOUND_NAME+"\n");
		} 
		catch (Exception e) {
			System.err.println("My host exception:"+"\n");
			e.printStackTrace();
			System.exit(-1);
		}
		_viewAdapter.append("Please type in ip addresses to connect..."+"\n");
	}
	/**
	 * Connect to a remote IHost and get its stub using this ip address.
	 * will return the name of the remote host if success, otherwise return null.
	 * @param ipAddress a string representing an ip address.
	 */
	public String connectTo(String ipAddress){
		String result = null;
		try {
			_viewAdapter.append("Locating registry at " + ipAddress + "...\n");
			Registry registry = rmiUtils.getRemoteRegistry(ipAddress);
			_viewAdapter.append("Found registry: " + registry + "\n");
			final IHost remoteHostStub = (IHost) registry.lookup(IHost.BOUND_NAME);
			_viewAdapter.append("Found remote IHost object: " + remoteHostStub + "\n");
			if(idRemoteHostDict.containsKey(remoteHostStub.getUUID())){
				_viewAdapter.append("But this remote host is already in the collection, please dont connect again");
			}else{
				result = remoteHostStub.getName();
				idRemoteHostDict.put(remoteHostStub.getUUID(), remoteHostStub);  //add remoteHostStub to remote host stubs collection, the ip-remotehost dictionary.
				nameRemoteHostDict.put(remoteHostStub.getName(), remoteHostStub);
			}
			(new Thread() {
				public void run() {
			try {
				remoteHostStub.sendLocalHostStub(_hostStub);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				}
			}).start(); // start the new thread
			_viewAdapter.append("Sent my host stub"+ _hostStub + "\n");
			_viewAdapter.append("Connection to " + ipAddress + " established!");
			return result;
		} catch (Exception e) {
			_viewAdapter.append("Exception connecting to " + ipAddress + ": " + e
					+ "\n");
			e.printStackTrace();
			_viewAdapter.append("No connection established!");
			return "No connection";
		}
	}

	/**
	 * join a remote hosted game in the remoteHostName's host
	 * @param remoteHostName
	 * @return
	 */
	public void joinGame(String remoteHostName){
		final IHost remoteHost = nameRemoteHostDict.get(remoteHostName);
		(new Thread(){
			public void run(){
				try {
					remoteHost.requestInvite(_hostStub);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		return;
	}


}

