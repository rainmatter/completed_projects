

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;


/**
 * The main frams and main class of the program.
 * Holds the Session and the GUI panels.
 * @author Kimberly Stewart - last edit: 3/8/2015
 * @author Brandon Lioce - last edit 3/8/2015
 *
 */
@SuppressWarnings("serial")
public class GUIFrame extends JFrame {

	/** The controller that handles the searches for answers. */
	private final Session mySession = new Session();

	/** The JPanel that will display the answers. */
	private final AnswerPanel myAnswerPanel = new AnswerPanel();

	/** The JPanel that will allow a user to login. */
	private LoginPanel myLoginPanel = new LoginPanel(this);

	/** The JPanel that will display a logo & current user. */
	private LogoPanel myLogoPanel = new LogoPanel(this);

	/** The JPanel that will allow a user to search the repository. */
	private final SearchPanel mySearchPanel = new SearchPanel(mySession);

	/** The repository for the user login info. */
	private final UserInfo myUserInfo = new UserInfo();

	/** Stored the current user */
	private User myUser;

	/** The left section panel */
	private JPanel myLeftPanel;

	/** Button to add an entry. */
	private JButton myAddEntryButton;

	/** The label for the admin panel. */
	private JLabel myAdminLabel;

	/** Panel that stored the admin buttons. */
	private JPanel myAdminPanel;

	/**
	 * A constructor that sets up the JFrame.
	 */
	public GUIFrame() {
		// Set the AnswerPanel observer
		mySession.addObserver(myAnswerPanel);
		initializeGUIComponents();
		setVisible(true);
	}

	/**
	 * This method will be called on my the Login panel. The user will enter
	 * their credentials where it will be sent here. From here, they will be
	 * sent to the UserInfo class for authentication.
	 * 
	 * @author Brandon Lioce
	 * @param theUsername
	 *            The username entered by the user.
	 * @param thePin
	 *            The pin number entered by the user.
	 * @return Returns a boolean if credentials had a match.
	 */
	public boolean setuser(final String theUsername, final int thePin) {
		User user = myUserInfo.authenticateUser(theUsername, thePin);
		if (user == null) { // Login was unsuccessful - No match in UserInfo
			
			return false; // LoginPanel will know to ask user to reenter info
		} else {
			myUser = user; // Login was successful
			 
			remove(myLoginPanel); // We no longer need the login panel
			setMainLayout(); // Set up the rest of the interface
			pack(); // Adjust to new content
			setLocationRelativeTo(null);
			myLogoPanel.setUser(myUser);
			return true;
		}
	}
	
	/**
	 * This method will logout the user and bring them
	 * to the original login page.
	 * @author Brandon Lioce - 3/17/15
	 * @author Kim Stewart- Minor: added 2 clear calls.
	 */
	public void logout() {
		this.remove(myLeftPanel);
		this.remove(myAdminPanel);
		this.remove(myLogoPanel);
		this.remove(mySearchPanel);
		this.remove(myAnswerPanel);	
		add(myLoginPanel);
		setSize(new Dimension(209, 246));
		setLocationRelativeTo(null);
		myLoginPanel.clear();
		myAnswerPanel.clear();	//KS
		mySearchPanel.clear();	//KS
	}

	/**
	 * Main method. Starts the program and GUI.
	 * 
	 * @param args
	 *            main argument string
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GUIFrame();
			}
		});

	}

	///////GUI Setup below/////
	/**
	 * @author Kim Stewart - date: 3/6/15
	 * Initializes the GUI components and organizes the panels.
	 */
	private void initializeGUIComponents() {
		// Set general settings of frame.
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("GBL Repository");
		setResizable(false);
		
		ImageIcon myLogoIcon;
		myLogoIcon = new ImageIcon("gbl_icon.png");
		Image img = myLogoIcon.getImage();
		setIconImage(img);
		
		// Create components.
		setupLeftPanel();
		myLogoPanel = new LogoPanel(this);
		
		// Prompt the user to login.
		add(myLoginPanel);
		
		
		repaint();
		
		// Pack components and set to open in center of screen.
		pack();
		System.out.println(this.getWidth() + " " + this.getHeight());
		setLocationRelativeTo(null);

	}

	/**
	 * @author Kim Stewart - date: 3/6/15
	 * Sets the main layout of the frame. Adds the main panel sections.
	 */
	private void setMainLayout() {
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(myLogoPanel, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(myLeftPanel,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(myAnswerPanel,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(myLogoPanel,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING)
												.addComponent(
														myLeftPanel,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														myAnswerPanel,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE))));
	}

	/**
	 * @author Kim Stewart - date: 3/6/15
	 * Sets up the left panel and adds the components.
	 */
	private void setupLeftPanel() {
		// Set up components.
		myLeftPanel = new JPanel();
		setupAdminPanel();
		// Set up layout.
		// Adds the admin & search panels
		GroupLayout leftPaneLayout = new GroupLayout(myLeftPanel);
		myLeftPanel.setLayout(leftPaneLayout);
		leftPaneLayout.setHorizontalGroup(leftPaneLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(mySearchPanel, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(
						leftPaneLayout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(myAdminPanel,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE).addContainerGap()));
		leftPaneLayout.setVerticalGroup(leftPaneLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				leftPaneLayout
						.createSequentialGroup()
						.addComponent(mySearchPanel,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(
								LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(myAdminPanel, GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap()));
	}

	/**
	 * @author Kim Stewart - date: 3/6/15
	 * Sets up the admin panel.
	 */
	private void setupAdminPanel() {
		// Set up components.
		myAdminPanel = new JPanel();
		myAdminLabel = new JLabel();
		myAddEntryButton = new JButton();
		// Font and text of the Administrator Label & button.
		myAdminLabel.setFont(new Font("Tahoma", 0, 14)); // NOI18N
		myAdminLabel.setForeground(new Color(153, 153, 153));
		myAdminLabel.setText("Administrator:");

		myAddEntryButton.setText("Add Entries");
		myAddEntryButton.setEnabled(false);

		// Sets up the layout & adds components.
		GroupLayout adminPaneLayout = new GroupLayout(myAdminPanel);
		myAdminPanel.setLayout(adminPaneLayout);
		adminPaneLayout.setHorizontalGroup(adminPaneLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				GroupLayout.Alignment.TRAILING,
				adminPaneLayout
						.createSequentialGroup()
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.addGroup(
								adminPaneLayout
										.createParallelGroup(
												GroupLayout.Alignment.TRAILING)
										.addComponent(myAddEntryButton)
										.addComponent(myAdminLabel))
						.addGap(69, 69, 69)));
		adminPaneLayout.setVerticalGroup(adminPaneLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				GroupLayout.Alignment.TRAILING,
				adminPaneLayout
						.createSequentialGroup()
						.addContainerGap(282, Short.MAX_VALUE)
						.addComponent(myAdminLabel)
						.addPreferredGap(
								LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(myAddEntryButton,
								GroupLayout.PREFERRED_SIZE, 45,
								GroupLayout.PREFERRED_SIZE).addContainerGap()));
	}

}
