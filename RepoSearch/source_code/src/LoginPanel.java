import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JPasswordField;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * This is the panel that will log in a user.
 * @author Dan Taylor 
 * @author Brandon Lioce
 *
 */
@SuppressWarnings("serial")
public class LoginPanel extends JPanel {
	/**
	 * Holds access to the GUIFrame to access login.
	 */
	private GUIFrame myFrame;
	
	/**
	 * The name field so every method can access the name.
	 */
	private JTextField nameField;
	
	/**
	 * The password field to hold the password.
	 */
	private JPasswordField passwordField;
	
	/**
	 * The login button for logging in.
	 */
	private JButton loginButton;
	
	/**
	 * The print area for error messages.
	 */
	private JTextArea printArea;


	/**
	 * Create the panel.
	 */
	public LoginPanel(GUIFrame frame) {
		myFrame = frame;
		
		/**
		 * This bit of code adds the name to the panel
		 */
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
		JLabel nameLabel = new JLabel("Name: ");
		
		namePanel.add(nameLabel);
		nameField = new JTextField();
		nameField.setColumns(10);
		namePanel.add(nameField);
		
		
		
		/**
		 * This is the Pin panel where the stuff for the pin is held.
		 */
		JPanel pinPanel = new JPanel();
		pinPanel.setLayout(new BoxLayout(pinPanel, BoxLayout.X_AXIS));
		JLabel pinLabel = new JLabel("Pin:     ");
		
		pinPanel.add(pinLabel);
		passwordField = new JPasswordField();
		
		// This allows us to hit enter and have it login without
		// having to click the login button.
		// Brandon Lioce - 3/9/2015
		passwordField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent theEvent) {
				loginButton.doClick();
			}
		});		
		
		
		pinPanel.add(passwordField);
		
		loginButton = new JButton("Login");
		
		loginButton.addActionListener(new LoginListener());
		
		loginButton.setAlignmentX(CENTER_ALIGNMENT);
		
		printArea = new JTextArea();
		
		printArea.setOpaque(false);
		
		printArea.setForeground(Color.RED);
		
		// Doesn't allow the user to type in the message print area
		printArea.setEditable(false);
		
		JPanel panel = new JPanel();
		panel.setOpaque(true);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
		panel.add(namePanel);
		panel.add(pinPanel);
		panel.add(Box.createRigidArea(new Dimension(0, 10)));
		panel.add(loginButton);
		panel.add(Box.createRigidArea(new Dimension(0, 10)));
		panel.add(printArea);
		ImageIcon logoIcon = new ImageIcon("gbl_globe.png");
		Image iconImage = logoIcon.getImage();
		Image resize = iconImage.getScaledInstance(75, 75,
				java.awt.Image.SCALE_SMOOTH);
		logoIcon = new ImageIcon(resize);
		
		JLabel logoPanel = new JLabel("", logoIcon, JLabel.CENTER);
		logoPanel.setAlignmentX(CENTER_ALIGNMENT);
		panel.add(logoPanel);
		add(panel);

	}
	
	/**
	 * This method has been added to change the display of the login panel
	 * when the user selects the log out button. I tried creating a new
	 * login panel although but for some reason, the GUI was not having it. 
	 * @author Brandon Lioce
	 */
	public void clear() {
		passwordField.setText("");
		printArea.setText("Successfully logged out");
		nameField.selectAll();
		
	}
	
	/**
	 * This is the action listener looking for the click on the button.
	 * @author Dan
	 *
	 */
	private  class LoginListener implements ActionListener {

		/**
		 * When the button is pressed it checks if the pin is an integer, then it checks if we have a correct login.
		 */
		@SuppressWarnings("deprecation")
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			int pin;
			
			try {
				pin = Integer.parseInt(passwordField.getText());
			}
			catch (Exception e){
				printArea.setText("Pin must be an Integer");
				passwordField.selectAll();
				pin = 0;
			}
			
			if (myFrame.setuser(nameField.getText(), pin) && pin != 0) {
				printArea.setText(nameField.getText() + " ");
			}
			else if (pin != 0) {
				printArea.setText("Entries do not match records");
				passwordField.selectAll();
		
			}
			
		}
	}
		
}