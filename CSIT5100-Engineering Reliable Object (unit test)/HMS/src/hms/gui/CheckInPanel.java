package hms.gui;

import hms.command.CheckInCommand;
import hms.command.Command;
import hms.main.HotelManager;
import hms.model.Room;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
//import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

/**
 * The Class CheckInPanel encapsulates GUI elements for checking-in.
 */
public class CheckInPanel extends JPanel {
	
	public CheckInCommand command;
	
	public HotelManager hotelManager;
	
	public JTextField IDField;
	
	public JTextField nameField;
	
	public JComboBox typeField;
	
	public JTextField companyField;
	
	public JTextField checkInDateField;
	
	public JComboBox dataServiceRequiredBox;
	
	public JTextField ethernetAddressField;
	
	public JTable availableRoomTable;
	
	public Object[] availableRooms;
	
	public JButton checkInButton;

	/**
	 * Instantiates a new check in panel.
	 * @param cmd the check-in command
	 * @param hm the hotel manager
	 */
	public CheckInPanel(Command cmd, HotelManager hm) {		
		command =  (CheckInCommand) cmd;
		hotelManager = hm;
		setLayout(new BorderLayout());
		addOccupantInfo();
		addRoomInfo();
		getCommandValues();
		updateDataServiceRequired();
	}

