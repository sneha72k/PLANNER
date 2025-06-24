package com.example.todo1;

public class Task {
    private int id;
    private String name, dueDate, dueTime, category;

    public Task(int id, String name, String dueDate, String dueTime, String category) {
        this.id = id;
        this.name = name;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
        this.category = category;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDueDate() { return dueDate; }
    public String getDueTime() { return dueTime; }
    public String getCategory() { return category; }
}
