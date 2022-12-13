// ** final edit dec 3 0230 1510 hrs
// whoever keeps pushing the old version of tests, can you not?
// my heart stopped for a second cause i thought the tests were failing again
// - matia
package immaculatemap;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

/**
 * @author matialee (m334lee@uwo.ca)
 * @author Scott Gilhuly (sgilhul@uwo.ca)
 * November 2022
 * Test Cases for Building and POI classes
 * */

// NOTE: Test cases for User and Floor class are INCLUDED IN THE RESPECTIVE CLASS FILES.

class test {
    static private int total_tests = 0, passed_tests = 0;
    
    static ArrayList<Building> testbuildinglist; // fake list of buildings for test purposes

    static private void test_equality(Object obj, Object obj2) {
        total_tests++;
        if (obj.equals(obj2)) {
            System.out.println("Test " + (total_tests) + " Passed. ");
            passed_tests++;
        } else {
            System.out.println("Test " + (total_tests) + " Failed. \n"
                    + "\tExpected: " + obj2 + "\t | \t Actual: " + obj);
        }
    }

    // Assumes obj, obj2 are arrays of objects
    static private void array_equality(Object[] obj, Object[] obj2) {
        total_tests++;
        if (obj.length != obj2.length) {
            System.out.println("Test " + (total_tests) + " Failed. \n"
                    + "\tExpected: " + obj2 + " Actual: " + obj);
            return; }
        for (int i = 0; i < obj.length; i++) {
            if (!obj[i].equals(obj2[i])) {
                System.out.println("Test " + (total_tests) + " Failed. \n"
                        + "\tExpected: " + obj2 + " Actual: " + obj +
                        "\n\t Even more inaccuracies could possibly exist" +
                        " past this point. ");
                return; }
        }
        System.out.println("Test " + (total_tests) + " Passed. ");
        passed_tests++;
    }

