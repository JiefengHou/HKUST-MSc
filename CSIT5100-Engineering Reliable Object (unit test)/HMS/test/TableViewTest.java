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
public class TableViewTest {
	
	private TableView tableView;
	private UI gui;
	private HotelManager hotelManager;
	private Command currentCommand;
	
	@Before
	public void setUp() throws Exception { 
		hotelManager = new HotelManager(); 
		gui = hotelManager.getUI(); 
		gui.setVisible(false);
		tableView = new TableView(hotelManager);	
	}
	
	@After 
	public void tearDown() throws Exception { 
		tableView = null; 
		hotelManager = null;
		gui = null;
	}

	@Test
	public void testAccept() {
		CheckInCommand checkInCommand = new CheckInCommand(hotelManager);
		CheckInPanel checkInPanel = (CheckInPanel) tableView.accept(checkInCommand);
		Assert.assertTrue(checkInPanel instanceof JPanel);
	}
	
	@Test
	public void testCheckInPanel() {
		CheckInCommand checkInCommand = new CheckInCommand(hotelManager);
		tableView.currentCommand = checkInCommand;
		CheckInPanel checkInPanel_2 = (CheckInPanel) tableView.checkInPanel();
		Assert.assertTrue(checkInPanel_2 instanceof JPanel);
	}
	
	@Test
	public void testCheckOutPanel() {
		CheckOutCommand checkOutCommand = new CheckOutCommand(hotelManager);
		tableView.currentCommand = checkOutCommand;
		CheckOutPanel checkOutPanel = (CheckOutPanel) tableView.checkOutPanel();
		Assert.assertTrue(checkOutPanel instanceof JPanel);
	}
	
	@Test
	public void testSearchPanel() {
		SearchCommand searchCommand = new SearchCommand(hotelManager);
		tableView.currentCommand = searchCommand;
		SearchPanel searchPanel = (SearchPanel) tableView.searchPanel();
		Assert.assertTrue(searchPanel instanceof JPanel);
	}
	
	@Test
	public void testManageRoomPanel() {
		ManageRoomCommand manageRoomCommand = new ManageRoomCommand();
		tableView.currentCommand = manageRoomCommand;
		ManageRoomPanel manageRoomPanel = (ManageRoomPanel) tableView.manageRoomPanel();
		Assert.assertTrue(manageRoomPanel instanceof JPanel);
	}
}
