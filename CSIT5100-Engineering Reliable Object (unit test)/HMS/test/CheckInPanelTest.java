import static org.junit.Assert.*;

import java.awt.event.ItemListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

public class CheckInPanelTest {

	private HotelManager hotelManager; 
	private UI gui;
	private CheckInPanel checkInPanel;
	private ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	
	@Before
	public void setUp() throws Exception { 
		hotelManager = new HotelManager(); 		
		gui = hotelManager.getUI(); 
		
		checkInPanel = new CheckInPanel(new CheckInCommand(hotelManager),hotelManager);
	}
	
	@After 
	public void tearDown() throws Exception { 
		gui.setVisible(false);
		gui = null; 
		hotelManager = null; 
		checkInPanel =null;
	}
	
	@Test
	public void testAddOccupantInfo() {				
		//check in without selection room		
		System.setErr(new PrintStream(errContent));
		checkInPanel.checkInButton.doClick();
		Assert.assertEquals("Input Error: No room is selected", errContent.toString());
	
		//check in with matching incorrect ID format
		checkInPanel.IDField.setText("Aa1234567");
		checkInPanel.nameField.setText("John");
		checkInPanel.companyField.setText("Google");
		checkInPanel.availableRoomTable.setRowSelectionInterval(0, 0);
		
		errContent = new ByteArrayOutputStream();
		System.setErr(new PrintStream(errContent));

		checkInPanel.checkInButton.doClick();
		Assert.assertEquals("Input Error: The format of the inputted ID is invalid. "
				+ "It should be an English letter followed by exactly 8 digits", 
				errContent.toString());
		
		//check in without correct data format
		checkInPanel.IDField.setText("A12345678");
		checkInPanel.nameField.setText("John");
		checkInPanel.companyField.setText("Google");
		checkInPanel.availableRoomTable.setRowSelectionInterval(0, 0);
		checkInPanel.checkInDateField.setText("aa-10-2016");
		
		errContent = new ByteArrayOutputStream();
		System.setErr(new PrintStream(errContent));

		checkInPanel.checkInButton.doClick();		
		Assert.assertEquals("Input Error: The format of the input check-in "
				+ "date is invalid", errContent.toString());
		
		//check in without correct data format
		checkInPanel.IDField.setText("A12345678");
		checkInPanel.nameField.setText("John");
		checkInPanel.companyField.setText("Google");
		checkInPanel.typeField.setSelectedItem("Business");
		checkInPanel.availableRoomTable.setRowSelectionInterval(0, 0);
		checkInPanel.dataServiceRequiredBox.setSelectedItem("Yes");
		checkInPanel.checkInDateField.setText(new SimpleDateFormat("dd-MM-yyyy").
				format(new GregorianCalendar().getTime()));
		checkInPanel.ethernetAddressField.setText("p0:00:00:00:00:00");
		
		errContent = new ByteArrayOutputStream();
		System.setErr(new PrintStream(errContent));
		
		checkInPanel.checkInButton.doClick();
		Assert.assertEquals("Input Error: The format of the input ethernet "
				+ "address is invalid", errContent.toString());
		
		//check in not successfully because name = ""
		checkInPanel.IDField.setText("A12345678");
		checkInPanel.nameField.setText("");
		checkInPanel.companyField.setText("Google");
		checkInPanel.typeField.setSelectedItem("Standard");
		checkInPanel.availableRoomTable.setRowSelectionInterval(0, 0);
		checkInPanel.dataServiceRequiredBox.setSelectedItem("No");
		checkInPanel.checkInDateField.setText(new SimpleDateFormat("dd-MM-yyyy").
				format(new GregorianCalendar().getTime()));
		checkInPanel.ethernetAddressField.setText("00:00:00:00:00:00");
			
		errContent = new ByteArrayOutputStream();
		System.setErr(new PrintStream(errContent));
		checkInPanel.checkInButton.doClick();
	
		Assert.assertEquals("Input Error: ID, name, and company cannot be empty", 
				errContent.toString());

		//check in successfully
		Date date = new GregorianCalendar().getTime();
		checkInPanel.IDField.setText("A12345678");
		checkInPanel.nameField.setText("John");
		checkInPanel.companyField.setText("Google");
		checkInPanel.typeField.setSelectedItem("Standard");
		checkInPanel.availableRoomTable.setRowSelectionInterval(0, 0);
		checkInPanel.dataServiceRequiredBox.setSelectedItem("No");
		checkInPanel.checkInDateField.setText(new SimpleDateFormat("dd-MM-yyyy").
				format(date));
		checkInPanel.ethernetAddressField.setText("00:00:00:00:00:00");
		
		int checkInBefore = checkInPanel.availableRooms.length;
		checkInPanel.checkInButton.doClick();
		int checkInAfter = checkInPanel.availableRooms.length;
		Assert.assertEquals(checkInBefore - 1, checkInAfter);
		gui.updateMainPanel();
	}
	
