package routerrental_package;

//imports
import java.util.Scanner;

/*//Setting the price depending on the model
	this.price = 10 + (Integer.valueOf('Z' - model) * 5);// Calculated data member
 */

// Applies Single Responsibility & Open-Closed from the SOLID Principles
// Applies interface

/**
 * Prints all the information of an instance.
 */
interface printer{
	abstract public void printAllDataMemberInformation(Object obj);
}
/**
 * Prints all router's information.
 */
class routerPrinter implements printer{
	/**
	 * Prints all router's information.
	 * Passing wrong object that is not an instance of router will cause the program to stop execution.
	 * @see <a href="https://stackoverflow.com/questions/18041770/stop-executing-further-code-in-java">
	 * Stop executing further code in Java
	 * </a>
	 * @see <a href="https://stackoverflow.com/questions/907360/explanation-of-classcastexception-in-java#907367">
	 * Explanation of "ClassCastException" in Java
	 * </a>
	 * @param obj an instance of router class
	 */
	@Override
	public void printAllDataMemberInformation(Object obj) {
		//handling java defined exception
		try {
			router r = (router) obj;
		} catch (ClassCastException e) {
			System.out.print("Can't cast " + obj.getClass() + " to class router \nProgram forced to stop");
			System.exit(1);
		}
	}
}

/**
 * User-defined exception.
 * @see <a href="https://www.geeksforgeeks.org/g-fact-32-user-defined-custom-exception-in-java/">
 * User-defined Custom Exception in Java
 * </a>
 * @see <a href="https://docs.oracle.com/javase/7/docs/technotes/tools/windows/javadoc.html#see">
 * How to use see tag
 * </a>
 */
class inputError extends Exception{
	public inputError(String message) {
		super(message);
	}
}
// Overloading, Final data member(s), Exception handling, Calculated data members applied & SINGLE RESPOSIBLITY
/**
 * Creates a new router.
 */
class router{
	//Attributes
	private final int serialNumber; // final data member
	private int portsNumber;
	private char model;
	
	//Constructors
	/**
	 * Overloaded Constructor that creates a router.
	 * @param serialNumber serial number of the router.
	 * @param model number of ports of the router.
	 */
	public router(int serialNumber, char model) {
		this.serialNumber = serialNumber;
		setModel(model);
	}
	/**
	 * Overloaded Constructor that creates a router.
	 * @param serialNumber serial number of the router.
	 * @param portsNumber number of ports of the router.
	 * @param model router's model.
	 */
	public router(int serialNumber, int portsNumber, char model) {
		this(serialNumber, model);
		setPortsNumber(portsNumber);
	}
	
	//Getters And Setters
	public int getSerialNumber() {
		return serialNumber;
	}
	public int getPortsNumber() {
		return portsNumber;
	}
	/**
	 * Sets the number of ports value
	 * The function May throw inputError a user-defined exception if an invalid number is passed,
	 * which will cause it to call itself recursively until a valid input is provided.
	 * @see <a href="https://stackoverflow.com/questions/51300038/javadoc-exception-not-thrown-in-eclipse">
	 * Why the exception tag can't be used here
	 * </a>
	 * @param portsNumber the number of ports (positive number).
	 */
	public void setPortsNumber(int portsNumber) {
		try {
			if(portsNumber < 0)
				throw new inputError("Invalid input. Please, enter a postive number: "); // handling user-defined exception
			this.portsNumber = portsNumber;
		} catch (inputError e) {
			System.out.print(e.getMessage());
			Scanner scanner = new Scanner(System.in);
			setPortsNumber(scanner.nextInt());
			scanner.close();
		}
	}
	public char getModel() {
		return model;
	}
	/**
	 * Sets the model value.
	 * The function May throw inputError a user-defined exception if an invalid character is passed,
	 * which will cause it to call itself recursively until a valid input is provided.
	 * @see <a href="https://stackoverflow.com/questions/51300038/javadoc-exception-not-thrown-in-eclipse">
	 * Why the exception tag can't be used here
	 * </a>
	 * @see <a href="https://www.geeksforgeeks.org/gfact-51-java-scanner-nextchar/">
	 * How to read char in java
	 * </a>
	 * @param model the model type (a - z or A - Z).
	 */
	public void setModel(char model) {
		try {
			if(!((model >= 'A' && model <= 'Z') || (model >= 'a' && model <= 'z'))) {
				throw new inputError("Invalid input. Please, enter [a - z] or [A- Z] charcters only: "); // handling user-defined exception
			} 
			this.model = Character.toUpperCase(model);
		} catch (inputError e) {
			System.out.print(e.getMessage());
			Scanner scanner = new Scanner(System.in);
			setModel(scanner.next().charAt(0));
			scanner.close();
		}
	}
}

public class RouterRental {

	public static void main(String[] args) {
	}

}
