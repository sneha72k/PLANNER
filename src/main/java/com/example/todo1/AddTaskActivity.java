package com.example.todo1;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
//s
public class AddTaskActivity extends AppCompatActivity {
    private TaskDatabaseHelper dbHelper;
    private EditText editTextTask, editTextDueDate, editTextDueTime;
    private Spinner spinnerCategory;
    private Button buttonDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tasks);

        dbHelper = new TaskDatabaseHelper(this);
        editTextTask = findViewById(R.id.editTextTask);
        editTextDueDate = findViewById(R.id.editTextDueDate);
        editTextDueTime = findViewById(R.id.editTextDueTime);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        buttonDone = findViewById(R.id.buttonDone);

        // Setup category list
        List<String> categories = new ArrayList<>();
        categories.add("Work");
        categories.add("College");
        categories.add("Personal");
        categories.add("Shopping");
        categories.add("Health");
        categories.add("Other");

        // Custom Spinner Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories) {
            @Override   //how each item is displayed
            public View getView(int position, View convertView, ViewGroup parent) {  //user makes a choice-recycled view-
                View view = super.getView(position, convertView, parent);
                ((TextView) view).setTextColor(Color.parseColor("#7A8AE4")); // Change selected item text color
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                view.setBackgroundColor(Color.parseColor("#CFD4EFFF")); // Dropdown background color
                ((TextView) view).setTextColor(Color.parseColor("#7A8AE4")); // Dropdown text color
                return view;
            }
        };
        //----------------
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        // Open Date Picker when clicking Due Date field
        editTextDueDate.setOnClickListener(v -> showDatePicker());

        // Open Time Picker when clicking Due Time field
        editTextDueTime.setOnClickListener(v -> showTimePicker());//view that was clicked

        buttonDone.setOnClickListener(v -> {
            String taskText = editTextTask.getText().toString();
            String dueDate = editTextDueDate.getText().toString();
            String dueTime = editTextDueTime.getText().toString();
            String category = spinnerCategory.getSelectedItem().toString(); // Get selected category

            if (!taskText.isEmpty()) {
                dbHelper.addTask(taskText, dueDate, dueTime, category);
                Toast.makeText(this, "Task Added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddTaskActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Enter a task", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            editTextDueDate.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
        }, year, month, day);

        datePickerDialog.show();
    }

    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, selectedHour, selectedMinute) -> {
            editTextDueTime.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
        }, hour, minute, true);

        timePickerDialog.show();
    }
}
