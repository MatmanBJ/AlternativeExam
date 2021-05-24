// Alternative exam -- 4 term
// © Matthew Sobolewski and Victor Stepovik
// Resolution method version alpha 0.25
// Updates:
// -- FILE OUTPUT IS UNUPDATED
// -- Some methods has been updated

package resolution;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

/**
 * Main class "ResolutionMethod"
 * @author MatmanBJ
 * @version alpha 0.25
 */
public class ResolutionMethod
{
	public static void ExampleOne() // old and unupdated example
	{
		/*ResolutionVar a = new ResolutionVar('a');
		ResolutionVar b = new ResolutionVar('b');
		
		ResolutionVariable yesa = new ResolutionVariable();
		ResolutionVariable yesb = new ResolutionVariable();
		ResolutionVariable noa = new ResolutionVariable();
		ResolutionVariable nob = new ResolutionVariable();
		
		ResolutionDisjunct one = new ResolutionDisjunct();
		ResolutionDisjunct two = new ResolutionDisjunct();
		
		ResolutionFunction f = new ResolutionFunction();
		ResolutionFunction F;
		
		yesa.SetDenial(true);
		yesb.SetDenial(true);
		noa.SetDenial(false);
		nob.SetDenial(false);
		yesa.SetVar(a);
		yesb.SetVar(b);
		noa.SetVar(a);
		nob.SetVar(b);
		
		one.SetVariables(yesa);
		one.SetVariables(yesb);
		two.SetVariables(noa);
		two.SetVariables(nob);
		
		f.SetDisjuncts(one);
		f.SetDisjuncts(two);
		
		//f.GetFunction();
		//F = f.Resolution();
		//F.GetFunction();
		f.GetFunction();*/
		
		// new example
		
		/*ResolutionVar x = new ResolutionVar ('x');
		ResolutionVar y = new ResolutionVar ('y');
		ResolutionVar z = new ResolutionVar ('z');
		ResolutionVar t = new ResolutionVar ('t');
		ResolutionVar s = new ResolutionVar ('s');*/
	}
	public static void ExampleTwo ()
	{
		ArrayList<ResolutionVariable> variables = new ArrayList<ResolutionVariable>();
		
		variables.add(new ResolutionVariable(false, "x"));
		variables.add(new ResolutionVariable(false, "y"));
		variables.add(new ResolutionVariable(false, "z"));
		ResolutionDisjunct d_one = new ResolutionDisjunct(variables);
		variables.removeAll(variables);
		
		variables.add(new ResolutionVariable(false, "x"));
		variables.add(new ResolutionVariable(false, "t"));
		variables.add(new ResolutionVariable(true, "s"));
		ResolutionDisjunct d_two = new ResolutionDisjunct(variables);
		variables.removeAll(variables);
		
		variables.add(new ResolutionVariable(false, "x"));
		variables.add(new ResolutionVariable(false, "s"));
		ResolutionDisjunct d_three = new ResolutionDisjunct(variables);
		variables.removeAll(variables);
		
		variables.add(new ResolutionVariable(false, "x"));
		variables.add(new ResolutionVariable(true, "y"));
		variables.add(new ResolutionVariable(true, "t"));
		ResolutionDisjunct d_four = new ResolutionDisjunct(variables);
		variables.removeAll(variables);
		
		variables.add(new ResolutionVariable(true, "x"));
		ResolutionDisjunct d_five = new ResolutionDisjunct(variables);
		variables.removeAll(variables);
		
		variables.add(new ResolutionVariable(true, "z"));
		ResolutionDisjunct d_six = new ResolutionDisjunct(variables);
		variables.removeAll(variables);
		
		ResolutionFunction d_func = new ResolutionFunction();
		d_func.SetDisjuncts(d_one);
		d_func.SetDisjuncts(d_two);
		d_func.SetDisjuncts(d_three);
		d_func.SetDisjuncts(d_four);
		d_func.SetDisjuncts(d_five);
		d_func.SetDisjuncts(d_six);
		
		//d_func.ResolutionFind();
		//d_func.Resolution();
		d_func.GetFunction();
		//d_func_return.GetFunction();
	}
	
	public static void StrategiesDemonstration (ResolutionFunction localLocalRF) // resolution strategy demonstrations
	{
		// I STOPPED HERE
		ResolutionFunction localStrategy;
		System.out.println("---------- ALL UNIQUE STRATEGY ----------");
		ResolutionDisjunct.SetMaxID();
		localStrategy = new ResolutionFunction ();
		localStrategy.ResolutionFileInput("example_theory.txt");
		localStrategy.ResolutionAllUnique();
		localStrategy.GetFunction();
		System.out.println("---------- SATURATION STRATEGY ----------");
		ResolutionDisjunct.SetMaxID();
		localStrategy = new ResolutionFunction ();
		localStrategy.ResolutionFileInput("example_theory.txt");
		localStrategy.ResolutionSaturation();
		localStrategy.GetFunction();
		System.out.println("---------- PREFERENCE STRATEGY ----------");
		ResolutionDisjunct.SetMaxID();
		localStrategy = new ResolutionFunction ();
		localStrategy.ResolutionFileInput("example_theory.txt");
		localStrategy.ResolutionPreference();
		localStrategy.GetFunction();
		System.out.println("---------- STRIKEOUT STRATEGY ----------");
		ResolutionDisjunct.SetMaxID();
		localStrategy = new ResolutionFunction ();
		localStrategy.ResolutionFileInput("example_theory.txt");
		localStrategy.ResolutionStrikeout();
		localStrategy.GetFunction();
	}
	
