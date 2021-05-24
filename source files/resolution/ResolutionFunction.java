package resolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.io.*;
import java.util.Arrays;
import java.lang.Object.*;

/**
 * This class a function, that contains disjuncts.
 * By default, it is commonly prepared for the resolution.
 * A function contains a list with disjuncts.
 * @author MatmanBJ
 * @version alpha 0.25
 */
public class ResolutionFunction
{
	// -------------------------------
	// ---------- VARIABLES ----------
	// -------------------------------
	
	private ArrayList<ResolutionDisjunct> disjuncts = new ArrayList<ResolutionDisjunct>();
	
	// -----------------------------
	// ---------- METHODS ----------
	// -----------------------------
	
	// ---------- CONSTRUCTORS ----------
	
	public ResolutionFunction () // default constructor
	{}
	
	public ResolutionFunction (String localJust) // special constructor for statements
	{
		Scanner localInputScanner = new Scanner(System.in);
		System.out.println("Repeated and same disjuncts will be deleted!");
		System.out.println("How many disjuncts do you want?");
		String [] localInputString = new String[localInputScanner.nextInt()];
		localInputScanner.nextLine();
		System.out.println("Input them:");
		for (int i = 0; i < localInputString.length; i++)
		{
			ArrayList<ResolutionVariable> localInputVariables = new ArrayList<ResolutionVariable>();
			localInputString[i] = localInputScanner.nextLine();
			
			boolean localEmpty = false;
			boolean localOne = false;
			
			String localInputVars [] = localInputString[i].split("\\s");
			for (int j = 0; j < localInputVars.length; j++)
			{
				if (!localInputVars[j].equals("") && !localInputVars[j].contains("+") && !localInputVars[j].equals("□") && !localInputVars[j].equals("1"))
				{
					if (localInputVars[j].charAt(0) == '!')
					{
						localInputVariables.add(new ResolutionVariable(false, localInputVars[j].substring(1)));
					}
					else
					{
						localInputVariables.add(new ResolutionVariable(true, localInputVars[j]));
					}
				}
				else if (localInputVars[j].equals("□"))
				{
					localEmpty = true;
				}
				else if (localInputVars[j].equals("1"))
				{
					localOne = true;
				}
			}
			
			ResolutionDisjunct localInputDisjunct = new ResolutionDisjunct(localInputVariables);
			localInputDisjunct.SetEmpty(localEmpty);
			localInputDisjunct.SetOne(localOne);
			
			disjuncts.add(localInputDisjunct);
			this.GetOneDisjunct(i + 1);
			
			//System.out.println("New disjunct? 0 -- no, 1 -- yes:");

		}
		this.RefreshD();
		this.SortD();
		this.Refresh();
		System.out.println("Current console input:");
		this.GetFunction();
		System.out.print("\n");
	}
	
	public ResolutionFunction (int localJust) // special constructor for predicates
	{
		char leftBrace = '(';
		char rightBrace = ')';
		
		Scanner localInputScanner = new Scanner(System.in);
		System.out.println("Repeated and same disjuncts will be deleted!");
		System.out.println("How many disjuncts do you want?");
		String [] localInputString = new String[localInputScanner.nextInt()];
		localInputScanner.nextLine();
		System.out.println("Input them:");
		for (int i = 0; i < localInputString.length; i++)
		{
			ArrayList<ResolutionPredicate> localInputPreds = new ArrayList<ResolutionPredicate>();
			localInputString[i] = localInputScanner.nextLine();
			
			boolean localEmpty = false;
			boolean localOne = false;
			
			String localInputPredicates [] = localInputString[i].split("\\s");
			for (int j = 0; j < localInputPredicates.length; j++)
			{
				if (!localInputPredicates[j].equals("") && !localInputPredicates[j].contains("+") && !localInputPredicates[j].equals("□") && !localInputPredicates[j].equals("1"))
				{
					boolean localDenial = true;
					
					if (localInputPredicates[j].charAt(0) == '!')
					{
						localInputPredicates[j] = localInputPredicates[j].substring(1);
						localDenial = false;
					}
					
					int localJ = 0;
					String localName = "";
					while (localInputPredicates[j].charAt(localJ) != '(')
					{
						localName = localName.concat(String.valueOf(localInputPredicates[j].charAt(localJ)));
						localJ = localJ + 1;
					}
					ResolutionPredicate localPredicate = new ResolutionPredicate(localDenial, localName);
					
					if (localInputPredicates[j].indexOf(leftBrace) + 1 != localInputPredicates[j].lastIndexOf(rightBrace))
					{
						ResolutionTerm.newFunction(localInputPredicates[j].substring(localInputPredicates[j].indexOf(leftBrace) + 1, localInputPredicates[j].lastIndexOf(rightBrace)), localPredicate);
					}
					else
					{
						ResolutionTerm.newFunction("", localPredicate);
					}
					
					localInputPreds.add(localPredicate);
				}
				else if (localInputPredicates[j].equals("□"))
				{
					localEmpty = true;
				}
				else if (localInputPredicates[j].equals("1"))
				{
					localOne = true;
				}
			}
			
			ResolutionDisjunct localInputDisjunct = new ResolutionDisjunct(localInputPreds, 1);
			localInputDisjunct.SetEmpty(localEmpty);
			localInputDisjunct.SetOne(localOne);
			
			disjuncts.add(localInputDisjunct);
			this.GetOneDisjunctPredicate(i + 1);

		}
		this.RefreshDPredicate();
		this.SortDPredicate();
		this.RefreshPredicate();
		System.out.println("Current console input:");
		this.GetFunctionPredicate();
		System.out.print("\n");
	}
	
	public ResolutionFunction (ResolutionFunction localFunction) // deep copy constructor
	{
		for (ResolutionDisjunct p : localFunction.GetDisjuncts())
		{
			disjuncts.add(new ResolutionDisjunct(p));
		}
	}
	
	// ---------- SETTERS ----------
	
	public void SetDisjuncts (ResolutionDisjunct localDisjuncts)
	{
		disjuncts.add(localDisjuncts);
	}
	
	// ---------- GETTERS ----------
	
	public ArrayList<ResolutionDisjunct> GetDisjuncts ()
	{
		return disjuncts;
	}
	
	// ---------- METHODS ----------
	
	/**
	 * This method consistently applies "Sort" method from "ResolutionDisjunct" class for each disjunct in the function.
	 * @see {@link resolution.ResolutionDisjunct#Sort() Sort()}
	 * @see {@link resolution.ResolutionDisjunct ResolutionDisjunct}
	 */
	public void SortD ()
	{
		for (int i = 0; i < disjuncts.size(); i++)
		{
			disjuncts.get(i).Sort();
		}
	}
	
	/**
	 * This method consistently applies "SortPredicate" method from "ResolutionDisjunct" class for each disjunct in the function.
	 * @see {@link resolution.ResolutionDisjunct#SortPredicate() SortPredicate()}
	 * @see {@link resolution.ResolutionDisjunct ResolutionDisjunct}
	 */
	public void SortDPredicate ()
	{
		for (int i = 0; i < disjuncts.size(); i++)
		{
			disjuncts.get(i).SortPredicate();
		}
	}
	
	/**
	 * This method consistently applies "Refresh" method from "ResolutionDisjunct" class for each disjunct in the function.
	 * @see {@link resolution.ResolutionDisjunct#Refresh() Refresh()}
	 * @see {@link resolution.ResolutionDisjunct ResolutionDisjunct}
	 * @see {@link resolution.ResolutionFunction#RefreshSize() RefreshSize}
	 * @see {@link resolution.ResolutionFunction#RefreshExtension() RefreshExtension}
	 * @see {@link resolution.ResolutionFunction#Refresh() Refresh}
	 */
	public void RefreshD ()
	{
		for (int i = 0; i < disjuncts.size(); i++)
		{
			disjuncts.get(i).Refresh();
		}
	}
	
