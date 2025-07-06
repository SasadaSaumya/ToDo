package com.sasadev.todo;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sasadev.todo.adapter.TodoItemAdapter;
import com.sasadev.todo.models.TodoItem;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ArrayList<TodoItem> todoItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        todoItems = new ArrayList<>();

        todoItems.add(new TodoItem("Gym","test","43234",true,"work"));
        todoItems.add(new TodoItem("Gym","test","43234",true,"work"));
        todoItems.add(new TodoItem("Gym","test","43234",false,"work"));
        todoItems.add(new TodoItem("Gym","test","43234",false,"work"));
        todoItems.add(new TodoItem("Gym","test","43234",false,"work"));
        todoItems.add(new TodoItem("Gym","test","43234",false,"work"));
        todoItems.add(new TodoItem("Gym","test","43234",false,"work"));
        todoItems.add(new TodoItem("Gym","test","43234",false,"work"));
        todoItems.add(new TodoItem("Gym","test","43234",false,"work"));
        todoItems.add(new TodoItem("Gym","test","43234",false,"work"));


        RecyclerView recyclerView = findViewById(R.id.toDoItemRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TodoItemAdapter(todoItems));

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                todoItems.remove(position);
                recyclerView.getAdapter().notifyItemChanged(position);

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View dialogView = getLayoutInflater().inflate(R.layout.add_item_dialog,null);
                builder.setView(dialogView);

                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });



    }

}