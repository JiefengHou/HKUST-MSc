import static org.junit.Assert.*;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.*;
import org.junit.rules.ExpectedException;

import javax.swing.*;
import hms.command.*;
import hms.config.HotelConfig;
import hms.config.HotelConfigException;
import hms.gui.*;
import hms.main.*;
import hms.model.*;

public class HotelManagerTest {
	
	private HotelManager hotelManager;
	private UI gui;
	
	@Before
	public void setUp() throws Exception { 
		if(new File("Hotel.xml").getName() == "Hotel.xml") {
			new File("Hotel.xml").renameTo(new File("5100Hotel.xml"));
		}
		hotelManager = new HotelManager(); 
		gui = hotelManager.getUI(); 
		gui.setVisible(false); 
	}
	
	@After
	public void tearDown() throws Exception { 
		if(new File("Hotel.xml").getName() == "Hotel.xml") {
			new File("Hotel.xml").renameTo(new File("5100Hotel.xml"));
		}
		hotelManager = null; 
		gui = null;
	}
	
	@Test
	public void testHotelManager() {
		Assert.assertNotNull(hotelManager.hotel);
		Assert.assertNotNull(hotelManager.rooms);
		Assert.assertNotNull(hotelManager.ui);
	}
		
	@Test
	public void testGetHotelName() {
		Assert.assertEquals(hotelManager.hotel.getName(), 
				hotelManager.getHotelName());
	}

	@Test
	public void testListAllOccupiedRooms() {
		//no occupied room
		ArrayList result = new ArrayList();
		Assert.assertEquals(result, hotelManager.listAllOccupiedRooms());
		
		//one occupied room after check in
		Room room = hotelManager.rooms[0][0];
		Date checkInDate = new GregorianCalendar().getTime();
		hotelManager.checkIn("A12345678", "John", "Standard", "Google", checkInDate, 
				false, "00:00:00:00:00:00", room);
		result.add(room);
		Assert.assertEquals(result, hotelManager.listAllOccupiedRooms());
		
		//no occupied room after check out
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(checkInDate);
		calendar.add(calendar.DATE, 1);
		hotelManager.checkOut(calendar.getTime(), room);
		result.remove(room);
		Assert.assertEquals(result, hotelManager.listAllOccupiedRooms());
	}
	
	@Test
	public void testListAllAvailableRooms() {		
		//one occupied room after check in
		int roomNumBefore = hotelManager.listAllAvailableRooms().size();
		Room room = hotelManager.rooms[0][0];
		Date checkInDate = new GregorianCalendar().getTime();
		hotelManager.checkIn("A12345678", "John", "Standard", "Google", checkInDate, 
				false, "00:00:00:00:00:00", room);
		int roomNumafter = hotelManager.listAllAvailableRooms().size();
		Assert.assertEquals(roomNumBefore - 1, roomNumafter);
		
		//no occupied room after check out
		roomNumBefore = hotelManager.listAllAvailableRooms().size();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(checkInDate);
		calendar.add(calendar.DATE, 1);
		hotelManager.checkOut(calendar.getTime(), room);
		roomNumafter = hotelManager.listAllAvailableRooms().size();
		Assert.assertEquals(roomNumBefore + 1, roomNumafter);
	}
	
