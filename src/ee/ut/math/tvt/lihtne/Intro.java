package ee.ut.math.tvt.lihtne;

import java.io.File;

import org.apache.log4j.Logger;

import javafx.application.Application;
import javafx.stage.Stage;

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
