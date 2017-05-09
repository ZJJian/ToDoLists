package com.example.zhijia_jian.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddNote extends AppCompatActivity {
    private EditText editText;
    private EditText texteditText;
    private Button addNoteButton;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        addNoteButton = (Button)findViewById(R.id.buttonAdd);
        editText = (EditText) findViewById(R.id.titleET);
        texteditText = (EditText) findViewById(R.id.textET);

        Bundle bun = this.getIntent().getExtras();
        token=bun.getString("token");
        Log.d("app", token);
    }
    public void onAddButtonClick(View view) {
        //if(noteId==-1)
            addNote();
        //else
        //    editNote();
        Intent intent = new Intent();
        intent.setClass(AddNote.this , ToDoLists.class);
        Bundle bun=new Bundle();
        bun.putString("token",token);
        intent.putExtras(bun);
        startActivity(intent);
    }

    private void addNote() {
        String noteText = editText.getText().toString();
        editText.setText("");
        String textnoteText = texteditText.getText().toString();
        texteditText.setText("");

        final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        String comment = "Added on " + df.format(new Date());

        Note note = new Note();
        note.setTitle(noteText);
        note.setComment(comment);
        note.setDate(new Date());
        note.setText(textnoteText);

        Gson gson = new Gson();
        //將Book物件轉成JSON
        String json = gson.toJson(note);
        Log.d("app",json);

        noteToServere(json,token);
        //noteDao.insert(note);
        //Log.d("DaoExample", "Inserted new note, ID: " + note.getId());

    }
    private void noteToServere(final String json, final String token)
    {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        final ExecutorService service = Executors.newFixedThreadPool(10);

        OkHttpClient.Builder b = new OkHttpClient.Builder();
        b.readTimeout(1000*30, TimeUnit.MILLISECONDS);
        b.writeTimeout(600, TimeUnit.MILLISECONDS);

        final OkHttpClient client = b.build();
        //final String name= nameET.getText().toString();
        //final String pass=pwET.getText().toString();
        service.execute(new Runnable() {
            @Override
            public void run() {
                RequestBody formBody = RequestBody.create(JSON, json);
                Request request = new Request.Builder()
                        .url("https://todolist-token.herokuapp.com/list")
                        .header("x-access-token",token)
                        .post(formBody)//
                        .build();
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //showclient.setText("註冊中...");
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

                            //showclient.setText(resStr);
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