	@Test
	public void testCheckIn() {
		/*
		 * ID = null, name != null, type != null, company != null, 
		 * ethernetAddress != null, checkInDate != null, room != null
		 */
		Assert.assertEquals("No input can be null", hotelManager.checkIn(null, 
				"John", "Standard", "Google", new Date(), false, 
				"00:00:00:00:00:00", new Room((int) 6, (int) 1, (int) 2, 
						(short) 1, (double) 1000.0)));

		/*
		 * ID != null, name = null, type != null, company != null, 
		 * ethernetAddress != null, checkInDate != null, room != null
		 */
		Assert.assertEquals("No input can be null", hotelManager.checkIn("A12345678", 
				null, "Standard", "Google", new Date(), false, 
				"00:00:00:00:00:00", new Room((int) 6, (int) 1, (int) 2, 
						(short) 1, (double) 1000.0)));
	
		/*
		 * ID != null, name != null, type = null, company != null, 
		 * ethernetAddress != null, checkInDate != null, room != null
		 */
		Assert.assertEquals("No input can be null", hotelManager.checkIn("A12345678", 
				"John", null, "Google", new Date(), false, 
				"00:00:00:00:00:00", new Room((int) 6, (int) 1, (int) 2, 
						(short) 1, (double) 1000.0)));
		
		/*
		 * ID != null, name != null, type != null, company = null, 
		 * ethernetAddress != null, checkInDate != null, room != null
		 */
		Assert.assertEquals("No input can be null", hotelManager.checkIn("A12345678", 
				"John", "Standard", null, new Date(), false, 
				"00:00:00:00:00:00", new Room((int) 6, (int) 1, (int) 2, 
						(short) 1, (double) 1000.0)));
		
		/*
		 * ID != null, name != null, type != null, company != null, 
		 * ethernetAddress != null, checkInDate = null, room != null
		 */
		Assert.assertEquals("No input can be null", hotelManager.checkIn("A12345678", 
				"John", "Standard", "Google", null, false, 
				"00:00:00:00:00:00", new Room((int) 6, (int) 1, (int) 2, 
						(short) 1, (double) 1000.0)));
		
		/*
		 * ID != null, name != null, type != null, company != null, 
		 * ethernetAddress = null, checkInDate != null, room != null
		 */
		Assert.assertEquals("No input can be null", hotelManager.checkIn("A12345678", 
				"John", "Standard", "Google", new Date(), false, 
				null, new Room((int) 6, (int) 1, (int) 2, 
						(short) 1, (double) 1000.0)));

		
		/*
		 * ID != null, name != null, type != null, company != null, 
		 * ethernetAddress != null, checkInDate != null, room = null
		 */
		Assert.assertEquals("No input can be null", hotelManager.checkIn("A12345678", 
				"John", "Standard", "Google", new Date(), false, 
				"00:00:00:00:00:00", null));
		
		
		/*
		 * ID = "", name != "", company != "", 
		 */
		Assert.assertEquals("ID, name, and company cannot be empty", 
				hotelManager.checkIn("", "John", "Standard", "Google", 
				new Date(), false, "00:00:00:00:00:00", new Room((int) 6, 
						(int) 1, (int) 2, (short) 1, (double) 1000.0)));
		
		/*
		 * ID != "", name = "", company != "", 
		 */
		Assert.assertEquals("ID, name, and company cannot be empty", 
				hotelManager.checkIn("A12345678", "", "Standard", "Google", 
				new Date(), false, "00:00:00:00:00:00", new Room((int) 6, 
						(int) 1, (int) 2, (short) 1, (double) 1000.0)));
		
		
		/*
		 * ID != "", name != "", company = "", 
		 */
		Assert.assertEquals("ID, name, and company cannot be empty", 
				hotelManager.checkIn("A12345678", "John", "Standard", "", 
				new Date(), false, "00:00:00:00:00:00", new Room((int) 6, 
						(int) 1, (int) 2, (short) 1, (double) 1000.0)));
		
		/*
		 * type != Standard && type != Business
		 */
		Assert.assertEquals("Invalid type", 
				hotelManager.checkIn("A12345678", "John", "Double", "Google", 
				new Date(), false, "00:00:00:00:00:00", new Room((int) 6, 
						(int) 1, (int) 2, (short) 1, (double) 1000.0)));		
		
		
		/*
		 * room type = 1 && dataServiceRequired = true
		 */
		Assert.assertEquals("No data service for standard rooms", 
				hotelManager.checkIn("A12345678", "John", "Standard", "Google", 
				new Date(), true, "00:00:00:00:00:00", new Room((int) 6, 
						(int) 1, (int) 2, (short) 1, (double) 1000.0)));	
		
		/*
		 * type = Standard && dataServiceRequired = true
		 */
		Assert.assertEquals("No data service for standard occupants", 
				hotelManager.checkIn("A12345678", "John", "Standard", "Google", 
				new Date(), true, "00:00:00:00:00:00", new Room((int) 6, 
						(int) 1, (int) 2, (short) 2, (double) 1000.0)));
		
		/*
		 * !ID.matches("[a-zA-Z][0-9]{8}")
		 */
		Assert.assertEquals("The format of the inputted ID is invalid", 
				hotelManager.checkIn("Aa2345678", "John", "Standard", "Google", 
				new Date(), false, "00:00:00:00:00:00", new Room((int) 6, 
						(int) 1, (int) 2, (short) 1, (double) 1000.0)));
		
		/*
		 * dataServiceRequired == true && 
		 * !ethernetAddress.matches("([0-9a-f]{2}:){5}[0-9a-f]{2}")
		 */
		Assert.assertEquals("The format of the inputted ethernetAddress is invalid", 
				hotelManager.checkIn("A12345678", "John", "Business", "Google", 
				new Date(), true, "A0:00:00:00:00:00", new Room((int) 6, 
						(int) 1, (int) 2, (short) 2, (double) 1000.0)));
		
		/*
		 * dataServiceRequired == false
		 */
		Room room = hotelManager.rooms[0][0];
		hotelManager.checkIn("A12345678", "John", "Standard", "Google", 
				new Date(), false, "00:00:00:00:00:00", room);
		Assert.assertEquals("", 
				hotelManager.getRoom(0,0).getOccupation().getEthernetAddress());
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(room.getOccupation().checkInDate);
		calendar.add(calendar.DATE, 1);
		hotelManager.checkOut(calendar.getTime(),room);
		
		/*
		 * dataServiceRequired != false
		 */
		room = hotelManager.rooms[1][0];
		hotelManager.checkIn("A12345678", "John", "Business", "Google", 
				new Date(), true, "00:00:00:00:00:00", room);
		Assert.assertEquals("00:00:00:00:00:00", 
				hotelManager.getRoom(1,0).getOccupation().getEthernetAddress());
		calendar.setTime(room.getOccupation().checkInDate);
		calendar.add(calendar.DATE, 1);
		hotelManager.checkOut(calendar.getTime(),room);
		
		//check in success
		int checkInBefore = hotelManager.listAllAvailableRooms().size();
		room = hotelManager.rooms[1][0];
		Assert.assertEquals("Success", 
				hotelManager.checkIn("A12345678", "John", "Standard", "Google", 
						new Date(), false, "00:00:00:00:00:00", room));
		int checkInAfter = hotelManager.listAllAvailableRooms().size();
		Assert.assertEquals(checkInBefore - 1, checkInAfter);
	}
	
