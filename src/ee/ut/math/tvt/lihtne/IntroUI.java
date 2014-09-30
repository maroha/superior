package ee.ut.math.tvt.lihtne;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

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

public class IntroUI extends Stage {
	
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
	
	//widgets conf
	private int teamListCircleRadius = 3;
	
	Font font = new Font("Arial", 16);
	
	//Insets (Padding)
	private Insets rootPadding = new Insets(20,20,20,20);
	private Insets logoPadding = new Insets(50,0,0,0);
	private Insets membersLabelPadding = new Insets(5,0,0,0);
	
	public IntroUI() {
		//load properties
		readAppProperties(appProperties);
		readVerProperties(verProperties);
		
		//window creation
		setTitle(windowTitle);
		root = new BorderPane();
		root.setPadding(rootPadding);
		Scene scene = new Scene(root, width, height);
		setScene(scene);
		show();	
		log.info("Intro window has been opened.");

		createWidgets();
	}
	
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
		InputStream imgStream = null;
		try {
			imgStream = new FileInputStream(logo);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Image logoImage = new Image(imgStream);
		
		ImageView logoView = new ImageView(logoImage);
		root.setRight(logoView);
		BorderPane.setMargin(logoView, logoPadding);
		
		//version on the bottom
		Label versionLabel = new Label(versionPrefix + version);

		versionLabel.setFont(font);
		root.setBottom(versionLabel);
		BorderPane.setAlignment(versionLabel, Pos.BOTTOM_CENTER);
	}

	private void readAppProperties(String fileName){
		Properties prop = new Properties();
		
		InputStream in = null;
		try {
			in = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
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
	
	private void readVerProperties(String fileName){
		Properties prop = new Properties();
		
		InputStream in = null;
		try {
			in = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String rev = prop.getProperty("build.revision.number");
		String min = prop.getProperty("build.minor.number");
		String maj = prop.getProperty("build.major.number");
		version = maj + "." + min + "." + rev;
		
		prop.setProperty("build.number", version);
		
		OutputStream out = null;
		try {
			out = new FileOutputStream(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			prop.store(out, "");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
