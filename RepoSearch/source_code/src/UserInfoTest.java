import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * JUnit test for UserInfo class.
 * @author Christopher Helmer
 *
 */
public class UserInfoTest {
	
	/** Static instance of UserInfo class */
	private static UserInfo user;
	
	/** Initialize static UserInfo */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		user = new UserInfo();
	}

	/**
	 * Test user jawit
	 */
	@Test
	public void testAuthenticateUserjawit() {
		User test_user = new User("jawit", 7890, true);
		assertTrue(user.authenticateUser("jawit", 7890).getName().equals(test_user.getName()));
	}

	/**
	 * Test user dtaylor
	 */
	@Test
	public void testAuthenticateUserdtaylor() {
		User test_user = new User("dtaylor", 1234, true);
		assertTrue(user.authenticateUser("dtaylor", 1234).getName().equals(test_user.getName()));
	}
	
	/**
	 * Test user helmerc
	 */
	@Test
	public void testAuthenticateUserhelmerc() {
		User test_user = new User("helmerc", 1234, true);
		assertTrue(user.authenticateUser("helmerc", 1234).getName().equals(test_user.getName()));
	}
}
