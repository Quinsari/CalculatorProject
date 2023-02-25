import java.util.Scanner;
import java.util.Stack;
public class Calculator {

	public static void main(String[] args) {	
		String input = "";
		Scanner scanner = new Scanner(System.in);
		Boolean valid = false;
		

		System.out.println("Input Equation or \"END\":");
		input = scanner.nextLine();
		while (!input.toUpperCase().equals("END")){

			valid = validCheck(input);
		while (!valid && !input.toUpperCase().equals("END")) {
			System.out.println("Equation Invalid\nPlease Try Again or \"END\":");
			input = scanner.nextLine();
			valid = validCheck(input);
		}
		//Ends the program after exiting the while loop if requested
		if (input.toUpperCase().equals("END")) {
			break;
		}

			String infix = in_fix(input);	
			String postfix = post_fix(input);
			System.out.println("Infix Expression: " + infix);
			System.out.println("Postfix Expression: " + postfix);
			System.out.println("Evaluated: " + evaluate(postfix) + "\n\n");
			
			System.out.println("Input Equation or \"END\":");
			input = scanner.nextLine();
		}
		
		//I just hate the scanner not closed warning
		scanner.close();

		

	}
	
	
	//Transforms the infix expression into a postfix one
	public static String post_fix(String input) {
		Stack<Character> symbols = new Stack<>();
		String output = "";
		String digits = "";

		
		if (input.length() == 0){
			return "EMPTY INPUT";
		}
		
		for (int i = 0; i < input.length(); i++) {
			switch(input.charAt(i)){

			case ' ':
				if (i == 0 || input.charAt(i - 1) == ' ') {
					break;
				}
				if (digits.equals("") == false){
					output = output + digits + " ";
					digits = "";
				}
				//Leftover Debugging
				//System.out.println(input.charAt(i));
				//System.out.println(output);
				break;
			case '(':
				symbols.push(new Character(input.charAt(i)));
				if (digits.equals("") == false){
					output = output + digits + " ";
					digits = "";
				}
				//Leftover Debugging
				//System.out.println(input.charAt(i));
				//System.out.println(output);
				break;
			case '+':
				if (digits.equals("") == false){
					output = output + digits + " ";
					digits = "";
				}
				while (symbols.isEmpty() == false && symbols.peek() != '('){
					output = output + symbols.pop() + " ";
				}
				symbols.push(new Character(input.charAt(i)));
				//Leftover Debugging
				//System.out.println(input.charAt(i));
				//System.out.println(output);
				break;
			case '-':
				if (digits.equals("") == false){
					output = output + digits + " ";
					digits = "";
				}
				while (symbols.isEmpty() == false && symbols.peek() != '('){
					output = output + symbols.pop() + " ";
				}
				symbols.push(new Character(input.charAt(i)));
				//Leftover Debugging
				//System.out.println(input.charAt(i));
				//System.out.println(output);
				break;
			case '/':
				if (digits.equals("") == false){
					output = output + digits + " ";
					digits = "";
				}
				while (symbols.isEmpty() == false && symbols.peek() != '(' && symbols.peek() != '-' && symbols.peek() != '+'){
					output = output + symbols.pop() + " ";
				}
				symbols.push(new Character(input.charAt(i)));
				//Leftover Debugging
				//System.out.println(input.charAt(i));
				//System.out.println(output);
				break;
			case '*':
				if (digits.equals("") == false){
					output = output + digits + " ";
					digits = "";
				}
				while (symbols.isEmpty() == false && symbols.peek() != '(' && symbols.peek() != '-' && symbols.peek() != '+'){
					output = output + symbols.pop() + " ";
				}
				symbols.push(new Character(input.charAt(i)));
				//Leftover Debugging
				//System.out.println(input.charAt(i));
				//System.out.println(output);
				break;
			case ')':
				if (digits.equals("") == false){
					output = output + digits + " ";
					digits = "";
				}
				while (symbols.isEmpty() == false && symbols.peek() != '('){
					output = output + symbols.pop() + " ";
				}
				symbols.pop();
				//Leftover Debugging
				//System.out.println(input.charAt(i));
				//System.out.println(output);
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
			case '.':
				digits = digits + input.charAt(i);
				break;
			default:
				System.out.println("INVALID INPUT");
				output = "INVALID INPUT";
			}

		}
		
		//Places digits if leftovers exist
		if (digits.equals("") == false){
			output = output + digits + " ";
			digits = "";
		}
		
		//Pops and places remainder of symbols
		while (symbols.isEmpty() == false){
			output = output + symbols.pop() + " ";
		}
		return output;
	}
	