	public static ResolutionFunction TreatmentMain (String localString, ResolutionFunction localRF)
	{
		if (localString.equals("???"))
		{
			ResolutionFunction rfo = new ResolutionFunction(localRF);
			ResolutionFunction rft = new ResolutionFunction(localRF);
			rfo.ResolutionPreference();
			//rft.ResolutionPreferenceTest();
			rfo.GetFunction();
			System.out.println("\n");
			rft.GetFunction();
		}
		else if (localString.equals("saturation"))
		{
			localRF.ResolutionSaturation();
		}
		else if (localString.equals("preference"))
		{
			localRF.ResolutionPreference();
		}
		else if (localString.equals("strikeout"))
		{
			localRF.ResolutionStrikeout();
		}
		else if (localString.equals("strategies demonstration"))
		{
			StrategiesDemonstration(localRF);
		}
		else if (localString.equals("all"))
		{
			localRF.ResolutionAll();
		}
		else if (localString.equals("all find"))
		{
			localRF.ResolutionAllFind();
		}
		else if (localString.equals("unique"))
		{
			localRF.ResolutionAllUnique();
		}
		else
		{
			System.out.println("Incorrect type of treatment! Nothing is done with function");
		}
		return localRF;
	}
	
	public static ResolutionFunction TreatmentMainPredicate (String localString, ResolutionFunction localRF)
	{
		if (localString.equals("unique"))
		{
			localRF.ResolutionAllUniquePredicate();
		}
		else
		{
			System.out.println("Incorrect type of treatment! Nothing is done with function");
		}
		return localRF;
	}
	
	public static ResolutionFunction InputMain (String localType)
	{
		ResolutionFunction cons = new ResolutionFunction ();
		if (localType.equals("console"))
		{
			System.out.println("---------- CONSOLE INPUT ----------");
			cons = new ResolutionFunction("INPUT");
		}
		else if (localType.equals("file"))
		{
			System.out.println("---------- FILE INPUT ----------");
			Scanner inputScanner = new Scanner(System.in);
			System.out.println("Please write file name(<file name>):");
			String localFS = inputScanner.nextLine();
			cons.ResolutionFileInput(localFS);
		}
		else
		{
			System.out.println("Incorrect type of input! Default input from console");
		}
		return cons;
	}
	
	public static ResolutionFunction InputMainPredicate (String localType)
	{
		ResolutionFunction cons = new ResolutionFunction ();
		if (localType.equals("console"))
		{
			System.out.println("---------- CONSOLE INPUT ----------");
			cons = new ResolutionFunction(1);
		}
		else if (localType.equals("file"))
		{
			System.out.println("---------- FILE INPUT ----------");
			Scanner inputScanner = new Scanner(System.in);
			System.out.println("Please write file name(<file name>):");
			String localFS = inputScanner.nextLine();
			cons.ResolutionFileInputPredicate(localFS);
		}
		else
		{
			System.out.println("Incorrect type of input! Default input from console");
		}
		return cons;
	}
	
	public static void OutputMain (ResolutionFunction localOutput, String localType)
	{
		if (localType.equals("console"))
		{
			System.out.println("---------- CONSOLE OUTPUT ----------");
			localOutput.GetFunction();
		}
		else if (localType.equals("file"))
		{
			System.out.println("---------- CHOOSED FILE OUTPUT ----------");
			Scanner outputScanner = new Scanner(System.in);
			System.out.println("Please write file name and specification (<file name> <specification>):");
			String localFS = outputScanner.nextLine();
			String localFSArray [] = localFS.split("\\s");
			if (localFSArray[1].equals("readable"))
			{
				localOutput.ResolutionFileReadable("readable_" + localFSArray[0]);
			}
			else if (localFSArray[1].equals("unreadable"))
			{
				localOutput.ResolutionFileUnreadable("unreadable_" + localFSArray[0]);
			}
			else
			{
				System.out.println("Incorrect type of output");
			}
		}
		else if (localType.equals("console file") || localType.equals("file console"))
		{
			System.out.println("---------- CONSOLE AND CHOOSED FILE OUTPUT ----------");
			localOutput.GetFunction();
			Scanner outputScanner = new Scanner(System.in);
			System.out.println("Please write file name and specification (<file name> <specification>):");
			String localFS = outputScanner.nextLine();
			String localFSArray [] = localFS.split("\\s");
			if (localFSArray[1].equals("readable"))
			{
				localOutput.ResolutionFileReadable("readable_" + localFSArray[0]);
			}
			else if (localFSArray[1].equals("unreadable"))
			{
				localOutput.ResolutionFileUnreadable("unreadable_" + localFSArray[0]);
			}
			else
			{
				System.out.println("Incorrect type of output");
			}
		}
		else if (localType.equals("all"))
		{
			System.out.println("---------- CONSOLE AND ALL FILES OUTPUT ----------");
			localOutput.GetFunction();
			Scanner outputScanner = new Scanner(System.in);
			System.out.println("Please write file name (<file name>):");
			String localFS = outputScanner.nextLine();
			localOutput.ResolutionFileReadable("readable_" + localFS);
			localOutput.ResolutionFileUnreadable("unreadable_" + localFS);
		}
		else
		{
			System.out.println("Incorrect type of output");
		}
	}
	
