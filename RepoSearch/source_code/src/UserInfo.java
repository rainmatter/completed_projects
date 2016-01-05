

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * This class will hold the registered users for the software. In further iterations it will also
 * add users to the registered user list.
 * 
 * @author Dan Taylor
 *
 */
public class UserInfo {
	
	/**
	 * This field will hold the set of users uploaded from file.
	 * 
	 */
	private List<User> myUsers;
	
	/**
	 * Default file for the user information.
	 */
	private final static String DEFAULT_FILE = "users.txt";

	/**
	 * This constructor will probably be the default constructor.
	 */
	public UserInfo() {
		this(DEFAULT_FILE);
	}
	
	/**
	 * This constructor is set up for testing so that the tester can make up their own file.
	 * @param file The file containing the user data.
	 */
	public UserInfo(final String file) {
		myUsers = new LinkedList<User>();
		populateUsers(file);
		
	}
	
	/**
	 * This method populates the users from the file into active memory
	 * @param file Name of the file with the active users.
	 */
	private void populateUsers(String file) {
		try {
			File read = new File(file);
			Scanner input = new Scanner(read);
			while (input.hasNext()) {
				String name = input.next();
				int pin = input.nextInt();
				int admin = input.nextInt();
				boolean adminCheck = admin == 1;
				myUsers.add(new User(name, pin, adminCheck));
				
			}
			input.close();
		}
		// In case the file cannot be read, an Admin user will be created.
		catch (Exception e) {
			
			myUsers.add(new User("admin", 1234, true));
		}
		
	}

	/**
	 * This method checks the Users stored to see if any are the user trying to currently log in.
	 * @param name Input name of the user to log in.
	 * @param pin Input pin of the user to log in.
	 * @return The user object for the user logged in, or null if a user was not found.
	 */
	public User authenticateUser(final String name, final int pin) {
		User results = null;
		for(User u : myUsers) {
			if (u.authenticate(name, pin)) results = u;
		}
		return results;
	}


}

