import static org.junit.Assert.*;

import java.awt.event.ItemListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import java.awt.event.ActionListener;

import org.junit.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.UIManager.*;

import hms.command.*;
import hms.gui.*;
import hms.main.*;
import hms.model.*;
import java.io.*;

public class CheckOutPanelTest {
	
	private HotelManager hotelManager; 
	private UI gui;
	private CheckInPanel checkInPanel;
	private CheckOutPanel checkOutPanel;
	private ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
	public void setUp() throws Exception { 
		hotelManager = new HotelManager(); 
		gui = hotelManager.getUI(); 
	
		checkInPanel = new CheckInPanel(new CheckInCommand(hotelManager),hotelManager);
		//first person check in		
		checkInPanel.command.setName("John");
		checkInPanel.command.setCompany("Baidu");
		checkInPanel.command.setSelectedRoom(hotelManager.rooms[0][0]);
		checkInPanel.command.checkIn();
		//second person check in
		checkInPanel.command.setName("Nick");
		checkInPanel.command.setCompany("Google");
		checkInPanel.command.setSelectedRoom(hotelManager.rooms[2][0]);
		checkInPanel.command.checkIn();
		//third person check in
		checkInPanel.command.setName("Ferry");
		checkInPanel.command.setCompany("Apple");
		checkInPanel.command.setType("Business");
		checkInPanel.command.setDataServiceRequired(true);
		checkInPanel.command.setSelectedRoom(hotelManager.rooms[1][1]);
		checkInPanel.command.checkIn();
		
		gui.checkOut.setSelected(true);
		checkOutPanel = new CheckOutPanel(new CheckOutCommand(hotelManager),hotelManager);
	}
	
	@After 
	public void tearDown() throws Exception { 
		gui.setVisible(false);
		gui = null; 
		hotelManager = null;  
		checkOutPanel = null;
		checkInPanel = null;
	}
	
	@Test
	public void testAddOccupantInfo() {	
		/*
		 * GUI test of checkOutPanel
		 */
		//number of component of checkOutPanel - wholePanel and roomInfo
		Assert.assertEquals(2, checkOutPanel.getComponentCount());
		
		//The first component of checkOutPanel
		JPanel wholePanel = (JPanel) checkOutPanel.getComponent(0);
		Assert.assertTrue(wholePanel instanceof JPanel);
		
		//number of component of wholePanel - checkOutDatePanel and checkOutDateField
		Assert.assertEquals(2, wholePanel.getComponentCount());
		
		//The first component of wholePanel
		JPanel checkOutDatePanel = (JPanel) wholePanel.getComponent(0);
		Assert.assertTrue(checkOutDatePanel instanceof JPanel);
		
		//The first component of checkOutDatePanel
		JLabel label_1 = (JLabel) checkOutDatePanel.getComponent(0);
		Assert.assertTrue(label_1 instanceof JLabel);
		Assert.assertEquals("Date of Check-out (dd-MM-yyyy):",label_1.getText());
		
		//The second component of checkOutDatePanel
		JTextField checkOutDateField = (JTextField) checkOutDatePanel.getComponent(1);
		Assert.assertTrue(checkOutDateField instanceof JTextField);
		Assert.assertEquals(new SimpleDateFormat("dd-MM-yyyy").
				format(checkOutPanel.command.getCheckInDate()), 
				checkOutDateField.getText());
		
		//The second component of wholePanel
		JToolBar checkOutToolbar = (JToolBar) wholePanel.getComponent(1);
		Assert.assertTrue(checkOutToolbar instanceof JToolBar);
		
		//The first component of checkOutToolbar
		JButton checkOutButton = (JButton) checkOutToolbar.getComponent(0);
		Assert.assertTrue(checkOutButton instanceof JButton);
		Assert.assertEquals("Check Out", checkOutButton.getText());
		
		
		/*
		 * Functional test of checkOutPanel
		 */
		//check out without selection room		
		System.setErr(new PrintStream(errContent));
		checkOutPanel.checkOutButton.doClick();
		Assert.assertEquals("Input Error: No room is selected", errContent.toString());
		
		//check out with incorrect date
		checkOutPanel.occupiedRoomTable.setRowSelectionInterval(0, 0);
		checkOutPanel.checkOutDateField.setText("aa-10-2016");
		errContent = new ByteArrayOutputStream();
		System.setErr(new PrintStream(errContent));
		checkOutPanel.checkOutButton.doClick();
		Assert.assertEquals("Input Error: The format of the inputted check-in "
				+ "date is invalid", errContent.toString());
		
		//check out not successfully
		checkOutPanel.occupiedRoomTable.setRowSelectionInterval(0, 0);
		checkOutPanel.checkOutDateField.setText(new SimpleDateFormat("dd-MM-yyyy").
				format(checkInPanel.command.checkInDate));
		
		errContent = new ByteArrayOutputStream();
		System.setErr(new PrintStream(errContent));
		checkOutPanel.checkOutButton.doClick();
		//String result = checkOutPanel.command.checkOut();
		Assert.assertEquals("Input Error: Check-out date must be after check-in "
				+ "date", errContent.toString());
		
		// check out successful
		checkOutPanel.occupiedRoomTable.setRowSelectionInterval(0, 0);

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(checkInPanel.command.getCheckInDate());
		calendar.add(calendar.DATE, 1);
		checkOutPanel.checkOutDateField.setText(new SimpleDateFormat("dd-MM-yyyy").
				format(calendar.getTime()));
		
		int checkOutBefore = checkOutPanel.occupiedRooms.length;
		checkOutPanel.checkOutButton.doClick();
		int checkOutAfter = checkOutPanel.occupiedRooms.length;
		Assert.assertEquals(checkOutBefore - 1, checkOutAfter);
		gui.updateMainPanel();
	}
	
	@Test
	public void testGetCommandValues() {		
		Assert.assertEquals(new SimpleDateFormat("dd-MM-yyyy").
				format(checkOutPanel.command.getCheckInDate()),
				checkOutPanel.checkOutDateField.getText());
		
		//set selection room != null
		checkOutPanel.command.setSelectedRoom(hotelManager.rooms[0][0]);
		checkOutPanel.getCommandValues();
		
		//get value from non-existent column
		Assert.assertEquals("", checkOutPanel.occupiedRoomTable.getModel().
				getValueAt(0, 11));
	}
	
	@Test
	public void testAddRoomInfo() {
		
		//The second component of checkOutPanel
		JPanel roomInfo = (JPanel) checkOutPanel.getComponent(1);
		Assert.assertTrue(roomInfo instanceof JPanel);
		
		//number of component of roomInfo
		Assert.assertEquals(2, roomInfo.getComponentCount());
		
		//Available room list
		JLabel label_1 = (JLabel) roomInfo.getComponent(0);
		Assert.assertTrue(label_1 instanceof JLabel);
		Assert.assertEquals(" Occupied Room List:",label_1.getText());

		//Occupied room
		JScrollPane occupiedRoom = (JScrollPane) roomInfo.getComponent(1);
		Assert.assertTrue(occupiedRoom instanceof JScrollPane);	
		
		//Available room table
		JViewport viewport = occupiedRoom.getViewport();
		JTable occupiedRoomTable = (JTable) viewport.getComponent(0);
		Assert.assertTrue(occupiedRoomTable instanceof JTable);
		
		//set first available rooms is null
		checkOutPanel.occupiedRooms = new Room[]{null};
		checkOutPanel.occupiedRoomTable.setRowSelectionInterval(0, 0);
	}
}