	@Test
	public void testGetCommandValues() {
		Assert.assertEquals(checkInPanel.command.getID(),checkInPanel.IDField.getText());
		
		Assert.assertEquals(checkInPanel.command.getName(),checkInPanel.nameField.getText());

		Assert.assertEquals(checkInPanel.command.getType(),
				checkInPanel.typeField.getSelectedItem());
		
		Assert.assertEquals(checkInPanel.command.getCompany(),
				checkInPanel.companyField.getText());
		
		Assert.assertEquals(new SimpleDateFormat("dd-MM-yyyy").
				format(checkInPanel.command.getCheckInDate()),
				checkInPanel.checkInDateField.getText());
		
		Assert.assertEquals(checkInPanel.command.getEthernetAddress(),
				checkInPanel.ethernetAddressField.getText());	
		
		//set select room != null
		checkInPanel.command.setSelectedRoom(hotelManager.rooms[0][0]);
		checkInPanel.getCommandValues();
				
		//get value from non-existent column
		Assert.assertEquals("", checkInPanel.availableRoomTable.getModel().
				getValueAt(0, 5));
		
		//set first available rooms is null and then get value from table
		checkInPanel.availableRooms = new Room[]{null};
		Assert.assertNull(checkInPanel.availableRoomTable.getModel().getValueAt(0, 0));
	}
	
	@Test
	public void testUpdateDataServiceRequired() {	
		//room == null
		checkInPanel.command.setSelectedRoom(null);
		checkInPanel.updateDataServiceRequired();
		Assert.assertFalse(checkInPanel.dataServiceRequiredBox.isEnabled());
		Assert.assertFalse(checkInPanel.ethernetAddressField.isEnabled());
		Assert.assertEquals("No",checkInPanel.dataServiceRequiredBox.
				getSelectedItem());
		Assert.assertEquals(false,checkInPanel.command.isDataServiceRequired());
		
		//room.getType() == Room.STANDARD 
		checkInPanel.command.setSelectedRoom(hotelManager.rooms[0][0]);
		checkInPanel.command.selectedRoom.type = Room.STANDARD;
		checkInPanel.updateDataServiceRequired();
		Assert.assertFalse(checkInPanel.dataServiceRequiredBox.isEnabled());
		Assert.assertFalse(checkInPanel.ethernetAddressField.isEnabled());
		Assert.assertEquals("No",checkInPanel.dataServiceRequiredBox.
				getSelectedItem());
		Assert.assertEquals(false,checkInPanel.command.isDataServiceRequired());
	
		//typeField.getSelectedItem().equals("Standard")
		checkInPanel.typeField.setSelectedItem("Standard");
		checkInPanel.updateDataServiceRequired();
		Assert.assertFalse(checkInPanel.dataServiceRequiredBox.isEnabled());
		Assert.assertFalse(checkInPanel.ethernetAddressField.isEnabled());
		Assert.assertEquals("No",checkInPanel.dataServiceRequiredBox.
				getSelectedItem());
		Assert.assertEquals(false,checkInPanel.command.isDataServiceRequired());	
		
		// room != null, room.getType() != Room.STANDARD and 
		// !typeField.getSelectedItem().equals("Standard") and 
		// checkInPanel.command.isDataServiceRequired() = true
		checkInPanel.command.setSelectedRoom(hotelManager.rooms[0][0]);
		checkInPanel.command.selectedRoom.type = Room.EXECUTIVE;
		checkInPanel.typeField.setSelectedItem("Business");
		checkInPanel.dataServiceRequiredBox.setSelectedItem("No");
		checkInPanel.command.setDataServiceRequired(true);
		checkInPanel.updateDataServiceRequired();
		Assert.assertTrue(checkInPanel.dataServiceRequiredBox.isEnabled());
		Assert.assertEquals("Yes",checkInPanel.dataServiceRequiredBox.
				getSelectedItem());
		Assert.assertTrue(checkInPanel.ethernetAddressField.isEnabled());
		
		// room != null, room.getType() != Room.STANDARD and 
		// !typeField.getSelectedItem().equals("Standard") and 
		// checkInPanel.command.isDataServiceRequired() = false
		checkInPanel.command.setSelectedRoom(hotelManager.rooms[0][0]);
		checkInPanel.command.selectedRoom.type = Room.EXECUTIVE;
		checkInPanel.typeField.setSelectedItem("Business");
		checkInPanel.dataServiceRequiredBox.setSelectedItem("Yes");
		checkInPanel.command.setDataServiceRequired(false);
		checkInPanel.updateDataServiceRequired();
		Assert.assertTrue(checkInPanel.dataServiceRequiredBox.isEnabled());
		Assert.assertEquals("No",checkInPanel.dataServiceRequiredBox.
				getSelectedItem());
		Assert.assertFalse(checkInPanel.ethernetAddressField.isEnabled());
	}
	
