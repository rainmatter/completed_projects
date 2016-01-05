

import java.util.*;

/**
 * Category class provides access to an array of answers associated
 * with a specific categry as well as access to an array of keywords
 * associated with this category.
 * 
 * @author Christopher Helmer
 *
 */
public class Category {

	/** String representation of the category */
	public String category;
	
	/** Array of answers associated with this category */
	public List<String> answers;
	
	/** Array of keywords associated with this category */
	public List<Keyword> keywords;
	
	/**
	 * Constructor for the Category class
	 * @param aCategory is the name of this category
	 */
	public Category(final String aCategory) {	
		this.category = aCategory;
		this.answers = new ArrayList<String>();
		this.keywords = new ArrayList<Keyword>();
	}
	
	/**
	 * Get the answers associated with this category.
	 * @return the array of answers associated with this category.
	 */
	public List<String> getAnswers() {
		return this.answers;
	}
	
	/**
	 * Get the answers associated with this category by searching the 
	 * keywords in this category
	 * @param aKeyword is the keyword to search for within this category
	 * @return the array of answers associated with this category when searching 
	 * for a keyword within this category
	 */
	public List<String> getAnswersByKeyword(Keyword aKeyword) {
		List<String> tempAnswers = new ArrayList<String>();
		for(int i = 0; i < keywords.size(); i++) {
			if(keywords.get(i).keyword == aKeyword.keyword) {
				tempAnswers = keywords.get(i).answers;
			}
		}
		return tempAnswers;
	}
}
