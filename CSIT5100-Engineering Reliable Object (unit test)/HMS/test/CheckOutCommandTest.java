import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.junit.*;
import javax.swing.*;
import hms.command.*;
import hms.gui.*;
import hms.main.*;
import hms.model.*;

public class CheckOutCommandTest {

	private HotelManager hotelManager; 
	private UI gui;
	private CheckOutCommand checkOutCommand;
	
	@Before
	public void setUp() throws Exception { 
		hotelManager = new HotelManager();  	
		gui = hotelManager.getUI(); 
		gui.setVisible(false);
		gui.checkOut.setSelected(true);
		
		checkOutCommand = new CheckOutCommand(hotelManager);
	}
	
	@After 
	public void tearDown() throws Exception { 
		gui = null; 
		hotelManager = null; 
		checkOutCommand = null;
	}
	
	@Test
	public void testGetPanel() {
		Command command = gui.currentCommand; 
		Assert.assertTrue(command instanceof CheckOutCommand);
		
		//get checkOutPanel
		JPanel panel = command.getPanel(gui.tableView); 
		Assert.assertTrue(panel instanceof CheckOutPanel);
	}
	
	@Test
	public void testGetID() {
		checkOutCommand.ID = "A8473294";
		Assert.assertEquals("A8473294", checkOutCommand.getID());
	}
	
	@Test
	public void testSetID() {
		checkOutCommand.setID("A8473294");
		Assert.assertEquals("A8473294", checkOutCommand.ID);
	}
	
	@Test
	public void testGetName() {
		checkOutCommand.name = "John";
		Assert.assertEquals("John", checkOutCommand.getName());
	}
	
	@Test
	public void testSetName() {
		checkOutCommand.setName("John");
		Assert.assertEquals("John", checkOutCommand.getName());
	}
	
	@Test
	public void testGetType() {
		checkOutCommand.type = "Business";
		Assert.assertEquals("Business", checkOutCommand.getType());
	}
	
	@Test
	public void testSetType() {
		checkOutCommand.setType("Business");
		Assert.assertEquals("Business", checkOutCommand.type);
	}
	
	@Test
	public void testGetCompany() {
		checkOutCommand.company = "Google";
		Assert.assertEquals("Google", checkOutCommand.getCompany());
	}
	
	@Test
	public void testSetCompany() {
		checkOutCommand.setCompany("Google");
		Assert.assertEquals("Google", checkOutCommand.company);
	}
	
	@Test
	public void testGetCheckInDate() {
		Date date = new GregorianCalendar().getTime();
		checkOutCommand.checkInDate = date;
		Assert.assertEquals(new SimpleDateFormat("dd-MM-yyyy").format(date), 
				new SimpleDateFormat("dd-MM-yyyy").format(checkOutCommand.getCheckInDate()));	
	}
	
	@Test
	public void testSetCheckInDate() {
		Date date = new GregorianCalendar().getTime();
		checkOutCommand.setCheckInDate(new Date());
		Assert.assertEquals(new SimpleDateFormat("dd-MM-yyyy").format(date), 
				new SimpleDateFormat("dd-MM-yyyy").format(checkOutCommand.checkInDate));
	}
	
	@Test
	public void testGetCheckOutDate() {
		Date date = new GregorianCalendar().getTime();
		checkOutCommand.checkOutDate = date;
		Assert.assertEquals(new SimpleDateFormat("dd-MM-yyyy").format(date), 
				new SimpleDateFormat("dd-MM-yyyy").
				format(checkOutCommand.getCheckOutDate()));
	}
	
	@Test
	public void testSetCheckOutDate() {
		Date date = new GregorianCalendar().getTime();
		checkOutCommand.setCheckOutDate(new Date());
		Assert.assertEquals(new SimpleDateFormat("dd-MM-yyyy").format(date), 
				new SimpleDateFormat("dd-MM-yyyy").
				format(checkOutCommand.checkOutDate));
	}
	
