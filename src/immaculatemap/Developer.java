// final edit dec 2 1418hrs
// - Matia

/** 
 * @author Matia Lee (mlee927@uwo.ca)
 * November 2022 
 * Developer Class
 */

package immaculatemap;

import com.amazonaws.services.dynamodbv2.model.PointInTimeRecoveryDescription;

public class Developer extends User {
	// same attributes as User object
	public Developer() {
		super(false, "Admin", "DearImmaculateProfessor"); //Only one developer account
	}

	// Helper - Find the index of a target building in the my_Buildings list.
	public int getIndex(Building target) {
		for (int i = 0; i < Main.buildingList.size(); i++) {
			if (Main.buildingList.get(i).getName().equals(target.getName())) {
				return i;
			}
		}
		return -1;
	}
	
	/** Helper for retrieving building from a source floor.
	 * 
	 * Storing direct references to buildings from floors was problematic for the save
	 * file format, so a string name had to be used instead. This is the fallout.
	 * 
	 * @param source
	 * @return The building source belongs to
	 */
	private Building getParentBuilding(Floor source) { 
		for (int i = 0; i < Main.buildingList.size(); i++) {
			if (Main.buildingList.get(i).name.equals(source.getBuilding())) {
				return Main.buildingList.get(i);
			}
		}
		return null;
	}

	// Buildings - Add, Edit, Remove
	public void addBuilding(Building newBuilding) {
		// Main.my_buildings[2] = null;
		Main.buildingList.add(newBuilding);

		Main.updateGson(); 
	
	}

	// Remove a Building
	public void removeBuilding(Building target) {
		int loc = getIndex(target);
		if (loc == -1)
			return;
		Main.buildingList.remove(loc);
		Main.updateGson(); 
		
	}

	// Remove a floor from a building.
	public void removeFloor(Floor target) {
		
		if (target.getBuilding() == null) {
			System.out.println("Target must belong to a building. ");
			return; 
		}
		
		Building build = getParentBuilding(target);

		int loc = getIndex(build);
		Floor[] floors = Main.buildingList.get(loc).getFloors(); // floors now contains the
		// address of Main.my_buildings.get(loc).getFloors();
		for (int i = 0; i < floors.length; i++) {
			if (floors[i].equals(target)) {
				Floor[] newList = new Floor[floors.length - 1];
				floors[i] = null;
				int j = 0;
				for (; j < i; j++) { // up to the point of deletion, copy each cell from
										// floors into newList
					newList[j] = floors[j];
				}
				for (j++ ; j < floors.length; j++) { // From the point of deletion,
					// insert the element of floors[x+1] into newList[x]
					newList[j - 1] = floors[j]; // MATIA - fixed small issue here
				}
				Main.buildingList.get(loc).floors = newList;
	
				return;
			}
		}
		Main.updateGson(); 
	}

	// Add a floor to a building.
	public void addFloor(Floor newFloor) {
		Building curr_Building = getParentBuilding(newFloor);
		curr_Building = Main.buildingList.get(getIndex(curr_Building)); // i know this looks redundant, 
																		// but its necessary
		Floor[] curr_floors_list = curr_Building.floors;
		Floor[] newList = new Floor[curr_floors_list.length + 1];
		
		newList[0] = newFloor; 
		for (int i = 0; i < curr_floors_list.length; i++) {
			newList[i + 1] = curr_floors_list[i]; 
		}
		
		Main.buildingList.get(getIndex(curr_Building)).floors = newList; 
		Main.updateGson(); 
	
		}


	// POI - add, edit, remove

