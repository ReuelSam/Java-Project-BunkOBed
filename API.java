import java.sql.*;
import Core.*;
import javafx.collections.FXCollections;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;

import javafx.collections.ObservableList;

/*
UPDATED
    newHost()
    getHostByEmail()

    newResident()
    getResidentByEmail()

    newRoom()
    getRoomById()
    updateRoom()

    makeReservation()
    getReservationById()



Methods:
    CORE:
        - void createConnection()
        - void closeConnection()
        - int login(String email, String password)          -> -1   => incorrect credentials
        - int logout()
        - String getLoggedInUserEmail()                     -> null => no logged in user
        - int getLoggedInUserType() -> return int
                                -1: no loggedin user
                                 1:  Host
                                 0: Resident
        - int updatePassword(String newPassword)            ->  -1 => error

    HOST:
        - int newHost(Host host)                            -> -1  => Error
        - Host getHostByEmail(String email)
        - ObservableList<Host> viewAllHosts()
        - int removeHost()                                  -> -1  => Error
        - int updateHost(Host host)
    
        
    RESIDENT:
        - int newResident(Resident resident)                -> -1   => Error
        - Resident getResidentByEmail(String email)         -> null => Error
        - ObservableList<Resident> viewAllResidents()
        - int removeResident()                              -> -1   => Error
        - int updateResident(Resident resident)


    ROOM:
        Common:
            - ObservableList<Room> viewRoomsByHostEmail(String email)
            - ObservableList<Room> viewAllRooms()
            - ObservableList<Room> viewBookedRooms()
            - ObservableList<Room> viewFreeRooms()
            - ObservableList<Room> viewRoomsByAddress(String key)
            - Room getRoomById(int roomID)                  -> null => Error
            - int getRoomIdByResID(int resID)               -> -1   => Error
    
        for HOST:
            - int newRoom(Room room)                        -> -1   => Error
            - int removeRoom(int roomID)                    -> -1   => Error
            - int updateRoom(room Room)                     -> -1   => Error

        for RESIDENT:
            - int checkRoomAvailable(int roomID)
                                                           -1: no room
                                                            1: available
                                                            0: not available
            - boolean checkRoomReserved(int roomID)                                                

    RESERVATION:
        Common:
            - ObservableList<Reservation> viewAllReservations()
            - int deleteReservation(int resID)              -> -1   => Error
            - Reservation getReservationByID(int resID)     -> null => Error

        for RESIDENT:
            - int makeReservation(int roomID, date startDate, date endDate)                     -> -1   => Error
            - int updateReservationByResident(int resID, date startDate, date endDate)          -> -1   => Error
            - boolean checkReservationByResidentEmail(int resID, String email)
            - ObservableList<Reservation> viewReservationsByResidentEmail(String email)

        for OWNER:
            - boolean checkReservationByOwnerEmail(int resID, String email)
            - ObservableList<Reservation> viewReservationsByOwnerEmail(String email)

            
*/

public class API
{
    Connection conn;
    Statement stmt;
    // static Host host;

    public API()
    {
        createConnection();
    }
    // public static void main(String[] args)
    // {
    //     System.out.println("\n\n~~~~~~~~~~~~~~~~~PROGRAM STARTS HERE~~~~~~~~~~~~~~~~~~~");
    //     createConnection();
        
    //     // // ~~~~~~~~~~~~~~~~~~~~~~~~~~~ New User ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //     // String fname = "Sayf";
    //     // String lname = "Zakir";
    //     // String email = "sayf@gmail.com";
    //     // String password = "password10";
    //     // int age = 20;
    //     // Host host = new Host();
    //     // try
    //     // {
    //     //     host = new Host(email, fname, lname, password, age);
    //     // }
    //     // catch (Exception e)
    //     // {
    //     //     System.out.println(e.getMessage());
    //     // }
    //     // newHost(host);
        

    //     // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ login ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    //     String email = "suryaa@gmail.com";
    //     String password = "password10";
    //     // String newPassword = "password";
    //     login(email, password);
 
    //     // System.out.println("\nAll hosts: ");
    //     // viewAllHosts();
    //     // System.out.println("\nAll residents: ");
    //     // viewAllResidents();
    //     // System.out.println("\nAll Rooms: ");
    //     // viewAllRooms();
    //     System.out.println("\nAll Reservations: ");
    //     viewAllReservations();

