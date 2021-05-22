import java.sql.*;

public class jdbc
{
    public static void main(String[] args)
    {
        System.out.println("\n\n~~~~~~~~~~~~~~~~~PROGRAM STARTS HERE~~~~~~~~~~~~~~~~~~~");
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_project", "root", "reuelsam");
            Statement stmt = conn.createStatement();

            try 
            {
                stmt.executeUpdate("insert into testtable values(3, 'Suryaa')");
            }
            catch (SQLIntegrityConstraintViolationException e )
            {
                System.out.println(e.getMessage());
            }
            
            //stmt.executeUpdate("delete from test where id = 3");

            ResultSet rs = stmt.executeQuery("SELECT * from testtable");
            while(rs.next())
            {
                System.out.println(rs.getString(1) + " " + rs.getString(2));
            }


            conn.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}