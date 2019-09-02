package com.github.machadowma.commentsapp;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

public class AddCommentActivity extends AppCompatActivity {
    public SQLiteDatabase bancoDados;
    public Button button;
    public EditText editText;
    public RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);

        button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editText);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrar();
            }
        });
    }

    public void cadastrar() {
        if (TextUtils.isEmpty(editText.getText().toString())) {
            editText.setError("Campo obrigatório");
        } else {
            try {
                bancoDados = openOrCreateDatabase("comment", MODE_PRIVATE, null);
                String sql = "INSERT INTO comment (comment,rating) VALUES (?,?)";
                SQLiteStatement stmt = bancoDados.compileStatement(sql);
                stmt.bindString(1, editText.getText().toString());
                stmt.bindDouble(2, ratingBar.getRating());
                stmt.executeInsert();
                bancoDados.close();
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