	/**
	 * This method consistently applies "RefreshPredicate" method from "ResolutionDisjunct" class for each disjunct in the function.
	 * @see {@link resolution.ResolutionDisjunct#RefreshPredicate() RefreshPredicate()}
	 * @see {@link resolution.ResolutionDisjunct ResolutionDisjunct}
	 */
	public void RefreshDPredicate ()
	{
		for (int i = 0; i < disjuncts.size(); i++)
		{
			disjuncts.get(i).RefreshPredicate();
		}
	}
	
	/**
	 * This method sort all disjuncts in the function by their size (from short to long).
	 * Classic "Refresh" method doesn't included.
	 * @see {@link resolution.ResolutionFunction#RefreshD() RefreshD}
	 * @see {@link resolution.ResolutionFunction#RefreshExtension() RefreshExtension}
	 * @see {@link resolution.ResolutionFunction#Refresh() Refresh}
	 */
	public void RefreshSize ()
	{
		Comparator<ResolutionDisjunct> bySize = new Comparator<ResolutionDisjunct>()
	    {
	        @Override
	        public int compare(ResolutionDisjunct localRDOne, ResolutionDisjunct localRDTwo)
	        {
	            return Integer.compare(localRDOne.GetVariables().size() , localRDTwo.GetVariables().size());
	        }
	    };
		Collections.sort(disjuncts, bySize);
	}
	
	/**
	 * This method deletes disjuncts, that are duplicate or expanded version of previous.
	 * For example, program will delete A + B + C and A + B if there is A + B disjunct.
	 * It works only for new disjuncts, not for processed and default!
	 * @see {@link resolution.ResolutionFunction#RefreshD() RefreshD}
	 * @see {@link resolution.ResolutionFunction#RefreshSize() RefreshSize}
	 * @see {@link resolution.ResolutionFunction#Refresh() Refresh}
	 */
	public void RefreshExtension (int localSize)
	{
		for (int i = 0; i < localSize; i++)
		{
			for (int j = localSize; j < disjuncts.size(); j++)
			{
				boolean ekwality = true;
				if ((disjuncts.get(i).GetOne() == disjuncts.get(j).GetOne())
						&& (disjuncts.get(i).GetEmpty() == disjuncts.get(j).GetEmpty()))
				{
					int localMaxSize = 0;
					if (disjuncts.get(i).GetVariables().size() <= disjuncts.get(j).GetVariables().size())
					{
						localMaxSize = disjuncts.get(i).GetVariables().size();
					}
					else
					{
						ekwality = false;
					}
					// we have two situations: the last (j) disjunct should be less or equal to current (i)
					// disjunct -- so that we can delete him, else it would be unnesessary and wrong
					// because we should prevent expansion instead of giving a "chance" to the new disjunct!!!
					for (int k = 0; k < localMaxSize; k++)
					{
						boolean localEkwality = false;
						for (int l = 0; l < disjuncts.get(j).GetVariables().size(); l++)
						{
							if ((disjuncts.get(i).GetVariables().get(k).GetName().equals(disjuncts.get(j).GetVariables().get(l).GetName())) // don't forget "!"
									&&
									(disjuncts.get(i).GetVariables().get(k).GetDenial() ==
									disjuncts.get(j).GetVariables().get(l).GetDenial()))
							{
								//System.out.println((i + 1) + " " + (j + 1) + " " + (k + 1) + " " + (l + 1));
								localEkwality = true;
							}
						}
						if (localEkwality == false)
						{
							ekwality = false;
						}
					}
				}
				else
				{
					ekwality = false;
				}
				if (ekwality == true)
				{
					disjuncts.remove(j);
					j = j - 1;
				}
				else if (disjuncts.get(j).GetOne() == true)
				{
					disjuncts.remove(j);
					j = j - 1;
				}
			}
		}
	}
	
	/**
	 * This method deletes duplicate disjuncts.
	 * @see {@link resolution.ResolutionFunction#RefreshD() RefreshD}
	 * @see {@link resolution.ResolutionFunction#RefreshSize() RefreshSize}
	 * @see {@link resolution.ResolutionFunction#RefreshExtension() RefreshExtension}
	 */
	public void Refresh()
	{
		for (int i = 0; i < disjuncts.size() - 1; i++)
		{
			for (int j = i + 1; j < disjuncts.size(); j++)
			{
				boolean ekwality = true;
				if ((disjuncts.get(i).GetVariables().size() == disjuncts.get(j).GetVariables().size())
						&& (disjuncts.get(i).GetOne() == disjuncts.get(j).GetOne())
						&& (disjuncts.get(i).GetEmpty() == disjuncts.get(j).GetEmpty()))
				{
					for (int k = 0; k < disjuncts.get(i).GetVariables().size(); k++)
					{
						if (!(disjuncts.get(i).GetVariables().get(k).GetName().equals(disjuncts.get(j).GetVariables().get(k).GetName())) // don't forget "!"
								||
								(disjuncts.get(i).GetVariables().get(k).GetDenial() !=
								disjuncts.get(j).GetVariables().get(k).GetDenial()))
						{
							ekwality = false;
						}
					}
				}
				else
				{
					ekwality = false;
				}
				if (ekwality == true)
				{
					disjuncts.remove(j);
					j = j - 1;
				}
			}
		}
	}
	
	public void RefreshPredicate ()
	{
		for (int i = 0; i < this.GetDisjuncts().size() - 1; i++)
		{
			for (int j = i + 1; j < this.GetDisjuncts().size(); j++)
			{
				boolean ekwality = true;
				if ((disjuncts.get(i).GetPredicates().size() == disjuncts.get(j).GetPredicates().size())
						&& (disjuncts.get(i).GetOne() == disjuncts.get(j).GetOne())
						&& (disjuncts.get(i).GetEmpty() == disjuncts.get(j).GetEmpty()))
				{
					for (int k = 0; k < disjuncts.get(i).GetPredicates().size(); k++)
					{
						if (!(disjuncts.get(i).GetPredicates().get(k).equals(disjuncts.get(j).GetPredicates().get(k))) // don't forget "!"
								||
								(disjuncts.get(i).GetPredicates().get(k).GetDenial() !=
								disjuncts.get(j).GetPredicates().get(k).GetDenial()))
						{
							ekwality = false;
						}
					}
				}
				else
				{
					ekwality = false;
				}
				if (ekwality == true)
				{
					disjuncts.remove(j);
					j = j - 1;
				}
			}
		}
	}
	
