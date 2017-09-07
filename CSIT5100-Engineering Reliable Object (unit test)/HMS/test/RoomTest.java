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

public class RoomTest {
	
	private Room room;
	private Hotel hotel;

	@Test
	public void testGetFloorNo() {
		room = new Room((int) 6, (int) 1, (int) 2, (short) 1, (double) 1000.0);
		Assert.assertEquals((int) 6, room.getFloorNo());
	}
	
	@Test
	public void testGetRoomNo() {
		room = new Room((int) 6, (int) 1, (int) 2, (short) 1, (double) 1000.0);
		Assert.assertEquals((int) 1, room.getRoomNo());
	}
		
	@Test
	public void testGetTypeString() {
		room = new Room((int) 6, (int) 1, (int) 2, (short) 1, (double) 1000.0);
		Assert.assertEquals("Standard", room.getTypeString());
		
		room = new Room((int) 6, (int) 1, (int) 2, (short) 2, (double) 1000.0);
		Assert.assertEquals("Executive", room.getTypeString());
		
		room = new Room((int) 6, (int) 1, (int) 2, (short) 3, (double) 1000.0);
		Assert.assertEquals("Presidential", room.getTypeString());
		
		room = new Room((int) 6, (int) 1, (int) 2, (short) 4, (double) 1000.0);
		Assert.assertEquals(null, room.getTypeString());
	}
	
	@Test
	public void testGetCapacity() {
		room = new Room((int) 6, (int) 1, (int) 2, (short) 1, (double) 1000.0);
		Assert.assertEquals((int) 2, room.getCapacity());
	}
	
	@Test
	public void testGetType() {
		room = new Room((int) 6, (int) 1, (int) 2, (short) 1, (double) 1000.0);
		Assert.assertEquals((short) 1, room.getType());
	}
	
	@Test
	public void testGetRate() {
		room = new Room((int) 6, (int) 1, (int) 2, (short) 1, (double) 1000.0);
		Assert.assertTrue((double) 1000.0 == room.getRate());	
	}
	
	@Test
	public void testSetRate() {
		room = new Room((int) 6, (int) 1, (int) 2, (short) 1, (double) 1000.0);	
		room.setRate(2000.0);
		Assert.assertTrue((double) 2000.0 == room.getRate());
	}
	
	@Test
	public void testGetOccupation() {
		room = new Room((int) 6, (int) 1, (int) 2, (short) 1, (double) 1000.0);
		Occupation occupation = new Occupation(new Date(),false,"00:00:00:00:00:00", 
				new Occupant("A12345678","Standard","John","Google"));
		room.occupation = occupation;
		Assert.assertEquals(occupation,room.getOccupation());
	}
	
	@Test
	public void testSetOccupation() {
		room = new Room((int) 6, (int) 1, (int) 2, (short) 1, (double) 1000.0);
		Occupation occupation = new Occupation(new Date(),false,"00:00:00:00:00:00", 
				new Occupant("A12345678","Standard","John","Google"));
		room.setOccupation(occupation);
		Assert.assertEquals(occupation,room.occupation);
	}
	
	@Test
	public void testIsAvailable() {
		room = new Room((int) 6, (int) 1, (int) 2, (short) 1, (double) 1000.0);
		Assert.assertTrue(room.isAvailable());
	}
	
