package Core;
import java.util.*;

public class Resident extends User
{
    ArrayList<Room> listOfReservations = new ArrayList<Room>();
    
    public Resident()
    {
        super();
    }

    public Resident(String em, String fn, String ln, String pd) throws Exception
    {
        super(em, fn, ln, pd);
    }

    public Resident(String em, String fn, String ln, String pd, String ph) throws Exception
    {
        super(em, fn, ln, pd, ph);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
