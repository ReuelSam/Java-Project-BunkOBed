import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

final class MailAPI
{
    private class SMTPAuthenticator extends Authenticator
    {
        private PasswordAuthentication authentication;

        public SMTPAuthenticator(String login, String password)
        {
             authentication = new PasswordAuthentication(login, password);
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication()
        {
             return authentication;
        }
    }

    public int mail(String receiver, String subject, String body)
    {
            String from = "reuelsayf@gmail.com";
            String login = "reuelsayf@gmail.com";
            String password = "passwordchecker@1";

            Properties props = new Properties();
            props.setProperty("mail.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.auth", "true");
            props.setProperty("mail.smtp.starttls.enable", "true");

            Authenticator auth = new SMTPAuthenticator(login, password);

            Session session = Session.getInstance(props, auth);

            MimeMessage msg = new MimeMessage(session);

           try
           {
                msg.setText(body);
                msg.setSubject(subject);
                msg.setFrom(new InternetAddress(from));
                msg.addRecipient(Message.RecipientType.TO,
                new InternetAddress(receiver));
                Transport.send(msg);
                return 0;
           }
           catch (MessagingException ex)
           {
                Logger.getLogger(MailAPI.class.getName()).
                log(Level.SEVERE, null, ex);
                return -1;
           }
        }

}