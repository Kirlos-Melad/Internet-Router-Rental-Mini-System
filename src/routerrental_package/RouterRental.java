package routerrental_package;

/**
 * Used packages
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.text.DefaultEditorKit.CutAction;

/**
 * An interface prints all the information of an instance. Applies Single
 * Responsibility, Interface Segregation & Liskov Substitution from the SOLID
 * Principles
 */
interface Printer {
	/**
	 * @param obj An instance of a class
	 */
	abstract public void printAllDataMemberInformation(Object obj);
}

/**
 * Implements printer. Prints all router's information.
 */
class RouterPrinter implements Printer {
	/**
	 * Prints all router's information. Passing wrong object that is not an instance
	 * of router will cause the program to throw java-defined exception that will
	 * cause the it to stop execution.
	 * 
	 * @see <a href=
	 *      "https://stackoverflow.com/questions/18041770/stop-executing-further-code-in-java">
	 *      Stop executing further code in Java </a>
	 * @see <a href=
	 *      "https://stackoverflow.com/questions/907360/explanation-of-classcastexception-in-java#907367">
	 *      Explanation of "ClassCastException" in Java </a>
	 * @param obj an instance of router class
	 */
	@Override
	public void printAllDataMemberInformation(Object obj) {
		try {
			Router r = (Router) obj;
			System.out.printf("Router's serial number: %d\nModel: %c\nNumber of ports: %d\n", r.getSerialNumber(),
					r.getModel(), r.getPortsNumber());
		} catch (ClassCastException e) {
			System.out.print("Couldn't cast " + obj.getClass() + " to class router \nProgram forced to stop");
			System.exit(1);
		}
	}
}

/**
 * Implements printer. Prints all reservation's information.
 */
class ReservationPrinter implements Printer {
	/**
	 * Prints all router's information. Passing wrong object that is not an instance
	 * of router will cause the program to throw java-defined exception that will
	 * cause the it to stop execution.
	 * 
	 * @see <a href="https://www.tutorialspoint.com/java/java_date_time.htm"> Java -
	 *      Date and Time </a>
	 */
	@Override
	public void printAllDataMemberInformation(Object obj) {
		try {
			Reservation r = (Reservation) obj;
			SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy 'at' hh:mm a");
			System.out.printf("Reservation's number: %d\nDate: %s\nStart date: %s\nDuration: %d%c\nDue date: %s\n",
					r.getNumber(), formater.format(r.getReservationDate()).toString(),
					formater.format(r.getStartDate()).toString(), r.getDuration(), r.getType(),
					formater.format(r.getDueDate()).toString());
		} catch (ClassCastException e) {
			System.out.print("Can't cast " + obj.getClass() + " to class reservation \nProgram forced to stop");
			System.exit(1);
		}
	}
}

class InvoicePrinter implements Printer {
	/**
	 * Prints all Invoice's information. Passing wrong object that is not an
	 * instance of invoice will cause the program to throw java-defined exception
	 * that will cause the it to stop execution.
	 */
	@Override
	public void printAllDataMemberInformation(Object obj) {
		try {
			Invoice i = (Invoice) obj;
			System.out.printf("Reservation's number: %d\nRouter serial number: %d\nFees: %.2f\n",
					i.getReservationNumber(), i.getRouterSerialNumber(), i.getFees());
		} catch (ClassCastException e) {
			System.out.print("Can't cast " + obj.getClass() + " to class Invoice \nProgram forced to stop");
			System.exit(1);
		}
	};
}

/**
 * User-defined exception. Single responsibility applied.
 * 
 * @see <a href=
 *      "https://www.geeksforgeeks.org/g-fact-32-user-defined-custom-exception-in-java/">
 *      User-defined Custom Exception in Java </a>
 * @see <a href=
 *      "https://docs.oracle.com/javase/7/docs/technotes/tools/windows/javadoc.html#see">
 *      How to use see tag </a>
 */
final class InputError extends Exception {
	/**
	 * serialVersionUID is used in serializable classes
	 */
	private static final long serialVersionUID = 1L;

	public InputError(String message) {
		super(message);
	}
}

/**
 * Creates a new router. Single responsibility applied.
 */
class Router implements Serializable {
	/**
	 * serialVersionUID is used in serializable classes
	 */
	private static final long serialVersionUID = 2L;
	/**
	 * Final data member serial number of the router.
	 */
	private final int serialNumber;
	/**
	 * Number of ports of the router.
	 */
	private int portsNumber;
	/**
	 * Router's model.
	 */
	private char model;

	/**
	 * Overloaded Constructor.
	 * 
	 * @param serialNumber Serial number of the router.
	 * @param model        Number of ports of the router.
	 */
	public Router(int serialNumber, char model) {
		this.serialNumber = serialNumber;
		setModel(model);
	}

	/**
	 * Overloaded Constructor.
	 * 
	 * @param serialNumber Serial number of the router.
	 * @param portsNumber  Number of ports of the router.
	 * @param model        Router's model.
	 */
	public Router(int serialNumber, char model, int portsNumber) {
		this(serialNumber, model);
		setPortsNumber(portsNumber);
	}

	/**
	 * @return serialNumber Serial number of the router.
	 */
	public int getSerialNumber() {
		return serialNumber;
	}