	/**
	 * Adds the occupant info.
	 */
	public void addOccupantInfo() {	
		JPanel wholePanel = new JPanel();
		JPanel upperPanel = new JPanel();			
		JPanel lowerPanel = new JPanel();
		wholePanel.setLayout(new BorderLayout());
		lowerPanel.setLayout(new BorderLayout());

		add(wholePanel, BorderLayout.EAST);
		wholePanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		wholePanel.add(upperPanel, BorderLayout.CENTER);
		wholePanel.add(lowerPanel, BorderLayout.SOUTH);
		upperPanel.add(getOccupantInfoHelper());
		checkInButton = new JButton("Check in");
		checkInButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								Container container = checkInButton.getTopLevelAncestor();
								String ID = IDField.getText();
								if (command.getSelectedRoom() == null)
								{
//									JOptionPane.showMessageDialog(container, "No room is selected", 
//									"Input Error", JOptionPane.INFORMATION_MESSAGE);
									System.err.print("Input Error: No room is selected");
									return;
								}
								if (!ID.matches("[a-zA-Z][0-9]{8}")) {
//									JOptionPane.showMessageDialog(container, "The format of the inputted ID is invalid. It should be an English letter followed by exactly 8 digits", 
//											"Input Error", JOptionPane.INFORMATION_MESSAGE);
									System.err.print("Input Error: The format of the inputted ID is invalid. It should be an English letter followed by exactly 8 digits");
									return;
								} 
								command.setID(ID);
								command.setName(nameField.getText());
								command.setType((String) typeField.getSelectedItem());
								command.setCompany(companyField.getText());
								if (dataServiceRequiredBox.getSelectedItem().equals("Yes")) {
									command.setDataServiceRequired(true);
								} else {
									command.setDataServiceRequired(false);
								}
								
								String date = checkInDateField.getText();
								try {
									command.setCheckInDate(new SimpleDateFormat("dd-MM-yyyy").parse(date));
								}
								catch (Exception ex) {
									checkInDateField.setText(new SimpleDateFormat("dd-MM-yyyy").format(command.getCheckInDate()));
//									JOptionPane.showMessageDialog(container, "The format of the inputted check-in date is invalid", 
//											"Input Error", JOptionPane.INFORMATION_MESSAGE);
									System.err.print("Input Error: The format of the input check-in date is invalid");
									return;
								}
								
								String text = ethernetAddressField.getText();
								if (text.matches("([0-9a-f]{2}:){5}[0-9a-f]{2}")) {
									command.setEthernetAddress(text);
								} else {
//									JOptionPane.showMessageDialog(container, "The format of the inputted ethernet address is invalid", 
//											"Input Error", JOptionPane.INFORMATION_MESSAGE);
									System.err.print("Input Error: The format of the input ethernet address is invalid");
									ethernetAddressField.setText(command.getEthernetAddress());
									return;
								}
								
								String result = command.checkIn();
								if (result.equals("Success")) {
									getCommandValues();
								} else {
//									JOptionPane.showMessageDialog(container, result, 
//									"Input Error", JOptionPane.INFORMATION_MESSAGE);
									System.err.print("Input Error: " + result);
								}
							}
						});
		lowerPanel.add(checkInButton);
	}

	/**
	 * Gets the command values, can be used to update available room table
	 */
	public void getCommandValues() {
		ArrayList list = hotelManager.listAllAvailableRooms();
		availableRooms = list.toArray();
		availableRoomTable.setModel(new AbstractTableModel() {
							String[] headers = {"Room No", "Room Type", "Capacity", "Rate", "Data Service"};
							public int getRowCount() { return availableRooms.length; }
							public int getColumnCount() { return headers.length; }
							public Object getValueAt(int r, int c) { 
								Room room = (Room) availableRooms[r];
								if (room == null) { return null; }
								switch(c) {
									case 0: return room.getFloorNo() + "-" + room.getRoomNo();
									case 1: return room.getTypeString();
									case 2: return new Integer(room.getCapacity());
									case 3: return new Double(room.getRate());
									case 4: if (room.getType() == 1) { return "N/A"; }
										    else { return "Not in used"; }
								}
								return "";
							}
							public String getColumnName(int c) { return headers[c]; }
						});
		Room selectedRoom = command.getSelectedRoom();
		if (selectedRoom != null) {
			int index = list.indexOf(selectedRoom);
			availableRoomTable.setRowSelectionInterval(index, index);
		}
		IDField.setText(command.getID());
		nameField.setText(command.getName());
		typeField.setSelectedItem(command.getType());
		companyField.setText(command.getCompany());
		checkInDateField.setText(new SimpleDateFormat("dd-MM-yyyy").format(command.getCheckInDate()));
		updateDataServiceRequired();
		ethernetAddressField.setText(command.getEthernetAddress());
	}

	/**
	 * Update data service required, standard rooms do not provide data service.
	 */
	public void updateDataServiceRequired() {
		Room room = command.getSelectedRoom();
		if (room == null || room.getType() == Room.STANDARD || typeField.getSelectedItem().equals("Standard")) {
			dataServiceRequiredBox.setEnabled(false);
			ethernetAddressField.setEnabled(false);
			if (dataServiceRequiredBox.getSelectedItem().equals("Yes")) {
				dataServiceRequiredBox.setSelectedItem("No");
			}
			command.setDataServiceRequired(false);
		} else {
			dataServiceRequiredBox.setEnabled(true);
			if (command.isDataServiceRequired()) {
				if (dataServiceRequiredBox.getSelectedItem().equals("No")) {
					dataServiceRequiredBox.setSelectedItem("Yes");
				}
				ethernetAddressField.setEnabled(true);
			} else {
				if (dataServiceRequiredBox.getSelectedItem().equals("Yes")) {
					dataServiceRequiredBox.setSelectedItem("No");
				}
				ethernetAddressField.setEnabled(false);
			}
		}
	}

	/**
	 * Gets the occupant info helper.
	 * @return the occupant info helper
	 */
	public JPanel getOccupantInfoHelper() {
		JPanel occupantInfo = new JPanel();
		occupantInfo.setLayout(new GridLayout(11, 2, 1, 1));
		occupantInfo.add(new JLabel("Occupant Details:")); occupantInfo.add(new JLabel(""));

		occupantInfo.add(new JLabel("ID:"));
		IDField = new JTextField(15);
		occupantInfo.add(IDField);

		occupantInfo.add(new JLabel("Name:"));
		nameField = new JTextField(15);	
		occupantInfo.add(nameField);

		occupantInfo.add(new JLabel("Type:"));
		typeField = new JComboBox(new String[] {"Standard", "Business"});
		typeField.addItemListener(new ItemListener() {
									public void itemStateChanged(ItemEvent e) {
										command.setType((String) typeField.getSelectedItem());
										updateDataServiceRequired();
									}
								});
		typeField.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if (UIManager.getLookAndFeel().getName().equals("Windows")) {
											JFrame f = (JFrame)(typeField.getTopLevelAncestor());
											if (f != null) {
												f.setVisible(false); f.setVisible(true);
											}
										}
									}
								});
		occupantInfo.add(typeField);

		occupantInfo.add(new JLabel("Company:"));
		companyField = new JTextField(15);	
		occupantInfo.add(companyField);

		occupantInfo.add(new JLabel(""));
		occupantInfo.add(new JLabel(""));
		occupantInfo.add(new JLabel("Occupation Details:"));
		occupantInfo.add(new JLabel(""));
		occupantInfo.add(new JLabel("Date of Check-in (dd-MM-yyyy):"));
		checkInDateField = new JTextField(15);	
		occupantInfo.add(checkInDateField);

		occupantInfo.add(new JLabel("Is Data Service Required?"));
		dataServiceRequiredBox = new JComboBox(new String[] {"Yes", "No"});
		dataServiceRequiredBox.addItemListener(new ItemListener() {
									public void itemStateChanged(ItemEvent e) {
										if (dataServiceRequiredBox.getSelectedItem().equals("Yes")) {
											command.setDataServiceRequired(true);
										} else {
											command.setDataServiceRequired(false);
										}
										updateDataServiceRequired();
									}
								});
		dataServiceRequiredBox.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										if (UIManager.getLookAndFeel().getName().equals("Windows")) {
											JFrame f = (JFrame)(dataServiceRequiredBox.getTopLevelAncestor());
											if (f != null) {
												f.setVisible(false); f.setVisible(true);
											}
										}
									}
								});
		occupantInfo.add(dataServiceRequiredBox);

		occupantInfo.add(new JLabel("EthernetAddress:"));
		ethernetAddressField = new JTextField(15);	
		occupantInfo.add(ethernetAddressField);

		return occupantInfo;
	}

	/**
	 * Adds the room info.
	 */
	public void addRoomInfo() {
		JPanel roomInfo = new JPanel();
		roomInfo.setLayout(new BorderLayout());
		roomInfo.add(new JLabel("Available Room List:"), BorderLayout.NORTH);

		availableRoomTable = new JTable();
		roomInfo.add(new JScrollPane(availableRoomTable), BorderLayout.CENTER);			
		add(roomInfo, BorderLayout.CENTER);

		availableRoomTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		final ListSelectionModel model = availableRoomTable.getSelectionModel();
		model.addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent e) {
								int index = model.getMinSelectionIndex();
								if (index != -1) {
									Room room = (Room) availableRooms[index];
									if (room != null) {
										command.setSelectedRoom(room);
										updateDataServiceRequired();
									}
								}
							}
					});
	}
	
}
