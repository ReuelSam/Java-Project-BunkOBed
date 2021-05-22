package Core;

import java.util.regex.Pattern;

public class Room {

    static int numRooms=0;
    int id, maxOccupancy;
    double price;
    String address;
    boolean availability;
    String ownerEmail;
    String photo;

    public Room()
    {
        this(null, null, 0, true, 0);
    }

    public Room(String em, String a, int m, boolean check, double p)
    {
        ownerEmail = em;
        maxOccupancy = m;
        address = a;
        availability = check;
        photo = "C:/Users/reuel/Desktop/College/Semester6/Subjects/Java - CS6308/Mini Project/Code/src/Images/room.jpg";
        price = p;
        numRooms++;
        id = -1;
    }

    public Room(int i, String em, String a, int m, boolean check, double p)
    {
        ownerEmail = em;
        maxOccupancy = m;
        address = a;
        availability = check;
        photo = "C:/Users/reuel/Desktop/College/Semester6/Subjects/Java - CS6308/Mini Project/Code/src/Images/room.jpg";
        price = p;
        numRooms++;
        id = i;
    }

    public Room(int i, String em, String a, int m, boolean check, double p, String ph)
    {
        ownerEmail = em;
        maxOccupancy = m;
        address = a;
        availability = check;
        photo = ph;
        price = p;
        numRooms++;
        id = i;
    }

    public String getOwnerEmail()
    {
        return ownerEmail;
    }

    public int getId()
    {
        return id;
    }

    public void setMaxOccupancy(int m)
    {
        maxOccupancy = m;
    }
    public int getMaxOccupancy()
    {
        return maxOccupancy;
    }

    public void setAddress(String a) 
    {
        address = a;    
    }
    public String getAddress()
    {
        return address;
    }

    public void setAvailability()
    {
        availability = !availability;
    }
    public boolean getAvailability()
    {
        return availability;
    }

    public void setPhoto(String s)
    {
        photo = "";
        //String[] arrOfStr = s.split("\\", 0);
        String separator = "\\";
        String[] arrOfStr=s.replaceAll(Pattern.quote(separator), "\\\\").split("\\\\");
        int i=0 ;
        for (String str: arrOfStr)
        {
            photo += str;
            if(++i != arrOfStr.length)
                photo += "/";
        }
        System.out.println(photo);
    }
    public String getPhoto()
    {
        return photo;
    }
    public void resetPhoto()
    {
        photo = "C:/Users/reuel/Desktop/College/Semester6/Subjects/Java - CS6308/Mini Project/Code/src/Images/room.jpg";
        System.out.println(photo);
    }

    public void setPrice(double m)
    {
        price = m;
    }
    public double getPrice()
    {
        return price;
    }

    @Override
    public String toString() {
        return String.format("Room ID: %d\nOwner: %s\nAddress: %s\nMaximum number of occupants: %d\nPrice: %f\nAvailability: %s\n", 
            id, ownerEmail, address, maxOccupancy, price, availability ? "Yes" : "No");
    }
}
