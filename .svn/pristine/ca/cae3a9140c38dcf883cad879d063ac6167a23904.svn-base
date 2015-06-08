package mainMVC.model;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import javax.swing.JFrame;
import javax.swing.Timer;
import mainMVC.model.dataPacket.*;
import mainMVC.model.game.CityNode;
import mainMVC.model.game.Team;
import mainMVC.model.game.WorkNode;
import comp310f13.rmiChat.*;
import provided.datapacket.*;
import provided.mixedData.IMixedDataDictionary;
import provided.rmiUtils.IRMIUtils;
import provided.rmiUtils.IRMI_Defs;
import provided.rmiUtils.RMIUtils;
import provided.util.IVoidLambda;
/**
 * main model for server model
 * 
 * @author Bojun
 *
 */
public class MainModel {
	/**
	 * update the game status every 5 seconds
	 */
	private Timer _timer = new  Timer (5000, new ActionListener ()  {
		/**
		 * each time slice passed, the view update adapter will be called to update the view.
		 * @param e ActionEvent
		 * @return
		 */
		public void actionPerformed (ActionEvent e) {
			updateScores();
	    }
	  });
	/**
	 * a list of work nodes
	 */
	private ArrayList<WorkNode> workNodes= new ArrayList<WorkNode>();
	/**
	 * a dictionary from team name to team object, do not allow teams with same name
	 */
	private HashMap<String, Team> stringTeamDict = new HashMap<String, Team>();
	/**
	 * a list of all team chatrooms
	 */
	private ArrayList<IChatRoom> _rooms = new ArrayList<IChatRoom>();
	/**
	 * a list of all city nodes
	 */
	private ArrayList<CityNode> cityNodes = new ArrayList<CityNode>();
	/**
	 * a dictionary form uuid to remote hosts
	 */
	private HashMap<UUID, IHost> idRemoteHostDict = new HashMap<UUID, IHost>();
	/**
	 * a dictionary from remote host name to remote host
	 */
	private HashMap<String, IHost> nameRemoteHostDict = new HashMap<String, IHost>();
	/**
	 * a dictionary from chatroom name to chatroom
	 */
	private HashMap<String, IChatRoom> stringChatRoomDict = new HashMap<String, IChatRoom>();
	/**
	 * a dictionary from user uuid to the chatroom name the user is currently in
	 */
	private HashMap<UUID, String> uUUIDCRSDict = new HashMap<UUID, String>();
	/**
	 * dictionary from cmd index to the actual cmd
	 */
	private HashMap<Class<?>, ADataPacketAlgoCmd<ADataPacket, ?,?>> cmdDict = new HashMap<Class<?>, ADataPacketAlgoCmd<ADataPacket, ?,?>>();
	/**
	 * adatper between all foregin cmds to model
	 */
	private ICmd2ModelAdapter _cmd2ma = new ICmd2ModelAdapter(){
		public IUser getLocalUserStub() {
			return _serverIUserStub;
		}
		/**
		 * if the string starts with GAMECOMMAND, then that means this string is a game command that will modify game status, game command is only produced
		 * when certain buttons were clicked on the components the server sent to the client. then the game command string is parsed.
		 */
		public void append(String s) {
			if(s.startsWith("GAMECOMMAND:")) {
				s = s.replaceAll("GAMECOMMAND:", "").trim();
				String[] params = s.split(":UUID=");
				String command = params[0];
				UUID senderUUID = UUID.fromString(params[1]);
				String teamName = uUUIDCRSDict.get(senderUUID);
				for(CityNode cityNode: cityNodes) {
					if(cityNode.getName().toLowerCase().equals(command.toLowerCase())) {
						_viewAdp.append(teamName + " selected " + cityNode.getName() + "\n");
						boolean ret = stringTeamDict.get(teamName).addCity(cityNode);
						if(ret){
							sendMsgToAll(teamName + " selected " + cityNode.getName() + "\n");
						}else{
							sendMsgToAll(cityNode.getName() + " is already conquered or team city limit has been met");
						}
						
					}
					if(cityNode.getAbbr().toLowerCase().equals(command.toLowerCase())) {
						_viewAdp.append(teamName + " selected " + cityNode.getName()+ "\n");
						boolean ret = stringTeamDict.get(teamName).addCity(cityNode);
						if(ret){
							sendMsgToAll(teamName + " selected " + cityNode.getName() + "\n");
						}else{
							sendMsgToAll(cityNode.getName() + " is already conquered or team city limit has been met");
						}
					}
				}
				if(command.toLowerCase().indexOf(";") != -1) {
					String[] components = command.split(";");
					int numPeople = Integer.parseInt(components[0]);
					if(numPeople > 0) {
						for(WorkNode workNode: workNodes) {
							if(command.toLowerCase().indexOf(workNode.getName().toLowerCase()) != -1) {
								_viewAdp.append(teamName + " moved " + numPeople + " to " + workNode.getName());
								int numMovable = stringTeamDict.get(teamName).subtractFromPopulation(numPeople);
								workNode.addToTeam(teamName, numMovable);
							}
						}
					}
				}
			} else {
				_viewAdp.append(s);
			}
		}

		/**
		 * no-op
		 */
		public void addComponent(String name, Component newComp) {
			//noop
		}

		/**
		 * no-op
		 */
		public IMixedDataDictionary getDataDict() {
			// TODO Auto-generated method stub
			return null;
		}};
		/**
		 * server host and IUser
		 */
	private IHost _host;
	private IHost _hostStub;
	private IUser _serverIUser;
	private IUser _serverIUserStub;
	/**
	 * visitor used to process all datapacket
	 */
	private DataPacketAlgo<ADataPacket, Object> _visitor;
	
