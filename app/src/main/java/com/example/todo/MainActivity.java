package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
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

        items=new ArrayList<>();
        items.add("Milk");
        items.add("cookie");

        ItemsAdapter.OnLongClickListener onLongClickListener=new ItemsAdapter.OnLongClickListener(){
            @Override
            public void onItemLongClicked(int position) {
//                Delete the item from the model
                items.remove(position);
//                Notify the adapter
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();

            }
        };
        itemsAdapter=new ItemsAdapter(items, onLongClickListener);

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

            }
        });


    }
}