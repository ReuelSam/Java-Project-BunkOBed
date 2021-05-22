package Core;
import java.util.*;

public class Host extends User{
    
    ArrayList<Room> listOfRooms = new ArrayList<Room>();
    private int age;

    public Host()
    {
        super();
        age = -1;
    }

    public Host(String em, String fn, String ln, String pd, int a) throws Exception
    {
        super(em, fn, ln, pd);
        if (a < 18)
        {
            throw new Exception("User is too young to be a Host.");
        }
        age = a;
    }

    public Host(String em, String fn, String ln, String pd, int a, String ph) throws Exception
    {
        super(em, fn, ln, pd, ph);
        if (a < 18)
        {
            throw new Exception("User is too young to be a Host.");
        }
        age = a;
    }


    public void setAge(int a)
    {
        age = a;
    }
    public int getAge()
    {
        return age;
    }

    public void addNewRoom(Room r)
    {
        listOfRooms.add(r);
    }

    public void removeRoom(Room r)
    {
        int index = listOfRooms.indexOf(r);
        listOfRooms.remove(index);
    }

    public Room getRoombyId(int id) throws Exception
    {
        boolean flag = false;
        Room retValue = new Room(email, null, 0, false, 0);
        for(Room rm: listOfRooms)
        {
            if (rm.id == id)
            {
                flag = true;
                retValue = rm;
                break;
            }
        }
        if (flag == false)
        {
            throw new Exception("No room with this ID");
        }
        return retValue;
    }

    public void listRooms()
    {
        for(Room rm: listOfRooms)
        {
            System.out.println("\n"+rm);
        }
    }

    @Override
    public String toString() {
        return String.format("%sAge: %d\n", super.toString(), age);
    }

}
