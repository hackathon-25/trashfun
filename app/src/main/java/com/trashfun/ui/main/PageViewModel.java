package com.trashfun.ui.main;

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
import android.widget.EditText;

import com.trashfun.ChallengesActivity;
import com.trashfun.R;

public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private String tempURL;

    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            new LongRunningGetIO().execute();
            return "";
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
        tempURL = (mIndex.getValue() == 1) ? "https://jsonplaceholder.typicode.com/todos/1" :
                "https://trashfun.herokuapp.com/api/challenges";
    }

    public LiveData<String> getText() {
        return mText;
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
                ChallengesActivity.setResults(results);
            }
        }
    }
}