package com.example.pedroramalho.lab_4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    public static final String TASKS_FILE = "com.example.pedroramalho.TasksFile";
    public static final String NUM_TASKS = "NumOfTasks";
    public static final String TASK = "task_";
    public static final String DESC = "desc_";
    public static final String PIC = "pic_";

    public static final String taskExtra = "Task";

    private int listItemPosition = -1;

    static public ArrayList<Task> myTasks;

    static {
        myTasks = new ArrayList<Task>();
        myTasks.add(new Task("Pedro Ramalho", "Frito","19"));
        myTasks.add(new Task("João Pereira", "Louco", "12"));
        myTasks.add(new Task("Luís Sousa", "LOuco", "12"));
    }
    // Used to load the 'native-lib' library on application startup.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton add= (FloatingActionButton) findViewById(R.id.addPerson);

        final FloatingActionButton remove=(FloatingActionButton) findViewById(R.id.removePerson);

        final TaskListFragment taskFr = (TaskListFragment) getSupportFragmentManager().findFragmentById(R.id.taskFragment);

        final ArrayAdapter<Task> taskAdapter = (ArrayAdapter<Task>) taskFr.getListAdapter();

        taskFr.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "" + parent.getItemAtPosition(position), Toast.LENGTH_LONG).show();



                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

                    TaskInfoFragment frag = (TaskInfoFragment) getSupportFragmentManager().findFragmentById(R.id.displayFragment);
                    frag.displayTask((Task) parent.getItemAtPosition(position));
                    final Task choosed_task=(Task) parent.getItemAtPosition(position);
                    remove.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {



                                myTasks.remove(choosed_task);

                                Intent intent =new Intent(getApplicationContext(),MainActivity.class);

                                startActivity(intent);



                        }
                    });
                } else {
                    startSecondActivity(parent, position);
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"button clicked",Toast.LENGTH_SHORT);
                Intent intent = new Intent(getApplicationContext(),Add_Activity.class);
                startActivity(intent);

                }
        });

    }

    private boolean saveTasks() {

        SharedPreferences tasks = getSharedPreferences(TASKS_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = tasks.edit();

        editor.clear();

        editor.putInt(NUM_TASKS, myTasks.size());

        for (Integer i = 0; i < myTasks.size(); i++) {
            editor.putString(TASK + i.toString(), myTasks.get(i).title);
            editor.putString(DESC + i.toString(), myTasks.get(i).description);
            editor.putString(PIC + i.toString(), myTasks.get(i).picPath);
        }
        return editor.commit();

    }

    private void startSecondActivity(AdapterView<?> parent, int position) {

        Intent intent = new Intent(getApplicationContext(), TaskInfoActivity.class);
        Task tmp = (Task) parent.getItemAtPosition(position);

        intent.putExtra(taskExtra, tmp);
        startActivity(intent);
    }


    public void addClick(View view) {
        String task_title = ((EditText) findViewById(R.id.taskTitle)).getText().toString();
        String task_desc = ((EditText) findViewById(R.id.taskDescription)).getText().toString();

        if (task_desc.isEmpty() && task_title.isEmpty()) {

            myTasks.add(new Task(getResources().getString(R.string.defaultTitle), getResources().getString(R.string.defaultDesc)));
        } else {

            if (task_title.isEmpty())
                task_title = getResources().getString(R.string.defaultTitle);
            if (task_desc.isEmpty())
                task_desc = getResources().getString(R.string.defaultDesc);
            ;


            myTasks.add(new Task(task_title, task_desc));

        }

        TaskListFragment taskFr = (TaskListFragment) getSupportFragmentManager().findFragmentById(R.id.taskFragment);

        ArrayAdapter<Task> taskAdapter = (ArrayAdapter<Task>) taskFr.getListAdapter();

        taskAdapter.notifyDataSetChanged();

        ((EditText) findViewById(R.id.taskTitle)).setText(null);
        ((EditText) findViewById(R.id.taskDescription)).setText(null);

        View focusedView = getCurrentFocus();
        if (focusedView != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }


    }
    @Override
    protected void onResume(){
        super.onResume();
        TaskListFragment taskFr = (TaskListFragment) getSupportFragmentManager().findFragmentById(R.id.taskFragment);

        ArrayAdapter<Task> taskAdapter = (ArrayAdapter<Task>) taskFr.getListAdapter();

        taskAdapter.notifyDataSetChanged();

    }

    private void restoreTasks() {
        SharedPreferences tasks = getSharedPreferences(TASKS_FILE, MODE_PRIVATE);
        int numOfTasks = tasks.getInt("NumOfTasks", 0);
        if (numOfTasks != 0) {

            myTasks.clear();
            for (Integer i = 0; i < numOfTasks; i++) {
                String title = tasks.getString(TASK + i.toString(), "0");
                String desc = tasks.getString(DESC + i.toString(), "0");
                String pic_path = tasks.getString(PIC + i.toString(), "");
                Task tmp = new Task(title, desc, pic_path);

                myTasks.add(tmp);
            }
        }
        restoreTasksFromFile();
    }

    private void saveTasksToFile() {
        String filename = "myTasks.txt";
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputStream.getFD()));
            String delim = ";";

            for (Integer i = 0; i < myTasks.size(); i++) {
                Task tmp = myTasks.get(i);
                String line = tmp.title + delim + tmp.description + delim + tmp.age;
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void restoreTasksFromFile() {
        String filename = "myTasks.txt";
        FileInputStream inputStream;
        StringBuilder contents = new StringBuilder();
        try {
            inputStream = openFileInput(filename);
            BufferedReader reader = new BufferedReader(new FileReader(inputStream.getFD()));
            String line;
            while ((line = reader.readLine()) != null) {

                contents.append(line);
                contents.append('\n');
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
        if (!saveTasks()) {
            Toast.makeText(getApplicationContext(), "Save failed!", Toast.LENGTH_LONG).show();
        }
        saveTasksToFile();
    }
}
