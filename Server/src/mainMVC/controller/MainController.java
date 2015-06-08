package mainMVC.controller;

import java.awt.EventQueue;

import mainMVC.model.IViewAdapter;
import mainMVC.model.MainModel;
import mainMVC.view.IModelAdapter;
import mainMVC.view.MainFrame;
/**
 * Controller for the main MVC.
 * @author 
 */
public class MainController {
	private MainFrame _view;
	private MainModel _model;
	/**
	 * constructor that coonects model and view with adapters
	 */
	public MainController(){
		_view = new MainFrame(new IModelAdapter(){
			public void makeNewTeam(String text) {
				_model.makeTeam(text);
			}

			public void send(String teamName, String text) {
				_model.send(teamName, text);
			}

			@Override
			public void sendMap() {
				// TODO Auto-generated method stub
				_model.sendMap();
			}

			@Override
			public void endGame() {
				_model.endGame();
			}
			});
		_model = new MainModel(new IViewAdapter(){
			public void append(String string) {
				_view.append(string);
			}

			@Override
			public void addRemoteHost(String name) {
				_view.addRemoteHost(name);
				
			}});
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




