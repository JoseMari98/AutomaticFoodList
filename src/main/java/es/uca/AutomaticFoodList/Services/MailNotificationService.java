package es.uca.AutomaticFoodList.Services;

import com.vaadin.flow.component.notification.Notification;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailNotificationService {
    public MailNotificationService() {}
    public static void enviaEmail(String destino, String asunto, String cuerpo) {
        Properties props = new Properties();

        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.user", "anespiba98@gmail.com");
        props.setProperty("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);

        MimeMessage message = new MimeMessage(session);

        try{
            message.setFrom(new InternetAddress("anespiba98@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destino));
            message.setSubject(asunto);
            message.setText(cuerpo);
            Transport t = session.getTransport("smtp");
            t.connect("anespiba98@gmail.com", "apruebaiw2019");
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (Exception ex) {
            Notification.show("Ha ocurrido un error!");
        }
    }
}