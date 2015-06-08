package mainMVC.model.game;

import java.util.HashMap;

/**
 * a work node where the scores are earned
 * the amount of score earned is related to the number of people in it.
 * @author Bojun
 *
 */
public class WorkNode {
	/**
	 * the rate of how fast the number of people reduces
	 */
	private double deathRate;
	/**
	 * the rate of how many scores are awarded, it is related to some complicated maths relations.
	 */
	private double coefficient;
	/**
	 * worknode's name
	 */
	private String name;
	HashMap<String, Integer> teamPopulations;
	/**
	 * constructor and initializer.
	 * @param name name of the work node
	 */
	public WorkNode(String name) {
		this.teamPopulations = new HashMap<String, Integer>();
		this.name = name;
		init();
	}
	/**
	 * initializer
	 */
	private void init() {
		deathRate = Math.random() * 0.002 + 0.001;
		coefficient = (Math.random() * .8) + .2;
	}
	/**
	 * compute scores earned by each team and put them in a hashmap
	 * @return key is team's name
	 * 			value is the score they earned during one period.
	 */
	public HashMap<String, Integer> getTeamScoreIncrements() {
		int maxPop = 0;
		String maxTeam = "";
		HashMap<String, Integer> scoreIncrements = new HashMap<String, Integer>();
		for (String teamName : teamPopulations.keySet()) {
			int population = teamPopulations.get(teamName);
			teamPopulations.put(teamName, (int)(population * (1 - deathRate)));
			scoreIncrements.put(teamName, 0);
			if(population > maxPop) {
				maxPop = population;
				maxTeam = teamName;
			}
		}
		if(maxPop > 0) {
			scoreIncrements.put(maxTeam, (int)(coefficient * Math.log(maxPop)));
		}
		return scoreIncrements;
	}
	public String getName() {
		return name;
	}
	/**
	 * add certain amount of people from a team to this work node
	 * @param teamName the team that's providing the people
	 * @param numToAdd number of people the team provides.
	 */
	public void addToTeam(String teamName, int numToAdd){
		System.out.println("addToTeam teamName:" + teamName);
		if(!teamPopulations.containsKey(teamName)){
			teamPopulations.put(teamName, numToAdd);
		}else{
			teamPopulations.put(teamName, teamPopulations.get(teamName)+numToAdd);
		}
	}
	/**
	 * getter for a nice representing of each team's population in this work node.
	 * @return
	 */
	public String teamPopulationString() {
		String ret = "";
		for(String teamname: teamPopulations.keySet()) {
			ret += "   " + teamname + ": " + teamPopulations.get(teamname) + " humans\n"; 
		}
		return ret;
	}
}