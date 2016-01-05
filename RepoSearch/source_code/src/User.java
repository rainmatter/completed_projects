/**
 * 
 */


/**
 * This class holds the information for a User.
 * 
 * @author Dan Taylor
 *
 */

public class User {

	/**
	 * Will hold the name of the User.
	 */
	private String myName;
	
	/**
	 * Will hold the pin of the User
	 */
	private int myPin;
	
	/**
	 * Determines if this user is an administrator;
	 * 
	 */
	private boolean myAdmin;
	
	/**
	 * This is the constructor to create a User object.
	 * @param name The name of the User
	 * @param pin The pin of the User
	 * @param admin This is true if the user is an admin, false otherwise.
	 */
	public User(String name, int pin, boolean admin) {
		myName = name;
		myPin = pin;
		myAdmin = admin;
		
	}
	
	/**
	 * This checks to see if the given information describes this user.
	 * @param name Name to be checked
	 * @param pin Piin to be checked.
	 * @return True if this is the user false if not.
	 */
	public boolean authenticate(String name, int pin) {
		return name.equals(myName) && pin == myPin;
	}
	
	/**
	 * This method checks to see if a user is an admin for special privileges
	 * @return True if user is admin, false otherwise.
	 */
	public boolean isAdmin() {
		return myAdmin;
	}
	
	/**
	 * Method to access the User's name.
	 * @return String of user's name
	 */
	public String getName() {
		return this.myName;
	}
	
	/**
	 * This method is for debugging only.
	 */
	public String toString() {
		return myName + " " + myPin + " ";
	}
	
}
