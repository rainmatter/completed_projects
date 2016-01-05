import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests the user class
 * @author Jacob Peterson
 *
 */
public class UserTest {
	
	private static User usrJ;
	private static User usrB;
	private static User usrA;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		usrJ = new User("Jacob", 1234, true);
		usrA = new User("Alan", 1101, false);
	}

	/**
	 * Tests the Authenticate method
	 */
	@Test
	public void testAuthenticate() {
		assertTrue(usrJ.authenticate("Jacob", 1234));
		assertFalse(usrJ.authenticate("Jacob", 1235));
		
		assertTrue(usrA.authenticate("Alan", 1101));
		assertFalse(usrA.authenticate("Jacob", 1234));
	}
	
	/**
	 * Tests the isAdmin method
	 */
	@Test
	public void testIsAdmin() {
		assertTrue(usrJ.isAdmin());
		assertFalse(usrA.isAdmin());
	}
	
	@Test
	public void testGetName() {
		assertTrue(usrJ.getName().equals("Jacob"));
		assertTrue(usrA.getName().equals("Alan"));
		
		assertFalse(usrA.getName().equals("Jacob"));
	}
	
	/**
	 * Tests the toString method
	 */
	@Test
	public void testToString() {
		String stringJ = "Jacob 1234 ";
		String stringA = "Alan 1101 ";
		assertTrue(usrJ.toString().equals(stringJ));
		assertTrue(usrA.toString().equals(stringA));
	}

}