	@Test
	public void testCheckOut() {
		Room room = hotelManager.rooms[0][0];
		hotelManager.checkIn("A12345678", "John", "Standard", "Google", 
				new Date(), false, "00:00:00:00:00:00", room);
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(room.getOccupation().checkInDate);
		calendar.add(calendar.DATE, 1);
		
		//checkOutDate == null
		Assert.assertEquals("No input can be null",
				hotelManager.checkOut(null, room));
		
		//room == null
		Assert.assertEquals("No input can be null",
				hotelManager.checkOut(calendar.getTime(), null));
			
		//occupation == null
		Assert.assertEquals("The room has no occupant",
				hotelManager.checkOut(calendar.getTime(), hotelManager.rooms[1][0]));
		
		//checkin date < checkout date
		Assert.assertEquals("Check-out date must be after check-in date",
				hotelManager.checkOut(room.getOccupation().checkInDate, room));
		
		//check out success
		int checkOutBefore = hotelManager.listAllOccupiedRooms().size();
		hotelManager.checkOut(calendar.getTime(), room);
		int checkOutAfter = hotelManager.listAllOccupiedRooms().size();
		Assert.assertEquals(checkOutBefore - 1, checkOutAfter);
	}
	
	@Test
	public void testFindOccupant() {
		Room room = hotelManager.rooms[0][0];
		hotelManager.checkIn("A12345678", "John", "Standard", "Google", 
				new Date(), false, "00:00:00:00:00:00", room);
		ArrayList result = new ArrayList();
		result.add(room);
		
		/*
		 * search by ID
		 * type = ID && o.getID().matches(value)
		 */
		Assert.assertEquals(result,hotelManager.findOccupant("ID", "A12345678"));
		
		/*
		 * search by ID
		 * type = ID && !o.getID().matches(value)
		 */
		Assert.assertEquals(new ArrayList(),
				hotelManager.findOccupant("ID", "A12345677"));
		
		/*
		 * search by type
		 * type = Type && o.gettype().matches(value)
		 */
		Assert.assertEquals(result,hotelManager.findOccupant("Type", "Standard"));
		
		/*
		 * search by type
		 * type = Type && !o.gettype().matches(value)
		 */
		Assert.assertEquals(new ArrayList(),
				hotelManager.findOccupant("Type", "Standards"));
		
		/*
		 * search by company
		 * type = Company && o.getCompany().matches(value)
		 */
		Assert.assertEquals(result,hotelManager.findOccupant("Company", "Google"));
		
		/*
		 * search by company
		 * type = Company && !o.getCompany().matches(value)
		 */
		Assert.assertEquals(new ArrayList(),
				hotelManager.findOccupant("Company", "Baidu"));
		
		/*
		 * search by name
		 * type = Name && o.getName().matches(value)
		 */
		Assert.assertEquals(result,hotelManager.findOccupant("Name", "John"));
		
		/*
		 * search by name
		 * type = Name && !o.getName().matches(value)
		 */
		Assert.assertEquals(new ArrayList(),hotelManager.findOccupant("Name", "Bob"));	
	}
	
	@Test
	public void testGetRoom() {	
		Room room = hotelManager.rooms[0][0];
		Assert.assertEquals(room, hotelManager.getRoom(0, 0));
	}	
	
	@Test
	public void testUpdateRoomRate() {	
		Room room = hotelManager.rooms[0][0];
		double rate = 10.0;
		Assert.assertTrue(hotelManager.updateRoomRate(room, rate));
		Assert.assertTrue((double) 10.0 == room.getRate());
	}
	
	@Test
	public void testGenerateID() {	
		Assert.assertNotNull(hotelManager.generateID());
	}	
	
	@Test
	public void testGetUI() {	
		Assert.assertEquals(hotelManager.ui, hotelManager.getUI());
	}	
	
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testHotelManagerException() throws Exception {		
		new File("5100Hotel.xml").renameTo(new File("Hotel.xml"));
		exception.expect(Exception.class);
		new HotelManager(); 
	}
}
