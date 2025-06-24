package com.example.todo1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<Task> taskList;
    private TaskDatabaseHelper dbHelper;

    public TaskAdapter(List<Task> taskList, TaskDatabaseHelper dbHelper) {
        this.taskList = taskList;// assigns the passed task list to the adapter's own instance variable
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.textViewTask.setText(task.getName());
        holder.textViewDueDate.setText("Due: " + task.getDueDate() + " " + task.getDueTime());
        holder.textViewCategory.setText("Category: " + task.getCategory());

        // remove task on checkbox click
        holder.checkBoxTask.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                dbHelper.deleteTask(task.getId());
                taskList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, taskList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBoxTask;
        TextView textViewTask, textViewDueDate, textViewCategory;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxTask = itemView.findViewById(R.id.checkBoxTask);
            textViewTask = itemView.findViewById(R.id.textViewTask);
            textViewDueDate = itemView.findViewById(R.id.textViewDueDate);
            textViewCategory = itemView.findViewById(R.id.textViewCategory);
        }
    }
}
