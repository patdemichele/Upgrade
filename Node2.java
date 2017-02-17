package SynId;
import java.io.Serializable;
import java.util.HashMap;

/*
 Author: Patrick DeMichele
*/ 
public class Node2 implements Serializable, Comparable
{
    String name;
    double activation;
    HashMap<Node2, Double> adj;
    int count;
     
    public Node2(String n)
    {
         
        adj = new HashMap<>();
        name = n;
         
    }
     
    @Override
    public String toString()
    {
        return name;
    }
     
    @Override
    public boolean equals(Object o)
    {
        if(o instanceof Node2)
        {
            if(((Node2) o).name.equals(name))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
     
    @Override
    public int hashCode()
    {
        return (name).hashCode();
    }
 
    @Override
    public int compareTo(Object o)
    {
        if(((Node2)o).activation > this.activation)
        {
            return 1;
        }
        else
        {
            return -1;
        }
    }
   
   
   
}