	public void RefreshExtensionPredicate (int localSize)
	{
		for (int i = 0; i < localSize; i++)
		{
			for (int j = localSize; j < disjuncts.size(); j++)
			{
				boolean ekwality = true;
				if ((disjuncts.get(i).GetOne() == disjuncts.get(j).GetOne())
						&& (disjuncts.get(i).GetEmpty() == disjuncts.get(j).GetEmpty()))
				{
					int localMaxSize = 0;
					if (disjuncts.get(i).GetPredicates().size() <= disjuncts.get(j).GetPredicates().size())
					{
						localMaxSize = disjuncts.get(i).GetPredicates().size();
					}
					else
					{
						ekwality = false;
					}
					// we have two situations: the last (j) disjunct should be less or equal to current (i)
					// disjunct -- so that we can delete him, else it would be unnesessary and wrong
					// because we should prevent expansion instead of giving a "chance" to the new disjunct!!!
					for (int k = 0; k < localMaxSize; k++)
					{
						boolean localEkwality = false;
						for (int l = 0; l < disjuncts.get(j).GetPredicates().size(); l++)
						{
							if ((disjuncts.get(i).GetPredicates().get(k).equals(disjuncts.get(j).GetPredicates().get(l))) // don't forget "!"
									&&
									(disjuncts.get(i).GetPredicates().get(k).GetDenial() ==
									disjuncts.get(j).GetPredicates().get(l).GetDenial()))
							{
								//System.out.println((i + 1) + " " + (j + 1) + " " + (k + 1) + " " + (l + 1));
								localEkwality = true;
							}
						}
						if (localEkwality == false)
						{
							ekwality = false;
						}
					}
				}
				else
				{
					ekwality = false;
				}
				if (ekwality == true)
				{
					disjuncts.remove(j);
					j = j - 1;
				}
				else if (disjuncts.get(j).GetOne() == true)
				{
					disjuncts.remove(j);
					j = j - 1;
				}
			}
		}
	}
	
	public void RefreshExtensionPredicatePseudo (int localSize)
	{
		for (int i = 0; i < localSize; i++)
		{
			for (int j = localSize; j < disjuncts.size(); j++)
			{
				boolean ekwality = true;
				if ((disjuncts.get(i).GetOne() == disjuncts.get(j).GetOne())
						&& (disjuncts.get(i).GetEmpty() == disjuncts.get(j).GetEmpty()))
				{
					int localMaxSize = 0;
					if (disjuncts.get(i).GetPredicates().size() <= disjuncts.get(j).GetPredicates().size())
					{
						localMaxSize = disjuncts.get(i).GetPredicates().size();
					}
					else
					{
						ekwality = false;
					}
					// we have two situations: the last (j) disjunct should be less or equal to current (i)
					// disjunct -- so that we can delete him, else it would be unnesessary and wrong
					// because we should prevent expansion instead of giving a "chance" to the new disjunct!!!
					for (int k = 0; k < localMaxSize; k++)
					{
						boolean localEkwality = false;
						for (int l = 0; l < disjuncts.get(j).GetPredicates().size(); l++)
						{
							if ((disjuncts.get(i).GetPredicates().get(k).pseudoEquals(disjuncts.get(j).GetPredicates().get(l))) // don't forget "!"
									&&
									(disjuncts.get(i).GetPredicates().get(k).GetDenial() ==
									disjuncts.get(j).GetPredicates().get(l).GetDenial()))
							{
								//System.out.println((i + 1) + " " + (j + 1) + " " + (k + 1) + " " + (l + 1));
								localEkwality = true;
							}
						}
						if (localEkwality == false)
						{
							ekwality = false;
						}
					}
				}
				else
				{
					ekwality = false;
				}
				if (ekwality == true)
				{
					disjuncts.remove(j);
					j = j - 1;
				}
				else if (disjuncts.get(j).GetOne() == true)
				{
					disjuncts.remove(j);
					j = j - 1;
				}
			}
		}
	}
	
	public void RefreshExtensionPredicateSize (int localSize)
	{
		for (int i = 0; i < localSize; i++)
		{
			for (int j = localSize; j < disjuncts.size(); j++)
			{
				boolean ekwality = true;
				if ((disjuncts.get(i).GetOne() == disjuncts.get(j).GetOne())
						&& (disjuncts.get(i).GetEmpty() == disjuncts.get(j).GetEmpty()))
				{
					int localMaxSize = 0;
					if (disjuncts.get(i).GetPredicates().size() <= disjuncts.get(j).GetPredicates().size())
					{
						localMaxSize = disjuncts.get(i).GetPredicates().size();
					}
					else
					{
						ekwality = false;
					}
					// we have two situations: the last (j) disjunct should be less or equal to current (i)
					// disjunct -- so that we can delete him, else it would be unnesessary and wrong
					// because we should prevent expansion instead of giving a "chance" to the new disjunct!!!
					for (int k = 0; k < localMaxSize; k++)
					{
						boolean localEkwality = false;
						for (int l = 0; l < disjuncts.get(j).GetPredicates().size(); l++)
						{
							if ((disjuncts.get(i).GetPredicates().get(k).sizeEquals(disjuncts.get(j).GetPredicates().get(l))) // don't forget "!"
									&&
									(disjuncts.get(i).GetPredicates().get(k).GetDenial() ==
									disjuncts.get(j).GetPredicates().get(l).GetDenial()))
							{
								//System.out.println((i + 1) + " " + (j + 1) + " " + (k + 1) + " " + (l + 1));
								localEkwality = true;
							}
						}
						if (localEkwality == false)
						{
							ekwality = false;
						}
					}
				}
				else
				{
					ekwality = false;
				}
				if (ekwality == true)
				{
					disjuncts.remove(j);
					j = j - 1;
				}
				else if (disjuncts.get(j).GetOne() == true)
				{
					disjuncts.remove(j);
					j = j - 1;
				}
			}
		}
	}
	
	/**
	 * This method output statement disjunct in console, while you input your function from console or file.
	 */
	public void GetOneDisjunct (int localI) // for console-constructor
	{
		ResolutionDisjunct localDisjunct = disjuncts.get(disjuncts.size() - 1);
		System.out.print("Input " + localI + ": ");
		System.out.println(localDisjunct.toString());
	}
	
	public void GetOneDisjunctPredicate (int localI) // for console-constructor
	{
		ResolutionDisjunct localDisjunct = disjuncts.get(disjuncts.size() - 1);
		System.out.print("Input " + localI + ": ");
		System.out.println(localDisjunct.toStringPredicate());
	}
	
	/**
	 * This method output statement function in console.
	 */
	public void GetFunction ()
	{
		int i = 1;
		for (ResolutionDisjunct localDisjunct : disjuncts)
		{
			System.out.print(localDisjunct.toOutputString(i));
			i = i + 1;
		}
	}
	
	/**
	 * This method output predicate function in console.
	 */
	public void GetFunctionPredicate ()
	{
		int i = 1;
		for (ResolutionDisjunct localDisjunct : disjuncts)
		{
			System.out.print(localDisjunct.toOutputStringPredicate(i));
			i = i + 1;
		}
	}
	
	public void ResolutionFileInput (String localFileName)
	{
		try(FileReader reader = new FileReader(localFileName))
        {
            char[] localBufferChar = new char[256];
            String localBufferString = new String("");
            String localBufferStringArray [];
            int c;
            while((c = reader.read(localBufferChar)) > 0)
            {
                if(c < 256)
                {
                    localBufferChar = Arrays.copyOf(localBufferChar, c);
                }
                localBufferString = localBufferString + String.copyValueOf(localBufferChar);
            } 
            localBufferStringArray = localBufferString.split("\n");
            System.out.println("Current file input:");
            for (int i = 0; i < localBufferStringArray.length; i++)
    		{
    			ArrayList<ResolutionVariable> localInputVariables = new ArrayList<ResolutionVariable>();
    			boolean localEmpty = false;
    			boolean localOne = false;
    			
    			String localInputVars [] = localBufferStringArray[i].split("\\s");
    			for (int j = 0; j < localInputVars.length; j++)
    			{
    				if (!localInputVars[j].equals("") && !localInputVars[j].contains("+") && !localInputVars[j].equals("□") && !localInputVars[j].equals("1"))
    				{
    					if (localInputVars[j].charAt(0) == '!')
    					{
    						localInputVariables.add(new ResolutionVariable(false, localInputVars[j].substring(1)));
    					}
    					else
    					{
    						localInputVariables.add(new ResolutionVariable(true, localInputVars[j]));
    					}
    				}
    				else if (localInputVars[j].equals("□"))
    				{
    					localEmpty = true;
    				}
    				else if (localInputVars[j].equals("1"))
    				{
    					localOne = true;
    				}
    			}
    			
    			ResolutionDisjunct localInputDisjunct = new ResolutionDisjunct(localInputVariables);
    			localInputDisjunct.SetEmpty(localEmpty);
    			localInputDisjunct.SetOne(localOne);
    			
    			disjuncts.add(localInputDisjunct);
    			this.GetOneDisjunct(i + 1);
    		}
    		this.RefreshD();
    		this.SortD();
    		this.Refresh();
    		System.out.print("\n");
        }
        catch(IOException ex)
		{
            System.out.println(ex.getMessage());
        }
	}
	
