package hms.command;

import hms.gui.TableView;
import hms.model.Room;

import javax.swing.JPanel;
/**
 * The Class ManageRoomCommand provides functions to manage room rate and discounts.
 */
public class ManageRoomCommand implements Command {
	
	
	public Room selectedRoom;
	
	/**
	 * get the manage room panel
	 */
	@Override
	public JPanel getPanel(TableView view) {
		return view.manageRoomPanel();
	}
	
	/**
	 * Sets the selected room.
	 * @param room the new selected room
	 */
	public void setSelectedRoom(Room room){
		selectedRoom = room;
	}
	
	/**
	 * Gets the selected room.
	 * @return the selected room
	 */
	public Room getSelectedRoom(){
		return this.selectedRoom;
	}
	
}
