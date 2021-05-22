package Core;

public class Date {
    int day, month, year;
    static int monthDays[] = {31, 28, 31, 30, 31, 30,
        31, 31, 30, 31, 30, 31};

    public Date()
    {
        day = 0;
        month = 0;
        year = 0;
    }

    public Date(int d, int m, int y)
    {
        day = d;
        month = m;
        year = y;
    }

    public Date(String date)
    {
        int dateArr[] = new int[3];
        int i = 0;
        for(String s: date.split("-",3))
        {
            dateArr[i] = Integer.parseInt(s);
            // System.out.println(dateArr[i]);
            i++;
        }
        this.day = dateArr[2];
        this.month = dateArr[1];
        this.year = dateArr[0];
    }

    public void setDay(int i)
    {
        day = i;
    }
    public int getDay()
    {
        return day;
    }

    public void setMonth(int i)
    {
        month = i;
    }
    public int getMonth()
    {
        return month;
    }

    public void setYear(int i)
    {
        year = i;
    }
    public int getYear()
    {
        return year;
    }

    static int countLeapYears(Date d)
    {
        int years = d.getYear();
        if (d.getMonth() <= 2)
        {
            years--;
        }
 
        return years / 4 - years / 100 + years / 400;
    }
 
    static int getDifference(Date dt1, Date dt2)
    {
        int n1 = dt1.getYear() * 365 + dt1.getDay();
 
        for (int i = 0; i < dt1.getMonth() - 1; i++)
        {
            n1 += monthDays[i];
        }
 
        n1 += countLeapYears(dt1);
 
        int n2 = dt2.getYear() * 365 + dt2.getDay();
        for (int i = 0; i < dt2.getMonth() - 1; i++)
        {
            n2 += monthDays[i];
        }
        n2 += countLeapYears(dt2);
 
        return (n2 - n1);
    }

    @Override
    public String toString() {
        return String.format("%d-%02d-%02d", year, month, day);
    }
}
