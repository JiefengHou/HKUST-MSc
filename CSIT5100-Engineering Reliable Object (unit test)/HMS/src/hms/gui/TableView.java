package hms.gui;

import hms.command.Command;
import hms.main.HotelManager;

import javax.swing.JPanel;

/**
 * The Class TableView is the main view of the HMS's GUI.
 */
public class TableView
{
	public HotelManager hotelManager;
	
	public Command currentCommand;

	/**
	 * Instantiates a new table view.
	 * @param hotelManager the hotel manager
	 */
	public TableView(HotelManager hotelManager) {
		this.hotelManager = hotelManager;
	}

	/**
	 * Accept a command.
	 * @param command the command
	 * @return the panel for each type of command
	 */
	public JPanel accept(Command command) {
		this.currentCommand = command;		
		return command.getPanel(this);
	}

	/**
	 * prepare check in panel.
	 * @return the panel
	 */
	public JPanel checkInPanel() {
		return new CheckInPanel(currentCommand, hotelManager);
	}

	/**
	 * prepare check out panel.
	 * @return the panel
	 */
	public JPanel checkOutPanel() {
		return new CheckOutPanel(currentCommand, hotelManager);
	}

	/**
	 * prepare search panel.
	 *
	 * @return the panel
	 */
	public JPanel searchPanel() {
		return new SearchPanel(currentCommand);
	}
	
	/**
	 * prepare manage room panel.
	 * @return the panel
	 */
	public JPanel manageRoomPanel() {
		return new ManageRoomPanel(currentCommand, hotelManager);
	}

	
}