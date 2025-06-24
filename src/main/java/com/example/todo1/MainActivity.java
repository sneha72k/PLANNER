package com.example.todo1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TaskDatabaseHelper dbHelper;
    private TaskAdapter adapter;
    private List<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new TaskDatabaseHelper(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Button buttonNewTask = findViewById(R.id.buttonNewTask);

        taskList = dbHelper.getAllTasks();
        adapter = new TaskAdapter(taskList, dbHelper);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        buttonNewTask.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivity(intent);
        });
    }
    public void logoutUser(View view) {
        startActivity(new Intent(MainActivity.this, LogoutActivity.class));
        finish();
    }

}
