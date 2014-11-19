package ee.ut.math.tvt.lihtne;

import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.controller.impl.SalesDomainControllerImpl;
import ee.ut.math.tvt.salessystem.ui.ConsoleUI;
import ee.ut.math.tvt.salessystem.ui.SalesSystemUI;

/**
 * MAIN CLASS Starts JavaFX application and initializes SaleesSystemUI / ConsoleUI
 * 
 * @author Kevin Nemerzitski
 */
public class Intro extends Application {

	private static final Logger log = Logger.getLogger(Intro.class);
	private static final String MODE = "console";

	private int introDuration = 0; // milliseconds, 0 - disabled

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			List<String> params = getParameters().getRaw();
			String[] args = new String[params.size()];
			params.toArray(args);

			final SalesDomainController domainController = new SalesDomainControllerImpl();

			if (args.length == 1 && args[0].equals(MODE)) {
				log.debug("Mode: " + MODE);

				ConsoleUI cui = new ConsoleUI(domainController);
				cui.run();
			} else {
				IntroUI introUI = new IntroUI();
				introUI.initModality(Modality.APPLICATION_MODAL);
				introUI.setAlwaysOnTop(true);

				final SalesSystemUI ui = new SalesSystemUI(domainController);

				if (introDuration > 0) {
					introUI.show();
					new Timeline(new KeyFrame(Duration.millis(introDuration),
						action -> {
							introUI.hide();
							ui.show();
						}
					)).play();
				} else
					ui.show();

			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
