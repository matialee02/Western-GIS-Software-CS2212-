// final edit dec 2 1418hrs
// - Matia
package immaculatemap;

/**
 * Defines a Point of Interest
 * <b>Example use: </b>
 *     <pre>
 *         {@code
 *              POI favorite_room = new POI("Room A", "My favorite classroom",
 *              "Classroom", sample_loc, sample_floor)
 *         }
 *     </pre>
 *
 * @version 1.0.0
 * @author Matia Lee (mlee927@uwo.ca)
 */



public class POI {
    /** Name, description, type of POI */
    String name, description, type;
    /** Location of POI, represented as coordinates */
    int[] location;
    /** The floor the POI belongs in */
    Floor floor;

    /**
     * POI Constructor. Creates a POI object.
     *
     * @param name Name of the POI
     * @param description Description of POI
     * @param type Type of POI
     * @param location Integer coordinate of the POI
     * @param floor The floor the POI belongs in
     * */
    public POI(String name, String description, String type,
               int[] location, Floor floor) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.location = location;
        this.floor = floor;
    }
    
    public POI() {
    	name = ""; 
    	description = "";
    	type = ""; 
    	location = new int[] {0,0}; 
    	Floor f = new Floor(); 
    	floor = f; 
    }

    // Basic getters and setters
    public String getName() {return name; }
    public void setName(String POIName) {name = POIName; }
    public String getDescription() {return description; }
    public void setDescription(String description) {this.description = description; }
    public int[] getLocation() {return location; }
    public void setLocation(int[] newLocation) {location = newLocation; }
    public Floor getFloor() {return floor; }
    public void setFloor(Floor newFloor) {floor = newFloor; }
    public String getType() {return type; }
    public void setType(String type) {this.type = type; }
}
