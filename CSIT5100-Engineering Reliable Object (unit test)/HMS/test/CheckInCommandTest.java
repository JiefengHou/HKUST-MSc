import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.*;
import javax.swing.*;
import hms.command.*;
import hms.gui.*;
import hms.main.*;
import hms.model.*;

public class CheckInCommandTest {
	
	private HotelManager hotelManager; 
	private UI gui;
	private CheckInCommand checkInCommand;
	
	@Before
	public void setUp() throws Exception { 
		hotelManager = new HotelManager(); 
		
		//create a new object of checkInCommand
		checkInCommand = new CheckInCommand(hotelManager);
		gui = hotelManager.getUI(); 
		gui.setVisible(false); 
		
	}
	
	@After 
	public void tearDown() throws Exception { 
		gui = null; 
		hotelManager = null; 
		checkInCommand = null;
	}
	
	@Test
	public void testGetPanel() {
		Command command = gui.currentCommand; 
		Assert.assertTrue(command instanceof CheckInCommand);
		
		//get checkInPamel
		JPanel panel = command.getPanel(gui.tableView); 
		Assert.assertTrue(panel instanceof CheckInPanel);
	}	
	
	@Test
	public void testGetID() {
		checkInCommand.ID = "A8473294";
		Assert.assertEquals("A8473294", checkInCommand.getID());
	}
	
	@Test
	public void testSetID() {
		checkInCommand.setID("A8473294");
		Assert.assertEquals("A8473294", checkInCommand.ID);
	}
	
	@Test
	public void testGetName() {
		checkInCommand.name = "John";
		Assert.assertEquals("John", checkInCommand.getName());
	}
	
	@Test
	public void testSetName() {
		checkInCommand.setName("John");
		Assert.assertEquals("John", checkInCommand.name);
	}
	
	@Test
	public void testGetType() {
		checkInCommand.type = "Business";
		Assert.assertEquals("Business", checkInCommand.getType());
	}
	
	@Test
	public void testSetType() {
		checkInCommand.setType("Business");
		Assert.assertEquals("Business", checkInCommand.type);
	}
	
	@Test
	public void testGetCompany() {
		checkInCommand.company = "Google";
		Assert.assertEquals("Google", checkInCommand.getCompany());
	}
	
	@Test
	public void testSetCompany() {
		checkInCommand.setCompany("Google");
		Assert.assertEquals("Google", checkInCommand.company);
	}
	
	@Test
	public void testGetCheckInDate() {
		Date date = new GregorianCalendar().getTime();
		checkInCommand.checkInDate = new GregorianCalendar().getTime();
		Assert.assertEquals(new SimpleDateFormat("dd-MM-yyyy").format(date), 
				new SimpleDateFormat("dd-MM-yyyy").
				format(checkInCommand.getCheckInDate()));
	}
	
	@Test
	public void testSetCheckInDate() {
		Date date = new GregorianCalendar().getTime();
		checkInCommand.setCheckInDate(new GregorianCalendar().getTime());
		Assert.assertEquals(new SimpleDateFormat("dd-MM-yyyy").format(date), 
				new SimpleDateFormat("dd-MM-yyyy").
				format(checkInCommand.checkInDate));
	}
	
	@Test
	public void testIsDataServiceRequired() {
		checkInCommand.dataServiceRequired = true;
		Assert.assertTrue(checkInCommand.isDataServiceRequired());
	}
	
	@Test
	public void testSetDataServiceRequired() {
		checkInCommand.setDataServiceRequired(true);
		Assert.assertTrue(checkInCommand.dataServiceRequired);
	}
	
	@Test
	public void testGetEthernetAddress() {
		checkInCommand.ethernetAddress = "10:10:10:10:10:10";
		Assert.assertEquals("10:10:10:10:10:10", 
				checkInCommand.getEthernetAddress());
	}
	
	@Test
	public void testSetEthernetAddress() {
		checkInCommand.setEthernetAddress("10:10:10:10:10:10");
		Assert.assertEquals("10:10:10:10:10:10", checkInCommand.ethernetAddress);
	}
	
	@Test
	public void testGetSelectedRoom() {
		Room room = hotelManager.rooms[0][0];
		checkInCommand.selectedRoom = room;
		Assert.assertEquals(room.toString(),
				checkInCommand.getSelectedRoom().toString());
	}	
	
	@Test
	public void testSetSelectedRoom() {
		Room room = hotelManager.rooms[0][0];
		checkInCommand.setSelectedRoom(room);
		Assert.assertEquals(room.toString(), 
				checkInCommand.selectedRoom.toString());
	}
	
	@Test
	public void testCheckIn() {	
		//check in successfully
		checkInCommand.setName("John");
		checkInCommand.setCompany("Baidu");
		checkInCommand.setSelectedRoom(hotelManager.rooms[0][0]);
		Assert.assertEquals("Success",checkInCommand.checkIn());
		
		//check in not successfully
		checkInCommand.setName("");
		checkInCommand.setCompany("Baidu");
		checkInCommand.setSelectedRoom(hotelManager.rooms[1][0]);
		Assert.assertEquals("ID, name, and company cannot be empty",
				checkInCommand.checkIn());
	}
	
	@Test
	public void testClear() {
		checkInCommand.clear();
		Assert.assertEquals("",checkInCommand.getName());
		Assert.assertEquals("", checkInCommand.getCompany());
		Assert.assertEquals(checkInCommand.ID,checkInCommand.getID());
		Assert.assertFalse(checkInCommand.isDataServiceRequired());
		Assert.assertEquals(new SimpleDateFormat("dd-MM-yyyy").format
				(new GregorianCalendar().getTime()), 
				new SimpleDateFormat("dd-MM-yyyy").
				format(checkInCommand.getCheckInDate()));
		Assert.assertEquals("00:00:00:00:00:00", checkInCommand.getEthernetAddress());
		Assert.assertEquals("Standard", checkInCommand.getType());
		Assert.assertNull(checkInCommand.getSelectedRoom());		
	}
	
	@Test
	public void testGetCurrentDate() throws Exception {
		Date date = new SimpleDateFormat("dd-MM-yyyy").
				parse(new SimpleDateFormat("dd-MM-yyyy").
						format(new GregorianCalendar().getTime()));	
		Assert.assertEquals(date, checkInCommand.getCurrentDate());
	}
}
