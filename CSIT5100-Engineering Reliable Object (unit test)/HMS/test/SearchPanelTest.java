import static org.junit.Assert.*;

import java.awt.event.ItemListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

public class SearchPanelTest {

	private HotelManager hotelManager; 
	private UI gui;
	private CheckInPanel checkInPanel;
	private SearchPanel searchPanel;
	private SearchCommand searchCommand;

	@Before
	public void setUp() throws Exception { 
		hotelManager = new HotelManager(); 
		gui = hotelManager.getUI(); 

		checkInPanel = new CheckInPanel(gui.currentCommand,hotelManager);
				
		checkInPanel.command.setName("John");
		checkInPanel.command.setCompany("Baidu");
		checkInPanel.command.setSelectedRoom(hotelManager.rooms[0][0]);
		checkInPanel.command.checkIn();
		
		checkInPanel.command.setName("Nick");
		checkInPanel.command.setCompany("Google");
		checkInPanel.command.setSelectedRoom(hotelManager.rooms[2][0]);
		checkInPanel.command.checkIn();
		
		checkInPanel.command.setName("Ferry");
		checkInPanel.command.setCompany("Apple");
		checkInPanel.command.setType("Business");
		checkInPanel.command.setDataServiceRequired(true);
		checkInPanel.command.setSelectedRoom(hotelManager.rooms[1][1]);
		checkInPanel.command.checkIn();
	
		gui.search.setSelected(true);
		searchCommand = (SearchCommand) gui.currentCommand;
		searchPanel = new SearchPanel(searchCommand);	
	}
	
	@After 
	public void tearDown() throws Exception { 
		gui.setVisible(false);
		gui = null; 
		hotelManager = null;  
		searchPanel =null;
		searchCommand = null;
	}
	
	@Test
	public void testAddSearchInfo() {	
		
		//number of component of searchPanel - wholePanel and roomInfo
		Assert.assertEquals(2, searchPanel.getComponentCount());
		
		//The first component of searchPanel
		JPanel wholePanel = (JPanel) searchPanel.getComponent(0);
		Assert.assertTrue(wholePanel instanceof JPanel);
		
		//The first component of wholePanel
		JPanel innerPanel = (JPanel) wholePanel.getComponent(0);
		Assert.assertTrue(innerPanel instanceof JPanel);
		
		//The first component of innerPanel
		JPanel searchInfo = (JPanel) innerPanel.getComponent(0);
		Assert.assertTrue(searchInfo instanceof JPanel);
		
		//The first component of searchInfo
		JLabel label_1 = (JLabel) searchInfo.getComponent(0);
		Assert.assertTrue(label_1 instanceof JLabel);
		Assert.assertEquals("Search Field:",label_1.getText());
		
		//The second component of searchInfo
		JTextField searchField = (JTextField) searchInfo.getComponent(1);
		Assert.assertTrue(searchField instanceof JTextField);
		
		//The third component of searchInfo
		JLabel label_2 = (JLabel) searchInfo.getComponent(2);
		Assert.assertTrue(label_2 instanceof JLabel);
		Assert.assertEquals("Type:",label_2.getText());
		
		//The fourth component of searchInfo
		JComboBox typeField = (JComboBox) searchInfo.getComponent(3);
		Assert.assertTrue(typeField instanceof JComboBox);
		Assert.assertEquals("ID",typeField.getSelectedItem().toString());
		
		//The second component of wholePanel
		JToolBar searchToolbar = (JToolBar) wholePanel.getComponent(1);
		Assert.assertTrue(searchToolbar instanceof JToolBar);
		
		//The first component of searchToolbar - search button
		JButton searchButton = (JButton) searchToolbar.getComponent(0);
		Assert.assertTrue(searchButton instanceof JButton);
		Assert.assertEquals("Search", searchButton.getText());
				
		//search
		searchPanel.searchField.setText("Baidu");
		searchPanel.typeField.setSelectedItem("Company");
		searchPanel.searchButton.doClick();
		gui.updateMainPanel();
		Assert.assertNotNull(searchPanel.searchResults);
		
		
		//set LookAndFeel for searchPanel.typeField
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			//UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (Exception ex) {
			
		}
		searchPanel.typeField.setSelectedItem("Name");
		gui.add(searchPanel.typeField);
		searchPanel.typeField.setSelectedItem("ID");
	}
	
	@Test
	public void testAddRoomInfo() {
		
		//The second component of checkOutPanel
		JPanel roomInfo = (JPanel) searchPanel.getComponent(1);
		Assert.assertTrue(roomInfo instanceof JPanel);
		
		//number of component of roomInfo
		Assert.assertEquals(2, roomInfo.getComponentCount());
		
		//Available room list
		JLabel label_1 = (JLabel) roomInfo.getComponent(0);
		Assert.assertTrue(label_1 instanceof JLabel);
		Assert.assertEquals("Searched Room List:",label_1.getText());

		//Search room
		JScrollPane searchRoom = (JScrollPane) roomInfo.getComponent(1);
		Assert.assertTrue(searchRoom instanceof JScrollPane);	
		
		//search room table
		JViewport viewport = searchRoom.getViewport();
		JTable searchRoomTable = (JTable) viewport.getComponent(0);
		Assert.assertTrue(searchRoomTable instanceof JTable);
		
	}
	
	@Test
	public void testGetCommandValues() {		
		//search by name "Nick"
		searchPanel.command.setSearchField("Nick");
		searchPanel.command.setType("Name");
		searchPanel.command.setSearchResults(searchPanel.command.search().toArray());
		searchPanel.getCommandValues();
		gui.updateMainPanel();
		Assert.assertEquals("Standard", searchPanel.searchRoomTable.getModel().getValueAt(0, 1));
		Assert.assertEquals("N/A", searchPanel.searchRoomTable.getModel().getValueAt(0, 4));
		
		//search by name "Ferry"
		searchPanel.command.setSearchField("Ferry");
		searchPanel.command.setType("Name");
		searchPanel.command.setSearchResults(searchPanel.command.search().toArray());
		searchPanel.getCommandValues();
		gui.updateMainPanel();	
		Assert.assertEquals("Executive", searchPanel.searchRoomTable.getModel().getValueAt(0, 1));
		Assert.assertEquals("In used", searchPanel.searchRoomTable.getModel().getValueAt(0, 4));
		
		//search by name "John"
		searchPanel.command.setSearchField("John");
		searchPanel.command.setType("Name");
		searchPanel.command.setSearchResults(searchPanel.command.search().toArray());
		searchPanel.getCommandValues();
		gui.updateMainPanel();
		Assert.assertEquals("Presidential", searchPanel.searchRoomTable.getModel().getValueAt(0, 1));
		Assert.assertEquals("Not in used", searchPanel.searchRoomTable.getModel().getValueAt(0, 4));
		
		//get value from non-existent column
		Assert.assertEquals("", searchPanel.searchRoomTable.getModel().
				getValueAt(0, 11));
	}
}