	public void ResolutionFileInputPredicate (String localFileName)
	{
		try(FileReader reader = new FileReader(localFileName))
        {
			char leftBrace = '(';
			char rightBrace = ')';
			
            char[] localBufferChar = new char[256];
            String localBufferString = new String("");
            String localBufferStringArray [];
            int c;
            while((c = reader.read(localBufferChar)) > 0)
            {
                if(c < 256)
                {
                    localBufferChar = Arrays.copyOf(localBufferChar, c);
                }
                localBufferString = localBufferString + String.copyValueOf(localBufferChar);
            } 
            localBufferStringArray = localBufferString.split("\n");
            System.out.println("Current file input:");
            for (int i = 0; i < localBufferStringArray.length; i++)
    		{
    			ArrayList<ResolutionPredicate> localInputPreds = new ArrayList<ResolutionPredicate>();
    			
    			boolean localEmpty = false;
    			boolean localOne = false;
    			
    			String localInputPredicates [] = localBufferStringArray[i].split("\\s");
    			for (int j = 0; j < localInputPredicates.length; j++)
    			{
    				if (!localInputPredicates[j].equals("") && !localInputPredicates[j].contains("+") && !localInputPredicates[j].equals("□") && !localInputPredicates[j].equals("1"))
    				{
    					boolean localDenial = true;
    					
    					if (localInputPredicates[j].charAt(0) == '!')
    					{
    						localInputPredicates[j] = localInputPredicates[j].substring(1);
    						localDenial = false;
    					}
    					
    					int localJ = 0;
    					String localName = "";
    					while (localInputPredicates[j].charAt(localJ) != '(')
    					{
    						localName = localName.concat(String.valueOf(localInputPredicates[j].charAt(localJ)));
    						localJ = localJ + 1;
    					}
    					ResolutionPredicate localPredicate = new ResolutionPredicate(localDenial, localName);
    					
    					if (localInputPredicates[j].indexOf(leftBrace) + 1 != localInputPredicates[j].lastIndexOf(rightBrace))
    					{
    						ResolutionTerm.newFunction(localInputPredicates[j].substring(localInputPredicates[j].indexOf(leftBrace) + 1, localInputPredicates[j].lastIndexOf(rightBrace)), localPredicate);
    					}
    					else
    					{
    						ResolutionTerm.newFunction("", localPredicate);
    					}
    					
    					localInputPreds.add(localPredicate);
    				}
    				else if (localInputPredicates[j].equals("□"))
    				{
    					localEmpty = true;
    				}
    				else if (localInputPredicates[j].equals("1"))
    				{
    					localOne = true;
    				}
    			}
    			
    			ResolutionDisjunct localInputDisjunct = new ResolutionDisjunct(localInputPreds, 1);
    			localInputDisjunct.SetEmpty(localEmpty);
    			localInputDisjunct.SetOne(localOne);
    			
    			disjuncts.add(localInputDisjunct);
    			this.GetOneDisjunctPredicate(i + 1);
    		}
    		this.RefreshDPredicate();
    		this.SortDPredicate();
    		this.RefreshPredicate();
    		System.out.print("\n");
        }
        catch(IOException ex)
		{
            System.out.println(ex.getMessage());
        }
	}
	
	public void ResolutionFileReadable (String localFileName)
	{
		try (FileWriter writer = new FileWriter(localFileName, false))
        {
			int i = 1;
			String localOut [] = new String[this.GetDisjuncts().size()];
			for (ResolutionDisjunct localDisjunct : disjuncts)
			{
				ArrayList<ResolutionVariable> variables = localDisjunct.GetVariables();
				localOut[i - 1] = localDisjunct.toReadableString();
				i = i + 1;
			}
			i = 1;
			for (ResolutionDisjunct localDisjunct : disjuncts)
			{
				String localString = localOut[i - 1];
				writer.write(localString);
				writer.append('\n');
				i = i + 1;
			}
            writer.flush();
        }
        catch(IOException localException)
		{
            System.out.println(localException.getMessage());
        }
	}
	
	public void ResolutionFileUnreadable (String localFileName)
	{
		try (FileWriter writer = new FileWriter(localFileName, false))
        {
			int i = 1;
			String localOut [] = new String[this.GetDisjuncts().size()];
			for (ResolutionDisjunct localDisjunct : disjuncts)
			{
				ArrayList<ResolutionVariable> variables = localDisjunct.GetVariables();
				localOut[i - 1] = localDisjunct.toOutputString(i);
				i = i + 1;
			}
			i = 1;
			for (ResolutionDisjunct localDisjunct : disjuncts)
			{
				String localString = localOut[i - 1];
				writer.write(localString);
				i = i + 1;
			}
            writer.flush();
        }
        catch(IOException localException)
		{
            System.out.println(localException.getMessage());
        }
	}
	
	/**
	 * [DEMONSTRATION]
	 * Parameter for console: "all".
	 * This method makes all possible disjuncts.
	 * Then it stops, when new ArrayList of disjuncts is empty.
	 * The method will work until an empty disjunct will be found (doesn't matter if it has found an empty one).
	 * WARNING: IT MAY NOT STOP!
	 * @author MatmanBJ
	 */
	public ResolutionFunction ResolutionAll ()
	{
		int i;
		int j;
		int k;
		int l;
		int c;
		boolean repeat = true;
		ResolutionFunction localF = new ResolutionFunction();
		localF.GetDisjuncts().addAll(disjuncts);
		disjuncts.removeAll(disjuncts);
		while (repeat == true)
		{
			ResolutionFunction localFR = new ResolutionFunction();
			int d = disjuncts.size();
			disjuncts.addAll(localF.GetDisjuncts());
			for (i = 0; i < disjuncts.size() - 1; i++)
			{
				ResolutionDisjunct localDisjunct = disjuncts.get(i);
				ArrayList<ResolutionVariable> variables = localDisjunct.GetVariables();
				if (i < d)
				{
					c = d;
				}
				else
				{
					c = i + 1;
				}
				for (j = c; j < disjuncts.size(); j++)
				{
					ResolutionDisjunct localDisjunctTwo = disjuncts.get(j);
					ArrayList<ResolutionVariable> variablesTwo = localDisjunctTwo.GetVariables();
					for (k = 0; k < variables.size(); k++)
					{
						ResolutionVariable localVariable = variables.get(k);
						for (l = 0; l < variablesTwo.size(); l++)
						{
							ResolutionVariable localVariableTwo = variablesTwo.get(l);
							if ((localVariable.GetName().equals(localVariableTwo.GetName())) && (localVariable.GetDenial() != localVariableTwo.GetDenial()))
							{
								ResolutionDisjunct localNewDisjunct = new ResolutionDisjunct();
								
								localNewDisjunct.GetVariables().addAll(localDisjunct.GetVariables());
								ResolutionVariable x = localNewDisjunct.GetVariables().get(k);
								localNewDisjunct.GetVariables().remove(x);
								
								localNewDisjunct.GetVariables().addAll(localDisjunctTwo.GetVariables());
								ResolutionVariable y = localNewDisjunct.GetVariables().get(localDisjunct.GetVariables().size() - 1 + l);
								localNewDisjunct.GetVariables().remove(y);
								int [] localParents = {localDisjunct.GetID(), localDisjunctTwo.GetID()};
								localNewDisjunct.SetParents(localParents);
								
								localNewDisjunct.SetContrary(String.valueOf(localVariable.GetName()));
								
								localNewDisjunct.Refresh();
								localNewDisjunct.Sort();
								
								localFR.SetDisjuncts(localNewDisjunct);
							}
						}
					}
				}
			}
			if(localFR.GetDisjuncts().size() == 0)
			{
				repeat = false;
			}
			localF = localFR;
		}
		return localF;
	}
	
