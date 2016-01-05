

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Session contains all the searchable lists and implements all the 
 * searching methods.
 * @author Christopher Helmer - last edit: 17 March 2015
 *
 */
public class Session extends Observable {

	/** Array of answers for the default search.*/
	public List<String> answers;
	
	/** Array of Keyword objects to search by keyword.*/
	public List<Keyword> keywords;
	
	/** Array of Category objects to search by category and to search by keyword within a category.*/
	public List<Category> categories;
	
	/** Array that holds the current search results.*/
	public List<String> searchResults;
	
	/**
	 * Constructor for the Session class.
	 * @throws FileNotFoundException 
	 * @author Christopher Helmer
	 */
	public Session()  {
		answers = new ArrayList<String>();
		keywords = new ArrayList<Keyword>();
		categories = new ArrayList<Category>();
		searchResults = new ArrayList<String>();
		populate();
	}

	/**
	 * Populates the Keyword, Category, and Answer lists for quick and easy searching.
	 * @author Christopher Helmer
	 */
	public void populate()  {
		Scanner input;
		try {
			input = new Scanner(new File("Answers.csv"));
			input.useDelimiter(",");
			while(input.hasNextLine()) {
				//puts the next line into an array split by commas
				String[] line = input.nextLine().split(",");
				//the category from this row of the .csv file
				String temp_cat;
				//a list of keywords from this row of the .csv file
				List<String> temp_keys = new ArrayList<String>();
				//will act as the String representation of the answer from this row
				String temp_answer = "";
				//used to scan through the answer for where we need to add new lines.
				String temp_answer_scanner = "";
				//the category from this line is the first element in the line array
				temp_cat = line[0];
				// all the keywords are from the second element of the line array
				String keys = line[1];
				//we will scan through the line array using * as a delimiter to get 
				//individual keywords.
				StringTokenizer sc = new StringTokenizer(keys);
				//we scan through all the keywords and add them to the temp keyword list
				while(sc.hasMoreTokens()) {
					String tempkey = sc.nextToken("*");
					if(!tempkey.equals("*")) {
						temp_keys.add(tempkey);
					}
				}
				
				//Because there are more commas in this .csv file then there are columns, we 
				//have to copy what's left of the answer into a temp scannable answer
				for(int sp = 2; sp < line.length - 1; sp++) {
					temp_answer_scanner += line[sp];
				}
				
				//This adds a new line to the answer where needed.
				@SuppressWarnings("resource")
				Scanner scan_answer = new Scanner(temp_answer_scanner).useDelimiter(">");
				while(scan_answer.hasNext()) {
					temp_answer += scan_answer.next() + "\n";
				}
				scan_answer.close();
				//add the answer from this line to the answer list
				this.answers.add(temp_answer);
				 
				//Fill the categories
				if(!this.categories.isEmpty()) {
					boolean cat_seen = false;
					for(int j = 0; j < this.categories.size(); j++) {
						if(this.categories.get(j).category.toLowerCase().equals(temp_cat.toLowerCase())) {
							this.categories.get(j).answers.add(temp_answer);
							cat_seen = true;
							for(int i = 0; i < temp_keys.size(); i++) {
								boolean key_seen = false;
								for(int k = 0; k < this.categories.get(j).keywords.size(); k++) {
									if(this.categories.get(j).keywords.get(k).keyword.toLowerCase().equals(temp_keys.get(i).toLowerCase())) {
										boolean seen_answer = false;
										for(String s : this.categories.get(j).keywords.get(k).answers) {
											if(s.equals(temp_answer) || temp_answer.equals(s)) {
												seen_answer = true;
												break;
											}
										}
										if(!seen_answer) {
											this.categories.get(j).keywords.get(k).answers.add(temp_answer);
										}
										key_seen = true;
										break;
									}
								}
								if(!key_seen) {
									Keyword t_k = new Keyword(temp_keys.get(i));
									t_k.answers.add(temp_answer);
									this.categories.get(j).keywords.add(t_k);
								}
							}
							break;
						}
					}
					if(!cat_seen) {
						Category t_cat = new Category(temp_cat);
						t_cat.answers.add(temp_answer);
						Keyword k = new Keyword(temp_keys.get(0));
						k.answers.add(temp_answer);
						t_cat.keywords.add(k);
						for(int i = 1; i < temp_keys.size(); i++) {
							boolean key_seen = false;
							for(int j = 0; j < t_cat.keywords.size(); j++) {
								if(t_cat.keywords.get(j).keyword.toLowerCase().equals(temp_keys.get(i).toLowerCase())) {
									key_seen = true;
									break;
								}
							}
							if(!key_seen) {
								Keyword tk = new Keyword(temp_keys.get(i));
								tk.answers.add(temp_answer);
								t_cat.keywords.add(tk);
							}
						}
						this.categories.add(t_cat);
					}
				} else {
					Category t_cat = new Category(temp_cat);
					t_cat.answers.add(temp_answer);
					Keyword k = new Keyword(temp_keys.get(0));
					k.answers.add(temp_answer);
					t_cat.keywords.add(k);
					for(int i = 1; i < temp_keys.size(); i++) {
						boolean key_seen = false;
						for(int j = 0; j < t_cat.keywords.size(); j++) {
							if(t_cat.keywords.get(j).keyword.toLowerCase().equals(temp_keys.get(i).toLowerCase())) {
								key_seen = true;
								break;
							}
						}
						if(!key_seen) {
							Keyword tk = new Keyword(temp_keys.get(i));
							tk.answers.add(temp_answer);
							t_cat.keywords.add(tk);
						}
					}
					this.categories.add(t_cat);
				}
				
				//Fill the keyword list
				if(!this.keywords.isEmpty()) {
					boolean key_seen = false;
					boolean answer_seen = false;
					for(int i = 0; i < temp_keys.size(); i++) {
						for(int j = 0; j < this.keywords.size(); j++) {
							if(temp_keys.get(i).toLowerCase().equals(this.keywords.get(j).keyword.toLowerCase())) {
								for(int k = 0; k < this.keywords.get(j).answers.size(); k++) {
									if(this.keywords.get(j).answers.get(k).toLowerCase().equals(temp_answer.toLowerCase())) {
										answer_seen = true;
										break;
									}
								}
								if(!answer_seen) {
									this.keywords.get(j).answers.add(temp_answer);
								}
								key_seen = true;
								break;
							} else {
								key_seen = false;
							}
						}
						if(!key_seen) {
							Keyword temp_keyword = new Keyword(temp_keys.get(i));
							temp_keyword.answers.add(temp_answer);
							this.keywords.add(temp_keyword);
						}
					}
				} else {
					for(int i = 0; i < temp_keys.size(); i++) {
						Keyword temp_keyword = new Keyword(temp_keys.get(i));
						temp_keyword.answers.add(temp_answer);
						this.keywords.add(temp_keyword);
					}
				}
			}
			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Searches for a keyword and change the search results list to the list
	 * of answers associated with that keyword if it is found. If search 
	 * results is empty, your search returned no results.
	 * @param aKeyword is the keyword we are looking for.
	 * @author Christopher Helmer
	 */
	public void searchByKeyword(final String aKeyword) {
		this.searchResults.clear();
		//check all keywords 
		for(int i = 0; i < this.keywords.size(); i++) {
			if(this.keywords.get(i).keyword.toLowerCase().contains(aKeyword.toLowerCase())) {
				for(String s : this.keywords.get(i).answers) {
					boolean seen = false;
					for(String st : this.searchResults) {
						if(st.contains(s) || s.contains(st)) {
							seen = true;
							break;
						}
					}
					if(!seen) {
						this.searchResults.add(s);
					}
				}
			}
		}
		setChanged();
		notifyObservers(searchResults);
	}
	
	/**
	 * Search for an answer to a question posed as a phrase
	 * and change the search results list to the list
	 * of answers associated with the keyword found in 
	 * the phrase if it is found. If search results is empty,
	 * your search returned no results.
	 * @param aPhrase is the question we are searching for.
	 * @author Christopher Helmer
	 */
	public void searchByPhrase(final String aPhrase) {
		this.searchResults.clear();
		//check all keywords 
		for(int i = 0; i < this.keywords.size(); i++) {
			if(this.keywords.get(i).keyword.toLowerCase().equals(aPhrase.toLowerCase())) {
				for(String s : this.keywords.get(i).answers) {
					boolean seen = false;
					for(String st : this.searchResults) {
						if(st.contains(s) || s.contains(st)) {
							seen = true;
							break;
						}
					}
					if(!seen) {
						this.searchResults.add(s);
					}
				}
			}
		}
		if(searchResults.isEmpty()) {
			Scanner s = new Scanner(aPhrase);
			while(s.hasNext() && searchResults.isEmpty()) {
				String temp_string = s.next();
				searchByKeyword(temp_string);
			}
			s.close();
		}
		setChanged();
		notifyObservers(searchResults);
	}
	
	/**
	 * Search by category and change the search results list to the list
	 * of answers associated with that category if it is found. If search
	 * results is empty, your search returned no results.
	 * @param aCategory the category to search for.
	 * @author Christopher Helmer
	 */
	public void searchByCategory(final String aCategory) {
		this.searchResults.clear();
		//check all categories for this category
		for(int i = 0; i < this.categories.size(); i++) {
			if(this.categories.get(i).category.toLowerCase().equals(aCategory.toLowerCase())) {
				this.searchResults.addAll(this.categories.get(i).answers);
				break;
			}
		}
		setChanged();
		notifyObservers(searchResults);
	}
	
	/**
	 * Search for a phrase within a category, if you find it, update 
	 * search results with list of answers associated with that phrase 
	 * in this category. If search results are empty, then your search 
	 * returned no results.
	 * @param aCategory is the category we're in when conducting this search.
	 * @param aPhrase is the phrase we are looking for in this search.
	 * @author Christopher Helmer
	 */
	public void searchByPhraseCategory(final String aCategory, final String aPhrase) {
		searchResults.clear();
		for(int i = 0; i < this.categories.size(); i++) {
			boolean found = false;
			if(this.categories.get(i).category.toLowerCase().equals(aCategory.toLowerCase())) {
				for(int j = 0; j < categories.get(i).keywords.size(); j++) {
					if(this.categories.get(i).keywords.get(j).keyword.toLowerCase().equals(aPhrase.toLowerCase())) {
						this.searchResults.addAll(this.categories.get(i).keywords.get(j).answers);
						found = true;
						break;
					}
				}
				if(found) {
					break;
				}
			}
		}
		if(searchResults.isEmpty()) {
			Scanner s = new Scanner(aPhrase);
			while(s.hasNext() && searchResults.isEmpty()) {
				String temp_string = s.next();
				searchByKeyCategory(aCategory, temp_string);
			}
			s.close();
		}
		setChanged();
		notifyObservers(searchResults);
	}
	
	/**
	 * Search for a keyword when in a category. Updates the search results list 
	 * with the answers associated with this keyword in this category. Search results 
	 * are empty if the search returns no results.
	 * @param aCategory is the category we are in when conducting this search.
	 * @param aKeyword is the keyword we are looking for in this search.
	 * @author Christopher Helmer
	 */
	public void searchByKeyCategory(final String aCategory, final String aKeyword) {
		//find the category
		searchResults.clear();
		for(int i = 0; i < this.categories.size(); i++) {
			boolean found = false;
			if(this.categories.get(i).category.toLowerCase().equals(aCategory.toLowerCase())) {
				for(int j = 0; j < categories.get(i).keywords.size(); j++) {
					if(this.categories.get(i).keywords.get(j).keyword.toLowerCase().contains(aKeyword.toLowerCase())) {
						this.searchResults.addAll(this.categories.get(i).keywords.get(j).answers);
						found = true;
						break;
					}
				}
				if(found) {
					break;
				}
			}
		}
		setChanged();
		notifyObservers(searchResults);
	}
	
	/**
	 * Fills the searchResults with all the answers so when you choose "All" from
	 * the category drop down, all the answers are displayed. Also will populate the 
	 * answer panel after login.
	 */
	public void searchAll() {
		searchResults.clear();
		searchResults.addAll(answers);
		setChanged();
		notifyObservers(searchResults);
	}
}
