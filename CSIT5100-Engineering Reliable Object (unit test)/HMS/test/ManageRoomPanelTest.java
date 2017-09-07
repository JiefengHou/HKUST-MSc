import static org.junit.Assert.*;

import java.awt.Component;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
import javax.swing.event.ListSelectionListener;

public class ManageRoomPanelTest {

	private HotelManager hotelManager; 
	private UI gui;
	private ManageRoomCommand manageRoomCommand;
	private ManageRoomPanel manageRoomPanel;
	private ByteArrayOutputStream content = new ByteArrayOutputStream();
	
	@Before
	public void setUp() throws Exception { 
		hotelManager = new HotelManager(); 
		gui = hotelManager.getUI(); 
		
		gui.manageRooms.setSelected(true);
		Command command = gui.currentCommand;		
		manageRoomPanel = (ManageRoomPanel) command.getPanel(gui.tableView);
	}
	
	@After 
	public void tearDown() throws Exception { 
		gui.setVisible(false);
		gui = null; 
		hotelManager = null;
		manageRoomPanel = null;
	}
	
	@Test
	public void testAddEditableRooms() {
		
		//number of component of manageRoomPanel - editableRoomsTable and controlPanel
		Assert.assertEquals(2, manageRoomPanel.getComponentCount());
		
		//The first component of manageRoomPanel
		JPanel editableRoomsPanel = (JPanel) manageRoomPanel.getComponent(0);
		Assert.assertTrue(editableRoomsPanel instanceof JPanel);
		
		//number of component of editableRoomsPanel
		Assert.assertEquals(2, editableRoomsPanel.getComponentCount());
		
		//editable room list - first component of editableRoomsPanel
		JLabel label_1 = (JLabel) editableRoomsPanel.getComponent(0);
		Assert.assertTrue(label_1 instanceof JLabel);
		Assert.assertEquals("Editable Room List:",label_1.getText());

		//editable room - second component of editableRoomsPanel
		JScrollPane editableRooms = (JScrollPane) editableRoomsPanel.getComponent(1);
		Assert.assertTrue(editableRooms instanceof JScrollPane);	
		
		//editable room table
		JViewport viewport = editableRooms.getViewport();
		JTable editableRoomsTable = (JTable) viewport.getComponent(0);
		Assert.assertTrue(editableRoomsTable instanceof JTable);
		
		//set room of editableRooms is null and then get value from table
		manageRoomPanel.editableRooms = new Room[]{null};
		manageRoomPanel.editableRoomsTable.setRowSelectionInterval(0, 0);
	}
	
	@Test
	public void testAddRoomInfoPanel() {
		
		//The second component of manageRoomPanel
		JPanel controlPanel = (JPanel) manageRoomPanel.getComponent(1);
		Assert.assertTrue(controlPanel instanceof JPanel);
				
		//The first component of controlPanel
		JPanel cmdPanel = (JPanel) controlPanel.getComponent(0);
		Assert.assertTrue(cmdPanel instanceof JPanel);
		
		JLabel label_1 = (JLabel) cmdPanel.getComponent(0);
		Assert.assertTrue(label_1 instanceof JLabel);
		Assert.assertEquals("You can manage:",label_1.getText());
		
		JLabel label_2 = (JLabel) cmdPanel.getComponent(2);
		Assert.assertTrue(label_2 instanceof JLabel);
		Assert.assertEquals("1. Today's room rate",label_2.getText());
		
		JLabel label_3 = (JLabel) cmdPanel.getComponent(4);
		Assert.assertTrue(label_3 instanceof JLabel);
		Assert.assertEquals("2. Today's room discount",label_3.getText());
		
		JButton editBtn = (JButton) cmdPanel.getComponent(6);
		Assert.assertTrue(editBtn instanceof JButton);
		Assert.assertEquals("Edit Selected Room", editBtn.getText());
	}
	
	@Test
	public void testGetCommandValues() {
		manageRoomPanel.command.setSelectedRoom(hotelManager.rooms[0][0]);
		manageRoomPanel.getCommandValues();
		
		//get value from non-existent column
		Assert.assertEquals("", manageRoomPanel.editableRoomsTable.getModel().
				getValueAt(0, 5));
		
		//set first editable room is null and then get value from table
		manageRoomPanel.editableRooms = new Room[]{null};
		Assert.assertNull(manageRoomPanel.editableRoomsTable.getModel().getValueAt(0, 0));
	}
	
