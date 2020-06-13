package com.app.mytasks.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.mytasks.R;
import com.app.mytasks.database.DBHelper;
import com.app.mytasks.database.MyDataBase;
import com.app.mytasks.database.TaskModel;

public class AddTaskActivity extends AppCompatActivity {


    private EditText edTitle;
    private EditText edDetails;
    private Button btSave;

    private MyDataBase myDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        myDataBase = MyDataBase.getInstance(this);

        edDetails = findViewById(R.id.ed_task_desc);
        edTitle = findViewById(R.id.ed_task_title);

        btSave = findViewById(R.id.bt_submit);
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edTitle.getText().toString();
                String details = edDetails.getText().toString();
                if (!title.isEmpty() && !details.isEmpty()) {
                    TaskModel model = new TaskModel();
                    model.setTitle(title);
                    model.setDetails(details);

                    myDataBase.insertTask(model);
                    edDetails.setText("");
                    edTitle.setText("");
                    finish();
                } else {
                    Toast.makeText(AddTaskActivity.this, "Please fill the data!", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}
