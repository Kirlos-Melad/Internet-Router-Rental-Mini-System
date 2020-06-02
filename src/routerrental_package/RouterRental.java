package routerrental_package;

import java.io.Serializable;
/**
 * Used packages
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

/**
 * An interface prints all the information of an instance. Applies Single
 * Responsibility & Open-Closed from the SOLID Principles
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
	@Override
	public void printAllDataMemberInformation(Object obj) {
		try {
			Invoice i = (Invoice) obj;
			System.out.printf("Reservation's number: %d\nRouter serial number: %d\nFees: %d\n",
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
	 * 
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
			setPortsNumber(SystemManager.read.nextInt());
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
			setModel(SystemManager.read.next().charAt(0));
		}
	}
}

/**
 * Creates a reservation.
 */
class Reservation implements Serializable {
	private static final long serialVersionUID = 3L;

	/**
	 * Static member counts the instances of the class
	 */
	private static int counter = 0;
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
			setStartDate(SystemManager.read.nextLine());
		} catch (ParseException e) {
			System.out.print("Couldn't identify the entered format.\n"
					+ "Please, enter the date again using the next format [day-month-year at hour:minutes PM/AM]:");
			setStartDate(SystemManager.read.nextLine());
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
			setDuration(SystemManager.read.nextInt());
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
}

/**
 * Creates an invoice. Single responsibility applied.
 */
class Invoice implements Serializable {
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
	private int fees;

	/**
	 * @param router      Takes router instance
	 * @param reservation Takes reservation instance
	 */
	public Invoice(Router router, Reservation reservation) {
		this.routerSerialNumber = router.getSerialNumber();
		this.reservationNumber = reservation.getNumber();
		setFees(router.getModel(), reservation.getType(), reservation.getDuration());
	}

	/**
	 * Calculates the fees automatically
	 * 
	 * @param routerModel         Router's model
	 * @param reservationType     Type of reservation
	 * @param reservationDuration Duration of reservation
	 */
	private void setFees(char routerModel, char reservationType, int reservationDuration) {
		int days;
		if (routerModel == 'd')
			days = reservationDuration;
		else if (routerModel == 'w')
			days = reservationDuration * 7;
		else
			days = reservationDuration * 30;

		this.fees = (2 * days) + (Integer.valueOf('Z' - routerModel) * 5);
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
	public int getFees() {
		return fees;
	}
}

/**
 * @see <a href="https://www.techiedelight.com/implement-pair-class-java/">
 *      Implement Pair class in Java </a>
 *
 * @param <U>
 * @param <V>
 */
class Pair<U, V> {
	public U first;
	public V second;

	public Pair(U first, V second) {
		this.first = first;
		this.second = second;
	}
}

/**
 * Creates system manager
 * 
 * @see <a href="https://www.tutorialspoint.com/java/java_hashmap_class.htm">
 *      Java - The HashMap Class </a>
 */
abstract class SystemManager {
	public static final Scanner read = new Scanner(System.in);
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
	protected static ArrayList<String> feedback;

	protected SystemManager() {
	}

	public static void startSystem() {
		router = new HashMap<Integer, Router>();
		reservation = new HashMap<Integer, Reservation>();
		routerSchedule = new HashMap<Integer, ArrayList<Integer>>();
		feedback = new ArrayList<String>();
	}

	public static void closeSystem() {
		read.close();
		System.exit(1);
	}