	/**
	 * @return portsNumber Number of ports of the router.
	 */
	public int getPortsNumber() {
		return portsNumber;
	}

	/**
	 * Sets the number of ports value The function may throw inputError a
	 * user-defined exception if an invalid number is passed, which will cause it to
	 * call itself recursively until a valid input is provided.
	 * 
	 * @see <a href=
	 *      "https://stackoverflow.com/questions/51300038/javadoc-exception-not-thrown-in-eclipse">
	 *      Why the exception tag can't be used here </a>
	 * @param portsNumber the number of ports (positive number).
	 */
	public void setPortsNumber(int portsNumber) {
		try {
			if (portsNumber < 0)
				throw new InputError("Invalid input. Please, enter a non-negative number: ");
			this.portsNumber = portsNumber;
		} catch (InputError e) {
			System.out.print(e.getMessage());
			setPortsNumber(RouterRental.read.nextInt());
		}
	}

	/**
	 * @return model Router's model.
	 */
	public char getModel() {
		return model;
	}

	/**
	 * Sets the model value. The function May throw inputError a user-defined
	 * exception if an invalid character is passed, which will cause it to call
	 * itself recursively until a valid input is provided.
	 * 
	 * @param model The model type (a - z or A - Z).
	 * 
	 * @see <a href=
	 *      "https://stackoverflow.com/questions/51300038/javadoc-exception-not-thrown-in-eclipse">
	 *      Why the exception tag can't be used here </a>
	 * @see <a href="https://www.geeksforgeeks.org/gfact-51-java-scanner-nextchar/">
	 *      How to read char in java </a>
	 */
	public void setModel(char model) {
		try {
			if (!((model >= 'A' && model <= 'Z') || (model >= 'a' && model <= 'z'))) {
				throw new InputError("Invalid input. Please, enter [a - z] or [A- Z] charcters only: ");
			}
			this.model = Character.toUpperCase(model);
		} catch (InputError e) {
			System.out.print(e.getMessage());
			setModel(RouterRental.read.next().charAt(0));
		}
	}
}

/**
 * Creates a reservation. Applies Single-Responsibility principle.
 */
class Reservation implements Serializable {
	/**
	 * serialVersionUID is used in serializable classes
	 */
	private static final long serialVersionUID = 3L;

	/**
	 * Static member counts the instances of the class
	 */
	private static int counter = 0;
	private static boolean isCalled = false;
	/**
	 * Final member reservation's number
	 */
	private final int number;
	/**
	 * The duration of the reservation
	 */
	private int duration;
	/**
	 * The date of the reservation
	 * 
	 * @see <a href="https://www.tutorialspoint.com/java/java_date_time.htm"> Java -
	 *      Date and Time </a>
	 */
	private final Date reservationDate;
	/**
	 * The start date of the reservation
	 */
	private Date startDate;
	/**
	 * The end of reservation date
	 */
	private Date dueDate;
	/**
	 * Type of reservation
	 */
	private final char type;

	/**
	 * Overloaded constructor
	 * 
	 * @param Type type of reservation
	 */
	public Reservation(char type) {
		type = Character.toLowerCase(type);
		if (type != 'm' && type != 'd' && type != 'w') {
			System.out.print("Invalid reservation type.\nProgram forced to stop.");
			System.exit(1);
		}
		this.type = type;
		number = ++counter;
		this.reservationDate = new Date();
		this.duration = 0;
		this.startDate = this.dueDate = null;
	}

	/**
	 * Overloaded constructor
	 * 
	 * @param type      Type of reservation
	 * @param startDate The start date of the reservation
	 * @param duration  The duration of the reservation
	 */
	public Reservation(char type, String startDate, int duration) {
		this(type);
		setStartDate(startDate);
		setDuration(duration);
	}

	/**
	 * @return startDate The start date of the reservation
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * If an invalid date or unidentified format is passed, will cause the program
	 * to call itself recursively until a valid parameter is provided.
	 * 
	 * @param startDate The start date of the reservation
	 */
	public void setStartDate(String startDate) {
		try {
			SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy 'at' hh:mm a");
			Date d = formater.parse(startDate);
			if (d.compareTo(reservationDate) < 0)
				throw new InputError(
						"Invalid start date. Please, enter the date again using the next format [day-month-year at hour:minutes PM/AM]: ");
			this.startDate = d;
			setDueDate();
		} catch (InputError e) {
			System.out.print(e.getMessage());
			setStartDate(RouterRental.read.nextLine());
		} catch (ParseException e) {
			System.out.print("Couldn't identify the entered format.\n"
					+ "Please, enter the date again using the next format [day-month-year at hour:minutes PM/AM]:");
			setStartDate(RouterRental.read.nextLine());
		}
	}

	/**
	 * @return duration The duration of the reservation
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Throws a user-defined exception if an invalid duration is passed, which will
	 * cause it to call itself recursively until a valid input is provided.
	 * 
	 * @param duration the duration of the reservation
	 */
	public void setDuration(int duration) {
		try {
			if (duration <= this.duration)
				throw new InputError(
						"The new duration must be postive number which is greater than the current duration.\n"
								+ "Please, try again: ");
			this.duration = duration;
			setDueDate();
		} catch (InputError e) {
			System.out.print(e.getMessage());
			setDuration(RouterRental.read.nextInt());
		}
	}

