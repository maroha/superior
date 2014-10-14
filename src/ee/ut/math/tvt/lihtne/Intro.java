package ee.ut.math.tvt.lihtne;

import org.apache.log4j.Logger;

import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 * MAIN CLASS
 * Starts JavaFX application and initializes IntroUI stage.
 * @author Kevin Nemerzitski
 */
public class Intro extends Application{
	
	private static final Logger log = Logger.getLogger(Intro.class);
	private static final String MODE = "console";
	
	private static String[] args;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try{
			String[] args = (String[]) getParameters().getUnnamed().toArray();
			Log.
			final SalesDomainController domainController = new SalesDomainControllerImpl();
			
			if (args.length == 1 && args[0].equals(MODE)) {
				log.debug("Mode: " + MODE);

				ConsoleUI cui = new ConsoleUI(domainController);
				cui.run();
			} else {

				IntroUI introUI = new IntroUI();
				introUI.show();
				introUI.initModality(Modality.APPLICATION_MODAL);

				final SalesSystemUI ui = new SalesSystemUI(domainController);
				ui.setVisible(true);

				introUI.initModality(Modality.NONE);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				introUI.hide();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		launch(args);
	}


}
