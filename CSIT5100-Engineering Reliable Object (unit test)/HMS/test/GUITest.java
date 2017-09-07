import static org.junit.Assert.*;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import org.junit.Test;

import hms.command.CheckInCommand;
import hms.gui.*;
import hms.main.HotelManager;
/*
 * 
 * This test uses CheckInPanel as an example to show basic ways to invoke 
 * event handlers of the GUI widgets.
 * There are two basic ways: (1) Mock the user event 
 *                           (2) Directly invoke the call back methods 
 *
 */
public class GUITest {

	@Test
	public void test() {
		//Create a HotelManager object
		HotelManager manager = new HotelManager();
		
		//Create a CheckInPanel object
		CheckInPanel checkInPanel = 
				new CheckInPanel(new CheckInCommand(manager), manager);
		
		assertNotNull(checkInPanel.hotelManager);
		assertNotNull(checkInPanel.command);
		assertNotNull(checkInPanel.checkInButton);
		
		//Mock click action of checkInPanel.checkInButton
		checkInPanel.checkInButton.doClick();
		
		//Get registered ItemListener of checkInPanel.typeField and invoke 
		//the callback: itemStateChanged
		ItemListener[] itemListeners = checkInPanel.typeField.getItemListeners();
		for (ItemListener listener : itemListeners) {
			listener.itemStateChanged(new ItemEvent
					(checkInPanel.typeField, ItemEvent.SELECTED,
							checkInPanel.typeField.getItemAt(0),
							ItemEvent.SELECTED));
		}
		
		assertEquals(checkInPanel.typeField.getSelectedItem(), "Standard");
		assertFalse(checkInPanel.command.dataServiceRequired);
	}

}