	@Test
	public void testToString() {
		String result = "";
		String dataServiceInfo = "";
		String occupantInfo = "None";
		Occupant occupant = null;
		Occupation occupation = null;
		
		room = new Room((int) 6, (int) 1, (int) 2, (short) 2, (double) 1000.0);
		
		/*
		 * occupation != null, 
		 * type != STANDARD && occupation.isDataServiceRequired() == true
		 */
		occupant = new Occupant("A12345678","Executive","John","Google");
	
		occupation = new Occupation(new Date(),true,"00:00:00:00:00:00", 
				occupant);
		
		dataServiceInfo += " Data service: In used (Ethernet address: " 
				+ occupation.getEthernetAddress() + ")";
		occupantInfo = "<" + occupant +  ">" + " Date of checking in: " +
				new SimpleDateFormat("dd-MM-yyyy").
				format(occupation.getCheckInDate());
		
		room.setOccupation(occupation);
		
		result = "Room No: " + room.floorNo + "-" + room.roomNo + ", Room Type: " 
		         + room.getTypeString() + ", Capacity: " + room.capacity 
		         + ", Rate: " + room.rate + dataServiceInfo +  ", Occupant: " 
		         + occupantInfo;
		
		Assert.assertEquals(result,room.toString());
		
		/*
		 * occupation != null, 
		 * type != STANDARD && occupation.isDataServiceRequired() == false
		 */
		result = "";
		dataServiceInfo = "";
		occupantInfo = "None";
		occupant = null;
		occupation = null;
		
		occupant = new Occupant("A12345678","Executive","John","Google");
		occupation = new Occupation(new Date(),false,"00:00:00:00:00:00", 
				occupant);
		
		occupantInfo = "<" + occupant +  ">" + " Date of checking in: " +
				new SimpleDateFormat("dd-MM-yyyy").
				format(occupation.getCheckInDate());
		room.setOccupation(occupation);
		result = "Room No: " + room.floorNo + "-" + room.roomNo + ", Room Type: " 
		         + room.getTypeString() + ", Capacity: " + room.capacity 
		         + ", Rate: " + room.rate + dataServiceInfo +  ", Occupant: " 
		         + occupantInfo;
		Assert.assertEquals(result,room.toString());
		
		/*
		 * occupation != null, 
		 * type == STANDARD && occupation.isDataServiceRequired() == false
		 */
		result = "";
		dataServiceInfo = "";
		occupantInfo = "None";
		occupant = null;
		occupation = null;
		room = new Room((int) 6, (int) 1, (int) 2, (short) 1, (double) 1000.0);
		occupant = new Occupant("A12345678","Standard","John","Google");
		occupation = new Occupation(new Date(),false,"00:00:00:00:00:00", 
				occupant);
		
		occupantInfo = "<" + occupant +  ">" + " Date of checking in: " +
				new SimpleDateFormat("dd-MM-yyyy").
				format(occupation.getCheckInDate());
		room.setOccupation(occupation);
		result = "Room No: " + room.floorNo + "-" + room.roomNo + ", Room Type: " 
		         + room.getTypeString() + ", Capacity: " + room.capacity 
		         + ", Rate: " + room.rate + dataServiceInfo +  ", Occupant: " 
		         + occupantInfo;
		Assert.assertEquals(result,room.toString());
		
		/*
		 * occupation != null, 
		 * type == STANDARD && occupation.isDataServiceRequired() == true
		 */
		result = "";
		dataServiceInfo = "";
		occupantInfo = "None";
		occupant = null;
		occupation = null;
		room = new Room((int) 6, (int) 1, (int) 2, (short) 1, (double) 1000.0);
		occupant = new Occupant("A12345678","Standard","John","Google");
		occupation = new Occupation(new Date(),true,"00:00:00:00:00:00", 
				occupant);
		
		occupantInfo = "<" + occupant +  ">" + " Date of checking in: " +
				new SimpleDateFormat("dd-MM-yyyy").
				format(occupation.getCheckInDate());
		room.setOccupation(occupation);
		result = "Room No: " + room.floorNo + "-" + room.roomNo + ", Room Type: " 
		         + room.getTypeString() + ", Capacity: " + room.capacity 
		         + ", Rate: " + room.rate + dataServiceInfo +  ", Occupant: " 
		         + occupantInfo;
		Assert.assertEquals(result,room.toString());
		
		
		
		/*
		 * occupation == null, 
		 * type != STANDARD
		 */
		result = "";
		dataServiceInfo = "";
		occupantInfo = "None";
		occupant = null;
		occupation = null;
		room = new Room((int) 6, (int) 1, (int) 2, (short) 2, (double) 1000.0);
		dataServiceInfo += "\n Data service: Not in used";
		room.setOccupation(occupation);
		result = "Room No: " + room.floorNo + "-" + room.roomNo + ", Room Type: " 
		         + room.getTypeString() + ", Capacity: " + room.capacity 
		         + ", Rate: " + room.rate + dataServiceInfo +  ", Occupant: " 
		         + occupantInfo;
		Assert.assertEquals(result,room.toString());
		
		
		/*
		 * occupation == null, 
		 * type == STANDARD
		 */
		result = "";
		dataServiceInfo = "";
		occupantInfo = "None";
		occupant = null;
		occupation = null;
		room = new Room((int) 6, (int) 1, (int) 2, (short) 1, (double) 1000.0);
		room.setOccupation(occupation);
		result = "Room No: " + room.floorNo + "-" + room.roomNo + ", Room Type: " 
		         + room.getTypeString() + ", Capacity: " + room.capacity 
		         + ", Rate: " + room.rate + dataServiceInfo +  ", Occupant: " 
		         + occupantInfo;
		
		Assert.assertEquals(result,room.toString());
	}	
}
