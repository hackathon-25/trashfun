package com.trashfun;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.protocol.BasicHttpContext;
import cz.msebera.android.httpclient.protocol.HttpContext;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.trashfun.ChallengesActivity;
import com.trashfun.R;

public abstract class GetHTTPData extends AppCompatActivity {

    private String tempURL = "https://jsonplaceholder.typicode.com/todos/1";
    private TextView fillin = findViewById(R.id.fillinEnrolled);

    private class LongRunningGetIO extends AsyncTask <Void, Void, String> {
        protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {

            InputStream in;
            StringBuffer out = new StringBuffer();

            try {
                in = entity.getContent();

                int n = 1;
                while (n>0) {
                    byte[] b = new byte[4096];

                    n =  in.read(b);

                    if (n>0) out.append(new String(b, 0, n));

                }
            } catch (Exception e) {
                System.out.println("LongRunningGetIO failed.");
            }

            return out.toString();

        }

        @Override
        protected String doInBackground(Void... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpGet httpGet = new HttpGet(tempURL);
            String text = null;
            try {
                HttpResponse response = httpClient.execute(httpGet, localContext);
                HttpEntity entity = response.getEntity();
                text = getASCIIContentFromEntity(entity);
            } catch (Exception e) {
                return e.getLocalizedMessage();
            }
            return text;
        }

        protected void onPostExecute(String results) {
            if (results!=null) {

            }
        }
    }
}