	/**
	 * The adapter to the view
	 */
	private IViewAdapter _viewAdp;

	/**
	 * The RMI Registry
	 */
	private Registry registry;

	/**
	 * A command used as a wrapper around the view adapter for the IRMIUtils.
	 */
	private IVoidLambda<String> outputCmd = new IVoidLambda<String> (){
		public void apply(String... strs){
			for(String s: strs)_viewAdp.append(s);
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
		_viewAdp = viewAdpt;
	}
	/**
	 * update the game status and sent the updated game result to everyone
	 */
	protected void updateScores() {
		for (WorkNode workNode : this.workNodes){
			HashMap<String, Integer> increments = workNode.getTeamScoreIncrements();
			for(String teamName : increments.keySet()){
				if(stringTeamDict.get(teamName) != null) {
					stringTeamDict.get(teamName).incrementScore(increments.get(teamName));
				} else {
					_viewAdp.append("stringTeamDict.get(" + teamName + ") = null");
				}
			}
		}
		for (Team team: this.stringTeamDict.values()){
			team.update();
		}
		String result = "";
		for (Team team: this.stringTeamDict.values()){
			result += team.getName() + ": "+team.getScore() + " pts "+ team.getNumHumans() + " humans \n" ;
		}
		for (WorkNode workNode : this.workNodes){
			result += workNode.getName() + ":\n";
			result += workNode.teamPopulationString();
		}
		for(IChatRoom room: _rooms){
			Iterable<ADataPacket> res = room.sendMessage(new DataPacket<MyGameStatusMsg>(MyGameStatusMsg.class, _serverIUserStub, new MyGameStatusMsg(result)));
			for(ADataPacket re: res){
				re.execute(_visitor, _viewAdp); //somepeople says ok ,somenot
			}
		}
		
	}
	/**
	 * instantiate my IHost object and bind it to IHost.BOUND_NAME on the local registry.
	 * set up the visitor
	 */
	public void start() {
		rmiUtils.startRMI(IRMI_Defs.CLASS_SERVER_PORT_SERVER);
		loadCities();
		loadWorkNodes();
		_visitor = new DataPacketAlgo<ADataPacket, Object>(new ADataPacketAlgoCmd<ADataPacket, Object, Object>(){

			private static final long serialVersionUID = 2139708709472239562L;
			/**
			 * server do not want to accept any known data packet or its cmd. so return fail all the time when encountered with unknown data packet
			 */
			public ADataPacket apply(Class<?> index, DataPacket<Object> host,
					Object... params) {
				//server reject any unknown datapacket
				return new DataPacket<IStatusFail>(IStatusFail.class, _serverIUserStub, new StatusFail("server rejects unkonw data",host));
			}
			public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
				// TODO Auto-generated method stub
				
			}});
		MyStringMsgCmd cmddd = new MyStringMsgCmd();
		cmddd.setCmd2ModelAdpt(_cmd2ma);
		_visitor.setCmd(MyStringMsg.class, cmddd);
		cmdDict.put(MyStringMsg.class, cmddd);
		
		MyMapFrameMsgCmd cmdd = new MyMapFrameMsgCmd();
		cmdd.setCmd2ModelAdpt(_cmd2ma);
		_visitor.setCmd(MyMapFrameMsg.class, cmdd);
		cmdDict.put(MyMapFrameMsg.class, cmdd);
		
		MyCitySelMsgCmd citycmd = new MyCitySelMsgCmd();
		citycmd.setCmd2ModelAdpt(_cmd2ma);
		_visitor.setCmd(MyCitySelMsg.class, citycmd);
		cmdDict.put(MyCitySelMsg.class, citycmd);
		
		MyGameStatusMsgCmd stcmd = new MyGameStatusMsgCmd();
		stcmd.setCmd2ModelAdpt(_cmd2ma);
		_visitor.setCmd(MyGameStatusMsg.class, stcmd);
		cmdDict.put(MyGameStatusMsg.class, stcmd);
		//unknown data packet test
		
		_visitor.setCmd(IAddUser.class, new ADataPacketAlgoCmd<ADataPacket, IAddUser, Object>(){
			private static final long serialVersionUID = 9201509160170062937L;

			public ADataPacket apply(Class<?> index, DataPacket<IAddUser> host,
					Object... params) {
				IAddUser d = host.getData();
				IChatRoom tRoom;
				try {
					tRoom = stringChatRoomDict.get(uUUIDCRSDict.get(d.getUser().getUUID()));
					tRoom.addLocalUser(d.getUser());
					uUUIDCRSDict.put(d.getUser().getUUID(), tRoom.getName());
					_viewAdp.append(d.getUser().getName() + "was added to chatroom" + tRoom.getName() + "\n");

					StatusOk dok = new StatusOk();
					return new DataPacket<IStatusOk>(IStatusOk.class, _serverIUserStub, dok);
				} catch (RemoteException e1) {
					e1.printStackTrace();
					return new DataPacket<IStatusFail>(IStatusFail.class, _serverIUserStub, new StatusFail(e1.getMessage(),host));
				}
			}

			@Override
			public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
				// TODO Auto-generated method stub
			}});
		
