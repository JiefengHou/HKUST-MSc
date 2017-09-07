package hms.command;

import hms.gui.TableView;
import hms.main.HotelManager;
import hms.model.Room;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JPanel;

/**
 * The Class CheckOutCommand encapsulates the functions for checking-out purposes.
 */
public class CheckOutCommand implements Command 
{
	
	public HotelManager hotelManager;
	
	public String ID = "";
	
	public String name = "";
	
	public String type = "";
	
	public String company = "";
	
	public Date checkInDate = getCurrentDate();
	
	public Date checkOutDate = getCurrentDate();
	
	public boolean dataServiceRequired = false;
	
	public String ethernetAddress = "00:00:00:00:00:00";
	
	public Room selectedRoom;

	/**
	 * Instantiates a new check out command.
	 * @param hotelManager the hotel manager
	 */
	public CheckOutCommand(HotelManager hotelManager) {
		this.hotelManager = hotelManager;
	}

	/**
	 * Get the reference to the check-in panel.
	 */
	public JPanel getPanel(TableView view) {
		return view.checkOutPanel();
	}

	/**
	 * Gets the customer id.
	 * @return the id
	 */
	public String getID() { return ID; }
	
	/**
	 * Gets the customer name.
	 * @return the name
	 */
	public String getName() { return name; }
	
	/**
	 * Gets the room type.
	 * @return the type
	 */
	public String getType() { return type; }
	
	/**
	 * Gets the customer's company.
	 * @return the company
	 */
	public String getCompany() { return company; }
	
	/**
	 * Gets the check in date.
	 * @return the check in date
	 */
	public Date getCheckInDate() { return checkInDate; }
	
	/**
	 * Gets the check out date.
	 * @return the check out date
	 */
	public Date getCheckOutDate() { return checkOutDate; }
	
	/**
	 * Checks if data service is required.
	 * @return true, if data service is required
	 */
	public boolean isDataServiceRequired() { return dataServiceRequired; }
	
	/**
	 * Gets the ethernet address.
	 * @return the ethernet address
	 */
	public String getEthernetAddress() { return ethernetAddress; }
	
	/**
	 * Gets the selected room.
	 * @return the selected room
	 */
	public Room getSelectedRoom() { return selectedRoom; }

	/**
	 * Sets the customer id.
	 * @param ID the new id
	 */
	public void setID(String ID) { this.ID = ID; }
	
	/**
	 * Sets the customer name.
	 * @param name the new name
	 */
	public void setName(String name) { this.name = name; }
	
	/**
	 * Sets the room type.
	 * @param type the new type
	 */
	public void setType(String type) { this.type = type; }
	
	/**
	 * Sets the customer's company.
	 * @param company the new company
	 */
	public void setCompany(String company) { this.company = company; }
	
	/**
	 * Sets the check in date.
	 * @param checkInDate the new check in date
	 */
	public void setCheckInDate(Date checkInDate) { this.checkInDate = checkInDate; }
	
	/**
	 * Sets the check out date.
	 * @param checkOutDate the new check out date
	 */
	public void setCheckOutDate(Date checkOutDate) { this.checkOutDate = checkOutDate; }
	
	/**
	 * Sets the data service required.
	 * @param dataServiceRequired the boolean value indicating whether data service is required
	 */
	public void setDataServiceRequired(boolean dataServiceRequired) { this.dataServiceRequired = dataServiceRequired; }
	
	/**
	 * Sets the ethernet address.
	 * @param ethernetAddress the new ethernet address
	 */
	public void setEthernetAddress(String ethernetAddress) { this.ethernetAddress = ethernetAddress; }
	
	/**
	 * Sets the selected room.
	 * @param selectedRoom the new selected room
	 */
	public void setSelectedRoom(Room selectedRoom) { this.selectedRoom = selectedRoom; }

	/**
	 * Check out.
	 * @return the checking-out result
	 */
	public String checkOut() {
		String result = hotelManager.checkOut(checkOutDate, selectedRoom);	
		if (result.equals("Success")) {
			clear();
		}
		return result;
	}

	/**
	 * Clear the checking-out data.
	 */
	public void clear() {
		ID = name = type = company = "";
		checkInDate = checkOutDate = getCurrentDate();
		dataServiceRequired = false;
		ethernetAddress = "00:00:00:00:00:00";
		selectedRoom = null;
	}

	/**
	 * Gets the current date.
	 * @return the current date
	 */
	public Date getCurrentDate() {
		try
		{
			return new SimpleDateFormat("dd-MM-yyyy").parse(new SimpleDateFormat("dd-MM-yyyy").format(new GregorianCalendar().getTime()));	
		}
		catch (ParseException e)
		{ // Never happens
			return null;
		}
	}
}
