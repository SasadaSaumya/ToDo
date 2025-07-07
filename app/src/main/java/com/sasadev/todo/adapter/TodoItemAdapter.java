package com.sasadev.todo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sasadev.todo.R;
import com.sasadev.todo.models.TodoItem;

import java.util.ArrayList;

import io.realm.Realm;

public class TodoItemAdapter extends RecyclerView.Adapter<TodoItemAdapter.TodoItemViewHolder> {

    private ArrayList<TodoItem> todoItemsList;
    private Realm realm;

    public TodoItemAdapter(ArrayList<TodoItem> todoItemsList) {
        this.todoItemsList = todoItemsList;
        this.realm = Realm.getDefaultInstance();
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

        strikeThruLineUpdate(holder.itemTitle,holder.itemStatus.isChecked());
        strikeThruLineUpdate(holder.itemDescription,holder.itemStatus.isChecked());

        holder.itemStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        TodoItem resultItem = realm.where(TodoItem.class).equalTo("id",item.getId()).findFirst();

                        if(resultItem != null){
                            resultItem.setStatus(isChecked);
                        }
                    }
                });

                item.setStatus(isChecked);

                strikeThruLineUpdate(holder.itemTitle,isChecked);
                strikeThruLineUpdate(holder.itemDescription,isChecked);

                Animation shake = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.shake);
                holder.itemView.startAnimation(shake);

            }
        });

    }

    private void strikeThruLineUpdate(TextView textView, boolean isChecked){

        if(isChecked){
            textView.setPaintFlags(textView.getPaintFlags() | android.graphics.Paint.STRIKE_THRU_TEXT_FLAG);
        }else{
            textView.setPaintFlags(textView.getPaintFlags() & (~android.graphics.Paint.STRIKE_THRU_TEXT_FLAG));
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
