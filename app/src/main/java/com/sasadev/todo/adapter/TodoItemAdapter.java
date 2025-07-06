package com.sasadev.todo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sasadev.todo.R;
import com.sasadev.todo.models.TodoItem;

import java.util.ArrayList;

public class TodoItemAdapter extends RecyclerView.Adapter<TodoItemAdapter.TodoItemViewHolder> {

    private ArrayList<TodoItem> todoItemsList;

    public TodoItemAdapter(ArrayList<TodoItem> todoItemsList) {
        this.todoItemsList = todoItemsList;
    }

    @NonNull
    @Override
    public TodoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item,parent,false);
        return new TodoItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoItemViewHolder holder, int position) {
        TodoItem item = todoItemsList.get(position);
        holder.itemTitle.setText(item.getTitle());
        holder.itemDescription.setText(item.getDescription());
        holder.itemDate.setText(item.getDate());
        holder.itemTag.setText(item.getTag());
        holder.itemStatus.setChecked(item.isStatus());

        if(item.isStatus()){
            holder.itemTitle.setPaintFlags(holder.itemTitle.getPaintFlags() | android.graphics.Paint.STRIKE_THRU_TEXT_FLAG);
            holder.itemDescription.setPaintFlags(holder.itemDescription.getPaintFlags() | android.graphics.Paint.STRIKE_THRU_TEXT_FLAG);
        }

    }

    @Override
    public int getItemCount() {
        return todoItemsList.size();
    }

    static class TodoItemViewHolder extends RecyclerView.ViewHolder{

        TextView itemTitle;
        TextView itemDescription;
        TextView itemDate;
        TextView itemTag;
        CheckBox itemStatus;


        public TodoItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.todoItemTitleTextView);
            itemDescription = itemView.findViewById(R.id.todoItemDesTextView);
            itemDate = itemView.findViewById(R.id.todoItemDateTextView);
            itemTag = itemView.findViewById(R.id.todoItemTagTextView);
            itemStatus = itemView.findViewById(R.id.toDoItemCheckBox);

        }
    }

}
