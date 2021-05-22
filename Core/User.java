package Core;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public abstract class User {
    String firstName, lastName, email;
    String password;
    String photo;
    private final String emailRegex = "[a-zA-Z0-9._+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+";
    private final String passwordRegex = "[a-zA-Z]+[0-9]+|[0-9]+[a-zA-Z]+";
    private final String nameRegex = "[A-Z][a-zA-Z]*";


    public User()
    {
        email = null;
        firstName = null;
        lastName = null;
        password = null;
        photo = "C:/Users/reuel/Desktop/College/Semester6/Subjects/Java - CS6308/Mini Project/Code/src/Images/avatar.png";
    }

    public User(String e, String fn, String ln, String p) throws Exception
    {
        if (!validate(e, emailRegex))
        {
            throw new Exception("Invalid Email");
        }
        if (!(validate(fn, nameRegex) && validate(ln, nameRegex)))
        {
            throw new Exception("Invalid Name. Must start contain only alphabets and start with Capital Letter.");
        }
        
        if (!validate(p, passwordRegex) || p.length() < 8)
        {
            throw new Exception("Invalid Password. Password must contain atleast 8 characters (including one letter and one digit).");
        }
        firstName = fn;
        lastName = ln;
        password = p;
        email = e;
        photo = "C:/Users/reuel/Desktop/College/Semester6/Subjects/Java - CS6308/Mini Project/Code/src/Images/avatar.png";
    }

    public User(String e, String fn, String ln, String p, String ph) throws Exception
    {
        if (!validate(e, emailRegex))
        {
            throw new Exception("Invalid Email");
        }
        if (!(validate(fn, nameRegex) && validate(ln, nameRegex)))
        {
            throw new Exception("Invalid Name. Must start contain only alphabets and start with Capital Letter.");
        }
        
        if (!validate(p, passwordRegex) || p.length() < 8)
        {
            throw new Exception("Invalid Password. Password must contain atleast 8 characters (including one letter and one digit).");
        }
        firstName = fn;
        lastName = ln;
        password = p;
        email = e;
        photo = ph;
    }

    public String getName()
    {
        return String.format("%s %s", firstName, lastName, password);
    }
    
    public void setFirstName(String fn)
    {
        firstName = fn;
    }
    public String getFirstName()
    {
        return firstName;
    }
    
    public void setLastName(String ln)
    {
        lastName = ln;
    }
    public String getLastName()
    {
        return lastName;
    }

    public void setEmail(String em)
    {
        email = em;
    }
    public String getEmail()
    {
        return email;
    }

    public void setPassword(String p)
    {
        password = p;
    }
    public String getPassword()
    {
        return password;
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
        photo = "C:/Users/reuel/Desktop/College/Semester6/Subjects/Java - CS6308/Mini Project/Code/src/Images/avatar.jpg";
        System.out.println(photo);
    }

    @Override
    public String toString() {
        return String.format("Name: %s %s\nEmail: %s\n", firstName, lastName, email);
    }

    private boolean validate(String str, String regex)
    {
        boolean valid = false;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find())
        {
            valid = true;
        }
        return valid;
    }

}
