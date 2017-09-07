package hms.model;
import java.text.*;

/**
 * The Class Room.
 */
public class Room {
	
	public int floorNo;
	
	public int roomNo;
	
	public int capacity;
	
	public short type;
	
	public double rate;
	
	public Occupation occupation;
	
	/** The Constant STANDARD room type. */
	public static final short STANDARD = 1;
	
	/** The Constant EXECUTIVE room type. */
	public static final short EXECUTIVE = 2;
	
	/** The Constant PRESIDENTIAL room type. */
	public static final short PRESIDENTIAL = 3;

	/**
	 * Instantiates a new room.
	 * @param floorNo the floor number
	 * @param roomNo the room number
	 * @param capacity the room capacity
	 * @param type the room type
	 * @param rate the room rate
	 */
	public Room(int floorNo, int roomNo, int capacity, short type, double rate) {
		this.floorNo = floorNo;
		this.roomNo = roomNo;
		this.capacity = capacity;
		this.type = type;
		this.rate = rate;
	}

	/**
	 * Gets the floor number.
	 * @return the floor number
	 */
	public int getFloorNo() {
		return floorNo;
	}

	/**
	 * Gets the room number.
	 * @return the room no
	 */
	public int getRoomNo() {
		return roomNo;
	}

	/**
	 * Gets the room type string.
	 * @return the type string
	 */
	public String getTypeString() {
		String typeString = null;
		if (type == STANDARD) {
			typeString = "Standard";
		} else if (type == EXECUTIVE) {
			typeString = "Executive";
		} else if (type == PRESIDENTIAL) {
			typeString = "Presidential";
		}
		return typeString;
	}

	/**
	 * Gets the room capacity.
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Gets the room type.
	 * @return the type
	 */
	public short getType() {
		return type;
	}

	/**
	 * Gets the room rate.
	 * @return the rate
	 */
	public double getRate() {
		return rate;
	}
	
	/**
	 * Sets the room rate.
	 * @param newRate the new rate
	 */
	public void setRate(double newRate) {
		rate = newRate;
	}

	/**
	 * Sets the room occupation.
	 * @param occupation the new occupation
	 */
	public void setOccupation(Occupation occupation) {
		this.occupation = occupation;
	}

	/**
	 * Gets the room occupation.
	 * @return the occupation
	 */
	public Occupation getOccupation() {
		return occupation;
	}

	/**
	 * Checks if the room is available.
	 * @return true, if available
	 */
	public boolean isAvailable() {
		return occupation == null;
	}

	public String toString() {
		String dataServiceInfo = "";
		String occupantInfo = "None";

		Occupant occupant = null;
		if (occupation != null) {
			occupant = occupation.getOccupant();

			if (type != STANDARD && occupation.isDataServiceRequired()) {
				dataServiceInfo += " Data service: In used (Ethernet address: " 
						+ occupation.getEthernetAddress() + ")";
			}

			occupantInfo = "<" + occupant +  ">" + " Date of checking in: " +
						new SimpleDateFormat("dd-MM-yyyy").format(occupation.getCheckInDate());
		} 
		else if (type != STANDARD) {
			dataServiceInfo += "\n Data service: Not in used";
		}

		return "Room No: " + floorNo + "-" + roomNo + ", Room Type: " + getTypeString() + 
			  ", Capacity: " + capacity + ", Rate: " + rate + 
			  dataServiceInfo +  ", Occupant: " + occupantInfo;
	}
}
