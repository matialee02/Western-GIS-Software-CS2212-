package immaculatemaptests;

import static org.junit.Assert.*;

import org.junit.*;

import immaculatemap.*;
import org.junit.Test;

public class FloorTest {
	
	/**
	 * Test both Floor constructors
	 */
	@Test
	public void testFloor() {
		Boolean passed = false;
		Floor testFloor = null;
		testFloor = new Floor();

		if(testFloor != null) {
			testFloor = null;
			POI[] builtIn = {null, null, null, null};
			testFloor = new Floor("Level 3", "WH/WH-LEVEL3.png", "WH-Accessibility/WH-Accessibility-3.png", builtIn, "Westminster Hall");
			if(testFloor != null) {
				passed = true;
			}
		}
		
		assertTrue(passed);
	}

	/**
	 * Test the setName method
	 */
	@Test
	public void testSetName() {
		Floor testFloor = new Floor();
		testFloor.setName("test name");
		
		assertEquals(testFloor.getName(), "test name");
	}

	/**
	 * Test the getName method
	 */
	@Test
	public void testGetName() {
		Floor testFloor = new Floor();
		testFloor.setName("test name");
		
		String name = testFloor.getName();
		
		assertEquals(name, "test name");
	}

	/**
	 * Test the setMapImage method
	 */
	@Test
	public void testSetMapImage() {
		Floor testFloor = new Floor();
		testFloor.setMapImage("WH/WH-LEVEL3.png");
		
		assertEquals(testFloor.getMapImage(), "WH/WH-LEVEL3.png");
	}

	/**
	 * Test the getMapImage method
	 */
	@Test
	public void testGetMapImage() {
		Floor testFloor = new Floor();
		testFloor.setMapImage("WH/WH-LEVEL3.png");
		
		String mapImage = testFloor.getMapImage();
		
		assertEquals(mapImage, "WH/WH-LEVEL3.png");
	}

	/**
	 * Test the setAccessibilityLayer method
	 */
	@Test
	public void testSetAccessibilityLayer() {
		Floor testFloor = new Floor();
		testFloor.setAccessibilityLayer("WH-Accessibility/WH-Accessibility-3.png");
		
		String accessibilityMapImage = testFloor.getAccessibilityLayer();
		
		assertEquals(accessibilityMapImage, "WH-Accessibility/WH-Accessibility-3.png");
	}

	/**
	 * Test the getAccessibilityLayer method
	 */
	@Test
	public void testGetAccessibilityLayer() {
		Floor testFloor = new Floor();
		testFloor.setAccessibilityLayer("WH-Accessibility/WH-Accessibility-3.png");
		
		String accessibilityMapImage = testFloor.getAccessibilityLayer();
		
		assertEquals(accessibilityMapImage, "WH-Accessibility/WH-Accessibility-3.png");
	}

	/**
	 * Test the setBuiltInPOIs method
	 */
	@Test
	public void testSetBuiltInPOIs() {
		Floor testFloor = new Floor();
		
		POI POI1 = new POI("POI1", "none", "type", null, null);
		POI POI2 = new POI("POI2", "none", "type", null, null);
		POI POI3 = new POI("POI3", "none", "type", null, null);
		
		POI[] buitInPOIs = {POI1, POI2, POI3};
		
		testFloor.setBuiltInPOIs(buitInPOIs);
		
		assertTrue(testFloor.getBuiltInPOIs()[0] != null);
	}

	/**
	 * Test the getBuiltInPOIs method
	 */
	@Test
	public void testGetBuiltInPOIs() {
		Floor testFloor = new Floor();
		
		POI POI1 = new POI("POI1", "none", "type", null, null);
		POI POI2 = new POI("POI2", "none", "type", null, null);
		POI POI3 = new POI("POI3", "none", "type", null, null);
		
		POI[] buitInPOIs = {POI1, POI2, POI3};
		
		testFloor.setBuiltInPOIs(buitInPOIs);
		
		assertTrue(testFloor.getBuiltInPOIs()[0] != null);
	}

	/**
	 * Test the hasBuiltInPOI method
	 */
	@Test
	public void testHasBuiltInPOI() {
		Floor testFloor = new Floor();
		
		POI POI1 = new POI("POI1", "none", "type", null, null);
		POI POI2 = new POI("POI2", "none", "type", null, null);
		POI POI3 = new POI("POI3", "none", "type", null, null);
		
		POI[] buitInPOIs = {POI1, POI2, POI3};
		testFloor.setBuiltInPOIs(buitInPOIs);
		
		Boolean passed = testFloor.hasBuiltInPOI("POI1") && testFloor.hasBuiltInPOI("POI2") && testFloor.hasBuiltInPOI("POI3");
		
		assertTrue(passed);
	}

	/**
	 * Test the equals method
	 */
	@Test
	public void testEquals() {
		POI[] builtIn = {null, null, null, null};
		Floor testFloor = new Floor("Level 3", "WH/WH-LEVEL3.png", "WH-Accessibility/WH-Accessibility-3.png", builtIn, "Westminster Hall");
		Floor testFloor2 = new Floor("Level 3", "WH/WH-LEVEL3.png", "WH-Accessibility/WH-Accessibility-3.png", builtIn, "Westminster Hall");
		
		Boolean passed = testFloor.equals(testFloor2);
		
		assertTrue(passed);
	}

	/**
	 * Test the setBuilding method
	 */
	@Test
	public void testSetBuilding() {
		Floor testFloor = new Floor();
		
		testFloor.setBuilding("Westminster Hall");
		
		String building = testFloor.getBuilding();
		
		assertEquals(building, "Westminster Hall");
	}

	/**
	 * Test the getBuilding method
	 */
	@Test
	public void testGetBuilding() {
		Floor testFloor = new Floor();
		
		testFloor.setBuilding("Westminster Hall");
		
		String building = testFloor.getBuilding();
		
		assertEquals(building, "Westminster Hall");
	}

}
