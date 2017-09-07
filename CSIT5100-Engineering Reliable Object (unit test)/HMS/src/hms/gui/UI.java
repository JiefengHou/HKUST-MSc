package hms.gui;

import hms.command.CheckInCommand;
import hms.command.CheckOutCommand;
import hms.command.Command;
import hms.command.ManageRoomCommand;
import hms.command.SearchCommand;
import hms.main.HotelManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

/**
 * The Class UI models the main GUI of the HMS system.
 */
public class UI extends JFrame
{
	public HotelManager hotelManager;

	public JMenuBar menuBar;
	
	public JPanel toolBarPanel;

	public JCheckBox checkIn, checkOut, search, manageRooms;
	
	public JMenu lookAndFeelMenu;

	public TableView tableView;
	
	public Command currentCommand;

	/**
	 * Instantiates a new ui.
	 * @param hotelManager the hotel manager
	 */
	public UI(HotelManager hotelManager) {
		super("Hotel Management System");

		this.hotelManager = hotelManager;

		// Construct the menu
		constructMenu();

		// Construct the tool bar
		constructToolBar();

		// Add window Listener
		addWindowListener(new WindowAdapter() {
							public void windowClosing(WindowEvent e) {
								//store things to database to do
								System.exit(0);
							}
						});

		// Initial views and commands
		initializeViewAndCommand();

		pack();
		this.setPreferredSize(new Dimension(1080, 720));
		show();
	}

	/**
	 * Initialize view and command.
	 */
	public void initializeViewAndCommand() {
		tableView = new TableView(hotelManager);

		checkIn.setSelected(true);
		currentCommand = new CheckInCommand(hotelManager);

		updateMainPanel();		
	}

	/**
	 * Update main panel.
	 */
	public void updateMainPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(toolBarPanel, BorderLayout.NORTH);
		panel.add(tableView.accept(currentCommand), BorderLayout.CENTER);
		setContentPane(panel);
		invalidate();
		validate();
		this.repaint();
	}

	/**
	 * Construct menu.
	 */
	public void constructMenu() {
		menuBar = new JMenuBar();
		menuBar.setBorder(new BevelBorder(BevelBorder.RAISED));

		lookAndFeelMenu = new JMenu("Change Theme");
		menuBar.add(lookAndFeelMenu);
		
		lookAndFeelMenu.add(new AbstractAction("Metal", null) {
					public void actionPerformed(ActionEvent e) {
						try {
							UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
							SwingUtilities.updateComponentTreeUI(UI.this);
							setVisible(false); setVisible(true);
						}
						catch (Exception ex) { }
					}
				});
		lookAndFeelMenu.add(new AbstractAction("Motif", null) {
					public void actionPerformed(ActionEvent e) {
						try {
							UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
							SwingUtilities.updateComponentTreeUI(UI.this);
							setVisible(false); setVisible(true);
						}
						catch (Exception ex) { }
					}
				});
		this.setJMenuBar(menuBar);
	}

	/**
	 * Construct tool bar.
	 */
	public void constructToolBar() {
		toolBarPanel = new JPanel();
		getContentPane().add(toolBarPanel, BorderLayout.NORTH);
		toolBarPanel.setLayout(new GridLayout(1,2, 0, 0));
		
		addButtonsForCommandSelection();
	}

	/**
	 * Adds the buttons for command selection.
	 */
	public void addButtonsForCommandSelection() {
		JToolBar commandToolbar = new JToolBar();
		
		// Construct buttons for command selection.
		ButtonGroup commandGroup = new ButtonGroup();
		toolBarPanel.add(commandToolbar);

		// Check In
		checkIn = new JCheckBox("Check In");
		commandToolbar.add(checkIn);
		checkIn.setSelected(true);
		commandGroup.add(checkIn);
		checkIn.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent e) {
									currentCommand = new CheckInCommand(hotelManager);
									updateMainPanel();					
							}
						});	

		// Check Out		
		checkOut = new JCheckBox("Check Out");
		commandToolbar.add(checkOut);
		commandGroup.add(checkOut);
		checkOut.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent e) {
									currentCommand = new CheckOutCommand(hotelManager);
									updateMainPanel();					
							}
						});	

		// Search
		search = new JCheckBox("Search");
		search.setSelected(false);
		commandToolbar.add(search);
		commandGroup.add(search);
		search.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent e) {
									currentCommand = new SearchCommand(hotelManager);
									updateMainPanel();					
							}
						});
		
		// manage rooms
		manageRooms = new JCheckBox("Manage Rooms");
		search.setSelected(false);
		commandToolbar.add(manageRooms);
		commandGroup.add(manageRooms);
		manageRooms.addItemListener(new ItemListener() {
							public void itemStateChanged(ItemEvent e) {
									currentCommand = new ManageRoomCommand();
									updateMainPanel();
							}
						});

		commandToolbar.setFloatable(false);
	}
}