	@Test
	public void testIsDataServiceRequired() {
		checkOutCommand.dataServiceRequired = true;
		Assert.assertTrue(checkOutCommand.isDataServiceRequired());
	}
	
	@Test
	public void testSetDataServiceRequired() {
		checkOutCommand.setDataServiceRequired(true);
		Assert.assertTrue(checkOutCommand.dataServiceRequired);
	}
	
	@Test
	public void testGetEthernetAddress() {
		checkOutCommand.ethernetAddress = "10:10:10:10:10:10";
		Assert.assertEquals("10:10:10:10:10:10", checkOutCommand.getEthernetAddress());
	}
	
	@Test
	public void testSetEthernetAddress() {
		checkOutCommand.setEthernetAddress("10:10:10:10:10:10");
		Assert.assertEquals("10:10:10:10:10:10", checkOutCommand.ethernetAddress);
	}
	
	@Test
	public void testGetSelectedRoom() {
		Room room = hotelManager.rooms[0][0];
		checkOutCommand.selectedRoom = room;
		Assert.assertEquals(room.toString(),checkOutCommand.getSelectedRoom().toString());
	}
	
	@Test
	public void testSetSelectedRoom() {
		Room room = hotelManager.rooms[0][0];
		checkOutCommand.setSelectedRoom(room);
		Assert.assertEquals(room.toString(),checkOutCommand.selectedRoom.toString());
	}
	
	@Test
	public void testCheckOut() {
		CheckInCommand checkInCommand = new CheckInCommand(hotelManager);
		Room room = hotelManager.rooms[0][0];
		
		//check out success		
		checkInCommand.setName("John");
		checkInCommand.setCompany("Baidu");
		checkInCommand.setSelectedRoom(room);

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(checkInCommand.getCheckInDate());
		calendar.add(calendar.DATE, 1);
		
		checkOutCommand.setCheckOutDate(calendar.getTime());		
		checkOutCommand.setSelectedRoom(checkInCommand.getSelectedRoom());
		
		Assert.assertEquals("Success",checkInCommand.checkIn());
		Assert.assertEquals("Success",checkOutCommand.checkOut());
		
		
		//check out not success
		checkInCommand.setName("John");
		checkInCommand.setCompany("Baidu");
		checkInCommand.setSelectedRoom(room);

		checkOutCommand.setCheckOutDate(checkInCommand.getCheckInDate());		
		checkOutCommand.setSelectedRoom(checkInCommand.getSelectedRoom());
		
		Assert.assertEquals("Success",checkInCommand.checkIn());
		Assert.assertEquals("Check-out date must be after check-in date", 
				checkOutCommand.checkOut());
	}
	
	@Test
	public void testClear() {
		checkOutCommand.clear();
		Assert.assertEquals("",checkOutCommand.getName());
		Assert.assertEquals("", checkOutCommand.getCompany());
		Assert.assertEquals("",checkOutCommand.getID());
		Assert.assertEquals("",checkOutCommand.getType());
		Assert.assertFalse(checkOutCommand.isDataServiceRequired());
		Assert.assertEquals(new SimpleDateFormat("dd-MM-yyyy").
				format(new GregorianCalendar().getTime()), 
				new SimpleDateFormat("dd-MM-yyyy").
				format(checkOutCommand.getCheckOutDate()));
		Assert.assertEquals("00:00:00:00:00:00", checkOutCommand.getEthernetAddress());
		Assert.assertNull(checkOutCommand.getSelectedRoom());		
	}
	
	@Test
	public void testGetCurrentDate() throws Exception {
		Date date = new SimpleDateFormat("dd-MM-yyyy").
				parse(new SimpleDateFormat("dd-MM-yyyy").
						format(new GregorianCalendar().getTime()));	
		Assert.assertEquals(date, checkOutCommand.getCurrentDate());
	}
}
