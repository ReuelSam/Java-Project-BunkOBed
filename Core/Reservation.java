package Core;

public class Reservation 
{
    int id, roomID;
    double cost, roomPrice;
    // Resident resident;
    // Host host;
    // Room room;
    // Date startDate;
    // Date endDate;

    String resident, owner, startDate, endDate;
    
    public Reservation()
    {
        this(-1, -1, null, null, "1000-01-01", "1000-01-01", -1);
    }

    public Reservation(int rID, String o, String r, String sD, String eD, double rp)
    {
        roomID = rID;
        owner = o;
        resident = r;
        startDate = sD;
        endDate = eD;
        roomPrice = rp;
        cost = calculateCost();
    }

    public Reservation(int i, int rID, String o, String r, String sD, String eD, double rp)
    {
        id = i;
        roomID = rID;
        owner = o;
        resident = r;
        startDate = sD;
        endDate = eD;
        roomPrice = rp;
        cost = calculateCost();
    }

    public Reservation(int i, int rID, String o, String r, String sD, String eD, double rp, double c)
    {
        id = i;
        roomID = rID;
        owner = o;
        resident = r;
        startDate = sD;
        endDate = eD;
        roomPrice = rp;
        cost = c;
    }

    public static double findCost(double rp, String sD, String eD)
    {
        int days = Date.getDifference(new Date(sD), new Date(eD));
        System.out.println(rp);
        System.out.println(days);
        System.out.println();
        return (rp * days);
    }

    public double calculateCost()
    {
        int days = Date.getDifference(new Date(startDate), new Date(endDate));
        return (roomPrice * days);
    }

    public void setId(int i)
    {
        id = i;
    }
    public int getId()
    {
        return id;
    }

    public void setResident(String r)
    {
        resident = r;
    }
    public String getResident()
    {
        return resident;
    } 

    public void setOwner(String h)
    {
        owner = h;
    }
    public String getOwner()
    {
        return owner;
    }

    public void setRoomID(int r)
    {
        roomID = r;
    }
    public int getRoomID()
    {
        return roomID;
    }

    public void setStartDate(int d, int m, int y)
    {
        startDate = new Date(d, m, y).toString();
        cost = calculateCost();
    }
    public String getStartDate()
    {
        return startDate;
    }

    public void setEndDate(int d, int m, int y)
    {
        endDate = new Date(d, m, y).toString();
        cost = calculateCost();
    }
    public String getEndDate()
    {
        return endDate;
    }

    public void setRoomPrice(double rp)
    {
        roomPrice = rp;
    }
    public double getRoomPrice()
    {
        return roomPrice;
    }

    public void setCost(double rp)
    {
        cost = rp;
    }
    public double getCost()
    {
        return cost;
    }



    @Override
    public String toString() {
        return String.format("Reservation ID: %d\nRoom ID: %d\nOwner: %s\nResident: %s\nFrom: %s To: %s\nCost: %.2f", id, roomID, owner, resident, startDate, endDate, cost);
    }
}
