

import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;


/**
 * Displays the company logo.
 * Also holds the user panel with the user info.
 * 
 * @author Kimberly Stewart - last edit: 3/17/15
 * @author Brandon Lioce
 * @author Jacob Peterson
 *
 */
@SuppressWarnings("serial")
public class LogoPanel extends JPanel {
	/** The Logo label-- holds the logo label. */
	private final JLabel myLogoLabel;

	/** Panel that holds the user information. */
	private JPanel myUserPanel;

	/** Label of the User's name and log in date. */
	private JLabel myUserLabel;

	/** Panel that holds the logo. */
	private JPanel myLogoPanel;
	
	/** The reference to the frame to logout. */
	private GUIFrame myFrame;
	
	/** Button that allows user to log out. */
	private JButton myLogoutButton;

	/**
	 * Constructs a new Logo Panel.
	 * @author Kimberly Stewart - last edit: 3/8/15
	 * @author Jacob Peterson - last edit: 3/8/15
	 */
	public LogoPanel(GUIFrame theFrame) {
		myFrame = theFrame;
		myLogoutButton = new JButton();
		ImageIcon myLogoIcon;
		myLogoIcon = new ImageIcon("gbl_logo.png");

		Image img = myLogoIcon.getImage();
		Image resized = img.getScaledInstance(220, 75,
				java.awt.Image.SCALE_SMOOTH);
		myLogoIcon = new ImageIcon(resized);

		myLogoLabel = new JLabel("", myLogoIcon, JLabel.CENTER);
		myUserLabel = new JLabel();
		setLayout();
		// KS: These 2 lines are to test the User label.
		// When users are set, the code below is not needed. Might replace it
		// with default info.
		User useTest = new User("Bob Jones", 3333, false);
		setUser(useTest);
		
		// Allows the user to logout - Brandon Lioce 3/18/2015
		myLogoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent theEvent) {
				myFrame.logout();
			}
		});
	}

	/**
	 * The method takes a user name from the user and displays in on the panel.
	 * Displays who is logged in.
	 * @author Brandon Lioce - last edit: 3/17/15
	 * @author Kimberly Stewart - last edit: 3/8/15
	 * @author Jacob Peterson - last edit: 3/8/15
	 * @param myUser the User that is logged in.
	 */
	public void setUser(User myUser) {
		// JLabel myUserLabel = new JLabel(); //KS: Moved to a field and
		// instantiated in constructor.
		myUserLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		String labelText = "Logged in as ";
		if(myUser.isAdmin()) labelText += "administrator: ";
		else labelText += "analyst: ";
		labelText += myUser.getName() + " since " + dateFormat.format(date);
		myUserLabel.setText(labelText);
		myUserLabel.repaint();
	}

	////////////GUI setup below/////////////
	/**
	 * Adds the components and Sets the layout of the panels.
	 * @author Kim Stewart - date: 3/6/15
	 */
	public void setLayout() {
		myUserPanel = new JPanel();
		myLogoPanel = new JPanel();
		setLogoPanel();
		setUserPanel();

		// Set overall layout of top panel.
		// Adds logo panel and user panel
		GroupLayout headerPaneLayout = new GroupLayout(this);
		this.setLayout(headerPaneLayout);
		headerPaneLayout.setHorizontalGroup(headerPaneLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
						headerPaneLayout
								.createSequentialGroup()
								.addComponent(myLogoPanel,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(myUserPanel,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)));
		headerPaneLayout.setVerticalGroup(headerPaneLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(myLogoPanel, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(myUserPanel, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
	}

	/**
	 * Sets the layout of the Logo area and adds the icon label.
	 * @author Kim Stewart - date: 3/6/15
	 */
	private void setLogoPanel() {
		// Add the layout type.
		GroupLayout logoPaneLayout = new GroupLayout(myLogoPanel);
		myLogoPanel.setLayout(logoPaneLayout);
		// Set the horizontal layout.
		logoPaneLayout.setHorizontalGroup(logoPaneLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				logoPaneLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(myLogoLabel)
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		// Set the vertical layout.
		logoPaneLayout.setVerticalGroup(logoPaneLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				logoPaneLayout.createSequentialGroup().addContainerGap()
						.addComponent(myLogoLabel)));
	}

	/**
	 * Sets the layout of the user area. Adds the user label.
	 * @author Kim Stewart - date: 3/6/15
	 */
	private void setUserPanel() {
		// Set the layout of the User portion of the top panel.
		//Add the logout button.
		myLogoutButton.setFont(new Font("Tahoma", 0, 11));
		myLogoutButton.setMargin(new Insets(2, 8, 2, 8));
        myLogoutButton.setText("Log out");
        
        GroupLayout userPaneLayout = new GroupLayout(myUserPanel);
        myUserPanel.setLayout(userPaneLayout);
        userPaneLayout.setHorizontalGroup(
            userPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, userPaneLayout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(userPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(myUserLabel, GroupLayout.Alignment.TRAILING)
                    .addComponent(myLogoutButton, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        userPaneLayout.setVerticalGroup(
                userPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(userPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(myUserLabel)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(myLogoutButton)
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
	}

}