	// Add a POI
	public void addPOI(POI newPOI, Floor target) {
		int x = 0; 
		String targetName = target.getName(); 
		Building curr_Building = getParentBuilding(target);
		for (; x < curr_Building.getFloors().length; x++) {
			if (curr_Building.getFloors()[x].getName().equals(targetName)) break; 
		} 
		if (x == curr_Building.getFloors().length) {
			System.out.println("Target floor not found. ");
			return ; 
		}
		Floor cl = curr_Building.getFloors()[x];
		POI[] p = cl.getBuiltInPOIs(); 
		POI[] newList = new POI[p.length + 1]; 
		newList[0] = newPOI; 
		for (int i = 0; i < p.length; i++) {
			newList[i + 1] = p[i];
		}
		
		cl.setBuiltInPOIs(newList);
		
		for(int i = 0; i < curr_Building.getFloors().length; i++) {

			for (int j = 0; j < 1;j++) {
if (curr_Building.getFloors()[i].getBuiltInPOIs()[j] == null) break; 
				String k = curr_Building.getFloors()[i].getBuiltInPOIs()[j].getName(); 
				System.out.println();
			}
		}

		
		
		
		
		return;
		
		//Main.saveBuildings();
	}
	
	public void addPOI(POI poi) {
		Floor floor = poi.getFloor(); 
		Building b = getParentBuilding(floor);
		int loc = getIndex(b); 
		Building curr = Main.buildingList.get(loc); 
		
		POI[] curr_poi_list = floor.getBuiltInPOIs(); 
		
		POI[] new_poi_list = new POI[curr_poi_list.length];
		new_poi_list[0] = poi; 
		for (int i = 0; i < curr_poi_list.length - 1; i++) {
			new_poi_list[i+1] = curr_poi_list[i]; 
		}
		
		
		int x = 0; 
		for (; x < curr.getFloors().length; x++) {
			if (curr.getFloors()[x].getName().equals(floor.getName())) 
				break;
		}
		curr.floors[x].setBuiltInPOIs(null); 

		curr.floors[x].setBuiltInPOIs(new_poi_list); 

Main.to_String(); 
	}

	// Remove a POI
	public void removePOI(POI targetPOI, Floor targetFloor) {
		int x = 0; 
		String targetName = targetFloor.getName(); 
		Building c = getParentBuilding(targetFloor);
		Building curr_Building = Main.buildingList.get(getIndex(c));
		for (; x < curr_Building.getFloors().length; x++) {
			if (curr_Building.getFloors()[x].getName().equals(targetName)) break; 
		} 
		if (x == curr_Building.getFloors().length) {
			System.out.println("Target floor not found. ");
			return ; 
		}
		Floor cl = curr_Building.getFloors()[x];
		POI[] p = cl.getBuiltInPOIs(); 
		POI[] newList = new POI[p.length - 1]; 
		
		int i = 0; 
		for (; !p[i].equals(targetPOI); i++) {
			if (i == p.length) {System.out.println("Target POI not found"); return; }
			newList[i] = p[i]; 
		}
		for (; i < newList.length; i++) {
			newList[i] = p[i + 1]; 
		}
		Main.buildingList.get(getIndex(c)).getFloors()[x].setBuiltInPOIs(newList);
	
	}

	// Edit a POI
	public void editPOI(POI targetPOI, String newName, 
			String newDescription, String newType, int[] newLocation) {
		if (targetPOI.getFloor() == null) System.out.println("POI must belong to a Floor. "); 
		Floor targetFloor = targetPOI.getFloor();
		
		int x = 0; 
		String targetName = targetFloor.getName(); 
		Building c = getParentBuilding(targetFloor);
		Building curr_Building = Main.buildingList.get(getIndex(c));
		for (; x < curr_Building.getFloors().length; x++) {
			if (curr_Building.getFloors()[x].getName().equals(targetName)) break; 
		} 
		if (x == curr_Building.getFloors().length) {
			System.out.println("Target floor not found. ");
			return ; 
		}
		Floor cl = curr_Building.getFloors()[x];
		POI[] pois = cl.getBuiltInPOIs(); 
		
		int target = 0;
		for (; target < pois.length; target++) {
			if (pois[target].equals(targetPOI)) {
				break;
			}
		}
		pois[target].setName(newName);
		pois[target].setDescription(newDescription);
		pois[target].setType(newType);
		pois[target].setLocation(newLocation);
	
	}

}