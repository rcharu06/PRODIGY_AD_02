package com.example.todolist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> taskList;
    private ArrayAdapter<String> adapter;
    private ListView listView;
    private EditText editText;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);

        listView = findViewById(R.id.listView);
        editText = findViewById(R.id.editText);
        addButton = findViewById(R.id.addButton);

        listView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = editText.getText().toString();
                if (!task.isEmpty()) {
                    taskList.add(task);
                    adapter.notifyDataSetChanged();
                    editText.setText("");
                }
            }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> showEditDeleteDialog(position));
    }

    private void showEditDeleteDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_delete, null);
        builder.setView(dialogView);

        EditText editTask = dialogView.findViewById(R.id.editTask);
        Button editButton = dialogView.findViewById(R.id.editButton);
        Button deleteButton = dialogView.findViewById(R.id.deleteButton);

        editTask.setText(taskList.get(position));

        AlertDialog dialog = builder.create();

        editButton.setOnClickListener(v -> {
            String newTask = editTask.getText().toString();
            if (!newTask.isEmpty()) {
                taskList.set(position, newTask);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        deleteButton.setOnClickListener(v -> {
            taskList.remove(position);
            adapter.notifyDataSetChanged();
            dialog.dismiss();
        });

        dialog.show();
    }
}
