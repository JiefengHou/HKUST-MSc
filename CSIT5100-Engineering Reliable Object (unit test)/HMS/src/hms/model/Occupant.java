package hms.model;
/**
 * The Class Occupant models a customer who occupies a hotel room.
 */
public class Occupant {
	
	public String ID;
	
	public String type;
	
	public String name;
	
	public String company;

	/**
	 * Instantiates a new occupant.
	 * @param ID the customer id
	 * @param type the room type
	 * @param name the customer name
	 * @param company the customer's company
	 */
	public Occupant(String ID, String type, String name, String company) {
		this.ID = ID;
		this.type = type;
		this.name = name;
		this.company = company;
	}

	/**
	 * Gets the customer id.
	 * @return the id
	 */
	public String getID() {
		return ID;
	}

	/**
	 * Gets the room type.
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Gets the customer's company.
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * Gets the customer's name.
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public String toString() {
		return "Name: " + name + ", Member ID: " + ID + ", Company: " + company;
	}
}
