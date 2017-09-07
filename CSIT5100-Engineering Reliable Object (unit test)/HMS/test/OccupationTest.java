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

public class OccupationTest {

	private Occupation occupation;
	private Occupant occupant;
	
	@Before
	public void setUp() throws Exception { 
		occupation = new Occupation(null,false,null,null);
	}
	
	@After 
	public void tearDown() throws Exception { 
		occupation = null;
	}
	
	@Test
	public void testOccupant() {
		Date checkInDate = new GregorianCalendar().getTime();
		boolean dataServiceRequired = false;
		String ethernetAddress = "10:10:10:10:10:10";
		occupant = new Occupant("A12345678", "Standard", "John", "Google");
		occupation = new Occupation(checkInDate, dataServiceRequired, ethernetAddress, 
				occupant);
		
		Assert.assertEquals(checkInDate, occupation.checkInDate);
		Assert.assertEquals(dataServiceRequired, occupation.dataServiceRequired);
		Assert.assertEquals(ethernetAddress, occupation.ethernetAddress);
		Assert.assertEquals(occupant, occupation.occupant);
	}	
	
	@Test
	public void testGetCheckInDatet() {
		Date checkInDate = new GregorianCalendar().getTime();
		occupation.checkInDate = checkInDate;
		Assert.assertEquals(checkInDate, occupation.getCheckInDate());
	}
	
	@Test
	public void testIsDataServiceRequired() {
		boolean dataServiceRequired = false;
		occupation.dataServiceRequired = dataServiceRequired;
		Assert.assertEquals(dataServiceRequired, occupation.isDataServiceRequired());
	}
	
	@Test
	public void testGetOccupant() {
		occupant = new Occupant("A12345678", "Standard", "John", "Google");
		occupation.occupant = occupant;
		Assert.assertEquals(occupant, occupation.getOccupant());
	}
	
	@Test
	public void testGetEthernetAddress() {
		String ethernetAddress = "10:10:10:10:10:10";
		occupation.ethernetAddress = ethernetAddress;
		Assert.assertEquals(ethernetAddress, occupation.getEthernetAddress());
	}
}
