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

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;

import net.sf.javaml.core.DenseInstance;



public class ChisquareData
{
	
	public static double kappa(double fp, double tp, double fn, double tn)
	{
		double po = accuracy(fp, tp, fn, tp);
		double yes = ((tp + fn) / (tn + fp + fn + tp)) * ((tp + fp) / (tn + fp + fn + tp));
		double no = ((tn + fp) / (tn + fp + fn + tp)) * ((fn + tn)/ (tn + fp + fn + tp));
		double pe = yes + no;
		return (po - pe) / ( 1 - pe);
	}
	public static double accuracy(double falsPos, double truPos, double falsNeg, double truNeg)
	{
		return (truNeg + truPos) / (truPos+truNeg+falsPos+falsNeg);
	}
					
	
}