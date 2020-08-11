package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editText=findViewById(R.id.editText);
        button=findViewById(R.id.button);

        getSupportActionBar().setTitle("Edit Item");
        editText.setText(getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT));
//        When the user is done editing, they click the save button
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
//                Create an intenet which will contain the results
                Intent intent=new Intent();
//                Pass the data (results of editing)
                intent.putExtra(MainActivity.KEY_ITEM_TEXT, editText.getText().toString());
                intent.putExtra(MainActivity.KEY_ITEM_POSITION, getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));
//                set the result of the intent
                setResult(RESULT_OK, intent);
//                Finish the activity, close the screen and go back
                finish();


            }
        });

    }
}