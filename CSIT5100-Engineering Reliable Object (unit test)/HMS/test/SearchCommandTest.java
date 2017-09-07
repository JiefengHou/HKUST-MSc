import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.*;
import javax.swing.*;
import hms.command.*;
import hms.gui.*;
import hms.main.*;
import hms.model.*;

public class SearchCommandTest {
	
	
	private HotelManager hotelManager; 
	private UI gui;
	private SearchCommand searchCommand;
	
	@Before
	public void setUp() throws Exception { 
		hotelManager = new HotelManager(); 
		gui = hotelManager.getUI(); 
		gui.setVisible(false); 
		gui.search.setSelected(true);

		//create a new object of search command
		searchCommand = new SearchCommand(hotelManager);
		
	}
	
	@After 
	public void tearDown() throws Exception { 
		gui = null; 
		hotelManager = null; 
		searchCommand = null;
	}
	
	@Test
	public void testGetPanel() {
		Command command = gui.currentCommand; 
		Assert.assertTrue(command instanceof SearchCommand);
		
		//get searchPanel
		JPanel panel = command.getPanel(gui.tableView); 
		Assert.assertTrue(panel instanceof SearchPanel);
	}
	
	@Test
	public void testGetSearchField() {
		searchCommand.searchField = "A8473294";
		Assert.assertEquals("A8473294", searchCommand.getSearchField());
	}
	
	@Test
	public void testSetSearchField() {
		searchCommand.setSearchField("A8473294");
		Assert.assertEquals("A8473294", searchCommand.searchField);
	}
		
	@Test
	public void testGetSearchType() {
		searchCommand.type = "Name";
		Assert.assertEquals("Name", searchCommand.type);
	}	
	
	@Test
	public void testSetSearchType() {
		searchCommand.setType("Name");
		Assert.assertEquals("Name", searchCommand.type);
	}

	@Test
	public void testGetSearchRuslts() {
		Object[] result = new Object[]{"1-1","Presidential","8","3000.0",
				"Not in used","10:10:10:10:10:10","John","Standard","A12345678",
				"Baidu","01-10-2016"};	
		searchCommand.searchResults = result;
		Assert.assertEquals(result.toString(), searchCommand.getSearchResults().
				toString());
	}
	
	@Test
	public void testSetSearchRuslts() {
		Object[] result = new Object[]{"1-1","Presidential","8","3000.0",
				"Not in used","10:10:10:10:10:10","John","Standard","A12345678",
				"Baidu","01-10-2016"};	
		searchCommand.setSearchResults(result);
		Assert.assertEquals(result.toString(), searchCommand.searchResults.toString());
	}
	
	@Test
	public void testSearch() {
		ArrayList result = new ArrayList();
		Room room = hotelManager.rooms[0][0];
		hotelManager.checkIn("A12345678", "John", "Standard", "Google", 
				new Date(), false, "00:00:00:00:00:00", room);
		result.add(room);

		//find result and then return a arrayList of result.
		searchCommand.setType("Name");
		searchCommand.setSearchField("John");
		Assert.assertEquals(result,searchCommand.search());
		
		//result not found and then return a empty arrayList.
		searchCommand.setType("Name");
		searchCommand.setSearchField("Joe");
		Assert.assertEquals(new ArrayList(), searchCommand.search());
	}
}
