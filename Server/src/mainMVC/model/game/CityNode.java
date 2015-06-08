package mainMVC.model.game;

/**
 * A node representing a city where humans are produced at a certain rate
 * each city has different human producing rate
 * each city could be selected by a single team.
 * each city has a name and an abbreviation
 * 
 * @author Bojun
 *
 */
public class CityNode {
	/**
	 * initial amount of human in the city
	 */
	private int initialAmount;
	/**
	 * human producing rate of the city
	 */
	private int peoplePerSec;
	/**
	 * whether the city is selected or not
	 */
	private boolean selected;
	/**
	 * abbreviation of the city
	 */
	private String abbr;
	/**
	 * name of the city
	 */
	private String name;
	/**
	 * constructor as setter
	 * @param name
	 * @param abbr
	 * @param latitude
	 * @param longitude
	 */
	public CityNode(String name, String abbr, double latitude, double longitude) {
		this.name = name;
		this.abbr = abbr;
		this.selected = false;
		init();
	}
	/**
	 * init the city with random number
	 */
	private void init() {
		int total = (int)(Math.random() * 420000) + 80000;
		initialAmount = (int)(Math.random() * 100000) + 50000;
		int amountDuringGame = total - initialAmount;
		peoplePerSec = (int)(amountDuringGame / (7.0 * 12));
	}
	/**
	 * getters
	 * @return
	 */
	public String getName() {
		return name;
	}

	public String getAbbr() {
		return abbr;
	}

	public boolean getSelected() {
		// TODO Auto-generated method stub
		return this.selected;
	}

	public void setSelected() {
		this.selected = true;
	}

	public int getInitialHumans() {
		// TODO Auto-generated method stub
		return this.initialAmount;
	}

	public int getIncrementHumans() {
		// TODO Auto-generated method stub
		return this.peoplePerSec;
	}

}