    //     // System.out.printf("Logged in user is of type: %s\n", getLoggedInUserType()==1 ? "Host" : "Resident");

    //     // updateRoom(16, "Villa #12, Palm Boulevard, California", 4, true);
    //     Core.Date startDate = new Core.Date(7,6,21);
    //     Core.Date endDate = new Core.Date(8,6,21);
    //     updateReservationByResident(13, startDate, endDate);

    //     // updatePassword(newPassword);
    //     // String emailKey = "reuelsam@gmail.com";
    //     // Host rHost = getHostByEmail(emailKey);
    //     // System.out.println("\nReturned Host: \n" + rHost);


    //     // //~~~~~~~~~~~~~~~~~~~~~~~~~~~ New Room ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //     // String address = "Villa #11, Palm Boulevard, California";
    //     // int occupancy = 3;
    //     // Room room = new Room(getLoggedInUserEmail(), address, occupancy, true);
    //     // newRoom(room);
        
    //     // removeRoom(14);

    //     // System.out.println("Rooms by " + rHost.getEmail() + " :");
    //     // viewRoomsByHostEmail(rHost);

    //     // removeHost();

    //     //~~~~~~~~~~~~~~~~~~~~~~~~~~~ Make Reservation ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //     // Core.Date startDate = new Core.Date(4,5,21);
    //     // Core.Date endDate = new Core.Date(6,5,21);
    //     // int reservationRoomID = 13;
    //     // makeReservation(reservationRoomID, startDate, endDate);
        
    //     // deleteReservation(12);

    //     // System.out.println("\nAll hosts: ");
    //     // viewAllHosts();
    //     // System.out.println("\nAll residents: ");
    //     // viewAllResidents();
    //     // System.out.println("\nAll Rooms: ");
    //     // viewAllRooms();
    //     System.out.println("\nAll Reservations: ");
    //     viewAllReservations();

    //     // int removeRoomID = 8;
    //     // removeRoom(rHost, removeRoomID);

