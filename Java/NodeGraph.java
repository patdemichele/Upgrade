package SynId;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;
/**
 *
 * @author Patrick DeMichele
 */

public class NodeGraph implements Serializable
{
	HashMap<String, Node2> allNode2s;
	int totalCount = 0;
	HashSet<String> hs = new HashSet<>();
	public static final void main(String[] args) throws IOException
	{
		
		NodeGraph ng = buildNodeGraph();
		ng.printNG("/home/jeremy/UpGrade/Corpora/NodeGraph2.txt");
		
		double orig = System.currentTimeMillis();
		ng = reconstructGraph("/home/jeremy/UpGrade/Corpora/NodeGraph.txt");
		System.out.println((System.currentTimeMillis() - orig)/1000);
		
	
		
	}
	private void fix() 
	{
		for(String s : allNode2s.keySet())
		{
			Node2 n = allNode2s.get(s);
			for(Node2 n2 : n.adj.keySet())
			{
				double d = n.adj.get(n2);
				
				double idf = 0;
				idf = ((double)allNode2s.size() )/ (1 + n2.adj.size());
				idf = Math.log(1 + idf);
				
				double ret = d*idf;
				ret = Math.round(ret*1000)/1000.0;
				n.adj.put(n2, ret);
			}
		}
	}
	
