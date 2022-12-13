

package immaculatemap;

import com.google.gson.*;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * User entity used for storing user data, storing user data, and performing operations on that user data.
 * <br><br>
 * 
 * <b>Example Use:</b>
 * <pre>
 * {@code 
 * 			User sampleUser = new User(true, "Name", "password");
 * 			sampleUser.getName();
 * 			sampleUser.saveToCloud();
 * 			System.out.println(sampleUser.validateLogin("password");
 * }
 * </pre>
 * <b>Example Output:</b> <code>true</code><br>
*
* @version 1.0
* @author Connor McGoey
*  */
public class User {
	/** User's name */
	private String name;
	/** User's password */
	private String password;
	/** User's favourites<br> @see POI */
	private POI[] favourites;
	/** User's created POIs<br> @see POI */
	public POI[] userPOIs;
	/** Does the user have cloud enabled */
	public Boolean isCloudEnabled;
	
	/**
	 * User constructor. Creates new User object
	 * 
	 * @param cloudEnabled whether or not the user signed up with cloud-enabled
	 * @param name User's name
	 * @param password User's password
	 */
	public User(Boolean cloudEnabled, String name, String password){
		isCloudEnabled = cloudEnabled;
		this.name = name;
		this.password = password;
		this.favourites = new POI[10];
		this.userPOIs = new POI[5];
		saveUserInfo(this.isCloudEnabled);
	}
	
	/**
	 * Get the User's name
	 * 
	 * @return the User's name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Test to see if the User has their POI array filled
	 * 
	 * @return false if the User's POI array has an open slot, true if it is full
	 */
	public Boolean userPOIsFull() {
		for(int i = 0; i < userPOIs.length; i++) {
			if(userPOIs[i] == null) return false;
		}
		return true;
	}
	
	/**
	 * Test to see if the User has their Favourites POI array filled
	 * 
	 * @return false if the User's Favourites POI array has an open slot, true if it is full
	 */
	public Boolean userFavouritesFull() {
		for(int i = 0; i < favourites.length; i++) {
			if(favourites[i] == null) return false;
		}
		return true;
	}
	
	/**
	 * Add a POI to the User's POI array. Save user info after if changed.
	 * 
	 * @param POIName the name of the POI to be added
	 * @param description a description of the POI to be added
	 * @param type the type of the POI to be added
	 * @param location the location of the POI to be added
	 * @param floor the floor of the POI to be added
	 * @return true if successfully added to the POI array, false otherwise
	 * @see saveUserInfo
	 * @see POI
	 */
	public Boolean addPOI(String POIName, String description, String type, int[] location, Floor floor) {
		// Create POI with POI Constructor here
		for (int i = 0; i < 5; i++) {
			if ((this.userPOIs[i] != null) && (this.userPOIs[i].name.equals(POIName))) {
				return false;
			}
		}
		POI newPOI = new POI(POIName, description, type, location, floor);
		for (int i = 0; i < 5; i++) {
			if (this.userPOIs[i] == null) {
				this.userPOIs[i] = newPOI;
				saveUserInfo(this.isCloudEnabled);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Remove a POI from the User's POI array. Save user info after if changed.
	 * 
	 * @param POIName name of the POI to be removed
	 * @return true if removed
	 * @see saveUserInfo
	 */
	public Boolean removePOI(String POIName) {
		for (int i = 0; i < 5; i++) {
			if ((this.userPOIs[i] != null) && (this.userPOIs[i].name.equals(POIName))) {
				this.userPOIs[i] = null;
				saveUserInfo(this.isCloudEnabled);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Return an array of the User's favourites
	 * 
	 * @return the User's favourites POI array
	 */
	public POI[] getFavourites() {
		return this.favourites;
	}
	
	/**
	 * Return an array of the User's POIs
	 * 
	 * @return the User's custom POI array
	 */
	public POI[] getUserPOIs() {
		return this.userPOIs;
	}
	
	/**
	 * Add a favourite to the User's POI array POI array. Save changes locally if there was a change.
	 * 
	 * @param favourite a POI object to be added into the User's favourites
	 * @return true if the object was added, false otherwise.
	 * @see saveUserInfo
	 * @see POI
	 */
	public Boolean addFavourite(POI favourite) {
		for (int i = 0; i < 10; i++) {
			if (this.favourites[i] == null) {
				this.favourites[i] = favourite;
				saveUserInfo(this.isCloudEnabled);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Remove a favourite from the favourites array. Save changes if a change is made.
	 * 
	 * @param POIName the name of the favourite to be removed
	 * @return true if the favourite was removed. False if it wasn't found.
	 * @see saveUserInfo
	 */
	public Boolean removeFavourite(String POIName) {
		for (int i = 0; i < 10; i++) {
			if ((this.favourites[i] != null) && (this.favourites[i].name.equals(POIName))) {
				this.favourites[i] = null;
				saveUserInfo(this.isCloudEnabled);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Validate a User's password
	 * 
	 * @param password password to compare to the User's saved password
	 * @return true if the passwords match, false otherwise
	 */
	public Boolean validateLogin(String password) {
		if (this.password.equals(password)) return true;
		else return false;
	}
	
	/**
	 * Save user information locally and format the file name depending on whether they are cloud enabled.
	 * 
	 * @param isCloudEnabled whether or not the User has a cloud enabled account
	 */
	public void saveUserInfo(boolean isCloudEnabled) {
		
		Gson gson = new Gson();
		String fileName = this.name.toLowerCase();
		fileName = fileName.replaceAll("\\s+","");
		String currentPath = System.getProperty("user.dir");
		
		if (!isCloudEnabled) {
			currentPath = currentPath + "/Users/" + fileName + "LOCAL" + ".json";
		} else {
			currentPath = currentPath + "/Users/" + fileName + "CLOUD" + ".json";
		}
		
		try (FileWriter writer = new FileWriter(currentPath)) {
			gson.toJson(this, writer);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the User's locally saved JSON file and return it converted into a user
	 * 
	 * @param userName the name of the User
	 * @param isCloudEnabled whether or not the user had cloud enabled
	 * @return a User object representation of the information stored locally or null if the user doesn't exist 
	 */
	public static User getUser(String userName, boolean isCloudEnabled) {
		Gson gson = new Gson();
		String fileName = userName.toLowerCase();
		fileName = fileName.replaceAll("\\s+",""); 
		String currentPath = System.getProperty("user.dir");
		
		if (!isCloudEnabled) {
			currentPath = currentPath + "/Users/" + fileName + "LOCAL.json";
		} else {
			currentPath = currentPath + "/Users/" + fileName + "CLOUD.json";
		}
		try {
			Reader reader = Files.newBufferedReader(Paths.get(currentPath));
			User user = gson.fromJson(reader,User.class);
			reader.close();
			return user;
		}
		catch(Exception e){
			System.out.println("ERROR: NO SUCH FILE EXISTS \n" + e);
		}
		return null;
	}
	
	/**
	 * Save/overwrite the User's information to AWS S3 bucket in the cloud.
	 * 
	 * @return true if the upload is successful, false otherwise
	 * 
	 * @see AWSFunctions
	 */
	public boolean saveToCloud() {
		return AWSFunctions.uploadJsonToCloud(this.name);
	}
	
	/**
	 * Get the cloud saved User information from AWS S3 bucket
	 * 
	 * @param userName the name of the User whose information is stored in the cloud
	 * @return User object of the information stored in the AWS S3 bucket.
	 * @see AWSFunctions
	 */
	public static User getFromCloud(String userName) {
		return AWSFunctions.getUserFromCloud(userName);
	}
}