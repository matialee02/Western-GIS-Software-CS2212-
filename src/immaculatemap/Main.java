package immaculatemap;



import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * Main class, initializes JavaFX components.
 * 
 * @author Scott Gilhuly (sgilhul@uwo.ca)
 */
public class Main extends Application {

	static ArrayList<Building> buildingList = new ArrayList<Building>();
	public static void to_String(){
		
		for (int i = 0; i < buildingList.size(); i++) {
			System.out.print("\n"+buildingList.get(i).getName());
			for (int j = 0; j < buildingList.get(i).getFloors().length; j++) {
				System.out.print(": " + buildingList.get(i).getFloors()[j].getName() + " - ");
				for (int k = 0; k < buildingList.get(i).getFloors()[j].getBuiltInPOIs().length; k++) {
					if (buildingList.get(i).getFloors()[j].getBuiltInPOIs()[k] == null) {
						System.out.print(" null");
					} else {
						System.out.print(" "+buildingList.get(i).getFloors()[j].getBuiltInPOIs()[k].getName());
					}
					
				}
			}
		}
	}
	
	static User currentUser = User.getUser("John Doe", true);
	static String searchedItem = "";
	static Boolean searched = false;
	
	/**
	 * Start method for UI once launched.
     * @Override
	 */
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml")); //fxml data
        primaryStage.setTitle("Immaculate Login"); //Whatever we want our project name to be
        primaryStage.setScene(new Scene(root)); //This controls window size!!!
        primaryStage.show(); 
    }

    /**
     * Main method.
     * Launches UI code.
     * @param args
     */
    public static void main(String[] args) {
    	//test.test_main(args);
    	//test.test_buildinglist();
    	//User.user_test(args);
    	loadBuildings();
        launch(args);
    }
    
    /**
     * Loads building data from file
     */
    public static void loadBuildings() {
    	
    	Type collectionType = new TypeToken<ArrayList<Building>>(){}.getType();
    	Gson gson = new Gson();
		String currentPath = System.getProperty("user.dir");
		currentPath = currentPath + "/" + "MapData" + ".json";
		
		try {
			Reader reader = Files.newBufferedReader(Paths.get(currentPath));
			
			buildingList = gson.fromJson(reader, collectionType);
			
			//System.out.println(buildingList);
			reader.close();
		}
		catch(Exception e){
			System.out.println("ERROR:\n" + e);
		}
    }
    
    /**
     * Call this whenever we want to save changes to the building list
     */
    public static void saveBuildings() {
    	Gson gson = new Gson();
    	String currentPath = System.getProperty("user.dir");		
		currentPath = currentPath + "/" + "MapData" + ".json";
		//System.out.println(currentPath);
		
		try (FileWriter writer = new FileWriter(currentPath)) {
			gson.toJson(buildingList, writer);
			writer.close(); 
		}
		catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void initGson() {
    	Gson clear = new Gson();
    	String currentPath = System.getProperty("user.dir");		
		currentPath = currentPath + "/" + "MapData" + ".json";
		//System.out.println(currentPath);
		ArrayList<Building> empty = new ArrayList<Building>(); 
		
		try (FileWriter writer = new FileWriter(currentPath)) {
			clear.toJson(empty, writer);
			writer.close(); 
		}
		catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void updateGson() {
    	initGson(); 
    	saveBuildings(); 
    }

    
   
    
    /**
     * Call this when user clicks on window 'x' 
     * @param stage
     */
    public static void logout(Stage primaryStage) throws Exception{
    	
		Alert alert = new Alert(AlertType.CONFIRMATION);
    	
        alert.setTitle("Logout");
        alert.setHeaderText("You're about to logout");
        alert.setContentText("Are you sure you want to logout?");
   	
        if (alert.showAndWait().get() == ButtonType.OK) {
        	primaryStage.close();
        	
        	 Parent root = FXMLLoader.load(Main.class.getResource("Login.fxml")); //fxml data
             primaryStage.setTitle("Immaculate Login"); //Whatever we want our project name to be
             primaryStage.setScene(new Scene(root)); //This controls window size!!!
             primaryStage.show(); 
        }
		
	}
    
    
    
    
}
