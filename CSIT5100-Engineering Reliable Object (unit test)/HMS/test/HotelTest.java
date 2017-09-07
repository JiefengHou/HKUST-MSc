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

public class HotelTest {

	private Hotel hotel;
	
	@Test
	public void testHotel() {
		hotel = new Hotel("ABC Hotel", (int) 10, (double) 20.0);
		Assert.assertEquals("ABC Hotel", hotel.name);
		Assert.assertEquals(10, hotel.numFloors);
		Assert.assertTrue((double) 20.0 == hotel.dataServiceCharge);
	}

	@Test
	public void testGetHotelName() {
		hotel = new Hotel("ABC Hotel", (int) 10, (double) 20.0);
		Assert.assertEquals("ABC Hotel", hotel.getName());
	}
	
	@Test
	public void testMain() {
		hotel.main(new String[] {"main"});
	}
}