	/**
	 * @return dueDate The end of reservation date
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * Automatically calculate the due date. Called after any change of the duration
	 * or start date.
	 * 
	 * @see <a href=
	 *      "https://stackoverflow.com/questions/1005523/how-to-add-one-day-to-a-date">
	 *      How to add one day to a date? </a>
	 */
	private void setDueDate() {
		if (startDate == null || duration == 0)
			return;
		long days;
		if (type == 'd')
			days = duration;
		else if (type == 'w')
			days = duration * 7;
		else
			days = duration * 30;

		dueDate = new Date(startDate.getTime() + ((1000 * 60 * 60 * 24) * days));
	}

	/**
	 * @return type Type of reservation
	 */
	public char getType() {
		return type;
	}

	/**
	 * @return number Reservation's number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @return reservationDate The date of the reservation
	 */
	public Date getReservationDate() {
		return reservationDate;
	}

	public static void setCounter() throws FileNotFoundException, ClassNotFoundException, IOException {
		if(isCalled)
			return;
		
		FileManager fileManager = new FileManager();
		if (!fileManager.isFileEmpty("reservationCounter.ser")) {
			counter = (int) fileManager.readFromFile("reservationCounter.ser");
		}else {
			counter = 0;
		}
		
		isCalled = true;
	}

	public static int getCounter() {
		return counter;
	}
}

/**
 * Creates an invoice. Single responsibility applied.
 */
class Invoice implements Serializable {
	/**
	 * serialVersionUID is used in serializable classes
	 */
	private static final long serialVersionUID = 4L;

	/**
	 * Serial number of the router
	 */
	private final int routerSerialNumber;
	/**
	 * Number of the reservation
	 */
	private final int reservationNumber;
	/**
	 * Fees of the reservation
	 */
	private float fees;

	/**
	 * @param router      Takes router instance
	 * @param reservation Takes reservation instance
	 */
	public Invoice(Router router, Reservation reservation) {
		this(router, reservation, 0);
	}

	public Invoice(Router router, Reservation reservation, float discount) {
		this.routerSerialNumber = router.getSerialNumber();
		this.reservationNumber = reservation.getNumber();
		setFees(router.getModel(), reservation.getType(), reservation.getDuration(), discount);
	}

	/**
	 * Calculates the fees automatically
	 * 
	 * @param routerModel         Router's model
	 * @param reservationType     Type of reservation
	 * @param reservationDuration Duration of reservation
	 */
	private void setFees(char routerModel, char reservationType, int reservationDuration, float discount) {
		if (discount < 0 || discount > 1) {
			System.out.print("Invalid discount. Please, try again [0-1]: ");
			setFees(routerModel, reservationType, reservationDuration, RouterRental.read.nextFloat());
		}
		int days;
		if (routerModel == 'd')
			days = reservationDuration;
		else if (routerModel == 'w')
			days = reservationDuration * 7;
		else
			days = reservationDuration * 30;

		this.fees = (2 * days) + ((Integer.valueOf('Z' - routerModel) + 1) * 5);
		this.fees = this.fees - (this.fees * discount);
	}

	/**
	 * @return routerSerialNumber Serial number of the router
	 */
	public int getRouterSerialNumber() {
		return routerSerialNumber;
	}

	/**
	 * @return reservationNumber Number of the reservation
	 */
	public int getReservationNumber() {
		return reservationNumber;
	}

	/**
	 * @return fees Fees of the reservation
	 */
	public float getFees() {
		return fees;
	}
}

class FileManager {
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;

	public final boolean isFileEmpty(String fileName) {
		File file = new File("./database/" + fileName);

		return (file.length() == 0);
	}

	public final Object readFromFile(String fileName)
			throws FileNotFoundException, IOException, ClassNotFoundException {
		inputStream = new ObjectInputStream(new FileInputStream("./database/" + fileName));
		return inputStream.readObject();
	}

	public final void writeToFile(String fileName, Object obj) throws FileNotFoundException, IOException {
		outputStream = new ObjectOutputStream(new FileOutputStream("./database/" + fileName));
		outputStream.writeObject(obj);
		outputStream.flush();
	}

	public void checkSystemDataBase() throws IOException {
		if (!Files.exists(Paths.get("./database"))) {
			Files.createDirectories(Paths.get("./database/"));
		}
		if (!Files.exists(Paths.get("./database/router.ser"))) {
			Files.createFile(Paths.get("./database/router.ser"));
		}
		if (!Files.exists(Paths.get("./database/reservation.ser"))) {
			Files.createFile(Paths.get("./database/reservation.ser"));
		}
		if (!Files.exists(Paths.get("./database/routerSchedule.ser"))) {
			Files.createFile(Paths.get("./database/routerSchedule.ser"));
		}
		if (!Files.exists(Paths.get("./database/customer.ser"))) {
			Files.createFile(Paths.get("./database/customer.ser"));
		}
		if (!Files.exists(Paths.get("./database/feedback.ser"))) {
			Files.createFile(Paths.get("./database/feedback.ser"));
		}
		if (!Files.exists(Paths.get("./database/reservationCounter.ser"))) {
			Files.createFile(Paths.get("./database/reservationCounter.ser"));
		}
	}
}

