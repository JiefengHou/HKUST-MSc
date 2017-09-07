import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.*;
import javax.swing.*;
import hms.command.*;
import hms.gui.*;
import hms.main.*;
import hms.model.*;

public class OccupantTest {
	
	private Occupant occupant;
	
	@Before
	public void setUp() throws Exception { 
		occupant = new Occupant("", "", "", "");
	}
	
	@After 
	public void tearDown() throws Exception { 
		occupant = null;
	}
	
	@Test
	public void testOccupant() {
		occupant = new Occupant("A12345678", "Standard", "John", "Google");
		Assert.assertEquals("A12345678", occupant.ID);
		Assert.assertEquals("Standard", occupant.type);
		Assert.assertEquals("John", occupant.name);
		Assert.assertEquals("Google", occupant.company);
	}	

	@Test
	public void testGetID() {
		occupant.ID = "A12345678";
		Assert.assertEquals("A12345678", occupant.getID());
	}
	
	@Test
	public void testGetType() {
		occupant.type = "Standard";
		Assert.assertEquals("Standard", occupant.getType());
	}
	
	@Test
	public void testGetCompany() {
		occupant.company = "Google";
		Assert.assertEquals("Google", occupant.getCompany());
	}
	
	@Test
	public void testGetName() {
		occupant.name = "John";
		Assert.assertEquals("John", occupant.getName());
	}

}
