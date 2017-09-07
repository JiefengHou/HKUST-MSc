package hms.gui;

import hms.command.Command;
import hms.command.ManageRoomCommand;
import hms.main.HotelManager;
import hms.model.Room;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
//import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

/**
 * The Class ManageRoomPanel encapsulates GUI elements for managing rooms.
 */
public class ManageRoomPanel extends JPanel{
	
	public ManageRoomCommand command;
	
	public HotelManager hotelManager;
	
	public JPanel editableRoomsPanel;
	
	public JPanel controlPanel;
	
	public JTable editableRoomsTable;
	
	public Object[] editableRooms;
	
	public JLabel floorNo;
	
	public JLabel roomNo;
	
	public JLabel roomType;
	
	public JLabel capacity;
	
	public JTextField rate;
	
	public JTextField discount;
	
	public JPanel roomInfoPanel;
	
	public JButton editBtn;
	
	public JButton updateBtn;
	
	public JButton cancelBtn;
	
	
	/**
	 * Instantiates a new manage room panel.
	 * @param cmd the manage room command
	 * @param hm the hotel manager
	 */
	public ManageRoomPanel(Command cmd, HotelManager hm){
		command = (ManageRoomCommand) cmd;
		hotelManager = hm;
		initPanels();
		addButtonActions();
		
	}
	
	/**
	 * Initiate the panels.
	 */
	public void initPanels(){
		setLayout(new BorderLayout());
		addEditableRooms();
		addRoomInfoPanel();
		
	}
	
	/**
	 * Adds the editable rooms.
	 */
	public void addEditableRooms() {
		
		editableRoomsPanel = new JPanel();
		editableRoomsPanel.setLayout(new BorderLayout());
		editableRoomsPanel.add(new JLabel("Editable Room List:"), BorderLayout.NORTH);

		editableRoomsTable = new JTable();
		editableRoomsPanel.add(new JScrollPane(editableRoomsTable), BorderLayout.CENTER);		
		add(editableRoomsPanel, BorderLayout.CENTER);

		editableRoomsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		final ListSelectionModel model = editableRoomsTable.getSelectionModel();
		model.addListSelectionListener(new ListSelectionListener() {
							public void valueChanged(ListSelectionEvent e) {
								int index = model.getMinSelectionIndex();
								if (index != -1) {
									Room room = (Room) editableRooms[index];
									if (room != null) {
										command.setSelectedRoom(room);
									}
								}
							}
					});
		getCommandValues();
	}
	
