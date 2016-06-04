package hu.bugbusters.corpus.core.mail;

import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

public class Mail {
    private static final String from = "corpus.message@gmail.com";
    private static final String password = "a1s2d3!%";
    private static final String host = "smtp.gmail.com";
    private static final int port = 465;

    private static Session getSession() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        return Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
    }

    private static void send(String senderName, InternetAddress[] addresses, String subject, String text, boolean containsHtml)
            throws MessagingException {
        MimeMessage message = new MimeMessage(getSession());
        message.setFrom(new InternetAddress(from));
        message.setSubject(subject);
        message.addRecipients(Message.RecipientType.TO, addresses);
        if(containsHtml){
            message.setText(text);
        } else {
            message.setContent(text,"text/html");
        }
        Transport.send(message);

        store(senderName, subject, text, containsHtml);
    }

    private static void store(String senderName, String subject, String text, boolean containsHtml) {
        Dao dao = new DaoImpl();

    }

    public static void sendMail(String senderName, String to, String subject, String text, boolean containsHtml)
            throws MessagingException {
        InternetAddress[] addresses = new InternetAddress[1];
        addresses[0] = new InternetAddress(to);

        send(senderName, addresses, subject, text, containsHtml);
    }

    public static void sendMail(String senderName, List<String> toList, String subject, String text, boolean containsHtml)
            throws MessagingException {
        InternetAddress[] addresses = new InternetAddress[toList.size()];
        for (int i = 0; i < toList.size(); i++) {
            addresses[i] = new InternetAddress(toList.get(i));
        }

        send(senderName, addresses, subject, text, containsHtml);
    }
}
