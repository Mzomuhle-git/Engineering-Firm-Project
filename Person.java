package poise;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/** This class is for person objects such as architect, contractor and customer*/

public class Person {
	
	Scanner input = new Scanner(System.in);
	int rowsAffected;
	private String person;
	
	String name;
	String telNum;
	String email;
	String physicalAdd;

	
	public String getperson() {
		return person;
	}

	public void setperspn(String stakeholder) {
		this.person = stakeholder;
	}

	
	/**
	 * Person constructor
	 */
	public Person(String name, String telNum, String email, String physicalAdd) {
		this.name = name;
		this.telNum = telNum;
		this.email = email;
		this.physicalAdd = physicalAdd;
	}

	
/**  function for obtaining person data 
 * @throws SQLException */
	public void setDetails(Statement statement, String ProjectNum) throws SQLException{
		System.out.print("Name: ");
		name = input.nextLine();
		System.out.print("Telephone number: ");
		telNum = input.nextLine();
		System.out.print("Email address: ");
		email = input.nextLine();
		System.out.print("Physical Address: ");
		physicalAdd = input.nextLine();
		System.out.println("Details captured");
		
		int Project_Num = Integer.parseInt(ProjectNum);
		
		if(getperson().equals("Customer")){
			
			rowsAffected = statement.executeUpdate("INSERT INTO Customer VALUES ("+Project_Num+", '"+name+"', '"+telNum+"', '"+email+"', '"+physicalAdd+"')");
			System.out.println("Query complete, " + rowsAffected + " rows updated.");
			
		}
		else if(getperson().equals("Architect")){
			
			rowsAffected = statement.executeUpdate("INSERT INTO Architect VALUES ("+Project_Num+", '"+name+"', '"+telNum+"', '"+email+"', '"+physicalAdd+"')");
			System.out.println("Query complete, " + rowsAffected + " rows updated.");
			
		}
		else if(getperson().equals("Contractor")){
			 
			rowsAffected = statement.executeUpdate("INSERT INTO Contractor VALUES ("+Project_Num+", '"+name+"', '"+telNum+"', '"+email+"', '"+physicalAdd+"')");
			System.out.println("Query complete, " + rowsAffected + " rows updated.");
			
		}
		
	}
	
	
	/**Getting person details
	 */
	public void findPerson(Statement statement, int ProjectNum) throws SQLException{
		
		ResultSet results = statement.executeQuery("SELECT * FROM Customer WHERE ProjectNum = "+ProjectNum);
		
		// Loop over the results, printing them all.
		while(results.next()){
			System.out.println("Project number: "+results.getInt("ProjectNum") + "\nName: " +results.getString("Name")+"\nTelNum: "
			+results.getString("TelNum")+"\nEmail : "+results.getString("Email")+"\nAddress : "+results.getString("PhysicalAddress"));
		}
		
	}

	
}
