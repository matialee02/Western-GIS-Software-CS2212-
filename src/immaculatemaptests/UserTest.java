package immaculatemaptests;

import static org.junit.Assert.*;

import org.junit.*;

import immaculatemap.*;
/**
 * Unit tests for the User class
 * 
 * @author Connor McGoey
 *
 */
public class UserTest {
	
	/**
	 * Test the user constructor
	 * 
	 */
	@Test
	public void testUser() {
		System.out.println("testUser");
		User instance = new User(false, "TestUser", "123456");
		assertTrue(instance instanceof User);
	}

	/**
	 * Test the getName method
	 */
	@Test
	public void testGetName() {
		System.out.println("testGetName");
		User instance = User.getUser("TestUser", false);
		String instanceName = instance.getName();
		assertEquals("TestUser", instanceName);
	}

	/**
	 * Test the userPOIsFull method
	 */
	@Test
	public void testUserPOIsFull() {
		System.out.println("testUserPOIsFull");
		User instance = User.getUser("TestUser", false);
		
		Boolean passed = false;
		
		instance.addPOI("POI1", "none", "type", null, null);
		instance.addPOI("POI2", "none", "type", null, null);
		instance.addPOI("POI3", "none", "type", null, null);
		instance.addPOI("POI4", "none", "type", null, null);
		instance.addPOI("POI5", "none", "type", null, null);
		if(instance.userPOIsFull() == true) { 
			passed = true;
		
			instance.removePOI("POI1");
			instance.removePOI("POI2");
			instance.removePOI("POI3");
			instance.removePOI("POI4");
			instance.removePOI("POI5");
			
			if(instance.userPOIsFull() == false) {
				passed = true;
			} else {
				passed = false;
			}
		}
		
		assertTrue(passed);
	}

	/**
	 * Test the userFavouritesFull method
	 */
	@Test
	public void testUserFavouritesFull() {
		System.out.println("testUserFavouritesFull");
		User instance = User.getUser("TestUser", false);
		
		Boolean passed = false;
		
		POI POI1 = new POI("POI1", "none", "type", null, null);
		POI POI2 = new POI("POI2", "none", "type", null, null);
		POI POI3 = new POI("POI3", "none", "type", null, null);
		POI POI4 = new POI("POI4", "none", "type", null, null);
		POI POI5 = new POI("POI5", "none", "type", null, null);
		POI POI6 = new POI("POI6", "none", "type", null, null);
		POI POI7 = new POI("POI7", "none", "type", null, null);
		POI POI8 = new POI("POI8", "none", "type", null, null);
		POI POI9 = new POI("POI9", "none", "type", null, null);
		POI POI10 = new POI("POI10", "none", "type", null, null);
		
		
		instance.addFavourite(POI1);
		instance.addFavourite(POI2);
		instance.addFavourite(POI3);
		instance.addFavourite(POI4);
		instance.addFavourite(POI5);
		instance.addFavourite(POI6);
		instance.addFavourite(POI7);
		instance.addFavourite(POI8);
		instance.addFavourite(POI9);
		instance.addFavourite(POI10);
		
		if(instance.userFavouritesFull() == true) {
			passed = true;
			
			instance.removeFavourite("POI1");
			instance.removeFavourite("POI2");
			instance.removeFavourite("POI3");
			instance.removeFavourite("POI4");
			instance.removeFavourite("POI5");
			instance.removeFavourite("POI6");
			instance.removeFavourite("POI7");
			instance.removeFavourite("POI8");
			instance.removeFavourite("POI9");
			instance.removeFavourite("POI10");
			
			if(instance.userFavouritesFull() == false) {
				passed = true;
			} else {
				passed = false;
			}
		}
		
		assertTrue(passed);
	}

	/**
	 * Test the addPOI method
	 */
	@Test
	public void testAddPOI() {
		System.out.println("testAddPOI");
		User instance = User.getUser("TestUser", false);
		
		Boolean passed = false;
		
		instance.addPOI("POI1", "none", "type", null, null);
		
		if(instance.getUserPOIs()[0] != null) passed = true;
		
		instance.removePOI("POI1");
		
		assertTrue(passed);
	}

