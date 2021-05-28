// Alternative exam -- 4 term
// © Matthew Sobolewski and Victor Stepovik
// "A GIANT OF THOUHT, A FATHER OF RUSSIAN RES(V)OLUTION"
// Resolution method version alpha 0.29
// Updates:
// -- try/catch/finally added for the main class (with lists of potential commands)
// -- each iteration demonstration added (only for "all number" method)
// -- idz function added
// -- semantic resolution minor bug fixes
// -- output function major update
// -- file output for predicate added
// -- old examples deleted, new files with examples added
// -- main class minor bug fixes

package resolution;

import java.util.List;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Main class "ResolutionMethod"
 * @author MatmanBJ
 * @version alpha 0.29
 */
public class ResolutionMethod
{
	private static List<String> logicList = Arrays.asList("statement", "predicate");
	private static List<String> inputList = Arrays.asList("console", "file");
	private static List<String> treatmentList = Arrays.asList("saturation", "preference", "strikeout", "strategies demonstration", "all", "all number", "all find", "idz", "all unique", "semantic");
	private static List<String> treatmentListPredicate = Arrays.asList("all unique");
	private static List<String> outputList = Arrays.asList("console", "file", "console file", "file console", "all", "none");
	
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
		if (localString.equals("saturation"))
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
		else if (localString.equals("all number"))
		{
			Scanner inputScanner = new Scanner(System.in);
			System.out.println("Please write number of iterations:");
			int localIters = inputScanner.nextInt();
			System.out.println("Current number of iterations: " + localIters);
			
			localRF.ResolutionAllNumber(localIters);
		}
		else if (localString.equals("all find"))
		{
			localRF.ResolutionAllFind();
		}
		else if (localString.equals("idz"))
		{
			localRF.ResolutionIDZ();
		}
		else if (localString.equals("all unique"))
		{
			localRF.ResolutionAllUnique();
		}
		else if (localString.equals("semantic"))
		{
			localRF.ResolutionSemantic();
		}
		else if (localString.equals("semantic test"))
		{
			// NOTHING YET
		}
		else
		{
			System.out.println("Incorrect type of treatment! Nothing is done with function");
		}
		return localRF;
	}
	
	public static ResolutionFunction TreatmentMainPredicate (String localString, ResolutionFunction localRF)
	{
		if (localString.equals("all unique"))
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
		else if (localType.equals("none"))
		{}
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
				localOutput.ResolutionFileReadablePredicate("readable_" + localFSArray[0]);
			}
			else if (localFSArray[1].equals("unreadable"))
			{
				localOutput.ResolutionFileUnreadablePredicate("unreadable_" + localFSArray[0]);
			}
			else
			{
				System.out.println("Incorrect type of output");
			}
		}
		else if (localType.equals("console file") || localType.equals("file console"))
		{
			System.out.println("---------- CONSOLE AND CHOOSED FILE OUTPUT ----------");
			localOutput.GetFunctionPredicate();
			Scanner outputScanner = new Scanner(System.in);
			System.out.println("Please write file name and specification (<file name> <specification>):");
			String localFS = outputScanner.nextLine();
			String localFSArray [] = localFS.split("\\s");
			if (localFSArray[1].equals("readable"))
			{
				localOutput.ResolutionFileReadablePredicate("readable_" + localFSArray[0]);
			}
			else if (localFSArray[1].equals("unreadable"))
			{
				localOutput.ResolutionFileUnreadablePredicate("unreadable_" + localFSArray[0]);
			}
			else
			{
				System.out.println("Incorrect type of output");
			}
		}
		else if (localType.equals("all"))
		{
			System.out.println("---------- CONSOLE AND ALL FILES OUTPUT ----------");
			localOutput.GetFunctionPredicate();
			Scanner outputScanner = new Scanner(System.in);
			System.out.println("Please write file name (<file name>):");
			String localFS = outputScanner.nextLine();
			localOutput.ResolutionFileReadablePredicate("readable_" + localFS);
			localOutput.ResolutionFileUnreadablePredicate("unreadable_" + localFS);
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
			
			try
			{
				System.out.println("Please write type of logic (<type>):");
				typeLogic = inputScanner.nextLine();
				if (logicList.contains(typeLogic) == false)
				{
					throw new Exception("Wrong type of logic!");
				}
				
				System.out.println("Please write type of input (<type>):");
				typeInput = inputScanner.nextLine();
				if (inputList.contains(typeInput) == false)
				{
					throw new Exception("Wrong type of input!");
				}
				
				System.out.println("Please write type of treatment (<type>):");
				typeTreatment = inputScanner.nextLine();
				if ((treatmentList.contains(typeTreatment) == false) && (treatmentListPredicate.contains(typeTreatment) == false))
				{
					throw new Exception("Wrong type of treatment for your logic type!");
				}
				
				System.out.println("Please write type of output (<type 1> or <type 1> <type 2> or all):");
				typeOutput = inputScanner.nextLine();
				if (outputList.contains(typeOutput) == false)
				{
					throw new Exception("Wrong type of output!");
				}
				
				if (typeLogic.equals("statement"))
				{
					localFunction = InputMain(typeInput);
					localFunction = TreatmentMain(typeTreatment, localFunction);
					OutputMain(localFunction, typeOutput);
				}
				else if (typeLogic.equals("predicate"))
				{
					localFunction = InputMainPredicate(typeInput);
					localFunction = TreatmentMainPredicate(typeTreatment, localFunction);
					OutputMainPredicate(localFunction, typeOutput);
				}
				
			}
			catch (Exception e)
			{
				System.out.println("\nException caught in ResolutionMethod class:");
				System.out.println(e.getMessage() + " Follow the instructions below to try again.\n");
			}
			finally
			{
				System.out.println("Do you want to input something else? Press 0 to NO, press else to YES:");
				key = inputScanner.nextInt();
				inputScanner.nextLine();
			}
		}
		
		inputScanner.close(); // be careful: it closes I/O system, so if you do it once, you can not use I/O again!
		System.out.println("---------- PROGRAM TERMINATED ----------");
	}
}
