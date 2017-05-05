package com.example.zhijia_jian.todolist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.SortedList;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Login extends AppCompatActivity {

    private Button sButton;
    private Button lButton;
    private EditText nameET;
    private EditText pwET;
    private TextView showclient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nameET=(EditText) findViewById(R.id.nameET);
        pwET=(EditText) findViewById(R.id.passwordET);
        lButton=(Button)findViewById(R.id.loginButton);
        sButton=(Button)findViewById(R.id.signupButton);



        showclient =(TextView)findViewById(R.id.tv3);

        lButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                handleLoginButton();
            }
        });

        sButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSignupButton();
            }
        });



    }
    private void handleSignupButton()
    {
        final ExecutorService service = Executors.newFixedThreadPool(10);

        OkHttpClient.Builder b = new OkHttpClient.Builder();
        b.readTimeout(1000*20, TimeUnit.MILLISECONDS);
        b.writeTimeout(600, TimeUnit.MILLISECONDS);

        final OkHttpClient client = b.build();
        final String name= nameET.getText().toString();
        final String pass=pwET.getText().toString();
        service.execute(new Runnable() {
            @Override
            public void run() {
                RequestBody formBody = new FormBody.Builder()
                        .add("username", name)
                        .add("password", pass)
                        .build();
                Request request = new Request.Builder()
                        .url("https://todolist-token.herokuapp.com/user/register")
                        .post(formBody)
                        .build();
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showclient.setText("註冊中...");
                        }
                    });

                    Log.d("app", "run: execute");
                    final Response response = client.newCall(request).execute();
                    final String resStr = response.body().string();
                    Log.d("app", "run: resStr: " + resStr);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("app", "run: execute done");
                            showclient.setText(resStr);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        service.shutdown();

    }
    private void handleLoginButton()
    {
        final ExecutorService service = Executors.newFixedThreadPool(10);

        OkHttpClient.Builder b = new OkHttpClient.Builder();
        b.readTimeout(1000*20, TimeUnit.MILLISECONDS);
        b.writeTimeout(600, TimeUnit.MILLISECONDS);

        final OkHttpClient client = b.build();
        final String name= nameET.getText().toString();
        final String pass=pwET.getText().toString();
        service.execute(new Runnable() {
            @Override
            public void run() {
                RequestBody formBody = new FormBody.Builder()
                        .add("username", name)
                        .add("password", pass)
                        .build();
                Request request = new Request.Builder()
                        .url("https://todolist-token.herokuapp.com/user/login")
                        .post(formBody)
                        .build();
                try {
                    final Response response = client.newCall(request).execute();
                    final String resStr = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final String token=resStr.substring(resStr.indexOf(':')+2,resStr.length()-2);
                            showclient.setText(resStr+"\n"+token);

                            Intent intent = new Intent();
                            intent.setClass(Login.this , ToDoLists.class);
                            Bundle bun=new Bundle();
                            bun.putString("token",token);
                            intent.putExtras(bun);
                            startActivity(intent);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        service.shutdown();
    }


}
