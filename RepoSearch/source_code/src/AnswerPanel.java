

import java.awt.Font;
import java.util.*;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;

/**
 * Panel that takes the results and displays them in a field.
 * Uses Observer pattern.
 * @author Kimberly Stewart - last edit: 3/6/2015
 * @author Jacob Peterson - last edit: 3/8/2015
 *
 */
@SuppressWarnings("serial")
public class AnswerPanel extends JPanel implements Observer {

	/** Label for the results display */
	private JLabel myResultsLabel;

	/** Text area for results */
	private JTextArea myResultsTextArea;

	/** Display pane that scrolls. */
	private JScrollPane myResultDisplay;

	/** The default of no search results. */
	private final String myDefaultText;

	/** The result text. */
	private String myResultText;

	/**
	 * Constructs a new answer panel.
	 */
	public AnswerPanel() {
		myDefaultText = "0 results found.";
		myResultText = myDefaultText;
		setupComponents();
		setLayout();
	}

	/**
	 * Updates the text area when new search results are loaded.
	 * @author Brandon Lioce
	 * @author Jacob Peterson
	 * @author Kim Stewart - minor: in testing
	 */
	@Override
	public void update(Observable o, Object arg) {
		// Brandon Lioce - 3/9/2015
		myResultText = new String();
		@SuppressWarnings("unchecked")
		List<String> answers = ((List<String>) arg);
		if(!answers.isEmpty()) {
			for(String s: answers) {
			myResultText += s + "\n";
			}
		} else {
			myResultText = myDefaultText;
		}		
		myResultsTextArea.setText(myResultText);
		
		// This should repaint the text display with new text.
		repaint();

	}
	
	/**
	 * Clears the answers and resets to the default on log out/log back in.
	 * @author Kim Stewart
	 */
	public void clear() {
		myResultText = myDefaultText;
		myResultsTextArea.setText(myResultText);
		repaint();
	}

	////////////GUI settings below.
	/**
	 * Sets up the components of the panel.
	 * @author Kim Stewart - date: 3/6/15
	 */
	private void setupComponents() {
		myResultsLabel = new JLabel();
		myResultDisplay = new JScrollPane();
		myResultsTextArea = new JTextArea();

		myResultsLabel.setFont(new Font("Tahoma", 0, 18)); // NOI18N
		myResultsLabel.setText("Search results:");

		myResultsTextArea.setEditable(false);
		myResultsTextArea.setLineWrap(true);
		myResultsTextArea.setWrapStyleWord(true);
		myResultsTextArea.setColumns(20);
		myResultsTextArea.setRows(5);
		myResultsTextArea.setText(myResultText);
		myResultDisplay.setViewportView(myResultsTextArea);
	}

	/**
	 * Set the layout of the panel.
	 * @author Kim Stewart - date: 3/6/15
	 */
	private void setLayout() {
		GroupLayout answerPaneLayout = new GroupLayout(this);
		this.setLayout(answerPaneLayout);
		answerPaneLayout
				.setHorizontalGroup(answerPaneLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								answerPaneLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												answerPaneLayout
														.createParallelGroup(
																GroupLayout.Alignment.LEADING)
														.addGroup(
																answerPaneLayout
																		.createSequentialGroup()
																		.addComponent(
																				myResultsLabel)
																		.addGap(0,
																				0,
																				Short.MAX_VALUE))
														.addComponent(
																myResultDisplay,
																GroupLayout.DEFAULT_SIZE,
																488,
																Short.MAX_VALUE))
										.addContainerGap()));
		answerPaneLayout.setVerticalGroup(answerPaneLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING)
				.addGroup(
						answerPaneLayout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(myResultsLabel)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(myResultDisplay)
								.addContainerGap()));
	}

}
