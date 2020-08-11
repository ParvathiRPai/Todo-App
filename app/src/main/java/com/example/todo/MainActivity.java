package com.example.todo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.apache.commons.io.FileUtils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String KEY_ITEM_TEXT="item_text";
    public static final String KEY_ITEM_POSITION="item_position";
    public static final int EDIT_TEXT_CODE=20;
    List<String> items;
    Button btnadd;
    EditText eitem;
    RecyclerView rvitems;
    ItemsAdapter itemsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnadd=findViewById(R.id.btnadd);
        eitem=findViewById(R.id.eitem);
        rvitems=findViewById(R.id.rvitems);

       loadItems();
        ItemsAdapter.OnLongClickListener onLongClickListener=new ItemsAdapter.OnLongClickListener(){
            @Override
            public void onItemLongClicked(int position) {
//                Delete the item from the model
                items.remove(position);
//                Notify the adapter
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();
                saveItems();

            }
        };
        ItemsAdapter.onClickListener onClickListner=new ItemsAdapter.onClickListener() {
            @Override
            public void onItemClicked(int position) {
                Log.d("MainActivity", "Single click position" +position);
//                Create new activity
                Intent i = new Intent(MainActivity.this, EditActivity.class);
//                Pass the data being edited
                i.putExtra(KEY_ITEM_TEXT, items.get(position));
                i.putExtra(KEY_ITEM_POSITION, position);
//                Display the activity
                startActivityForResult(i, EDIT_TEXT_CODE);

            }
        };
        itemsAdapter=new ItemsAdapter(items, onLongClickListener, onClickListner);

        rvitems.setAdapter(itemsAdapter);
        rvitems.setLayoutManager(new LinearLayoutManager(this));
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String todoItem=eitem.getText().toString();
//                Add item to the model
                items.add(todoItem);
//                Notify the adapter that the item is added
                itemsAdapter.notifyItemInserted(items.size()-1);
                eitem.setText("");
                Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT).show();
                saveItems();

            }
        });

    }
//    Handle the result of the add activity
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode==RESULT_OK && requestCode==EDIT_TEXT_CODE){
//            Get the updated text value
            String itemtext=data.getStringExtra(KEY_ITEM_TEXT);
//            Extract the original position of the edited item from the key position
            int position=data.getExtras().getInt(KEY_ITEM_POSITION);
//            Update the model at the right position with new item text
            items.set(position, itemtext);
//            Notify the adapter
            itemsAdapter.notifyItemChanged(position);

//            Persist the changes
            saveItems();
            Toast.makeText(getApplicationContext(), "Item is updated successfully!", Toast.LENGTH_SHORT).show();
        }
        else{
            Log.w("MainActivity", "Unknown call to onActivityResult");
        }
    }

    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }
    private void loadItems(){
        try{
             items=new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        }
        catch (IOException e){
            Log.e("MainActivity", "Error reading items", e);
            items=new ArrayList<>();
        }
    }
    private void saveItems(){
        try{
            FileUtils.writeLines(getDataFile(), items);
        }catch(IOException e){
            Log.e("MainActivity", "Error writing items", e);
        }


    }
}