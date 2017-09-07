package hms.command;

import hms.gui.TableView;

import javax.swing.JPanel;

/**
 * The Interface Command.
 */
public interface Command 
{
	
	/**
	 * Gets the command panel.
	 * @param view the view
	 * @return the panel
	 */
	public JPanel getPanel(TableView view);
}
