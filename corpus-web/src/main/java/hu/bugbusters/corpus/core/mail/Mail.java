package hu.bugbusters.corpus.core.mail;

import hu.bugbusters.corpus.core.bean.RegisteredUser;
import hu.bugbusters.corpus.core.bean.Inbox;
import hu.bugbusters.corpus.core.dao.Dao;
import hu.bugbusters.corpus.core.dao.impl.DaoImpl;
import hu.bugbusters.corpus.core.exceptions.UserNotFoundException;
import hu.bugbusters.corpus.core.factories.MessageFactory;

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
    private static final Dao dao = DaoImpl.getInstance();
    
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

    private static void send(Long senderId, InternetAddress[] addresses, String subject, String message, boolean containsHtml)
            throws MessagingException, UserNotFoundException {

        RegisteredUser sender = dao.getUserById(senderId);

        /*MimeMessage msg = new MimeMessage(getSession());
        msg.setFrom(new InternetAddress(from));
        msg.setSubject(subject);
        msg.addRecipients(Message.RecipientType.TO, addresses);
        if(containsHtml){
            msg.setText(message);
        } else {
            msg.setContent(message,"text/html");
        }

        Transport.send(msg);*/

        store(sender, addresses, subject, message);
    }

    private static void store(RegisteredUser sender, InternetAddress[] addresses, String subject, String message) {
        // Store the message
        hu.bugbusters.corpus.core.bean.Message msg = MessageFactory.createAndSaveMessage(sender.getId(), subject, message);

        // Store for the sender as sentMail
        sender.getSentMails().add(msg);
        dao.updateEntity(sender);

        //Store for the receivers as receivedMail
        RegisteredUser receiver;
        for (InternetAddress ia : addresses){
            try {
                receiver = dao.getUserByEmail(ia.getAddress());
                Inbox inbox = new Inbox();
                inbox.setSeen('N');
                inbox.setRegisteredUser(receiver);
                inbox.setMessage(msg);
                receiver.getReceivedMails().add(inbox);
                dao.saveEntity(inbox);
            } catch (UserNotFoundException e) {
                System.out.print("Invalid email address: " + ia.getAddress());
            }
        }
    }

    public static void sendMail(Long senderId, String to, String subject, String message, boolean containsHtml)
            throws MessagingException, UserNotFoundException {
        InternetAddress[] addresses = new InternetAddress[1];
        addresses[0] = new InternetAddress(to);

        send(senderId, addresses, subject, message, containsHtml);
    }

    public static void sendMail(Long senderId, List<String> toList, String subject, String message, boolean containsHtml)
            throws MessagingException, UserNotFoundException {
        InternetAddress[] addresses = new InternetAddress[toList.size()];
        for (int i = 0; i < toList.size(); i++) {
            addresses[i] = new InternetAddress(toList.get(i));
        }

        send(senderId, addresses, subject, message, containsHtml);
    }
}
