package p1;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DemoBatabase 
{
		public static void main(String arg[])
		{
			try
			{
				Class.forName("org.h2.Driver");
				Connection com=DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test","sa","sa");
				Statement statement=com.createStatement();
				ResultSet result=statement.executeQuery("select * from category1");
				
				while(result.next())
				{
					System.out.print(result.getInt(1)+"\t");
					System.out.print(result.getString(2)+"\t");
					System.out.println(result.getInt(3));
				}
				statement.close();
				com.close();
			
			}
			catch(Exception e)
			{
				System.out.println("Exception Arised:"+e);
			}
		}
	}


