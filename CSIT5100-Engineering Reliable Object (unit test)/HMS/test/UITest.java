import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.lang.System;
import org.junit.*;
import com.sun.prism.paint.Stop;

import javax.swing.*;
import hms.command.*;
import hms.gui.*;
import hms.main.*;
import hms.model.*;

public class UITest {
	
	private HotelManager hotelManager; 
	private UI gui;
	
	@Before
	public void setUp() throws Exception { 
		hotelManager = new HotelManager(); 
		gui = hotelManager.getUI();  
	}
	
	@After 
	public void tearDown() throws Exception { 
		gui.setVisible(false);
		gui = null;  
		hotelManager = null;
	}
	
	@Test
	public void testUI() {
		//check title of GUI
		Assert.assertEquals("Hotel Management System", gui.getTitle());
	}
	
	@Test
	public void testInitializeViewAndCommand() {
		Assert.assertNotNull(gui.tableView);
		Assert.assertTrue(gui.tableView instanceof TableView);
		
		Assert.assertTrue(gui.checkIn.isSelected());
		
		Assert.assertNotNull(gui.currentCommand);
		Assert.assertTrue(gui.currentCommand instanceof CheckInCommand);
	}
	
	@Test
	public void testUpdateMainPanel() {
		JPanel panel = (JPanel) gui.getContentPane().getComponent(0);
	}
	
	@Test
	public void testConstructMenu() throws Exception {
		Assert.assertNotNull(gui.menuBar);
		Assert.assertTrue(gui.menuBar instanceof JMenuBar);
		
		//check text of menu
		Assert.assertEquals("Change Theme", gui.lookAndFeelMenu.getText());
		
		//There are two menu component of gui.lookAndFeelMenu, Metal and Motif
		Assert.assertEquals(2, gui.lookAndFeelMenu.getMenuComponentCount());
		
		//click the menu item "metal"
		JMenuItem metal = null;
		metal = (JMenuItem) gui.lookAndFeelMenu.getMenuComponent(0);
		Assert.assertEquals("Metal", metal.getText());
		metal.doClick();
		Assert.assertEquals("javax.swing.plaf.metal.MetalLookAndFeel",
				UIManager.getLookAndFeel().getClass().getName());
		
		//click the menu item "metal"
		JMenuItem motif = null;
		motif = (JMenuItem) gui.lookAndFeelMenu.getMenuComponent(1);
		Assert.assertEquals("Motif", motif.getText());
		motif.doClick();
		Assert.assertEquals("com.sun.java.swing.plaf.motif.MotifLookAndFeel",
				UIManager.getLookAndFeel().getClass().getName());
	}
	
	@Test
	public void testConstructToolBar() {
		//get tooBalPanel of contentPane of GUI
		JPanel toolBarPanel = (JPanel) gui.getContentPane().getComponent(0);
		Assert.assertTrue(toolBarPanel instanceof JPanel);
	}
	
	@Test
	public void testAddButtonsForCommandSelection() {
		//check title of GUI
		JPanel toolBarPanel = (JPanel) gui.getContentPane().getComponent(0);
		JToolBar commandToolbar = (JToolBar) toolBarPanel.getComponent(0);
		Assert.assertTrue(commandToolbar instanceof JToolBar);
		
		JCheckBox checkIn = (JCheckBox) commandToolbar.getComponent(0);
		Assert.assertTrue(checkIn instanceof JCheckBox);
		Assert.assertTrue(checkIn.isSelected());
		Assert.assertEquals("Check In", checkIn.getText());
		
		JCheckBox checkOut = (JCheckBox) commandToolbar.getComponent(1);
		Assert.assertTrue(checkOut instanceof JCheckBox);
		Assert.assertFalse(checkOut.isSelected());
		Assert.assertEquals("Check Out", checkOut.getText());
		
		JCheckBox search = (JCheckBox) commandToolbar.getComponent(2);
		Assert.assertTrue(search instanceof JCheckBox);
		Assert.assertFalse(search.isSelected());
		Assert.assertEquals("Search", search.getText());
		
		JCheckBox manageRooms = (JCheckBox) commandToolbar.getComponent(3);
		Assert.assertTrue(manageRooms instanceof JCheckBox);
		Assert.assertFalse(manageRooms.isSelected());
		Assert.assertEquals("Manage Rooms", manageRooms.getText());	
	}
}
