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

public class ManageRoomCommandTest {

	private HotelManager hotelManager; 
	private UI gui;
	private ManageRoomCommand manageRoomCommand;
	
	@Before
	public void setUp() throws Exception { 
		hotelManager = new HotelManager(); 
		gui = hotelManager.getUI(); 
		gui.setVisible(false); 
		gui.manageRooms.setSelected(true);

		manageRoomCommand = new ManageRoomCommand();

	}
	
	@After 
	public void tearDown() throws Exception { 
		gui = null; hotelManager = null; manageRoomCommand = null;
	}
	
	@Test
	public void testGetPanel() {
		Command command = gui.currentCommand; 
		Assert.assertTrue(command instanceof ManageRoomCommand);
		
		//get manageRoomPanel
		JPanel panel = command.getPanel(gui.tableView); 
		Assert.assertTrue(panel instanceof ManageRoomPanel);
	}
	
	@Test
	public void testGetSelectedRoom() {
		Room room = hotelManager.rooms[0][0];
		manageRoomCommand.selectedRoom = room;
		Assert.assertEquals(room.toString(), 
				manageRoomCommand.getSelectedRoom().toString());
	}
	
	@Test
	public void testSetSelectedRoom() {
		Room room = hotelManager.rooms[0][0];
		manageRoomCommand.setSelectedRoom(room);
		Assert.assertEquals(room.toString(), 
				manageRoomCommand.selectedRoom.toString());
	}
}
