package ru.org.sevn.testmail;

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public abstract class MyAsyncTask<Params, Progress> extends AsyncTask<Params, Progress, Exception> {
    protected final MainActivity ctx;
    public MyAsyncTask(MainActivity c) {
        this.ctx = c;
    }

    @Override
    protected void onPostExecute(Exception result) {
        Toast.makeText(ctx, "Result: error=" + result, Toast.LENGTH_LONG).show();
        ctx.tv.setText("Result: error=" + result);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            if (result != null) {
                PrintStream ps = new PrintStream(baos, true, "utf-8");
                result.printStackTrace(ps);
                String content = new String(baos.toByteArray(), "utf-8");
                ctx.tv.setText(content);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