	/**
	 * Test the removePOI method
	 */
	@Test
	public void testRemovePOI() {
		System.out.println("testRemovePOI");
		User instance = User.getUser("TestUser", false);
		
		instance.addPOI("POI1", "none", "type", null, null);
		instance.removePOI("POI1");
		
		POI[] testPOIs = instance.getUserPOIs();
		POI firstPOI = instance.getUserPOIs()[0];
		
		Boolean passed = instance.getUserPOIs()[0] == null;
		
		assertTrue(passed);
	}

	/**
	 * Test the getFavourites method
	 */
	@Test
	public void testGetFavourites() {
		System.out.println("testGetFavourites");
		User instance = User.getUser("TestUser", false);
		
		POI[] testInstanceFavourites = null;
		
		testInstanceFavourites = instance.getFavourites();
		
		assertTrue(testInstanceFavourites != null);
	}

	/**
	 * Test the getUserPOIs method
	 */
	@Test
	public void testGetUserPOIs() {
		System.out.println("testGetUserPOIs");
		User instance = User.getUser("TestUser", false);
		
		POI[] testInstancePOIs = null;
		
		testInstancePOIs = instance.getUserPOIs();
		
		assertTrue(testInstancePOIs != null);
	}

	/**
	 * Test the addFavourite method
	 */
	@Test
	public void testAddFavourite() {
		System.out.println("testAddFavourite");
		User instance = User.getUser("TestUser", false);
		
		POI POI1 = new POI("POI1", "none", "type", null, null);
		instance.addFavourite(POI1);
		
		Boolean passed = instance.getFavourites()[0] != null;
		instance.removeFavourite("POI1");
		
		assertTrue(passed);
	}

	/**
	 * test the removeFavourite method
	 */
	@Test
	public void testRemoveFavourite() {
		System.out.println("testRemoveFavourite");
		User instance = User.getUser("TestUser", false);
		POI POI1 = new POI("POI1", "none", "type", null, null);
		instance.addFavourite(POI1);
		
		Boolean passed = false;
		
		instance.removeFavourite("POI1");
		
		passed = instance.getFavourites()[0] == null;
		
		assertTrue(passed);
	}

	/**
	 * Tests the validateLogin method
	 */
	@Test
	public void testValidateLogin() {
		System.out.println("testValidateLogin");
		User instance = User.getUser("TestUser", false);
		
		Boolean passed = instance.validateLogin("123456");
		
		assertTrue(passed);
	}

	/**
	 * Tests the saveUserInfo method
	 */
	@Test
	public void testSaveUserInfo() {
		System.out.println("testSaveUserInfo");
		User instance = User.getUser("TestUser", false);
		
		instance.addPOI("POI1", "none", "type", null, null);
		instance.saveUserInfo(false);
		
		instance = null;
		
		instance = User.getUser("TestUser", false);
		
		Boolean passed = instance.getUserPOIs()[0] != null;
		
		instance.removePOI("POI1");
		
		assertTrue(passed);
	}

	/**
	 * Tests the getUser method
	 */
	@Test
	public void testGetUser() {
		System.out.println("testGetUser");
		User instance = null;
		instance = User.getUser("TestUser", false);
		
		Boolean passed = (instance != null) && (instance.getName().equals("TestUser"));
		
		assertTrue(passed);
	}

	/**
	 * Test the saveToCloud method
	 */
	@Test
	public void testSaveToCloud() {
		System.out.println("testSaveToCloud");
		User cloudInstance = new User(true, "TestUser2", "123456");
		
		cloudInstance.saveToCloud();
		cloudInstance = null;
		cloudInstance = User.getFromCloud("TestUser2");
		
		Boolean passed = (cloudInstance != null) && (cloudInstance.getName().equals("TestUser2"));
		
		assertTrue(passed);
	}

	/**
	 * Test the getFromCloud method
	 */
	@Test
	public void testGetFromCloud() {
		System.out.println("testGetFromCloud");
		
		User cloudInstance = null;
		
		cloudInstance = User.getFromCloud("TestUser2");
		
		Boolean passed = (cloudInstance != null) && (cloudInstance.getName().equals("TestUser2"));

		assertTrue(passed);
	}

}
