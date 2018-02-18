package ru.org.sevn.testmail;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

//import javax.activation.DataHandler;
//import javax.activation.DataSource;
//import javax.activation.FileDataSource;

//https://code.google.com/archive/p/javamail-android/
public class MailSender {

    public static boolean isOnline(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public static Properties makeMailProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        //props.put("mail.smtp.port", "587");//587 //465

        props.put("mail.smtp.port", "587");
//        props.put("mail.smtp.socketFactory.port", "587");
//        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        props.put("mail.smtp.socketFactory.fallback", "false");

//        props.setProperty("mail.smtp.quitwait", "false");

        return props;
    }
    public static void sendEmail(final String emailHost, final String username, final String password, final Session mailSession, final Message emailMessage) throws AddressException, MessagingException {
        Transport transport = mailSession.getTransport("smtp");

        transport.connect(emailHost, username, password);
        transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
        transport.close();
        System.out.println("Email sent successfully.");
    }
    public static Exception sendMail(final String username, final String password, Properties smtpSettings, String mailFromAddress, String mailToAddress, String subj, String msgText) {

        Session session = Session.getDefaultInstance(smtpSettings, null);
//        Session session = Session.getInstance(smtpSettings,
//                new javax.mail.Authenticator() {
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(username, password);
//                    }
//                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailFromAddress));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailToAddress));
//            for (String mailToAddress: mailToAddresses) {
//                //InternetAddress.parse(mailToAddress)
//                message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailToAddress));
//            }
            message.setSubject(subj);
            message.setText(msgText);//.setContent(msgText, "text/html");//for a html email
/*
            MimeBodyPart messageBodyPart = new MimeBodyPart();

            Multipart multipart = new MimeMultipart();

            messageBodyPart = new MimeBodyPart();
            String file = "path of file to be attached";
            String fileName = "attachmentName";

            DataSource source = new FileDataSource(file);
            messageBodyPart.setDataHandler(new DataHandler(source));

            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);
            */

            sendEmail((String)smtpSettings.get("mail.smtp.host"), username, password, session, message);
            //Transport.send(message);

            System.out.println("Done");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error");
            return e;
        }
        return null;
    }
}