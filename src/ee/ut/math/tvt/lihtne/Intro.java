package ee.ut.math.tvt.lihtne;

import javafx.application.Application;
import javafx.stage.Stage;
/**
 * MAIN CLASS
 * Starts JavaFX application and initializes IntroUI stage.
 * @author Kevin Nemerzitski
 */
public class Intro extends Application{
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try{
			new IntroUI();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		launch(args);
	}


}
