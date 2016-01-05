

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit test for Session class.
 * @author Kim Stewart - Last edit 3/19/15
 * @author Brandon Lioce - Last edit 3/18/15
 *
 */
public class SessionTest {
	private static Session s;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		s = new Session();
	}
	
	/**
	 * This tests the single results of the keyword: Woman
	 * Matches results too a string.
	 * Author: Kim S.
	 * Date: 3/13/15
	 */
	@Test
	public void testSearchByKeywordWoman() {
		String keyword = "woman";
		String testAnswer = "\"Global Business Logistics actively solicits diversity in our service providers. The spirit of these efforts is to help emerging firms in the industry gain a greater presence while at the same time adding diversity among providers.\"\n";
		//System.out.println(testAnswer);
		s.searchByKeyword(keyword);
		//List<String> actualAnswerList = s.searchResults; //For if there was more than one result.
		String actual = s.searchResults.get(0);
		assertTrue(testAnswer.equals(actual));
		//assertTrue(actual.contains(testAnswer));
	}
	
	/**
	 * This tests the multiple results of the keyword: Philosopy
	 * Matches results to a string.
	 * Author: Kim S.
	 * Date: 3/18/15
	 */
	@Test
	public void testSearchByKeywordPhilosophy() {
		String keyword = "philosophy";
		String testAnswer = "\"Global Business Logistics's philosophy is when our "
				+ "customers need consolidation and forwarding of air or ocean "
				+ "freight customs brokerage cargo insurance order management "
				+ "warehousing or distribution we make it our business to provide "
				+ "the best customized logistics solutions and results.\"\n"
				+ "\"Global Business Logistics maintains a corporate wide code of ethics.  "
				+ "Disclosure of conflicts is designed to comply with appropriate legal and "
				+ "regulatory standards and Global Business Logistics's internal legal and "
				+ "compliance staff reviews disclosures regularly.  More importantly full and "
				+ "fair disclosure is important to our clients and prospects in choosing providers.  "
				+ "Global Business Logistics also believes in complete transparency of compensation "
				+ "and has been an industry proponent in this effort. The Managing Potential "
				+ "Conflicts of Interest section  from Global Business Logistics\'s Code of "
				+ "Ethics is included with this response.\"\n";
		//System.out.println(testAnswer);
		s.searchByKeyword(keyword);
		//List<String> actualAnswerList = s.searchResults; //For if there was more than one result.
		String actual = s.searchResults.get(0);
		actual += s.searchResults.get(1);
		//System.out.println(actual);
		assertTrue(testAnswer.equals(actual));
		//assertTrue(testAnswer.contains(actual));
		//assertTrue(actual.contains(testAnswer));
	}

	/**
	 * This tests a phrase to see if the proper answer is returned.
	 * @author Brandon Lioce 3/18/2015
	 */
	@Test
	public void testSearchByPhrase() {
		String phrase = "Provide a brief overview of your company and history of your organization.";
		String answer = "\"Established in 1979 Global Business Logistics is headquartered in Seattle "
				+ "Washington and provides logistics and freight forwarding services throughout the world.\"\n";
		s.searchByPhrase(phrase);
		String actual = s.searchResults.get(0);
		assertTrue(answer.equals(actual));
	}

	/**
	 * This method will test various keyword/category possibilities. I have examined the 
	 * answers.csv file to determine how many answers there should be. 
	 * @author Dan Taylor
	 */
	@Test
	// Dan Taylor will implement.
	public void testSearchByKeyCategory1() {
		s.searchByKeyCategory("Background", "Staff"); // This should have 1 answer.
		assertTrue(s.searchResults.size() == 1);
		
	}
	
	/**
	 * This method will test various keyword/category possibilities. I have examined the 
	 * answers.csv file to determine how many answers there should be. 
	 * @author Dan Taylor
	 */
	@Test
	public void testSearchByKeyCategory2() {
		s.searchByKeyCategory("history", "company"); // This should have 3 answers
		assertTrue(s.searchResults.size() == 3);
		
	}
	
	/**
	 * This method will test various keyword/category possibilities. I have examined the 
	 * answers.csv file to determine how many answers there should be. 
	 * @author Dan Taylor
	 */
	@Test
	public void testSearchByKeyCategory3() {
		s.searchByKeyCategory("Job", "people"); // Job is not a category should have 0 answers
		assertTrue(s.searchResults.size() == 0);
	}

	/**
	 * This method will test various keyword/category possibilities. I have examined the 
	 * answers.csv file to determine how many answers there should be. 
	 * @author Dan Taylor
	 */
	@Test
	public void testSearchByKeyCategory4() {
		
		s.searchByKeyCategory("operations", "location"); // Location is not a keyword in operations category
		assertTrue(s.searchResults.size() == 0);
		
	}
}
