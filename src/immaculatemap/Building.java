// final edit dec 2 1418hrs
// - Matia
package immaculatemap;

/**
 * Defines a Building
 * <b>Example use: </b>
 *     <pre>
 *         {@code
 *              POI Middlesex = new Building("Middlesex College", mc_floors, true)
 *         }
 *     </pre>
 *
 * @version 1.0.0
 * @author Matia Lee (mlee927@uwo.ca)
 */

import java.util.ArrayList;

public class Building {
    /** The building's name */
    String name;
    /** A list of floors that belong to the building */
    Floor[] floors;
    /** Whether the building has built-in POIs */
    boolean builtInPOI;

    /**
     * Building constructor. Creates a new "building".
     *
     * @param name The building's name
     * @param floors A list of floors that belong in the building
     * @param builtInPOI Indicates whether the building will have built-in POIs
     * */
    public Building(String name, Floor[] floors, boolean builtInPOI) {
        this.name = name;
        this.floors = floors;
        this.builtInPOI = builtInPOI;
    }

    public class BuildingWrapper {
    	ArrayList<Building> buildings;
    }
    
    // Basic getters and setters
    public void setName(String buildingName) {name = buildingName; }
    public String getName() {return name; }
    public Floor[] getFloors() {return floors; }
    public void setBuiltInPOI(boolean hasPOIs) {builtInPOI = hasPOIs; }

    /**
     * Appends a Floor onto the list of floors as it exits as the class attribute
     *
     * @param newFloor A "floor" variable to be added to "floors"
     * @return Nothing
     * */
    public void addFloor(Floor newFloor) {
        int len = floors.length;
        Floor[] newList = new Floor[len + 1]; // Create a new array of "Floor" with +1 size
        newList[0] = newFloor; 
        for (int i = 1; i < newList.length; i++) {newList[i] = floors[i-1]; } // Inserting all elements of "floors" into "newList"
         floors = newList; // "floors" is updated with "newList"
        System.out.println("List of floors successfully updated. "); // Prints result status to console
    }

    /**
     * Searches through the list of floors, and returns a boolean value indicating whether a POI exists in any of the
     * floors the list or not.
     *
     * @param POIName Name of the POI to be sought
     *
     * @return Whether the POI being sought exists in the list of POIs.
     * */
    public boolean hasPOI(String POIName) {
        for (int i = 0; i < floors.length; i++) {
            // Immediately returns true if POIName is an element of "floors"
            if (floors[i].hasBuiltInPOI(POIName)) {return true; }
        }
        return false; // Returns false if POIName is not found in "floors"
    }
}