	/**
	 * [DEMONSTRATION]
	 * Parameter for console: "all find".
	 * This method makes all possible disjuncts until it will find an empty one.
	 * Then it stops (breaks) all the loops.
	 * The method will work until an empty disjunct will be found or all possible disjuncts will be found.
	 * WARNING: IT MAY NOT STOP!
	 * @author MatmanBJ
	 */
	public void ResolutionAllFind ()
	{
		int i;
		int j;
		int k;
		int l;
		int c;
		boolean repeat = true;
		ResolutionFunction localF = new ResolutionFunction();
		localF.GetDisjuncts().addAll(disjuncts);
		disjuncts.removeAll(disjuncts);
		while (repeat == true)
		{
			ResolutionFunction localFR = new ResolutionFunction();
			int d = disjuncts.size();
			disjuncts.addAll(localF.GetDisjuncts());
			for (i = 0; i < disjuncts.size() - 1; i++)
			{
				if (repeat == false)
				{
					break;
				}
				ResolutionDisjunct localDisjunct = disjuncts.get(i);
				ArrayList<ResolutionVariable> variables = localDisjunct.GetVariables();
				if (i < d)
				{
					c = d;
				}
				else
				{
					c = i + 1;
				}
				for (j = c; j < disjuncts.size(); j++)
				{
					if (repeat == false)
					{
						break;
					}
					ResolutionDisjunct localDisjunctTwo = disjuncts.get(j);
					ArrayList<ResolutionVariable> variablesTwo = localDisjunctTwo.GetVariables();
					for (k = 0; k < variables.size(); k++)
					{
						if (repeat == false)
						{
							break;
						}
						ResolutionVariable localVariable = variables.get(k);
						for (l = 0; l < variablesTwo.size(); l++)
						{
							if (repeat == false)
							{
								break;
							}
							ResolutionVariable localVariableTwo = variablesTwo.get(l);
							if ((localVariable.GetName().equals(localVariableTwo.GetName())) && (localVariable.GetDenial() != localVariableTwo.GetDenial()))
							{
								ResolutionDisjunct localNewDisjunct = new ResolutionDisjunct();
								
								localNewDisjunct.GetVariables().addAll(localDisjunct.GetVariables());
								ResolutionVariable x = localNewDisjunct.GetVariables().get(k);
								localNewDisjunct.GetVariables().remove(x);
								
								localNewDisjunct.GetVariables().addAll(localDisjunctTwo.GetVariables());
								ResolutionVariable y = localNewDisjunct.GetVariables().get(localDisjunct.GetVariables().size() - 1 + l);
								localNewDisjunct.GetVariables().remove(y);
								int [] localParents = {localDisjunct.GetID(), localDisjunctTwo.GetID()};
								localNewDisjunct.SetParents(localParents);
								
								localNewDisjunct.SetContrary(String.valueOf(localVariable.GetName()));
								
								localNewDisjunct.Refresh();
								localNewDisjunct.Sort();
								
								if (localNewDisjunct.GetOne() == false && localNewDisjunct.GetVariables().size() == 0)
								{
									localNewDisjunct.SetEmpty(true);
									repeat = false;
								}
								localFR.SetDisjuncts(localNewDisjunct);
							}
						}
					}
				}
			}
			if (repeat == false)
			{
				disjuncts.addAll(localFR.GetDisjuncts());
				//System.out.println("The □ has been found (DONE)!!!");
			}
			if(localFR.GetDisjuncts().size() == 0)
			{
				repeat = false;
				//System.out.println("The □ hasn't been found (NOT DONE)!!!");
			}
			localF = localFR;
		}
	}
	
	// ----------------------------------------
	// ---------- RESOLUTION METHODS ----------
	// ----------------------------------------
	
	// ---------- A GIANT OF THOUHT, A FATHER OF RUSSIAN RES(V)OLUTION ----------
	
	/**
	 * [DEMONSTRATION]
	 * Parameter for console: "unique".
	 * This method makes all possible, but unique disjuncts
	 * (first gotten disjunct is new unique, other will be deleted).
	 * I guess it's a remake of the famous "ResolutionHash" (absolutely LEGENDARY function)!
	 * The method will work until all unique disjunct would be find.
	 * @author MatmanBJ
	 */
	public void ResolutionAllUnique ()
	{
		int i;
		int j;
		int k;
		int l;
		int c;
		boolean repeat = true;
		this.SortD();
		this.RefreshD();
		ResolutionFunction localF = new ResolutionFunction();
		localF.GetDisjuncts().addAll(disjuncts);
		disjuncts.removeAll(disjuncts);
		while (repeat == true)
		{
			ResolutionFunction localFR = new ResolutionFunction();
			int d = disjuncts.size();
			localF.Refresh();
			disjuncts.addAll(localF.GetDisjuncts());
			this.Refresh(); // it's only here, because at the end it doesn't work
			for (i = 0; i < disjuncts.size() - 1; i++)
			{
				ResolutionDisjunct localDisjunct = disjuncts.get(i);
				ArrayList<ResolutionVariable> variables = localDisjunct.GetVariables();
				if (i < d)
				{
					c = d;
				}
				else
				{
					c = i + 1;
				}
				for (j = c; j < disjuncts.size(); j++)
				{
					ResolutionDisjunct localDisjunctTwo = disjuncts.get(j);
					ArrayList<ResolutionVariable> variablesTwo = localDisjunctTwo.GetVariables();
					for (k = 0; k < variables.size(); k++)
					{
						ResolutionVariable localVariable = variables.get(k);
						for (l = 0; l < variablesTwo.size(); l++)
						{
							ResolutionVariable localVariableTwo = variablesTwo.get(l);
							if ((localVariable.GetName().equals(localVariableTwo.GetName())) && (localVariable.GetDenial() != localVariableTwo.GetDenial()))
							{
								ResolutionDisjunct localNewDisjunct = new ResolutionDisjunct();
								
								localNewDisjunct.GetVariables().addAll(localDisjunct.GetVariables());
								ResolutionVariable x = localNewDisjunct.GetVariables().get(k);
								localNewDisjunct.GetVariables().remove(x);
								
								localNewDisjunct.GetVariables().addAll(localDisjunctTwo.GetVariables());
								ResolutionVariable y = localNewDisjunct.GetVariables().get(localDisjunct.GetVariables().size() - 1 + l);
								localNewDisjunct.GetVariables().remove(y);
								int [] localParents = {localDisjunct.GetID(), localDisjunctTwo.GetID()}; // create parent's numbers array
								localNewDisjunct.SetParents(localParents); // set parent's numbers
								
								localNewDisjunct.SetContrary(String.valueOf(localVariable.GetName())); // set parent's contrary variable
								
								localNewDisjunct.Refresh();
								localNewDisjunct.Sort();
								
								localFR.SetDisjuncts(localNewDisjunct);
							}
						}
					}
				}
			}
			if(localFR.GetDisjuncts().size() == 0)
			{
				repeat = false;
			}
			localF = localFR;
		}
	}
	
