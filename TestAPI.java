// import Core.*;


public class TestAPI {
    static API api;
    
    public static void main(String[] args) {

        api = new API();

        String email = "reuelsam@gmail.com";
        String password = "password10";
        api.login(email, password);

        MailAPI mailAPI = new MailAPI();    
        mailAPI.mail(email, "D-mail", "Supa Hacka");
        
        api.logout();
        api.closeConnection();
    }
}
