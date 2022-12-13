package immaculatemaptests;
import static org.junit.Assert.*;

import org.junit.*;

import immaculatemap.*;
public class POITest {

	@Test
	public void testPOI() {
		System.out.println("testPOI");
		POI instance = new POI("test", "description", "place",
               new int[2], null);
		assertTrue(instance instanceof POI);
	}

	@Test
	public void testGetName() {
		System.out.println("testGetName");
		POI instance = new POI("test", "description", "place",
	               new int[2], null);
		String instanceName = instance.getName();
		assertEquals("test", instanceName);
	}
	
	@Test
	public void testDescription() {
		System.out.println("testGetDescription");
		POI instance = new POI("test", "description", "place",
	               new int[2], null);
		String instanceDescription = instance.getDescription();
		assertEquals("description", instanceDescription);
	}

	@Test
	public void testType() {
		System.out.println("testGetType");
		POI instance = new POI("test", "description", "place",
	               new int[2], null);
		String instanceType = instance.getType();
		assertEquals("place", instanceType);
	}
	
	@Test
	public void testPosition() {
		System.out.println("testGetPosition");
		int pos[] = {2,3};
		POI instance = new POI("test", "description", "place",
	               pos, null);
		int instanceLocation[] = instance.getLocation();
		assertEquals(pos, instanceLocation);
	}
	
	@Test
	public void testFloor() {
		System.out.println("testGetFloor");
		Floor floor = new Floor("testFloor","mapimg","accimg",null,"building");
		POI instance = new POI("test", "description", "place",
	               null, floor);
		Floor instanceFloor = instance.getFloor();
		assertEquals(floor, instanceFloor);
	}
}
