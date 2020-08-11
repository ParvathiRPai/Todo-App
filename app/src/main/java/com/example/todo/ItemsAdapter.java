package com.example.todo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//Responsibe for displaying data from the model into row in the recyler view
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    public interface onClickListener{
        void onItemClicked(int position);
    }
    public interface OnLongClickListener{
        void onItemLongClicked(int position);
    }
    List<String> items;
    OnLongClickListener longClickListener;
    onClickListener clickListener;
    public ItemsAdapter(List<String> items, OnLongClickListener longClickListner, onClickListener clickListener)
    {
         this.items=items;
         this.longClickListener=longClickListner;
         this.clickListener=clickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Use the layout inflator to inflate the view
        View todoView= LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
//        Wrap it inside the view holder and return it
        return new ViewHolder(todoView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Grab the item at position
        String item=items.get(position);
//        Bind the item to the view holder
        holder.bind(item);

    }
//Tells the number of items in the list
    @Override
    public int getItemCount() {
        return items.size();
    }

    //Container that provide easy access to views that represents each row
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvItem;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        tvItem=itemView.findViewById(android.R.id.text1);
    }

        public void bind(String item) {
        tvItem.setText(item);
        tvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClicked(getAdapterPosition());
            }
        });
        tvItem.setOnLongClickListener(new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View view) {
//               Notify the listner which position was long pressed.
                longClickListener.onItemLongClicked(getAdapterPosition());
                return true;
            }
        });
        }
    }

}
