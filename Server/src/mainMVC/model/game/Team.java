package mainMVC.model.game;

import java.util.ArrayList;

/**
 * a team object represents a team in the game.
 * each team has a score that is updated every 5 second
 * each team has a name
 * each team has the total number of humans. If player tries to move more human than they have, it will move 
 * the total number of humans
 * each team has the most 3 cities.
 * @author Bojun
 *
 */
public class Team {
	/**
	 * team's score
	 */
	private int score;
	/**
	 * team's name
	 */
	private String name;
	/**
	 * team's number of humans.
	 */
	private int numHumans;
	/**
	 * array list of cities selected by team
	 */
	private ArrayList<CityNode> activeCities;
	/**
	 * constructor and initializer with 0s.
	 * @param text
	 */
	public Team(String text) {
		// TODO Auto-generated constructor stub
		this.name = text;
		this.score = 0;
		this.numHumans = 0;
		this.activeCities = new ArrayList<CityNode>();
	}
	/**
	 * getters and setters
	 * @return
	 */
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	/**
	 * increment the team score by amount
	 * @param amount
	 */
	public void incrementScore(int amount){
		this.score += amount;
	}
	/** 
	 * getters and setters
	 * @return
	 */
	public int getNumHumans() {
		return numHumans;
	}
	
	public void setNumHumans(int numHumans) {
		this.numHumans = numHumans;
	}
	/**
	 * add a city to a team
	 * if the team already has 3 cities, the city won't be added to team.
	 * @param newCity
	 * @return
	 */
	public boolean addCity(CityNode newCity) {
		if(numCities() < 3){
			if(!newCity.getSelected()){
				activeCities.add(newCity);
				newCity.setSelected();
				this.numHumans += newCity.getInitialHumans();
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	public int numCities() {
		return activeCities.size();
	}
	/**
	 * check if this team has this city
	 * @param cityNode the city to check
	 * @return
	 */
	public boolean hasCity(CityNode cityNode) {
		for(CityNode city: activeCities) {
			if(city.getName().equals(cityNode.getName())) {
				return true;
			}
		}
		return false;
	}
	/**
	 * update the  team's humans number by getting all increments from cities he has
	 */
	public void update() {
		for(CityNode cityNode : this.activeCities){
			this.numHumans += cityNode.getIncrementHumans();
		}
	}

	public String getName() {
		return this.name;
	}
	/**
	 * subtract a certain amount of population
	 * if that exceeds the total number of humans, it will zero the total number of humans.
	 * @param numPeople
	 * @return
	 */
	public int subtractFromPopulation(int numPeople) {
		if(numHumans < numPeople) {
			int ret = numHumans;
			numHumans = 0;
			return ret;
		} else {
			numHumans -= numPeople;
			return numPeople;
		}
	}
}