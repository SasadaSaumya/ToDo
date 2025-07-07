package com.sasadev.todo;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.sasadev.todo.models.User;
import com.sasadev.todo.utils.AuthUtils;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity {

    private ArrayList<TodoItem> todoItems ;
    private User loginUser;
    String username;
    Realm realm;


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

         realm = Realm.getDefaultInstance();

        SharedPreferences sharedPreferences = getSharedPreferences("user",0);
        username =     sharedPreferences.getString("username",null);
        loginUser = realm.where(User.class).equalTo("username", username).findFirst();

        TextView textView = findViewById(R.id.welcomeTextView);
        textView.setText("Hi Welcome " + username+"!");

        loadUserTodoList();

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View dialogView = getLayoutInflater().inflate(R.layout.add_item_dialog,null);
                builder.setView(dialogView);

                AlertDialog dialog = builder.create();
                dialog.show();

                TextView cancelButton = dialogView.findViewById(R.id.cancelButton);
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        }
                });

                Button button = dialogView.findViewById(R.id.addButton);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        EditText titleEditTextView = dialogView.findViewById(R.id.toDoTitleEditText);
                        EditText descriptionEditTextView = dialogView.findViewById(R.id.toDoDescriptionEditText);
                        EditText tagTexEdittView = dialogView.findViewById(R.id.toDoTagEditText);

                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                TodoItem newTodoItem = realm.createObject(TodoItem.class);
                                newTodoItem.setId(todoItems.size());
                                newTodoItem.setTitle(titleEditTextView.getText().toString());
                                newTodoItem.setDescription(descriptionEditTextView.getText().toString());
                                newTodoItem.setDate(tagTexEdittView.getText().toString());
                                newTodoItem.setTag(tagTexEdittView.getText().toString());
                                newTodoItem.setStatus(false);

                                loginUser.getTodoItems().add(newTodoItem);
                                dialog.dismiss();
                                FancyToast.makeText(MainActivity.this,"Task Added Successful!",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                            }
                        });

                        loadUserTodoList();

                    }
                });



            }
        });

        TextView logoutText = findViewById(R.id.logoutText);
        logoutText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthUtils.signOut(MainActivity.this);
                finish();
            }
        });


    }

    private void loadUserTodoList(){

        if(loginUser == null){
            todoItems = new ArrayList<>();
        }else{
            todoItems = new ArrayList<>(
                    realm.copyFromRealm(loginUser.getTodoItems()));

        }

        if(todoItems.isEmpty()){
            LinearLayout linearLayout = findViewById(R.id.emptyStateLayout);
            linearLayout.setVisibility(View.VISIBLE);
            return;
        }

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

        // Open a Realm instance
        Realm.init(MainActivity.this);
        Realm realm = Realm.getDefaultInstance();

        try {
            // Query all User objects
            RealmResults<User> realmResults = realm.where(User.class).findAll();

            // Log usernames
            for (User user : realmResults) {
                Log.i("Sasa", "Username: " + user.getUsername());
            }
        } finally {
            // Always close Realm in finally block to prevent memory leaks
            realm.close();
        }

    }

    private void addTodoItem(TodoItem todoItem){

    }

}