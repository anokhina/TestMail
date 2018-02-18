package ru.org.sevn.testmail;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

public class MailTask extends MyAsyncTask<String, Void> {

    private final String user;
    private final String password;
    private final String fromUser;
    private final String toUser;

    public MailTask(MainActivity ctx, String u, String p, String f, String t) {
        super(ctx);
        this.user = u;
        this.password = p;
        this.fromUser = f;
        this.toUser = t;
    }

    @Override
    protected Exception doInBackground(String... params) {
        try {
            Email email = new SimpleEmail();
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(587);
            email.setSslSmtpPort("465");
            email.setAuthenticator(new DefaultAuthenticator(user, password));
            email.setSSLOnConnect(true);
            email.setStartTLSEnabled(true);
            email.setFrom(fromUser);
            email.setSubject("apache test subj");
            email.setMsg(params[0]);
            email.addTo(toUser);
            email.send();
        } catch (Exception e) {
            e.printStackTrace();
            return e;
        }

        return null;
    }

}