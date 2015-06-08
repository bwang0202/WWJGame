package comp310f13.rmiChat;

import java.io.Serializable;
import java.util.UUID;

/**
 * An interface that represents the invitation to a particular chatroom
 *
 */
public interface IChatRoomInvite extends Serializable{
	  /** 
	   * Accessor for the name of the chatroom the recipient is being invited to.
	   * @return The name of the chatroom. 
	   * */
	  public String getName();

	  /**
	   * Accessor for the UUID that uniquely identifies this invitation.
	   * @return A unique identifier for the invite. 
	   * */
	  public UUID getUUID();

	  /**
	   * Accessor for an interable of the list of names of users currently in the invited chatroom.
	   * @return The current user list for the chatroom. 
	   */
	  public Iterable<String> getUserNames();

	  /**
	   * The null choice, representing not choosing any invitation.
	   */
	  public static final UUID NONE = UUID.nameUUIDFromBytes(new byte[]{});
	  
}