	@Test
	public void testAddButtonActions() throws Exception {
		Room[][] rooms = hotelManager.rooms;
		
		//click edit button without selecting room
		hotelManager.rooms = new Room[][]{};
		System.setOut(new PrintStream(content));
		manageRoomPanel.editBtn.doClick();
		Assert.assertEquals("Warning: no room selected!", content.toString());
		
		//click edit button
		hotelManager.rooms = rooms;
		manageRoomPanel.editableRoomsTable.setRowSelectionInterval(0, 0);
		manageRoomPanel.editBtn.doClick();	
		manageRoomPanel.cancelBtn.doClick();
		
		//click edit button
		hotelManager.rooms = rooms;
		manageRoomPanel.editableRoomsTable.setRowSelectionInterval(0, 0);
		manageRoomPanel.roomInfoPanel = new JPanel();
		manageRoomPanel.editBtn.doClick();	

		//GUI test of roomInfoPanel
		Assert.assertEquals(2, manageRoomPanel.controlPanel.getComponentCount());
		
		JPanel roomInfoPanel = (JPanel) manageRoomPanel.controlPanel.getComponent(1);
		Assert.assertTrue(roomInfoPanel instanceof JPanel);
		
		JLabel label_1 = (JLabel) roomInfoPanel.getComponent(0);
		Assert.assertTrue(label_1 instanceof JLabel);
		Assert.assertEquals("Room Details:",label_1.getText());
		
		JLabel label_2 = (JLabel) roomInfoPanel.getComponent(2);
		Assert.assertTrue(label_2 instanceof JLabel);
		Assert.assertEquals("Floor No:",label_2.getText());
		
		JLabel label_3 = (JLabel) roomInfoPanel.getComponent(4);
		Assert.assertTrue(label_3 instanceof JLabel);
		Assert.assertEquals("Room No:",label_3.getText());
		
		JLabel label_4 = (JLabel) roomInfoPanel.getComponent(6);
		Assert.assertTrue(label_4 instanceof JLabel);
		Assert.assertEquals("Room Type:",label_4.getText());
		
		JLabel label_5 = (JLabel) roomInfoPanel.getComponent(8);
		Assert.assertTrue(label_5 instanceof JLabel);
		Assert.assertEquals("Capacity:",label_5.getText());
		
		JLabel label_6 = (JLabel) roomInfoPanel.getComponent(10);
		Assert.assertTrue(label_6 instanceof JLabel);
		Assert.assertEquals("Rate:",label_6.getText());
		
		JLabel label_7 = (JLabel) roomInfoPanel.getComponent(12);
		Assert.assertTrue(label_7 instanceof JLabel);
		Assert.assertEquals("Discount %:",label_7.getText());
		
		JTextField discount = (JTextField) roomInfoPanel.getComponent(13);
		Assert.assertTrue(discount instanceof JTextField);
		Assert.assertEquals("20.0",discount.getText());
		
		JButton updateBtn = (JButton) roomInfoPanel.getComponent(14);
		Assert.assertTrue(updateBtn instanceof JButton);
		Assert.assertEquals("Update", updateBtn.getText());
		
		JButton cancelBtn = (JButton) roomInfoPanel.getComponent(15);
		Assert.assertTrue(cancelBtn instanceof JButton);
		Assert.assertEquals("Cancel", cancelBtn.getText());
	
		
		//populate the room info
		Room selectedRoom = (Room) manageRoomPanel.editableRooms
				[manageRoomPanel.editableRoomsTable.getSelectedRow()];
		
		//incorrect rate
		manageRoomPanel.rate.setText("abc");
		content = new ByteArrayOutputStream();
		System.setErr(new PrintStream(content));
		manageRoomPanel.updateBtn.doClick();
		Assert.assertEquals("Error: Rate not correctly set!", content.toString());
		
		//incorrect discount
		manageRoomPanel.rate.setText(new Double(selectedRoom.getRate()).toString());
		manageRoomPanel.discount.setText("abc");
		content = new ByteArrayOutputStream();
		System.setErr(new PrintStream(content));
		manageRoomPanel.updateBtn.doClick();
		Assert.assertEquals("Error: Discount not correctly set!"
				+ "Error: Invalid room rate or discount!", content.toString());
		
		//newRate <= 0
		manageRoomPanel.discount.setText("30");
		manageRoomPanel.rate.setText("0");
		content = new ByteArrayOutputStream();
		System.setErr(new PrintStream(content));
		manageRoomPanel.updateBtn.doClick();
		Assert.assertEquals("Error: Invalid room rate or discount!", content.toString());	
		
		//newDiscount < 0
		manageRoomPanel.discount.setText("-10");
		manageRoomPanel.rate.setText("3000");
		content = new ByteArrayOutputStream();
		System.setErr(new PrintStream(content));
		manageRoomPanel.updateBtn.doClick();
		Assert.assertEquals("Error: Invalid room rate or discount!", content.toString());
		
		//newDiscount > 100
		manageRoomPanel.discount.setText("101");
		content = new ByteArrayOutputStream();
		System.setErr(new PrintStream(content));
		manageRoomPanel.updateBtn.doClick();
		Assert.assertEquals("Error: Invalid room rate or discount!", content.toString());
		
		//newRate > 0 && newDiscount >= 0 && newDiscount < 100 
		//roomInfoPanel != null
		manageRoomPanel.discount.setText("30");
		manageRoomPanel.updateBtn.doClick();
		
		//newRate > 0 && newDiscount >= 0 && newDiscount < 100
		//roomInfoPanel = null
		manageRoomPanel.discount.setText("30");
		manageRoomPanel.roomInfoPanel = null;
		manageRoomPanel.updateBtn.doClick();
				
		//click cancel button
		manageRoomPanel.cancelBtn.doClick();		
	}
}
