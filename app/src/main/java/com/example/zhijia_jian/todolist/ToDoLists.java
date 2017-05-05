package com.example.zhijia_jian.todolist;

import android.content.DialogInterface;
import android.content.Intent;
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

public class ToDoLists extends AppCompatActivity {

    private NotesAdapter notesAdapter;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_lists);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        setUpViews();
        Bundle bun = this.getIntent().getExtras();
        token=bun.getString("token");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent();
                intent.setClass(ToDoLists.this , AddNote.class);

                Bundle bun=new Bundle();
                bun.putLong("note",-1);
                intent.putExtras(bun);
                startActivity(intent);
            }
        });
    }
    protected void setUpViews() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewNotes);
        //noinspection ConstantConditions
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notesAdapter = new NotesAdapter(noteClickListener);
        recyclerView.setAdapter(notesAdapter);

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
//        builder.setTitle(note.getTitle());
//        //設定Dialog的內容
//        builder.setMessage(note.getText());
//        //設定Positive按鈕資料
//        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                //按下按鈕時顯示快顯
//
//                noteDao.deleteByKey(noteId);
//                Log.d("DaoExample", "Deleted note, ID: " + noteId);
//
//                updateNotes();
//                Toast.makeText(MainActivity.this, "You clicked \"delete\"", Toast.LENGTH_SHORT).show();
//            }
//        });
//        //設定Negative按鈕資料
//        builder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                Intent intent = new Intent();
//                intent.setClass(MainActivity.this , AddNote.class);
//                Bundle bun=new Bundle();
//                bun.putLong("note",noteId);
//                intent.putExtras(bun);
//
//                startActivity(intent);
//                //按下按鈕時顯示快顯
//                Toast.makeText(MainActivity.this, "You clicked \"Edit\"", Toast.LENGTH_SHORT).show();
//            }
//        });
//        //利用Builder物件建立AlertDialog
        return builder.create();
    }

}
