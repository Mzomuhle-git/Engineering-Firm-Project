package poise;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * This class  is for an invoice
 */
public class Invoice {

	Scanner input = new Scanner(System.in);
	ResultSet results;
	int rowsAffected;

	double TotalFee; 
	double AmountPaid;
	String Deadline;


	/**
	 * Constructor
	 */
	public Invoice(double TotalFee, double AmountPaid, String Deadline){

		this.TotalFee = TotalFee;
		this.AmountPaid = AmountPaid;
		this.Deadline = Deadline;
	}

	
	/**
	 * Getting details from user
	 * @throws ParseException
	 * @throws SQLException
	 */
	public void inputInvoice(Statement statement, String ProjectNum) throws ParseException, SQLException {

		System.out.print("Total fee: ");
		TotalFee = input.nextDouble();
		System.out.print("Amount paid: ");
		AmountPaid = input.nextDouble();
		input.nextLine();
		System.out.print("Deadline: [yyyy-MM-dd] ");
		Deadline = input.nextLine();

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");	
		java.util.Date date = format.parse(Deadline);
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());

		rowsAffected = statement.executeUpdate("INSERT INTO invoice VALUES ('"+ProjectNum+"', "+TotalFee+", "+AmountPaid+", '"+sqlDate+"')");
		System.out.println("Query complete, " + rowsAffected + " rows updated.");
				
	}


	/**
	 * Updating invoice details
	 * @throws ParseException
	 * @throws SQLException
	 */
	public void updateDetails(Statement statement, int ProjectNum) throws ParseException, SQLException{

		results = statement.executeQuery("SELECT * FROM Invoice WHERE ProjectNum = "+ProjectNum);
		while(results.next()){
			System.out.println("Total fee: "+results.getDouble("TotalFee")+"\nAmount paid: "+results.getDouble("AmountPaid")
			+"\nDeadline: "+results.getDate("Deadline"));
		}
		
		System.out.print("\nUpdate amount paid: ");
		double updatedAmount = input.nextDouble();
		input.nextLine();
		System.out.print("Update Deadline [yyyy-MM-dd]: ");
		String updatedDate = input.nextLine();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");	
		java.util.Date date = format.parse(updatedDate);
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		
		rowsAffected = statement.executeUpdate("UPDATE Invoice SET AmountPaid = "+updatedAmount+", "+"Deadline = '"+sqlDate
											  +"' WHERE ProjectNum = "+ProjectNum);
		System.out.println("Query complete, " + rowsAffected + " rows updated.");	

	}
	
	
	/**
	 * Finding invoice
	 * @throws SQLException
	 */
	public void findInvoice(Statement statement, int ProjectNum) throws SQLException{
		
		ResultSet results = statement.executeQuery("SELECT * FROM Invoice WHERE ProjectNum = "+ProjectNum);
		
		// Loop over the results, printing them all.
		while(results.next()){
			System.out.println("Project number: "+results.getInt("ProjectNum") + "\nTotal fee: " +results.getDouble("TotalFee")+"\nAmount paid: "
			+results.getDouble("AmountPaid")+"\nDeadline: "+results.getDate("Deadline"));
		}
		
	}
	
	
	/**
	 *Calculating amount due poise
	 * @throws SQLException
	 */
	public double getAmountdue(Statement statement, int ProjectNum) throws SQLException{
		double outstandingAmount = 0;
		ResultSet results = statement.executeQuery("SELECT * FROM Invoice WHERE ProjectNum = "+ProjectNum);
		while(results.next()){
			
			outstandingAmount = results.getDouble("TotalFee") - results.getDouble("AmountPaid") ;
		}
		return outstandingAmount;
		
	}
	
	
	/**
	 * Getting today's date
	 */
	public String getTodaydate(){
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDateTime now = LocalDateTime.now();
		String Datenow = dtf.format(now);
		return Datenow;
		
	}
	
	
	/**
	 * See projects past duedate
	 * @throws SQLException
	 */
	public void pastDuedate(Statement statement) throws SQLException{
		
		results = statement.executeQuery("SELECT * FROM Invoice WHERE Deadline < '"+getTodaydate()+"'");
		
		while (results.next()) {
				System.out.println("Project number: "+results.getInt("ProjectNum")+" is past duedate");
		}
		System.out.println("No other projects are past duedate");
		
	}
	
	
	/**
	 * Generating invoice after finalising project
	 * @throws SQLException
	 */
	public void generateInvoice(Statement statement, int ProjectNum) throws SQLException{
		
		// printing out invoice
		System.out.println("\nfind the invoice below! \n\n*************************************************************************");
		System.out.println("POISE ENGINEERING DURBAN \nINVOICE \nTel: 031 565 0968 \nDate: "+getTodaydate()+"\n\nProject details:");
		
		ResultSet results = statement.executeQuery("SELECT * FROM Project WHERE ProjectNum = "+ProjectNum+" and Status = 'finalised'");
		// Loop over the results, printing them all.
		while(results.next()){
			System.out.println("Project number: "+results.getInt("ProjectNum") + "\nProject name: " +results.getString("ProjectName")+"\nBuilding type: "
			+results.getString("BuildType")+"\nAddress"+results.getString("ProjectAddress")+"\nERF number: "+results.getString("ERF")
			+"\nStatus: "+results.getString("Status"));
		}
		
		System.out.println("\nCustomer details:");
		Person cust = new Person(null, null, null, null);
		cust.findPerson(statement, ProjectNum);
		System.out.println();
		findInvoice(statement, ProjectNum);
		System.out.println("\nAmount due to us is :R "+getAmountdue(statement, ProjectNum));
		System.out.println("\n************************************************************************* \nThank you");
	
	}

	
}
