package SynId;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.stanford.nlp.trees.TypedDependency;
/**
 * This defines the RelevancyList object primarily used in the implementation of the SynonymIdentifier
 * @author Jeremy Dohmann
 * @author Patrick DeMichele
 */

public class RelevancyList implements Serializable
{
	//Defines the HashMap fields storing all of the words
	public HashMap<String, WordEntry> nounList;
	public HashMap<String, WordEntry> verbList;
	private HashMap<String, WordEntry> adjList;
	private HashMap<String, WordEntry> prepList;
	private HashMap<String, WordEntry> modalList;
	private HashMap<String, WordEntry> advList;
	private HashMap<String, WordEntry> determinerList;
	private HashMap<String, WordEntry> pronounList;
	private HashMap<String, WordEntry> otherList;
	//Contains all of the individual HashMap objects
	public LinkedList<HashMap<String, WordEntry>> totalList;
	
	public WordEntry getWithoutPOS(String str)
	{
		int max = -100;
		ArrayList<WordEntry> word = new ArrayList<>();
		if(contains(str + "/vb"))
		{
			word.add(get(str + "/vb"));
		}
		if(contains(str + "/vbz"))
		{
			word.add(get(str + "/vbz"));

		}
		if(contains(str + "/vbd"))
		{
			word.add(get(str + "/vbd"));

		}
		if(contains(str + "/jj"))
		{
			word.add(get(str + "/jj"));

		}
		if(contains(str + "/vbg"))
		{
			word.add(get(str + "/vbg"));

		}
		if(contains(str + "/vbn"))
		{
			word.add(get(str + "/vbn"));

		}
		if(contains(str + "/vbp"))
		{
			word.add(get(str + "/vbp"));

		}
		if(contains(str + "/nn"))
		{
			word.add(get(str + "/nn"));

		}
		if(contains(str + "/nns"))
		{
			word.add(get(str + "/nns"));

		}
		if(contains(str + "/nnp"))
		{
			word.add(get(str + "/nnp"));

		}
		if(contains(str + "/nnps"))
		{
			word.add(get(str + "/nnps"));

		}
		if(contains(str + "/jjr"))
		{
			word.add(get(str + "/jjr"));

		}
		if(contains(str + "/jjs"))
		{
			word.add(get(str + "/jjs"));

		}
		if(contains(str + "/in"))
		{
			word.add(get(str + "/in"));

		}
		if(contains(str + "/pdt"))
		{
			word.add(get(str + "/pdt"));

		}
		if(contains(str + "/pos"))
		{
			word.add(get(str + "/pos"));

		}
		if(contains(str + "/prp"))
		{
			word.add(get(str + "/prp"));

		}
		if(contains(str + "/prp$"))
		{
			word.add(get(str + "/prp$"));

		}
		if(contains(str + "/rb"))
		{
			word.add(get(str + "/rb"));

		}
		if(contains(str + "/rbr"))
		{
			word.add(get(str + "/rbr"));

		}
		if(contains(str + "/rbs"))
		{
			word.add(get(str + "/rbs"));

		}
		if(contains(str + "/rp"))
		{
			word.add(get(str + "/rp"));

		}
		if(contains(str + "/cc"))
		{
			word.add(get(str + "/cc"));

		}
		if(contains(str + "/cd"))
		{
			word.add(get(str + "/cd"));

		}
		if(contains(str + "/dt"))
		{
			word.add(get(str + "/dt"));

		}
		if(contains(str + "/ex"))
		{
			word.add(get(str + "/ex"));

		}
		if(contains(str + "/fw"))
		{
			word.add(get(str + "/fw"));

		}
		if(contains(str + "/ls"))
		{
			word.add(get(str + "/ls"));

		}
		if(contains(str + "/md"))
		{
			word.add(get(str + "/md"));

		}
		if(contains(str + "/sym"))
		{
			word.add(get(str + "/sym"));

		}
		if(contains(str + "/to"))
		{
			word.add(get(str + "/to"));

		}
		if(contains(str + "/uh"))
		{
			word.add(get(str + "/uh"));

		}
		if(contains(str + "/wdt"))
		{
			word.add(get(str + "/wdt"));

		}
		if(contains(str + "/wp"))
		{
			word.add(get(str + "/wp"));

		}
		if(contains(str + "/wp$"))
		{
			word.add(get(str + "/wp$"));

		}
		if(contains(str + "/wrb"))
		{
			word.add(get(str + "/wrb"));
		}
		
	
		WordEntry ret = null;
		for(WordEntry w : word)
		{
			if(w.totalRelns > max)
			{
				ret = w;
				max = w.totalRelns;
			}

		}

		return ret;
		
		
		
	}
	public boolean containsWithoutPOS(String str)
	{
		str = str.toLowerCase();
		if(contains(str + "/vb"))
		{
			
			return true;
		}
		else if(contains(str + "/vbz"))
		{
			
			return true;
		}
		else if(contains(str + "/cc"))
		{
			return true;
		}
		else if(contains(str + "/cd"))
		{
			return true;
		}
		else if(contains(str + "/dt"))
		{
			return true;
		}
		else if(contains(str + "/ex"))
		{
			return true;
		}
		else if(contains(str + "/fw"))
		{
			return true;
		}
		else if(contains(str + "/in"))
		{
			return true;
		}
		else if(contains(str + "/jj"))
		{
			return true;
		}
		else if(contains(str + "/jjr"))
		{
			return true;
		}
		else if(contains(str + "/jjs"))
		{
			return true;
		}
		else if(contains(str + "/ls"))
		{
			return true;
		}
		else if(contains(str + "/md"))
		{
			return true;
		}
		else if(contains(str + "/nn"))
		{
			return true;
		}
		else if(contains(str + "/nns"))
		{
			return true;
		}
		else if(contains(str + "/nnp"))
		{
			return true;
		}
		else if(contains(str + "/nnps"))
		{
			return true;
		}
		else if(contains(str + "/pdt"))
		{
			return true;
		}
		else if(contains(str + "/pos"))
		{
			return true;
		}
		else if(contains(str + "/prp"))
		{
			return true;
		}
		else if(contains(str + "/prp$"))
		{
			return true;
		}
		else if(contains(str + "/rb"))
		{
			return true;
		}
		else if(contains(str + "/rbr"))
		{
			return true;
		}
		else if(contains(str + "/rbs"))
		{
			return true;
		}
		else if(contains(str + "/rp"))
		{
			return true;
		}
		else if(contains(str + "/sym"))
		{
			return true;
		}
		else if(contains(str + "/to"))
		{
			return true;
		}
		else if(contains(str + "/uh"))
		{
			return true;
		}
		else if(contains(str + "/vbd"))
		{
			return true;
		}
		else if(contains(str + "/vbg"))
		{
			return true;
		}
		else if(contains(str + "/vbn"))
		{
			return true;
		}
		else if(contains(str + "/vbp"))
		{
			return true;
		}
		else if(contains(str + "/wdt"))
		{
			return true;
		}
		else if(contains(str + "/wp"))
		{
			return true;
		}
		else if(contains(str + "/wp$"))
		{
			return true;
		}
		else if(contains(str + "/wrb"))
		{
			return true;
		}
		else
		{
			return false;
		}
		
		
	}
	/**
	 * Constructs the RelevancyList object by initializing the 
	 * HashMaps and ArrayList
	 */
	public RelevancyList()
	{
		//Estimate numbers
		nounList = new HashMap<>();
		verbList = new HashMap<>();
		adjList = new HashMap<>();
		prepList = new HashMap<>();
		modalList = new HashMap<>();
		advList = new HashMap<>();
		determinerList = new HashMap<>();
		pronounList = new HashMap<>();
		otherList = new HashMap<>();
		totalList = new LinkedList<>();
		
		totalList.add(nounList);
		totalList.add(verbList);
		totalList.add(adjList);
		totalList.add(prepList);
		totalList.add(modalList);
		totalList.add(advList);
		totalList.add(determinerList);
		totalList.add(pronounList);
		totalList.add(otherList);
		
		
		
	}
	public int size()
	{
		int ret = 0;
		for(int i = 0; i < totalList.size(); i++)
		{
			ret+= totalList.get(i).size();
		}
		return ret;
	}
	/**
	 * Sorts the given WordEntry object by part of speech
	 * and then adds it to the appropriate HashMap.
	 * @param word WordEntry object to be added to the implicit RelevancyList object
	 */
	public void add(WordEntry gov)
	{
		if(gov.pos.equals("NN"))
		{
			this.addNoun(gov);
		}
		else if(gov.pos.equals("JJ"))
		{
			this.addAdj(gov);
		}
		else if(gov.pos.equals("VB"))
		{
			this.addVerb(gov);
		}
		else if(gov.pos.equals("RB"))
		{
			this.addAdv(gov);
		}
		else if(gov.pos.equals("MD"))
		{
			this.addModal(gov);
		}
		else if(gov.pos.equals("DT"))
		{
			this.addDeterminer(gov);
		}
		else if(gov.pos.equals("PRO"))
		{
			this.addPronoun(gov);
		}
		else
		{
			this.addOther(gov);
		}
	}
	/**
	 * Prints the RelevancyList to the file location provided in a computer-readable format.
	 * @param fileName The name of the file the RelevancyList should be printed out to.
	 * @throws IOException
	 */
	public void PrintRelevancyList(String fileName) throws IOException
	{
		//Creates a new PrintWriter object
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
		
		//Goes through each HashMap in the totalList parameter
		for(HashMap<String, WordEntry> hList : totalList)
		{
			//Goes through each word stored in each HashMap
			for(String word : hList.keySet())
			{
				
				WordEntry w = hList.get(word); //Retrieves he underlying WordEntry object associated with the word key
				
				pw.print(w.name + "@" + w.pos  + "|"); //Prints the name and POS of the WordEntry
				
				int i = 0;
				for(HashMap<String, Integer> h : w.relns) //Cycles through all of the Relation HashMaps in the WordEntry object
				{
						
					
						for(String r : h.keySet())//Cycles through each Relation r in Relation HashMap
						{
							try
							{
								
									//Prints out the details of the Relation
								pw.print(" " + i + "!" + "reln" +  "!" + r + "/" + this.get(r).pos + "!" + h.get(r));
								
							}
							catch(NullPointerException n)
							{
							

							}
						}
					
					
					i++;
				}
				//Prints a new line after each WordEntry is done				
				pw.print("\n");
			}
		}
		pw.close(); //Closes our PrintWriter
	}
	/**
	 * Checks whether the RelevancyList contains the given word
	 * @param str The word to be searched for
	 * @return Whether the RelevancyList contains the given word
	 */
	public boolean contains(String str)
	{
		boolean b = false;
		for(HashMap<String, WordEntry> h : totalList)
		{
			
			if(h.containsKey(str))
			{
				return true;
			}
		}
		
		return b;
	}
	/**
	 * Obtains the WordEntry associated with the given key 
	 * @param str The word to be returned
	 * @return The WordEntry associated with the String word passed in
	 */
	public WordEntry get(String str)
	{
		WordEntry retval = null;
		for(HashMap<String, WordEntry> h : totalList)
		{
			
				if(h.containsKey(str))
				{
					return h.get(str);
				}
			
		}
		return retval;
	}
	public void addModal(WordEntry modal)
	{
		modalList.put(modal.name, modal);
	}
	public void addOther(WordEntry other)
	{
		otherList.put(other.name, other);
	}
	public void addPronoun(WordEntry pronoun)
	{
		pronounList.put(pronoun.name, pronoun);
	}
	public void addDeterminer(WordEntry determiner)
	{
		determinerList.put(determiner.name, determiner);
	}
	public void addAdv(WordEntry adv)
	{
		advList.put(adv.name, adv);
	}
	public void addNoun(WordEntry noun)
	{
		nounList.put(noun.name, noun);
	}
	public void addVerb(WordEntry verb)
	{
		verbList.put(verb.name, verb);
	}
	public void addAdj(WordEntry adj)
	{
		adjList.put(adj.name, adj);
	}
	public void addPrep(WordEntry prep)
	{
		prepList.put(prep.name, prep);
	}
	public static String deAccent(String str)
	{
	    String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD); 
	    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	    return pattern.matcher(nfdNormalizedString).replaceAll("").replaceAll("-|'", "");
	}
	public static String clean(String str)
	{
		String retval = "";
		StringTokenizer s1 = new StringTokenizer(str);
		
		while(s1.hasMoreTokens())
		{
			String token = "";
			StringTokenizer s2 = new StringTokenizer(s1.nextToken(), "!");
			token += s2.nextToken() + "!" + s2.nextToken() + "!" + s2.nextToken().replaceAll("[^a-zA-Z/]", "") + "!" + s2.nextToken();
			retval += token + " ";
		}
		
		return retval;
	}
	/**
	 * Reads in a file and reconstructs the RelevancyList object in the file.
	 * @param fileName The location of the RelevancyList to be constructed.
	 * @return The RelevancyList object reconstructed from the file.
	 * @throws IOException
	 */
	public static RelevancyList reconstructList(String fileName) throws IOException
	{
		HashMap<String, String> hmap = new HashMap<>();
		//Creates a BufferedReader object around the fileName String
		BufferedReader bf = new BufferedReader(new FileReader(fileName)); 
		RelevancyList r = new RelevancyList(); //Instantiates a new RelevancyList
		int k =0;
		int coun = 0;
		while(bf.ready())//Cycles through the entire document
		{
			String line = bf.readLine();
			if(line.equals(""))
			{
				continue;
			}
			k++;

			if(k % 1000 == 0)
			{
				System.out.println(k);
			}
			//Splits the line between the WordEntry and its relations
			StringTokenizer st = new StringTokenizer(line, "|"); 
			//Saves name token of the form "name@POS"
			
			StringTokenizer nameTokenSplit = new StringTokenizer(st.nextToken(), "@");
			
			
			WordEntry rootWord;
			int tot = 0;
			String rootWordName = nameTokenSplit.nextToken();
			if(r.contains(rootWordName))
			{
				//System.out.println("Contained");
				rootWord = r.get(rootWordName);
			}
			else
			{
				rootWord = new WordEntry(rootWordName); 
			}
			rootWord.setPOS(nameTokenSplit.nextToken());
			
			
			if(st.hasMoreTokens())
			{
				//Extracts relations token of the form " rel1 rel2 rel3..."
				//Trims preceding whitespace and splits the token into individual relations
				StringTokenizer relnsArray = new StringTokenizer(st.nextToken().trim());

				//Processes each relation
				while(relnsArray.hasMoreTokens())
				{
					
					String reln = relnsArray.nextToken();
					reln = reln.trim();
					if(!reln.matches("([0-9]+)!([a-z_]+)!([a-z]+)/([a-z]+)/([A-Z]+)!([0-9]+)"))
					{
						continue;
					}
					//Splits relation token of the form "index!type!word/pos!count"
					StringTokenizer components = new StringTokenizer(reln, "!"); 
					//Extracts and trims index
					Integer index = Integer.parseInt(components.nextToken().replaceAll("[^0-9]", "")); 
					//Extracts type 
					String type = components.nextToken();
					//Extracts word token of type "word/pos"
					String otherWordNameAndPOS = components.nextToken();
					//Splits above into word and pos
					String[] otherWordInfo = otherWordNameAndPOS.split("/"); 
					String otherWordName = (otherWordInfo[0] + "/" + otherWordInfo[1]); //Assigns word
					String otherWordPOS = otherWordInfo[2]; //Assigns pos
					String counter = components.nextToken().trim().replaceAll("[^0-9]", "");
					
					Integer count = Integer.parseInt(counter); //Extracts and parses count
					
					tot += count;
					//Creates new WordEntry object and sets pos
					WordEntry otherWord;
					
					
					
					if(!r.contains(otherWordName))
					{
						otherWord = new WordEntry(otherWordName);
						
						otherWord.setPOS(otherWordPOS);
					}
					else
					{
						otherWord = r.get(otherWordName);

					}
					
					if(hmap.containsKey(otherWordName))
					{
						otherWordName = hmap.get(otherWordName);
					}
					else
					{
						hmap.put(otherWordName, otherWordName);
					}
					if(hmap.containsKey(rootWord.name))
					{
						rootWord.name = hmap.get(rootWord.name);
						
					}
					else
					{
						hmap.put(rootWord.name, rootWord.name);
					}
					if(hmap.containsKey(type))
					{
						type = hmap.get(type);
					}
					else
					{
						hmap.put(type, type);
					}
					
					
				
					
					
					
					//Creates new Relation object	
					if(!rootWord.relns[otherWord.returnIndex()].containsKey(otherWordName))
					{
						//Adds Relation object to appropriate HashMap field of the rootWord WordEntry object
						rootWord.relns[otherWord.returnIndex()].put(otherWordName, count/2);
					}
					else
					{
						rootWord.relns[otherWord.returnIndex()].put(otherWordName, count/2 + rootWord.relns[otherWord.returnIndex()].get(otherWordName));
					}
					
					
					
					if(!otherWord.relns[rootWord.returnIndex()].containsKey(rootWord.name))
					{
						otherWord.relns[rootWord.returnIndex()].put(rootWord.name, count/2);

					}
					else
					{
						otherWord.relns[rootWord.returnIndex()].put(rootWord.name, count/2 + otherWord.relns[rootWord.returnIndex()].get(rootWord.name));
					}
					
					r.add(otherWord);
					

					
				}

			}
		
			if(tot == 0)
			{
				continue;
			}
			
			rootWord.totalRelns = tot;
			
			//Adds the rootWord and its associated fields
			r.add(rootWord);

		}
		
		
		
		
		bf.close(); //Closes the reader
		System.out.println(coun + " " + hmap.size());
		hmap = null;
		return r; //Returns the reconstructed RelevancyList to the caller
	}
	
	public void process(TypedDependency t)
	{
		if(!t.gov().toString().equals("ROOT")) //Ignores the ROOT dependency
		{
			String str1 = t.gov().toString(); //Stores the governing word
			String str2 = t.dep().toString(); //Stores the dependent word
					
					
			//Splits the governing and dependent words into the word and pos pair
			String[] govArray = str1.split("/");
			String[] depArray = str2.split("/");
			String govName =  govArray[0].trim().toLowerCase();
			String depName = depArray[0].trim().toLowerCase();
				
			//Expands commonly used contractions
			if(govName.equals("'re"))
			{
				govName = "are" + "/" + govArray[1];
			}
			else if(govName.equals("'m"))
			{
				govName = "am" + "/" + govArray[1];
			}
			else if(govName.equals("n't"))
			{
				govName = "not"+ "/" + govArray[1];
			}
			else if(govName.equals("'ve"))
			{
				govName = "have"+ "/" + govArray[1];
			}
			else if(govName.equals("'ll"))
			{
				govName = "will"+ "/" + govArray[1];
			}
			
			govName.replaceAll("'", "");
			govName.replaceAll("-", "");

			
			
					
			if(depName.equals("'re"))
			{
				depName = "are"+ "/" + depArray[1];
			}
			else if(depName.equals("'m"))
			{
				depName = "am"+ "/" + depArray[1];
			}
			else if(depName.equals("n't"))
			{
				depName = "not"+ "/" + depArray[1];
			}
			else if(depName.equals("'ve"))
			{
				depName = "have"+ "/" + depArray[1];
			}
			else if(depName.equals("'ll"))
			{
				depName = "will"+ "/" + depArray[1];
			}
			
			depName = depName.replaceAll("'", "");
			govName = govName.replaceAll("-", "");

			
			govName = deAccent(govName);
			depName = deAccent(depName);
			
			govName = govName + "/" + govArray[1];
			depName = depName + "/" + depArray[1];
			
			govName = govName.toLowerCase();
			depName = depName.toLowerCase();
					
			//Allocates new objects
			WordEntry gov;
			WordEntry dep;
			//Checks to see if the given WordEntry objects already exists, if they don't a new one is created
			if(contains(govName))
			{
				gov = get(govName);
			}
			else
			{
				gov = new WordEntry(govName);
			}
			if(contains(depName))
			{
				dep = get(depName);

			}
			else
			{
				dep = new WordEntry(depName);
			}
			
			//Extracts the relation 
			String relation = t.reln().toString();
			//Increments relation counters
			gov.incrementTotalRelns();
			dep.incrementTotalRelns();
			
			
			
			//Sets parts of speech
			gov.setPOS(govArray[1]);
			dep.setPOS(depArray[1]);
					
			//Sets new relations
			gov.setReln(relation, dep);
			dep.setReln(relation, gov);
					
					
					
					
			//Extracts prepositions
			Pattern p = Pattern.compile("(prep_)(.*)");
			Matcher m = p.matcher(relation);
			if(m.find())
			{
				WordEntry prep;
						
				if(contains(m.group(2)))
				{
					prep = get(m.group(2));
				}
				else
				{
					prep = new WordEntry(m.group(2) + "/prep");
				}
				prep.incrementTotalRelns();
				prep.setPOS("PREP");
				prep.setReln("prep", gov);
				prep.setReln("prep", dep);
				addPrep(prep);
				
				gov.incrementTotalRelns();
				dep.incrementTotalRelns();
				gov.setReln("prep", prep);
				dep.setReln("prep", prep);
			}
					
					
			add(gov);
			add(dep);
			
					
		}
	
		

	}
	public WordEntry getProper(String str ,HashMap<String, ArrayList<TypedDependency>> hs) 
	{
		int max = -100;
		str = str.toLowerCase();
		ArrayList<WordEntry> word = new ArrayList<>();
		if(contains(str + "/vb") && hs.containsKey(str + "/vb"))
		{
			word.add(get(str + "/vb"));
		}
		if(contains(str + "/vbz") && hs.containsKey(str + "/vbz"))
		{
			word.add(get(str + "/vbz"));

		}
		if(contains(str + "/vbd")  && hs.containsKey(str + "/vbd"))
		{
			word.add(get(str + "/vbd"));

		}
		if(contains(str + "/jj") && hs.containsKey(str + "/jj"))
		{
			word.add(get(str + "/jj"));

		}
		if(contains(str + "/vbg")  && hs.containsKey(str + "/vbg"))
		{
			word.add(get(str + "/vbg"));

		}
		if(contains(str + "/vbn")  && hs.containsKey(str + "/vbn"))
		{
			word.add(get(str + "/vbn"));

		}
		if(contains(str + "/vbp")  && hs.containsKey(str + "/vbp"))
		{
			word.add(get(str + "/vbp"));

		}
		if(contains(str + "/nn")  && hs.containsKey(str + "/nn"))
		{
			word.add(get(str + "/nn"));

		}
		if(contains(str + "/nns")  && hs.containsKey(str + "/nns"))
		{
			word.add(get(str + "/nns"));

		}
		if(contains(str + "/nnp")  && hs.containsKey(str + "/nnp"))
		{
			word.add(get(str + "/nnp"));

		}
		if(contains(str + "/nnps")  && hs.containsKey(str + "/nnps"))
		{
			word.add(get(str + "/nnps"));

		}
		if(contains(str + "/jjr")  && hs.containsKey(str + "/jjr"))
		{
			word.add(get(str + "/jjr"));

		}
		if(contains(str + "/jjs")  && hs.containsKey(str + "/jjs"))
		{
			word.add(get(str + "/jjs"));

		}
		if(contains(str + "/in")  && hs.containsKey(str + "/in"))
		{
			word.add(get(str + "/in"));

		}
		if(contains(str + "/pdt")  && hs.containsKey(str + "/pdt"))
		{
			word.add(get(str + "/pdt"));

		}
		if(contains(str + "/pos")  && hs.containsKey(str + "/pos"))
		{
			word.add(get(str + "/pos"));

		}
		if(contains(str + "/prp")  && hs.containsKey(str + "/prp"))
		{
			word.add(get(str + "/prp"));

		}
		if(contains(str + "/prp$")  && hs.containsKey(str + "/prp$"))
		{
			word.add(get(str + "/prp$"));

		}
		if(contains(str + "/rb")  && hs.containsKey(str + "/rb"))
		{
			word.add(get(str + "/rb"));

		}
		if(contains(str + "/rbr")  && hs.containsKey(str + "/rbr"))
		{
			word.add(get(str + "/rbr"));

		}
		if(contains(str + "/rbs")  && hs.containsKey(str + "/rbs"))
		{
			word.add(get(str + "/rbs"));

		}
		if(contains(str + "/rp")  && hs.containsKey(str + "/rp"))
		{
			word.add(get(str + "/rp"));

		}
		if(contains(str + "/cc") && hs.containsKey(str + "/cc"))
		{
			word.add(get(str + "/cc"));

		}
		if(contains(str + "/cd")  && hs.containsKey(str + "/cd"))
		{
			word.add(get(str + "/cd"));

		}
		if(contains(str + "/dt")  && hs.containsKey(str + "/dt"))
		{
			word.add(get(str + "/dt"));

		}
		if(contains(str + "/ex")  && hs.containsKey(str + "/ex"))
		{
			word.add(get(str + "/ex"));

		}
		if(contains(str + "/fw")  && hs.containsKey(str + "/fw"))
		{
			word.add(get(str + "/fw"));

		}
		if(contains(str + "/ls")  && hs.containsKey(str + "/ls"))
		{
			word.add(get(str + "/ls"));

		}
		if(contains(str + "/md")  && hs.containsKey(str + "/md"))
		{
			word.add(get(str + "/md"));

		}
		if(contains(str + "/sym")  && hs.containsKey(str + "/sym"))
		{
			word.add(get(str + "/sym"));

		}
		if(contains(str + "/to")  && hs.containsKey(str + "/to"))
		{
			word.add(get(str + "/to"));

		}
		if(contains(str + "/uh")  && hs.containsKey(str + "/uh"))
		{
			word.add(get(str + "/uh"));

		}
		if(contains(str + "/wdt")  && hs.containsKey(str + "/wdt"))
		{
			word.add(get(str + "/wdt"));

		}
		if(contains(str + "/wp")  && hs.containsKey(str + "/wp"))
		{
			word.add(get(str + "/wp"));

		}
		if(contains(str + "/wp$")  && hs.containsKey(str + "/wp$"))
		{
			word.add(get(str + "/wp$"));

		}
		if(contains(str + "/wrb")  && hs.containsKey(str + "/wrb"))
		{
			word.add(get(str + "/wrb"));
		}
		
	
		WordEntry ret = null;
		for(WordEntry w : word)
		{
			if(w.totalRelns > max)
			{
				ret = w;
				max = w.totalRelns;
			}

		}

		return ret;
	}
}
