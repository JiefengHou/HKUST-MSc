package hms.command;
import hms.gui.TableView;
import hms.main.HotelManager;

import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * The Class SearchCommand encapsulates functions for searching check-in/check-out records.
 */
public class SearchCommand implements Command {
	
	public HotelManager hotelManager;
	
	public String searchField = "";
	
	public String type = "ID";
	
	public Object[] searchResults = null;
	
	/**
	 * Instantiates a new search command.
	 * @param hotelManager the hotel manager
	 */
	public SearchCommand(HotelManager hotelManager) {
		this.hotelManager = hotelManager;
	}

	/**
	 * get the search panel
	 */
	public JPanel getPanel(TableView view) {
		return view.searchPanel();
	}

	/**
	 * Gets the search field.
	 * @return the search field
	 */
	public String getSearchField() { return searchField; }
	
	/**
	 * Gets the search field type. Possible types are: customer ID, customer name, customer's company and room type
	 * @return the type
	 */
	public String getType() { return type; }
	
	/**
	 * Gets the search results.
	 * @return the search results
	 */
	public Object[] getSearchResults() { return searchResults; }

	/**
	 * Sets the search field.
	 * @param searchField the new search field
	 */
	public void setSearchField(String searchField) { this.searchField = searchField; }
	
	/**
	 * Sets the search field type.
	 * @param type the new type
	 */
	public void setType(String type) { this.type = type; }
	
	/**
	 * Sets the search results.
	 * @param searchResults the new search results
	 */
	public void setSearchResults(Object[] searchResults) { this.searchResults = searchResults; }

	/**
	 * Search function.
	 * @return the array list of search results
	 */
	public ArrayList search() {
		return hotelManager.findOccupant(type, searchField);
	}
}