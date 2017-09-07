package hms.main;
/**
 * The Class Hotel.
 */
public class Hotel {
	
	public String name;
	
	public int numFloors;
	
	public double dataServiceCharge;

	/**
	 * Instantiates a new hotel.
	 * @param n the hotel name
	 * @param num the number of floors
	 * @param charge the data service charge rate
	 */
	public Hotel(String n, int num, double charge) {
		name = n;
		numFloors = num;
		dataServiceCharge = charge;
	}

	/**
	 * Gets the hotel name.
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * The main method.
	 */
	public static void main(String[] args) {
			new HotelManager();
	}
}