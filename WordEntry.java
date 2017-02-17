package SynId;
import java.io.Serializable;
import java.util.HashMap;

import edu.stanford.nlp.trees.TypedDependency;
/**
 * This class defines the WordEntry object which is used in the implementation of the
 * relevancy list and synonym identifier.
 * @author Jeremy Dohmann
 * @author Patrick DeMichele
 */
public class WordEntry implements Serializable
{
	
	public String name; //String representation of word
	public String pos; //Part of speech
	public int totalRelns;
	public HashMap<String, Integer>[] relns; //Array holding String HashMap for easy printing
	
	//HashMaps for various parts of speech to hold String-counter pairs
	HashMap<String, Integer> prepList = new HashMap<String, Integer>();
	HashMap<String, Integer> nounList = new HashMap<String, Integer>();
	public HashMap<String, Integer> verbList = new HashMap<String, Integer>();
	HashMap<String, Integer> adjList = new HashMap<String, Integer>();
	HashMap<String, Integer> modalList = new HashMap<String, Integer>();
	HashMap<String, Integer> advList= new HashMap<String, Integer>();
	HashMap<String, Integer> determinerList = new HashMap<String, Integer>();
	HashMap<String, Integer> pronounList= new HashMap<String, Integer>();
	HashMap<String, Integer> otherList = new HashMap<String, Integer>();
	
