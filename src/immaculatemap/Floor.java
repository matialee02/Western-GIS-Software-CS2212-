// final edit dec 2 1418hrs
// - Matia
package immaculatemap;
/**
 * This is an entity storing information about a building's floor.
 * 
 * @author Connor McGoey
 *
 */
public class Floor {
	/** The Floor's name*/
	private String name;
	/** The Floor's map's document path*/
	private String mapImage;
	/** The Floor's accessibility layer's document path*/
	private String accessibilityLayer;
	/** The Floor's built in Points of Interest @see POI*/
	private POI[] builtInPOIs;
	/** The building the Floor belongs to @see Building*/
	private String building;
	
	/**
	 * Empty constructor to create a Floor with default values.
	 * 
	 */
	public Floor() {
		this.name = null;
		this.mapImage = null;
		this.accessibilityLayer = null;
		this.builtInPOIs = new POI[5];
		this.building = null;
	}
	
	/**
	 * Constructor for creating a Floor with specified parameters
	 * 
	 * @param name the name of the Floor to be created
	 * @param mapImage the Floor map image file destination of the Floor to be created
	 * @param accessibilityLayer the Floor accessibility map image file destination of the Floor to be created
	 * @param builtInPOIs the POI array of built in POIs
	 * @param building the name of the building the Floor belongs to
	 */
	public Floor(String name, String mapImage, String accessibilityLayer, POI[] builtInPOIs, String building) {
		this.name = name;
		this.mapImage = mapImage;
		this.accessibilityLayer = accessibilityLayer;
		this.builtInPOIs = builtInPOIs;
		this.building = building;
	}
	
	/**
	 * Setter method to set the name of the Floor
	 * 
	 * @param name new name of the Floor
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Getter method to get the current name of the Floor
	 * 
	 * @return the name of the Floor
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Setter method to set the file path of the Floor's map image
	 * 
	 * @param filePath the string file path of the Floor's map image
	 */
	public void setMapImage(String filePath) {
		this.mapImage = filePath;
	}
	
	/**
	 * Getter method to get the Floor's current map image file path
	 * 
	 * @return the Floor's map image file path
	 */
	public String getMapImage() {
		return this.mapImage;
	}
	
	/**
	 * Setter method to set the Floor's accessibility Image file path
	 * 
	 * @param accessibilityPOIs the file path of the accessibility layer's image
	 */
	public void setAccessibilityLayer(String accessibilityPOIs) {
		this.accessibilityLayer = accessibilityPOIs;
	}
	
	/**
	 * Getter method to get the file path of the accessibility layer image
	 * 
	 * @return the string representation of the accessibility layer image file path
	 */
	public String getAccessibilityLayer() {
		return this.accessibilityLayer;
	}
	
	/**
	 * Setter method to set the built in POI array
	 * 
	 * @param builtInPOIs array of POIs to be made the Floor's built in POI array
	 */
	public void setBuiltInPOIs(POI[] builtInPOIs) {
		this.builtInPOIs = builtInPOIs;
	}
	
	/**
	 * Getter method to get the array of built in POIs
	 * 
	 * @return the array of built in POIs
	 */
	public POI[] getBuiltInPOIs() {
		return this.builtInPOIs;
	}
	
	/**
	 * Helper function to determine if the Floor has a built in POI with the given name
	 * 
	 * @param POIName name of the POI to be searched
	 * @return true if the Floor has a built in POI by that name, false otherwise
	 */
	public Boolean hasBuiltInPOI(String POIName) {
		for (int i = 0; i < builtInPOIs.length; i++) {
			if ((builtInPOIs[i] != null) && (builtInPOIs[i].name.equals(POIName))) return true;
		}
		return false;
	}
	
	/**
	 * Helper method to determine if two floors are equal
	 * 
	 * @return true if the two floors are equal and false if they are not
	 */
	public boolean equals(Object obj) {
		Floor other = (Floor) obj;
		if (this.building.equals(other.getBuilding()) && this.name.equals(other.getName())) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Setter method to set the name of the building the Floor belongs to
	 * 
	 * @param buildingObject
	 */
	public void setBuilding(String buildingObject) {
		this.building = buildingObject;
	}
	
	/**
	 * Getter method to get the name of the building the Floor belongs to
	 * 
	 * @return the name of the building the Floor belongs to
	 */
	public String getBuilding() {
		return building; 
	}
	
}