		_visitor.setCmd(IRemoveUser.class,new ADataPacketAlgoCmd<ADataPacket, IRemoveUser, Object>(){
			private static final long serialVersionUID = -8419904892261355432L;

			public ADataPacket apply(Class<?> index, DataPacket<IRemoveUser> host,
					Object... params) {
				IRemoveUser d = host.getData();
				IChatRoom tRoom;
				try {
					tRoom = stringChatRoomDict.get(uUUIDCRSDict.get(d.getUser().getUUID()));
					tRoom.removeLocalUser(d.getUser());
					uUUIDCRSDict.put(d.getUser().getUUID(), tRoom.getName());
					_viewAdp.append(d.getUser().getName() + "was removed from chatroom" + tRoom.getName() + "\n");
					StatusOk dok = new StatusOk();
					return new DataPacket<IStatusOk>(IStatusOk.class, _serverIUserStub, dok);
				} catch (RemoteException e1) {
					e1.printStackTrace();
					return new DataPacket<IStatusFail>(IStatusFail.class, _serverIUserStub, new StatusFail(e1.getMessage(),host));
				}
				
			}

			@Override
			public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
				// TODO Auto-generated method stub
				
			}});
		_visitor.setCmd(ITextMessage.class, new ADataPacketAlgoCmd<ADataPacket, ITextMessage, Object>(){

			private static final long serialVersionUID = -8419904892261355432L;

			public ADataPacket apply(Class<?> index,
					DataPacket<ITextMessage> host, Object... params) {
				ITextMessage dt = host.getData();
				try {
					_viewAdp.append("\n      In chatroom "+uUUIDCRSDict.get(host.getSender().getUUID())+" "+dt.getTime()+" "+dt.getName()+ " says:"+dt.getMsg());
					//
					StatusOk dok = new StatusOk();
					return new DataPacket<IStatusOk>(IStatusOk.class, _serverIUserStub, dok);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return new DataPacket<IStatusFail>(IStatusFail.class, _serverIUserStub, new StatusFail(e.getMessage(),host));
				}
			}
			public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
				// TODO Auto-generated method stub
			}});
		
		_visitor.setCmd(IRequestCmd.class, new ADataPacketAlgoCmd<ADataPacket, IRequestCmd, Object>(){

			private static final long serialVersionUID = -5671516462086387887L;

			public ADataPacket apply(Class<?> index,
					DataPacket<IRequestCmd> host, Object... params) {
				//ADataPacketAlgoCmd<ADataPacket, ?, ?> cmd;
				//MyStringMsgCmd cmdd = new MyStringMsgCmd();
				ADataPacketAlgoCmd<ADataPacket, ?, ?> cmdd = cmdDict.get(host.getData().getID());
				//maybe need a dictionary other than/ aswellas visitor to hold special cmds
				//note: cmd will be sent, so any special cmd ided not known, should have a seperate concrete class.
				IAddCmd d = new AddCmd(host.getData().getID(), cmdd);
				try {
					return new DataPacket<IAddCmd>(IAddCmd.class, _serverIUserStub, d);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return new DataPacket<IStatusFail>(IStatusFail.class, _serverIUserStub, new StatusFail(e.getMessage(),host));
				}
			}

			@Override
			public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
				// TODO Auto-generated method stub
				
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
					_viewAdp.append(host.getSender().getName() + "says ok \n");
					//return new DataPacket<IStatusOk>(IStatusOk.class, _userStub, new StatusOk());
					return null;
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return new DataPacket<IStatusFail>(IStatusFail.class, _serverIUserStub, new StatusFail(e.getMessage(),host));
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
			 * print fail status to view
			 */
			public ADataPacket apply(Class<?> index,
					DataPacket<IStatusFail> host, Object... params) {
				try {
					_viewAdp.append(host.getSender().getName() + "says fail \n");
					//return new DataPacket<IStatusOk>(IStatusOk.class, _userStub, new StatusOk());
					return null;
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return new DataPacket<IStatusFail>(IStatusFail.class, _serverIUserStub, new StatusFail(e.getMessage(),host));
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
			 * print reject status to view
			 */
			public ADataPacket apply(Class<?> index,
					DataPacket<IStatusReject> host, Object... params) {
				try {
					_viewAdp.append(host.getSender().getName() + "says reject \n");
					//return new DataPacket<IStatusOk>(IStatusOk.class, _userStub, new StatusOk());
					return null;
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return new DataPacket<IStatusFail>(IStatusFail.class, _serverIUserStub, new StatusFail(e.getMessage(),host));
				}
			}
			/**
			 * no-op
			 */
			public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
				// TODO Auto-generated method stub
				
			}});
		try {
			//instantiate IHost object for the game server and put its stub in registry
			_host = new IHost(){
				
				private HashMap<UUID, IChatRoom> UUIDChatRoomDict = new HashMap<UUID, IChatRoom>();
				private UUID _uuid = UUID.randomUUID();
				public String getName() throws RemoteException {
					return "bw6 server host";
				}
				public UUID getUUID() throws RemoteException {
					return _uuid;
				}
				
				@Override
				/**
				 * when some one tries to connect to me, I received a local host stub of theirs , I want to tell the model and the view that I have another remote
				 * host stub in my collection.
				 * @param localHostStub, someone's host stub.
				 */
				public void sendLocalHostStub(IHost localHostStub)
						throws RemoteException {
					if(idRemoteHostDict.containsKey(localHostStub.getUUID())){
						_viewAdp.append("This remote host stub" + localHostStub.getName() + "is already in the collection \n");
					}else{
						idRemoteHostDict.put(localHostStub.getUUID(), localHostStub);
						nameRemoteHostDict.put(localHostStub.getName(), localHostStub);
						_viewAdp.addRemoteHost(localHostStub.getName());
						_viewAdp.append(localHostStub.getName() + " connected to this server. \n");
					}
					
				}
				/**
				 * Initiate an invite sequence.  
				 * @param localHostStub  The stub of the local host, so that the receiver knows who is requesting the invite.
				 * @throws RemoteException Required for RMI transactions.
				 */
				public void requestInvite(IHost localHostStub)
						throws RemoteException {
					final IHost thatHost = localHostStub;
					(new Thread(){
						public void run(){
							try{
								ArrayList<IChatRoomInvite> list = new ArrayList<IChatRoomInvite>();
								for(IChatRoom room: _rooms){
									IChatRoomInvite invite = new MyChatRoomInvite(room);
									list.add(invite);
									UUIDChatRoomDict.put(invite.getUUID(), room);
								}
								UUID choice = thatHost.sendInvite(list); //method on stub, should spawn new thread automatically

								if(choice != IChatRoomInvite.NONE){
									IChatRoom localChatRoom = UUIDChatRoomDict.get(choice);
									uUUIDCRSDict.put(thatHost.getUUID(), localChatRoom.getName());
									thatHost.addToChatRoom(localChatRoom); //method on stub should spawn new thread automatically
									
								}else{
									_viewAdp.append(thatHost.getName()+" chose to join none of the rooms \n");
									//SwingUtiltities.invokeLater()
								}

							} catch (Exception e) {
								e.printStackTrace();
							}
						}}).start();

				}
				/**
				 * non op
				 */
				public UUID sendInvite(Iterable<IChatRoomInvite> chatroomInfo)
						throws RemoteException {
					return null;
				}

				/**
				 * non-op
				 */
				public boolean addToChatRoom(IChatRoom localChatRoom)
						throws RemoteException {
					return false;
				}
				
			};
			//_viewAdapter.append("Instantiated new IHost: "+_host+"\n");
			_viewAdp.append("Instantiated new IHost: "+"\n");
			_hostStub =(IHost) UnicastRemoteObject.exportObject(_host, IHost.CONNECTION_PORT_SERVER);
			_viewAdp.append("Looking for registry..."+"\n");
			registry = rmiUtils.getLocalRegistry();
			//_viewAdapter.append("Found registry: "+ registry+ "\n");
			_viewAdp.append("Found registry: " + "\n");
			registry.rebind(IHost.BOUND_NAME, _hostStub);
			_viewAdp.append("My host bound to "+IHost.BOUND_NAME+"\n");
			//instantiate IUser object for the game server and its stub 
			//for test: also put that stub in every chatroom, here testchatroom
			_serverIUser = new IUser(){
				public String getName() throws RemoteException {
					return "bw6 server IUser";
				}
				public UUID getUUID() throws RemoteException {
					return _host.getUUID();
				}
				public ADataPacket receiveData(ADataPacket dp)
						throws RemoteException {
					// TODO Auto-generated method stub
					//i am the game server, some one sent me an dp, first figure out who sent me, then deal it
					System.out.println("some one sent me an dp");
					if(dp != null){
						return dp.execute(_visitor, _viewAdp);
					}else{
						return null;
					}
				}
				
			};

			_serverIUserStub = (IUser)UnicastRemoteObject.exportObject(_serverIUser, IUser.CONNECTION_PORT_SERVER);
			((MyMapFrameMsgCmd)cmdDict.get(MyMapFrameMsg.class)).setServerUserStub(_serverIUserStub);
			
		} 
		catch (Exception e) {
			System.err.println("My host exception:"+"\n");
			e.printStackTrace();
			System.exit(-1);
		}
		_viewAdp.append("Waiting..."+"\n");
	}
	/**
	 * load work nodes into the model
	 */
	private void loadWorkNodes() {
		// TODO Auto-generated method stub
		workNodes.add(new WorkNode("Mine"));
		workNodes.add(new WorkNode("Factory"));
		workNodes.add(new WorkNode("Farm"));
		workNodes.add(new WorkNode("Fish"));
		
	}
	/**
	 * local city nodes into the model
	 */
	private void loadCities() {
		cityNodes.add(new CityNode("New York", "NY", 40.748974, -73.990288));
		cityNodes.add(new CityNode("Houston", "H", 29.7628, -95.3831));
		cityNodes.add(new CityNode("Moscow", "MOS", 55.7500, 37.6167));
		cityNodes.add(new CityNode("Mexico City", "MC", 19.4328, -99.1333));
		cityNodes.add(new CityNode("Buenos Aires", "BA", -34.6033, -58.3817));
		cityNodes.add(new CityNode("Tokyo", "T", 35.6895, 139.6917));
		cityNodes.add(new CityNode("Beijing", "B", 39.9139, 116.3917));
		cityNodes.add(new CityNode("Hong Kong", "HK", 22.2783, 114.1589));
		cityNodes.add(new CityNode("Mumbai", "MUM", 18.9750, -72.8258));
		cityNodes.add(new CityNode("Paris", "P", 48.8567, 2.3508));
		cityNodes.add(new CityNode("Cape Town", "CT", 33.9253, 18.4239));
		cityNodes.add(new CityNode("Vienna", "V", 48.205456,16.339202));
	}
	/**
	 * server want more team/chatroom with a chatroom name
	 * chatroom are specified only by different names
	 * @param text The ChatRoom's name
	 */
	public void makeTeam(String text) {
		try{
			IChatRoom room = new MyChatRoom(text);
			stringChatRoomDict.put(text, room);
			//uUUIDCRSDict.put(_serverIUser.getUUID(),text);
			stringTeamDict.put(text, new Team(text));
			_rooms.add(room);
			room.addLocalUser(_serverIUser);
			_viewAdp.append("Made a chatroom "+text +"\n");
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}
	
	/**
	 * start the game
	 */
	public void startgame(){
		sendMsgToAll("\n Game STARTS!!!!!\n");
		sendMsgToAll("The winner of the game is the team who scores the most points.  Points are scored by \n assigning captured humans to a different jobs: mining, factory work, farming, and fishing.Different work nodes produce points at different rates, but work nodes also have certain death rates.  Only the team with the most humans at any work node receives any points.At the onset of the game, teams will choose 3 cities that will produce humans for the team.  Cities are selected by clicking on the corresponding city annotation on the map.The first team to select a city \n captures it and no other team may select it.  Each team may select 3 cities from which to capture humans.  Cities differ in he initial human yield as well as the number of humans generated per \n second of gameplay.  This information can be seen in the right info pane.  After players chose the city, they could start moving humans to one of the work nodes by entering the amount using textfield and the destination using combo box. Then the scores and game \n status got updated every five seconds so that each player could seewhat the game status is and continue to move their humans. \n");
		_timer.start();
	}
	/**
	 * send text message to the chatroom with name teamName
	 * @param teamName
	 * @param text
	 */
	public void send(String teamName, String text) {
		IChatRoom room = stringChatRoomDict.get(teamName);
		if(room != null){
			ADataPacket dp;
			try {
				dp = new DataPacket<ITextMessage>(ITextMessage.class, _serverIUserStub, new TextMessage(_serverIUser.getName(), new Date(), text));
				Iterable<ADataPacket> res = room.sendMessage(dp);
				//maybe deal with result which are mostly IOk or IFailure, or Irequestcmd
				
				for(ADataPacket result: res){
					if(result != null) result.execute(_visitor, _viewAdp); //somepeople says ok ,somenot
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//dp = new DataPacket<MyStringMsg>(MyStringMsg.class, _serverIUserStub, new MyStringMsg(text));
			
				
		}
		
	}
	/**
	 * send the game map to everyone
	 */
	public void sendMap() {
		ADataPacket dp = new DataPacket<MyMapFrameMsg>(MyMapFrameMsg.class, _serverIUserStub, new MyMapFrameMsg(new JFrame()));
		System.out.println("sent the map");
		for(IChatRoom room: _rooms){
			Iterable<ADataPacket> res = room.sendMessage(dp);
			for(ADataPacket result: res){
				if(result != null) result.execute(_visitor, _viewAdp); //somepeople says ok ,somenot
			}
		}
		startgame();
		
	}
	/**
	 * send a message to all
	 * @param msg
	 */
	public void sendMsgToAll(String msg){
		ADataPacket dp;
		try {
			dp = new DataPacket<ITextMessage>(ITextMessage.class, _serverIUserStub, new TextMessage(_serverIUser.getName(), new Date(), msg));
			for(IChatRoom room: _rooms){
				
				Iterable<ADataPacket> res = room.sendMessage(dp);
				for(ADataPacket result: res){
					if(result != null){
						result.execute(_visitor, _viewAdp); //somepeople says ok ,somenot
					}
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * end the game tell everyone gameover and send the result
	 */
	public void endGame() {
		// TODO Auto-generated method stub
		_timer.stop();
		sendMsgToAll("game over");
		int high = 0;
		String result = "";
		for(String teamname: stringTeamDict.keySet()){
			if(stringTeamDict.get(teamname).getScore() > high){
				result = teamname;
				high = stringTeamDict.get(teamname).getScore();
			}
		}
		sendMsgToAll(result + " the winner with the highest " + high + " scores ! \n");
	}



}

