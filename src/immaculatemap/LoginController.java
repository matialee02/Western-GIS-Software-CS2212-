package immaculatemap;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.Gson;

public class LoginController {

    @FXML
    private Button login_button;
    @FXML
    private Label wrong_credentials;
    @FXML
    private TextField username_input;
    @FXML
    private PasswordField password_input;
    @FXML
    private Button register_button;
    @FXML
    private TextField register_username;
    @FXML
    private PasswordField register_password;
    @FXML
    private PasswordField confirm_password;
    @FXML
    private Label invalid_registration;
    @FXML
    private CheckBox isCloudEnabled;
    @FXML
    private CheckBox isCloudEnabledLogin;
    @FXML
    private Label wifiConnectionLogin;
    @FXML
    private Label wifiConnectionRegistration;
    
    public AWSFunctions aws = new AWSFunctions();
    
    public void enableCloud(ActionEvent event) {
    	if (!netIsAvailable()){
    		isCloudEnabled.setSelected(false);
    		wifiConnectionRegistration.setText("unable to connect to wifi");
    		
    	}
    	else {
    		isCloudEnabled.setSelected(true);
    		wifiConnectionRegistration.setText("successfully connected to wifi");
    		wifiConnectionRegistration.setStyle("-fx-text-fill: green");
    	}
    }
    public void enableCloudLogin(ActionEvent event) {
    	if (!netIsAvailable()){
    		isCloudEnabled.setSelected(false);
    		wifiConnectionLogin.setText("unable to connect to wifi");
    		
    	}
    	else {
    		isCloudEnabled.setSelected(true);
    		wifiConnectionLogin.setText("successfully connected to wifi");
    		wifiConnectionLogin.setStyle("-fx-text-fill: green");
    	}
    }
    
    public void userLogin(ActionEvent event) throws IOException {
        boolean cloud = isCloudEnabledLogin.isSelected();
        String userName = username_input.getText().toString();
        String enteredPassword = password_input.getText().toString(); 
        User enteredUser = User.getUser(userName, cloud);
        
        //if no data entered
        if(username_input.getText().isEmpty() || password_input.getText().isEmpty()) {
        	wrong_credentials.setText("Please enter your data.");
        }
        
        if (userName.equals("admin") && enteredPassword.equals("DearImmaculateProfessor")) {
            DevPage newPage = new DevPage();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            newPage.start(stage);

        }
        
        //if in cloud and matches
        else if(cloud && AWSFunctions.userExistsInCloud(userName)) {
          	  enteredUser = User.getFromCloud(userName);
          	  
          	  if (!enteredUser.validateLogin(enteredPassword)){
             		wrong_credentials.setText("Incorrect Password");
             		
             	}
          	  else {
          		Main.currentUser = enteredUser;
     			loginSuccessChangeScene(event);
          	  }
        }
        
       // does not exist in cloud
       else {
    	   if (enteredUser != null) {
    		   if (!enteredUser.validateLogin(enteredPassword)){
    	        	wrong_credentials.setText("Incorrect Password");
    	        }
    	        else {
    	        	Main.currentUser = enteredUser;
    	        	loginSuccessChangeScene(event);
    	        	
    	       }
    	   }
    	   else {
    		   wrong_credentials.setText("User name does not exist");
    	   }
       }
    	//loginSuccessChangeScene(event);
    }
    public void userRegistration(ActionEvent event) throws IOException {
        boolean cloud = isCloudEnabled.isSelected();
        String registrationName = register_username.getText().toString();
        User enteredUser = User.getUser(registrationName, cloud);
        String enteredPassword = register_password.getText().toString();
        String confirmPassword = confirm_password.getText().toString();
        
        if(register_username.getText().isEmpty() || register_password.getText().isEmpty()) {
        	invalid_registration.setText("Please enter your data.");
        }
        else if(!confirmPassword.equals(enteredPassword)){
        	invalid_registration.setText("Passwords do not match - Please try again");
        }
        // check if cloud enabled
        else if (cloud) {
        	// if not in cloud.... make and save new user
        	if (!AWSFunctions.userExistsInCloud(registrationName)) {
        		enteredUser = new User(true, registrationName, enteredPassword);
        		enteredUser.saveUserInfo(cloud);
        		enteredUser.saveToCloud();
        		invalid_registration.setStyle("-fx-text-fill: green");
        		invalid_registration.setText("user registration successful - enter using login portal");
        		register_username.clear();
        		register_password.clear();
        		confirm_password.clear();
        	}
        	// if user already there...
        	else {
        		invalid_registration.setText("user already exists");
        	}
        }
        else {
        	// if name is not already present, make new 
        	if (enteredUser == null) {
        		enteredUser = new User(cloud, registrationName, enteredPassword);
        		enteredUser.saveUserInfo(cloud);
        		invalid_registration.setStyle("-fx-text-fill: green");
        		invalid_registration.setText("user registration successful - enter using login portal");
        		register_username.clear();
        		register_password.clear();
        		confirm_password.clear();
        	}
        	else {
        		invalid_registration.setText("User already exists");
        	}
        }

    }
    private void loginSuccessChangeScene(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("mapview.fxml"));
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	Scene scene = new Scene(root, 1200, 700);
    	stage.setScene(scene);
    	stage.show();
    	
        //closing window when user clicks on window 'x'
    	/*stage.setOnCloseRequest(logEvent -> {
    		logEvent.consume();
        	try {
				Main.logout(stage);
			} catch (Exception e) {
			}
        });*/
    }
    
    public static boolean netIsAvailable() {
        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }


}