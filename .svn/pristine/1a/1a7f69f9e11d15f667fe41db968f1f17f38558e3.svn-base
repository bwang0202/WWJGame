package mainMVC.model.dataPacket;

import java.rmi.RemoteException;

import comp310f13.rmiChat.IUser;

import provided.datapacket.DataPacket;
import gov.nasa.worldwind.geom.Position;
import map.ToggleAnnotation;
/**
 * subclass of ToggleAnnotation in the map package
 * the only difference is when someone clicking this annotation, besides change text on the annotation.
 * it send back a special datapacket that specifies who chose which city.
 * @author Bojun
 *
 */
public class MyToggleAnnotation extends ToggleAnnotation{

	/**
	 * generated uid
	 */
	private static final long serialVersionUID = 3420458139384089182L;
	/**
	 * server user stub for sending back datapacket
	 */
	private IUser _serverUserStub;
	/**
	 * client user stub for knowing sender of datapacket
	 */
	private IUser _clientUserStub;
	/**
	 * constructor as setter
	 * @param unselectedText
	 * @param selectedText
	 * @param pos
	 * @param serverUserStub
	 * @param clientUserStub
	 */
	public MyToggleAnnotation(String unselectedText, String selectedText,
			Position pos, IUser serverUserStub, IUser clientUserStub) {
		super(unselectedText, selectedText, pos);
		_serverUserStub = serverUserStub;
		_clientUserStub = clientUserStub;
	}
	/**
	 * overriden method
	 * when someone clicking this annotation, besides change text on the annotation.
	 * it send back a special datapacket that specifies who chose which city
	 */
	protected void selectedTrueAction(){
		super.selectedTrueAction();
		try {
			_serverUserStub.receiveData(new DataPacket<MyStringMsg>(MyStringMsg.class, _clientUserStub, new MyStringMsg("Houston")));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
