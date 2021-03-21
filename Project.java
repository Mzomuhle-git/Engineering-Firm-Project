package poise;

import java.sql.*;
import java.util.*;

/**this class is for creating a project
 */

public class Project {	

	Scanner input = new Scanner(System.in);
	int rowsAffected;
	ResultSet results;

	// attributes
	String ProjectName;
	String ProjectNum;
	String BuildType;
	String ProjectAddress;
	String ERF;
	String Status;


	public String getProjectNum() {
		return ProjectNum;
	}

 
	public void setProjectNum(String projectNum) {
		ProjectNum = projectNum;
	}
	
	
	/** Constructor*/
	public Project(String ProjectName, String ProjectNum, String BuildType, String ProjectAddress, String ERF, String Status){

		this.ProjectName = ProjectName;
		this.ProjectNum = ProjectNum;
		this.BuildType = BuildType;
		this.ProjectAddress = ProjectAddress;
		this.ERF = ERF;
		this.Status = Status;
		
	}


/**	getting project details from user 
 * @throws SQLException */
	public void ProjectInput(Statement statement) throws SQLException{

		System.out.println("Enter Project details below");
		System.out.print("Project name: ");
		ProjectName = input.nextLine();
		System.out.print("Project number: ");
		ProjectNum = input.nextLine();
		int Project_Num = Integer.parseInt(ProjectNum);
		System.out.print("Project type: ");
		BuildType = input.nextLine();
		System.out.print("Physical Address: ");
		ProjectAddress = input.nextLine();
		System.out.print("ERF number: ");
		ERF = input.nextLine();
		System.out.println("The Project inprogrss");
		Status = "inprogress";
		
		rowsAffected = statement.executeUpdate("INSERT INTO Project VALUES ("+Project_Num+", '"+ProjectName+"', '"+BuildType+"', '"
		+ProjectAddress+"', '"+ERF+"', '"+Status+"')");
		System.out.println("Query complete, " + rowsAffected + " rows updated.");

	}
	
	
	/**	finding project details from database
	 * @throws SQLException */	
	public void findProject(Statement statement, int ProjectNum) throws SQLException{
		
		ResultSet results = statement.executeQuery("SELECT * FROM Project WHERE ProjectNum = "+ProjectNum);
		
		// Loop over the results, printing them all.
		while(results.next()){
			System.out.println("Project number: "+results.getInt("ProjectNum") + "\nProject name: " +results.getString("ProjectName")+"\nType: "
			+results.getString("BuildType")+"\nAddress"+results.getString("ProjectAddress")+"\nERF: "+results.getString("ERF")
			+"\nStatus: "+results.getString("Status"));
		}
		
	}
	
	
	/**	finalising a project, marking as complete
	 * @throws SQLException */	
	public void Finalise(Statement statement, int ProjectNum) throws SQLException{
		
		rowsAffected = statement.executeUpdate("UPDATE Project SET Status = 'finalised' WHERE ProjectNum = "+ProjectNum);
		System.out.println("Query complete, " + rowsAffected + " rows updated.");
		
	}
	
	
	/**	finding incomplete projects
	 * @throws SQLException */
	public void incompleteProjects(Statement statement) throws SQLException{
		
		results = statement.executeQuery("SELECT * FROM Project WHERE Status = 'inprogress' ");
		
		while (results.next()) {
			System.out.println("Project: "+results.getInt("ProjectNum")+", "+results.getString("ProjectName")+", "+results.getString("BuildType")
			+", "+results.getString("ProjectAddress")+", "+results.getString("ERF")+", "+results.getString("Status"));
		}
		
	}
	
	
	/**	viewing all projects
	 * @throws SQLException */
	public void viewAll(Statement statement) throws SQLException{
		
		results = statement.executeQuery("SELECT * FROM Project");
		
		while (results.next()) {
			System.out.println(results.getInt("ProjectNum")+", "+results.getString("ProjectName")+", "+results.getString("BuildType")
			+", "+results.getString("ProjectAddress")+", "+results.getString("ERF")+", "+results.getString("Status"));
		}
		
	}
	

}