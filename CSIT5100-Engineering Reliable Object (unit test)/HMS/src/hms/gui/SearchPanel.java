package hms.gui;

import hms.command.Command;
import hms.command.SearchCommand;
import hms.model.Occupant;
import hms.model.Occupation;
import hms.model.Room;

import java.awt.BorderLayout;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.table.AbstractTableModel;

/**
 * The Class SearchPanel encapsulates GUI elements for searching functions.
 */
public class SearchPanel extends JPanel {
	
	public SearchCommand command;
	
	public JTextField searchField;
	
	public JComboBox typeField;
	
	public JButton searchButton;
	
	public JTable searchRoomTable;
	
	public Object[] searchResults = new Room[0];

	/**
	 * Instantiates a new search panel.
	 * @param cmd the search command
	 */
	public SearchPanel(Command cmd) {
		command = (SearchCommand) cmd;
		setLayout(new BorderLayout());
		addSearchInfo();
		addRoomInfo();
		getCommandValues();
	}

	/**
	 * Adds the search info.
	 */
	public void addSearchInfo() {	
		JPanel wholePanel = new JPanel();
		add(wholePanel, BorderLayout.NORTH);
		wholePanel.setLayout(new GridLayout(1, 2, 1, 1));
		wholePanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		
		JPanel searchInfo = new JPanel();
		JPanel innerPanel = new JPanel();
		innerPanel.add(searchInfo);
		wholePanel.add(innerPanel);
		searchInfo.setLayout(new GridLayout(2, 2, 1, 1));

		searchInfo.add(new JLabel("Search Field:"));
		searchField = new JTextField(15);
		searchInfo.add(searchField);

		searchInfo.add(new JLabel("Type:"));
		typeField = new JComboBox(new String[] {"ID", "Name", "Type", "Company"});
		typeField.addItemListener(new ItemListener() {
									public void itemStateChanged(ItemEvent e) {
										command.setType((String) typeField.getSelectedItem());
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
		searchInfo.add(typeField);

		JToolBar searchToolbar = new JToolBar();
		wholePanel.add(searchToolbar);

		searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								command.setSearchField(searchField.getText());
								command.setType((String) typeField.getSelectedItem());
								ArrayList result = command.search();
								searchResults = result.toArray();
								command.setSearchResults(result.toArray());
								getCommandValues();
							}
						});
		searchToolbar.add(searchButton);
	}

	/**
	 * Adds the room info.
	 */
	public void addRoomInfo() {
		JPanel roomInfo = new JPanel();
		roomInfo.setLayout(new BorderLayout());
		roomInfo.add(new JLabel("Searched Room List:"), BorderLayout.NORTH);

		searchRoomTable = new JTable();
		roomInfo.add(new JScrollPane(searchRoomTable), BorderLayout.CENTER);			
		add(roomInfo, BorderLayout.CENTER);
	}
	
	/**
	 * Gets the command values, can be used to update the search result table.
	 * @return the command values
	 */
	public void getCommandValues() {
		searchField.setText(command.getSearchField());
		typeField.setSelectedItem(command.getType());
		Object[] results = command.getSearchResults();
		if (results != null) {
			searchResults = results;
		}

		searchRoomTable.setModel(new AbstractTableModel() {
							String[] headers = {"Room No", "Room Type", "Capacity", "Rate", "Data Service", "Ethernet Address",
											"Occupant Name", "Occupant Type", "Member ID", "Company", "Check-in Date"};
							public int getRowCount() { return searchResults.length; }
							public int getColumnCount() { return headers.length; }
							public Object getValueAt(int r, int c) { 
								Room room = (Room) searchResults[r];
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
	}
}	

