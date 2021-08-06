import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
/**
 * This class is used to show how to use the database connection 
 */
public class Example {
   
   //This varible stores the password to your database, adjust appropriately!
   private static String password = "password";
   
   /**
    * This is a utility method to create a connection to the database, the defualt user table is used, so run this on a test database only!
    */
   private static Connection createConnection(){
      Connection c = null;
      try {
         c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/","postgres", password);
      } catch (Exception e) {
         e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
         System.exit(0);
      }
      System.out.println("Opened database successfully");
      return c;
   }
   
   /**
    * This method creates a new table on the database called student with columns ID, NAME and POSTCODE
    */
   public static void createTable() {
      //First a connection is opened using the utility method defined above.
      Connection c = createConnection();
      try {
         Statement stmt = c.createStatement();
         
         //SQL is used to define the structure of the table
         String sql = "CREATE TABLE STUDENT " +
            "(ID INT PRIMARY KEY     NOT NULL," +
            " NAME           TEXT    NOT NULL," +
            " POSTCODE       CHAR(7))";
         //The SQL is placed in a statement and then sent to the database to be run.
         stmt.executeUpdate(sql);
         //Once run the statement and connection need to be closed.
         stmt.close();
         c.close();
         System.out.println("Table created successfully");
      } catch (Exception e) {
         e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
      }
   }
   
   /**
    * This method creates two new rows in the STUDENT table
    */
   public static void insertRows() {
      //First a connection is opened using the utility method defined above.
      Connection c = createConnection();
      try {
         // Once again a statement is used to send SQL to the database
         Statement stmt = c.createStatement();
         String sql = "INSERT INTO STUDENT (ID,NAME,POSTCODE) "
            + "VALUES (1, 'Julia', 'NW1 7RS' );";
         stmt.executeUpdate(sql);

         // Now a second row is inserted using the same statement object
         sql = "INSERT INTO STUDENT (ID,NAME,POSTCODE) "
            + "VALUES (2, 'Simon', 'S21 2PQ' );";
         stmt.executeUpdate(sql);
         
         
         // Once run the statement and connection need to be closed.         
         stmt.close();
         c.close();
         System.out.println("Rows inserted successfully");
      } catch (Exception e) {
         e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
      }
      
   }

   /**
    * This method reads the rows back from the STUDENT table.
    */
   public static void selectRows() {
      //First a connection is opened using the utility method defined above.
      Connection c = createConnection();
      try {
         // Once again a statement is used to send SQL to the database
         Statement stmt = c.createStatement();
         // A result set is used to store the result of a SELECT query that is sent ot the database via a statement.
         ResultSet rs = stmt.executeQuery( "SELECT * FROM STUDENT;" );
         // As a result set can contain multiple rows, in this case 2, we need a loop to check the contents of all of them.
         while ( rs.next() ) {
            // First read the values from the result set, using getter methods to fetch data of the appropriate type.
            // This tells the API what type we are expecting for each column.
            int id = rs.getInt("id");
            String  name = rs.getString("name");
            String  postcode = rs.getString("postcode");
            // Print the found values out to the console.
            System.out.println( "ID = " + id );
            System.out.println( "NAME = " + name );
            System.out.println( "POSTCODE = " + postcode );
            System.out.println();
         }
         
         // Once run the resultset, statement and connection need to be closed.   
         rs.close();
         stmt.close();
         c.close();
      } catch (Exception e) {
         e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
      }
   }
   

   /**
    * This method drops the STUDENT table from the database
    */
   public static void dropTable() {
      //First a connection is opened using the utility method defined above.
      Connection c = createConnection();
      try {
         // Once again a statement is used to send SQL to the database
         Statement stmt = c.createStatement();
         // In this case SQL is uses to drop the table.
         String sql = "DROP TABLE STUDENT";
         stmt.executeUpdate(sql);
         
         // Once run the statement and connection need to be closed.  
         stmt.close();
         c.close();
         System.out.println("Table dropped successfully");
      } catch (Exception e) {
         e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
      }
   }
}

