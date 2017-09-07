package hms.model;
import java.util.*;

/**
 * The Class Occupation records necessary check-in information.
 */
public class Occupation 
{
	
	public Date checkInDate;
	
	public boolean dataServiceRequired;
	
	public String ethernetAddress;
	
	public Occupant occupant;

	/**
	 * Instantiates a new occupation.
	 * @param checkInDate the check in date
	 * @param dataServiceRequired whether data service is required
	 * @param ethernetAddress the ethernet address
	 * @param occupant the occupant
	 */
	public Occupation(Date checkInDate, boolean dataServiceRequired, String ethernetAddress, Occupant occupant) {
		this.checkInDate = checkInDate;
		this.dataServiceRequired = dataServiceRequired;
		this.ethernetAddress = ethernetAddress;
		this.occupant = occupant;
	}

	/**
	 * Gets the occupant.
	 * @return the occupant
	 */
	public Occupant getOccupant() {
		return occupant;
	}

	/**
	 * Gets the check in date.
	 * @return the check in date
	 */
	public Date getCheckInDate() {
		return checkInDate;
	}

	/**
	 * Checks if data service is required.
	 * @return true, if data service is required
	 */
	public boolean isDataServiceRequired() {
		return dataServiceRequired;
	}

	/**
	 * Gets the ethernet address.
	 * @return the ethernet address
	 */
	public String getEthernetAddress() {
		return ethernetAddress;
	}
}
