import java.util.regex.Pattern;

/**
 * 4.	Coding exercise: You are tasked to write a checker that validates the parentheses of a LISP code.
 * <p>
 * Write a program (in Java or JavaScript) which
 * takes in a string as an input and
 * returns true if all the parentheses in the string are properly closed and nested.
 */
public class ParenthesesValidator
{
	public static void main (String[] args)
	{
		System.out.println(checkParentheses(args[0]));
	}
	
	/**
	 * @param input takes in a string as an input.
	 *
	 * @return true if all the parentheses in the string are properly closed and nested.
	 */
	private static boolean checkParentheses (final String input)
	{
		// Validate existing parentheses only if they exist in the input.
		if (checkContainsParenthesis(input))
		{
			// If the counts of opens and closes match, proceed.
			if (checkCountsMatch(input))
			{
				// Compile a String array of all opens and closes in the input.
				final String OPEN_WITH_DELIMITER = "((?<=\\()|(?=\\())";
				final String CLOSE_WITH_DELIMITER = "((?<=\\))|(?=\\)))";
				final String[] opensAndCloses = input.split("(" + OPEN_WITH_DELIMITER + ")|(" + CLOSE_WITH_DELIMITER + ")");
				
				// Check that the first and last parentheses are both correct.
				if (checkEnds(opensAndCloses))
				{
					// Check that the nesting is correct for all parentheses.
					if (checkNesting(opensAndCloses))
					{
						return true;
					}
				}
			}
		}
		
		// Return false if any of the checks returned false.
		return false;
	}
	
	/**
	 * Checks if the input contains an open or close parenthesis at all.
	 *
	 * @param input The String to be checked for parentheses.
	 *
	 * @return true only if the input has at least 1 open or close parenthesis.
	 */
	private static boolean checkContainsParenthesis (final String input)
	{
		return input.matches(".*[()]+.*");
	}
	
	/**
	 * Checks for matching numbers of open parentheses and close parentheses.
	 *
	 * @param input The String to be checked for parentheses.
	 *
	 * @return true only if the input has a matching number of open and close parentheses.
	 */
	private static boolean checkCountsMatch (final String input)
	{
		// Count the opens and closes in the input.
		long opens = Pattern.compile("\\(").matcher(input).results().count();
		long closes = Pattern.compile("\\)").matcher(input).results().count();
		
		// Return if the counts of opens and closes match: true/false respectively.
		return opens == closes;
	}
	
	/**
	 * Check the first and last parentheses to ensure those are valid.
	 *
	 * @param opensAndCloses Array of the parentheses to loop thru.
	 *
	 * @return true if the first and last parentheses were valid.
	 */
	private static boolean checkEnds (final String[] opensAndCloses)
	{
		// Return false if the first parenthesis is a close.
		if (opensAndCloses[0].contains(")"))
		{
			return false;
		}
		
		// Return false if the last parenthesis is an open.
		if (opensAndCloses[opensAndCloses.length - 1].contains("("))
		{
			return false;
		}
		
		return true;
	}
	
	/**
	 * Loop thru each parenthesis and incrementally count the results
	 * of opens and closes, ensuring closes never exceed the opens.
	 *
	 * @param opensAndCloses Array of the parentheses to loop thru.
	 *
	 * @return true if after loop completes, there were no errors found.
	 */
	private static boolean checkNesting (final String[] opensAndCloses)
	{
		// Number of open and close parentheses found.
		long opens = 0;
		long closes = 0;
		
		// Loop thru each parenthesis. Increment each open and close variable accordingly.
		for (final String parenthesis : opensAndCloses)
		{
			// Increment open variable if this is an open.
			if (parenthesis.contains("("))
			{
				opens++;
			}
			// Increment close variable if this is a close.
			else if (parenthesis.contains(")"))
			{
				closes++;
			}
			
			// Ensure the closes never exceed opens; when they do, return false.
			if (closes > opens)
			{
				return false;
			}
		}
		
		// Return true if no breaks happened during loop.
		return true;
	}
}

