package ru.org.sevn.testmail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//a set of mail sollutions from internet
//https://www.google.com/settings/security/lesssecureapps
//to enable access
//unblock new device when the massage is sent at first
//doesnt work in the emulator
public class MainActivity extends AppCompatActivity {

    private static String user = "user_from";
    private static String password = "user_password";
    private static String user_from = "user_from@gmail.com";
    private static String user_to = "user_to@gmail.com";
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b1 = (Button)findViewById(R.id.button);
        Button b2 = (Button)findViewById(R.id.button2);
        Button b3 = (Button)findViewById(R.id.button3);
        Button b4 = (Button)findViewById(R.id.button4);
        tv = (TextView)findViewById(R.id.text);
        tv.setMovementMethod(new ScrollingMovementMethod());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(""+user);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_LONG).show();
                new SendStateTask(MainActivity.this).execute(null, null);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_LONG).show();
                new MailTask(MainActivity.this, user, password,
                        user_from,
                        user_to).execute("test message");
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_LONG).show();
                new SendJsseTask(MainActivity.this).execute();
            }
        });

    }

    static class SendJsseTask extends MyAsyncTask<Void, Void> {
        public SendJsseTask(MainActivity c) {
            super(c);
        }
        @Override
        protected Exception doInBackground(Void... params) {
            if (MailSender.isOnline(ctx)) {
                try {
                    GMailSender sender = new GMailSender(user,
                            password);
                    sender.sendMail("jsse test", "test message",
                            user_from,
                            user_to
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                    return e;
                }
            } else {
                Toast.makeText(ctx, "Not connected", Toast.LENGTH_LONG);
            }
            return null;
        }
    }

    static class SendStateTask extends MyAsyncTask<String, Void> {
        public SendStateTask(MainActivity c) {
            super(c);
        }
        @Override
        protected Exception doInBackground(String... params) {
            try {
                return sendState(params[0], params[1]);
            } catch (Exception e) {
                e.printStackTrace();
                return e;
            }
        }
        private Exception sendState(final String msg, final String locationInfoStr) {
            if (MailSender.isOnline(ctx)) {
                return MailSender.sendMail(user, password, MailSender.makeMailProperties(),
                        user_from, user_to, "test", "message text");
            } else {
                Toast.makeText(ctx, "Not connected", Toast.LENGTH_LONG);
            }
            return null;
        }
    }

}
