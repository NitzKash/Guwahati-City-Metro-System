import java.util.Scanner;
import java.io.IOException;
import java.sql.*;

class MetroCard  
{
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost/GhyMetro";

    static final String USER = "root";
    static final String PASS = "root";
	
	int UniqueId,i;
	String Name;
	String Address;
	int initbal=200;
	int Recharge,Bal, q;
	Scanner s=new Scanner(System.in);
	public void EnterDetails()
	{	
		Connection conn = null;
   		Statement stmt = null;
   		try
   		{
	   		Class.forName("com.mysql.jdbc.Driver");
	   		conn = DriverManager.getConnection(DB_URL,USER,PASS);
	   		//stmt = conn.createStatement();
		   	ResultSet rs=null;
                        PreparedStatement st=null;
			System.out.println("Enter your choice\n\n1.New Customer Record\n2.Recharge");
			q=s.nextInt();
			switch(q)
			{
			case 1:	
					System.out.println("Enter the customer Name");
                                        s.nextLine();
					Name=s.nextLine();
					System.out.println("Enter the unique customer id");
					UniqueId=s.nextInt();
					System.out.println("Enter the customer's address for record maintenanance");
                                        s.nextLine();
					Address=s.nextLine();
					System.out.println("Enter the amount u want to recharge additionally to the compulsory initial balance");
					Recharge=s.nextInt();
					Bal=initbal+Recharge;
                                        st=conn.prepareStatement("insert into people values(?,?,?,?)");
                                        st.setString(1, Name);
                                        st.setInt(2, UniqueId);
                                        st.setString(3, Address);
                                        st.setInt(4, Bal);
					//String q1="insert into people values("+Name+","+UniqueId+","+Address+","+Recharge+")";
					st.executeUpdate();              //IMPORTANT: returns int (no. of rows effected)                         
					break;
			case 2:         System.out.println("Enter the amount u want to recharge");
					Recharge=s.nextInt();
					System.out.println("Enter your Unique ID: ");
					UniqueId=s.nextInt();
					//String q2="Select bal from people where id="+UniqueId;
                                        st=conn.prepareStatement("Select recharge from people where id=?");
                                        st.setInt(1, UniqueId);
					rs=st.executeQuery();
					while(rs.next())
                                        {
                                            Bal  = rs.getInt("recharge");
                                            Bal+=Recharge;
					}
					/*String q3="update people set recharge="+Bal+" where id="+UniqueId;
					stmt.executeUpdate(q3);*/
                                        st=conn.prepareStatement("update people set recharge=? where id=?");
                                        st.setInt(1, Bal);
                                        st.setInt(2, UniqueId);
                                        st.executeUpdate();
                                        rs.close();
					System.out.println("The current balance is "+Bal);
					break;
			default:        System.out.println("Invalid option");
					break;
			}
			
                        st.close();
                        conn.close();
		}
		catch (Exception ee)
		{
			System.out.println("\nError connecting to database\n");
		}
	}
	public void deduct(double dedAmt)
	{
		Connection conn = null;
   		Statement stmt = null;
   		try
   		{
	   		Class.forName("com.mysql.jdbc.Driver");
	   		conn = DriverManager.getConnection(DB_URL,USER,PASS);
	   		stmt = conn.createStatement();
		   	ResultSet rs;
		   	System.out.println("\nEnter your unique ID: ");
		   	UniqueId=s.nextInt();
		   	String q1="select recharge from people where id="+UniqueId;
		   	rs=stmt.executeQuery(q1);
		   	while(rs.next())
		   	{
		   		Bal=rs.getInt("recharge");
		   	}
		   	double newBal=Bal+dedAmt;
		   	String q2="update people set recharge="+newBal+" where id="+UniqueId;
		   	stmt.executeUpdate(q2);
		   	System.out.println("Your current balance is: "+newBal);
		   	System.out.println("Fare is Rs "+dedAmt+"\n\nThank you for using Guwahati City Metro. Safe travel!");
		   	rs.close();
                        stmt.close();
                        conn.close();
		}
		catch (Exception ee)
		{
			System.out.println("\nError connecting to database\n");
		}
	}
	public void showAll()
	{
		Connection conn = null;
   		Statement stmt = null;
   		try
   		{
	   		Class.forName("com.mysql.jdbc.Driver");
	   		conn = DriverManager.getConnection(DB_URL,USER,PASS);
	   		stmt = conn.createStatement();
		   	ResultSet rs;
		   	String sql = "SELECT * FROM people";
                        rs = stmt.executeQuery(sql);
                        System.out.format("%5s%20s%40s%10s", "id","name","address","balance");
                        System.out.println();
                        while(rs.next())
                        {
                                int id  = rs.getInt("id");
                                String name = rs.getString("name");
                                String addr = rs.getString("address");
                                int bal=rs.getInt("recharge");

                                System.out.format("%5d%20s%40s%10d", id,name,addr,bal);
                                System.out.println();
                        }
                        System.out.println();
                        rs.close();
                        stmt.close();
                        conn.close();
		}
		catch (Exception ee)
		{
			System.out.println("\nError connecting to database\n");
		}
	}
}
class Stations
{
	String name;
	int sl_no, route_no;
	double dist;
}
class Main
{
	Scanner s= new Scanner(System.in);
	String username, password, user1="Nitish", user2="Chinmoy", pass="ProjectMetro";
	int count;
	Stations[] gs_station = new Stations[17];
	Stations[] zoo_station = new Stations[13];
	static void clear()
	{
		final String os=System.getProperty("os.name");
		try
		{
			if(os.contains("Windows"))
				Runtime.getRuntime().exec("cls");
			else
				Runtime.getRuntime().exec("clear");
		}
		catch(Exception e)
		{
			for(int i=0;i<1000;i++)
				System.out.println("\b");
			System.out.println("except");
		}
		finally
		{
			System.out.println("=================================Screen Cleared=================================");
		}
	}
	void assign()
	{
		int i;
		for(i=0;i<17;i++)
			gs_station[i]=new Stations();
		for(i=0;i<13;i++)
			zoo_station[i]=new Stations();
		gs_station[0].name="Ulubari";gs_station[0].sl_no=1;gs_station[0].route_no=1;gs_station[0].dist=0;
		gs_station[1].name="Lachitnagar (KFC)";gs_station[1].sl_no=2;gs_station[1].route_no=1;gs_station[1].dist=0.6;
		gs_station[2].name="Bora Service";gs_station[2].sl_no=3;gs_station[2].route_no=1;gs_station[2].dist=1.2;
		gs_station[3].name="Bhangaghar";gs_station[3].sl_no=4;gs_station[3].route_no=1;gs_station[3].dist=1.7;
		gs_station[4].name="ABC";gs_station[4].sl_no=5;gs_station[4].route_no=1;gs_station[4].dist=2.3;
		gs_station[5].name="Christian Basti";gs_station[5].sl_no=6;gs_station[5].route_no=1;gs_station[5].dist=2.9;
		gs_station[6].name="Wallfort";gs_station[6].sl_no=7;gs_station[6].route_no=1;gs_station[6].dist=3.7;
		gs_station[7].name="Ganeshguri";gs_station[7].sl_no=8;gs_station[7].route_no=1;gs_station[7].dist=4.5;
		gs_station[8].name="Dispur (State Bank)";gs_station[8].sl_no=9;gs_station[8].route_no=1;gs_station[8].dist=5.0;
		gs_station[9].name="Supermarket";gs_station[9].sl_no=10;gs_station[9].route_no=1;gs_station[9].dist=5.6;
		gs_station[10].name="Downtown";gs_station[10].sl_no=11;gs_station[10].route_no=1;gs_station[10].dist=6.2;
		gs_station[11].name="Rukmini Gaon";gs_station[11].sl_no=12;gs_station[11].route_no=1;gs_station[11].dist=6.7;
		gs_station[12].name="Six Mile";gs_station[12].sl_no=13;gs_station[12].route_no=1;gs_station[12].dist=7.4;
		gs_station[13].name="Diary";gs_station[13].sl_no=14;gs_station[13].route_no=1;gs_station[13].dist=8.0;
		gs_station[14].name="Farm gate";gs_station[14].sl_no=15;gs_station[14].route_no=1;gs_station[14].dist=8.5;
		gs_station[15].name="Research gate";gs_station[15].sl_no=16;gs_station[15].route_no=1;gs_station[15].dist=8.9;
		gs_station[16].name="Khanapara";gs_station[16].sl_no=17;gs_station[16].route_no=1;gs_station[16].dist=9.4;
		zoo_station[0].name="Commerce";zoo_station[0].sl_no=0;zoo_station[0].route_no=2;zoo_station[0].dist=0;
		zoo_station[1].name="Zoo Tini-Ali";zoo_station[1].sl_no=1;zoo_station[1].route_no=2;zoo_station[1].dist=0.8;
		zoo_station[2].name="AIDC";zoo_station[2].sl_no=2;zoo_station[2].route_no=2;zoo_station[2].dist=1.6;
		zoo_station[3].name="Zoo";zoo_station[3].sl_no=3;zoo_station[3].route_no=2;zoo_station[3].dist=2.4;
		zoo_station[4].name="Nursery";zoo_station[4].sl_no=4;zoo_station[4].route_no=2;zoo_station[4].dist=3.2;
		zoo_station[5].name="Ganeshguri";zoo_station[5].sl_no=5;zoo_station[5].route_no=2;zoo_station[5].dist=4.0;
		zoo_station[6].name="Ganesh Mandir";zoo_station[6].sl_no=6;zoo_station[6].route_no=2;zoo_station[6].dist=4.9;
		zoo_station[7].name="Rajdhani Masjid";zoo_station[7].sl_no=7;zoo_station[7].route_no=2;zoo_station[7].dist=5.6;
		zoo_station[8].name="Last Gate";zoo_station[8].sl_no=8;zoo_station[8].route_no=2;zoo_station[8].dist=6.3;
		zoo_station[9].name="Housing";zoo_station[9].sl_no=9;zoo_station[9].route_no=2;zoo_station[9].dist=7.0;
		zoo_station[10].name="Wireless";zoo_station[10].sl_no=10;zoo_station[10].route_no=2;zoo_station[10].dist=7.6;
		zoo_station[11].name="Survey";zoo_station[11].sl_no=11;zoo_station[11].route_no=2;zoo_station[11].dist=8.4;
		zoo_station[12].name="Beltola";zoo_station[12].sl_no=12;zoo_station[12].route_no=2;zoo_station[12].dist=9.2;
		
	}
	void login()
	{
		System.out.println("\t\t\tGUWAHATI CITY METRO");
		System.out.print("\n\n\t\t\tUsername: ");
		username=s.next();
		System.out.print("\n\t\t\tPassword: ");
		password=s.next();
		if((username.equals(user1)||username.equals(user2))&&password.equals(pass))
			System.out.println("Welcome "+username);
		else
		{
			System.out.println("Invalid username and/or password\n\nEXITTING SYSTEM");
			System.exit(0);
		}
	}
	void menu()
	{
		int menu_option;
		do
		{
			System.out.println("\t\t\tWELCOME TO GUWAHATI CITY METRO");
			System.out.println("\n\nPress:\n1. Distance between stations\n2. Fare Rates\n3. Get Ticket\n4. Register for Metro card\n5. Show list of metro card holders\n6. Help\n7. Logout");
			menu_option=s.nextInt();
			switch(menu_option)
			{
				case 1: double dist= m_dist();
						if(dist!=5)
							System.out.println("Distance is: "+dist+"km");
						System.out.println("Enter any key to go back to the menu: ");
						String st=s.next();	
						break;
				case 2: m_rate();
						break;
				case 3: m_ticket(); 
						break;
				case 4: MetroCard m1 = new MetroCard();
						m1.EnterDetails();
						break;
				case 5:	MetroCard m2=new MetroCard();
						m2.showAll();
						break;
				case 6: m_help(); 
						break;
				case 7: break;
				default: System.out.println("Invalid option");
			}
			clear();
		}while(menu_option!=7);
		System.out.println("Thank you for using GUWAHATI CITY METRO");
	}
	double m_dist()
	{
		System.out.println("\nSelect Route:\n1. GS Road\n2. Zoo Road");
		int menu_option,i,departure,destination;
		menu_option=s.nextInt();
		if(menu_option==1)
		{
			for(i=0;i<17;i++)
				System.out.println(gs_station[i].sl_no+". "+gs_station[i].name);
			System.out.println("\nEnter departure and destination station serial number from the list respectively: ");
			departure=s.nextInt();
			destination=s.nextInt();
			if(departure==destination)
				return 0;
			else if (departure<destination)
				return (gs_station[destination-1].dist-gs_station[departure-1].dist);
			else
				return (gs_station[departure-1].dist-gs_station[destination-1].dist);
		}
		else if(menu_option==2)
		{
			for(i=0;i<13;i++)
				System.out.println(zoo_station[i].sl_no+". "+zoo_station[i].name);
			System.out.println("\nEnter departure and destination station serial number from the list respectively: ");
			departure=s.nextInt();
			destination=s.nextInt();
			if(departure==destination)
				return 0;
			else if (departure<destination)
				return (zoo_station[destination-1].dist-zoo_station[departure-1].dist);
			else
				return (zoo_station[departure-1].dist-zoo_station[destination-1].dist);
		}
		else
			System.out.println("Invalid option");
		return 5;
	}
	void m_rate()
	{
		System.out.println("1. With metro card: 20 per km\n2. With token: 30 per km");
		System.out.println("Enter any key to go back to the menu: ");
		String st=s.next();
	}
	void m_ticket()
	{
		double dist= m_dist();
		if(dist==5)
			return;
		System.out.println("Distance is: "+dist+"km");
		System.out.println("\nGet token by:\n1. Cash\n2. Metro Card");
		int menu_option,option;;
		menu_option=s.nextInt();
		if(menu_option==1)
			System.out.println("Fare is Rs "+(30*dist)+"\n\nThank you for using Guwahati City Metro. Safe travel!");
		else if(menu_option==2)
		{
			MetroCard m1 = new MetroCard();
			m1.deduct(20*dist);
		}
		else
			System.out.println("Invalid option");
		System.out.println("Enter any key to go back to the menu: ");
		String st=s.next();
	}
	void m_help()
	{
		System.out.println("\t\t\tWELCOME TO GUWAHATI CITY METRO\n\nUsing this system we can \nview the different routes, \nfare rates and \ndistances between stations between stations \nand also buy tickets and\napply for metro card");
		System.out.println("\n\nAs you must have logged in using the username and password for this system, now you saw a menu with several options, each option lets you perform a specific task as according to the menu");
		System.out.println("Enter any key to go back to the menu: ");
		String st=s.next();
	}
	public static void main(String...st)
	{
		Main m = new Main();
		MetroCard m1 = new MetroCard();
		m.assign();
		m.login();
		clear();
		m.menu();
	}
 }