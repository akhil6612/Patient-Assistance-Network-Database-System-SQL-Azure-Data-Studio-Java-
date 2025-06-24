import java.io.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Sample {
	// Database credentials
	final static String HOSTNAME = "tand0000-sql-server.database.windows.net";
	final static String DBNAME = "cs-dsa-4513-F24-sql-db";
	final static String USERNAME = "tand0000";
	final static String PASSWORD = "Swarupa@6612";
	// Database connection string
	final static String URL = String.format(
			"jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;",
			HOSTNAME, DBNAME, USERNAME, PASSWORD);

	public static void main(String[] args) throws SQLException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// Connecting to the database
		try (final Connection con = DriverManager.getConnection(URL)) {
			while (true) {
				// Options
				System.out.println("WELCOME TO THE PATIENT ASSISTANT NETWORK DATABASE SYSTEM");
				System.out.println("1. Enter a new team into the database");
				System.out.println(
						"2. Enter a new client into the database and associate him or her with one or more teams");
				System.out.println(
						"3. Enter a new volunteer into the database and associate him or her with one or more teams.");
				System.out.println("4. Enter the number of hours a volunteer worked this month for a particular team");
				System.out.println(
						"5. Enter a new employee into the database and associate him or her with one or more teams");
				System.out.println("6. Enter an expense charged by an employee");
				System.out.println("7. Enter a new donor and associate him or her with several donations.");
				System.out.println("8. Retrieve the name and phone number of the doctor of a particular client.");
				System.out.println(
						"9. Retrieve the total amount of expenses charged by each employee for a particular period of\r\n"
								+ "time. The list should be sorted by the total amount of expenses");
				System.out.println(
						"10.Retrieve the list of volunteers that are members of teams that support a particular client");
				System.out.println("11.Retrieve the names of all teams that were founded after a particular date .");
				System.out.println(
						"12.Retrieve the names, social security numbers, contact information, and emergency contact\r\n"
								+ "information of all people in the database.");
				System.out.println(
						"13.Retrieve the name and total amount donated by donors that are also employees. The list\r\n"
								+ "should be sorted by the total amount of the donations, and indicate if each donor wishes to\r\n"
								+ "remain anonymous");
				System.out.println(
						"14.Increase the salary by 10% of all employees to whom more than one team must report.");
				System.out.println(
						"15.Delete all clients who do not have health insurance and whose value of importance for\r\n"
								+ "transportation is less than 5");
				System.out.println(
						"16.Import: enter new teams from a data file until the file is empty (the user must be asked\r\n"
								+ "to enter the input file name).");
				System.out.println(
						"17.Export: Retrieve names and mailing addresses of all people on the mailing list and\r\n"
								+ "output them to a data file instead of screen (the user must be asked to enter the output file\r\n"
								+ "name).");
				System.out.println("18.Quit");
				// Asking user to choose an option
				System.out.println("Enter Your Option:");
				int option = Integer.parseInt(br.readLine());
				switch (option) {
				case 1:
					try (final PreparedStatement stmt = con
							.prepareStatement("InsertTeam @Team_Name = ?, @Team_Type = ?, @Formed_date = ?")) {
						// Taking user input data

						System.out.println("Enter the Team Name: ");
						String Team_Name = br.readLine();

						System.out.println("Enter the Team Type: ");
						String Team_Type = br.readLine();

						System.out.println("Enter the Team Formed Date (yyyy-MM-dd): ");
						String teamFormationDate = br.readLine();

						// Setting the storage procedure input parameter values
						stmt.setString(1, Team_Name);
						stmt.setString(2, Team_Type);
						stmt.setString(3, teamFormationDate);

						// Calling the stored procedure
						int response = stmt.executeUpdate();

						if (response > 0) {
							System.out.println("Data inserted successfully. Rows affected: " + response);
						} else {
							try (ResultSet resultSet = stmt.executeQuery()) {
								if (resultSet != null && resultSet.next()) {
									System.out.println("Error: " + resultSet.getString(1));
								} else {
									System.out.println("Error: No specific error message returned.");
								}
							}
						}
					} catch (SQLException e) {
						System.out.println("Error occurred: " + e.getMessage());
					}
					break;
				case 2:	
						// Taking user input data
						System.out.println("Do you want to enter a new client? (yes or no): ");
						System.out.println("Enter YES to enter new client or NO for associating existing client with teams: ");
					    String isNewClientInput = br.readLine().trim().toLowerCase();
					    boolean isNewClient = "yes".equals(isNewClientInput);

					    if (isNewClient) {
					        // If it's a new client, proceed with InsertClientAndAssociateTeams

					        try (final PreparedStatement stmt = con.prepareStatement(
					                "EXEC InsertClientAndAssociateTeams @Social_Security_Number = ?, @Person_Name =?, @Gender=?, @Profession = ?, @Mailing_address  = ?, @Email_address = ?, @Phone_number=?, @on_mailing_list=?, @Emergency_Phone_number=?, @Emergency_contact_Name=?, @Relationship=?, @Doctor_Name = ?, @Doctor_Phone_number = ?, @Assigned_date = ?, @Need_type = ? ,  @Importance=?,  @policyId = ?, @providerName = ?, @providerAddress = ? , @policyType =?" )) {

					            // Taking user input for client data
					            System.out.println("Enter the Client SSN: ");
					            String client_SSN = br.readLine();

					            System.out.println("Enter the Client Name: ");
					            String client_name = br.readLine();

					            System.out.println("Enter the Client Gender(M/F): ");
					            String client_gender = br.readLine();

					            System.out.println("Enter the Client Profession: ");
					            String client_profession = br.readLine();

					            System.out.println("Enter the Client Mailing Address: ");
					            String mailing_address = br.readLine();

					            System.out.println("Enter the Client Email Address: ");
					            String email_address = br.readLine();

					            System.out.println("Enter the Client Phone Number: ");
					            String phone_number = br.readLine();

					            System.out.println("Enter if the Client is on the mailing list (1 or 0): ");
					            String mailing_list = br.readLine();
					            boolean on_mailing_list = "1".equals(mailing_list);

					            System.out.println("Enter the Client Emergency Contact Phone Number: ");
					            String emerg_contact_number = br.readLine();

					            System.out.println("Enter the Client Emergency Contact Name: ");
					            String emerg_contact_name = br.readLine();

					            System.out.println("Enter the Client Emergency Contact Relationship: ");
					            String emerg_contact_relationship = br.readLine();

					            System.out.println("Enter the Doctor Name: ");
					            String Doctor_Name = br.readLine();

					            System.out.println("Enter the Doctor Phone Number: ");
					            String Doctor_Phone_number = br.readLine();

					            System.out.println("Enter the Assigned Date(YYY-MM-DD)");
					            String AssignedDate = br.readLine();
					            
					            System.out.println("Enter the Client Need Type");
					            String needType = br.readLine();
					            
					            System.out.println("Enter the Client Need Type Importance");
					            int importance = Integer.parseInt(br.readLine());
					            
					            System.out.println("Enter the Client Policy Id");
					            String policyId = br.readLine();
					            
					            System.out.println("Enter the Client Policy Provider Name");
					            String providername = br.readLine();
					            
					            System.out.println("Enter the Policy Provider Address");
					            String provideraddress = br.readLine();
					            
					            System.out.println("Enter the Client Policy Type");
					            String policyType = br.readLine();
					            
					            
					            // Setting parameters for InsertClientAndAssociateTeams
					            stmt.setString(1, client_SSN);
					            stmt.setString(2, client_name);
					            stmt.setString(3, client_gender);
					            stmt.setString(4, client_profession);
					            stmt.setString(5, mailing_address);
					            stmt.setString(6, email_address);
					            stmt.setString(7, phone_number);
					            stmt.setBoolean(8, on_mailing_list);
					            stmt.setString(9, emerg_contact_number);
					            stmt.setString(10, emerg_contact_name);
					            stmt.setString(11, emerg_contact_relationship);
					            stmt.setString(12, Doctor_Name);
					            stmt.setString(13, Doctor_Phone_number);
					            stmt.setString(14, AssignedDate);
					            stmt.setString(15, needType);
					            stmt.setInt(16, importance);
					            stmt.setString(17, policyId);
					            stmt.setString(18, providername);
					            stmt.setString(19, provideraddress);
					            stmt.setString(20, policyType);

					            // Calling the first stored procedure
					            int response = stmt.executeUpdate();

					            if (response > 0) {
					                System.out.println("New client and related data inserted successfully. Rows affected: " + response);
					            } else {
									try (ResultSet resultSet = stmt.executeQuery()) {
										if (resultSet != null && resultSet.next()) {
											System.out.println("Error: " + resultSet.getString(1));
										} else {
											System.out.println("Error: No specific error message returned.");
										}
									}
								}

					        } catch (SQLException e) {
					            System.out.println("Error occurred while inserting client: " + e.getMessage());
					        }

					    } else {
					        // If it's an existing client, ask for SSN and directly call InsertIntoCares

					       

					        try (final PreparedStatement stmt2 = con.prepareStatement(
					                "EXEC InsertIntoCares @Client_Social_Security_Number = ?, @Team_Name = ?, @active_or_inactive = ?")) {

					            // Taking user input for team association
					        	 System.out.println("Enter the Client SSN for team association: ");
							        String client_SSN = br.readLine();
					        	
					            System.out.println("Enter the Team Name for the Client you want to associate: ");
					            String Team_Name = br.readLine();

					            System.out.println("Enter the status ('1-active', '0-inactive'): ");
					            String statusInput = br.readLine().trim();
					            boolean activeOrInactive = "1".equals(statusInput);

					            // Setting parameters for InsertIntoCares
					            stmt2.setString(1, client_SSN);  // Using the provided client SSN
					            stmt2.setString(2, Team_Name);
					            stmt2.setBoolean(3, activeOrInactive);

					            // Calling the second stored procedure
					            int response2 = stmt2.executeUpdate();

					            if (response2 > 0) {
					                System.out.println("Client associated with the team successfully. Rows affected: " + response2);
					            } else {
									try (ResultSet resultSet = stmt2.executeQuery()) {
										if (resultSet != null && resultSet.next()) {
											System.out.println("Error: " + resultSet.getString(1));
										} else {
											System.out.println("Error: No specific error message returned.");
										}
									}
								}

					        } catch (SQLException e) {
					            System.out.println("Error occurred while associating client with team: " + e.getMessage());
					        }
					    }

					
					break;
					
			 	case 3:
			 	// Taking user input data
					System.out.println("Do you want to enter a new Volunteer? (yes or no): ");
					System.out.println("Enter YES to enter new Volunteer or NO for associating existing Volunteer with teams: ");
				    String isNewVolunteerInput = br.readLine().trim().toLowerCase();
				    boolean isNewVolunteer = "yes".equals(isNewVolunteerInput);

				    if (isNewVolunteer) {
				        // If it's a new client, proceed with InsertClientAndAssociateTeams
					try (final PreparedStatement stmt = con.prepareStatement(
							"InsertVolunteerAndAssociateTeams @Social_Security_Number = ?, @Person_Name =?, @Gender=?, @Profession = ?, @Mailing_address  = ?, @Email_address = ?, @Phone_number=?, @on_mailing_list=?, @Emergency_Phone_number=?, @Emergency_contact_Name=?, @Relationship=?, @Date_of_join = ?, @Training_date = ?, @Training_location = ?")) {
						// Taking the user input data

						System.out.println("Enter the Volunteer SSN: ");
						String Volunteer_Social_Security_Number = br.readLine();
						
						 System.out.println("Enter the Volunteer Name: ");
				          String volunteer_name = br.readLine();

				            System.out.println("Enter the Volunteer Gender(M/F): ");
				            String volunteer_gender = br.readLine();

				            System.out.println("Enter the Volunteer Profession: ");
				            String volunteer_profession = br.readLine();

				            System.out.println("Enter the Volunteer Mailing Address: ");
				            String mailing_address = br.readLine();

				            System.out.println("Enter the Volunteer Email Address: ");
				            String email_address = br.readLine();

				            System.out.println("Enter the Volunteer Phone Number: ");
				            String phone_number = br.readLine();

				            System.out.println("Enter if the  Volunteer is on the mailing list (1 or 0): ");
				            String mailing_list = br.readLine();
				            boolean on_mailing_list = "1".equals(mailing_list);

				            System.out.println("Enter the Volunteer Emergency Contact Phone Number: ");
				            String emerg_contact_number = br.readLine();

				            System.out.println("Enter the  Volunteer Emergency Contact Name: ");
				            String emerg_contact_name = br.readLine();

				            System.out.println("Enter the Volunteer Emergency Contact Relationship: ");
				            String emerg_contact_relationship = br.readLine();
				            
				            System.out.println("Enter the Volunteer Date of Joining: (yyyy-MM-dd): ");
				            String dateOfJoining = br.readLine();

				            System.out.println("Enter the Volunteer Training Date: (yyyy-MM-dd): ");
				            String date_training = br.readLine();

				            System.out.println("Enter the Volunteer Training Location: ");
				            String training_location = br.readLine();
						

						// Setting the storage procedure input parameter values
						stmt.setString(1, Volunteer_Social_Security_Number);
			            stmt.setString(2, volunteer_name);
			            stmt.setString(3, volunteer_gender);
			            stmt.setString(4, volunteer_profession);
			            stmt.setString(5, mailing_address);
			            stmt.setString(6, email_address);
			            stmt.setString(7, phone_number);
			            stmt.setBoolean(8, on_mailing_list);
			            stmt.setString(9, emerg_contact_number);
			            stmt.setString(10, emerg_contact_name);
			            stmt.setString(11, emerg_contact_relationship);
						stmt.setString(12, dateOfJoining);
						stmt.setString(13, date_training);
						stmt.setString(14, training_location);
						// Calling the stored procedure
						int response = stmt.executeUpdate();

						if (response > 0) {
							System.out.println("Data inserted successfully.");
						} else {
							try (ResultSet resultSet = stmt.executeQuery()) {
								if (resultSet != null && resultSet.next()) {
									System.out.println("Error: " + resultSet.getString(1));
								} else {
									System.out.println("Error: No specific error message returned.");
								}
							} catch (SQLException e) {
								System.out.println("Error occurred: " + e.getMessage());
							}
						}

					} catch (SQLException e) {
						System.out.println("Error occurred: " + e.getMessage());
					}
					
				    }else {
				    	try (final PreparedStatement stmt2 = con.prepareStatement(
				                "EXEC InsertIntoServes @Social_Security_Number = ?, @Team_Name = ?,  @Working_Hours = ?,@active_or_inactive = ?, @Month=?")) {

				            // Taking user input for team association
				        	 System.out.println("Enter the Volunteer SSN for team association: ");
						        String Volunteer_SSN = br.readLine();
				        	
						     // associating with teams
					        System.out.println("Enter the Team Name: ");
					        String Team_Name = br.readLine();

							System.out.println("Enter the Working Hours: ");
							int working_hours = Integer.parseInt(br.readLine());

							System.out.println("Enter the status ('1-active', '0-inactive'):");
							String statusInput = br.readLine().trim();
							boolean activeOrInactive = "1".equals(statusInput);

							System.out.println("Enter the Month: ");
							String month = br.readLine();

				            // Setting parameters for InsertIntoCares
				            stmt2.setString(1, Volunteer_SSN);  // Using the provided client SSN
				            stmt2.setString(2, Team_Name);
				            stmt2.setInt(3, working_hours);
				            stmt2.setBoolean(4, activeOrInactive);
				            stmt2.setString(5, month);
				            // Calling the second stored procedure
				            int response2 = stmt2.executeUpdate();

				            if (response2 > 0) {
				                System.out.println(" Volunteer associated with the team successfully. Rows affected: " + response2);
				            } else {
								try (ResultSet resultSet = stmt2.executeQuery()) {
									if (resultSet != null && resultSet.next()) {
										System.out.println("Error: " + resultSet.getString(1));
									} else {
										System.out.println("Error: No specific error message returned.");
									}
								}
							}

				        } catch (SQLException e) {
				            System.out.println("Error occurred while associating Volunteer with team: " + e.getMessage());
				        }
				    }

				    
					break;

				case 4:
					try (final PreparedStatement stmt = con.prepareStatement(
							"UpdateVolunteerWorkingHours @Social_Security_Number = ?, @Team_Name= ?, @Working_Hours = ?, @active_or_inactive =?, @Month =?")) {
						// Taking the user input data

						System.out.println("Enter the Volunteer SSN: ");
						String Volunteer_Social_Security_Number = br.readLine();

						System.out.println("Enter the Team Name: ");
						String Team_Name = br.readLine();

						System.out.println("Enter the Working Hours: ");
						int working_hours = Integer.parseInt(br.readLine());

						System.out.println("Enter the status: ");
						String statusInput = br.readLine().trim();
						boolean activeOrInactive = "1".equals(statusInput);

						System.out.println("Enter the Month: ");
						String month = br.readLine();

						// Setting the storage procedure input parameter values
						stmt.setString(1, Volunteer_Social_Security_Number);
						stmt.setString(2, Team_Name);
						stmt.setInt(3, working_hours);
						stmt.setBoolean(4, activeOrInactive);
						stmt.setString(5, month);

						// Calling the stored procedure
						int response = stmt.executeUpdate();

						if (response > 0) {
							System.out.println("Data inserted successfully");
						} else {
							try (ResultSet resultSet = stmt.executeQuery()) {
								if (resultSet != null && resultSet.next()) {
									System.out.println("Error: " + resultSet.getString(1));
								} else {
									System.out.println("Error: No specific error message returned.");
								}
							}
						}

					} catch (SQLException e) {
						System.out.println("Error occurred: " + e.getMessage());
					}
					break;

				case 5:
					System.out.println("Do you want to enter a new Employee? (yes or no): ");
					System.out.println("Enter YES to enter new employee or NO for associating existing employee with teams: ");
				    String isNewEmployeeInput = br.readLine().trim().toLowerCase();
				    boolean isNewEmployee = "yes".equals(isNewEmployeeInput);

				    if (isNewEmployee) {
					try (final PreparedStatement stmt = con.prepareStatement(
							"InsertEmployee @Social_Security_Number = ?, @Person_Name =?, @Gender=?, @Profession = ?, @Mailing_address  = ?, @Email_address = ?, @Phone_number=?, @on_mailing_list=?, @Emergency_Phone_number=?, @Emergency_contact_Name=?, @Relationship=?,@Salary = ?, @Marital_status = ?, @Hire_date =?")) {
						// Taking the user input data

						System.out.println("Enter the Employee SSN: ");
						String employee_SSN = br.readLine();
						
						System.out.println("Enter the Employee Name: ");
				          String emp_name = br.readLine();

				            System.out.println("Enter the Employee Gender(M/F): ");
				            String employee_gender = br.readLine();

				            System.out.println("Enter the Employee Profession: ");
				            String employee_profession = br.readLine();

				            System.out.println("Enter the Employee Mailing Address: ");
				            String mailing_address = br.readLine();

				            System.out.println("Enter the Employee Email Address: ");
				            String email_address = br.readLine();

				            System.out.println("Enter the Employee Phone Number: ");
				            String phone_number = br.readLine();

				            System.out.println("Enter if the  Employee is on the mailing list (1 or 0): ");
				            String mailing_list = br.readLine();
				            boolean on_mailing_list = "1".equals(mailing_list);

				            System.out.println("Enter the Employee Emergency Contact Phone Number: ");
				            String emerg_contact_number = br.readLine();

				            System.out.println("Enter the  Employee Emergency Contact Name: ");
				            String emerg_contact_name = br.readLine();

				            System.out.println("Enter the Employee Emergency Contact Relationship: ");
				            String emerg_contact_relationship = br.readLine();
				            
				            System.out.println("Enter the Employee Salary: ");
				            String salaryInput = br.readLine().trim();
				            BigDecimal salary = new BigDecimal(salaryInput);

				            System.out.println("Enter the Employee Marital Status: ");
				            String marital_status = br.readLine();

						System.out.println("Enter the Employee Hire Date (yyyy-MM-dd): ");
						String hireDate = br.readLine();

						// associate with teams
						
						// Setting the storage procedure input parameter values
						stmt.setString(1, employee_SSN);
						stmt.setString(2, emp_name);
			            stmt.setString(3, employee_gender);
			            stmt.setString(4, employee_profession);
			            stmt.setString(5, mailing_address);
			            stmt.setString(6, email_address);
			            stmt.setString(7, phone_number);
			            stmt.setBoolean(8, on_mailing_list);
			            stmt.setString(9, emerg_contact_number);
			            stmt.setString(10, emerg_contact_name);
			            stmt.setString(11, emerg_contact_relationship);
						stmt.setBigDecimal(12, salary);
						stmt.setString(13, marital_status);
						stmt.setString(14, hireDate);

						// Calling the stored procedure
						int response = stmt.executeUpdate();

						if (response > 0) {
							System.out.println("Data inserted successfully!!");
						} else {
							try (ResultSet resultSet = stmt.executeQuery()) {
								if (resultSet != null && resultSet.next()) {
									System.out.println("Error: " + resultSet.getString(1));
								} else {
									System.out.println("Error: No specific error message returned.");
								}
							}
						}

					} 
				
				    catch (SQLException e) {
						System.out.println("Error occurred: " + e.getMessage());
					}
				    }else{
				    	try (final PreparedStatement stmt2 = con.prepareStatement(
				                "EXEC InsertIntoReports @Social_Security_Number = ?, @Team_Name = ?,  @date = ?, @description = ?")) {

				            // Taking user input for team association
				        	 System.out.println("Enter the Employee SSN for team association: ");
						        String Volunteer_SSN = br.readLine();
				        	
						     // associating with teams
						        System.out.println("Enter the Team Name: ");
								String Team_Name = br.readLine();

								System.out.println("Enter the Team Report Date (yyyy-MM-dd): ");
								String report_date = br.readLine();

								System.out.println("Enter the Report Description: ");
								String description = br.readLine();


				            // Setting parameters for InsertIntoCares
				            stmt2.setString(1, Volunteer_SSN);  // Using the provided client SSN
				            stmt2.setString(2, Team_Name);
				            stmt2.setString(3, report_date);
				            stmt2.setString(4, description);
				            // Calling the second stored procedure
				            int response2 = stmt2.executeUpdate();

				            if (response2 > 0) {
				                System.out.println(" Employee associated with the team successfully. Rows affected: " + response2);
				            } else {
								try (ResultSet resultSet = stmt2.executeQuery()) {
									if (resultSet != null && resultSet.next()) {
										System.out.println("Error: " + resultSet.getString(1));
									} else {
										System.out.println("Error: No specific error message returned.");
									}
								}
							}

				        } catch (SQLException e) {
				            System.out.println("Error occurred while associating Employee with team: " + e.getMessage());
				        }
				    }

					break;

				case 6:
					try (final PreparedStatement stmt = con.prepareStatement(
							"InsertExpenseByAnEmployee @Social_Security_Number = ?, @Expense_date=?, @Amount = ?,   @description = ?")) {
						// Taking the user input data

						System.out.println("Enter the Employee SSN: ");
						String employee_SSN = br.readLine();

						System.out.println("Enter the Expense Date(yyyy-MM-dd): ");
						String expdate = br.readLine();

						System.out.println("Enter the Expense Amount: ");
						String amt = br.readLine().trim();
						BigDecimal amount = new BigDecimal(amt);

						System.out.println("Enter the Expense Descripton: ");
						String description = br.readLine();

						// Setting the storage procedure input parameter values
						stmt.setString(1, employee_SSN);
						stmt.setString(2, expdate);
						stmt.setBigDecimal(3, amount);
						stmt.setString(4, description);

						// Calling the stored procedure
						int response = stmt.executeUpdate();

						if (response > 0) {
							System.out.println("Data inserted successfully. Rows affected: " + response);
						} else {
							try (ResultSet resultSet = stmt.executeQuery()) {
								if (resultSet != null && resultSet.next()) {
									System.out.println("Error: " + resultSet.getString(1));
								} else {
									System.out.println("Error: No specific error message returned.");
								}
							}
						}

					} catch (SQLException e) {
						System.out.println("Error occurred: " + e.getMessage());
					}
					break;

				case 7:
					try (final PreparedStatement stmt = con.prepareStatement(
							"DonorDonations @Social_Security_Number = ?, @Person_Name =?, @Gender=?, @Profession = ?, @Mailing_address  = ?, @Email_address = ?, @Phone_number=?, @on_mailing_list=?, @Emergency_Phone_number=?, @Emergency_contact_Name=?, @Relationship=?, @Anonymous=?, @Type_of_dontion = ?,  @Date = ?, @Amount=?, @Name_of_fund_campaign = ?, @Check_Number = ? , @Card_number = ?,  @Card_Type = ?, @Expiration_Date = ?")) {
						// Taking the user input data

							System.out.println("Enter the Donor SSN: ");
							String Donor_SSN = br.readLine();
						
							System.out.println("Enter the Donor Name: ");
							String Donor_name = br.readLine();

				            System.out.println("Enter the Donor Gender(M/F): ");
				            String Donor_gender = br.readLine();

				            System.out.println("Enter the Donor Profession: ");
				            String Donor_profession = br.readLine();

				            System.out.println("Enter the Donor Mailing Address: ");
				            String mailing_address = br.readLine();

				            System.out.println("Enter the Donor Email Address: ");
				            String email_address = br.readLine();

				            System.out.println("Enter the Donor Phone Number: ");
				            String phone_number = br.readLine();

				            System.out.println("Enter if the  Donor is on the mailing list (1 or 0): ");
				            String mailing_list = br.readLine();
				            boolean on_mailing_list = "1".equals(mailing_list);

				            System.out.println("Enter the Donor Emergency Contact Phone Number: ");
				            String emerg_contact_number = br.readLine();

				            System.out.println("Enter the Donor Emergency Contact Name: ");
				            String emerg_contact_name = br.readLine();

				            System.out.println("Enter the Donor Emergency Contact Relationship: ");
				            String emerg_contact_relationship = br.readLine();
				            
						System.out.println("Enter Is the donation anonymous?(1 for yes, 0 for no): ");
						String anonymousInput = br.readLine().trim();
						boolean anonymous = "1".equals(anonymousInput); // Converts "1" to true, "0" to false

						System.out.println("Enter the Type of Donation: ");
						String typeOfDonation = br.readLine();

						BigDecimal amount;
						String fundCampaignName, donation_date, expirationDate = null, checkNumber = null,
								cardNumber = null, cardType = null;
						// if donoation type is check inserting in checks table
						if (typeOfDonation.equals("check")) {
							System.out.println("Enter the Date of Donation (yyyy-mm-dd): ");
							donation_date = br.readLine();

							System.out.println("Enter the Amount: ");
							amount = new BigDecimal(br.readLine().trim());

							System.out.println("Enter the Name of the Fund Campaign: ");
							fundCampaignName = br.readLine();

							System.out.println("Enter the Check Number");
							checkNumber = br.readLine();

							// if donation type is card then in inserting in credit card table
						} else {

							System.out.println("Enter the Date of Donation (yyyy-mm-dd): ");
							donation_date = br.readLine();

							System.out.println("Enter the Amount: ");
							amount = new BigDecimal(br.readLine().trim());

							System.out.println("Enter the Name of the Fund Campaign: ");
							fundCampaignName = br.readLine();

							System.out.println("Enter the Card Number");
							cardNumber = br.readLine();

							System.out.println("Enter the Card Type");
							cardType = br.readLine();

							System.out.println("Enter the Expiration Date (yyyy-mm-dd)");
							expirationDate = br.readLine();
							;

						}
						// Setting the storage procedure input parameter values
						stmt.setString(1, Donor_SSN);
						stmt.setString(2, Donor_name);
			            stmt.setString(3, Donor_gender);
			            stmt.setString(4, Donor_profession);
			            stmt.setString(5, mailing_address);
			            stmt.setString(6, email_address);
			            stmt.setString(7, phone_number);
			            stmt.setBoolean(8, on_mailing_list);
			            stmt.setString(9, emerg_contact_number);
			            stmt.setString(10, emerg_contact_name);
			            stmt.setString(11, emerg_contact_relationship);
						stmt.setBoolean(12, anonymous);
						stmt.setString(13, typeOfDonation);
						stmt.setString(14, donation_date);
						stmt.setBigDecimal(15, amount);
						stmt.setString(16, fundCampaignName);
						stmt.setString(17, checkNumber);
						stmt.setString(18, cardNumber);
						stmt.setString(19, cardType);
						stmt.setString(20, expirationDate);

						// Calling the stored procedure
						int response = stmt.executeUpdate();

						if (response > 0) {
							System.out.println("Data inserted successfully.");
						} else {
							try (ResultSet resultSet = stmt.executeQuery()) {
								if (resultSet != null && resultSet.next()) {
									System.out.println("Error: " + resultSet.getString(1));
								} else {
									System.out.println("Error: No specific error message returned.");
								}
							}
						}

					} catch (SQLException e) {
						System.out.println("Error occurred: " + e.getMessage());
					}
					break;

				case 8:
					try (final PreparedStatement stmt = con
							.prepareStatement("RetrieveClient @Social_Security_Number = ?")) {
						// Taking the inputs from the user

						System.out.println("Enter the Client SSN: ");
						String Client_SSN = br.readLine();

						// Setting the storage procedure input parameter values
						stmt.setString(1, Client_SSN);

						ResultSet responseSet = stmt.executeQuery();

						// Displaying the result
						System.out.println("Doctor Name | Doctor Phone Number");

						// Iterating through the result set and displaying the data
						while (responseSet.next()) {
							String output = String.format("%s | %s", responseSet.getString("Doctor_Name"),
									responseSet.getString("Doctor_Phone_number")

							);

							System.out.println(output);

						}
						System.out.println("Retrieved Successfully.");
					} catch (SQLException e) {
						System.out.println("Error occurred: " + e.getMessage());
					}
					break;

				case 9:
					try (final PreparedStatement stmt = con
							.prepareStatement("GetTotalExpensesByEmployee @start_date = ?,@end_date = ?")) {
						// Taking the user input data

						System.out.println("Enter the start date for expenses charged by employees (yyyy-mm-dd): ");
						String stdate = br.readLine();

						System.out.println("Enter the End date for expenses charged by employees (yyyy-mm-dd): ");
						String enddate = br.readLine();

						// Setting the storage procedure input parameter values
						stmt.setString(1, stdate);
						stmt.setString(2, enddate);

						ResultSet responseSet = stmt.executeQuery();

						// Displaying the result
						System.out.println("Social Security Number | Total Expenses");

						// Iterating through the result set and displaying the data
						while (responseSet.next()) {
							String output = String.format("%s | %.2f", responseSet.getString("Social_Security_Number"),

									responseSet.getDouble("Total_Expenses") // Total Expenses
							);
							System.out.println(output);

						}
						System.out.println("Retrieved Successfully.");
					} catch (SQLException e) {
						System.out.println("Error occurred: " + e.getMessage());
					}
					break;

				case 10:
					try (final PreparedStatement stmt = con
							.prepareStatement("GetVolunteersByClient @Client_Social_Security_Number = ?")) {
						// Taking the user input data

						System.out.println("Enter the Client SSN: ");
						String volunteer_SSN = br.readLine();

						// Setting the storage procedure input parameter values
						stmt.setString(1, volunteer_SSN);

						ResultSet responseSet = stmt.executeQuery();

						// Displaying the result header
						System.out.println("Volunteer Social Security Number");

						// Iterating through the result set and displaying the data
						while (responseSet.next()) {
							String output = String.format("%s",
									responseSet.getString("Social_Security_Number") // Volunteer Social
																								// Security Number
							);
							System.out.println(output);

						}

						System.out.println("Retrieved Successfully.");
					} catch (SQLException e) {
						System.out.println("Error occurred: " + e.getMessage());
					}
					break;

				case 11:
					try (final PreparedStatement stmt = con.prepareStatement("NamesOfTeams @formed_date = ?")) {
						// Taking the user input data

						System.out.println("Enter the date to get the team names formed after that (yyyy-mm-dd):");
						String frmdate = br.readLine();

						// Setting the storage procedure input parameter values
						stmt.setString(1, frmdate);

						ResultSet responseSet = stmt.executeQuery();

						// Displaying the result
						System.out.println("Team Names");

						// Iterating through the result set and displaying the data
						while (responseSet.next()) {
							String output = String.format("%s", responseSet.getString("Team_Name"));
							System.out.println(output);

						}
						System.out.println("Retrieved Successfully.");
					} catch (SQLException e) {
						System.out.println("Error occurred: " + e.getMessage());
					}
					break;
				case 12:
					try (final PreparedStatement stmt = con.prepareStatement("PeopleInformation")) {

						ResultSet responseSet = stmt.executeQuery();

						// Displaying the result
						System.out.println(
								"Name | Social Security Number | Phone Number | Email Address | Emergency Contact Name | Emergency Contact Phone | Emergency Contact Relationship");

						// Iterating through the result set and displaying the data
						while (responseSet.next()) {
							String output = String.format("%s | %s | %s | %s | %s | %s | %s",
									responseSet.getString("Name"), responseSet.getString("Social_Security_Number"),
									responseSet.getString("Phone_number"), responseSet.getString("Email_address"),
									responseSet.getString("Emergency_Contact_Name"),
									responseSet.getString("Emergency_Contact_Phone"),
									responseSet.getString("Emergency_Contact_Relationship"));
							System.out.println(output);
						}
						System.out.println("Retrieved Successfully.");
					} catch (SQLException e) {
						System.out.println("Error occurred: " + e.getMessage());
					}
					break;

				case 13:
					try (final PreparedStatement stmt = con.prepareStatement("TotalAmountDonatedByDonors")) {

						ResultSet responseSet = stmt.executeQuery();

						// Displaying the result
						System.out.println("Donor Name | Total Donated | Anonymous");

						// Iterating through the result set and displaying the data
						while (responseSet.next()) {
							String output = String.format("%s | %.2f | %s", responseSet.getString("Donor_Name"),
									responseSet.getDouble("Total_Donated"),
									responseSet.getBoolean("Anonymous") ? 1 : 0);
							System.out.println(output);
						}
					} catch (SQLException e) {
						System.out.println("Error occurred: " + e.getMessage());
					}
					break;

				case 14:
					try (final PreparedStatement stmt = con.prepareStatement("IncreaseEmployeesSalary")) {
						int rowsUpdated = stmt.executeUpdate();

						// Displaying the result
						if (rowsUpdated > 0) {
							System.out.println("Salaries updated successfully.");
							System.out.println("Number of employees salaries increased: " + rowsUpdated);
						} else {
							System.out.println("None of the employees salary increased.");
						}
					} catch (SQLException e) {
						System.out.println("Error occurred: " + e.getMessage());
					}
					break;

				case 15:
					try (final PreparedStatement stmt = con.prepareStatement("DeleteClients")) {

						int rowsDeleted = stmt.executeUpdate();
						System.out.println("Clients deleted successfully.");
						// Displaying the result
						if (rowsDeleted > 0) {
							System.out.println("Clients deleted successfully.");

						} else {
							System.out.println("No clients with health insurance were deleted.");

						}
					} catch (SQLException e) {
						System.out.println("Error occurred: " + e.getMessage());
					}
					break;

				case 16:
					try {
						// Taking the user input TXT file name
						System.out.println("Enter the input TXT file name (with path): ");
						String fileName = br.readLine().trim();

						// Opening the TXT file and reading its contents
						try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))) {
							String line;
							int totalTeamsInserted = 0;

							// calling the stored procedure to insert team data
							try (PreparedStatement stmt = con
									.prepareStatement("InsertTeam @Team_Name = ?, @Team_Type = ?, @Formed_date = ?")) {

								// Reading each line
								while ((line = fileReader.readLine()) != null) {
									// Extracting data that is split by commas
									String[] teamDetails = line.split(",");
									if (teamDetails.length >= 3) {
										String teamName = teamDetails[0].trim();
										String teamType = teamDetails[1].trim();
										// Ensuring the date format is yyyy-mm-dd
										String frmddate = teamDetails[2].trim();

										// Set the parameters for the prepared statement
										stmt.setString(1, teamName);
										stmt.setString(2, teamType);
										stmt.setString(3, frmddate);

										// Calling the stored procedure
										int rowsAffected = stmt.executeUpdate();
										if (rowsAffected > 0) {
											totalTeamsInserted++;
										}
									} else {
										System.out.println("Invalid line format: " + line);
									}
								}
							}

							// result after inserting data from the file
							System.out.println("Import complete. Total teams inserted: " + totalTeamsInserted);
						} catch (FileNotFoundException e) {
							System.out.println("File not found: " + fileName);
						}
					} catch (IOException e) {
						System.out.println(e);
					} catch (SQLException e) {
						System.out.println("Error occurred: " + e.getMessage());
					}
					break;

				case 17:
					try {
						// Taking input from the user for the output file name
						System.out.println("Enter the output file name (with path): ");
						String fileName = br.readLine().trim();

						// SQL query to retrieve names and mailing addresses
						try (PreparedStatement stmt = con
								.prepareStatement("SELECT Name, Mailing_address FROM Person WHERE on_mailing_list = 1");
								ResultSet resultSet = stmt.executeQuery();
								BufferedWriter fileWriter = new BufferedWriter(new FileWriter(fileName))) {

							// Write the header line to the file
							fileWriter.write("Name,Mailing Address");
							fileWriter.newLine();

							// Iterate through the result set and write each record to the file
							while (resultSet.next()) {
								String name = resultSet.getString("Name");
								String mailingAddress = resultSet.getString("Mailing_address");

								// Formatting the line and writing it to the file
								String line = String.format("%s,%s", name, mailingAddress);
								fileWriter.write(line);
								fileWriter.newLine();
							}

							// displaying file export is completed
							System.out.println("Export complete. Data written to: " + fileName);

						} catch (FileNotFoundException e) {
							System.out.println("File not found: " + fileName);
						}
					} catch (IOException e) {
						System.out.println(e);
					} catch (SQLException e) {
						System.out.println("Error occurred: " + e.getMessage());
					}
					break;
				case 18:
					// Exit the program
					System.out.println("Quitting the Database System!");
					return;
				}
			}
		}
	}

}
