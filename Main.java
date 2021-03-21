package poise;

import java.sql.*;
import java.text.*;
import java.util.*;

/**
 * This program is for a small engineering firm called Poise, to help it manage its projects
 * It allows the users to create new projects, update projects details, view details and finalise projects.
 * The program is connected to a database, all the information is stored in a MySQL database.
 * @author Mzomuhle Ntshangase
 */

public class Main {

	public static void main(String[] args) throws ParseException{
		Scanner input = new Scanner(System.in);
		System.out.println("Welcome to Poised Online System");

		while(true){

			System.out.println("\nSelect appropriate option to continue");
			System.out.println("1 - To Create a new project \n2 - To Update information about existing projects"
					+"\n3 - To Finalise existing projects \n4 - To See a list of projects still to be completed."
					+"\n5 - To See a list of projects past the due date \n6 - To Find and select a project"
					+"\n7 - To Exit the program");
			System.out.print("Enter here: ");
			String option = input.nextLine();

			
			try {
				// Connect to the poisepms database, via the jdbc:mysql: channel on localhost (this PC)
				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisepms?useSSL=false",
						"otheruser",
						"swordfish"
						);

				// Create a direct line to the database for running our queries
				Statement statement = connection.createStatement();
				

				// capture all project details
				if(option.equals("1")){
					System.out.println("\nCreating a new project ");

					// capturing project details
					Project project = new Project(null, null, null, null, null, null);
					project.ProjectInput(statement);
					
					Invoice inv = new Invoice(0, 0, null);
					inv.inputInvoice(statement, project.getProjectNum());

					// capturing customer details
					System.out.println("\nEnter details of the Customer");
					Person customer = new Person(null, null, null, null);
					customer.setperspn("Customer");
					customer.setDetails(statement, project.getProjectNum());
					
					// capturing architect details
					System.out.println("\nEnter details of the Architect");
					Person architect = new Person(null, null, null, null);
					architect.setperspn("Architect");
					architect.setDetails(statement, project.getProjectNum());
					
					// capturing contractor details
					System.out.println("\nEnter details of the Contractor");
					Person contractor = new Person(null, null, null, null);
					contractor.setperspn("Contractor");
					contractor.setDetails(statement, project.getProjectNum());
					
				}
				
				// Update project details
				else if(option.equals("2")){
					
					System.out.println("\nUpdate project details");
					System.out.print("Project number: ");
					int ProjectNum = input.nextInt();
					input.nextLine();
					
					Project project = new Project(null, null, null, null, null, null);
					project.findProject(statement, ProjectNum);
					Invoice inv = new Invoice(0, 0, null);
					inv.updateDetails(statement, ProjectNum);
					
				}
				
				// Finalising projects
				else if(option.equals("3")){
					
					System.out.println("\nFinalising projects search by a project number");
					System.out.print("Enter project number: ");
					int ProjectNum = input.nextInt();
					input.nextLine();
					
					Project project = new Project(null, null, null, null, null, null);
					project.findProject(statement, ProjectNum);
					Invoice inv = new Invoice(0, 0, null);
					inv.findInvoice(statement, ProjectNum);
					
					System.out.println("\nConfirm a project then enter 1 to finalise:");
					String finalise = input.nextLine();
					if(finalise.equals("1")){
						project.Finalise(statement, ProjectNum);
						inv.generateInvoice(statement, ProjectNum);
					}
				
				}
				
				// See a list of projects
				else if(option.equals("4")){
					
					System.out.println("\nSee a list of projects still to be completed.");
					Project project = new Project(null, null, null, null, null, null);
					project.incompleteProjects(statement);
					
				}
				
				// projects past due date
				else if(option.equals("5")){
					
					System.out.println("\nSee a list of projects past the due date.");
					Invoice inv = new Invoice(0, 0, null);	
					inv.pastDuedate(statement);
					
				}
				
				// View a project
				else if(option.equals("6")){
					
					System.out.println("\nView a project or all projects \nSelect 1 to view only one project or 2 to view all projects");
					String viewOption = input.nextLine();
					
					Project project = new Project(null, null, null, null, null, null);
					
					if(viewOption.equals("1")){
						
						System.out.print("Enter project number: ");
						int ProjectNum = input.nextInt();
						input.nextLine();
						project.findProject(statement, ProjectNum);
						
					}
					
					if(viewOption.equals("2")){
						
						project.viewAll(statement);
								
					}
	
				}
				
				// exiting program
				else if(option.equals("7")){
					System.out.print("\nLogout success thank you!");
					break;
				}
				
				
				statement.close();
				connection.close();


			} catch (SQLException e) {
				// Catch a SQLException.
				e.printStackTrace();
			}

		}

		input.close();
	}


}