	// ---------- SATURATION STRATEGY ----------
	
	/**
	 * Parameter for console: "saturation".
	 * This method makes all possible disjuncts (it doesn't delete them)
	 * until it doesn't find empty disjunct. It's a very long and unoptimized method for big data,
	 * so, please, use it for demonstration.
	 * The method will work until the empty disjunct would be find.
	 * @author MatmanBJ
	 */
	public void ResolutionSaturation () // saturation strategy demonstration
	{
		int i;
		int j;
		int k;
		int l;
		int c;
		int iter = 1;
		int p = 0;
		int q = 0;
		int removeIndex = 0;
		this.RefreshD();
		this.SortD();
		boolean repeat = true; // repeating indicator
		boolean adding = false; // adding disjuncts in the end indicator
		ResolutionFunction localF = new ResolutionFunction();
		localF.GetDisjuncts().addAll(disjuncts);
		disjuncts.removeAll(disjuncts);
		while (repeat == true)
		{
			ResolutionFunction localFR = new ResolutionFunction();
			int d = disjuncts.size();
			disjuncts.addAll(localF.GetDisjuncts());
			for (i = 0; i < disjuncts.size() - 1; i++)
			{
				ResolutionDisjunct localDisjunct = disjuncts.get(i);
				ArrayList<ResolutionVariable> variables = localDisjunct.GetVariables();
				if (i < d)
				{
					c = d;
				}
				else
				{
					c = i + 1;
				}
				for (j = c; j < disjuncts.size(); j++)
				{
					ResolutionDisjunct localDisjunctTwo = disjuncts.get(j);
					ArrayList<ResolutionVariable> variablesTwo = localDisjunctTwo.GetVariables();
					for (k = 0; k < variables.size(); k++)
					{
						ResolutionVariable localVariable = variables.get(k);
						for (l = 0; l < variablesTwo.size(); l++)
						{
							ResolutionVariable localVariableTwo = variablesTwo.get(l);
							if ((localVariable.GetName().equals(localVariableTwo.GetName())) && (localVariable.GetDenial() != localVariableTwo.GetDenial()))
							{
								ResolutionDisjunct localNewDisjunct = new ResolutionDisjunct();
								
								localNewDisjunct.GetVariables().addAll(localDisjunct.GetVariables());
								ResolutionVariable x = localNewDisjunct.GetVariables().get(k);
								localNewDisjunct.GetVariables().remove(x);
								
								localNewDisjunct.GetVariables().addAll(localDisjunctTwo.GetVariables());
								ResolutionVariable y = localNewDisjunct.GetVariables().get(localDisjunct.GetVariables().size() - 1 + l);
								localNewDisjunct.GetVariables().remove(y);
								int [] localParents = {localDisjunct.GetID(), localDisjunctTwo.GetID()}; // create parent's numbers array
								localNewDisjunct.SetParents(localParents); // set parent's numbers
								
								localNewDisjunct.SetContrary(String.valueOf(localVariable.GetName())); // set parent's contrary variable
								
								localNewDisjunct.Refresh();
								localNewDisjunct.Sort();
								
								if (localNewDisjunct.GetEmpty() && repeat)
								{
									repeat = false;
									adding = true;
									removeIndex = localFR.GetDisjuncts().size() + 1;
								}
								
								localFR.SetDisjuncts(localNewDisjunct);
							}
						}
					}
				}
			}
			if(localFR.GetDisjuncts().size() == 0)
			{
				repeat = false;
			}
			localF = localFR;
			if (adding)
			{
				q = localF.GetDisjuncts().size();
				for (p = removeIndex; p < q; p++)
				{
					localF.GetDisjuncts().remove(removeIndex);
				}
				disjuncts.addAll(localF.GetDisjuncts());
			}
		}
	}
	
	// ---------- PREFERENCE STRATEGY ----------
	
	/**
	 * Parameter for console: "preference".
	 * This method makes all possible disjuncts (it doesn't delete them),
	 * but it starts from short and ends at long disjuncts (so, that's why it calls "preference").
	 * When you see a console output, you can see a mass discrepancy of number and ID (including inconsistency in the parents).
	 * It happens, because at every iteration it sort disjuncts by their size to keep an algorithm workable,
	 * but it remembers another disjuncts sequence for output to show correct order of their creating.
	 * The method will work until the empty disjunct would be find.
	 * @author MatmanBJ
	 */
	public void ResolutionPreference ()
	{
		int i;
		int j;
		int k;
		int l;
		int c;
		int iter = 1;
		int p = 0;
		int q = 0;
		int removeIndex = 0;
		boolean repeat = true; // repeating indicator
		boolean adding = false; // adding disjuncts in the end indicator
		this.RefreshD();
		this.SortD();
		ResolutionFunction localF = new ResolutionFunction();
		ResolutionFunction localClassicRF = new ResolutionFunction();
		localF.GetDisjuncts().addAll(disjuncts);
		disjuncts.removeAll(disjuncts);
		while (repeat == true)
		{
			ResolutionFunction localFR = new ResolutionFunction();
			int d = disjuncts.size();
			localF.RefreshSize();
			localClassicRF.GetDisjuncts().addAll(localF.GetDisjuncts());
			this.RefreshSize();
			disjuncts.addAll(localF.GetDisjuncts());
			for (i = 0; i < disjuncts.size() - 1; i++)
			{
				ResolutionDisjunct localDisjunct = disjuncts.get(i);
				ArrayList<ResolutionVariable> variables = localDisjunct.GetVariables();
				if (i < d)
				{
					c = d;
				}
				else
				{
					c = i + 1;
				}
				for (j = c; j < disjuncts.size(); j++)
				{
					ResolutionDisjunct localDisjunctTwo = disjuncts.get(j);
					ArrayList<ResolutionVariable> variablesTwo = localDisjunctTwo.GetVariables();
					for (k = 0; k < variables.size(); k++)
					{
						ResolutionVariable localVariable = variables.get(k);
						for (l = 0; l < variablesTwo.size(); l++)
						{
							ResolutionVariable localVariableTwo = variablesTwo.get(l);
							if ((localVariable.GetName().equals(localVariableTwo.GetName())) && (localVariable.GetDenial() != localVariableTwo.GetDenial()))
							{
								ResolutionDisjunct localNewDisjunct = new ResolutionDisjunct();
								
								localNewDisjunct.GetVariables().addAll(localDisjunct.GetVariables());
								ResolutionVariable x = localNewDisjunct.GetVariables().get(k);
								localNewDisjunct.GetVariables().remove(x);
								
								localNewDisjunct.GetVariables().addAll(localDisjunctTwo.GetVariables());
								ResolutionVariable y = localNewDisjunct.GetVariables().get(localDisjunct.GetVariables().size() - 1 + l);
								localNewDisjunct.GetVariables().remove(y);
								int [] localParents = {localDisjunct.GetID(), localDisjunctTwo.GetID()}; // create parent's numbers array
								localNewDisjunct.SetParents(localParents); // set parent's numbers
								
								localNewDisjunct.SetContrary(String.valueOf(localVariable.GetName())); // set parent's contrary variable
								
								localNewDisjunct.Refresh();
								localNewDisjunct.Sort();
								
								if (localNewDisjunct.GetEmpty() && repeat)
								{
									repeat = false;
									adding = true;
									removeIndex = localFR.GetDisjuncts().size() + 1;
								}
								
								localFR.SetDisjuncts(localNewDisjunct);
							}
						}
					}
				}
			}
			if(localFR.GetDisjuncts().size() == 0)
			{
				repeat = false;
			}
			localF = localFR;
			if (adding)
			{
				q = localF.GetDisjuncts().size();
				for (p = removeIndex; p < q; p++)
				{
					localF.GetDisjuncts().remove(removeIndex);
				}
				localF.RefreshSize();
				localClassicRF.GetDisjuncts().addAll(localF.GetDisjuncts());
				disjuncts.addAll(localF.GetDisjuncts());
				disjuncts.removeAll(disjuncts);
				disjuncts.addAll(localClassicRF.GetDisjuncts());
			}
		}
	}
	
