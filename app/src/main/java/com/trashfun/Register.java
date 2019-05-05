package com.trashfun;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.trashfun.ui.login.LoginActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;

public class Register extends AppCompatActivity {

    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;
    private TextInputLayout textInputLayoutNumPeople;

    private TextInputEditText name;
    private TextInputEditText email;
    private TextInputEditText password;
    private TextInputEditText confirmpass;
    private TextInputEditText numPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        AppCompatTextView login = (AppCompatTextView) findViewById(R.id.login);
        AppCompatButton register = (AppCompatButton) findViewById(R.id.register_register);

        textInputLayoutName = (TextInputLayout) findViewById(R.id.textInputLayoutName);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        textInputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.textInputLayoutConfirmPassword);
        textInputLayoutNumPeople = (TextInputLayout) findViewById(R.id.textInputLayoutNumPeople);

        name = (TextInputEditText) findViewById(R.id.name);
        email = (TextInputEditText) findViewById(R.id.email);
        password = (TextInputEditText) findViewById(R.id.reg_pass);
        confirmpass = (TextInputEditText) findViewById(R.id.confirmpass);
        numPeople = (TextInputEditText) findViewById(R.id.num_people);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPage = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intentPage);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (password.getText().toString().equals(confirmpass.getText().toString()) && !password.getText().toString().isEmpty()) {
                    registerUser();

                    Snackbar.make(view, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
                    emptyInputEditText();
                } else {
                    Snackbar.make(view, getString(R.string.fail_message), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void emptyInputEditText() {
        name.setText(null);
        email.setText(null);
        password.setText(null);
        confirmpass.setText(null);
        numPeople.setText(null);
    }

    private void registerUser() {
        HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead
        try {
            HttpPost request = new HttpPost("https://trashfun.herokuapp.com/api/users");
            StringEntity params =new StringEntity("user={\"email\":\"" + email.getText().toString() + "\",\"password\":" + password.getText().toString() + "}");
            request.addHeader("content-type", "application/x-www-form-urlencoded");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
        } catch (Exception ex) {
            System.out.println("Failed.");
        } finally {
            //Deprecated
            //httpClient.getConnectionManager().shutdown();
        }

        /*
        try {
            URL url = new URL("https://trashfun.herokuapp.com/api/users");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            String jsonInputString = "{\"user\": {\"email\": " + email.getText().toString() + ", \"password\": " + password.getText().toString() + "}";

            OutputStream os = conn.getOutputStream();
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);

            /*
            int responsecode = conn.getResponseCode();
            StringBuilder inline = new StringBuilder();

            if(responsecode != 200)
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            else
            {
                Scanner sc = new Scanner(url.openStream());
                while(sc.hasNext())
                {
                    inline.append(sc.nextLine()) ;
                }
                System.out.println("\nJSON data in string format");
                System.out.println(inline.toString());
                sc.close();
            }
        } catch (Exception e) {
            System.out.println("NO URL");
        }
        */
    }
}