	@Test
	public void testGetOccupantInfoHelper() throws Exception {
		JPanel occupantInfo = checkInPanel.getOccupantInfoHelper();

		//number of component of occupantInfoHelper
		Assert.assertEquals(20, occupantInfo.getComponentCount());
		
		//Occupant Details
		JLabel label_1 = (JLabel) occupantInfo.getComponent(0);
		Assert.assertTrue(label_1 instanceof JLabel);
		Assert.assertEquals("Occupant Details:",label_1.getText());
	
		//ID 
		JLabel label_3 = (JLabel) occupantInfo.getComponent(2);
		Assert.assertTrue(label_3 instanceof JLabel);
		Assert.assertEquals("ID:",label_3.getText());
		
		JTextField IDField = (JTextField) occupantInfo.getComponent(3);
		Assert.assertTrue(IDField instanceof JTextField);
		Assert.assertEquals("",IDField.getText());
		
		//Name
		JLabel label_4 = (JLabel) occupantInfo.getComponent(4);
		Assert.assertTrue(label_4 instanceof JLabel);
		Assert.assertEquals("Name:",label_4.getText());
		
		JTextField nameField = (JTextField) occupantInfo.getComponent(5);
		Assert.assertTrue(nameField instanceof JTextField);
		Assert.assertEquals("", nameField.getText());
		
		//Type
		JLabel label_5 = (JLabel) occupantInfo.getComponent(6);
		Assert.assertTrue(label_5 instanceof JLabel);
		Assert.assertEquals("Type:",label_5.getText());
		
		JComboBox typeField = (JComboBox) occupantInfo.getComponent(7);
		Assert.assertTrue(typeField instanceof JComboBox);
		Assert.assertEquals("Standard", typeField.getSelectedItem());
		
		//Company
		JLabel label_6 = (JLabel) occupantInfo.getComponent(8);
		Assert.assertTrue(label_6 instanceof JLabel);
		Assert.assertEquals("Company:",label_6.getText());
		
		JTextField companyField = (JTextField) occupantInfo.getComponent(9);
		Assert.assertTrue(companyField instanceof JTextField);
		Assert.assertEquals("", companyField.getText());
		
		//Occupation Details
		JLabel label_7 = (JLabel) occupantInfo.getComponent(12);
		Assert.assertTrue(label_7 instanceof JLabel);
		Assert.assertEquals("Occupation Details:",label_7.getText());
		
		//CheckInDate
		JLabel label_8 = (JLabel) occupantInfo.getComponent(14);
		Assert.assertTrue(label_8 instanceof JLabel);
		Assert.assertEquals("Date of Check-in (dd-MM-yyyy):",label_8.getText());
		
		JTextField checkInDateField = (JTextField) occupantInfo.getComponent(15);
		Assert.assertTrue(checkInDateField instanceof JTextField);
		Assert.assertEquals("", checkInDateField.getText());
		
		//Data service required
		JLabel label_9 = (JLabel) occupantInfo.getComponent(16);
		Assert.assertTrue(label_9 instanceof JLabel);
		Assert.assertEquals("Is Data Service Required?",label_9.getText());
		
		JComboBox dataServiceRequiredBox = (JComboBox) occupantInfo.getComponent(17);
		Assert.assertTrue(dataServiceRequiredBox instanceof JComboBox);
		Assert.assertEquals("Yes", dataServiceRequiredBox.getSelectedItem());
		
		//EthernetAddress
		JLabel label_10 = (JLabel) occupantInfo.getComponent(18);
		Assert.assertTrue(label_10 instanceof JLabel);
		Assert.assertEquals("EthernetAddress:",label_10.getText());
		
		JTextField ethernetAddressField = (JTextField) occupantInfo.getComponent(19);
		Assert.assertTrue(ethernetAddressField instanceof JTextField);
		Assert.assertEquals("", ethernetAddressField.getText());
		
		//set LookAndFeel for checkInPanel.typeField
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			//UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (Exception ex) {
			
		}
		checkInPanel.typeField.setSelectedItem("Business");
		gui.add(checkInPanel.typeField);
		checkInPanel.typeField.setSelectedItem("Standard");
		
		//set LookAndFeel for checkInPanel.typeField
		checkInPanel.dataServiceRequiredBox.setSelectedItem("Yes");
		gui.add(checkInPanel.dataServiceRequiredBox);
		checkInPanel.dataServiceRequiredBox.setSelectedItem("No");
	}
	
	@Test
	public void testAddRoomInfo() {
		//get roomInfo panel from checkInPanel
		JPanel roomInfo = (JPanel) checkInPanel.getComponent(1);
		Assert.assertTrue(roomInfo instanceof JPanel);
		
		//number of component of roomInfo
		Assert.assertEquals(2, roomInfo.getComponentCount());
		
		//Available room list
		JLabel label_1 = (JLabel) roomInfo.getComponent(0);
		Assert.assertTrue(label_1 instanceof JLabel);
		Assert.assertEquals("Available Room List:",label_1.getText());

		//Available room
		JScrollPane availableRoom = (JScrollPane) roomInfo.getComponent(1);
		Assert.assertTrue(availableRoom instanceof JScrollPane);	
		
		//Available room table
		JViewport viewport = availableRoom.getViewport();
		JTable availableRoomTable = (JTable) viewport.getComponent(0);
		Assert.assertTrue(availableRoomTable instanceof JTable);
		
		//set first available rooms is null
		checkInPanel.availableRooms = new Room[]{null};
		checkInPanel.availableRoomTable.setRowSelectionInterval(0, 0);		
	}
}
