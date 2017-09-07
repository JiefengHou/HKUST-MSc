import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	//command test
	CheckInCommandTest.class,
	CheckOutCommandTest.class,
	ManageRoomCommandTest.class,
	SearchCommandTest.class,
	
	//model test
	RoomTest.class,
	OccupantTest.class,
	OccupationTest.class,
	
	//main test
	HotelTest.class,
	HotelManagerTest.class,
	
	//GUI test
	CheckInPanelTest.class,
	CheckOutPanelTest.class,
	ManageRoomPanelTest.class,
	SearchPanelTest.class,
	UITest.class,
	TableViewTest.class
})

public class CSIT5100_TestMain {
}