    //     logout();
    //     closeConnection();
    // }


// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~CORE METHODS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public void createConnection()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_project", "root", "reuelsam");
            stmt = conn.createStatement();
            System.out.println("Connection Created.");
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public void closeConnection()
    {
        try
        {
            conn.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public int login(String email, String password)
    {
        System.out.println("Login entered");
        boolean flag = false;
        boolean hostCheck = false;
        try 
        {
            ResultSet rs = stmt.executeQuery(String.format("SELECT host from allusers where email = '%s' and password = '%s'", email, password));
            while(rs.next())
            {
                flag = true;
                hostCheck = rs.getBoolean(1);
                break;
            }
            if (flag == false)
            {
                System.out.printf("Incorrect Credentials\n");
                return -1;
            }
            else
            {
                stmt.executeUpdate(String.format("insert into loggedIn values('%s', %b)", email, hostCheck));
                System.out.println("Logged In");
                return 0;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public int logout()
    {
        try
        {
            stmt.executeUpdate(String.format("delete from loggedin"));
            System.out.println("Logged Out");
            return 0;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public String getLoggedInUserEmail()
    {
        boolean flag = false;
        String email = null;
        try 
        {
            ResultSet rs = stmt.executeQuery(String.format("SELECT email from loggedin"));
            while(rs.next())
            {
                email = rs.getString(1);
                flag = true;
                break;
            }
            if (flag == false)
            {
                System.out.printf("No user logged in\n");
                return null;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return email;
    }

    public int getLoggedInUserType()
    {
        boolean flag = false;
        int check = -1;
        try 
        {
            ResultSet rs = stmt.executeQuery(String.format("SELECT host from loggedin"));
            while(rs.next())
            {
                if(rs.getBoolean(1))
                    check = 1;
                else
                    check = 0;
                flag = true;
                break;
            }
            if (flag == false)
            {
                System.out.printf("No user logged in\n");
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return check;
    }

    private boolean validatePassword(String str)
    {
        boolean valid = false;
        String passwordRegex = "[a-zA-Z]+[0-9]+|[0-9]+[a-zA-Z]+";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find())
        {
            valid = true;
        }
        return valid;
    }

    public int updatePassword(String newPassword)
    {
        int userType = getLoggedInUserType();
        String email = getLoggedInUserEmail();
        if ((userType == -1) || (email == null) )
        {
            return -1;
        }
        boolean valid = validatePassword(newPassword);
        if (!valid)
        {
            return -1;
        }
        try
        {
            stmt.executeUpdate(String.format("update %s set password='%s' where email='%s'", userType == 1 ? "hosts" : "residents", newPassword, email));
            stmt.executeUpdate(String.format("update allusers set password='%s' where email='%s'", newPassword, email));
            System.out.println("Password Updated");
            return 0;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return -1;
    }

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~HOST METHODS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public int newHost(Host host)
    {
        try 
        {
            stmt.executeUpdate(String.format("insert into hosts(email, fname, lname, password, age, photo) values('%s', '%s', '%s', '%s', %d, '%s')", host.getEmail(), host.getFirstName(), host.getLastName(), host.getPassword(), host.getAge(), host.getPhoto()));
            stmt.executeUpdate(String.format("insert into allusers values('%s', '%s', '%s', '%s', true)", host.getEmail(), host.getFirstName(), host.getLastName(), host.getPassword()));
            
            System.out.println("New Host: " + host);
            return 0;
        }
        catch (SQLIntegrityConstraintViolationException e )
        {
            System.out.println(e.getMessage() + "\n");
            return -1;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return -1;
        }
    }  


    public Host getHostByEmail(String email)
    {
        boolean flag = false;
        Host retHost = new Host();
        try 
        {
            ResultSet rs = stmt.executeQuery(String.format("SELECT * from hosts where email = '%s'", email));
            while(rs.next())
            {
                // System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3)  + " " + rs.getString(4)  + " " + rs.getString(5));
                retHost = new Host(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), Integer.parseInt(rs.getString(5)));
                retHost.setPhoto(rs.getString(6));
                flag = true;
                break;
            }
            if (flag == false)
            {
                System.out.printf("No host with email: %s\n", email);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return retHost;
    }


    public ObservableList<Host> viewAllHosts()
    {
        ObservableList<Host> hosts = FXCollections.observableArrayList();
        try 
        {
            ResultSet rs = stmt.executeQuery("SELECT * from hosts");
            while(rs.next())
            {
                hosts.add(new Host(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), Integer.parseInt(rs.getString(5)), rs.getString(6) ));
                // System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return hosts;
    }

    public int removeHost()
    {
        String email = getLoggedInUserEmail();
        if (email == null)
            return -1;
        Host host = getHostByEmail(email);
        try
        {
            int res1 = stmt.executeUpdate(String.format("delete from hosts where email='%s'", host.getEmail()));
            if (res1 == 1)
            {
                System.out.println("Host Deleted");
                stmt.executeUpdate(String.format("delete from reservations where owner='%s'", host.getEmail()));
                stmt.executeUpdate(String.format("delete from allusers where email='%s'", host.getEmail()));
                stmt.executeUpdate(String.format("delete from rooms where owner='%s'", host.getEmail()));
                return 0;
            }   
            else
                System.out.printf("No such host to be deleted");
                return -1;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public int updateHost(Host host)
    {
        String email = getLoggedInUserEmail();
        if (email == null)
            return -1;
        // Host host = getHostByEmail(email);
        try
        {
            int res = stmt.executeUpdate(String.format("update hosts set fname='%s', lname='%s', password='%s', age=%d, photo='%s' where email='%s'", host.getFirstName(), host.getLastName(), host.getPassword(), host.getAge(), host.getPhoto(), host.getEmail()));
            if (res == 1)
            {
                stmt.executeUpdate(String.format("update allusers set fname='%s', lname='%s', password='%s' where email='%s'",  host.getFirstName(), host.getLastName(), host.getPassword(), host.getEmail()));
                System.out.println("Host Updated");
                return 0;
            }
            else
            {
                System.out.printf("This Room ID does not belong to Host '%s %s'", host.getFirstName(), host.getLastName());
                return -1;
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            return -1;
        }
    }

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~RESIDENT METHODS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public int newResident(Resident resident)
    {
        try 
        {
            stmt.executeUpdate(String.format("insert into residents(email, fname, lname, password, photo) values('%s', '%s', '%s', '%s', '%s')", resident.getEmail(), resident.getFirstName(), resident.getLastName(), resident.getPassword(), resident.getPhoto()));
            stmt.executeUpdate(String.format("insert into allusers values('%s', '%s', '%s', '%s', false)", resident.getEmail(), resident.getFirstName(), resident.getLastName(), resident.getPassword()));
            System.out.println("New resident: " + resident);
            return 0;
        }
        catch (SQLIntegrityConstraintViolationException e )
        {
            System.out.println(e.getMessage() + "\n");
            return -1;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return -1;
        }
    }  

    public Resident getResidentByEmail(String email)
    {
        boolean flag = false;
        Resident retResident = new Resident();
        try 
        {
            ResultSet rs = stmt.executeQuery(String.format("SELECT * from residents where email = '%s'", email));
            while(rs.next())
            {
                // System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3)  + " " + rs.getString(4));
                retResident = new Resident(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
                retResident.setPhoto(rs.getString(5));
                flag = true;
                break;
            }
            if (flag == false)
            {
                System.out.printf("No Resident with email: %s\n", email);
                return null;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return retResident;
    }


    public ObservableList<Resident> viewAllResidents()
    {
        ObservableList<Resident> residents = FXCollections.observableArrayList();
        try 
        {
            ResultSet rs = stmt.executeQuery("SELECT * from residents");
            while(rs.next())
            {
                residents.add(new Resident(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
                // System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return residents;
    }

    public int removeResident()
    {
        ArrayList<Integer> roomIDs = new ArrayList<Integer>();
        String email = getLoggedInUserEmail();
        if (email == null)
            return -1;
        Resident resident = getResidentByEmail(email);
        try
        {
            int res1 = stmt.executeUpdate(String.format("delete from residents where email='%s'", resident.getEmail()));
            if (res1 == 1)
            {
                System.out.println("Resident Deleted");
                stmt.executeUpdate(String.format("delete from allusers where email='%s'", resident.getEmail()));
                ResultSet rs = stmt.executeQuery(String.format("SELECT roomID from reservations where resident='%s'", resident.getEmail()));
                while(rs.next())
                {
                    roomIDs.add(Integer.parseInt(rs.getString(1)));
                }
                for (int i: roomIDs)
                {
                    stmt.executeUpdate("update rooms set availability=true where id=" + i);
                }
                stmt.executeUpdate(String.format("delete from reservations where resident='%s'", resident.getEmail()));
                
                return 0;
            }   
            else
            {
                System.out.printf("No such Resident to be deleted");
                return -1;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public int updateResident(Resident resident)
    {
        String email = getLoggedInUserEmail();
        if (email == null)
            return -1;
        // Host host = getHostByEmail(email);
        try
        {
            int res = stmt.executeUpdate(String.format("update residents set fname='%s', lname='%s', password='%s', photo='%s' where email='%s'", resident.getFirstName(), resident.getLastName(), resident.getPassword(), resident.getPhoto(), resident.getEmail()));
            if (res == 1)
            {
                stmt.executeUpdate(String.format("update allusers set fname='%s', lname='%s', password='%s' where email='%s'",  resident.getFirstName(), resident.getLastName(), resident.getPassword(), resident.getEmail()));
                System.out.println("Resident Updated");
                return 0;
            }
            else
            {
                System.out.printf("This Room ID does not belong to Resident '%s %s'", resident.getFirstName(), resident.getLastName());
                return -1;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return -1;
        }
    }

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ROOM METHODS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public int newRoom(Room room)
    {
        String email = getLoggedInUserEmail();
        if (email == null)
            return -1;
        Host host = getHostByEmail(email);
        try
        {
            stmt.executeUpdate(String.format("insert into rooms(owner, address, maxOccupancy, availability, price, photo) values('%s', '%s', %d, %b, %f, '%s')", host.getEmail(), room.getAddress(), room.getMaxOccupancy(), room.getAvailability(), room.getPrice(), room.getPhoto()));
            return 0;
            // System.out.println(room);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return -1;
        }
    }


    public Room getRoomById(int roomID)
    {
        boolean flag = false;
        Room room = new Room();
        try 
        {
            ResultSet rs = stmt.executeQuery(String.format("SELECT * from rooms where id = %d", roomID));
            while(rs.next())
            {
                // System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3)  + " " + rs.getString(4));
                room = new Room(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3), Integer.parseInt(rs.getString(4)), rs.getBoolean(5), Double.parseDouble(rs.getString(6)));
                room.setPhoto(rs.getString(7));

                flag = true;
                break;
            }
            if (flag == false)
            {
                System.out.printf("No Room with ID: %s\n", roomID);
                return null;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return room;
    }

    public int getRoomIdbyResID(int resID)
    {
        boolean flag = false;
        int roomID = -1;
        try 
        {
            ResultSet rs = stmt.executeQuery(String.format("SELECT roomID from reservations where id = %d", resID));
            while(rs.next())
            {
                // System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3)  + " " + rs.getString(4));
                roomID = Integer.parseInt(rs.getString(1));
                flag = true;
                break;
            }
            if (flag == false)
            {
                System.out.printf("No Reservation with ID: %s\n", roomID);
                return -1;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return roomID;
    }

    public int removeRoom(int roomID)
    {
        String email = getLoggedInUserEmail();
        if (email == null)
            return -1;
        Host host = getHostByEmail(email);
        try
        {
            int res = stmt.executeUpdate(String.format("delete from rooms where id=%d and owner='%s'", roomID, host.getEmail()));
            if (res == 1)
            {

                stmt.executeUpdate(String.format("delete from reservations where id=%d and owner='%s'", roomID, host.getEmail()));
                System.out.println("Room Deleted");
                return 0;
            }
            else
            {
                System.out.printf("This Room ID does not belong to Host '%s %s'", host.getFirstName(), host.getLastName());
                return -1;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public int updateRoom(Room room)
    {
        String email = getLoggedInUserEmail();
        if (email == null)
            return -1;
        Host host = getHostByEmail(email);
        try
        {
            int res = stmt.executeUpdate(String.format("update rooms set address='%s', maxOccupancy=%d, availability=%b, price=%f, photo='%s' where id=%d and owner='%s'", room.getAddress(), room.getMaxOccupancy(), room.getAvailability(), room.getPrice(), room.getPhoto(), room.getId(), room.getOwnerEmail()));
            if (res == 1)
            {
                stmt.executeUpdate(String.format("update reservations set roomPrice=%b where roomID=%d", room.getPrice(), room.getId()));
                System.out.println("Room Updated");
                return 0;
            }
            else
            {
                System.out.printf("This Room ID does not belong to Host '%s %s'", host.getFirstName(), host.getLastName());
                return -1;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public ObservableList<Room> viewRoomsByHostEmail(String email)
    {
        ObservableList<Room> rooms = FXCollections.observableArrayList();
        try 
        {
            ResultSet rs = stmt.executeQuery(String.format("SELECT * from rooms where owner = '%s'", email));
            while(rs.next())
            {
                rooms.add(new Room(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3), Integer.parseInt(rs.getString(4)), rs.getBoolean(5), Double.parseDouble(rs.getString(6)), rs.getString(7)));

            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return rooms;
    }

    public ObservableList<Room> viewAllRooms()
    {
        ObservableList<Room> rooms = FXCollections.observableArrayList();
        try 
        {
            ResultSet rs = stmt.executeQuery(String.format("SELECT * from rooms"));
            while(rs.next())
            {
                rooms.add(new Room(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3), Integer.parseInt(rs.getString(4)), rs.getBoolean(5), Double.parseDouble(rs.getString(6)), rs.getString(7)));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return rooms;
    }

    public ObservableList<Room> viewRoomsByAddress(String key, int type)
    {
        ObservableList<Room> rooms = FXCollections.observableArrayList();
        ResultSet rs = null;
        try 
        {
            if (type == 0)
                rs = stmt.executeQuery(String.format("SELECT * from rooms"));
            else if (type == 1)
                rs = stmt.executeQuery(String.format("SELECT * from rooms where availability=true"));
            else if (type == 2)
                rs = stmt.executeQuery(String.format("SELECT * from rooms where availability=false"));


            while(rs.next())
            {
                String address = rs.getString(3);
                Pattern pattern = Pattern.compile(key);
                Matcher matcher = pattern.matcher(address);
                if (matcher.find())
                {
                    rooms.add(new Room(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3), Integer.parseInt(rs.getString(4)), rs.getBoolean(5), Double.parseDouble(rs.getString(6)), rs.getString(7)));
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return rooms;
    }

    public ObservableList<Room> viewBookedRooms()
    {
        ObservableList<Room> rooms = FXCollections.observableArrayList();

        try 
        {
            ResultSet rs = stmt.executeQuery(String.format("SELECT * from rooms where availability=false"));
            while(rs.next())
            {
                rooms.add(new Room(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3), Integer.parseInt(rs.getString(4)), rs.getBoolean(5), Double.parseDouble(rs.getString(6)), rs.getString(7)));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return rooms;
    }

    public ObservableList<Room> viewFreeRooms()
    {
        ObservableList<Room> rooms = FXCollections.observableArrayList();
        try 
        {
            ResultSet rs = stmt.executeQuery(String.format("SELECT * from rooms where availability=true"));
            while(rs.next())
            {
                rooms.add(new Room(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3), Integer.parseInt(rs.getString(4)), rs.getBoolean(5), Double.parseDouble(rs.getString(6)), rs.getString(7)));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return rooms;
    }

    public int checkRoomAvailable(int roomID)
    {
        boolean flag = false;
        int check = -1;
        try 
        {
            ResultSet rs = stmt.executeQuery(String.format("SELECT availability from rooms where id = %d", roomID));
            while(rs.next())
            {
                if(rs.getBoolean(1))
                    check = 1;
                else
                    check = 0;
                flag = true;
                break;
            }
            if (flag == false)
            {
                System.out.printf("No room with this ID\n");
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return check;
    }

    public boolean checkRoomReserved(int roomID)
    {
        boolean flag = false;
        try 
        {
            ResultSet rs = stmt.executeQuery(String.format("SELECT * from reservations where roomID = %d", roomID));
            while(rs.next())
            {
                flag = true;
                break;
            }
            if (flag == false)
            {
                System.out.printf("Room is not reserved\n");
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return flag;
    }

// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~RESERVATION METHODS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public int makeReservation(int roomID, Core.Date startDate, Core.Date endDate)
    {
        int check = checkRoomAvailable(roomID);
        if (check != 1)
        {
            return -1;
        }
        String email = getLoggedInUserEmail();
        if (email == null)
        {
            return -1;
        }
        Room room = getRoomById(roomID);
        System.out.println(roomID);
        Reservation res = new Reservation(roomID, room.getOwnerEmail(), email, startDate.toString(), endDate.toString(), room.getPrice());
        try
        {
            stmt.executeUpdate(String.format("insert into reservations(roomID, owner, resident, startDate, endDate, roomPrice, cost) values(%d, '%s', '%s', '%s', '%s', %f, %f)", roomID, room.getOwnerEmail(), email, startDate, endDate, res.getRoomPrice(), res.getCost()));
            stmt.executeUpdate("update rooms set availability=false where id=" + roomID);
            System.out.println("\nReservation Made");
            return 0;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return -1;
        }
    }

    public boolean checkReservationByResidentEmail(int resID, String email)
    {
        boolean check = false;
        try 
        {
            ResultSet rs = stmt.executeQuery(String.format("SELECT * from reservations where id = %d and resident = '%s'", resID, email));
            while(rs.next())
            {
                if(rs.getBoolean(1))
                    check = true;
                else
                    check = false;
                break;
            }
            if (check == false)
            {
                System.out.printf("No reservation with this ID: %d and resident: %s\n", resID, email);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return check;
    }

    public boolean checkReservationByOwnerEmail(int resID, String email)
    {
        boolean check = false;
        try 
        {
            ResultSet rs = stmt.executeQuery(String.format("SELECT * from reservations where id = %d and owner = '%s'", resID, email));
            while(rs.next())
            {
                if(rs.getBoolean(1))
                    check = true;
                else
                    check = false;
                break;
            }
            if (check == false)
            {
                System.out.printf("No reservation with this ID: %d and owner: %s\n", resID, email);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return check;
    }

    public int deleteReservation(int resID)
    {
        String email = getLoggedInUserEmail();
        if (email == null)
        {
            return -1;
        }
        boolean checkResident = checkReservationByResidentEmail(resID, email);
        boolean checkOwner = checkReservationByOwnerEmail(resID, email);
        if ((!checkResident && !checkOwner))
        {
            return -1;
        }
        int roomID = getRoomIdbyResID(resID);
        System.out.println(roomID);
        try
        {
            stmt.executeUpdate(String.format("delete from reservations where id=%d", resID));
            stmt.executeUpdate("update rooms set availability=true where id=" + roomID);

            System.out.println("\nReservation Deleted");
            return 0;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public ObservableList<Reservation> viewAllReservations()
    {
        ObservableList<Reservation> reservations = FXCollections.observableArrayList();

        try 
        {
            ResultSet rs = stmt.executeQuery("SELECT * from reservations");
            while(rs.next())
            {
                reservations.add(new Reservation(Integer.parseInt(rs.getString(1)), Integer.parseInt(rs.getString(2)), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), Double.parseDouble(rs.getString(7)), Double.parseDouble(rs.getString(8))));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return reservations;
    }

    public ObservableList<Reservation> viewReservationsByResidentEmail(String email)
    {
        ObservableList<Reservation> reservations = FXCollections.observableArrayList();
        try 
        {
            ResultSet rs = stmt.executeQuery(String.format("SELECT * from reservations where resident='%s'", email));
            while(rs.next())
            {
                reservations.add(new Reservation(Integer.parseInt(rs.getString(1)), Integer.parseInt(rs.getString(2)), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), Double.parseDouble(rs.getString(7)), Double.parseDouble(rs.getString(8))));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return reservations;
    }

    public ObservableList<Reservation> viewReservationsByOwnerEmail(String email)
    {
        ObservableList<Reservation> reservations = FXCollections.observableArrayList();
        try 
        {
            ResultSet rs = stmt.executeQuery(String.format("SELECT * from reservations where owner='%s'", email));
            while(rs.next())
            {
                reservations.add(new Reservation(Integer.parseInt(rs.getString(1)), Integer.parseInt(rs.getString(2)), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), Double.parseDouble(rs.getString(7)), Double.parseDouble(rs.getString(8))));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return reservations;
    }

    public Reservation getReservationById(int resID)
    {
        System.out.println("resID"+resID);
        String email = getLoggedInUserEmail();
        if (email == null)
        {
            return null;
        }
        boolean checkResident = checkReservationByResidentEmail(resID, email);
        boolean checkOwner = checkReservationByOwnerEmail(resID, email);
        if ((!checkResident && !checkOwner))
        {
            return null;
        }
        boolean flag = false;
        System.out.printf("SELECT * from reservations where id=%d\n",resID);
        Reservation res = new Reservation();
        try 
        {
            ResultSet rs = stmt.executeQuery(String.format("SELECT * from reservations where id=%d",resID));
            while(rs.next())
            {
                // System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3)  + " " + rs.getString(4)  + " " + rs.getString(5) + " " + rs.getString(6) + " " + rs.getString(7));
                res = new Reservation(Integer.parseInt(rs.getString(1)), Integer.parseInt(rs.getString(2)), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), Double.parseDouble(rs.getString(7)));
                // System.out.println(res);
                flag = true;
                return res;
            }
            if (flag == false)
            {
                System.out.printf("No reservation with ID: %d\n", resID);
                return null;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
        return res;
    }

    public Reservation getReservationByRoomId(int roomID)
    {
        String email = getLoggedInUserEmail();
        if (email == null)
        {
            return null;
        }
        // boolean checkResident = checkReservationByResidentEmail(resID, email);
        // boolean checkOwner = checkReservationByOwnerEmail(resID, email);
        // if ((!checkResident && !checkOwner))
        // {
        //     return null;
        // }
        boolean flag = false;
        // System.out.printf("SELECT * from reservations where id=%d\n",resID);
        Reservation res = new Reservation();
        try 
        {
            ResultSet rs = stmt.executeQuery(String.format("SELECT * from reservations where roomID=%d",roomID));
            while(rs.next())
            {
                // System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3)  + " " + rs.getString(4)  + " " + rs.getString(5) + " " + rs.getString(6) + " " + rs.getString(7));
                res = new Reservation(Integer.parseInt(rs.getString(1)), Integer.parseInt(rs.getString(2)), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), Double.parseDouble(rs.getString(7)));
                // System.out.println(res);
                flag = true;
                return res;
            }
            if (flag == false)
            {
                System.out.printf("No reservation for room: %d\n", roomID);
                return null;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
        return res;
    }

    public int updateReservationByResident(int resID, String startDate, String endDate, double cost)
    {
        String email = getLoggedInUserEmail();
        if (email == null)
        {
            return -1;
        }

        try
        {
            stmt.executeUpdate(String.format("update reservations set startDate = '%s', endDate = '%s', cost=%f where id=%d and resident='%s'", startDate, endDate, cost, resID, email));
            System.out.println("\nReservation Updated");
            return 0;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return -1;
        }
    }
}