	/**
	 * Adds the room info panel.
	 */
	public void addRoomInfoPanel(){
		controlPanel = new JPanel();
		controlPanel.setLayout(new BorderLayout());
		controlPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));

		
		JPanel cmdPanel = new JPanel();
		cmdPanel.setLayout(new GridLayout(4,2,0,1));
		
		cmdPanel.add(new JLabel("You can manage:"));
		cmdPanel.add(new JLabel());
		cmdPanel.add(new JLabel("1. Today's room rate"));
		cmdPanel.add(new JLabel());
		cmdPanel.add(new JLabel("2. Today's room discount"));
		cmdPanel.add(new JLabel());
		
		editBtn = new JButton("Edit Selected Room");
		cmdPanel.add(editBtn);
		cmdPanel.add(new JLabel());
		
		controlPanel.add(cmdPanel, BorderLayout.NORTH);
		add(controlPanel, BorderLayout.EAST);
		
	}
	
	
	
	/**
	 * Gets the command values, can be used to update the editable room table.
	 * @return the command values
	 */
	public void getCommandValues() {
		ArrayList list = hotelManager.listAllAvailableRooms();
		editableRooms = list.toArray();
		editableRoomsTable.setModel(new AbstractTableModel() {
							String[] headers = {"Room No", "Room Type", "Capacity", "Rate", "Data Service"};
							public int getRowCount() { return editableRooms.length; }
							public int getColumnCount() { return headers.length; }
							public Object getValueAt(int r, int c) { 
								Room room = (Room) editableRooms[r];
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
			editableRoomsTable.setRowSelectionInterval(index, index);
		}
	}
	
	/**
	 * Adds the button actions.
	 */
	public void addButtonActions(){
		editBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = editableRoomsTable.getSelectedRow();
				if(row < 0){
//					JOptionPane.showMessageDialog(null, "no room selected!", "Warning", JOptionPane.WARNING_MESSAGE);
					System.out.print("Warning: no room selected!");
				} else{
					editableRoomsTable.setEnabled(false);
					if(roomInfoPanel != null) {
						controlPanel.remove(roomInfoPanel);
						controlPanel.validate();
						controlPanel.repaint();
					}
					
					roomInfoPanel = new JPanel();
					roomInfoPanel.setLayout(new GridLayout(8, 2, 0, 1));
					
					roomInfoPanel.add(new JLabel("Room Details:")); 
					roomInfoPanel.add(new JLabel(""));
					
					roomInfoPanel.add(new JLabel("Floor No:"));
					floorNo = new JLabel();
					roomInfoPanel.add(floorNo);
					
					roomInfoPanel.add(new JLabel("Room No:"));
					roomNo = new JLabel();
					roomInfoPanel.add(roomNo);
					
					roomInfoPanel.add(new JLabel("Room Type:"));
					roomType = new JLabel();
					roomInfoPanel.add(roomType);
					//add listener here or use button to retrive the value
					
					roomInfoPanel.add(new JLabel("Capacity:"));
					capacity = new JLabel();
					roomInfoPanel.add(capacity);
					
					roomInfoPanel.add(new JLabel("Rate:"));
					rate = new JTextField(15);
					roomInfoPanel.add(rate);
					
					roomInfoPanel.add(new JLabel("Discount %:"));
					discount = new JTextField();
					discount.setText("20.0");
					roomInfoPanel.add(discount);
					
					updateBtn = new JButton("Update");
					
					roomInfoPanel.add(updateBtn);
					//reg listener to do
					cancelBtn = new JButton("Cancel");
					
					roomInfoPanel.add(cancelBtn);
					

					//populate the room info
					Room selectedRoom = (Room) editableRooms[row];
					command.setSelectedRoom(selectedRoom);
					floorNo.setText(new Integer(selectedRoom.getFloorNo()).toString());
					roomNo.setText(new Integer(selectedRoom.getRoomNo()).toString());
					String type = selectedRoom.getTypeString();
					roomType.setText(type);
					capacity.setText(new Integer(selectedRoom.getCapacity()).toString());
					
					rate.setText(new Double(selectedRoom.getRate()).toString());
					
					controlPanel.add(roomInfoPanel, BorderLayout.SOUTH);
					controlPanel.validate();
					controlPanel.repaint();
					
					updateBtn.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							//check the validity of rate and discount input
							
							double newRate = -1;
							
							try{
								newRate = Double.parseDouble(rate.getText());
							} catch(Exception e){
//								JOptionPane.showMessageDialog(null, "Rate not correctly set!", "Error", JOptionPane.ERROR_MESSAGE);
								System.err.print("Error: Rate not correctly set!");
								return;
							}
							
							double newDiscount = -1;
							try{
								newDiscount = Double.parseDouble(discount.getText());
							} catch(Exception e){
//								JOptionPane.showMessageDialog(null, "Discount not correctly set!", "Error", JOptionPane.ERROR_MESSAGE);
								System.err.print("Error: Discount not correctly set!");
							}
							
							if(newRate > 0 && newDiscount >= 0 && newDiscount < 100){
								Double finalRate = newRate * (1 - newDiscount/100);
								boolean result = hotelManager.updateRoomRate(command.getSelectedRoom(), finalRate.intValue());
								if(result){
									if(roomInfoPanel != null){
										controlPanel.remove(roomInfoPanel);
										controlPanel.validate();
										controlPanel.repaint();
									}
									getCommandValues();
									editableRoomsTable.setEnabled(true);
								} else{
//									JOptionPane.showMessageDialog(null, "room update failed!", "Error", JOptionPane.WARNING_MESSAGE);
									System.err.print("Error: Room update failed!");
								}
								
							} else{
//								JOptionPane.showMessageDialog(null, "invalid room rate or discount!", "Error", JOptionPane.ERROR_MESSAGE);
								System.err.print("Error: Invalid room rate or discount!");
							}
						}
					});
					
					cancelBtn.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent arg0) {
							if(roomInfoPanel != null) {
								controlPanel.remove(roomInfoPanel);
								controlPanel.validate();
								controlPanel.repaint();
								editableRoomsTable.setEnabled(true);
							}
						}
					});
				}
				
			}
		});
	}
}
