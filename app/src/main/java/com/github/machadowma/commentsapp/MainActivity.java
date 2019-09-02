package com.github.machadowma.commentsapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public SQLiteDatabase bancoDados;
    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addComment();

            }
        });


        listView = (ListView) findViewById(R.id.listView);


        criarBancoDados();
        listarDados();
    }

    @Override
    protected void onResume() {
        super.onResume();
        listarDados();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addComment(){
        Intent intent = new Intent(this, AddCommentActivity.class);
        startActivity(intent);
    }

    public void criarBancoDados(){
        try {
            bancoDados = openOrCreateDatabase("comment", MODE_PRIVATE, null);
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS comment(" +
                    "   id INTEGER PRIMARY KEY AUTOINCREMENT" +
                    " , comment VARCHAR" +
                    " , rating INTEGER" +
                    " ) " );
            bancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listarDados() {
        try {
            bancoDados = openOrCreateDatabase("comment", MODE_PRIVATE, null);
            Cursor cursor = bancoDados.rawQuery("SELECT id,comment,rating FROM comment", null);
            ArrayList<Comment> commentsArray = new ArrayList<Comment>();
            CustomListAdapter customListAdapter = new CustomListAdapter(this, commentsArray);
            if(cursor.moveToFirst()) {
                do {
                    Comment comment = new Comment();
                    comment.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    comment.setComment(cursor.getString(cursor.getColumnIndex("comment")));
                    comment.setRating(cursor.getInt(cursor.getColumnIndex("rating")));
                    commentsArray.add(comment);
                } while (cursor.moveToNext());
            }
            listView.setAdapter(customListAdapter);
            bancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