/**
 * Class that has all the system information. All inheriting classes applies
 * Single-Responsibility, Open-Closed & Liskov Substitution principle.
 * 
 * @see <a href="https://www.tutorialspoint.com/java/java_hashmap_class.htm">
 *      Java - The HashMap Class </a>
 */
abstract class SystemIformationHolder {
	/**
	 * Saves all Routers [Key: Serial number, Value: Router]
	 */
	protected static HashMap<Integer, Router> router;
	/**
	 * Saves all Reservations [Key: Reservation number, Value: Reservation]
	 */
	protected static HashMap<Integer, Reservation> reservation;
	/**
	 * Keeps track of the router's reservations [Key: Router serial number, Value:
	 * Array-list of Reservation's Number]
	 */
	protected static HashMap<Integer, ArrayList<Integer>> routerSchedule;
	/**
	 * Keeps track of all received feedbacks
	 */
	protected static ArrayList<String> feedback;
}

/**
 * System printer Class that prints any information needed by the system users
 * [Admin/Customer]. All the functions apply Least Privilege principle.
 *
 */
class SystemInformationPrinter extends SystemIformationHolder {
	private HashMap<String, Printer> printer;
	private static boolean isUsed = false;
	private static SystemInformationPrinter systemInformationPrinter;

	public static SystemInformationPrinter getInstance() {
		if (!isUsed) {
			systemInformationPrinter = new SystemInformationPrinter();
			systemInformationPrinter.initialize();
			return systemInformationPrinter;
		} else
			return null;
	}

	/**
	 * Initialize the printer
	 */
	private void initialize() {
		printer = new HashMap<String, Printer>();
		printer.put("Router", new RouterPrinter());
		printer.put("Reservation", new ReservationPrinter());
		printer.put("Invoice", new InvoicePrinter());
	}

	/**
	 * Print all available routers in the system
	 */
	public void printSystemRouters() {
		if (router.isEmpty())
			System.out.print("No routers were found\n");
		for (Router r : router.values()) {
			printer.get("Router").printAllDataMemberInformation(r);
			System.out.println();
		}
	}

	/**
	 * Prints the schedule of given router [start/end date]
	 * 
	 * @param routerSerialNumber Seria number of the router
	 */
	public void printRouterSchedule(final Integer routerSerialNumber) {
		SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy 'at' hh:mm a");
		for (Integer reservationNumber : routerSchedule.get(routerSerialNumber)) {
			System.out.printf("Start date: %s\n", formater.format(reservation.get(reservationNumber).getStartDate()));
			System.out.printf("Due date: %s\n\n", formater.format(reservation.get(reservationNumber).getDueDate()));
		}
	}

	/**
	 * Prints the reservation information
	 * 
	 * @param invoice An invoice of the customer
	 */
	public void printReservation(final Integer reservationNumber) {
		printer.get("Reservation").printAllDataMemberInformation(reservation.get(reservationNumber));
	}

	/**
	 * Prints all information about each invoice
	 * 
	 * @param invoice Array-list that holds all the invoices the customer has
	 */
	public void printCustomerInvoices(final ArrayList<Invoice> invoice) {
		if (invoice == null || invoice.isEmpty()) {
			System.out.print("No invoices were found\n");
			return;
		}

		for (int i = 0; i < invoice.size(); i++) {
			System.out.printf("%d] ", i + 1);
			printer.get("Invoice").printAllDataMemberInformation(invoice.get(i));
		}
	}
	
	public void printAllFeedbacks() {
		for (int i = 0; i < feedback.size(); i++) {
			System.out.printf("%d] %s\n", i + 1, feedback.get(i));
		}
	}
}

/**
 * Class that manages the system start and close
 * 
 * @see <a href="https://www.javatpoint.com/serialization-in-java">
 *      Serialization and Deserialization in Java </a>
 * @see <a href="https://www.tutorialspoint.com/java/java_files_io.htm"> Java -
 *      Files and I/O </a>
 * @see <a href=
 *      "https://stackoverflow.com/questions/285793/what-is-a-serialversionuid-and-why-should-i-use-it">
 *      What is a serialVersionUID and why should I use it? </a>
 * @see <a href=
 *      "https://stackoverflow.com/questions/1816673/how-do-i-check-if-a-file-exists-in-java">
 *      How do I check if a file exists in Java? </a>
 * @see <a href=
 *      "https://www.java67.com/2018/03/a-simple-example-to-check-if-file-is-empty-in-java.html">
 *      A Simple Example to Check if File is Empty in Java </a>
 */
abstract class SystemManager extends SystemIformationHolder {
	/**
	 * Used to restrict the calling of start function
	 */
	private static boolean isCalled = false;
	private static FileManager fileManager = new FileManager();