	/**
	 * Overloaded function to update all tracked records. Edit the reservation's
	 * records.
	 * 
	 * @param routerSerialNumber Serial number of the router
	 * @param r                  Reservation object
	 * @param operation          [Operation '+': to add, Operation '-': to delete]
	 * @return boolean Returns true if the system is updated successfully
	 */
	protected static final boolean systemUpdate(Integer routerSerialNumber, Reservation r, char operation) {
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
	private static void sortSchedule(ArrayList<Integer> reservationList) {
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
	protected static boolean routerExists(Integer routerSerialNumber) {
		return router.containsKey(routerSerialNumber);
	}
}

class adminstratorSystemManager extends SystemManager {
	private adminstratorSystemManager() {
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
			if (routerExists(r.getSerialNumber()))
				return false;

			router.put(r.getSerialNumber(), r);
			routerSchedule.put(r.getSerialNumber(), new ArrayList<Integer>());
			return true;
		} else if (operation == '-') {
			if (router.isEmpty() || !routerExists(r.getSerialNumber()))
				return false;

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

}

class customerSystemManager extends SystemManager {
	private customerSystemManager() {
	}

	/**
	 * Checks if the router available by the given date.
	 * 
	 * @param routerSerialNumber Serial number of the router
	 * @param startDate          start date
	 * @param endDate            due date
	 * @return boolean Returns true if available
	 */
	private static boolean isDateAvailable(Integer routerSerialNumber, Date startDate, Date endDate) {
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
	private static Invoice createInvoice(Integer routerSerialNumber, Reservation r) {
		return new Invoice(router.get(routerSerialNumber), r);
	}

	/**
	 * Makes a reservation for the customer.
	 * 
	 * @param routerSerialNumber Serial number of the router
	 * @return Invoice Used by the customer to reserve the router
	 */
	public static Invoice makeReservation(int routerSerialNumber) {
		if (!routerExists(routerSerialNumber)) {
			System.out.print("Serial Number not found.\n");
			return null;
		}

		System.out.print("Please, enter your reservation information:\n");

		System.out.print("Start date using this format [day-month-year at hour:minutes PM/AM]: ");
		String startDate = SystemManager.read.nextLine();
		System.out.print("Type of reservation: ");
		char type = SystemManager.read.next().charAt(0);
		System.out.print("Duration: ");
		int duration = SystemManager.read.nextInt();

		Reservation r = new Reservation(type, startDate, duration);
		if (!isDateAvailable(routerSerialNumber, r.getStartDate(), r.getDueDate())) {
			System.out.print("The router isn't available.\n");
			return null;
		}

		systemUpdate(routerSerialNumber, r, '+');
		return createInvoice(routerSerialNumber, r);
	}

	public static Invoice extendReservation(Invoice invoice, int extendedDuration) {
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
		return new Invoice(router.get(invoice.getRouterSerialNumber()),
				reservation.get(invoice.getReservationNumber()));
	}

	/**
	 * Cancel a reservation.
	 * 
	 * @param invoice Used by the customer to reserve the router
	 * @return boolean Returns true if the reservation canceled successfully
	 */
	public static boolean cancelReservation(Invoice invoice) {
		Date today = new Date();
		long timeDiff = reservation.get(invoice.getReservationNumber()).getStartDate().getTime() - today.getTime();
		if (timeDiff < 2 * (1000 * 60 * 60 * 24))
			return false;

		return systemUpdate(invoice.getRouterSerialNumber(), reservation.get(invoice.getReservationNumber()), '-');
	}

	public static Invoice changeRouter(int routerSerialNumber, Invoice invoice) {
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

		return createInvoice(routerSerialNumber, reservation.get(invoice.getReservationNumber()));
	}

	public static void getFeedback(String feedback) {
		SystemManager.feedback.add(feedback);
	}
}

abstract class Customer {
	protected ArrayList<Invoice> invoice;
	protected InvoicePrinter printer;

	public Customer() {
		this.invoice = new ArrayList<Invoice>();
		printer = new InvoicePrinter();
	}

	public void printInvoices() {
		if (invoice.isEmpty())
			System.out.print("No invoices were found\n");

		for (int i = 0; i < invoice.size(); i++) {
			System.out.printf("%d] ", i + 1);
			printer.printAllDataMemberInformation(invoice.get(i));
		}
	}

	public boolean rentRouter(int routerSerialNumber) {
		Invoice i = customerSystemManager.makeReservation(routerSerialNumber);
		if (i == null) {
			System.out.print("Failed to make reservation.\n");
			return false;
		}

		invoice.add(i);
		return true;
	}

	public boolean extendRentDuration() {
		System.out.print("Please, choose the reservation number you want to extend: ");
		int index = SystemManager.read.nextInt();

		if (invoice.size() < index) {
			System.out.print("Failed to cancel reservation.\n");
			return false;
		}

		System.out.print("Please, enter the extended duration: ");
		int duration = SystemManager.read.nextInt();

		Invoice i = customerSystemManager.extendReservation(invoice.get(index), duration);

		if (invoice == null) {
			System.out.print("Failed to make reservation.\n");
			return false;
		}

		invoice.set(index, i);
		return true;
	}

	public boolean cancelRent() {
		System.out.print("Please, choose the reservation number you want cancel: ");
		int index = SystemManager.read.nextInt();

		if (invoice.size() < index) {
			System.out.print("Failed to cancel reservation.\n");
			return false;
		}
		boolean b = customerSystemManager.cancelReservation(invoice.get(index));
		if (!b)
			System.out.print("Failed to cancel reservation.\n");

		invoice.remove(index);
		return b;
	}

	public boolean changeModel(int routerSerialNumber) {
		System.out.print("Please, choose the reservation number you want to modify: ");
		int index = SystemManager.read.nextInt();

		if (invoice.size() < index) {
			System.out.print("Failed to change reservation.\n");
			return false;
		}

		Invoice i = customerSystemManager.changeRouter(routerSerialNumber, invoice.get(index));
		if (i == null) {
			System.out.print("Failed to change reservation.\n");
			return false;
		}

		invoice.set(index, i);
		return true;
	}

	public void sendFeedback() {
		System.out.print("Enter your feedback: ");
		customerSystemManager.getFeedback(SystemManager.read.nextLine());
	}
}

public class RouterRental {

	public static void main(String[] args) throws ParseException {
		SystemManager.startSystem();

		

		SystemManager.closeSystem();
	}

}
