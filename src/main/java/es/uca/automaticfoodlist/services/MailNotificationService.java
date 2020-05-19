package es.uca.automaticfoodlist.services;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailNotificationService {
    final static String correo = "automaticfoodlist@gmail.com";
    public MailNotificationService() {}
    public static void enviaEmail(String destino, String asunto, String cuerpo) throws MessagingException {
        Properties props = new Properties();

        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.user", correo);
        props.setProperty("mail.smtp.auth", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(correo, "JmEsITfG@1920#");
            }
        });

        MimeMessage message = new MimeMessage(session);

        try{
            message.setFrom(new InternetAddress(correo));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destino));
            message.setSubject(asunto);
            message.setText(cuerpo);
            Transport t = session.getTransport("smtp");
            t.connect(correo, "JmEsITfG@1920#");
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (Exception ex) {
            throw ex;
        }
    }
}