	public static void startSystem() throws IOException, ClassNotFoundException {
		if (!isCalled)
			isCalled = true;
		else
			return;

		fileManager.checkSystemDataBase();

		if (!fileManager.isFileEmpty("router.ser")) {
			router = (HashMap<Integer, Router>) fileManager.readFromFile("router.ser");
		}else {
			router = new HashMap<Integer, Router>();
		}

		if (!fileManager.isFileEmpty("reservation.ser")) {
			reservation = (HashMap<Integer, Reservation>) fileManager.readFromFile("reservation.ser");
		}else {
			reservation = new HashMap<Integer, Reservation>();
		}

		if (!fileManager.isFileEmpty("routerSchedule.ser")) {
			routerSchedule = (HashMap<Integer, ArrayList<Integer>>) fileManager.readFromFile("routerSchedule.ser");
		}else {
			routerSchedule = new HashMap<Integer, ArrayList<Integer>>();
		}

		if (!fileManager.isFileEmpty("feedback.ser")) {
			feedback = (ArrayList<String>) fileManager.readFromFile("feedback.ser");
		}else {
			feedback = new ArrayList<String>();
		}
		
		Reservation.setCounter();
	}

	public static void closeSystem() throws IOException {
		fileManager.writeToFile("router.ser", router);
		fileManager.writeToFile("reservation.ser", reservation);
		fileManager.writeToFile("routerSchedule.ser", routerSchedule);
		fileManager.writeToFile("feedback.ser", feedback);
		fileManager.writeToFile("reservationCounter.ser", Reservation.getCounter());
	}

}

abstract class UserSystemManager extends SystemIformationHolder {

	/**
	 * Function to update all tracked records. Edit the reservation's records.
	 * 
	 * @param routerSerialNumber Serial number of the router
	 * @param r                  Reservation object
	 * @param operation          [Operation '+': to add, Operation '-': to delete]
	 * @return boolean Returns true if the system is updated successfully
	 */
	protected final boolean systemUpdate(Integer routerSerialNumber, Reservation r, char operation) {
		if (operation == '+') {
			reservation.put(r.getNumber(), r);
			routerSchedule.get(routerSerialNumber).add(r.getNumber());
			sortSchedule(routerSchedule.get(routerSerialNumber));
			return true;
		} else if (operation == '-') {
			reservation.remove(r.getNumber());
			Integer removeNumber = r.getNumber();
			return routerSchedule.get(routerSerialNumber).remove(removeNumber);
		}
		return false;
	}

	/**
	 * Sort router's reservation by start date.
	 * 
	 * @see <a href=
	 *      "https://stackoverflow.com/questions/18441846/how-to-sort-an-arraylist-in-java">
	 *      How to sort an ArrayList in Java </a>
	 */
	private void sortSchedule(ArrayList<Integer> reservationList) {
		Collections.sort(reservationList, new Comparator<Integer>() {
			@Override
			public int compare(Integer arg0, Integer arg1) {
				return reservation.get(arg0).getStartDate().compareTo(reservation.get(arg1).getStartDate());
			}
		});
	}

	/**
	 * Check if there's a router that has the given serial number.
	 * 
	 * @param routerSerialNumber Serial number of the router
	 * @return boolean Returns true if the router exits
	 */
	protected final boolean routerExists(Integer routerSerialNumber) {
		return router.containsKey(routerSerialNumber);
	}

}

/**
 * Class that holds functionalities needed by customers
 */
class CustomerSystemManager extends UserSystemManager {
	private static CustomerSystemManager customerSystemManager;
	private static boolean isUsed = false;

	private CustomerSystemManager() {
	}

	public static CustomerSystemManager getInstance() {
		if (!isUsed) {
			customerSystemManager = new CustomerSystemManager();
			isUsed = true;
			return customerSystemManager;
		} else
			return null;
	}

	/**
	 * Checks if the router available by the given date.
	 * 
	 * @param routerSerialNumber Serial number of the router
	 * @param startDate          start date
	 * @param endDate            due date
	 * @return boolean Returns true if available
	 */
	private boolean isDateAvailable(Integer routerSerialNumber, Date startDate, Date endDate) {
		ArrayList<Integer> reservationList = routerSchedule.get(routerSerialNumber);

		for (int i = 0; i < reservationList.size(); i++) {
			if (!(startDate.after(reservation.get(reservationList.get(i)).getDueDate())
					|| endDate.before(reservation.get(reservationList.get(i)).getStartDate())))
				return false;
		}

		return true;
	}

	/**
	 * Creates an invoice.
	 * 
	 * @param routerSerialNumber Serial number of the router
	 * @param r                  Reservation object
	 * @return Invoice Used by the customer to reserve the router
	 */
	private Invoice createInvoice(Integer routerSerialNumber, Reservation r, boolean isResident) {
		float discount = 0f;
		if (isResident)
			discount = 0.25f;
		return new Invoice(router.get(routerSerialNumber), r, discount);
	}

	/**
	 * Makes a reservation for the customer.
	 * 
	 * @param routerSerialNumber Serial number of the router
	 * @return Invoice Used by the customer to reserve the router
	 */

	public Invoice makeReservation(int routerSerialNumber, boolean isResident) {
		if (!routerExists(routerSerialNumber)) {
			System.out.print("Serial Number not found.\n");
			return null;
		}

		System.out.print("Please, enter your reservation information\n");

		System.out.print("Start date using this format [day-month-year at hour:minutes PM/AM]: ");
		String startDate = RouterRental.read.nextLine();
		System.out.print("Type of reservation: ");
		char type = RouterRental.read.next().charAt(0);
		System.out.print("Duration: ");
		int duration = RouterRental.read.nextInt();
		RouterRental.read.nextLine(); // throw away the \n not consumed by nextInt()

		Reservation r = new Reservation(type, startDate, duration);
		if (!isDateAvailable(routerSerialNumber, r.getStartDate(), r.getDueDate())) {
			System.out.print("The router isn't available.\n");
			return null;
		}

		systemUpdate(routerSerialNumber, r, '+');
		return createInvoice(routerSerialNumber, r, isResident);
	}

