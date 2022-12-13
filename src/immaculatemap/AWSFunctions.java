package immaculatemap;

import com.amazonaws.AmazonServiceException;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class is responsible for all connectivity to the AWS S3 bucket including all relevant bucket connectivity and checks.
 * 
 * @author Connor McGoey
 * @version 1.0
 */
public class AWSFunctions {

	/**
	 * Method to upload the locally stored JSON file to the cloud
	 * 
	 * @param userName the name of the user whose information is to be uploaded
	 * @return true if the upload was successful, false otherwise
	 */
	public static boolean uploadJsonToCloud(String userName) {
		String filename = userName.toLowerCase();
		filename = filename.replaceAll("\\s+","");
		filename = filename + "CLOUD.json";
		String filepath = System.getProperty("user.dir") + "/Users/" + filename;
		System.out.format("Uploading %s to S3 bucket cs2212-project...\n", filename);
		final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
		try {
		    s3.putObject("cs2212-project", filename, new File(filepath));
		    return true;
		} catch (AmazonServiceException e) {
		    System.err.println(e.getErrorMessage());
		    System.exit(1);
		}
		return false;
	}
	
	/**
	 * Method to determine if a User exists in the S3 bucket already.
	 * 
	 * @param userName the name of the person who may exist in the cloud S3 bucket
	 * @return true if the User exists in the cloud, false if the do not
	 */
	public static boolean userExistsInCloud(String userName) {
		String filename = userName.toLowerCase();
		filename = filename.replaceAll("\\s+","");
		filename = filename + "CLOUD.json";
		System.out.format("Seeing if %s exists in S3 bucket cs2212-project...\n", filename);
		final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
		try {
			boolean exists = s3.doesObjectExist("cs2212-project", filename);
			return exists;
		} catch(AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
		    System.exit(1);
		}
		return false;
	}
	
	/**
	 * Method to get the User's information from the cloud, convert it into a User object, and then return it. <br>
	 * This method was made with the following resource:  https://www.youtube.com/watch?v=iQrOmbCiFBs&ab_channel=CodeSpace
	 * 
	 * @param userName the name of the user whose information is to be downloaded
	 * @return the User object of the person's information or null if an error occurred
	 */
	public static User getUserFromCloud(String userName) {
		String filename = userName.toLowerCase();
		filename = filename.replaceAll("\\s+","");
		filename = filename + "CLOUD.json";
		Gson gson = new Gson();
		System.out.format("Retrieving %s from S3 bucket cs2212-project...\n", filename);
		final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
		try {
			S3Object s3Object = s3.getObject(new GetObjectRequest("cs2212-project", filename));
			BufferedReader reader = new BufferedReader(new InputStreamReader(s3Object.getObjectContent()));
			User user = gson.fromJson(reader,User.class);
			user.saveUserInfo(true);
			reader.close();
			System.out.format("File %s found and returned\n", filename);
			return user;
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch(AmazonS3Exception e) {
			System.out.format("File %s NOT found", filename);
		}
		return null;
	}
}