	public static NodeGraph buildNodeGraph()
	{
		String s1 = "/home/jeremy/Downloads/pos-tagged-domains.txt";
	
		NodeGraph ng = new NodeGraph();
		ng.buildNodeGraph(s1);
		
		System.out.println(ng.hs.size());
		return ng;

	}
	public void buildNodeGraph(String filename)
	{
		BufferedReader bf = null;
		try 
		{
			bf = new BufferedReader(new FileReader(new File(filename)));
		} catch (FileNotFoundException e) 
		{
			System.out.println(filename + " not found");
		}
		ArrayList<String> al = new ArrayList<>();
		try 
		{
			while(bf.ready())
			{
				String l= bf.readLine();
				l = l.toLowerCase();
				String[] a = l.split("[?!.]");
				
				for(String s : a)
				{
					String str = s.replaceAll("[^a-z0-9 /]", "").trim();
					
					al.add(str);
					
				}
			}
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		
		for(int i = 0; i < al.size(); i++)
		{
			if(i % 3 == 0)
			{
				if( i < al.size() - 2)
				{
					String s = al.get(i) + " " + al.get(i+1) + " " + al.get(i+2);
					String[] words = s.split(" ");
					
					for(int k = 0 ; k < words.length; k++)
					{

						if(words[k].trim().equals(""))
						{
							continue;
						}
						if(words[k].trim().matches("[a-z0-9]+?/[a-z0-9]+?"))
						{
							if(!isAcceptable(words[k].trim()))
							{
								continue;
							}
							hs.add(words[k].trim());
							for(int j = k + 1; j < words.length; j++)
							{
								if(words[j].trim().equals(""))
								{
									continue;
								}
								if(!isAcceptable(words[j]))
								{
									continue;
								}
								
								addReln(words[k].trim().split("/")[0], words[j].trim().split("/")[0]);
							}
						}
						
					}
				}
			}
		}
	
	}
	
	
	private boolean isAcceptable(String string)
	{
		if(!string.matches("[a-z0-9]+?/[a-z0-9]+?"))
		{
			return false;
		}
		String pos = string.split("/")[1];
		
		if(pos.contains("vb"))
		{
			return true;
		}
		else if(pos.contains("nn"))
		{
			return true;
		}
		else if(pos.contains("rb"))
		{
			return true;
		}
		else if(pos.contains("jj"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public NodeGraph()
	{
		allNode2s = new HashMap<>();
	}
	public void addReln(String s1, String s2)
	{
		totalCount++;

		if((totalCount % 1000) == 0)
		{
			System.out.println(totalCount);
		}
		Node2 n1 = null;
		Node2 n2 = null;
		
		
		
		
		if(allNode2s.containsKey(s1))
		{
			n1 = allNode2s.get(s1);
		}
		else
		{
			n1 = new Node2(s1);
			allNode2s.put(s1, n1);
		}
		
		
		

		if(allNode2s.containsKey(s2))
		{
			n2 = allNode2s.get(s2);
		}
		else
		{
			n2 = new Node2(s2);
			allNode2s.put(s2, n2);
		}
		
		n1.count++;
		n2.count++;
		
		if(n1.adj.containsKey(n2))
		{
			double count = n1.adj.get(n2);
			n1.adj.put(n2, count+1);
			
		}
		else
		{
			n1.adj.put(n2, 1.0);
		}
		
		
		if(n2.adj.containsKey(n1))
		{
			double count = n2.adj.get(n1);
			n2.adj.put(n1, count+1);
			
		}
		else
		{
			n2.adj.put(n1, 1.0);
		}
		
				
		
		
	}

	public void printNG(String filename) throws IOException
	{
		PrintWriter pw = new PrintWriter(new FileWriter(new File(filename)));
		
		int i = 0;
		System.out.println(allNode2s.size());
		for(String s : allNode2s.keySet())
		{
			if(i % 1000 == 0)
			{
				System.out.println(i);
			}
			i++;
			printNode2(allNode2s.get(s), pw);
			pw.println();
		}
		System.out.println(i);
		pw.close();
	}
	private void printNode2(Node2 Node2, PrintWriter pw) 
	{
		String s = Node2.name  + "/" + Node2.count + " ! ";
		
		for(Node2 n : Node2.adj.keySet())
		{
			if(n.name.equals(""))
			{
				continue;
			}
			s += n.name  + "/" + n.count + " " + Node2.adj.get(n) + "@";
		}
		
		pw.print(s);
	}
	
	public static NodeGraph reconstructGraph(String filename) throws IOException
	{
		BufferedReader bf = new BufferedReader(new FileReader(new File(filename)));
		int k = 0;
		NodeGraph ng = new NodeGraph();
		
		while(bf.ready())
		{
			if(k % 1000 == 0)
			{
				System.out.println(k);
			}
			k++;
			String s = bf.readLine();
			String[] one = s.split("!");
			String[] Node2 = one[0].split("/");
			String Node2Name = Node2[0].trim().toLowerCase();
			
			Node2 n;
			if(ng.allNode2s.containsKey(Node2Name))
			{
				n = ng.allNode2s.get(Node2Name);
			}
			else
			{
				n = new Node2(Node2Name);
			}
			ng.totalCount += Integer.parseInt(Node2[1].trim());
	
				
			n.count = Integer.parseInt(Node2[1].trim());
				
			String[] relns = one[1].split("@");
				
				
			for(int i = 0; i < relns.length; i++)
			{
				String[] reln = relns[i].trim().split(" ");
				String[] info = reln[0].split("/");
				
				try
				{
					Node2 newN;
					if(ng.allNode2s.containsKey(info[0].trim()))
					{
						newN = ng.allNode2s.get(info[0].trim());
					}
					else
					{
						newN = new Node2(info[0].trim());
						newN.count = Integer.parseInt(info[1].trim());
						ng.allNode2s.put(info[0].trim(), newN);
		
					}
						
		
						
					if(!n.adj.containsKey(newN))
					{
						n.adj.put(newN, Double.parseDouble(reln[1].trim()));
					}
					
						
					if(!newN.adj.containsKey(n))
					{
						newN.adj.put(n, Double.parseDouble(reln[1].trim()));
					}
						
				}
				catch(Exception e)
				{
					continue;
				}
					
			}
				
			ng.allNode2s.put(Node2Name, n);
		
			
			
		}
		bf.close();
		
		return ng;
	}
	public Node2 getWithoutPOS(String str)
	{
		int max = -100;
		ArrayList<Node2> word = new ArrayList<>();
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
		
	
		Node2 ret = null;
		for(Node2 w : word)
		{
			if(w.count > max)
			{
				ret = w;
				max = w.count;
			}

		}

		return ret;
		
		
		
	}
	public Node2 get(String string) 
	{
		return allNode2s.get(string);
	}
	public boolean contains(String string)
	{
		return allNode2s.get(string) == null? false: true;

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
	
	public void prune(int i) 
	{
		HashSet<Node2> buffer = new HashSet<>();
		for(String s : allNode2s.keySet())
		{
			Node2 n = allNode2s.get(s);
			if(n.count < i)
			{
				System.out.println(n);
				buffer.add(n);
			}
		}
		for(Node2 n : buffer)
		{
			remove(n);
		}
	}
	private void remove(Node2 n) 
	{
		allNode2s.remove(n.name);
		totalCount -= n.count;
		for(Node2 n2 : n.adj.keySet())
		{
			n2.adj.remove(n);
			n2.count -= n.adj.get(n2);
		}
	}
	
	class ProcessThread extends Thread
	{
		public String filename;
		
		public ProcessThread(String f)
		{
			filename = f;
		}
		@Override
		public void run()
		{
			BufferedReader bf = null;
			try 
			{
				bf = new BufferedReader(new FileReader(new File(filename)));
			} catch (FileNotFoundException e) 
			{
				System.out.println(filename + " not found");
			}
			ArrayList<String> al = new ArrayList<>();
			try 
			{
				while(bf.ready())
				{
					String l= bf.readLine();
					l = l.toLowerCase();
					String[] a = l.split("[?!.]");
					
					for(String s : a)
					{
						al.add(s.replaceAll("[^a-z0-9 ]", ""));
					}
				}
			} catch (IOException e) 
			{
				e.printStackTrace();
			}
			
			
			for(int i = 0; i < al.size(); i++)
			{
				if(i % 3 == 0)
				{
					if( i < al.size() - 2)
					{
						String s = al.get(i) + " " + al.get(i+1) + " " + al.get(i+2);
						String[] words = s.split(" ");
						
						for(int k = 0 ; k < words.length; k++)
						{
							if(words[k].equals(""))
							{
								continue;
							}
							for(int j = k + 1; j < words.length; j++)
							{
								if(words[j].equals(""))
								{
									continue;
								}
								addReln(words[k], words[j]);
							}
						}
					}
				}
			}
		}
		
	}
}
