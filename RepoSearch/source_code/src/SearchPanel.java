

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

/**
 * This panel holds the search button, input field and the category drop down.
 * @author Kimberly Stewart - last edit: 3/6/2015
 * @author Brandon Lioce - Last edit: 3/7/2015
 *
 */
@SuppressWarnings("serial")
public class SearchPanel extends JPanel {
	/** Drop down category list. */
    private final JComboBox<?> myCategoryDrop;
    
    /** Label for the category list. */
    private final JLabel myCategoryLabel;
    
    /** Field for the keyword to be entered. */
    private final JTextField myKeywordField;
    
    /** Search button. */
    private final JButton mySearchButton;
    
    /** The session panel on which to call search methods. */
    private Session mySession;
    
    /**
     * Constructs a new Search Panel.
     */
    public SearchPanel(final Session theSession) {
    	mySession = theSession;
        myCategoryLabel = new JLabel();
        myCategoryDrop = new JComboBox<String>();
        myKeywordField = new JTextField();
        mySearchButton = new JButton();
        setupComponents();
        setLayout();
        setActions();
    }
    
    /**
	 * Clears the answers and resets to the default on log out/log back in.
	 * @author Kim Stewart
	 */
	public void clear() {
		myKeywordField.setText("");
		repaint();
	}
    
    
    ////////////GUI settings below////////////
    /**
     * This method sets the action listener for the search button.
     * It will read the text entered by the user and the category selected
     * to determine the proper search method to call within the session class.
     * @author Brandon Lioce
     */
    private void setActions() {
    	mySearchButton.addActionListener(new ActionListener() {
    		public void actionPerformed(final ActionEvent theEvent) {
    			String category = ((String) myCategoryDrop.getSelectedItem());
    			String text = myKeywordField.getText();
    			//Input validation
                //Checking for unnecessary spaces at the end of the input.
                if(!text.equals("")) {
                        while (text.charAt(text.length() - 1) == ' ') {
                                text = text.substring(0, text.length() - 2);
                        }
                }
    			boolean isPhrase = containsSpace(text);
    			if(isPhrase && category.equals("All")) {
    				mySession.searchByPhrase(text);
    			} else if(isPhrase && !category.equals("All")) { 
    				mySession.searchByPhraseCategory(category, text);
    			} else if(category.equals("All") && text.length() > 0) {
    				mySession.searchByKeyword(text);
    			} else if(text.length() == 0 && !category.equals("All")) {
    				mySession.searchByCategory(category);
    			} else if(text.length() > 0 && !category.equals("All")) {
    				mySession.searchByKeyCategory(category, text);
    			} else if(text.length() == 0 && category.equals("All")) {
    				mySession.searchAll();
    			}
    			myKeywordField.selectAll();
    		}
    	});
    	
    	// When the user presses enter in the text field, trigger action
    	// to the search button.
    	myKeywordField.addActionListener(new ActionListener() {
    		public void actionPerformed(final ActionEvent theEvent) {
    			mySearchButton.doClick();
    		}

    	});
    	
    	// When the user selects a category, trigger the search button
    	myCategoryDrop.addActionListener(new ActionListener() {
    		public void actionPerformed(final ActionEvent theEvent) {
    			mySearchButton.doClick();
    		}
    	});
    }

    /**
     * This is a helper method to determine if a string contains a space.
     * This will be useful when determining if text entered shall be used 
     * as a keyword or as a phrase.
     * @author Brandon Lioce
     * @param theString The string the user entered.
     * @return Returns if the string has a space or not.
     */
    private boolean containsSpace(final String theString) {
    	// If the string has less than 3 chars, it's probably not a phrase
    	if(theString.length() < 3) return false;
    	for(int i = 0; i < theString.length(); i++) {
    		if(theString.charAt(i) == ' ') return true;
    	}
    	return false;
    }
    
    /**
     * Sets up the components for this panel.
     * @author Kim Stewart
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void setupComponents() {
        myCategoryLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        myCategoryLabel.setText("Category: ");
        myCategoryDrop.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        myCategoryDrop.setModel(new DefaultComboBoxModel(new String[] { "All", "Background", "History", "Operations", "Ownership", "Regulatory/Legal", "Staff" }));
        mySearchButton.setText("Search");
    }
    
    /**
     * Sets up the layout for this panel.
     * @author Kim Stewart - date: 3/6/15
     */
    private void setLayout(){
        GroupLayout searchPaneLayout = new GroupLayout(this);
        this.setLayout(searchPaneLayout);
        searchPaneLayout.setHorizontalGroup(
            searchPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(searchPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(searchPaneLayout.createSequentialGroup()
                        .addComponent(myKeywordField, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mySearchButton))
                    .addGroup(searchPaneLayout.createSequentialGroup()
                        .addComponent(myCategoryLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(myCategoryDrop, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        searchPaneLayout.setVerticalGroup(
            searchPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, searchPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(myCategoryLabel)
                    .addComponent(myCategoryDrop, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(searchPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(myKeywordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(mySearchButton)))
        );
        
    }

}
