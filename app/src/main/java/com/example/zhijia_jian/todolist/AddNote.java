package com.example.zhijia_jian.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.util.Date;

public class AddNote extends AppCompatActivity {
    private EditText editText;
    private EditText texteditText;
    private Button addNoteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        addNoteButton = (Button)findViewById(R.id.buttonAdd);
        editText = (EditText) findViewById(R.id.titleET);
        texteditText = (EditText) findViewById(R.id.textET);
    }
    public void onAddButtonClick(View view) {
        //if(noteId==-1)
            addNote();
        //else
        //    editNote();
        Intent intent = new Intent();
        intent.setClass(AddNote.this , ToDoLists.class);
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
        //noteDao.insert(note);
        //Log.d("DaoExample", "Inserted new note, ID: " + note.getId());

    }
}
