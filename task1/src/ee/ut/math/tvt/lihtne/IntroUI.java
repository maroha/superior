package ee.ut.math.tvt.lihtne;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.lihtne.util.Util;

/**
 * Intro window for the lab3 team project.
 * @author Kevin Nemerzitski
 */
class IntroUI extends Stage {
	
	private static Logger log = Logger.getLogger(IntroUI.class);
	
	private String appProperties = "application.properties";
	private String verProperties = "version.properties";
	
	//Loaded from properties
	private String teamName, leader, leaderEmail, logo, version;
	private String[] members;
	
	private String namePrefix = "Team name: ";
	private String leaderPrefix = "Leader: ";
	private String emailPrefix = "Leader email: ";
	private String memberPrefix = "Members: ";
	private String versionPrefix = "Version number: ";
	
	//window conf
	private String windowTitle = "Intro Title";
	private int width = 480;
	private int height = 360;
	
	private BorderPane root;
	
	//Widgets conf
	private int teamListCircleRadius = 3;
	
	private Font font = new Font("Arial", 16);
	
	//Insets (Padding)
	private Insets rootPadding = new Insets(20,20,20,20);
	private Insets logoPadding = new Insets(50,0,0,0);
	private Insets membersLabelPadding = new Insets(5,0,0,0);
	
	public IntroUI() {
		//load properties
		readAppProperties(appProperties);
		readVerProperties(verProperties);
		
		//center the stage
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setX((screen.width - width) / 2);
		setY((screen.height - height) / 2);
		
		//window creation
		setTitle(windowTitle);
		root = new BorderPane();
		root.setPadding(rootPadding);
		Scene scene = new Scene(root, width, height);
		setScene(scene);
		
		log.info("Intro window has been opened.");

		createWidgets();
	}
	
	/**
	 * Stage widgets
	 * Creates team info in vbox, a logo on the right
	 * and version number on the bottom.
	 */
	private void createWidgets(){
		//team info on the left
		VBox teamInfo = new VBox();
		root.setLeft(teamInfo);
		
		Label teamNameLabel = new Label(namePrefix + teamName);
		teamNameLabel.setFont(font);
		teamInfo.getChildren().add(teamNameLabel);
		
		Label leaderLabel = new Label(leaderPrefix + leader);
		leaderLabel.setFont(font);
		teamInfo.getChildren().add(leaderLabel);
		
		Label emailLabel = new Label(emailPrefix + leaderEmail);
		emailLabel.setFont(font);
		teamInfo.getChildren().add(emailLabel);
		
		Label membersLabel = new Label(memberPrefix);
		membersLabel.setFont(font);
		membersLabel.setPadding(membersLabelPadding);
		teamInfo.getChildren().add(membersLabel);
		
		for(String name: members){
			Label l = new Label(name);
			l.setFont(font);
			l.setGraphic(new Circle(teamListCircleRadius));
			teamInfo.getChildren().add(l);
		}
		
		//logo on the right
		InputStream imgStream = Util.getFileInputStream(logo);
		Image logoImage = new Image(imgStream);
		ImageView logoView = new ImageView(logoImage);
		root.setRight(logoView);
		BorderPane.setMargin(logoView, logoPadding);
		
		//version number on the bottom
		Label versionLabel = new Label(versionPrefix + version);
		versionLabel.setFont(font);
		root.setBottom(versionLabel);
		BorderPane.setAlignment(versionLabel, Pos.BOTTOM_CENTER);
	}

	/**
	 * Reads team.name, leader, email, logo and members info
	 * from the file and stores values to this object.
	 * @param fileName - app.properties path from basedir
	 */
	private void readAppProperties(String fileName){
		Properties prop = new Properties();
		
		InputStream in = Util.getFileInputStream(fileName);
		
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		teamName = prop.getProperty("team.name");
		leader = prop.getProperty("team.leader");
		leaderEmail = prop.getProperty("team.leader_email");
		logo = prop.getProperty("team.logo");
		String strMembers = prop.getProperty("team.members");
		members = strMembers.split(",");
		
		for(int i=0;i<members.length;i++){
			members[i] = members[i].trim();
		}
		
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads build revision, minor and major numbers and
	 * stores value to version variable.
	 * @param fileName - ver.properties path from basedir
	 */
	private void readVerProperties(String fileName){
		Properties prop = new Properties();
		
		InputStream in = Util.getFileInputStream(fileName);
		
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String rev = prop.getProperty("build.revision.number");
		String min = prop.getProperty("build.minor.number");
		String maj = prop.getProperty("build.major.number");
		version = maj + "." + min + "." + rev;
	}

}