	public Invoice extendReservation(Invoice invoice, int extendedDuration, boolean isResident) {
		Date oldDueDate = reservation.get(invoice.getReservationNumber()).getDueDate();
		Date virtuallyStartDate = new Date(oldDueDate.getTime() + (long) 1);
		char type = reservation.get(invoice.getReservationNumber()).getType();
		int days;
		if (type == 'd')
			days = extendedDuration;
		else if (type == 'w')
			days = extendedDuration * 7;
		else
			days = extendedDuration * 30;
		Date newDueDate = new Date(virtuallyStartDate.getTime() + ((1000 * 60 * 60 * 24) * days) - (long) 1);
		if (!isDateAvailable(invoice.getRouterSerialNumber(), virtuallyStartDate, newDueDate)) {
			System.out.print("Router isn't available\n");
			return null;
		}

		reservation.get(invoice.getReservationNumber())
				.setDuration(reservation.get(invoice.getReservationNumber()).getDuration() + extendedDuration);
		return createInvoice(invoice.getRouterSerialNumber(), reservation.get(invoice.getReservationNumber()),
				isResident);
	}

	/**
	 * Cancel a reservation.
	 * 
	 * @param invoice Used by the customer to reserve the router
	 * @return boolean Returns true if the reservation canceled successfully
	 */
	public boolean cancelReservation(Invoice invoice) {
		Date today = new Date();
		long timeDiff = reservation.get(invoice.getReservationNumber()).getStartDate().getTime() - today.getTime();
		if (timeDiff < 2 * (1000 * 60 * 60 * 24))
			return false;

		return systemUpdate(invoice.getRouterSerialNumber(), reservation.get(invoice.getReservationNumber()), '-');
	}

	public Invoice changeRouter(int routerSerialNumber, Invoice invoice, boolean isResident) {
		if (!routerExists(routerSerialNumber)) {
			System.out.print("Serial Number not found.\n");
			return null;
		}

		if (!isDateAvailable(routerSerialNumber, reservation.get(invoice.getReservationNumber()).getStartDate(),
				reservation.get(invoice.getReservationNumber()).getDueDate())) {
			System.out.print("Router isn't available.\n");
			return null;
		}
		routerSchedule.get((Integer) invoice.getRouterSerialNumber()).remove((Integer) invoice.getReservationNumber());
		routerSchedule.get((Integer) invoice.getRouterSerialNumber()).add(invoice.getReservationNumber());

		return createInvoice(routerSerialNumber, reservation.get(invoice.getReservationNumber()), isResident);
	}

	public void getFeedback(String feedback) {
		SystemManager.feedback.add(feedback);
	}
}

class Customer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5L;
	private ArrayList<Invoice> invoice;
	private final boolean isResident;
	private final int id;
	private static CustomerSystemManager customerSystemManager = CustomerSystemManager.getInstance();

	public Customer(int id, boolean isResident) {
		this.isResident = isResident;
		this.invoice = new ArrayList<Invoice>();
		this.id = id;// random id number from 1 to 10
	}

	public boolean isResident() {
		return isResident;
	}

	public int getID() {
		return id;
	}

	public ArrayList<Invoice> getInvoices() {
		return invoice;
	}

	public boolean rentRouter(int routerSerialNumber) {
		Invoice i = customerSystemManager.makeReservation(routerSerialNumber, isResident);
		if (i == null) {
			return false;
		}

		invoice.add(i);
		return true;
	}

	private int chooseInvoice() {
		System.out.print("Please, choose the invoice number: ");
		int index = RouterRental.read.nextInt();
		RouterRental.read.nextLine(); // throw away the \n not consumed by nextInt()
		if (invoice.size() < index)
			return -1;
		return index;
	}

	public boolean extendRentDuration() {
		int index = chooseInvoice();
		if (index == -1) {
			return false;
		}

		System.out.print("Please, enter the extended duration: ");
		int duration = RouterRental.read.nextInt();
		RouterRental.read.nextLine(); // throw away the \n not consumed by nextInt()

		Invoice i = customerSystemManager.extendReservation(invoice.get(index - 1), duration, isResident);

		if (invoice == null) {
			return false;
		}

		invoice.set(index - 1, i);
		return true;
	}

	public boolean cancelRent() {
		int index = chooseInvoice();

		if (index == -1) {
			return false;
		}
		boolean b = customerSystemManager.cancelReservation(invoice.get(index - 1));
		if (!b)
			return false;

		invoice.remove(index - 1);
		return b;
	}

	public boolean changeModel(int routerSerialNumber) {
		int index = chooseInvoice();

		if (index == -1) {
			return false;
		}

		Invoice i = customerSystemManager.changeRouter(routerSerialNumber, invoice.get(index - 1), isResident);
		if (i == null) {
			return false;
		}

		invoice.set(index - 1, i);
		return true;
	}

	public void sendFeedback() {
		System.out.print("Enter your feedback: ");
		customerSystemManager.getFeedback(RouterRental.read.nextLine());
	}

	public Integer showReservation() {
		int index = chooseInvoice();
		if(index == -1) {
			return null;
		}
		
		return invoice.get(index).getReservationNumber();
	}
}

