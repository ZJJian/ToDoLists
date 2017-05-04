package com.example.zhijia_jian.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Login extends AppCompatActivity {

    private Button sButton;
    private Button lButton;
    private EditText nameET;
    private EditText pwET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nameET=(EditText) findViewById(R.id.nameET);
        pwET=(EditText) findViewById(R.id.passwordET);
        lButton=(Button)findViewById(R.id.loginButton);
        sButton=(Button)findViewById(R.id.signupButton);
        sButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String name= nameET.getText().toString();
                String pass=pwET.getText().toString();
                Intent intent = new Intent();
                intent.setClass(Login.this , signUpPage.class);
                Bundle bun=new Bundle();
                bun.putString("name",name);
                bun.putString("password",pass);
                intent.putExtras(bun);
                startActivity(intent);

            }

        });
    }

}
