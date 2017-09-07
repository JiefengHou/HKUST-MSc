package hms.main;
import hms.config.HotelConfig;
import hms.config.HotelConfigException;
import hms.gui.UI;
import hms.model.Occupant;
import hms.model.Occupation;
import hms.model.Room;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * The Class HotelManager.
 */
public class HotelManager 
{
	
	public Room[][] rooms;
	
	public Hotel hotel; 
	
	public UI ui;
	
	public Random generator = new Random(System.currentTimeMillis());		

	/**
	 * Instantiates a new hotel manager.
	 */
	public HotelManager() {
		//hotel room information is stored in a hidden file
		String configFileName = "5100Hotel.xml";
		HotelConfig config = null;
		try {
			config = new HotelConfig(configFileName);
		}
		catch (HotelConfigException e) {
			e.printStackTrace();
		}

		// Construct a hotel
		int noOfFloors = config.getNumFloors();
		hotel = new Hotel(config.getName(), noOfFloors, config.getDataServiceChargePerDay());

		// Construct the rooms
		rooms = new Room[noOfFloors][];

		for (int i=1; i<=noOfFloors; i++) {
			int noOfRoomsInFloor = config.getNumRoomsInFloor(i);
			rooms[i-1] = new Room[noOfRoomsInFloor];
			for (int j = 1; j<=noOfRoomsInFloor; j++) {
				Room room = new Room(i, j, config.getRoomCapacity(i,j), config.getRoomType(i,j), config.getRoomRate(i,j));
				rooms[i-1][j-1] = room;
			}
		}

		ui = new UI(this);
	}

	// Listing methods
	/**
	 * Gets the hotel name.
	 * @return the hotel name
	 */
	public String getHotelName() {
		return hotel.getName();
	}

	/**
	 * List all occupied rooms.
	 * @return the array list of occupied rooms
	 */
	public ArrayList listAllOccupiedRooms() {
		ArrayList result = new ArrayList();

		for (int i=0; i<rooms.length; i++) {
			int upper = rooms[i].length;
			for (int j=0; j<upper; j++) {
				if (!rooms[i][j].isAvailable()) {
					result.add(rooms[i][j]);
				}
			}
		}
		return result;
	}

	/**
	 * List all available rooms.
	 * @return the array list of available rooms
	 */
	public ArrayList listAllAvailableRooms() {
		ArrayList result = new ArrayList();

		for (int i=0; i<rooms.length; i++) {

			int upper = rooms[i].length;
			for (int j=0; j<upper; j++) {
				if (rooms[i][j].isAvailable()) {
					result.add(rooms[i][j]);
				}
			}
		}
		return result;
	}

// Check in, check out
/**
 * Check in function.
 * @param ID the customer id
 * @param name the customer name
 * @param type the room type
 * @param company the customer's company
 * @param checkInDate the check in date
 * @param dataServiceRequired the boolean indicate whether data service is required
 * @param ethernetAddress the ethernet address
 * @param room the room
 * @return check-in results
 */
public String checkIn(String ID, String name, String type, String company, 
					Date checkInDate, boolean dataServiceRequired, String ethernetAddress, Room room) {
		if (ID == null || name == null || type == null || company == null || checkInDate == null || ethernetAddress == null || room == null) {
			return "No input can be null";
		}
		else if (ID.equals("") || name.equals("") || company.equals("")) {
			return "ID, name, and company cannot be empty";
		} 
		else if (room == null) {
			return "No room is selected";
		}
		else if (!type.equals("Standard") && !type.equals("Business")) {
			return "Invalid type";
		}
		else if (room.getType() == 1 && dataServiceRequired == true) {
			return "No data service for standard rooms";
		}
		else if (type.equals("Standard") && dataServiceRequired == true) {
			return "No data service for standard occupants";
		}
		else if (!ID.matches("[a-zA-Z][0-9]{8}")) {
			return "The format of the inputted ID is invalid";
		}
		else if (dataServiceRequired && !ethernetAddress.matches("([0-9a-f]{2}:){5}[0-9a-f]{2}")) {
			return "The format of the inputted ethernetAddress is invalid";
		}
		
		if (dataServiceRequired == false) {
			ethernetAddress = "";
		}
		room.setOccupation(new Occupation(checkInDate, dataServiceRequired, ethernetAddress, 
							   new Occupant(ID, type, name, company)));

		return "Success";
	}

	/**
	 * Check out function.
	 * @param checkOutDate the check-out date
	 * @param room the room
	 * @return check-out result
	 */
	public String checkOut(Date checkOutDate, Room room) {
		if (checkOutDate == null || room == null) {
			return "No input can be null";
		}

		Occupation occupation = room.getOccupation();

		if (occupation == null) {
			return "The room has no occupant";
		} else if (!(occupation.getCheckInDate().compareTo(checkOutDate) < 0)) {
			return "Check-out date must be after check-in date";
		}

		room.setOccupation(null);
		return "Success";
	}

	// Searching methods
	/**
	 * Find occupant.
	 *
	 * @param type the search field type
	 * @param value the search target value
	 * @return the array list of search results
	 */
	public ArrayList findOccupant(String type, String value) {
		ArrayList result = new ArrayList();

		for (int i=0; i<rooms.length; i++) {
			int upper = rooms[i].length;
			for (int j=0; j<upper; j++) {
				if (!rooms[i][j].isAvailable()) {
					Occupant o = rooms[i][j].getOccupation().getOccupant();
					if ((type.equals("ID") && o.getID().matches(value)) ||
					   (type.equals("Type") && o.getType().matches(value)) || 
					   (type.equals("Company") && o.getCompany().matches(value)) ||
					   (type.equals("Name") && o.getName().matches(value))) {
						result.add(rooms[i][j]);
					}
				}
			}
		}
		return result;
	}

	/**
	 * Gets the room.
	 * @param floor the floor number
	 * @param num the room number
	 * @return the room
	 */
	public Room getRoom(int floor, int num) {
		return rooms[floor][num];
	}
	
	/**
	 * Update room rate.
	 * @param room the room
	 * @param newRate the new rate
	 * @return true, if successful
	 */
	public boolean updateRoomRate(Room room, double newRate) {
		room.setRate(newRate);
		return true;
	}

	/**
	 * Generate a valid id example.
	 * @return a valid ID
	 */
	public String generateID() {
		char c = (char)(Math.abs(generator.nextInt()%26) + 65);
		long i = Math.abs(generator.nextLong()%100000000);
		return "" + c + new DecimalFormat("00000000").format(i);
	}
	
	public UI getUI(){
		return this.ui;
	}
}