	//Takes the post_fix and calculates the expression
	public static Double evaluate (String input){
		Stack<Double> numbers = new Stack<>();
		String[] split = input.split("\\s+");
		//Leftover from alternative check
		//Double left;
		//Double right;
		//Double output;
		
		if (input.length() == 0) {
			System.out.println("EMPTY INPUT");
			return null;
		}
		
		for (int i = 0; i < split.length; i++){
			if (isNumber(split[i])){
				numbers.push(new Double (Double.parseDouble(split[i])));
				//Leftover Debugging
				//System.out.println(numbers.peek());
			}
			else{
				switch (split[i]){
				case "*":
					//Shortened to one line
					/*right = numbers.pop();
					left = numbers.pop();
					output = left * right;*/
					numbers.push(new Double (numbers.pop() * numbers.pop()));
					break;
				case "/":
					//Shortened to one line
					/*right = numbers.pop();
					left = numbers.pop();
					output = left / right;*/
					numbers.push(new Double ((1/numbers.pop()) * numbers.pop() ));
					break;
				case "+":
					//Shortened to one line
					/*right = numbers.pop();
					left = numbers.pop();
					output = left + right;*/
					numbers.push(new Double (numbers.pop() + numbers.pop()));
					break;
				case "-":
					//Shortened to one line
					/*right = numbers.pop();
					left = numbers.pop();
					output = left - right;*/
					numbers.push(new Double ( -(numbers.pop()) + numbers.pop()));
					break;
				}
			}
		}
		
		//Returns the final number, which should be the solved equation
		return numbers.pop();
		
		
		
	}
	
	//Checks if the string is numerical using try catch statements
	//(I never thought to use try-catch as a logic gate before)
	public static boolean isNumber (String input){
		if (input.length() == 0){
			System.out.println("ERROR");
			return false;
		}
		
		try {
			Double.parseDouble(input);
			return true;
		}
		catch (NumberFormatException e){
			return false;
		}
	}
	
	
	//Checks whether the equation is valid 
	public static boolean validCheck (String input) {
		boolean valid = true;
		int open_count = 0;
		int close_count = 0;
		boolean symbol = true;  //Defaults true so a leading symbol will not be ignored
		if (input.length() == 0) {
			return false;
		}
		for (int i = 0; i < input.length(); i++) {
			//Leftover Debugging
			//System.out.println("Evaluating " + input.charAt(i));
			switch (input.charAt(i)) {
			case ' ':
				break;
			case '.':
				if (i > 0 && input.charAt(i - 1) != '.') {
					break;
				}
				else {
					valid = false;
				}
				break;
			case '*':
				if (symbol) {
					valid = false;
				}
				symbol = true;
				break;
			case '/':
				if (symbol) {
					valid = false;
				}
				symbol = true;
				break;
			case '+':
				if (symbol) {
					valid = false;
				}
				symbol = true;
				break;
			case '-':
				if (symbol) {
					valid = false;
				}
				symbol = true;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				symbol = false;
				break;
			case '(':
				open_count++;
				break;
			case ')':
				if (symbol) {
					valid = false;
				}
				close_count++;
				if (close_count > open_count) {
					valid = false; 	//Identifies if an end parenthesis has been added without a matching open
				}
				break;
			default:
				valid = false;
				break;
			}
			//Leftover Debugging
			//System.out.println("Evaluated to be " + valid);
		}
		//Catches mismatched parenthesis and ending symbol
		if (open_count != close_count || symbol) {
			valid = false;
		}
		return valid;
		
	}
	
	//Formats infix with proper and consistent spacing
	public static String in_fix (String input) {
		String output = "";
		boolean number = false;
		if (input.length() == 0) {
			return "EMPTY INPUT";
		}
		for (int i = 0; i < input.length(); i++) {
			switch (input.charAt(i)) {
			case ' ':
				number = false;
				if (i == 0) {
					break;
				}
				if ((i > 0) && (input.charAt(i-1) != ' ')) {
					output = output + ' ';
				}
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
			case '.':
				if (number) {
					output = output + input.charAt(i);
				}
				else if (i > 0 && input.charAt(i - 1) != ' '){
					output = output + ' ' + input.charAt(i);
				}
				else {
					output = output + input.charAt(i);
				}
				number = true;
				break;
				
			default:
				if ( i > 0 && input.charAt(i - 1) != ' '){
					output = output + ' ' + input.charAt(i);
				}
				else {
				output = output + input.charAt(i);
				}
				number = false;
				break;
			}
		}
		return output;
	}

}