	// ---------- STRIKEOUT STRATEGY ----------
	
	/**
	 * Parameter for console: "strikeout".
	 * This method makes only non-expanding (which don't consist full of any other disjunct) disjuncts.
	 * Also, it means it will delete a duplicate disjuncts. The method will work until the empty disjunct would be find.
	 * @author MatmanBJ
	 */
	public void ResolutionStrikeout ()
	{		
		int i;
		int j;
		int k;
		int l;
		int c;
		int iter = 1;
		int p = 0;
		int q = 0;
		int removeIndex = 0;
		boolean repeat = true;
		boolean adding = false; // adding disjuncts in the end indicator
		this.RefreshD();
		this.SortD();
		ResolutionFunction localF = new ResolutionFunction();
		localF.GetDisjuncts().addAll(disjuncts);
		disjuncts.removeAll(disjuncts);
		while (repeat == true)
		{
			ResolutionFunction localFR = new ResolutionFunction();
			int d = disjuncts.size();
			localF.Refresh();
			disjuncts.addAll(localF.GetDisjuncts());
			this.RefreshExtension(d); // it's only here, because at the end it doesn't work
			for (i = 0; i < disjuncts.size() - 1; i++)
			{
				ResolutionDisjunct localDisjunct = disjuncts.get(i);
				ArrayList<ResolutionVariable> variables = localDisjunct.GetVariables();
				if (i < d)
				{
					c = d;
				}
				else
				{
					c = i + 1;
				}
				for (j = c; j < disjuncts.size(); j++)
				{
					ResolutionDisjunct localDisjunctTwo = disjuncts.get(j);
					ArrayList<ResolutionVariable> variablesTwo = localDisjunctTwo.GetVariables();
					for (k = 0; k < variables.size(); k++)
					{
						ResolutionVariable localVariable = variables.get(k);
						for (l = 0; l < variablesTwo.size(); l++)
						{
							ResolutionVariable localVariableTwo = variablesTwo.get(l);
							if ((localVariable.GetName().equals(localVariableTwo.GetName())) && (localVariable.GetDenial() != localVariableTwo.GetDenial()))
							{
								ResolutionDisjunct localNewDisjunct = new ResolutionDisjunct();
								
								localNewDisjunct.GetVariables().addAll(localDisjunct.GetVariables());
								ResolutionVariable x = localNewDisjunct.GetVariables().get(k);
								localNewDisjunct.GetVariables().remove(x);
								
								localNewDisjunct.GetVariables().addAll(localDisjunctTwo.GetVariables());
								ResolutionVariable y = localNewDisjunct.GetVariables().get(localDisjunct.GetVariables().size() - 1 + l);
								localNewDisjunct.GetVariables().remove(y);
								int [] localParents = {localDisjunct.GetID(), localDisjunctTwo.GetID()}; // create parent's numbers array
								localNewDisjunct.SetParents(localParents); // set parent's numbers
								
								localNewDisjunct.SetContrary(String.valueOf(localVariable.GetName())); // set parent's contrary variable
								
								localNewDisjunct.Refresh();
								localNewDisjunct.Sort();
								
								if (localNewDisjunct.GetEmpty() && repeat)
								{
									repeat = false;
									adding = true;
									removeIndex = localFR.GetDisjuncts().size() + 1;
								}
								
								localFR.SetDisjuncts(localNewDisjunct);
							}
						}
					}
				}
			}
			if(localFR.GetDisjuncts().size() == 0)
			{
				repeat = false;
			}
			localF = localFR;
			if (adding)
			{
				q = localF.GetDisjuncts().size();
				for (p = removeIndex; p < q; p++)
				{
					localF.GetDisjuncts().remove(removeIndex);
				}
				
				d = disjuncts.size();
				localF.Refresh();
				disjuncts.addAll(localF.GetDisjuncts());
				this.RefreshExtension(d); // it's only here, because at the end it doesn't work
			}
		}
	}
	
	// --------------------------------
	// ---------- PREDICATES ----------
	// --------------------------------
	
	/**
	 * ???
	 * @author MatmanBJ
	 */
	public void ResolutionAllUniquePredicate ()
	{
		int i;
		int j;
		int k;
		int m;
		int l;
		int c;
		int p = 0;
		int q = 0;
		int removeIndex = 0;
		boolean repeat = true;
		boolean adding = false; // adding disjuncts in the end indicator
		boolean oneExist = false;
		this.SortDPredicate();
		this.RefreshDPredicate();
		ResolutionFunction localF = new ResolutionFunction();
		localF.GetDisjuncts().addAll(disjuncts);
		disjuncts.removeAll(disjuncts);
		while (repeat == true)
		{
			ResolutionFunction localFR = new ResolutionFunction();
			int d = disjuncts.size();
			localF.RefreshPredicate();
			disjuncts.addAll(localF.GetDisjuncts());
			//this.RefreshExtensionPredicateSize(d);
			this.RefreshPredicate(); // it's only here, because at the end it doesn't work
			for (i = 0; i < disjuncts.size() - 1; i++)
			{
				ResolutionDisjunct localDisjunct = disjuncts.get(i);
				ArrayList<ResolutionPredicate> localPredicates = localDisjunct.GetPredicates();
				if (i < d)
				{
					c = d;
				}
				else
				{
					c = i + 1;
				}
				for (j = c; j < disjuncts.size(); j++)
				{
					ResolutionDisjunct localDisjunctTwo = disjuncts.get(j);
					ArrayList<ResolutionPredicate> variablesTwo = localDisjunctTwo.GetPredicates();
					for (k = 0; k < localPredicates.size(); k++)
					{
						ResolutionPredicate localVariable = localPredicates.get(k);
						for (l = 0; l < variablesTwo.size(); l++)
						{
							ResolutionPredicate localVariableTwo = variablesTwo.get(l);
							
							ResolutionPredicate localNewPredicateOne = new ResolutionPredicate(localVariable);
							ResolutionPredicate localNewPredicateTwo = new ResolutionPredicate(localVariableTwo);
							ResolutionDisjunct localNewDisjunctOne = new ResolutionDisjunct(localDisjunct);
							ResolutionDisjunct localNewDisjunctTwo = new ResolutionDisjunct(localDisjunctTwo);
							
							boolean localStart = false;
							if (localNewPredicateOne.preContrary(localNewPredicateTwo))
							{
								boolean localIndicator = true;
								for (m = 0; m < localNewPredicateOne.GetTerms().size(); m++)
								{
									localIndicator = ResolutionPredicate.unification(localNewPredicateOne, localNewDisjunctOne, localNewPredicateTwo, localNewDisjunctTwo);
									if (localIndicator == false)
									{
										m = localNewPredicateOne.GetTerms().size();
									}
								}
								if (localIndicator == true)
								{
									localStart = true;
								}
							}
							if (localStart == true)
							{
								ResolutionDisjunct localNewDisjunct = new ResolutionDisjunct();
								
								localNewDisjunct.GetPredicates().addAll(localNewDisjunctOne.GetPredicates());
								ResolutionPredicate x = localNewDisjunct.GetPredicates().get(k);
								localNewDisjunct.GetPredicates().remove(x);
								
								localNewDisjunct.GetPredicates().addAll(localNewDisjunctTwo.GetPredicates());
								ResolutionPredicate y = localNewDisjunct.GetPredicates().get(localNewDisjunctOne.GetPredicates().size() - 1 + l);
								localNewDisjunct.GetPredicates().remove(y);
								int [] localParents = {localNewDisjunctOne.GetID(), localNewDisjunctTwo.GetID()}; // create parent's numbers array
								localNewDisjunct.SetParents(localParents); // set parent's numbers
								
								localNewDisjunct.SetContrary(String.valueOf(localVariable.GetName())); // set parent's contrary variable
								
								localNewDisjunct.RefreshPredicate();
								localNewDisjunct.SortPredicate();
								
								localFR.SetDisjuncts(localNewDisjunct);
								
								if (localNewDisjunct.GetEmpty() && repeat == true)
								{
									repeat = false;
									adding = true;
									removeIndex = localFR.GetDisjuncts().size() + 1;
								}
							}
						}
					}
				}
			}
			if(localFR.GetDisjuncts().size() == 0)
			{
				repeat = false;
			}
			localF = localFR;
			if (adding)
			{
				q = localF.GetDisjuncts().size();
				for (p = removeIndex; p < q; p++)
				{
					localF.GetDisjuncts().remove(removeIndex);
				}
				
				d = disjuncts.size();
				localF.RefreshPredicate();
				disjuncts.addAll(localF.GetDisjuncts());
				this.RefreshPredicate();
				//this.RefreshExtensionPredicateSize(d); // it's only here, because at the end it doesn't work
			}
		}
	}
	
