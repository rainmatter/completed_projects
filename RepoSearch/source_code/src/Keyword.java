
import java.util.*;

/**
 * Keyword class provides access to an array of answers associated 
 * with a specific keyword.
 * 
 * @author Christopher Helmer
 *
 */
public class Keyword {
	
	/** String representation of the this keyword */
	public String keyword;
	
	/** Array of answers associated with this keyword */
	public List<String> answers;
	
	/**
	 * Constructor for the Keyword class
	 * @param aKeyword is the name of this keyword
	 */
	public Keyword(final String aKeyword) {
		this.keyword = aKeyword;
		this.answers = new ArrayList<String>();
	}
	
	/**
	 * Get the array of answers that are associated with this keyword
	 * @return the array of answers that are associated with this keyword
	 */
	public List<String> getAnswers() {
		return this.answers;
	}
}
