package hms.gui;

import hms.command.CheckOutCommand;
import hms.command.Command;
import hms.main.HotelManager;
import hms.model.Occupant;
import hms.model.Occupation;
import hms.model.Room;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
//import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

/**
 * The Class CheckOutPanel encapsulates GUI elements for checking out.
 */
public class CheckOutPanel extends JPanel {
	
	public CheckOutCommand command;
	
	public HotelManager hotelManager;
	
	public JTextField checkOutDateField;
	
	public JTable occupiedRoomTable;
	
	public Object[] occupiedRooms;
	
	public JButton checkOutButton;

	/**
	 * Instantiates a new check out panel.
	 * @param cmd the check out command
	 * @param hm the hotel manager
	 */
	public CheckOutPanel(Command cmd, HotelManager hm) {
		command = (CheckOutCommand) cmd;
		hotelManager = hm;
		setLayout(new BorderLayout());
		addOccupantInfo();
		addRoomInfo();
		getCommandValues();
	}

	/**
	 * Adds the occupant info.
	 */
	public void addOccupantInfo() {	
		JPanel wholePanel = new JPanel();
		add(wholePanel, BorderLayout.NORTH);
		wholePanel.setLayout(new GridLayout(1, 2, 1, 1));
		wholePanel.setBorder(new BevelBorder(BevelBorder.LOWERED));

		JPanel checkOutDatePanel = new JPanel();
		wholePanel.add(checkOutDatePanel);

		checkOutDatePanel.add(new JLabel("Date of Check-out (dd-MM-yyyy):"));
		checkOutDateField = new JTextField(15);	
		checkOutDatePanel.add(checkOutDateField);

		JToolBar checkOutToolbar = new JToolBar();
		wholePanel.add(checkOutToolbar);

		checkOutButton = new JButton("Check Out");
		checkOutButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								Container container = checkOutButton.getTopLevelAncestor();
								if (command.getSelectedRoom() == null)
								{
//									JOptionPane.showMessageDialog(container, "No room is selected", 
//									"Input Error", JOptionPane.INFORMATION_MESSAGE);
									System.err.print("Input Error: No room is selected");
									return;
								}
								String date = checkOutDateField.getText();
								try {
									command.setCheckOutDate(new SimpleDateFormat("dd-MM-yyyy").parse(date));
								}
								catch (Exception ex) {
									checkOutDateField.setText(new SimpleDateFormat("dd-MM-yyyy").format(command.getCheckInDate()));
//									JOptionPane.showMessageDialog(container, "The format of the inputted check-in date is invalid", 
//											"Input Error", JOptionPane.INFORMATION_MESSAGE);
									System.err.print("Input Error: The format of the inputted check-in date is invalid");
									return;
								}
								String result = command.checkOut();
								if (result.equals("Success")) {
									getCommandValues();
								} else {
//									JOptionPane.showMessageDialog(container, result, 
//									"Input Error", JOptionPane.INFORMATION_MESSAGE);
									System.err.print("Input Error: " + result);
								}
							}
						});
		checkOutToolbar.add(checkOutButton);
	}

	/**
	 * Gets the command values, can be used to update the occupied room table.
	 * @return the command values
	 */
	public void getCommandValues() {
		ArrayList list = hotelManager.listAllOccupiedRooms();
		occupiedRooms = list.toArray();
		occupiedRoomTable.setModel(new AbstractTableModel() {
							String[] headers = {"Room No", "Room Type", "Capacity", "Rate", "Data Service", "Ethernet Address",
										    "Occupant Name", "Occupant Type", "Member ID", "Company", "Check-in Date"};
							public int getRowCount() { return occupiedRooms.length; }
							public int getColumnCount() { return headers.length; }
							public Object getValueAt(int r, int c) { 
								Room room = (Room) occupiedRooms[r];
								Occupation occupation = room.getOccupation();
								Occupant occupant = occupation.getOccupant();
								if (room == null) { return null; }
								switch(c) {
									case 0: return room.getFloorNo() + "-" + room.getRoomNo();
									case 1: return room.getTypeString();
									case 2: return new Integer(room.getCapacity());
									case 3: return new Double(room.getRate());
									case 4: if (room.getType() == 1) { return "N/A"; }
										    else if (occupation.isDataServiceRequired()) { return "In used"; }
										    else { return "Not in used"; }
									case 5: return occupation.getEthernetAddress();
									case 6: return occupant.getName();
									case 7: return occupant.getType();
									case 8: return occupant.getID();
									case 9: return occupant.getCompany();
									case 10: return new SimpleDateFormat("dd-MM-yyyy").format(occupation.getCheckInDate());
								}
								return "";
							}
							public String getColumnName(int c) { return headers[c]; }
						});
		Room selectedRoom = command.getSelectedRoom();
		if (selectedRoom != null) {
			int index = list.indexOf(selectedRoom);
			occupiedRoomTable.setRowSelectionInterval(index, index);
		}
		checkOutDateField.setText(new SimpleDateFormat("dd-MM-yyyy").format(command.getCheckOutDate()));
	}

	/**
	 * Adds the room info.
	 */
	public void addRoomInfo() {
		JPanel roomInfo = new JPanel();
		roomInfo.setLayout(new BorderLayout());
		roomInfo.add(new JLabel(" Occupied Room List:"), BorderLayout.NORTH);

		occupiedRoomTable = new JTable();
		roomInfo.add(new JScrollPane(occupiedRoomTable), BorderLayout.CENTER);			
		add(roomInfo, BorderLayout.CENTER);

		occupiedRoomTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		final ListSelectionModel model = occupiedRoomTable.getSelectionModel();
		model.addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent e) {
								int index = model.getMinSelectionIndex();
								if (index != -1) {
									Room room = (Room) occupiedRooms[index];
									if (room != null) {
											command.setSelectedRoom(room);
											Occupation occupation = room.getOccupation();
											Occupant occupant = occupation.getOccupant();
											command.setID(occupant.getID());
											command.setName(occupant.getName());
											command.setType(occupant.getType());
											command.setCompany(occupant.getCompany());
											command.setCheckInDate(occupation.getCheckInDate());
											command.setDataServiceRequired(occupation.isDataServiceRequired());
											command.setEthernetAddress(occupation.getEthernetAddress());
									}
								}
							}
					});
	}
}
