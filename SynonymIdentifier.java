package SynId;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.StringTokenizer;

import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;



import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.tree.RandomForest;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;

/**
 * 
 * @author Jeremy Dohmann
 * @author Patrick DeMichele
 */
public class SynonymIdentifier 
{
	
	public static String dictLoc= "dict";
	public Classifier rf;
	
	public SynonymIdentifier() throws NumberFormatException, IOException
	{
		createClassifier();
	}
	public double wordSentenceProbability(WordEntry w, WordEntry[] sentence, RelevancyList r, NodeGraph ng) throws IOException
	{
		double count = 0;
		Node2 n1 = ng.get(w.name.split("/")[0]);
		for(WordEntry we : sentence)
		{
			Node2 n2 = ng.get(we.name.split("/")[0]);

			double like = identifySynonymPairLikelihood(w, we, r, n1, n2);
			double pos = 1-1/(1+like);
			count += Math.log(pos);
		}
		return count / sentence.length;
	}
	
	
	/**
	 * This method calculates the cos of the angle between the similarity and weighting vectors.
	 * 
	 * @param d The weighting array vector passed in
	 * @param i The similarity vector passed in
	 * @return
	 */
	private static double cosineFunct(int[] d)
	{
		double retval = 0; //The cos to be returned
		double dMag = 0; //The magnitude of the weighting vector
		double dot = 0; //The value of the dot product of the weighting and similarity vectors
		
		//Calculates the dot product
		for(int j = 0; j < d.length/2; j++)
		{
			dot += d[j];
		}
		//Calculates the magnitude of the weighting vector
		for(int j = 0; j < d.length; j++)
		{
			dMag = d[j]*d[j];
		}
		dMag = Math.sqrt(dMag);
		
		
		//Finds the cos by dividing the dot product by the two magnitudes
		retval = dot/(dMag + Math.sqrt(d.length));
		
		return retval;
		
		
	}
	/**
	 * This method takes in a word and a sentence to be searched and returns the number of times the given word
	 * is found in the sentence being searched.
	 * 
	 * @param w WordEntry object we are searching for in sentence 2
	 * @param sentence2WordEntry The WordEntry[] object representing the sentence we are searching
	 * @return Returns the number of times a match is found
	 */
	private int otherSentenceContains(WordEntry w, WordEntry[] sentence2WordEntry)
	{
		int count = 0;
		
		//Increases the count every time the word we are searching for is found
		for(WordEntry we : sentence2WordEntry)
		{
			if(we.equals(w))
			{
				count++;
			}
			
		}
		return count;
	}
	/**
	 * This method takes in a word to be searched for and a sentence to be searched, returning
	 * the number of times a related word is found in the sentence being searched.
	 * @param w WordEntry object we are searching for in the second sentence
	 * @param sentence2WordEntry The sentence we are searching 
	 * @return The number of times a related word is found in the sentence we are searching
	 * @throws IOException 
	 */
	private int otherSentenceContainsRelated(WordEntry w, WordEntry[] sentence2WordEntry,  RelevancyList r, NodeGraph ng) throws IOException
	{
		int count = 0;
		
		
		Node2 n1 = ng.get(w.name.split("/")[0]);

		//Every time a synonym pair is found, the counter is updated
		for(WordEntry we : sentence2WordEntry)
		{
			Node2 n2 = ng.get(we.name.split("/")[0]);
			if(identifySynonymPair(w, we, r, n1, n2))
			{
				System.out.println(w.name + " " + we.name);
				count++;
			}
			
		}
		return count;
	}
	public static void printVectors(ArrayList<SimilarityVector> n, ArrayList<Double> t) throws IOException
	{
		PrintWriter	pw1 = new PrintWriter(new FileWriter(new File("vectors.txt")));
		PrintWriter	pw2 = new PrintWriter(new FileWriter(new File("vectorsTgt.txt")));

		for(SimilarityVector sv : n)
		{
			for(double d : sv.vectorValues)
			{
				pw1.print(d + " ");
			}
			pw1.println();

		}
		pw1.close();
		for(Double d : t)
		{
			
			pw2.println(d);

		}
		pw2.close();
	}

	
	
	

	
	
	
	
	
	/**
	 * This method returns a true if the passed in network identifies the word pair
	 * as related.
	 * @param w1 The first WordEntry object to be compared
	 * @param w2 The second WordEntry object to be compared
	 * @param aList The array list containing the network weights to be used
	 * @return Returns a boolean representing whether the two are related to one another
	 * @throws IOException 
	 */
	public boolean identifySynonymPair(WordEntry w1, WordEntry w2, RelevancyList r, Node2 n1, Node2 n2) throws IOException
	{
		//Constructs similarity vector objects and sets the local weights to those passed in
		SimilarityVector sV = new SimilarityVector(w1, w2, r, n1, n2);
		if((double)rf.classify(new DenseInstance(sV.vectorValues)) == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
		
		
	}
	public boolean identifySynonymPair(double[] d1) throws IOException
	{
		//Constructs similarity vector objects and sets the local weights to those passed in
		
		if((double)rf.classify(new DenseInstance(d1)) == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
		
		
	}
	public double identifySynonymPairLikelihood(WordEntry w1, WordEntry w2, RelevancyList r, Node2 n1, Node2 n2) throws IOException
	{
		//Constructs similarity vector objects and sets the local weights to those passed in
		SimilarityVector sV = new SimilarityVector(w1, w2, r, n1, n2);
		
		Instance inst = new DenseInstance(sV.vectorValues, -1.0);
		double neg = 0;
		for(Object d : rf.classDistribution(inst).keySet())
		{
			if((double) d == 1)
			{
				
				neg = rf.classDistribution(inst).get(d);
				
			}
		}		

		return neg/(1 - neg);
	}
	public double identifySynonymPairLikelihood(double[] d1) throws IOException
	{
		//Constructs similarity vector objects and sets the local weights to those passed in
		
		Instance inst = new DenseInstance(d1, -1.0);
		double neg = 0;
		for(Object d : rf.classDistribution(inst).keySet())
		{
			if((double) d == 1)
			{
				
				neg = rf.classDistribution(inst).get(d);
				
			}
		}		
		return (1-neg)/neg;
	}
	
	
	public void createClassifier() throws NumberFormatException, IOException
	{
		
		BufferedReader bf = new BufferedReader(new FileReader(new File("training vectors2.txt")));
		BufferedReader bf0 = new BufferedReader(new FileReader(new File("training tags2.txt")));

		
		Dataset data0 = new DefaultDataset();
		while(bf.ready())
		{
			String s = bf.readLine();
			StringTokenizer st = new StringTokenizer(s);
			double[] d  = new double[st.countTokens()];
			int i = 0;
			while(st.hasMoreTokens())
			{
				d[i] = Double.parseDouble(st.nextToken());
				i++;
			}
			double targ = Double.parseDouble(bf0.readLine());
			data0.add(new DenseInstance(d, targ));
			
		}
		bf.close();
		bf0.close();
		rf = new RandomForest(300);
		rf.buildClassifier(data0);
		
	}
	
	public static class SimilarityVector
	{
		public static double[] TSV = new double[10];
		
		public static int count = 0;
		public double[] vectorValues;
		private WordEntry w1;
		private WordEntry w2;
		
		public String toString()
		{
			String s = "";
			for(double d : vectorValues)
			{
				s += (d + " ");
			}
			return s;
		}
		/**
		 * Constructs a SimilarityVector object and  populates its vectorValues array by calling
		 * the explicitProb and implicitProb methods
		 * 
		 * @param w1 The first WordEntry argument
		 * @param w2 The second WordEntry argument
		 * @throws IOException 
		 */
		public SimilarityVector(WordEntry word1, WordEntry word2, RelevancyList r, Node2 n1, Node2 n2)  throws IOException
		{
			
			this.w1 = word1;
			this.w2 = word2;
			
			double[] implicitProb = implicitProb(w1, w2, r);
			double[] d2 = direct_product(w1, w2); //9 all except 6
			double[] d3 = exp(n1, n2); //3 all
			double[] d4 = imp(n1, n2); //1 all

			double explicitDefOverlap = explicitDefOverlap(w1, w2, r);
			double overlap = stringOverlap(w1, w2);
			
			double posclass = posClass(w1, w2);
			
			double[] d1 = merge(implicitProb, overlap, posclass, explicitDefOverlap);
			
			vectorValues = concat(d1, d2);
			vectorValues = concat(vectorValues, d3);
			vectorValues = concat(vectorValues, d4);

		}
		public SimilarityVector() 
		{
			vectorValues = new double[5];
		}
		
		private static double[] merge( double[] implicitProb, double overlap, double posclass, double explicitDefOverlap)
		{
			double[] retval = new double[5];
			
			retval[0] = implicitProb[0];
			retval[1] = implicitProb[1];
			retval[2] = overlap;
			retval[3] = posclass;
			retval[4] = explicitDefOverlap;

			return retval;
		}
	}
	private static String cleanedDef(String def){
		String[] defSplit = def.split(" ");
		String common = "the be to of and a in that have i it for not on although";
		String[] commonSplit = common.split(" ");
		ArrayList<String> defSplit2 = new ArrayList();
		HashSet<String> commonSplit2 = new HashSet<>();
		for(String s: defSplit)
			defSplit2.add(s);
		for(String s: commonSplit)
			commonSplit2.add(s);
		ArrayList<String> finish = new ArrayList();
		for(String g: defSplit2)
			if(!commonSplit2.contains(g))
				finish.add(g);
		String s= "";
		for(String p: finish)
			if(Math.random()<.8)
			s+=p+" ";
		return s;
		
		
		
	}
	public static double[] concat(double[] a, double[] b) 
	{
		
		   int aLen = a.length;
		   int bLen = b.length;
		   double[] c= new double[aLen+bLen];
		   System.arraycopy(a, 0, c, 0, aLen);
		   System.arraycopy(b, 0, c, aLen, bLen);
		   return c;
	}
	// for each part of speech, computes the average of the products
	// of number of relations between w1 and word X the the number of relations between
	// w2 and word X, taken across all X.
	public static double[] direct_product(WordEntry w1, WordEntry w2)
	{
		int j = 0;
		double[] retval = new double[8];
		for(int i = 0 ; i < 9; i++)
		{
			if(i == 6)
			{
				continue;
			}
			
			int totalcount = 0;
			int magnitudeCounter = 0;
			double skip = 1;
			double exp = 2;
			if(w1.relns[i].size()*w2.relns[i].size()>200000)
				skip = 200000/(w1.relns[i].size()*w2.relns[i].size());
			for(String r: w1.relns[i].keySet())
			{
				for(String r2: w2.relns[i].keySet())
				{
					totalcount++;
					if(r2.equals(r)  && Math.random()<skip)//&& r2.name.equals(r.name)
						magnitudeCounter+=w1.relns[i].get(r)*w2.relns[i].get(r2);
				}
			}
			if(totalcount == 0)
				retval[j] = 0;
			else
			{
				exp = 1; // we will need to test for exact exp later, where exp = 2n, that is, n such that [# counts] = k*relns.size^n
				retval[j] = (double)magnitudeCounter/(Math.pow(totalcount,exp));	
			}
			j++;
		}
		
		return retval;
	}
	
	public static int occurs(String[] array1, String word){
		int count = 0;
		for(String s: array1)
			if(s.equals(word) || s.equals(word+"s")|| (s+"s").equals(word))
				count++;
		return count;
	}
	public static int entrySpot(WordEntry w)
	{
		if(w.pos.equals("NN"))
			return 0;
		if(w.pos.equals("JJ"))
			return 1;
		if(w.pos.equals("VB"))
			return 2;
		if(w.pos.equals("RB"))
			return 3;
		if(w.pos.equals("MD"))
			return 4;
		if(w.pos.equals("DT"))
			return 5;
		if(w.pos.equals("PRO"))
			return 6;
		return 7;
	}

	 public static double[] exp(Node2 n1, Node2 n2){
	    	double[] retval = new double[3];
	    	double[] overlap = new double[1];
	    	double[] total = new double[1];
	    	
	    	int index = 0;
	    	for(Node2 other: n1.adj.keySet()){
	    		index = 0;
	    		if(n1.adj.get(other) * 323000/other.adj.size() < 50)
	    		{
	    			continue;
	    		}
	    		if(n2.adj.containsKey(other))
	    		{
	    			if(n2.adj.get(other) * 323000/other.adj.size() < 50)
	        		{
	        			continue;
	        		}
	    			overlap[index] += n2.adj.get(other)+n1.adj.get(other);
	    		}
	    		total[index]+=n1.adj.get(other);
	    	}
	    	for(Node2 other: n2.adj.keySet()){
	    		index = 0;
	    		
	    		total[index]+=n2.adj.get(other);
	    		
	    	}
	    	
	    	for(int i = 0; i<1; i++)
	    		if(total[i]!=0)
	    		retval[i] = overlap[i]/total[i] ;
	    		else
	    		retval[i] = 0;
	    	
	    	
	    	retval[1] = tf_idf(n1, n2)/ ((n1.count + n2.count)/2);
	    	retval[2] =avgDistance(n1, n2) / ((n1.count + n2.count)/2);
	    	return retval;
	    }

		private static double avgDistance(Node2 n1, Node2 n2) 
		{
			double sum = 0;
			double count = 0;
			for(Node2 n : n1.adj.keySet())
			{
				double d1 = n1.adj.get(n)*323000/n.adj.size();
				if(n.adj.containsKey(n2))
				{
					double d2 = n.adj.get(n2)*323000/n2.adj.size();
					
					double dist = Math.sqrt(d1*d2);
					
					if(dist != 0)
					{
						sum += dist;
						count++;
					}
				}
				
			}
			return sum/count;
		}

		private static double tf_idf(Node2 n1, Node2 n2) 
		{
			double ret1 = 0;
			double ret2 = 0;
			
			
			if(n1.adj.containsKey(n2))
			{
				double tf = n1.adj.get(n2);
				double idf = (323000.0/ 1 + n2.adj.size());
				
				ret1 = Math.log(idf)*tf;
			}
			if(n2.adj.containsKey(n1))
			{
				double tf = n2.adj.get(n1);
				double idf = (323000.0/ 1 + n1.adj.size());
				
				ret2 = Math.log(idf)*tf;
			}
			
			
			
			return (ret1 + ret2)/2;
		}

		public static double[] imp(Node2 n1, Node2 n2) 
		{
			double[] retval = new double[1];
	    	double[] overlap = new double[1];
	    	double[] total = new double[1];
	    	
	    	HashMap<Node2, Double> seenSoFar = new HashMap<>();
	    	
	    	int index = 0;
	    	for(Node2 other: n1.adj.keySet())
	    	{
	    		if(n1.adj.get(other) * 323000/other.adj.size() < 50)
	    		{
	    			continue;
	    		}
	    		
				double skip = (((double)n1.adj.get(other) / (double)n1.count) / Math.log((double)other.count)) * 150;
				if(Math.random() > skip)
				{
					continue;
				}
	    		for(Node2 other2 : other.adj.keySet())
	    		{
	        		index = 0;

	        		
	        		if(other.adj.get(other2) * 323000/other2.adj.size() < 50)
	        		{
	        			continue;
	        		}
	    			if(seenSoFar.containsKey(other2))
	    			{
	    				seenSoFar.put(other2, other.adj.get(other2) + seenSoFar.get(other2));
	    			}
	    			else
	    			{
	    				seenSoFar.put(other2, other.adj.get(other2));
	    			}
	    			total[index] += other.adj.get(other2);
	    		}
	    	}
	    	for(Node2 other: n2.adj.keySet())
	    	{
	    		if(n2.adj.get(other) * 323000/other.adj.size() < 50)
	    		{
	    			continue;
	    		}
	    		double skip = (((double)n2.adj.get(other) / (double)n1.count) / Math.log((double)other.count)) * 150;
				if(Math.random() > skip)
				{
					continue;
				}
	    		for(Node2 other2 : other.adj.keySet())
	    		{
	    			if(other.adj.get(other2) * 323000/other2.adj.size() < 50)
	        		{
	        			continue;
	        		}
	        		index = 0;

	        		if(seenSoFar.containsKey(other2))
	        		{
	        			seenSoFar.put(other2,  other.adj.get(other2) + seenSoFar.get(other2));
	        		}
	        		total[index] += other.adj.get(other2);
	    		}
	    		
	    		
	    	}
	    	
	    	for(Node2 n : seenSoFar.keySet())
	    	{
	    		index = 0;
	    		overlap[index] += seenSoFar.get(n);
	    	}
	    	for(int i = 0; i<1; i++)
	    		if(total[i]!=0)
	    		retval[i] = overlap[i]/total[i] ;
	    		else
	    		retval[i] = 0;
	    	
	    	
	    	return retval;
		}
	
	
	static int implicitCount = 0;

	/**
	 * This method calculates the probability of a given word in the relational network of any word in the relational
	 * networks of both compared words being shared between them.
	 * @param w1 The first WordEntry object to be compared
	 * @param w2 The second WordEntry object to be compared
	 * @return A double array containing the probability of given word of kth part of speech
	 * being explicitly shared between either word
	 */
	private static double[] implicitProb(WordEntry w1, WordEntry w2, RelevancyList r) throws IOException
	{
		long begin = System.currentTimeMillis();
		double[] retval = new double[2];

		
		for(int i = 0; i < 3; i++) //Cycles through each part of speech HashMap
		{
			
			if(i > 0)
			{
				break;
			}
			for(int j = 0; j < 3; j++)
			{
			
				if(j > 1)
				{
					break;
				}
				double count = 0;
				HashMap<String, Integer> seenSoFar = new HashMap<>(); //Keeps track of all words seen so far and the number of times they are seen
				HashMap<String, Integer> overlap = new HashMap<>(); //Keeps track of the words which are seen in the relation networks of both words and the number of total times they are seen
				
				for(String s : w1.relns[i].keySet()) //Cycles through each word in w1's current part of speech HashMap
				{
					if(w1.relns[i].get(s) < 0)
					{
						continue;
					}
					if(r.get(s).name.split("/").length == 2)
					{
						if(r.get(s).name.split("/")[1].equals("nnp"))
						{
							
							continue;
						}
					}
					else
					{
					}
					if(r.get(s).totalRelns == 0 || w1.totalRelns == 0)
					{
						continue;
					}
					double skip = (((double)w1.relns[i].get(s) / (double)w1.totalRelns) / Math.log((double)r.get(s).totalRelns)) * 1050;
					if(Math.random() > skip)
					{
						continue;
					}
					for(String ss : r.get(s).relns[j].keySet()) //Cycles through each word in the current word's current part of speech HashMap
					{
						if(r.get(s).relns[j].get(ss) < 0)
						{
							continue;
						}
						if(seenSoFar.containsKey(ss)) 
						{
							int k = seenSoFar.get(ss);
							k += r.get(s).relns[j].get(ss);
							seenSoFar.replace(ss, k);	
								
						}
						//Otherwise it is a newly encountered word so put it in seenSoFar
						else
						{
							seenSoFar.put(ss, r.get(s).relns[j].get(ss)); 
						}
						
						
						count += r.get(s).relns[j].get(ss);
					}
					
				}
				
				

				
				
				for(String s : w2.relns[i].keySet()) //Cycles through each word in w2's current part of speech HashMap
				{
					if(w2.relns[i].get(s) < 0)
					{
						continue;
					}
					
					if(s.split("/").length == 2)
					{
						if(s.split("/")[1].equals("nnp"))
						{
							continue;
						}
					}
					
					if(r.get(s).totalRelns == 0 || w2.totalRelns == 0)
					{
						continue;
					}
					double skip = (((double)w2.relns[i].get(s) / (double)w2.totalRelns) / Math.log((double)r.get(s).totalRelns)) * 250;
					if(Math.random() > skip)
					{
						continue;
					}
					for(String rr: r.get(s).relns[j].keySet()) // Cycles through each word in the current word's current part of speech HashMap
					{

						if(r.get(s).relns[j].get(rr) < 0)
						{
							continue;
						}
						if(seenSoFar.containsKey(rr)) 
						{
							//Then we extract the count and update it 
							int k = seenSoFar.get(rr);
							k += r.get(s).relns[j].get(rr);
									
							overlap.put(rr, k); //And add it to the overlap with its new count (times seen in w1 + times seen in w2)
						}
						//Otherwise it is a newly encountered word so put it in seenSoFar
						else
						{
							seenSoFar.put(rr, r.get(s).relns[j].get(rr)); 
						}
							
						count += r.get(s).relns[j].get(rr); //Regardless, the total count must be updated to reflect the current number of tokens seen
							
					}
					
					
					
				}

				double k = 0;
				for(String n : overlap.keySet())
				{
					k += overlap.get(n);
				}
		
		
				//Add to the appropriate index in the return array, input represents the probability of any given word in either w1's or w2's relation network sharing a word in their respective relation networks
				
				retval[3*i+j] = k/count;
				if(count == 0)
				{
					retval[3*i+j] = 0;
				}
				
			}
			
		}
	
		SynonymIdentifier.SimilarityVector.TSV[2] = .001*(System.currentTimeMillis()-begin);
		return retval;	
	}
	

	
	public static double posClass(WordEntry w1, WordEntry w2)
	{
		if(w1.pos.equals("NN") && w2.pos.equals("NN"))
		{
			return 1;
		}
		else if((w1.pos.equals("NN") && w2.pos.equals("JJ")) || (w2.pos.equals("NN") && w1.pos.equals("JJ")))
		{
			return 2;

		}
		else if((w1.pos.equals("NN") && w2.pos.equals("VB")) || (w2.pos.equals("NN") && w1.pos.equals("VB")))
		{
			return 3;

		}
		else if((w1.pos.equals("NN") && w2.pos.equals("RB")) || (w2.pos.equals("NN") && w1.pos.equals("RB")))
		{
			return 4;

		}
		else if(w1.pos.equals("JJ") && w2.pos.equals("JJ"))
		{
			return 5;

		}
		else if((w1.pos.equals("JJ") && w2.pos.equals("RB")) || (w2.pos.equals("JJ") && w1.pos.equals("RB")))
		{
			return 6;

		}
		else if((w1.pos.equals("JJ") && w2.pos.equals("VB")) || (w2.pos.equals("JJ") && w1.pos.equals("VB")))
		{
			return 7;

		}
		else if(w1.pos.equals("RB") && w2.pos.equals("RB"))
		{
			return 8;

		}
		else if((w1.pos.equals("VB") && w2.pos.equals("RB")) || (w2.pos.equals("VB") && w1.pos.equals("RB")))
		{
			return 9;

		}
		else if(w1.pos.equals("VB") && w2.pos.equals("VB"))
		{
			return 10;

		}
		else
		{
			return 0;
		}
		

		
		
	}
	
	public static double stringOverlap(WordEntry w1, WordEntry w2)
	{
		double max = 0;
		String name1 = w1.name.split("/")[0];
		String name2 = w2.name.split("/")[0];
		if(name1.length() < name2.length())
		{
			for(int i = name1.length(); i >= 0; i--)
			{
				
				for(int j = 0; j < name1.length(); j++)
				{

					if(i <= j)
					{
						continue;
					}
					
					if(i < j)
					{
						continue;
					}
					if(name2.contains(name1.substring(j, i)))
					{
						double len = i - j;
						if(len > max)
						{
							max = len;
						}
					}
				}
			}
			return max / name1.length();
		}
		else
		{
			for(int i = name2.length(); i > 0; i--)
			{
				for(int j = 0; j < name2.length(); j++)
				{
					if(i <= j)
					{
						continue;
					}
					
					if(name1.contains(name2.substring(j, i)))
					{
						double len = i - j;
						if(len > max)
						{
						
							max = len;
						}
					}
				}
			}
			return max / name2.length();
		}
	}
	
	public static double explicitDefOverlap(WordEntry w1, WordEntry w2, RelevancyList r)
	{
		long begin = System.currentTimeMillis();
		String w1n = w1.name.split("/")[0];;
		String w2n = w2.name.split("/")[0];
		
		
		System.setProperty("wordnet.database.dir", dictLoc);
		WordNetDatabase database = WordNetDatabase.getFileInstance();


		String def1 = "";
		//if(database.getSynsets(w1n)!=null)
		if(w1.pos.equals("NN") && database.getSynsets(w1n, SynsetType.NOUN, true).length>0)
		{
			def1 = database.getSynsets(w1n, SynsetType.NOUN, true)[0].getDefinition(); 
		}
		else if(w1.pos.equals("VB") && database.getSynsets(w1n, SynsetType.VERB, true).length>0)
		{
			def1 = database.getSynsets(w1n, SynsetType.VERB, true)[0].getDefinition(); 

		}
		else if(w1.pos.equals("JJ") && database.getSynsets(w1n, SynsetType.ADJECTIVE, true).length>0)
		{
			def1 = database.getSynsets(w1n, SynsetType.ADJECTIVE, true)[0].getDefinition(); 

		}
		else if(w1.pos.equals("JJ") && database.getSynsets(w1n, SynsetType.ADVERB, true).length>0)
		{
			def1 = database.getSynsets(w1n, SynsetType.ADVERB, true)[0].getDefinition(); 
		}
		else
		{
			SynonymIdentifier.SimilarityVector.TSV[9] = .001*(System.currentTimeMillis()-begin);
			return 0;
		}
		
		String def2 = "";
		//if(database.getSynsets(w2n)!=null)
		if(w2.pos.equals("NN") && database.getSynsets(w2n, SynsetType.NOUN, true).length>0)
		{
			def2 = database.getSynsets(w2n, SynsetType.NOUN, true)[0].getDefinition(); 
		}
		else if(w2.pos.equals("VB") && database.getSynsets(w2n, SynsetType.VERB, true).length>0)
		{
			def2 = database.getSynsets(w2n, SynsetType.VERB, true)[0].getDefinition(); 

		}
		else if(w2.pos.equals("JJ") && database.getSynsets(w2n, SynsetType.ADJECTIVE, true).length>0)
		{
			def2 = database.getSynsets(w2n, SynsetType.ADJECTIVE, true)[0].getDefinition(); 

		}
		else if(w2.pos.equals("JJ") && database.getSynsets(w2n, SynsetType.ADVERB, true).length>0)
		{
			def2 = database.getSynsets(w2n, SynsetType.ADVERB, true)[0].getDefinition(); 
		}
		else
		{
			SynonymIdentifier.SimilarityVector.TSV[9] = .001*(System.currentTimeMillis()-begin);
			return 0;
		}
		def1 = cleanedDef(def1);
		def2 = cleanedDef(def2);
		String[] def1a = def1.toLowerCase().replaceAll("[^a-zA-Z\\s]", "").split(" ");
		String[] def2a = def2.toLowerCase().replaceAll("[^a-zA-Z\\s]", "").split(" ");
		double count = 0;
		if(def1a.length < def2a.length)
		{
			for(int i = 0; i < def1a.length; i++)
			{
				count += contains(def1a[i], def2a);

			}
			SynonymIdentifier.SimilarityVector.TSV[9] = .001*(System.currentTimeMillis()-begin);
			return count / def1a.length;
		}
		else
		{
			for(int i = 0; i < def2a.length; i++)
			{
				
				
				count += contains(def2a[i], def1a);
				
			}
			SynonymIdentifier.SimilarityVector.TSV[9] = .001*(System.currentTimeMillis()-begin);
			return count / def2a.length;
		}
		
		
	}
	public static double contains(String w, String[] a)
	{
		double count = 0;
		for(String s : a)
		{
			if(w.equals(s))
			{
				count++;
			}
		}
		return count;
	}
	
	
	
}
