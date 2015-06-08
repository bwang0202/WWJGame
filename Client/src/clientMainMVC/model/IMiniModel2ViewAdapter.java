package clientMainMVC.model;

import java.awt.Component;

/**
 * interface between minimodel and miniview
 * @author Bojun
 *
 */
public interface IMiniModel2ViewAdapter {
	/**
	 * append this string to mini view
	 * @param s
	 */
	public void append(String s);
	/**
	 * add a Component to mini view
	 * @param newComp
	 */
	public void addComp(Component newComp);
}
