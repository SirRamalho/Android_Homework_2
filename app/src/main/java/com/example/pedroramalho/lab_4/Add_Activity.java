package com.example.pedroramalho.lab_4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Add_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        final EditText disciption = (EditText) findViewById(R.id.taskDescription);
        final EditText title = (EditText) findViewById(R.id.taskTitle);
        final EditText age = (EditText) findViewById(R.id.taskAge);

        Button add = (Button)findViewById(R.id.addTopic);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task tmp = new Task(title.getText().toString(),disciption.getText().toString(),age.getText().toString());
                MainActivity.myTasks.add(tmp);

                finish();
            }
        });
    }
}