	public static void OutputMainPredicate (ResolutionFunction localOutput, String localType)
	{
		if (localType.equals("console"))
		{
			System.out.println("---------- CONSOLE OUTPUT ----------");
			localOutput.GetFunctionPredicate();
		}
		else if (localType.equals("file"))
		{
			System.out.println("---------- CHOOSED FILE OUTPUT ----------");
			Scanner outputScanner = new Scanner(System.in);
			System.out.println("Please write file name and specification (<file name> <specification>):");
			String localFS = outputScanner.nextLine();
			String localFSArray [] = localFS.split("\\s");
			if (localFSArray[1].equals("readable"))
			{
				localOutput.ResolutionFileReadable("readable_" + localFSArray[0]);
			}
			else if (localFSArray[1].equals("unreadable"))
			{
				localOutput.ResolutionFileUnreadable("unreadable_" + localFSArray[0]);
			}
			else
			{
				System.out.println("Incorrect type of output");
			}
		}
		else if (localType.equals("console file") || localType.equals("file console"))
		{
			System.out.println("---------- CONSOLE AND CHOOSED FILE OUTPUT ----------");
			localOutput.GetFunction();
			Scanner outputScanner = new Scanner(System.in);
			System.out.println("Please write file name and specification (<file name> <specification>):");
			String localFS = outputScanner.nextLine();
			String localFSArray [] = localFS.split("\\s");
			if (localFSArray[1].equals("readable"))
			{
				localOutput.ResolutionFileReadable("readable_" + localFSArray[0]);
			}
			else if (localFSArray[1].equals("unreadable"))
			{
				localOutput.ResolutionFileUnreadable("unreadable_" + localFSArray[0]);
			}
			else
			{
				System.out.println("Incorrect type of output");
			}
		}
		else if (localType.equals("all"))
		{
			System.out.println("---------- CONSOLE AND ALL FILES OUTPUT ----------");
			localOutput.GetFunction();
			Scanner outputScanner = new Scanner(System.in);
			System.out.println("Please write file name (<file name>):");
			String localFS = outputScanner.nextLine();
			localOutput.ResolutionFileReadable("readable_" + localFS);
			localOutput.ResolutionFileUnreadable("unreadable_" + localFS);
		}
		else
		{
			System.out.println("Incorrect type of output");
		}
	}
	
	public static void main(String[] args)
	{
		Scanner inputScanner = new Scanner(System.in);
		int key = 1;
		String typeLogic;
		String typeInput;
		String typeOutput;
		String typeTreatment;
		ResolutionFunction localFunction;
		
		while (key != 0)
		{
			System.out.println("Greek alphabet to copy: " + ResolutionTerm.GetGreekAlphabet());
			System.out.println("Special symbol to copy: " + "□\n");
			
			ResolutionDisjunct.SetMaxID();
			
			System.out.println("Please write type of logic (<type>):");
			typeLogic = inputScanner.nextLine();
			System.out.println("Please write type of input (<type>):");
			typeInput = inputScanner.nextLine();
			System.out.println("Please write type of treatment (<type>):");
			typeTreatment = inputScanner.nextLine();
			System.out.println("Please write type of output (<type 1> or <type 1> <type 2> or all):");
			typeOutput = inputScanner.nextLine();
			
			if (typeLogic.equals("statement"))
			{
				localFunction = InputMain(typeInput);
				localFunction = TreatmentMain(typeTreatment, localFunction);
				OutputMain(localFunction, typeOutput);
			}
			else if (typeLogic.equals("predicate"))
			{
				localFunction = InputMainPredicate("console");
				localFunction = TreatmentMainPredicate("unique", localFunction);
				OutputMainPredicate(localFunction, "console");
				//localFunction = InputMainPredicate(typeInput);
				//localFunction = TreatmentMainPredicate(typeTreatment, localFunction);
				//OutputMainPredicate(localFunction, typeOutput);
			}
			else
			{
				System.out.println("Incorrect type of logic! Try again");
			}
			
			System.out.println("Do you want to input something else? Press 0 to NO, press else to YES:");
			key = inputScanner.nextInt();
			inputScanner.nextLine();
		}
		
		inputScanner.close(); // be careful: it closes I/O system, so if you do it once, you can not use I/O again!
		System.out.println("---------- Bye!!! ----------");
	}
}