	public void ResolutionAllUniquePredicateOne ()
	{
		int i;
		int j;
		int k;
		int m;
		int l;
		int c;
		int p = 0;
		int q = 0;
		int removeIndex = 0;
		boolean repeat = true;
		boolean adding = false; // adding disjuncts in the end indicator
		boolean oneExist = false;
		
		ResolutionFunction localF = new ResolutionFunction();
		localF.GetDisjuncts().addAll(disjuncts);
		disjuncts.removeAll(disjuncts);
		this.RefreshDPredicate();
		this.SortDPredicate();
		this.RefreshPredicate();
		
		while (repeat == true)
		{
			ResolutionFunction localFR = new ResolutionFunction();
			int d = disjuncts.size();
			localF.SortDPredicate(); // the order is important
			localF.RefreshDPredicate();
			localF.RefreshPredicate();
			disjuncts.addAll(localF.GetDisjuncts());
			this.SortDPredicate(); // the order is important
			this.RefreshExtensionPredicateSize(d);
			//this.RefreshPredicate(); // it's only here, because at the end it doesn't work
			for (i = 0; i < disjuncts.size() - 1; i++)
			{
				ResolutionDisjunct localDisjunct = disjuncts.get(i);
				ArrayList<ResolutionPredicate> localPredicates = localDisjunct.GetPredicates();
				if (i < d)
				{
					c = d;
				}
				else
				{
					c = i + 1;
				}
				for (j = c; j < disjuncts.size(); j++)
				{
					ResolutionDisjunct localDisjunctTwo = disjuncts.get(j);
					ArrayList<ResolutionPredicate> variablesTwo = localDisjunctTwo.GetPredicates();
					for (k = 0; k < localPredicates.size(); k++)
					{
						ResolutionPredicate localVariable = localPredicates.get(k);
						for (l = 0; l < variablesTwo.size(); l++)
						{
							ResolutionPredicate localVariableTwo = variablesTwo.get(l);
							
							ResolutionPredicate localNewPredicateOne = new ResolutionPredicate(localVariable);
							ResolutionPredicate localNewPredicateTwo = new ResolutionPredicate(localVariableTwo);
							ResolutionDisjunct localNewDisjunctOne = new ResolutionDisjunct(localDisjunct);
							ResolutionDisjunct localNewDisjunctTwo = new ResolutionDisjunct(localDisjunctTwo);
							
							boolean localStart = false;
							if (localNewPredicateOne.preContrary(localNewPredicateTwo))
							{
								boolean localIndicator = true;
								for (m = 0; m < localNewPredicateOne.GetTerms().size(); m++)
								{
									localIndicator = ResolutionPredicate.unification(localNewPredicateOne, localNewDisjunctOne, localNewPredicateTwo, localNewDisjunctTwo);
									if (localIndicator == false)
									{
										m = localNewPredicateOne.GetTerms().size();
									}
								}
								if (localIndicator == true)
								{
									localStart = true;
								}
							}
							if (localStart == true)
							{
								ResolutionDisjunct localNewDisjunct = new ResolutionDisjunct();
								
								localNewDisjunct.GetPredicates().addAll(localNewDisjunctOne.GetPredicates());
								ResolutionPredicate x = localNewDisjunct.GetPredicates().get(k);
								localNewDisjunct.GetPredicates().remove(x);
								
								localNewDisjunct.GetPredicates().addAll(localNewDisjunctTwo.GetPredicates());
								ResolutionPredicate y = localNewDisjunct.GetPredicates().get(localNewDisjunctOne.GetPredicates().size() - 1 + l);
								localNewDisjunct.GetPredicates().remove(y);
								int [] localParents = {localNewDisjunctOne.GetID(), localNewDisjunctTwo.GetID()}; // create parent's numbers array
								localNewDisjunct.SetParents(localParents); // set parent's numbers
								
								localNewDisjunct.SetContrary(String.valueOf(localVariable.GetName())); // set parent's contrary variable
								
								localNewDisjunct.RefreshPredicate();
								localNewDisjunct.SortPredicate();
								
								//System.out.print(localNewDisjunct.toOutputStringPredicate(0));
								
								/*if (oneExist == false)
								{
									localFR.SetDisjuncts(localNewDisjunct);
									if (localNewDisjunct.GetOne() == true)
									{
										oneExist = true;
									}
								}
								else if (localNewDisjunct.GetOne() == false)
								{
									localFR.SetDisjuncts(localNewDisjunct);
								}*/
								
								localFR.SetDisjuncts(localNewDisjunct);
								
								if (localNewDisjunct.GetEmpty() && repeat == true)
								{
									repeat = false;
									adding = true;
									removeIndex = localFR.GetDisjuncts().size() + 1;
								}
							}
							/*if ((localVariable.GetName().equals(localVariableTwo.GetName())) && (localVariable.GetDenial() != localVariableTwo.GetDenial()))
							{
								
							}*/
						}
					}
				}
			}
			if(localFR.GetDisjuncts().size() == 0)
			{
				repeat = false;
			}
			localF = localFR;
			if (adding)
			{
				//q = localF.GetDisjuncts().size();
				//for (p = removeIndex; p < q; p++)
				//{
				//	localF.GetDisjuncts().remove(removeIndex);
				//}
				
				d = disjuncts.size();
				localF.SortDPredicate(); // the order is important
				localF.RefreshPredicate();
				disjuncts.addAll(localF.GetDisjuncts());
				this.SortDPredicate(); // the order is important
				//this.RefreshPredicate();
				this.RefreshExtensionPredicateSize(d); // it's only here, because at the end it doesn't work
			}
		}
	}
}
