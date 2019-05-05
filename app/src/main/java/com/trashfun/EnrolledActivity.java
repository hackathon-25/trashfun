package com.trashfun;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.trashfun.ui.main.PageViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.protocol.BasicHttpContext;
import cz.msebera.android.httpclient.protocol.HttpContext;

public class EnrolledActivity extends AppCompatActivity {

    private TextView fillin;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrolled);

        new LongRunningGetIO().execute();

        fillin = (TextView) findViewById(R.id.fillinEnrolled);
        fillin.setMovementMethod(new ScrollingMovementMethod());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPage = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intentPage);
            }
        });
    }

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
            HttpGet httpGet = new HttpGet("https://jsonplaceholder.typicode.com/todos/1");
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
                JSONArray res;

                try {
                    res = new JSONArray(results);

                    for (int i = 0; i < res.length(); ++i) {
                        JSONObject rec = res.getJSONObject(i);
                        String name = rec.getString("name");

                        fillin.setText(name);
                    }
                } catch (Exception e) {
                    System.out.println("fail");
                }
            }
        }
    }
}
