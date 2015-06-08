package mainMVC.controller;

import java.awt.Component;
import java.awt.EventQueue;

import clientMainMVC.model.IMiniModel2ViewAdapter;
import clientMainMVC.model.IViewAdapter;
import clientMainMVC.model.MainModel;
import clientMainMVC.model.MiniModel;
import mainMVC.view.IMiniView2ModelAdapter;
import mainMVC.view.IModelAdapter;
import mainMVC.view.MainFrame;
import mainMVC.view.MiniView;
/**
 * Controller for the main MVC.
 * The winner of the game is the team who scores the most points.  Points are scored by assigning captured humans to a different jobs: mining, factory work, farming, and fishing.
 *	Different work nodes produce points at different rates, but work nodes also have certain death rates.  Only the team with the most humans at any work node receives any points.
 * At the onset of the game, teams will choose 3 cities that will produce humans for the team.  Cities are selected by clicking on the corresponding city annotation on the map.
 * The first team to select a city captures it and no other team may select it.  Each team may select 3 cities from which to capture humans.  Cities differ in he initial human yield as well 
 * as the number of humans generated per second of gameplay.  This information can be seen in the right info pane.  After players chose the city, they could start moving humans to one of the 
 * work nodes by entering the amount using textfield and the destination using combo box. Then the scores and game status got updated every five seconds so that each player could see
 * what the game status is and continue to move their humans.
 * @author Bojun bw6 , LiTre, tsg1
 */
public class MainController {
	/**
	 * main view
	 */
	private MainFrame _view;
	/**
	 * main model
	 */
	private MainModel _model;
	/**
	 * constructor where view and model, miniview and minimodel are connected using adapters
	 */
	public MainController(){
		_view = new MainFrame(new IModelAdapter(){
			public String connectTo(String address) {
				return _model.connectTo(address);
			}
			public void joinGame(String uniqueString) {
				_model.joinGame(uniqueString);
			}
			public void informChatroomChoice(String s) {
				_model.informChatroomChoice(s);
			}});
		_model = new MainModel(new IViewAdapter(){
			public void append(String string) {
				_view.append(string);
			}
			/**
			 * factory method that takes a minimodel and return a minimodel-miniview adapter
			 */
			public IMiniModel2ViewAdapter makeMiniModel2ViewAdapter(
					final MiniModel miniModel) {
				// TODO Auto-generated method stub
				IMiniView2ModelAdapter mv2ma = new IMiniView2ModelAdapter(){
					public void sendTextMessage(String text) {
						miniModel.sendTextMessage(text);
					}
					public void leave() {
						
						miniModel.leave();
					}};
				final MiniView miniView = _view.makeMiniView(mv2ma);
				return new IMiniModel2ViewAdapter(){
					public void append(String s) {
						miniView.append(s);
					}

					@Override
					public void addComp(Component newComp) {
						miniView.addComp(newComp);
					}
					};
			}

			});
	}

	/**
	 * Starts the view then the model.  The view needs to be started first so that it can display 
	 * the model status updates as it starts.
	 */
	public void start(){
		_view.start();
		_model.start();
	}


	/**
	 * Main() method of the client application. Instantiates and then starts the controller.
	 * @param args ignored
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable(){

			public void run() {
				MainController mc = new MainController();
				mc.start();
			}
			
		});
	}
}




