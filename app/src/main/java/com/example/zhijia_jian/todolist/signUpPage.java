package com.example.zhijia_jian.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class signUpPage extends AppCompatActivity {
    private Button sButton;
    private EditText nameET;
    private EditText pwET;
    private EditText mailET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        nameET=(EditText) findViewById(R.id.nameET);
        pwET=(EditText) findViewById(R.id.passwordET);
        mailET=(EditText)findViewById(R.id.emailET);
        sButton=(Button)findViewById(R.id.signupButton);

        Bundle bun = this.getIntent().getExtras();
        if(bun.getString("name")!="")
            nameET.setText(bun.getString("name"));
        if(bun.getString("password")!="")
            pwET.setText(bun.getString("password"));
    }
}