	/**
	 * This method takes in an implicit WordEntry argument and an explicit one and
	 * determines whether they are equal by comparing their name and pos.
	 * @param otherWord The WordEntry object being compared against
	 * @return Whether the implicit WordEntry object is equivalent to the WordEntry object being 
	 * compared against
	 */
	@Override
	public boolean equals(Object otherW)
	{
		WordEntry w = null;
		if(otherW instanceof WordEntry)
		{
			w = (WordEntry)otherW;
		}
		else
		{
			return false;
		}
		if((this.name + this.pos).toLowerCase().equals((w.name + w.pos).toLowerCase()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	@Override
	public String toString()
	{
		return name;
	}
	@Override
	public int hashCode()
	{
		return toString().hashCode();
		
	}
	/**
	 * This method assigns an int based on the POS of the implicit
	 * WordEntry object and the indices given to the different parts
	 * of speech in the construction of the relns array.
	 * @return The index of the given word based on its part of speech
	 */
	public int returnIndex()
	{
		
			if(pos.equals("NN"))
			{
				return 0;
			}
			else if(pos.equals("JJ"))
			{
				return 2;
			}
			else if(pos.equals("VB"))
			{
				return 1;
			}
			else if(pos.equals("RB"))
			{
				return 4;
			}
			else if(pos.equals("MD"))
			{
				return 3;
			}
			else if(pos.equals("DT"))
			{
				return 5;
			}
			else if(pos.equals("PRO"))
			{
				return 6;
			}
			else if(pos.equals("PREP"))
			{
				return 8;
			}
			else
			{
				return 7;
			}
	}
	public static int returnIndex(String pos)
	{
		
			if(pos.equals("NN"))
			{
				return 0;
			}
			else if(pos.equals("JJ"))
			{
				return 2;
			}
			else if(pos.equals("VB"))
			{
				return 1;
			}
			else if(pos.equals("RB"))
			{
				return 4;
			}
			else if(pos.equals("MD"))
			{
				return 3;
			}
			else if(pos.equals("DT"))
			{
				return 5;
			}
			else if(pos.equals("PRO"))
			{
				return 6;
			}
			else if(pos.equals("PREP"))
			{
				return 8;
			}
			else
			{
				return 7;
			}
	}
	/**
	 * This method constructs the WordEntry object by assigning its name and 
	 * initializing its relns array.
	 * @param name String representation of the underlying word
	 */
	public WordEntry(String name)
	{
		this.name = name.toLowerCase(); //Assigns name
		
		relns = new HashMap[9]; //Constructs relns array
		totalRelns = 0;
		prepList = new HashMap<String, Integer>();
		nounList = new HashMap<String, Integer>();
		verbList = new HashMap<String, Integer>();
		adjList = new HashMap<String, Integer>();
		modalList = new HashMap<String, Integer>();
		advList= new HashMap<String, Integer>();
		determinerList = new HashMap<String, Integer>();
		pronounList= new HashMap<String, Integer>();
		otherList = new HashMap<String, Integer>();
		//Assigns HashMaps to appropriate indices in relns array
		relns[0] = nounList;
		relns[1] = verbList;
		relns[2] = adjList;
		relns[3] = modalList;
		relns[4] = advList;
		relns[5] = determinerList;
		relns[6] = pronounList;
		relns[7] = otherList;
		relns[8] = prepList;
		
		
		
	}
	/*

	public static class Relation implements Serializable
	{
		public String name; //Defines the name/type representation of the Relation
		public WordEntry firstWord; //Defines the word central to the Relation
		public WordEntry secondWord; //Defines the other argument of the relation
		
		
		public Relation(WordEntry firstWord, String name, WordEntry secondWord)
		{
			this.name = name.toLowerCase();
			this.firstWord = firstWord;
			this.secondWord = secondWord;
			
		}
		
		
		@Override
		public boolean equals(Object other)
		{
			if(!(other instanceof Relation))
			{
				return false;
			}
			
			Relation r = (Relation)other;
			
			//Compares using hashCode
			if(this.name.equals(r.name) && this.secondWord == r.secondWord && this.firstWord == r.firstWord)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		@Override
		public String toString()
		{
			return firstWord.name +"_" + secondWord.name + "/" + firstWord.pos + name;
		}
		/
		@Override
		public int hashCode()
		{	
			return toString().hashCode(); 	
		}

		public WordEntry otherWord(WordEntry w2) 
		{
			if(firstWord.equals(w2))
			{
				return secondWord;
			}
			else if(secondWord.equals(w2))
			{
				return firstWord;
			}
			else
			{
				return null;
			}
		}
	}
	*/
	public boolean contains(HashMap<String, Integer> h, String r)
	{
		return h.containsKey(r);

	}
	public boolean contains(String r)
	{
		for(int i = 0; i < relns.length; i++)
		{
			if(relns[i].containsKey(r))
			{
				return true;
			}
		}
		return false;

	}
	public int get(WordEntry w)
	{
		
			for(int i = 0; i < 9; i++)
			{
				for(String r: relns[i].keySet())
				{
					if(r.equals(w.name))
					{
						
						return relns[i].get(r);
					}
				}
			}
			
			return 0;
		
	}
	public boolean contains(WordEntry w)
	{
		for(int i = 0; i < 9; i++)
		{
			for(String r: relns[i].keySet())
			{
				if(r.equals(w.name))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	/**
	 * This adds a given WordEntry object to the Relation network of
	 * the implicit WordEntry object. 
	 * @param name The type of the Relation to be created
	 * @param otherWord The other WordEntry argument of the Relation
	 */
	public void setReln(String name, WordEntry otherWord)
	{
		
		String newReln = otherWord.name;
		//Classifies the passed in word by part of speech, then carries out the adding routine
		if(otherWord.pos.equals("NN"))
		{
			addReln(newReln, nounList);
		}
		else if(otherWord.pos.equals("PREP"))
		{
			addReln(newReln, prepList);
		}
		else if(otherWord.pos.equals("JJ"))
		{
			addReln(newReln, adjList);
		}
		else if(otherWord.pos.equals("VB"))
		{
			addReln(newReln, verbList);
		}
		else if(otherWord.pos.equals("RB"))
		{
			addReln(newReln, advList);
		}
		else if(otherWord.pos.equals("MD"))
		{
			addReln(newReln, modalList);
		}
		else if(otherWord.pos.equals("DT"))
		{
			addReln(newReln, determinerList);
		}
		else if(otherWord.pos.equals("PRO"))
		{
			addReln(newReln, pronounList);
		}	
		else
		{
			addReln(newReln, otherList);
		}
	}
	/**
	 * This method either adds new Relations or updates the counts to old ones according
	 * to their presence in the implicit WordEntry's Relation network.
	 * @param newReln The Relation being added
	 * @param list The HashMap being added to
	 */
	private void addReln(String newReln, HashMap<String, Integer> list)
	{
		if(contains(list, newReln))//Checks to see if there are any Relations in the list's keySet with matching hashCodes
		{
			
			 //Obtains the Relation object in nounList whose hashCode matches newReln, and then updates newReln to point to that
			
			
			int count = 0;
			try
			{
			    //Retrieves the old count, updates then replaces the value 
				count = list.get(newReln); 
			 	count++;
			}
			catch(NullPointerException e)
			{
			}
			
			list.put(newReln, count);
			
			
		}
		else //If this is a new relation, it is stored anew
		{
			list.put(newReln, 1);
		}
	}
	
	/**
	 * This method increments the totalRelns counter every time
	 * a new Relation is added for this word.
	 */
	public void incrementTotalRelns()
	{
		totalRelns += 1;
	}
	/**
	 * This method sets the POS according to several broad categories.
	 * @param pos The String version of the POS given by the dependency list
	 */
	public void setPOS(String pos)
	{
		switch(pos)
		{
			case "CC":
				this.pos = "CC";
				break;
			case "IN":
				this.pos = "PREP";
				break;
			case "PREP":
				this.pos = "PREP";
				break;
			case "DT":
				this.pos = "DT";
				break;
			case "EX":
				this.pos = "EX";
				break;
			case "JJ":
				this.pos = "JJ";
				break;
			case "JJR":
				this.pos = "JJ";
				break;
			case "JJS":
				this.pos = "JJ";
				break;
			case "MD":
				this.pos = "MD";
				break;
			case "NN":
				this.pos = "NN";
				break;
			case "NNS":
				this.pos = "NN";
				break;
			case "NNP":
				this.pos = "NN";
				break;
			case "NNPS":
				this.pos = "NN";
				break;
			case "PDT":
				this.pos = "DT";
				break;
			case "PRP" :
				this.pos = "PRO";
				break;
			case "PRP$":
				this.pos = "PRO";
				break;
			case "RB":
				this.pos = "RB";
				break;
			case "RBR":
				this.pos = "RB";
				break;
			case "RBS":
				this.pos = "RB";
				break;
			case "VB" :
				this.pos = "VB";
				break;
			case "VBD":
				this.pos = "VB";
				break;
			case "VBG":
				this.pos = "JJ";
				break;
			case "VBN":
				this.pos = "JJ";
				break;
			case "VBP":
				this.pos = "VB";
				break;
			case "VBZ":
				this.pos = "VB";
				break;
			case "WDT":
				this.pos = "DT";
				break;
			case "WP":
				this.pos = "PRO";
				break;
			case "WP$":
				this.pos = "PRO";
				break;
			case "WRB":
				this.pos = "RB";
				break;
			default:
				this.pos = "OTHER";
			
				
				
		}
	}
}
