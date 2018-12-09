package com.example.pedroramalho.lab_4;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TaskInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_info);

        TextView title=(TextView)findViewById(R.id.TitleTextView);
        TextView description=(TextView)findViewById(R.id.descTextView);
        TextView age= (TextView)findViewById(R.id.ageTextView);
        ImageView photo = (ImageView)findViewById(R.id.taskPhoto);

        final Task tmp = getIntent().getExtras().getParcelable(MainActivity.taskExtra);

        final String t =tmp.title;
        final String d= tmp.description;
        final String a =tmp.age;



        age.setText(a);
        if(a.equals("")==true){age.setText("--");}
        title.setText(t);
        description.setText(d);

       final FloatingActionButton delete=(FloatingActionButton) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < MainActivity.myTasks.size() ; i++) {


                if (MainActivity.myTasks.get(i).title.equals(t) && MainActivity.myTasks.get(i).description.equals(d) &&MainActivity.myTasks.get(i).age.equals(a))
                {
                    MainActivity.myTasks.remove(i);
                }

                }

                finish();
            }
        });
    }
}
