package com.example.zhijia_jian.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ToDoLists extends AppCompatActivity {

    private NotesAdapter notesAdapter;
    private String token;
    String getJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_lists);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        setUpViews();
        Bundle bun = this.getIntent().getExtras();
        token=bun.getString("token");
        //updateNotes();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent();
                intent.setClass(ToDoLists.this , AddNote.class);
                Bundle bun=new Bundle();
                bun.putString("token",token);
                bun.putLong("note",-1);
                intent.putExtras(bun);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();

        setUpViews();
        updateNotes();

    }
    private void updateNotes() {
        //while(getJson==null)
        //getNotesFromServer();
        new MyTask().execute(" ");
//        AsyncTask task = new MyTask();
//        task.execute(" ");
//        task.cancel(true);

    }
    private void getNotesFromServer()
    {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        final ExecutorService service = Executors.newFixedThreadPool(10);
        //String getJson;
        OkHttpClient.Builder b = new OkHttpClient.Builder();
        b.readTimeout(1000*30, TimeUnit.MILLISECONDS);
        b.writeTimeout(600, TimeUnit.MILLISECONDS);

        final OkHttpClient client = b.build();
        //final String name= nameET.getText().toString();
        //final String pass=pwET.getText().toString();
        service.execute(new Runnable() {
            @Override
            public void run() {

                Request request = new Request.Builder()
                        .url("https://todolist-token.herokuapp.com/list")
                        .header("x-access-token",token)
                        .get()//
                        .build();
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //add text here
                        }
                    });

                    Log.d("app", "run: execute");
                    final Response response = client.newCall(request).execute();
                    final String resStr = response.body().string();
                    Log.d("app", "run: resStr: " + resStr);
                    getJson=resStr;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("app", "run: execute done");
                            showList();
                            //showclient.setText(resStr);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        });

        service.shutdown();
        //return getJson;
        Log.d("app", "getNotesFromServer: end");
    }
    private  void showList()
    {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Note>>(){}.getType();
        List<Note> notes = gson.fromJson(getJson,listType);
        notesAdapter.setNotes(notes);

    }
    protected void setUpViews() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewNotes);
        //noinspection ConstantConditions
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notesAdapter = new NotesAdapter(noteClickListener);
        recyclerView.setAdapter(notesAdapter);

    }
    class MyTask extends AsyncTask<String, String, String> {
        protected void onPreExecute(){
            // in main thread
        }

        protected String doInBackground(String... params){
            // in background thread
            //final ExecutorService service = Executors.newFixedThreadPool(10);
            //String getJson;
            OkHttpClient.Builder b = new OkHttpClient.Builder();
            b.readTimeout(1000*30, TimeUnit.MILLISECONDS);
            b.writeTimeout(600, TimeUnit.MILLISECONDS);

            final OkHttpClient client = b.build();
            Request request = new Request.Builder()
                    .url("https://todolist-token.herokuapp.com/list")
                    .header("x-access-token",token)
                    .get()//
                    .build();
            try {


                Log.d("app", "run: execute");
                final Response response = client.newCall(request).execute();
                final String resStr = response.body().string();
                Log.d("app", "run: resStr: " + resStr);
                getJson=resStr;
                return resStr;

            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }

        protected void onProgressUpdate(String... progress){
            // in main thread
        }

        protected void onPostExecute(String result){
            // in main thread
            showList();
        }

        protected void onCancelled(String result){
            // in main thread

        }

    }
    NotesAdapter.NoteClickListener noteClickListener = new NotesAdapter.NoteClickListener() {
        @Override
        public void onNoteClick(int position) {
            Note note = notesAdapter.getNote(position);

            getAlertDialog(note,"Edit or Delete this message").show();


        }
    };
    private AlertDialog getAlertDialog(Note note, String message){


        final Long noteId = note.getId();

//        //產生一個Builder物件
        AlertDialog.Builder builder = new AlertDialog.Builder(ToDoLists.this);
//        //設定Dialog的標題
        builder.setTitle(note.getTitle());
//        //設定Dialog的內容
        builder.setMessage(note.getText());
        //設定Positive按鈕資料
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //按下按鈕時顯示快顯

                //noteDao.deleteByKey(noteId);
                Log.d("DaoExample", "Deleted note, ID: " + noteId);

                //updateNotes();
                Toast.makeText(ToDoLists.this, "You clicked \"delete\"", Toast.LENGTH_SHORT).show();
            }
        });
        //設定Negative按鈕資料
        builder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent();
                intent.setClass(ToDoLists.this , AddNote.class);
                Bundle bun=new Bundle();
                bun.putLong("note",noteId);
                bun.putString("token",token);
                intent.putExtras(bun);

                startActivity(intent);
                //按下按鈕時顯示快顯
                Toast.makeText(ToDoLists.this, "You clicked \"Edit\"", Toast.LENGTH_SHORT).show();
            }
        });
        //利用Builder物件建立AlertDialog
        return builder.create();
    }

}
