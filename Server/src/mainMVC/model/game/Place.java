package mainMVC.model.game;

import gov.nasa.worldwind.geom.Position;
/**
 * a simplified version of city node with only name, population, and position
 * @author Bojun
 *
 */
public class Place {
	private String _name;
	private Position _pos;
	private int _pop;
	/**
	 * constructor as setter
	 * @param name
	 * @param pos
	 * @param pop
	 */
	public Place(String name, Position pos, int pop){
		_name = name;
		_pos = pos;
		_pop = pop;
	}
	/**
	 * getters
	 * @return
	 */
	public Position getPosition(){
		return _pos;
	}
	public String toString(){
		return _name;
	}
	public int getPopulation(){
		return _pop;
	}
	
}
