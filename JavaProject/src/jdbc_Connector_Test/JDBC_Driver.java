package jdbc_Connector_Test;

import java.sql.*;

public class JDBC_Driver {

	public static void main(String[] args) {
		
		try {
			
			// 1.get a connection
			Connection myConn = 
					DriverManager.getConnection
						("jdbc:mysql://localhost:3306/java_project_database_master", "root", "Adeftday0302!?");
			
			// 2. create a statement
			Statement myStat = myConn.createStatement();
			
			// 3. execute sql query
			ResultSet myRs = myStat.executeQuery("SELECT * FROM users");
			 
			// 4. process the result set
			while (myRs.next()) {
			
				System.out.printf("%-17s %-17s", 
						myRs.getString("UserName"), myRs.getString("PasswordAsHash"));
				
			}
			
			
		} catch (Exception ex) {
			
			ex.printStackTrace();
		}

	}

}