    /**
     * Generates some buildings for the building controller to play with
     */
    public static void test_buildinglist () {
    	//testbuildinglist = new Building[3];
    	testbuildinglist = new ArrayList<Building>();
    	Building build = new Building(null, null, false);
    	
    	
    	//Floors for Middlesex College, MC has 5 floors
    	Floor MCFloors[] = {
    			new Floor("Level 1","MC/MC-LEVEL1.png","MC-Accessibility/MC-Accessibility-1.png",null,"Middlesex College"),
    			new Floor("Level 2","MC/MC-LEVEL2.png","MC-Accessibility/MC-Accessibility-2.png",null,"Middlesex College"),
    			new Floor("Level 3","MC/MC-LEVEL3.png","MC-Accessibility/MC-Accessibility-3.png",null,"Middlesex College"),
    			new Floor("Level 4","MC/MC-LEVEL4.png","MC-Accessibility/MC-Accessibility-4.png",null,"Middlesex College"),
    			new Floor("Level 5","MC/MC-LEVEL5.png","MC-Accessibility/MC-Accessibility-5.png",null,"Middlesex College")};
    	build = new Building("Middlesex College", MCFloors, true);
    	
    	testbuildinglist.add(build);
    	
    	
    	//Floors for Visual Art Centre, VAC has 3 floors
    	Floor VACFloors[] = {
    			new Floor("Level 1","VAC/VAC-LEVEL1.png","VAC-Accessibility/VAC-Accessibility-1.png",null,"Visual Arts Centre"), 
    			new Floor("Level 2","VAC/VAC-LEVEL2.png","VAC-Accessibility/VAC-Accessibility-2.png",null,"Visual Arts Centre"),
    			new Floor("Level 3","VAC/VAC-LEVEL3.png","VAC-Accessibility/VAC-Accessibility-3.png",null,"Visual Arts Centre")};
    	build = new Building("Visual Arts Centre", VACFloors, true);
    	
    	testbuildinglist.add(build);
    	
    	
    	//Floors for Westminster Hall, WH has 4 floors
    	Floor WHFloors[] = {
    			new Floor("Level 1","WH/WH-LEVEL1.png","WH-Accessibility/WH-Accessibility-1.png",null,"Westminster Hall"), 
    			new Floor("Level 2","WH/WH-LEVEL2.png","WH-Accessibility/WH-Accessibility-2.png",null,"Westminster Hall"), 
    			new Floor("Level 3","WH/WH-LEVEL3.png","WH-Accessibility/WH-Accessibility-3.png",null,"Westminster Hall"), 
    			new Floor("Level 4","WH/WH-LEVEL4.png","WH-Accessibility/WH-Accessibility-4.png",null,"Westminster Hall")};
    	build = new Building("Westminster Hall", WHFloors, true);
    	
    	testbuildinglist.add(build);
    	

		Gson gson = new Gson();
    	String currentPath = System.getProperty("user.dir");		
		currentPath = currentPath + "/" + "MapData" + ".json";
		System.out.println(currentPath);
		
		//for (int i = 0; i < testbuildinglist.length; i++) {
			try (FileWriter writer = new FileWriter(currentPath)) {
				//System.out.println(testbuildinglist[i].getName());
				gson.toJson(testbuildinglist, writer);
				System.out.println(testbuildinglist);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		//}*/
		
    }
    
    public static void test_main (String[] args) {
        /** Testing Building class */
        Floor fs[] = {};
        Building b = new Building("Sample Building",
                fs, true);
        // 1: Testing getName
        test_equality(b.getName(), "Sample Building");
        // 2: Testing setName
        b.setName("New Name");
        test_equality(b.getName(), "New Name");
        // 3: Testing getName AND setName together
        b.setName("Building Two!");
        test_equality(b.getName(), "Building Two!");
            // Creating new Floor and POI object
        int[] loc = {3, 4};
        POI p[] = {};
        Floor f = new Floor("Floor X", "Image link here",
                "", p, "");
        POI newBIP = new POI("Point X", "Sample POI", "X", loc, f);
        POI ppp[] = {newBIP};
        f.setBuiltInPOIs(ppp);


        // 4: Testing addFloor
        b.addFloor(f);

        Floor[] test_point_1 = {f};
        array_equality(b.floors, test_point_1);
        // 5: Testing getFloor
        array_equality(b.getFloors(), test_point_1);
        // 6: Testing hasPOI

        test_equality(b.hasPOI("Point X"), true);

        test_equality(b.hasPOI("Non-Existent POI"), false);
        // 7: Testing setBuiltInPOI
        b.setBuiltInPOI(true);
        test_equality(b.builtInPOI, true);
        b.setBuiltInPOI(false);
        test_equality(b.builtInPOI, false);
    }
   
    public static void main(String[] args) {
		/* To test: 
		 * - Create a developer  - COMPLETE 
		 * - Add/Remove building(s)  - COMPLETE 
		 * - Add/Remove floor(s) from buildings  - COMPLETE 
		 * - Add/Remove/Edit POI(s) from a Floor 
		 */
		Developer dev = new Developer(); 
		Floor[] ls = new Floor[] {}; 
		Building b1 = new Building("b1", ls, true); 
		Building b2 = new Building("b2", ls, false); 
		Building b3 = new Building("b1", ls, true); 
		
		// Buildings...
		dev.addBuilding(b1); 
		dev.addBuilding(b2); 
		dev.addBuilding(b3); 
		
		Building[] testList = {b1, b2, b3}; 
		for (int i = 0; i < testList.length; i++) {
			if (!testList[i].equals(Main.buildingList.get(i))) {
				System.out.println("Test 1 failed. ");
			}
		}
		System.out.println("Test 1 Passed. ");
		
		dev.removeBuilding(b2); 
		testList = new Building[] {b1, b3}; 
		for (int i = 0; i < testList.length; i++) {
			if (!testList[i].equals(Main.buildingList.get(i))) {
				System.out.println("Test 2 failed. ");
			}
		}
		System.out.println("Test 2 Passed. "); 

		// Floors... 
		Floor f1 = new Floor(); 
			f1.setName("f1"); 
			/*POI[] ppp = new POI[] {null}; 
			f1.setBuiltInPOIs(ppp); */
			f1.setBuilding(b1.getName());
			
		Floor f2 = new Floor(); 
			f2.setName("f2"); 
			f2.setBuilding(b1.getName());
		Floor f3 = new Floor(); 
			f3.setName("f3"); 
			f3.setBuilding(b1.getName());
		

		dev.addFloor(f1); 
		
		dev.addFloor(f2); 
		dev.addFloor(f3);

		
		
		
		Floor[] testFloorList = new Floor[] {f3, f2, f1}; 
		
		for (int i = 0; i < b1.getFloors().length; i++) {
			if (b1.getFloors()[i].getName().equals(testFloorList[i].getName()));
			else 	System.out.println("Test 3 failed. ");
		}
		System.out.println("Test 3 passed. ");

		System.out.println(Main.buildingList.get(0).getFloors()[0].getName()); 
		System.out.println(Main.buildingList.get(0).getFloors()[0]); 
		System.out.println(f2.getBuilding());
		
		dev.removeFloor(f2);
		dev.removeFloor(f3); 
		testFloorList = new Floor[] {f1}; 
		for (int i = 0; i < b1.getFloors().length; i++) {
			if (b1.getFloors()[i].getName().equals(testFloorList[i].getName())) 
				;
			else System.out.println("4 Failed. ");
		}
		System.out.println("Test 4 passed. ");
		
		Floor f4 = new Floor(); f4.setName("f4");
		Floor f5 = new Floor(); f5.setName("f5"); 
		
		// POIs...
		int x = dev.getIndex(b1); 
		Building b = Main.buildingList.get(x); 
		
		b.addFloor(f4);
		b.addFloor(f5);
		// NOW, b1 contains the floors {f1, f4, f5}
		/*
				for (int i = 0; i < b.getFloors().length; i++) {
					System.out.println(b.getFloors()[i].getName());
				} */
			POI p1 = new POI(); 
			p1.setName("POI ONE"); 
			POI p2 = new POI(); 
			p2.setName("POI TWO"); 
			POI px = new POI(); 
			px.setName("POI x"); 
		
			Floor fx = b.getFloors()[0]; 
			fx.setBuilding("b1");

			
		dev.addPOI(p1, fx);
		dev.addPOI(p2, fx); 
		dev.addPOI(px, fx); 
		
		POI[] testPoiList = new POI[] {px, p2, p1}; 
		
		for (int i = 0; i < b.getFloors()[0].getBuiltInPOIs().length; i++) {
			if (b.getFloors()[0].getBuiltInPOIs()[i] == null) {break; }
			if (b.getFloors()[0].getBuiltInPOIs()[i].equals(testPoiList[i])); 
			else {System.out.println("Test 5 failed. "); break; }
		}
		System.out.println("Test 5 PASSED. ");
		
		
		// Remove a POI; 
		dev.removePOI(px, fx); 
		testPoiList = new POI[] {p2, p1}; 
		for (int i = 0; i < fx.getBuiltInPOIs().length; i++) {
			if (fx.getBuiltInPOIs()[i] == null) break; 
			if (!fx.getBuiltInPOIs()[i].getName().equals(testPoiList[i])) 
				; 
			else System.out.println("Test 6 Failed. ");
		}
		System.out.println("Test 6 Passed. ");
		
		// edit a POI: 
/*
 * (POI targetPOI, String newName, 
			String newDescription, String newType, int[] newLocation)
 */
		p1.setFloor(fx); 
		dev.editPOI(p1, "new name for P1", "", "", new int[] {0, 0}); 
		if ("new name for P1".equals(fx.getBuiltInPOIs()[1].getName()))
			System.out.println("Test 7 Passed.");
		else System.out.println("Test 7 Failed. ");
		
		
    }
   
    
    
}