/**
 * Class that holds functionalities needed by the admins
 */
class AdminstratorSystemManager extends UserSystemManager {
	private static AdminstratorSystemManager adminstratorSystemManager;
	private static boolean isUsed = false;

	private AdminstratorSystemManager() {
	}

	public static AdminstratorSystemManager getInstance() {
		if (!isUsed) {
			adminstratorSystemManager = new AdminstratorSystemManager();
			isUsed = true;
			return adminstratorSystemManager;
		} else
			return null;
	}

	/**
	 * Overloaded function to update all tracked records. Edit the router's records.
	 * 
	 * @param r         Router object
	 * @param operation [Operation '+': to add, Operation '-': to delete]
	 * @return boolean Returns true if the system is updated successfully
	 */
	private boolean systemUpdate(Router r, char operation) {
		if (operation == '+') {
			if (routerExists(r.getSerialNumber())) {
				System.out.print("Router exists\n");
				return false;
			}

			router.put(r.getSerialNumber(), r);
			routerSchedule.put(r.getSerialNumber(), new ArrayList<Integer>());
			return true;
		} else if (operation == '-') {
			if (router.isEmpty() || !routerExists(r.getSerialNumber())) {
				System.out.print("Router doesn't exist\n");
				return false;
			}

			router.remove(r.getSerialNumber());
			ArrayList<Integer> arrayList = routerSchedule.get(r.getSerialNumber());
			for (Integer reservationNumber : arrayList) {
				reservation.remove(reservationNumber);
			}
			routerSchedule.remove(r.getSerialNumber());
			return true;
		}
		return false;
	}

	public boolean addRouter(Router r) {
		return systemUpdate(r, '+');
	}

	public boolean removeRouter(Integer routerSerialNumber) {
		return systemUpdate(router.get(routerSerialNumber), '-');
	}
}

class Administrator {
	private static AdminstratorSystemManager adminstratorSystemManager;
	private final int id;

	public Administrator(int id) {
		adminstratorSystemManager = AdminstratorSystemManager.getInstance();
		this.id = id;
	}

	public int getID() {
		return id;
	}

	public boolean addRouter() {
		System.out.print("Please, enter the router information\n");
		System.out.print("Router's serial number: ");
		int serialNumber = RouterRental.read.nextInt();
		RouterRental.read.nextLine(); // throw away the \n not consumed by nextInt()
		System.out.print("Router's model: ");
		char model = RouterRental.read.next().charAt(0);
		System.out.print("Number of ports: ");
		int portsNumber = RouterRental.read.nextInt();
		RouterRental.read.nextLine(); // throw away the \n not consumed by nextInt()
		return adminstratorSystemManager.addRouter(new Router(serialNumber, model, portsNumber));

	}

	public boolean removeRouter() {
		System.out.print("Please, enter router's serial number: ");
		Integer serialNumber = RouterRental.read.nextInt();
		RouterRental.read.nextLine(); // throw away the \n not consumed by nextInt()
		return adminstratorSystemManager.removeRouter(serialNumber);
	}
}

/**
 * Class that manages all interactions between the user and the system
 * 
 * @see <a href=
 *      "https://stackoverflow.com/questions/23450524/java-scanner-doesnt-wait-for-user-input">
 *      Java Scanner doesn't wait for user input [duplicate] </a>
 */

public class RouterRental {
	public static final Scanner read = new Scanner(System.in);
	private static SystemInformationPrinter systemInformationPrinter = SystemInformationPrinter.getInstance();
	private static Administrator administrator = new Administrator(2222);
	private static HashMap<Integer, Customer> customer;
	private static FileManager fileManager = new FileManager();
	private static boolean isCalled = false;
	private static boolean isAdmin = false;
	private static boolean isLogedin = false;

	private static void loadUsersFromFiles() throws IOException, ClassNotFoundException {
		if (!isCalled)
			isCalled = true;
		else
			return;

		fileManager.checkSystemDataBase();

		if (!fileManager.isFileEmpty("customer.ser")) {
			customer = (HashMap<Integer, Customer>) fileManager.readFromFile("customer.ser");
		}else {
			customer = new HashMap<Integer, Customer>();
		}
	}

	private static void loadUsersToFiles() throws IOException {
		fileManager.writeToFile("customer.ser", customer);
	}

	private static Integer logIn() {
		System.out.print("1] Admin\n" + "2] Customer\n" + "Enter your choice: ");
		int choice = read.nextInt();
		read.nextLine();
		System.out.print("Enter your ID: ");
		Integer id = read.nextInt();
		read.nextLine();
		if (choice == 1) {
			if (id == administrator.getID()) {
				System.out.print("Welcome Admin :D\n");
				isAdmin = true;
				isLogedin = true;
				return id;
			} else {
				System.out.print("Wrong ID\n");
				return null;
			}
		} else if (choice == 2) {
			if (customer.containsKey((Integer) id)) {
				System.out.print("Welcome again :D\n");
				isAdmin = false;
				isLogedin = true;
				return id;
			} else {
				System.out.print("Wrong ID\n");
				return null;
			}
		}
		return null;
	}

