
package immaculatemap;

import javafx.scene.control.Button;

public class FloorButton extends Button {

	private Floor floor;
	
	/* inherited constructor; not useful
	 * public FloorButton() {
		// TODO Auto-generated constructor stub
	}*/

	/**
	 * Constructor for a button, determining its text label and assigning its floor.
	 * @param arg0
	 * @param floor
	 */
	public FloorButton(String text, Floor floor) {
		super(text);
		this.floor = floor;
	}

	/* inherited constructor; not useful
	 * public FloorButton(String arg0, Node arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}*/

	/**
	 * Returns the button's assigned floor.
	 * @return
	 */
	public Floor getFloor() {
		return this.floor;
	}
	
	/**
	 * Assigns a new floor to the button.
	 * Probably unnecessary.
	 * @param newFloor
	 */
	public void setFloor(Floor newFloor) {
		this.floor = newFloor;
	}
}