	private static Integer signIn() {
		System.out.print("Enter an ID: ");
		Integer id = read.nextInt();
		read.nextLine();
		System.out.print("Are you from egypt? [Y/n]: ");
		char c = read.next().charAt(0);
		Character.toLowerCase(c);

		if (!customer.containsKey(id)) {
			if (c == 'y' || c == '\n') {
				customer.put(id, new Customer(id, true));
			} else if (c == 'n') {
				customer.put(id, new Customer(id, false));
			} else {
				return null;
			}
		} else {
			System.out.print("Customer already exists\n" + "System will login automatically\n");
		}

		isLogedin = true;
		isAdmin = false;
		return id;
	}

	public static void main(String[] args) {
		try {
			loadUsersFromFiles();
		} catch (ClassNotFoundException | IOException e2) {
			System.out.print("Failed to load users\n");
			e2.printStackTrace();
			System.exit(1);
		}
		
		try {
			SystemManager.startSystem();
		} catch (ClassNotFoundException | IOException e1) {
			System.out.print("Failed to start the system\n");
			e1.printStackTrace();
			System.exit(1);
		}
		
		Integer id = null;
		
		while (true) {
			if (!isLogedin) {
				System.out.print("1] Login\n" + "2] Signin\n" + "3] Close the system\n" + "Enter your choice: ");
				int choice = read.nextInt();
				read.nextLine();
				if (choice == 1)
					id = logIn();
				else if (choice == 2)
					id = signIn();
				else if (choice == 3)
					break;
			} else {
				if (isAdmin) {
					System.out.print("1] Add new router\n" + "2] Remove existing router\n" + "3] Read all feedbacks\n"
							+ "4] Print available routers\n" + "5] Print router's schedule\n" + "6] Logout\n"
							+ "Enter your choice: ");
					int choice = read.nextInt();
					read.nextLine();
					if (choice == 1) {
						if (!administrator.addRouter())
							System.out.print("Failed to add new router\n");
						else
							System.out.print("New router added successfully\n");
					} else if (choice == 2) {
						if (!administrator.removeRouter())
							System.out.print("Failed to remove router\n");
						else
							System.out.print("Router removed successfully\n");
					} else if (choice == 3) {
						systemInformationPrinter.printAllFeedbacks();
					} else if (choice == 4) {
						systemInformationPrinter.printSystemRouters();
					} else if (choice == 5) {
						System.out.print("Enter router serial number: ");
						Integer routerSerialNumber = read.nextInt();
						read.nextLine();
						systemInformationPrinter.printRouterSchedule(routerSerialNumber);
					} else if (choice == 6) {
						isLogedin = false;
					}
				} else {
					System.out.print("1] Rent router\n" + "2] Change router\n" + "3] Extend reservation duration\n"
							+ "4] Cancel Reservation\n" + "5] Send feedback\n" + "6] Print available routers\n"
							+ "7] Print schedule of a router\n" + "8] Print invoices\n"
									+ "9] Print your reservation\n" + "10] Logout\n"
							+ "Enter your choice: ");
					int choice = read.nextInt();
					read.nextLine();
					if (choice == 1) {
						System.out.print("Enter the router serial number: ");
						Integer routerSerialNumber = read.nextInt();
						read.nextLine();
						if (!customer.get(id).rentRouter(routerSerialNumber))
							System.out.print("Failed to make reservation.\n");
						else
							System.out.print("Reservation made successfully\n");
					} else if (choice == 2) {
						System.out.print("Enter the router serial number\n");
						Integer routerSerialNumber = read.nextInt();
						read.nextLine();
						if (!customer.get(id).changeModel(routerSerialNumber))
							System.out.print("Failed to change reservation.\n");
						else
							System.out.print("Reservation changed successfully\n");
					} else if (choice == 3) {
						if (!customer.get(id).extendRentDuration())
							System.out.print("Failed to extend reservation\n");
						else
							System.out.print("Reservation extended successfully\n");
					} else if (choice == 4) {
						if (!customer.get(id).cancelRent())
							System.out.print("Failed to cancel reservation\n");
						else
							System.out.print("Reservation canceled successfully\n");
					} else if (choice == 5) {
						customer.get(id).sendFeedback();
					} else if (choice == 6) {
						systemInformationPrinter.printSystemRouters();
					} else if (choice == 7) {
						System.out.print("Enter router serial number: ");
						Integer routerSerialNumber = read.nextInt();
						read.nextLine();
						systemInformationPrinter.printRouterSchedule(routerSerialNumber);
					} else if (choice == 8) {
						systemInformationPrinter.printCustomerInvoices(customer.get(id).getInvoices());
					}else if(choice == 9) {
						Integer reservattionNumber = customer.get(id).showReservation();
						if(reservattionNumber != null)
							systemInformationPrinter.printReservation(reservattionNumber);
						else
							System.out.print("Invoice not found");
					}
					else if (choice == 10) {
						isLogedin = false;
					}
				}
			}
		}

		try {
			SystemManager.closeSystem();
		} catch (IOException e) {
			System.out.print("Failed to close the system properly\n");
			e.printStackTrace();
		}

		try {
			loadUsersToFiles();
		} catch (IOException e) {
			System.out.print("Failes to save users\n");
			e.printStackTrace();
		}
	}
}
